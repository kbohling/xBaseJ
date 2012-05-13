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

import org.junit.Before;
import org.junit.Test;


public class TestMultiAdd {

    private static String prefix = "target/";
    
    @Before
    public void setUp() throws IOException {
        File dir = new File(prefix + "testfiles");
        dir.mkdirs();
        Util.copyFile("testfiles/test.dbf", prefix + "testfiles/test.dbf");
    }
    
    @Test
	public void testMultipleFieldAdd() throws SecurityException, xBaseJException, IOException {
		CharField zip = null;

		CharField preDir = null;

		CharField street = null;

		CharField suffix = null;

		CharField postDir = null;

		CharField term = null;

		String zipFieldStr = "ZIP";

		String preDirFieldStr = "PREDIR";

		String streetFieldStr = "STREET";

		String suffixFieldStr = "SUFFIX";

		String postDirFieldStr = "POSTDIR";

		String termFieldStr = "TERM";


		File File = new File(prefix + "testfiles/test.dbf");

		DBF dbf = new DBF(File.getPath(), DBF.DBASEIV, true);

		zip = new CharField(zipFieldStr, 5);
		dbf.addField(zip);
		zip.put("12345");
		dbf.write();

		preDir = new CharField(preDirFieldStr, 2);
		dbf.addField(preDir);
		preDir.put("12");
		dbf.write();

		street = new CharField(streetFieldStr, 28);
		dbf.addField(street);
		street.put("12345 through 28");
		dbf.write();

		suffix = new CharField(suffixFieldStr, 4);
		dbf.addField(suffix);
		suffix.put("1234");
		dbf.write();

		postDir = new CharField(postDirFieldStr, 2);
		dbf.addField(postDir);
		postDir.put("12");
		dbf.write();

		term = new CharField(termFieldStr, 5);
		dbf.addField(term);
		term.put("12345");
		dbf.write();

	}

}