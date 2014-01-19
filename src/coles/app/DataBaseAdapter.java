package coles.app;

import android.content.Context; 
import android.database.Cursor; 
import android.database.SQLException; 
import android.database.sqlite.SQLiteDatabase;
 
/**
 * @author Wayne
 * 
 * This class encapsulates database specific logic such as load and reload.
 * Reusable database code such as open and close is here.
 *
 */
public class DataBaseAdapter { 
 
    private DataBaseHelper mDbHelper; 
    private static SQLiteDatabase mDb; 
    private final Context adapterContext; 

 
    public DataBaseAdapter(Context context) { 
        this.adapterContext = context; 
    } 
    
    public DataBaseAdapter open() throws SQLException { 
        mDbHelper = new DataBaseHelper(adapterContext); 
         try { 
    		mDb = mDbHelper.getWritableDatabase();
        } catch (Exception ioe) { 
            throw new Error("Unable to create database\n" +  ioe.toString()); 
        } 
        try { 
            mDbHelper.openDataBase(); 
        } catch (SQLException sqle) { 
            throw sqle; 
        } 
        return this; 
    } 

    public void reload() throws SQLException { 
        rebuildTable("Building");
        rebuildTable("College");
        rebuildTable("Department");
        rebuildTable("Faculty");
        rebuildTable("Text");
        rebuildTable("majors");
        rebuildTable("traffic");
        rebuildTable("timestamp");
    } 
    
    public void rebuildTable(String tableName) 
    {
        try { 
        	mDbHelper = new DataBaseHelper(adapterContext); 
    		mDb = mDbHelper.getWritableDatabase();
    		mDbHelper.openDataBase(); 
    		mDbHelper.reloadTable(mDb, tableName);
        } catch (SQLException sqle) { 
            throw sqle; 
        } finally {
    		close();
        }
    } 
    
    public Cursor MySelect(String query) 
    { 
        return mDb.rawQuery(query, null); 
    } 
 
    public void MyCommand(String query) 
    { 
        mDb.execSQL(query); 
    } 
 
    public Cursor ExampleSelect(String myVariable) 
    { 
        String query = "SELECT locale, ? FROM android_metadata"; 
        return mDb.rawQuery(query, new String[]{myVariable}); 
    } 

    //Usage 
    // AnyDBAdatper dba = new DataBaseAdapter(contextObjecT); 
    // dba.open(); 
    // dba.ExampleCommand("en-CA", "en-GB"); 
    // dba.close(); 
    public void ExampleCommand(String myVariable1, String myVariable2) 
    { 
        String command = "INSERT INTO android_metadata (locale) SELECT ? UNION ALL SELECT ?"; 
        mDb.execSQL(command, new String[]{ myVariable1, myVariable2}); 
    } 
 
    public void close() { 
        mDbHelper.close(); 
    } 
} 
