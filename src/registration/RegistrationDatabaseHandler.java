package registration;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RegistrationDatabaseHandler extends SQLiteOpenHelper{
	//variables
		private final static int DATABASE_VERSION = 1;
		
		//database name
		private final static String DATABASE_NAME = "Registrations_TABLE";
		
		//table name
		private static String Registration = "Registrations";
		
		//table column names
		
		private static final String ID_NO = "id";
		private static final String TITLE = "title";
		private static final String	DESCRIPTION = "answer";
		private static final String LINK = "link";
		
		
		public RegistrationDatabaseHandler(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("RegistrationActivity","DatabaseHandler called");
			// TODO Auto-generated constructor stub
		}
		
		//Creating Table
			@Override
			public void onCreate(SQLiteDatabase db) {
				String CREATE_TEXT_TABLE = "CREATE TABLE " + Registration + "(" 
						+ ID_NO + " INTEGER PRIMARY KEY,"
						+ TITLE + " TEXT not null," 
						+ DESCRIPTION + " TEXT not null,"
						+ LINK + "TEXT not null"
						+ ")";
				db.execSQL(CREATE_TEXT_TABLE);
				Log.d("RegistrationActivity", "db created");
			}
			
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				Log.w("OnUpgrade", "Upgrading database from version " + oldVersion + " to "
		                + newVersion + ", which will destroy all old data");
				String dropQuery = "drop table if exists ";
		        db.execSQL(dropQuery + DATABASE_NAME);
		        onCreate(db);
			}
			
			public void deleteTable(SQLiteDatabase db){
				db.execSQL("DROP TABLE IF EXISTS " + Registration);
			}
			
			public void addText(RegistrationText txt){
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put(ID_NO, txt.getId()); //Text item id
				values.put(TITLE, txt.getTitle());
				values.put(DESCRIPTION, txt.getDescription());
				values.put(LINK, txt.getLink());
				db.insert(Registration,null,values);
				db.close();
				Log.d("RegistrationActivity","things added to db");
			}
			
			public RegistrationText getText(int id){
				SQLiteDatabase db = this.getReadableDatabase();
				
				Cursor cursor = db.query(Registration, new String[] {ID_NO}, ID_NO + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
				if (cursor != null)
					cursor.moveToFirst();
				
				RegistrationText txt = new RegistrationText(Integer.parseInt(cursor.getString(0)), 
									      cursor.getString(1), 
									      cursor.getString(2),
										  cursor.getString(3));
				return txt;
			}
			
			//Getting all Text
			public List<RegistrationText> getAllText(int ID_NO){
				List<RegistrationText> listText = new ArrayList<RegistrationText>();
				//Select All Query
				//String selectQuery = "Select * FROM " + TEXT;
				String menuQuery = "Select * from " + Registration + " WHERE id=" + ID_NO;
				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(menuQuery, null);
				
				//looping through all rows and adding to list
				if (cursor.moveToFirst()){
					do{
						RegistrationText txt = new RegistrationText();
						txt.setId(Integer.parseInt(cursor.getString(0)));
						txt.setTitle(cursor.getString(1));
						txt.setDescription(cursor.getString(2));
						txt.setLink(cursor.getString(3));
						listText.add(txt);
					} while (cursor.moveToNext());
					
				}
				return listText;
			}
			
			// Getting contacts Count
			public int getTextCount() {
				String countQuery = "SELECT  * FROM " + Registration;
		        SQLiteDatabase db = this.getReadableDatabase();
		        Cursor cursor = db.rawQuery(countQuery, null);
		        //cursor.close();
		 
		        // return count
		        return cursor.getCount();
			}
			
			//updating Text
			public int updateText(RegistrationText text) {
			    SQLiteDatabase db = this.getWritableDatabase();
			    
		        ContentValues values = new ContentValues();
		        values.put(ID_NO, text.getId());
		        values.put(TITLE, text.getTitle());
		        values.put(DESCRIPTION, text.getDescription());
		        values.put(LINK, text.getLink());
		 
		        // updating row
		        return db.update(Registration, values, ID_NO + " = ?",
		                new String[] { String.valueOf(text.getId()) });
			}
			
			//Deleting single roll
			public void deleteText(RegistrationText text){
				SQLiteDatabase db = this.getWritableDatabase();
				db.delete(Registration, ID_NO + " = ?", new String[] { String.valueOf(text.getId())});
				db.close();
			}

}
