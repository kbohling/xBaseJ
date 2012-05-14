package net.sourceforge.xBaseJ.stream;

public class MemoStreamField extends AbstractStreamField<String> {

    public static final char type = 'M';
 
    public MemoStreamField(String name) {
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
