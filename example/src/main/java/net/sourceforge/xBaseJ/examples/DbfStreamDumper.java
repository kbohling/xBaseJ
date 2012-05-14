package net.sourceforge.xBaseJ.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import net.sourceforge.xBaseJ.stream.DBFStreamReader;
import net.sourceforge.xBaseJ.stream.StreamField;

/**
 * Reads and prints all columns, all rows of the specified file.
 * @author clott
 *
 */
public class DbfStreamDumper {

	public static void main(String args[]) throws Exception {

		if (args.length != 1) {
			System.err.println("Usage: DbfStreamDumper file.dbf");
			return;
		}
		
		String fileName = args[0];

		InputStream stream = new FileInputStream(new File(fileName));
		DBFStreamReader dbf = new DBFStreamReader(stream);

		// Fields index from 1
		for (int f = 1; f <= dbf.getFieldCount(); ++f) {
		    StreamField<?> fld = dbf.getField(f);
		    System.out.println("Field " + f  
		            + ": " + fld.getName() 
		            + ", type=" + fld.getType()
		            + ", len=" + fld.getLength()
		            );
		}


		int i = 0;
		while (dbf.next()) {
		    System.out.println("Record " + i);
		    for (int f = 1; f <= dbf.getFieldCount(); ++f) {
		        StreamField<?> fld = dbf.getField(f);
		        System.out.println(fld.getName() 
		                + "= >" + fld.getString() + "<");
		    }
		    ++i;
		}

		dbf.close();
	}
}

