package net.sourceforge.xBaseJ.stream;


public class BooleanStreamField extends AbstractStreamField<Boolean> {

    public final static byte BYTETRUE = (byte) 'T';
    public final static byte BYTEFALSE = (byte) 'F';
    public static final char type = 'L';
    
    public BooleanStreamField(String name) {
        super(name, 1);
    }

    @Override
    public Boolean value() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public char getType() {
        return type;
    }

}
