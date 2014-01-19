package xmltosqlite;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{

	//variables
	//database version
	private final static int DATABASE_VERSION = 1;

	//database name
	private final static String DATABASE_NAME = "TEXT_TABLE";

	//table name
	public static String TEXT = "text";

	//table column names
	private static final String ID_NO = "ID";
	private static final String MENU_NO = "Menu";
	private static final String ITEM_NO = "Item";
	private static final String HEADING = "Heading";
	private static final String BODY = "Body";
	private static final String LINK = "Link";

	
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//Creating Table
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TEXT_TABLE = "CREATE TABLE " + TEXT + "(" 
				+ ID_NO + " INTEGER PRIMARY KEY,"
				+ MENU_NO + " TEXT not null," 
				+ ITEM_NO + " TEXT not null,"
				+ HEADING + " TEXT not null,"
				+ BODY + " TEXT not null,"
				+ LINK + " TEXT not null"
				+ ")";
		db.execSQL(CREATE_TEXT_TABLE);
		Log.d("DB", "db created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("OnUpgrade", "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		String dropQuery = "drop table if exists ";
		db.execSQL(dropQuery + DATABASE_NAME);
		onCreate(db);
	}

	public void addText(Text2 txt){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ID_NO, txt.getId()); //Text item id
		values.put(MENU_NO, txt.getMenu_no());
		values.put(ITEM_NO, txt.getItem_order());
		values.put(HEADING, txt.getHeading());
		values.put(BODY, txt.getBody());
		values.put(LINK, txt.getLink());
		db.insert(TEXT,null,values);
		db.close();
		Log.d("DB","things added to db");
	}
	
	public void bulkInsertText(InputStream stream) throws Exception, IOException, SAXException, ParserConfigurationException
	{
		ArrayList<Text2> list = new ArrayList<Text2>();
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();
		try
		{
			BufferedInputStream buffered = new BufferedInputStream(stream);
			
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			
			TextHandler dataHandler = new TextHandler();
			xmlReader.setContentHandler(dataHandler);
			xmlReader.parse(new InputSource(buffered));
			
			list = dataHandler.getData();
			
			String sql = "INSERT INTO " + TEXT + " (ID, Menu, Item, Heading, Body, Link) VALUES (?, ?, ?, ?, ?, ?)";
			SQLiteStatement insert = db.compileStatement(sql);
			
			for(Text2 text2: list)
			{
				insert.bindLong(1, text2.getId());
				insert.bindLong(2, text2.getMenu_no());
				insert.bindLong(3, text2.getItem_order());
				insert.bindString(4, text2.getHeading());
				insert.bindString(5, text2.getBody());
				insert.bindString(6, text2.getLink());
				
				insert.execute();
			}
			db.setTransactionSuccessful();
		}
		finally
		{
			db.endTransaction();
		}
		
	}

	public Text2 getText(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Text2 txt = new Text2();
		Cursor cursor = db.query(TEXT, new String[] {ID_NO}, ID_NO + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
		if (cursor != null)
		{
			if(cursor.moveToFirst())
			{
				txt = new Text2(Integer.parseInt(cursor.getString(0)), 
						Integer.parseInt(cursor.getString(1)), 
						Integer.parseInt(cursor.getString(2)),
						cursor.getString(3),
						cursor.getString(4),
						cursor.getString(5));
			}
		}
		return txt;

	}

	//Getting all Text
	public List<Text2> getAllText(int menuNum){
		List<Text2> listText = new ArrayList<Text2>();
		//Select All Query
		//String selectQuery = "Select * FROM " + TEXT;
		String menuQuery = "Select * from " + TEXT + " WHERE Menu=" + menuNum;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(menuQuery, null);

		//looping through all rows and adding to list
		if (cursor.moveToFirst()){
			do{
				Text2 txt = new Text2();
				txt.setId(Integer.parseInt(cursor.getString(0)));
				txt.setMenu_no(Integer.parseInt(cursor.getString(1)));
				txt.setItem_order(Integer.parseInt(cursor.getString(2)));
				txt.setHeading(cursor.getString(3));
				txt.setBody(cursor.getString(4));
				txt.setLink(cursor.getString(5));
				listText.add(txt);
			} while (cursor.moveToNext());

		}

		return listText;
	}

	// Getting contacts Count
	public int getTextCount() {
		String countQuery = "SELECT  * FROM " + TEXT;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	//updating Text
	public int updateText(Text2 text) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ID_NO, text.getId());
		values.put(MENU_NO, text.getMenu_no());

		// updating row
		return db.update(TEXT, values, ID_NO + " = ?",
				new String[] { String.valueOf(text.getId()) });
	}

	//Deleting single roll
	public void deleteText(Text2 text){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TEXT, ID_NO + " = ?", new String[] { String.valueOf(text.getId())});
		db.close();
	}

	@Override
	public void close(){
		super.close();
	}

}
