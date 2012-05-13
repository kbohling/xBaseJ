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
import org.xBaseJ.fields.MemoField;


public class testDBF {

    private static String prefix = "target/";
    
    @Before
    public void setUp() throws IOException {
        File dir = new File(prefix + "testfiles");
        dir.mkdirs();
    }
    
    @Test
	public void testBuildDBF() throws SecurityException, xBaseJException, IOException {
		DBF aDB = null;
		aDB = new DBF(prefix + "testfiles/testdbt.dbf", true);

		CharField cf = null;
		cf = new CharField("char", 10);
		MemoField mf = null;
		
		mf = new MemoField("memo");

		aDB.addField(cf);
		aDB.addField(mf);
		aDB.close();

		aDB = new DBF(prefix + "testfiles/testdbt.dbf");
		cf = (CharField) aDB.getField("char");
		mf = (MemoField) aDB.getField("memo");
		cf.put("123456789");
		mf.put("123456789");

		aDB.write();

		cf.put("9");
		mf.put("9");

		aDB.write();

		aDB.close();

		aDB = new DBF(prefix + "testfiles/testdbt.dbf");

		cf = (CharField) aDB.getField("char");
		mf = (MemoField) aDB.getField("memo");

		aDB.read();

		String s = cf.get();
		Assert.assertEquals("123456789", s);

		s = mf.get();
		Assert.assertEquals("123456789", s);

		aDB.read();

		s = cf.get();
		Assert.assertEquals("9", s);

		s = mf.get();
		Assert.assertEquals("9", s);


	}
}
