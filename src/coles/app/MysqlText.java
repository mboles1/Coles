package coles.app;


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
public class MysqlText {
//	public ArrayList<Text> textArray = new ArrayList<Text>();
	private static boolean dataIsInSqlLite = true; 
	// TABLE_NAME is defined as a string variable for the various tables
	// To reuse this code replace the table name in quotes, example "Text" by the appropriate table name
	// In addition to changing the table name there are a couple of locations where data type and 
	// column names need to be replaced. See the comments for the respective changes. 
	private static final String TABLE_NAME = "Text";
	
	// extract a single menu from the object
	public static int sqLiteTableSize (Context myContext) {
		int count = 0;
		DataBaseAdapter dba = new DataBaseAdapter(myContext); 
		dba.open(); 
	    Cursor c = dba.MySelect("Select * from " + TABLE_NAME); 
    	try {
    	    if(c.moveToFirst()) 
    		    do {
    		    	count++;
    		    } while (c.moveToNext()); 
    	} catch (Exception e) {
    		Log.d("MenuActivity", "getTableSize\n" + e.toString());
    	} finally {
    		c.close();
    	    dba.close(); 
    	}
		return count;
	}	
	
	// extract a single menu from the object
	public static List<Text2> getMenu (Context myContext, int menuNum) {
		ArrayList<xmltosqlite.Text2> menu = new ArrayList<xmltosqlite.Text2>();
		Text2 t;
		Log.d("MenuActivity", "At start oF getMenu. menuNum = " + menuNum);
		DatabaseHandler dba = new DatabaseHandler(myContext);
//		DataBaseAdapter dba = new DataBaseAdapter(myContext); 
//		dba.open(); 
//	    Cursor c = dba.MySelect("Select * from " + TABLE_NAME + " WHERE Menu_No=" + menuNum); 
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
//					t = new Text(Integer.parseInt(c.getString(0)),
//							Integer.parseInt(c.getString(1)),
//							Integer.parseInt(c.getString(2)),
//							c.getString(3),
//							c.getString(4),
//							c.getString(5));
//					menu.add(t);
//    		    } while (c.moveToNext()); 
//    	} catch (Exception e) {
//    		Log.d("MenuActivity", "Error while processing Cursor / scanSqlLiteTextTable, e = " + e.toString());
//    	} finally {
//    		c.close();
//    	    dba.close(); 
//    	}
//		Log.d("MenuActivity", "getMenu DONE.");
		List<Text2> returnList =  dba.getAllText(menuNum);
		dba.close();
		return returnList;
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
    				Log.d("MenuActivity", s1);
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
		//if (!dataIsInSqlLite) 
	//	{
			if (sourceIsMySQL) {
				Log.d("MenuActivity", "At start of SQLite Database reload from MySQL.");
				ArrayList<Text> textArray = loadText(); // loads from web service into private ArrayList textArray
				DataBaseAdapter dba = new DataBaseAdapter(myContext); 
			    dba.open(); 
			    
				String query_string = "DROP TABLE IF EXISTS " + TABLE_NAME;
				dba.MyCommand(query_string);

				// Changes required in the following statement
				// Update the column names with the current table column names.
				
				query_string = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
						" Text_No INTEGER PRIMARY KEY AUTOINCREMENT," +
						" Menu_No INTEGER," +
						" Item_Order INTEGER," +
						" Heading TEXT," +
						" Body TEXT," +
						" Link TEXT)";
				Log.d("MenuActivity", query_string);
				dba.MyCommand(query_string);

				Text txt;
				query_string = "";
				for( int i = 0 ; i < textArray.size();i++) {
					
					// Changes required in the following statement
					// Update the column names with the current table column names.
					
					query_string = "INSERT INTO " + TABLE_NAME + " VALUES("; //9, 1, 5, 'Advising','','advising')";
					query_string += textArray.get(i).id;
					query_string += ", " + textArray.get(i).menu_no;
					query_string += ", " + textArray.get(i).item_order;
					query_string += ", '" + textArray.get(i).heading;
					query_string += "', '" + textArray.get(i).body;
					query_string += "', '" + textArray.get(i).link;
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
	//}	
	
	// reset load flag
	public static void resetLoadFlag () {
		if (dataIsInSqlLite) 
		{
			dataIsInSqlLite = false;
			Log.d("MenuActivity", "dataIsInSqlLite was reset.");
		}
	}	
	
	// load the object from the SqlLite database
//	public void loadTextSqlLite () {
//	}	
	
	// load the object from the SqlLite database
	public static int mySqlTableSize () {
		return loadText().size();
	}	
	
	// load the object from the MySQL database
	public static ArrayList<Text> loadText () {
		ArrayList<Text> textArray = new ArrayList<Text>();
		Cursor myCursor;
		
		// get data load array
//		textArray = null;
		// sax stuff
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			TextHandler dataHandler = new TextHandler();
			xr.setContentHandler(dataHandler);
			xr.parse("http://130.218.51.52/ws/get_text_client.php");
			textArray = dataHandler.getData();
			
		} catch(ParserConfigurationException pce) {
			Log.e("SAX XML", "sax parse error", pce);
		} catch(SAXException se) {
			Log.e("SAX XML", "sax error", se);
		} catch(IOException ioe) {
			Log.d("SAX XML", "sax parse io error", ioe);
			textArray = new ArrayList<Text>();
			textArray.add(new Text(99, 99, 99, "Management", "Management", "IO error 77 / Can not open URL http://130.218.51.52/ws/get_text_client.php"));
		}
		return textArray;
	}
	
	public Text2 getItem(Context myContext, int itm) {
//	public Text getItem(int itm) {
//		Text txt = new Text(-1, -1, -1, "", "", "");
//		for( int i = 0 ; i < textArray.size();i++) {
//			Text txt2 = textArray.get(i);
//			if (txt == txt2) {
//				txt = txt2;
//			}
//		} 
		return getMenu (myContext, itm).get(itm);
//		return txt;
	}
	
	public String getText(String text) {
		String desc = TABLE_NAME + " Not Found";
//		for( int i = 0 ; i < textArray.size();i++) {
//			Text txt = textArray.get(i);
//			if (txt.heading.equals(text)) {
//				desc = txt.body;
//		ArrayList<Text> textArray = new ArrayList<Text>();

//		for( int i = 0 ; i < textArray.size();i++) {
//			Text txt = textArray.get(i);
//			if (txt.heading.equals(text)) {
//				desc = txt.body;
//			}
//		} 
		return desc;
	}
}
