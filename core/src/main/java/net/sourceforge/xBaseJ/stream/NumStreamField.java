package net.sourceforge.xBaseJ.stream;


public class NumStreamField extends AbstractStreamField<Long> {

    public static final char type = 'N';
    
    public NumStreamField(String name, int length, int decpoint) {
        super(name, length);
    }

    @Override
    public Long value() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public char getType() {
        return type;
    }

}
