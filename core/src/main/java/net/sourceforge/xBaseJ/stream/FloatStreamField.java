package net.sourceforge.xBaseJ.stream;


public class FloatStreamField extends AbstractStreamField<Double> {

    public static final char type = 'F';
    
    public FloatStreamField(String name, int length, int decpoint) {
        super(name, length);
    }

    @Override
    public Double value() {
        return null;
    }

    @Override
    public char getType() {
        return type;
    }

}
