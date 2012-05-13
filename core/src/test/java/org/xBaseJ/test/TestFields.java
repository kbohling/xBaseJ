/**
 * xBaseJ - Java access to dBase files
 *<p>Copyright 1997-2011 - American Coders, LTD  - Raleigh NC USA
 *<p>All rights reserved
 *<p>Currently supports only dBase III format DBF, DBT and NDX files
 *<p>                        dBase IV format DBF, DBT, MDX and NDX files
*<p>American Coders, Ltd
*<br>P. O. Box 97462
*<br>Raleigh, NC  27615  USA
*<br>1-919-846-2014
*<br>http://www.americancoders.com
@author Joe McVerry, American Coders Ltd.
@version 2.2.0
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
package org.xBaseJ.test;


import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xBaseJ.DBF;
import org.xBaseJ.xBaseJException;
import org.xBaseJ.fields.CharField;
import org.xBaseJ.fields.CurrencyField;
import org.xBaseJ.fields.DateField;
import org.xBaseJ.fields.FloatField;
import org.xBaseJ.fields.LogicalField;
import org.xBaseJ.fields.NumField;
import org.xBaseJ.fields.PictureField;


public class TestFields {

    private static String prefix = "target/";
	CharField f;
    
    @Before
    public void setUp() throws IOException, xBaseJException {
        File dir = new File(prefix + "testfiles");
        dir.mkdirs();
		f = new CharField("test", 10);
    }

    @Test
	public void testPutString() throws xBaseJException {
		f.put("a");
		Assert.assertEquals("a",f.get());
	}
	
	@Test
	public void testType() throws xBaseJException, IOException  {
	    CharField  c = new CharField("C", 1);
	    Assert.assertEquals('C', c.getType());
	    DateField  d = new DateField("D");
	    Assert.assertEquals('D', d.getType());
	    FloatField f = new FloatField("F", 10, 2); 
	    Assert.assertEquals('F', f.getType());
	    NumField n = new NumField("N", 10, 2);
	    Assert.assertEquals('N', n.getType());
	    LogicalField l = new LogicalField("L");
	    Assert.assertEquals('L', l.getType());
	    PictureField p = new PictureField("P");
	    Assert.assertEquals('P', p.getType());
	    CurrencyField cc = new CurrencyField("Money");
	    Assert.assertEquals('Y', cc.getType());
			
	}
	
	
	@Test
	public void testFloat() throws SecurityException, xBaseJException, IOException {
	    try {
	        DBF db = new DBF(prefix + "testfiles/float.dbf", true);
	        FloatField f = new FloatField("F", 10,3);
	        db.addField(f);
	        f.put(987.123f);
	        db.write();
	        db.close();
	        db = new DBF(prefix + "testfiles/float.dbf");
	        f = (FloatField) db.getField("F");
	        db.read();
	        Assert.assertEquals("   987.123", f.get());
	    }
		finally {
		    File f = new File(prefix + "testfiles/float.dbf");
		    f.delete();
		}
	}

}
