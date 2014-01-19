package coles.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Wayne
 * 
 * This module contains generic reusable code for the SQLite database as a whole.
 * It is largely derived directly from a module found on the web.
 * It is mostly concerned with creating the "first-time" SQLite database when
 * the app is first installed by the user. (i.e. no Internet connection.)
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {
	public static final String DB_PATH = "/data/data/coles.app/databases/";
	public static final String DB_NAME = "data";
	public static final int DATABASE_VERSION = 1;
 
    private SQLiteDatabase myDataBase; 
    private final Context myContext;

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		loadTable(db, "Building");
		loadTable(db, "College");
		loadTable(db, "Department");
		loadTable(db, "Faculty");
		loadTable(db, "Text");
		loadTable(db, "majors");
		loadTable(db, "traffic");
		loadTable(db, "timestamp");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("MenuActivity", "onUpgrade\n" + db.toString());
		reloadTable(db, "Building");
		reloadTable(db, "College");
		reloadTable(db, "Department");
		reloadTable(db, "Faculty");
		reloadTable(db, "Text");
		reloadTable(db, "majors");
		reloadTable(db, "traffic");
		reloadTable(db, "timestamp");
	}

	public void reloadTable(SQLiteDatabase db, String tableName) {
		db.execSQL("DROP TABLE IF EXISTS " + tableName);
		loadTable(db, tableName);
	}

	public void loadTable(SQLiteDatabase db, String selector) {
		Log.d("MenuActivity", "Starting to load " + selector + ".");

		String s;
		Short s2;
		try {
			InputStream in = myContext.getResources().openRawResource(R.raw.sql);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(in, null);
			NodeList statements = doc.getElementsByTagName(selector);
			for (int i=0; i<statements.getLength(); i++) {
				s = statements.item(i).getChildNodes().item(0).getNodeValue();
				try {
					if (selector.equals("Text")) {
						Log.d("MenuActivity", "Before convert, s = " + s);
						s2 = statements.item(i).getChildNodes().item(0).getNodeType();
						Log.d("MenuActivity", "Before convert, NodeType = " + s2);
					}
//					s = (Html.fromHtml(s)).toString();
					s = convert(s);
					if (selector.equals("Text")) {
						Log.d("MenuActivity", "After  convert, s = " + s);
					}
//					s = org.apache.commons.lang.StringEscapeUtils unescapehtml(s);
		    	} catch (Exception e) {
		    		Log.d("MenuActivity", "Error while processing Cursor / loadTable, e = " + e.toString());
		    	}
				db.execSQL(s);
			}
		} catch (Throwable t) {
			Toast.makeText(myContext, t.toString(), 50000).show();
		}
		Log.d("MenuActivity", "Load of " + selector + " is done.");
	}
	
	public void deleteAll() {
		
	}

    public SQLiteDatabase getDatabase() { 
        String myPath = DB_PATH + DB_NAME; 
        return SQLiteDatabase.openDatabase(myPath, null, 
                SQLiteDatabase.OPEN_READONLY); 
    } 

    public void createDataBase() throws IOException{
   	 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		
    	}
    	else
    	{
 
        	try {
            	this.getReadableDatabase();
        		copyDataBase();
 
    		} catch (Exception e) {
    			 
        		throw new Error("Error creating database" + e.toString());
 
        	}
    	}
 
    }
 
   
    private void copyDataBase() throws IOException{
 
    	
    	InputStream myInput = this.myContext.getAssets().open(DB_NAME);
 
    	
    	String outFileName = DB_PATH + DB_NAME;
 
    	
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
    
    public SQLiteDatabase openDataBase() throws SQLException{
 
    	
        String myPath = DB_PATH + DB_NAME;
    	return myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	private boolean checkDataBase(){
		 
		SQLiteDatabase checkDB = null;
	
		try{
	        String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	
		}catch(SQLiteException e){
	
			
	
		}
	
		if(checkDB != null){
	
			checkDB.close();
	
		}
	
		return checkDB != null ? true : false;
	}
	
	private String convert(String str) 
	{ 
	  str = str.replace("&amp;", "&"); 
	  str = str.replace("&gt;", ">"); 
	  str = str.replace("&lt;", "<"); 
	  str = str.replace("&quot;", "\""); 
	  str = str.replace("&#039;", "'");
	  str = str.replace("\\r\\n", "\n");
	  return str; 
	} 

}