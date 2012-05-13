package net.sourceforge.xBaseJ.test;
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


import java.io.File;
import java.io.IOException;

import net.sourceforge.xBaseJ.DBF;
import net.sourceforge.xBaseJ.DBFConstants;
import net.sourceforge.xBaseJ.Util;
import net.sourceforge.xBaseJ.xBaseJException;
import net.sourceforge.xBaseJ.fields.CharField;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MissingMDX {

    private static String prefix = "target/";
    
    @Before
    public void setUp() throws IOException {
        File dir = new File(prefix + "testfiles");
        dir.mkdirs();
        Util.copyFile("testfiles/test.dbf", prefix + "testfiles/test.dbf");
    }
    
    @Test
	public void testMissingMDX() throws  xBaseJException, IOException
	{
		Assert.assertEquals(Util.getxBaseJProperty("ignoreMissingMDX"), "");

		File f = new File(prefix + "testfiles/test.dbf");
		f.delete();
		f = new File(prefix + "testfiles/test.mdx");
		f.delete();
		DBF d = new DBF(prefix + "testfiles/test.dbf", DBFConstants.DBASEIV, true );
		d.addField(new CharField("one", 10));
		d.close();
		f = new File(prefix + "testfiles/test.mdx");
		f.delete();
		d = new DBF(prefix + "testfiles/test.dbf");
		d.write();
		d.close();
	}

    @Test
	public void testSetPropertyMissingMDXTrue() throws  xBaseJException, IOException
	{
		File f = new File(prefix + "testfiles/test.dbf");
		f.delete();
		f = new File(prefix + "testfiles/test.mdx");
		f.delete();
		DBF d = new DBF(prefix + "testfiles/test.dbf", DBFConstants.DBASEIV, true );
		d.addField(new CharField("one", 10));
		d.close();
		f = new File(prefix + "testfiles/test.mdx");
		f.delete();
		Util.setxBaseJProperty("ignoreMissingMDX", "true");
		Assert.assertEquals(Util.getxBaseJProperty("ignoreMissingMDX"), "true");
		d = new DBF(prefix + "testfiles/test.dbf");
		d.write();
		d.close();
	}
    
    @Test
	public void testSetPropertyMissingMDXFalse() throws  xBaseJException, IOException
	{
		File f = new File(prefix + "testfiles/test.dbf");
		f.delete();
		f = new File(prefix + "testfiles/test.mdx");
		f.delete();
		DBF d = new DBF(prefix + "testfiles/test.dbf", DBFConstants.DBASEIV, true );
		d.addField(new CharField("one", 10));
		d.close();
		f = new File(prefix + "testfiles/test.mdx");
		f.delete();
		Util.setxBaseJProperty("ignoreMissingMDX", "false");
		Assert.assertEquals(Util.getxBaseJProperty("ignoreMissingMDX"), "false");
		d = new DBF(prefix + "testfiles/test.dbf");
		d.write();
		d.close();
	}

}
