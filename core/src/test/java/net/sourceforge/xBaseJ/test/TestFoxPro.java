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
import net.sourceforge.xBaseJ.fields.MemoField;

import org.junit.Assert;
import org.junit.Before;

public class TestFoxPro {
    
    private static String prefix = "target/";
    
    @Before
    public void setUp() throws IOException {
        File dir = new File(prefix + "testfiles");
        dir.mkdirs();
        Util.copyFile("testfiles/foxprotest.fpt", prefix + "testfiles/foxprotest.fpt");
    }

//public void testStart()
//{ i lost the copy of memofile.dbf
//    try{
//        DBF fp = new DBF("testfiles/memofile.dbf");
//        System.out.println(fp.getVersion());
//       }
//    catch (Exception e)
//    {
//       fail(e.getMessage());
//    }
//}
    
    // TODO: Re-implement this test.
    // @Test
    public void testCreateAll() throws SecurityException, xBaseJException, IOException {

        DBF fp = new DBF(prefix + "testfiles/foxprotest.dbf", DBF.FOXPRO_WITH_MEMO, true);
        fp.addField(new CharField("name", 10));
        fp.addField(new MemoField("memo"));
        fp.close();
        File f = new File(prefix + "testfiles/foxprotest.dbf");
        if (f.exists() == false)
            Assert.fail("can't find foxpro dbf file");
        f = new File(prefix + "testfiles/foxprotest.fpt");
        if (f.exists() == false)
            Assert.fail("can't find foxpro fpt file");

    }


}
