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
import net.sourceforge.xBaseJ.fields.LogicalField;
import net.sourceforge.xBaseJ.fields.MemoField;
import net.sourceforge.xBaseJ.fields.NumField;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * test packing logic
 * @author joseph mcverry
 *
 */
public class TestPack {
    
    private static String prefix = "target/";
    
    @Before
    public void setUp() throws IOException, xBaseJException {
        File dir = new File(prefix + "testfiles");
        dir.mkdirs();
        // Util.copyFile("testfiles/class.DBF", prefix + "testfiles/class.DBF");
        // Util.copyFile("testfiles/class.dbt", prefix + "testfiles/class.dbt");
        Util.copyFile("testfiles/classId.ndx", prefix + "testfiles/classId.ndx");
        Util.copyFile("testfiles/crw.DBF", prefix + "testfiles/crw.DBF");
        Util.copyFile("testfiles/crw.fpt", prefix + "testfiles/crw.fpt");
    }

	public void build() throws SecurityException, xBaseJException, IOException
	{
	    //Create a new dbf file
	    DBF aDB=new DBF(prefix + "testfiles/class.DBF",true);

	    //Create the fields

	    CharField classId = new CharField("classId",9);
	    CharField className = new CharField("className",25);
	    CharField teacherId = new CharField("teacherId",9);
	    CharField daysMeet = new CharField("daysMeet",7);
	    CharField timeMeet =new CharField("timeMeet",4);
	    NumField credits = new NumField("credits",2, 0);
	    LogicalField UnderGrad = new LogicalField("UnderGrad");
	    MemoField discuss = new MemoField("discuss");


	    //Add field definitions to database
	    aDB.addField(classId);
	    aDB.addField(className);
	    aDB.addField(teacherId);
	    aDB.addField(daysMeet);
	    aDB.addField(timeMeet);
	    aDB.addField(credits);
	    aDB.addField(UnderGrad);
	    aDB.addField(discuss);

	    aDB.createIndex(prefix + "testfiles/classId.ndx","classId",true,true);     //  true - delete ndx, true - unique index,
	    aDB.createIndex(prefix + "testfiles/TchrClass.ndx","teacherID+classId", true, false);     //true - delete NDX,  false - unique index,
	    //System.out.println("index created");

	    classId.put("JAVA10100");
	    className.put("Introduction to JAVA");
	    teacherId.put("120120120");
	    daysMeet.put("NYNYNYN");
	    timeMeet.put("0800");
	    credits.put(3);
	    UnderGrad.put(true);
	    discuss.put("Intro class");

	    aDB.write();

	    classId.put("JAVA10200");
	    className.put("Intermediate JAVA");
	    teacherId.put("300020000");
	    daysMeet.put("NYNYNYN");
	    timeMeet.put("0930");
	    credits.put(3);
	    UnderGrad.put(true);
	    discuss.put("itermediate class");

	    aDB.write();

	    classId.put("JAVA102D0");
	    className.put("Interm");
	    teacherId.put("300020000");
	    daysMeet.put("ND");
	    timeMeet.put("0930");
	    credits.put(3);
	    UnderGrad.put(true);
	    discuss.put("itermediate class");

	    aDB.write();

	    aDB.delete();


	    classId.put("JAVA501");
	    className.put("JAVA And Abstract Algebra");
	    teacherId.put("120120120");
	    daysMeet.put("NNYNYNN");
	    timeMeet.put("0930");
	    credits.put(6);
	    UnderGrad.put(false);
	    discuss.put("weird class");

	    aDB.write();

	    aDB.close();

	    aDB = null;
	}

	// XXX: Fix this test.
	// @Test
	public void testPack() throws SecurityException, xBaseJException, IOException, CloneNotSupportedException
	{
		build();
		DBF dbf = new DBF(prefix + "testfiles/class.DBF");

		Assert.assertEquals(4, dbf.getRecordCount());

		dbf.pack();

		Assert.assertEquals(3, dbf.getRecordCount());

		for (int i = 1; i < 4; i++)
		{
			dbf.gotoRecord(i);
			String bean = dbf.getField(1).get();
			if (i == 1)
				Assert.assertEquals("JAVA10100", bean);
			else if (i == 2)
				Assert.assertEquals("JAVA10200", bean);
			else
				Assert.assertEquals("JAVA501", bean);
		}

		dbf = null;



	}

	@Test
	public void testPackwithFPT() throws SecurityException, xBaseJException, IOException, CloneNotSupportedException {
	    DBF dbf = new DBF(prefix + "testfiles/crw.DBF");

	    int recCnt = dbf.getRecordCount();

	    dbf.pack();

	    Assert.assertEquals(dbf.getRecordCount(), recCnt);

	}
	
	/*
	 * If you delete every
record in a DBF, then call pack followed by a reindex, then attempt to
re-add a record which contains a prior unique key value, you will fail with
a duplicate key error. It appears the index doesn't get initialized when
reindex knows there are zero records on file.
	 */
	@Test
	public void testBugDeleteAllPackReindexReadd() throws xBaseJException, IOException, SecurityException, CloneNotSupportedException
	{
		build();
		DBF aDB = new DBF(prefix + "testfiles/class.DBF");
		aDB.useIndex(prefix + "testfiles/classId.ndx");
		aDB.useIndex(prefix + "testfiles/TchrClass.ndx");
		
	
		for (int i = 0; i < aDB.getRecordCount(); i++) {
			aDB.gotoRecord(i+1);
			aDB.delete();
		}
		aDB.pack();
		aDB.getIndex(1).reIndex();
		aDB.getIndex(2).reIndex();
		Field classId = aDB.getField("classId");
		Field className = aDB.getField("className");
		Field teacherId = aDB.getField("teacherId");
		Field daysMeet = aDB.getField("daysMeet");
		Field timeMeet =aDB.getField("timeMeet");
//		Field credits = aDB.getField("credits");
//		Field UnderGrad = aDB.getField("UnderGrad");
		Field discuss = aDB.getField("discuss");
		
		classId.put("JAVA10100");
		className.put("Introduction to JAVA");
		teacherId.put("120120120");
		daysMeet.put("NYNYNYN");
		timeMeet.put("0800");
		discuss.put("Intro class");

		aDB.write();
		aDB = null;
		
	}
  

}
