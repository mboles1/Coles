package FAQs;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import xmltosqlite.DatabaseHandler;
import xmltosqlite.Text2;

import coles.app.DataBaseAdapter;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * @author Wayne
 *
 * This class provides data to the Activity classes that need text data.
 * It encapsulates the database logic that is specific to the Text table.
 * It creates the SQL, extracts the data and puts it in ArrayList objects.
 * For example, the array may contain menu items for a specified menu number. (getMenu())
 *
 */
public class MysqlFAQ {
	private static boolean dataIsInSqlLite = true; 
	// TABLE_NAME is defined as a string variable for the various tables
	// To reuse this code replace the table name in quotes, example "Text" by the appropriate table name
	// In addition to changing the table name there are a couple of locations where data type and 
	// column names need to be replaced. See the comments for the respective changes. 
	private static final String TABLE_NAME = "FAQs";
	
	// extract a single menu from the object
	public static int sqLiteTableSize (Context myContext) {
		int count = 0;
		Log.d("FAQActivity","Creating new DataBaseAdapter");
		DataBaseAdapter dba = new DataBaseAdapter(myContext);
		Log.d("FAQActivity","FAQ Table made");
		dba.open(); 
	    Cursor c = dba.MySelect("SELECT * FROM " + TABLE_NAME); 
    	try {
    	    if(c.moveToFirst()) 
    		    do {
    		    	count++;
    		    } while (c.moveToNext()); 
    	} catch (Exception e) {
    		Log.d("MenuActivity", "getTableSize\n" + e.toString());
    	} finally {
    		c.close();
    	    //dba.close(); 
    	}
    	dba.close();
		return count;
	}	
	
	// extract a single menu from the object
	public static List<FAQText> getFAQ (Context myContext, int id) {
		//ArrayList<FAQText> faqmenu = new ArrayList<FAQText>();
		//FAQText t;
		//Log.d("MenuActivity", "At start oF getMenu. menuNum = " + menuNum);
		FAQDatabaseHandler dba = new FAQDatabaseHandler(myContext);
//		ArrayList<FAQText> faq = new ArrayList<FAQText>();
//		FAQ t;
//		DataBaseAdapter dba = new DataBaseAdapter(myContext); 
//		dba.open(); 
//	    Cursor c = dba.MySelect("SELECT * FROM " + TABLE_NAME + " WHERE id= " + (id+1)); 
//	    String s1 = ""; 
//    	int cols = c.getColumnCount();
//    	try {
//    	    if(c.moveToFirst()) 
//    		    do {
//    		    	s1 = c.getString(0);
//    		    	for (int j = 1; j < cols; j++) 
//    		    	{
//    		    		s1 += ", " + c.getString(j);
//    		    		
//    		    	}
//    		    	Log.d("MenuActivity ", s1);
//    				// Changes required in the following statement
//    				// Update the data types (int, char,string, etc.) to reflect the column data types 
//    				// for the current table column names.
//
//					t = new FAQ(Integer.parseInt(c.getString(1)),
//							c.getString(2),
//							c.getString(3));
//					faq.add(t);
//    		    } while (c.moveToNext()); 
//    	} catch (Exception e) {
//    		Log.d("MenuActivity", "Error while processing Cursor / scanSqlLiteTextTable, e = " + e.toString());
//    	} finally {
//    		c.close();
//    	    dba.close(); 
//    	}
		return dba.getAllText(id);
	}	
	
	// scan data in the SqlLite database
	public void scanSqlLiteTextTable (Context myContext) {
		Log.d("MenuActivity", "At start of MysqlText / scanSqlLiteTextTable");
	    DataBaseAdapter dba = new DataBaseAdapter(myContext); 
	    dba.open(); 
	    Cursor c = dba.MySelect("Select * from " + TABLE_NAME); 
	    String s1 = ""; 
	    int count = 0;
    	int cols = c.getColumnCount();
    	try {
    	    if(c.moveToFirst()) 
    		    do {
    		    	s1 = c.getString(0);
    		    	for (int j = 1; j < cols; j++) 
    		    	{
    		    		s1 += ", " + c.getString(j);
    		    	}
    				count++;
    		    } while (c.moveToNext()); 
    	} catch (Exception e) {
    		Log.d("MenuActivity", "Error while processing Cursor / scanSqlLiteTextTable, e = " + e.toString());
    	} finally {
    		c.close();
    	    dba.close(); 
    	}
		Log.d("MenuActivity", "At end of MysqlText / scanSqlLiteTextTable, count = " + count);
	}	
	
