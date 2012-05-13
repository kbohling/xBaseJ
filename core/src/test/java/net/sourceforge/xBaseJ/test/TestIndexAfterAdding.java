/**
 * 
 */
package net.sourceforge.xBaseJ.test;

import java.io.File;
import java.io.IOException;

import net.sourceforge.xBaseJ.DBF;
import net.sourceforge.xBaseJ.xBaseJException;
import net.sourceforge.xBaseJ.fields.NumField;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author joseph mcverry
 *
 */
public class TestIndexAfterAdding {
    
    private static String prefix = "target/";
    
    @Before
    public void setUp() throws IOException, xBaseJException {
        File dir = new File(prefix + "testfiles");
        dir.mkdirs();
    }
    
	@Test
	public void testRun() throws Exception {
        NumField Elm_No = null;
        NumField Hit_Count = null;
        NumField Last_Draw_No = null;

        DBF oDB = new DBF( prefix + "testIndexAfterAdding.dbf", true);

        Elm_No          = new NumField( "ElmNo",    2, 0);
        Hit_Count       = new NumField( "HitCount", 6, 0);
        Last_Draw_No    = new NumField( "LstDrwNo", 6, 0);

        oDB.addField(Elm_No);
        oDB.addField(Hit_Count);
        oDB.addField(Last_Draw_No);


        oDB.createIndex(prefix + "testIndexAfterAdding_elmno.ndx","ElmNo",true,true);

        Elm_No.put( 14);
        Hit_Count.put( 22);
        Last_Draw_No.put( 897);
        oDB.write();

        Elm_No.put( 10);
        Hit_Count.put( 3);
        Last_Draw_No.put( 1);
        oDB.write();

        Elm_No.put( 44);
        Hit_Count.put( 33);
        Last_Draw_No.put( 301);
        oDB.write();

        oDB.close();

        DBF pDB = new DBF( prefix + "testIndexAfterAdding.dbf");
        Elm_No       = (NumField) pDB.getField("ElmNo");
        Hit_Count    = (NumField) pDB.getField("HitCount");
        Last_Draw_No = (NumField) pDB.getField("LstDrwNo");

        pDB.useIndex( prefix + "testIndexAfterAdding_elmno.ndx");

        System.out.println("first");
        pDB.find( "44");
        Assert.assertEquals("44", Elm_No.get());
        System.out.println("second");
        pDB.find( "44");
        Assert.assertEquals("44", Elm_No.get());
        
	

	}

}
