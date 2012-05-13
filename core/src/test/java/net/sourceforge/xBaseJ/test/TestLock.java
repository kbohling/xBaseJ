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
package net.sourceforge.xBaseJ.test;


import java.io.File;
import java.io.IOException;

import net.sourceforge.xBaseJ.DBF;
import net.sourceforge.xBaseJ.Util;
import net.sourceforge.xBaseJ.xBaseJException;
import net.sourceforge.xBaseJ.fields.CharField;
import net.sourceforge.xBaseJ.fields.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Joe McVerry - American Coders, Ltd.
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestLock {

    private static String prefix = "target/";
    
    @Before
    public void setUp() throws IOException {
        File dir = new File(prefix + "testfiles");
        dir.mkdirs();
    }
    
    @Test
	public  void testLocking() throws IOException, SecurityException, xBaseJException {
		Util.setxBaseJProperty("useSharedLocks", "false");
        Assert.assertFalse(Util.useSharedLocks());
		DBF writer = new DBF(prefix + "testfiles/temp.dbf", true);
		Field str_field = new CharField("st", 10);
		writer.addField(str_field);
		writer.close();
		writer = new DBF(prefix + "testfiles/temp.dbf");
		str_field = writer.getField(1);
		str_field.put("abcd");
		writer.write(true);
		str_field.put("abcd2");
		writer.write(true);
		writer.close();



	}
}
