package net.sourceforge.xBaseJ.stream;



public class StringStreamField extends AbstractStreamField<String> {
    
    public static final char type = 'C';
    
    public StringStreamField(String name, int length) {
        super(name, length);
    }

    public char getType() {
        return type;
    }
    
    public String value() {
        String value = getString();
        return value;
    }

}
