package net.sourceforge.xBaseJ.stream;

import java.util.Date;

public class DateStreamField extends AbstractStreamField<Date> {

    public static final char type = 'D';
    
    public DateStreamField(String name) {
        super(name, 8);
    }

    @Override
    public Date value() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public char getType() {
        return type;
    }
}
