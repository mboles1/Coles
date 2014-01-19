package FAQs;

import java.util.ArrayList;
import java.util.List;


import xmltosqlite.Text2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FAQDatabaseHandler extends SQLiteOpenHelper{

	//variables
	private final static int DATABASE_VERSION = 1;
	
	//database name
	private final static String DATABASE_NAME = "FAQs_TABLE";
	
	//table name
	private static String FAQ = "FAQs";
	
	//table column names
	
	private static final String ID_NO = "id";
	private static final String QUESTION = "question";
	private static final String	ANSWER = "answer";
	
	
	public FAQDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d("FAQActivity","DatabaseHandler called");
		// TODO Auto-generated constructor stub
	}
	
	//Creating Table
		@Override
		public void onCreate(SQLiteDatabase db) {
			String CREATE_TEXT_TABLE = "CREATE TABLE " + FAQ + "(" 
					+ ID_NO + " INTEGER PRIMARY KEY,"
					+ QUESTION + " TEXT not null," 
					+ ANSWER + " TEXT not null"
					+ ")";
			db.execSQL(CREATE_TEXT_TABLE);
			Log.d("FAQActivity", "db created");
		}
		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("OnUpgrade", "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
			String dropQuery = "drop table if exists ";
	        db.execSQL(dropQuery + DATABASE_NAME);
	        onCreate(db);
		}
		
		public void deleteTable(SQLiteDatabase db){
			db.execSQL("DROP TABLE IF EXISTS " + FAQ);
		}
		
		public void addText(FAQText txt){
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(ID_NO, txt.getId()); //Text item id
			values.put(QUESTION, txt.getQuestion());
			values.put(ANSWER, txt.getAnswer());
			db.insert(FAQ,null,values);
			db.close();
			Log.d("FAQActivity","things added to db");
		}
		
		public FAQText getText(int id){
			SQLiteDatabase db = this.getReadableDatabase();
			
			Cursor cursor = db.query(FAQ, new String[] {ID_NO}, ID_NO + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
			if (cursor != null)
				cursor.moveToFirst();
			
			FAQText txt = new FAQText(Integer.parseInt(cursor.getString(0)), 
								      cursor.getString(1), 
								      cursor.getString(2));
			return txt;
		}
		
		//Getting all Text
		public List<FAQText> getAllText(int ID_NO){
			List<FAQText> listText = new ArrayList<FAQText>();
			//Select All Query
			//String selectQuery = "Select * FROM " + TEXT;
			String menuQuery = "Select * from " + FAQ + " WHERE id=" + ID_NO;
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(menuQuery, null);
			
			//looping through all rows and adding to list
			if (cursor.moveToFirst()){
				do{
					FAQText txt = new FAQText();
					txt.setId(Integer.parseInt(cursor.getString(0)));
					txt.setQuestion(cursor.getString(1));
					txt.setAnswer(cursor.getString(2));
					listText.add(txt);
				} while (cursor.moveToNext());
				
			}
			return listText;
		}
		
		// Getting contacts Count
		public int getTextCount() {
			String countQuery = "SELECT  * FROM " + FAQ;
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(countQuery, null);
	        //cursor.close();
	 
	        // return count
	        return cursor.getCount();
		}
		
		//updating Text
		public int updateText(FAQText text) {
		    SQLiteDatabase db = this.getWritableDatabase();
		    
	        ContentValues values = new ContentValues();
	        values.put(ID_NO, text.getId());
	        values.put(QUESTION, text.getQuestion());
	 
	        // updating row
	        return db.update(FAQ, values, ID_NO + " = ?",
	                new String[] { String.valueOf(text.getId()) });
		}
		
		//Deleting single roll
		public void deleteText(FAQText text){
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(FAQ, ID_NO + " = ?", new String[] { String.valueOf(text.getId())});
			db.close();
		}

}
