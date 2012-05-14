package net.sourceforge.xBaseJ.stream;

public class PictureStreamField extends AbstractStreamField<String> {

    public static final char type = 'P';
    
    public PictureStreamField(String name) {
        super(name, -1);
    }

    @Override
    public String value() {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getType() {
        return type;
    }

}
