package net.sourceforge.xBaseJ.test;

import net.sourceforge.xBaseJ.fields.CharField;
import net.sourceforge.xBaseJ.fields.CurrencyField;
import net.sourceforge.xBaseJ.fields.DateField;
import net.sourceforge.xBaseJ.fields.FloatField;
import net.sourceforge.xBaseJ.fields.LogicalField;
import net.sourceforge.xBaseJ.fields.MemoField;
import net.sourceforge.xBaseJ.fields.NumField;
import net.sourceforge.xBaseJ.fields.PictureField;

import org.junit.Assert;
import org.junit.Test;

public class TestGetFieldTypes {

    @Test
	public void testCharField() throws Exception{
		CharField f = new CharField("a", 1);
		Assert.assertEquals(true, f.isCharField());
		Assert.assertEquals(false, f.isDateField());
		Assert.assertEquals(false, f.isFloatField());
		Assert.assertEquals(false, f.isLogicalField());
		Assert.assertEquals(false, f.isMemoField());
		Assert.assertEquals(false, f.isNumField());
		Assert.assertEquals(false, f.isPictureField());
		Assert.assertEquals(false, f.isCurrencyFIeld());
	}
    
    @Test
	public void testDateField() throws Exception{
		DateField f = new DateField("a");
		Assert.assertEquals(false, f.isCharField());
		Assert.assertEquals(true, f.isDateField());
		Assert.assertEquals(false, f.isFloatField());
		Assert.assertEquals(false, f.isLogicalField());
		Assert.assertEquals(false, f.isMemoField());
		Assert.assertEquals(false, f.isNumField());
		Assert.assertEquals(false, f.isPictureField());
		Assert.assertEquals(false, f.isCurrencyFIeld());
	}
    @Test
	public void testFloatField() throws Exception{
		FloatField f = new FloatField("a", 10, 2);
		Assert.assertEquals(false, f.isCharField());
		Assert.assertEquals(false, f.isDateField());
		Assert.assertEquals(true, f.isFloatField());
		Assert.assertEquals(false, f.isLogicalField());
		Assert.assertEquals(false, f.isMemoField());
		Assert.assertEquals(false, f.isNumField());
		Assert.assertEquals(false, f.isPictureField());
		Assert.assertEquals(false, f.isCurrencyFIeld());
	}
    @Test
	public void testLogicalField() throws Exception{
		LogicalField f = new LogicalField("a");
		Assert.assertEquals(false, f.isCharField());
		Assert.assertEquals(false, f.isDateField());
		Assert.assertEquals(false, f.isFloatField());
		Assert.assertEquals(true, f.isLogicalField());
		Assert.assertEquals(false, f.isMemoField());
		Assert.assertEquals(false, f.isNumField());
		Assert.assertEquals(false, f.isPictureField());
		Assert.assertEquals(false, f.isCurrencyFIeld());
	}
    @Test
	public void testMemoField() throws Exception{
		MemoField f = new MemoField("a");
		Assert.assertEquals(false, f.isCharField());
		Assert.assertEquals(false, f.isDateField());
		Assert.assertEquals(false, f.isFloatField());
		Assert.assertEquals(false, f.isLogicalField());
		Assert.assertEquals(true, f.isMemoField());
		Assert.assertEquals(false, f.isNumField());
		Assert.assertEquals(false, f.isPictureField());
		Assert.assertEquals(false, f.isCurrencyFIeld());
	}
    @Test
	public void testNumField() throws Exception{
		NumField f = new NumField("a", 10, 2);
		Assert.assertEquals(false, f.isCharField());
		Assert.assertEquals(false, f.isDateField());
		Assert.assertEquals(false, f.isFloatField());
		Assert.assertEquals(false, f.isLogicalField());
		Assert.assertEquals(false, f.isMemoField());
		Assert.assertEquals(true, f.isNumField());
		Assert.assertEquals(false, f.isPictureField());
		Assert.assertEquals(false, f.isCurrencyFIeld());
	}
    @Test
	public void testPictureField() throws Exception{
		PictureField f = new PictureField("a");
		Assert.assertEquals(false, f.isCharField());
		Assert.assertEquals(false, f.isDateField());
		Assert.assertEquals(false, f.isFloatField());
		Assert.assertEquals(false, f.isLogicalField());
		Assert.assertEquals(false, f.isMemoField());
		Assert.assertEquals(false, f.isNumField());
		Assert.assertEquals(true, f.isPictureField());
		Assert.assertEquals(false, f.isCurrencyFIeld());
	}
    @Test
	public void testCurrencyField() throws Exception{
		CurrencyField f = new CurrencyField("a");
		Assert.assertEquals(false, f.isCharField());
		Assert.assertEquals(false, f.isDateField());
		Assert.assertEquals(false, f.isFloatField());
		Assert.assertEquals(false, f.isLogicalField());
		Assert.assertEquals(false, f.isMemoField());
		Assert.assertEquals(false, f.isNumField());
		Assert.assertEquals(false, f.isPictureField());
		Assert.assertEquals(true, f.isCurrencyFIeld());
	}
}