	// load data into the SqlLite database
	@SuppressWarnings("unused")
	public void loadSqlLiteTextTable (Context myContext, boolean sourceIsMySQL) {
		if (!dataIsInSqlLite) 
		{
			if (sourceIsMySQL) {
				Log.d("MenuActivity", "At start of SQLite Database reload from MySQL.");
				ArrayList<FAQText> FAQArray = loadFAQ(); // loads from web service into private ArrayList FAQArray
				DataBaseAdapter dba = new DataBaseAdapter(myContext); 
			    dba.open(); 
			    
				String query_string = "DROP TABLE IF EXISTS " + TABLE_NAME;
				dba.MyCommand(query_string);

				// Changes required in the following statement
				// Update the column names with the current table column names.
				
				query_string = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
						" id INTEGER PRIMARY KEY AUTOINCREMENT," +
						" question TEXT," +
						" answer TEXT)";
				Log.d("MenuActivity", query_string);
				dba.MyCommand(query_string);

				FAQ faq;
				query_string = "";
				for( int i = 0 ; i < FAQArray.size();i++) {
					
					// Changes required in the following statement
					// Update the column names with the current table column names.
					
					query_string = "INSERT INTO " + TABLE_NAME + " VALUES("; //9, 1, 5, 'Advising','','advising')";
					query_string += FAQArray.get(i).id;
					query_string += "', '" + FAQArray.get(i).question;
					query_string += "', '" + FAQArray.get(i).answer;
					query_string += "')";
					Log.d("MenuActivity", query_string);
					dba.MyCommand(query_string);
				} 
	    	    dba.close(); 
				Log.d("MenuActivity", "SQLite Database was cleared and reloaded from MySQL");
			} else {
				Log.d("MenuActivity", "At start of SQLite Database reload.");
				DataBaseAdapter dba = new DataBaseAdapter(myContext); 
				dba.reload();
				Log.d("MenuActivity", "SQLite Database was cleared and reloaded");
			}
			dataIsInSqlLite = true;
		}
	}	
	
	// reset load flag
	public static void resetLoadFlag () {
		if (dataIsInSqlLite) 
		{
			dataIsInSqlLite = false;
			Log.d("MenuActivity", "dataIsInSqlLite was reset.");
		}
	}	
	
	// load the object from the SqlLite database
//	public void loadFAQSqlLite () {
//	}	
	
	// load the object from the SqlLite database
	public static int mySqlTableSize () {
		return loadFAQ().size();
	}	
	
	// load the object from the MySQL database
	public static ArrayList<FAQText> loadFAQ () {
		ArrayList<FAQText> FAQArray = new ArrayList<FAQText>();
		// get data load array
//		FAQArray = null;
		// sax stuff
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			FAQTextHandler dataHandler = new FAQTextHandler();
			xr.setContentHandler(dataHandler);
			xr.parse("http://130.218.51.52/ws/get_faqs_client.php");
			FAQArray = dataHandler.getData();
		} catch(ParserConfigurationException pce) {
			Log.e("SAX XML", "sax parse error", pce);
		} catch(SAXException se) {
			Log.e("SAX XML", "sax error", se);
		} catch(IOException ioe) {
			Log.d("SAX XML", "sax parse io error", ioe);
			FAQArray = new ArrayList<FAQText>();
			FAQArray.add(new FAQText(99, "This is the question", "This is the answer"));
		}
		return FAQArray;
	}
	
//	public FAQText getItem(Context myContext, int itm) {
//		return getFAQ(myContext, itm).get(itm);
//	}
	
	
	public String getFAQ(String text) {
		String desc = TABLE_NAME + " Not Found";

		return desc;
	}
}
