/**
 * Copyright 2012 Kirby C. Bohling
 * Copyright 1997 - 2011 American Coders, LTD  - Raleigh NC USA
 * All Rights Reserved
 * 
 * xBaseJ - Java access to dBase files
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library Lesser General Public
 * License along with this library; if not, write to the Free
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package net.sourceforge.xBaseJ.stream;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sourceforge.xBaseJ.DBF;
import net.sourceforge.xBaseJ.DBFConstants;
import net.sourceforge.xBaseJ.Util;
import net.sourceforge.xBaseJ.xBaseJException;

public class DBFStreamReader {
    
    private final Map<String, StreamField<?>> fieldMap;
    protected InputStream stream;
    protected int currentRecord = 0;
    protected short fldCount = 0;
    protected List<StreamField<?>> fldRoot;
    protected byte version = 3;
    protected byte lUpdate[] = new byte[3];
    protected int count = 0;
    protected short offset = 0;
    protected short recLen = 0;
    protected byte incompleteTransaction = 0;
    protected byte encryptFlag = 0;
    protected byte reserve[] = new byte[12];
    protected byte language = 0;
    protected byte reserve2[] = new byte[2];
    
    public DBFStreamReader(InputStream stream) throws IOException, xBaseJException {
        this.fieldMap = new TreeMap<String, StreamField<?>>(String.CASE_INSENSITIVE_ORDER);
        this.stream = stream;
        openDBF();
    }
    
    protected void openDBF() throws IOException, xBaseJException {
        int i;

        readFileHeader();

        fldCount = (short) ((offset - 1) / 32 - 1);

        if (( version != DBFConstants.DBASEIII)
                && ( version != DBFConstants.DBASEIII_WITH_MEMO)
                && ( version != DBFConstants.DBASEIV)
                && ( version != DBFConstants.DBASEIV_WITH_MEMO)
                && ( version != DBFConstants.FOXPRO_WITH_MEMO)) {
            String mismatch = Util.getxBaseJProperty("ignoreVersionMismatch").toLowerCase();
            if (mismatch == null || (mismatch.compareTo("true") != 0 && mismatch.compareTo("yes") != 0)) {
                throw new xBaseJException("Wrong Version " + String.valueOf((short) version));
            }
        }

        fldRoot = new ArrayList<StreamField<?>>(fldCount);

        for (i = 0; i < fldCount; i++) {
            StreamField<?> f = readFieldHeaders();
            fldRoot.add(f);
            fieldMap.put(f.getName(), f);
        }

        int position = (fldCount + 1) * 32;
        int skip = offset - position;
        long actualSkipped = this.stream.skip(skip);
            
        if (actualSkipped != skip) {
            throw new IOException("Did not skip the correct amount.");
        }
        currentRecord = 0;
    }
    
    private static int readInt(InputStream stream) throws IOException {
        int ch1 = stream.read();
        int ch2 = stream.read();
        int ch3 = stream.read();
        int ch4 = stream.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }
     
    private static short readShort(InputStream stream) throws IOException {
        int ch1 = stream.read();
        int ch2 = stream.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (short)((ch1 << 8) + (ch2 << 0));
    }
     
    private static byte readByte(InputStream stream) throws IOException {
        int buffer = stream.read();
        
        if (buffer == -1) {
            throw new IOException("Short read.");
        }
        
        byte value = (byte) buffer;
        return value;
    }
    
    private static void readFully(InputStream stream, byte[] buffer, int offset, int len) throws IOException {
        int n = 0;
        do {
            int count = stream.read(buffer, offset + n, len - n);
            if (count < 0)
                throw new EOFException();
            n += count;
        } while (n < len);
    }

    protected void readFileHeader() throws IOException {
        short currentrecord = 0; // not really used

        version = readByte(stream); // 1
        stream.read(lUpdate, 0, 3); // 3 - 4

        count = Util.x86(readInt(stream)); // 4 - 8
        offset = Util.x86(readShort(stream)); // 2 - 10
        recLen = Util.x86(readShort(stream)); // 2 - 12

        currentrecord = Util.x86(readShort(stream)); // 2 - 14
        currentRecord = Util.x86(currentrecord);

        incompleteTransaction = readByte(stream); // 1 - 15
        encryptFlag = readByte(stream); // 1 - 16
        stream.read(reserve, 0, 12); // 12 - 28
        // Read the byte for the MDX existing.
        readByte(stream); // 1 - 29
        language = readByte(stream); // 1 - 30
        stream.read(reserve2, 0, 2);  // 2 - 32
    }
    
    protected StreamField<?> readFieldHeaders() throws IOException, xBaseJException {

        StreamField<?> tField;
        int i;
        byte[] byter = new byte[15];
        String name;
        char type;
        byte length;
        int iLength;
        int decpoint;

        readFully(stream, byter, 0, 11); // 11 - 11
        for (i = 0; i < 12 && byter[i] != 0; i++);
        try {
            name = new String(byter, 0, i, DBF.encodedType);
        } catch (UnsupportedEncodingException UEE) {
            name = new String(byter, 0, i);
        }

        type = (char) readByte(stream); // 1 - 12

        readFully(stream, byter, 0, 4); // 4 - 16

        length = readByte(stream); // 1 - 17
        if (length > 0)
            iLength = (int) length;
        else
            iLength = 256 + (int) length;
        decpoint = readByte(stream); // 1 - 18
        readFully(stream, byter, 0, 14); // 14 - 32

        switch (type) {
            case StringStreamField.type:
                tField = new StringStreamField(name, iLength);
                break;
            case DateStreamField.type :
                tField = new DateStreamField(name);
                break;
            case FloatStreamField.type :
                tField = new FloatStreamField(name, iLength, decpoint);
                break;
            case BooleanStreamField.type :
                tField = new BooleanStreamField(name);
                break;
            case NumStreamField.type :
                tField = new NumStreamField(name, iLength, decpoint);
                break;
            case CurrencyStreamField.type :
                tField = new CurrencyStreamField(name);
                break;
            case MemoStreamField.type:
                throw new xBaseJException("Unsupported field type for streaming: " + MemoStreamField.type);
            case PictureStreamField.type :
                throw new xBaseJException("Unsupported field type for streaming: " + PictureStreamField.type);
            default :
                throw new xBaseJException("Unknown Field type '"+type+"' for " + name);
        } /* endswitch */

        return tField;
    }
    
    public int getFieldCount() {
        return fldCount;
    }

    public int getRecordCount()  {
        return count;
    }

    public int getCurrentRecordNumber() {
        return currentRecord;
    }
    
    public boolean next() throws xBaseJException, IOException {

        if (currentRecord == count) {
            return false;
        }

        currentRecord++;

        // Read the deletion indicator.
        readByte(stream);
        
        for (int i = 0; i < fldCount; i++) {
            StreamField<?> tField = fldRoot.get(i);
            tField.read(stream);
        }
        return true;
    }
    
    public StreamField<?> getField(int i)
            throws ArrayIndexOutOfBoundsException, xBaseJException {
        StreamField<?> field = getField(i, StreamField.class);
        return field;
    }
    
    public StreamField<?> getField(String name) throws xBaseJException {
        StreamField<?> field = getField(name, StreamField.class);
        return field;
    }
    
    public <T extends StreamField<?>> T getField(int i, Class<T> clazz )
        throws ArrayIndexOutOfBoundsException, xBaseJException {
        if ((i < 1) || (i > fldCount)) {
            throw new xBaseJException("Invalid Field number");
        }

        StreamField<?> rawTyped = fldRoot.get(i - 1);
        T obj = clazz.cast(rawTyped);
        return obj;
    }
    
    public <T extends StreamField<?>> T getField(String name, Class<T> clazz) throws xBaseJException {
        StreamField<?> tField = fieldMap.get(name);

        if (tField == null) {
            throw new xBaseJException("Field not found " + name);
        }
        
        T obj = clazz.cast(tField);
        return obj;
    }

    public void close() throws IOException {
        stream.close();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DBFStreamReader [currentRecord="
                + currentRecord
                + ", fldCount="
                + fldCount
                + ", "
                // + (fldRoot != null ? "fldRoot=" + fldRoot + ", " : "")
                + "version="
                + version
                + ", "
                + (lUpdate != null ? "lUpdate=" + Arrays.toString(lUpdate)
                        + ", " : "") + "count=" + count + ", offset=" + offset
                + ", recLen=" + recLen + "]";
    }
    
}
