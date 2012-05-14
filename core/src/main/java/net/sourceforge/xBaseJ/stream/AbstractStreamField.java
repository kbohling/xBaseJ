/**
 * Copyright 2012 Kirby C. Bohling
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

public abstract class AbstractStreamField<T> implements StreamField<T> {

    private final String name;
    private final int length;
    private byte[] buffer;
    
    public AbstractStreamField(String name, int length) {
        this.name = name;
        this.length = length;
        this.buffer = new byte[this.length];
    }

    @Override
    public String getName() {
        return name;
    }
    
    public int getLength() {
        return length;
    }

    @Override
    public String getString() {
        String value = new String(buffer);
        return value;
    }

    @Override
    public void read(InputStream stream) throws IOException {
        readFully(stream, buffer, 0, length);
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
    
}
