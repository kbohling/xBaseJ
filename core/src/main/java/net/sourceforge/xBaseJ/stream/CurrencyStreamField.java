package net.sourceforge.xBaseJ.stream;

import java.math.BigDecimal;

public class CurrencyStreamField extends AbstractStreamField<BigDecimal> {
    
    public static final char type = 'Y';
    
    public CurrencyStreamField(String name) {
        super(name, 8);
    }

    public char getType() {
        return type;
    }
    
    public BigDecimal value() {
        String strValue = getString();
        BigDecimal value = new BigDecimal(strValue);
        return value;
    }

}
