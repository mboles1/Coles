package coles.app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class RoomDBHelper 
{

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ROOM = "Room_Num";
	public static final String COLUMN_XPER = "X_Percent";
	public static final String COLUMN_YPER = "Y_Percent";
	public static final String COLUMN_BUILDING = "Building";
	public static final String COLUMN_FNUM = "FNUM";
	
	public static final String COLUMN_ABBREV = "abbrev";
	public static final String COLUMN_BNAME = "name";
	public static final String COLUMN_BNUM = "bNum";
	public static final String COLUMN_LAT = "lat";
	public static final String COLUMN_LONG = "long";
	
	public static final String COLUMN_LASTNAME = "Last_Name";
	public static final String COLUMN_FIRSTNAME = "First_Name";
	public static final String COLUMN_MIDDLE = "Middle_Name";
	public static final String COLUMN_TITLE = "Title";
	public static final String COLUMN_TITLE2 = "Title_2";
	public static final String COLUMN_DEPARTMENT = "Department";
	public static final String COLUMN_OFFICE = "Office_Location";
	public static final String COLUMN_PHONE = "Phone_Number";
	public static final String COLUMN_EMAIL = "Email_Address";
	public static final String COLUMN_BIO = "Bio";
	public static final String COLUMN_WEB = "Website";
	
	public static final String COLUMN_TIMESTAMP = "TimeStamp";
	
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	private static final String DATABASE_NAME = "ksu_database";
	private static final String BUILDING_TABLE = "buildings";
	private static final String ROOM_TABLE = "bb_rooms";
	private static final String DIRECTORY_TABLE = "directory";
	private static final String TIMESTAMP_TABLE = "timestamp";
	private static final int DATABASE_VERSION = 2;
	
	private final Context mContext;
	
	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			
			db.execSQL("CREATE TABLE bb_rooms (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Room_Num TEXT, X_Percent DOUBLE, Y_Percent DOUBLE, Building TEXT, FNUM INTEGER);");
			db.execSQL("CREATE TABLE science  (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Room_Num TEXT, X_Percent TEXT, Y_Percent TEXT, Building TEXT, FNUM TEXT);");
			db.execSQL("CREATE TABLE buildings (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, abbrev TEXT, name TEXT, bNum TEXT, lat DOUBLE, long DOUBLE);");
			
			db.execSQL("CREATE TABLE directory (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , Last_Name TEXT, First_Name TEXT, Middle_Name TEXT, Title TEXT, Title_2 " +
					"TEXT , Department TEXT , Office_Location TEXT , Phone_Number TEXT , Email_Address TEXT , Bio TEXT , Website TEXT );");
			
			db.execSQL("CREATE TABLE timestamp (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TimeStamp TEXT);");
			
			db.beginTransaction();
			try
			{
			
				//1353951821:1363284485:1353145641:1363892243:1358966604
				
				db.execSQL("INSERT INTO timestamp (TimeStamp) VALUES ('0');");
																										  //num, x, y, building, floor
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('401','0.07','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('402','0.11','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('403','0.14','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('404','0.17','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('405','0.22','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('406','0.265','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('406','0.265','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('407','0.305','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('408','0.339','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('409','0.375','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('410','0.409','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('411','0.445','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('412','0.487','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('413','0.582','0.146','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('414','0.52','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('415','0.474','0.146','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('416','0.622','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('417','0.66','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('418','0.698','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('419','0.733','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('420','0.777','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('421','0.823','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('422','0.856','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('423','0.891','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('424','0.927','0.125','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('426','0.07','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('427','0.11','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('428','0.14','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('429','0.17','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('430','0.229','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('431','0.267','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('432','0.305','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('433','0.339','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('434','0.375','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('435','0.409','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('436','0.445','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('437A','0.482','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('437C','0.516','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('438','0.554','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('439','0.59','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('440','0.622','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('441','0.66','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('442','0.698','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('444','0.777','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('445','0.823','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('446','0.856','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('447','0.891','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('448','0.927','0.21','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('451','0.304','0.41','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('452','0.334','0.41','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('453','0.372','0.41','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('454','0.41','0.41','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('455','0.447','0.41','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('456','0.552','0.41','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('457','0.587','0.41','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('458','0.625','0.41','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('459','0.66','0.41','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('460','0.69','0.41','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('465','0.339','0.5','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('466','0.443','0.5','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('467','0.552','0.5','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('468','0.658','0.5','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('478','0.265','0.65','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('487','0.35','0.75','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('488','0.391','0.75','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('489','0.448','0.75','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('490','0.487','0.75','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('491','0.518','0.75','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('492','0.554','0.75','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('493','0.587','0.75','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('494','0.622','0.75','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('495','0.661','0.75','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('496','0.714','0.73','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('477','0.712','0.65','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('480','0.415','0.664','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('483','0.517','0.664','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('484','0.603','0.664','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('475','0.49','0.597','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('475A','0.628','0.597','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('472','0.8','0.464','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('474','0.8','0.52','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('463','0.229','0.54','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('461','0.184','0.526','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('482','0.1865','0.453','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('301','0.065','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('302','0.106','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('303','0.14','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('304','0.173','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('305','0.218','0.1042','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('306','0.266','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('307','0.301','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('308','0.338','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('309','0.373','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('310','0.413','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('311','0.446','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('312','0.483','0.108','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('313','0.476','0.147','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('314','0.578','0.133','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('315','0.521','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('316','0.598','0.133','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('317','0.622','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('318','0.655','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('319','0.693','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('320','0.731','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('321','0.778','0.106','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('322','0.823','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('323','0.858','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('324','0.892','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('325','0.929','0.125','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('327','0.065','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('328','0.106','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('329','0.14','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('330','0.173','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('331','0.202','0.222','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('332','0.228','0.212','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('334','0.301','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('335','0.338','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('336','0.373','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('337','0.413','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('338','0.447','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('339A','0.482','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('339C','0.517','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('340','0.552','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('341','0.59','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('342','0.622','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('343','0.655','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('344','0.693','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('346','0.77','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('347','0.797','0.222','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('348','0.823','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('349','0.858','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('350','0.892','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('351','0.929','0.21','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('354','0.302','0.408','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('355','0.335','0.408','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('356','0.376','0.408','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('357','0.408','0.408','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('358','0.446','0.408','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('359','0.551','0.408','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('360','0.586','0.408','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('361','0.626','0.408','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('362','0.661','0.408','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('363','0.696','0.408','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('364','0.163','0.454','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('365','0.16','0.527','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('366','0.225','0.543','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('368','0.326','0.495','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('369','0.403','0.495','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('370','0.5','0.495','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('371','0.595','0.495','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('372','0.672','0.495','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('375','0.762','0.458','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('376','0.797','0.468','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('377','0.761','0.52','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('378','0.806','0.527','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('380','0.287','0.693','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('381','0.382','0.693','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('382A','0.468','0.693','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('382','0.533','0.693','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('383','0.612','0.693','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('384','0.707','0.693','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('201','0.084','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('202','0.12','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('203','0.15','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('204','0.17','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('205','0.23','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('206','0.27','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('207','0.306','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('208','0.349','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('209','0.378','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('210','0.409','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('211','0.448','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('212','0.487','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('213','0.522','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('214','0.578','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('215','0.58','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('216','0.596','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('217','0.656','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('218','0.656','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('219','0.69','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('220','0.726','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('221','0.766','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('222','0.813','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('223','0.847','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('224','0.88','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('225','0.915','0.125','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('227','0.085','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('228','0.12','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('229','0.15','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('230','0.17','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('232','0.23','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('234','0.31','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('235','0.345','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('236','0.38','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('237','0.415','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('238','0.45','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('239A','0.483','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('239C','0.515','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('240','0.553','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('241','0.587','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('242','0.622','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('243','0.653','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('244','0.692','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('246','0.762','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('247','0.785','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('248','0.812','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('249','0.845','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('250','0.881','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('251','0.914','0.205','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('255','0.327','0.407','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('256','0.388','0.403','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('257','0.443','0.4','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('258','0.5','0.38','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('259','0.556','0.4','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('260','0.605','0.4','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('261','0.652','0.4','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('262','0.684','0.4','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('268','0.32','0.51','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('269','0.427','0.51','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('270','0.522','0.51','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('271A','0.554','0.528','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('271B','0.556','0.486','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('271C','0.58','0.48','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('271D','0.645','0.488','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('271E','0.606','0.524','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('272A','0.637','0.528','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('272B','0.638','0.486','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('272C','0.663','0.474','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('272D','0.688','0.486','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('272E','0.688','0.526','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('281','0.32','0.62','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('284','0.32','0.68','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('288','0.32','0.733','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('283','0.378','0.61','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('286','0.378','0.653','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('287','0.378','0.693','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('290','0.378','0.74','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('291','0.43','0.655','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('291B','0.438','0.737','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('292','0.526','0.685','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('293','0.658','0.685','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('277','0.752','0.5','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('275','0.752','0.468','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('276','0.771','0.45','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('278','0.811','0.5','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('266','0.235','0.53','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('265','0.194','0.52','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('264','0.19','0.443','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('105','0.227','0.27','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('108','0.347','0.27','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('109','0.425','0.27','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('111','0.511','0.27','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('112','0.546','0.27','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('113','0.532','0.308','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('114','0.611','0.27','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('117','0.73','0.27','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('123','0.27','0.347','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('124','0.27','0.395','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('127','0.336','0.485','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('129','0.351','0.52','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('141','0.611','0.437','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('143','0.606','0.487','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('144','0.61','0.516','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('150','0.348','0.616','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('151','0.476','0.635','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('152','0.61','0.619','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('153','0.703','0.597','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('154','0.703','0.597','Burruss','1');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('E4','0.696','0.346','Burruss','4');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('E3','0.696','0.346','Burruss','3');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('E2','0.696','0.346','Burruss','2');");
				db.execSQL("INSERT  INTO bb_rooms (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES('E1','0.696','0.346','Burruss','1');");

			
			db.setTransactionSuccessful();
			
			}
			catch(Exception e)
			{
				db.endTransaction();
			
			}
			
			db.endTransaction();
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			
		}
	}
	
	public RoomDBHelper (Context context)
	{
		this.mContext = context;
	}
	
	public RoomDBHelper open() throws SQLException
	{
		dbHelper = new DatabaseHelper(mContext);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		dbHelper.close();
	}	
	
	public Cursor fetchTimeStamp()
	{
		return db.query(TIMESTAMP_TABLE, new String[] {COLUMN_TIMESTAMP}, null, null, null, null, null);
	}
	
	public boolean updateTimeStamp(String timestamp)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_TIMESTAMP, timestamp);
		return db.update(TIMESTAMP_TABLE, values, COLUMN_ID + "=1", null) > 0;
	}
	
	public Cursor fetchQR(String QR)
	{
		return db.query(ROOM_TABLE, new String[] {COLUMN_ID, COLUMN_ROOM, COLUMN_XPER, COLUMN_YPER, COLUMN_BUILDING, COLUMN_FNUM}, COLUMN_ROOM + "=\"" + QR + "\"", null, null, null, null);
	}
	
	public Cursor fetchRoom(String table, String selection)
	{
		return db.query(table, new String[] {COLUMN_ID, COLUMN_ROOM, COLUMN_XPER, COLUMN_YPER, COLUMN_BUILDING, COLUMN_FNUM}, COLUMN_ROOM + "=\"" + selection + "\"", null, null, null, null);
	}
	
	public Cursor fetchBuilding(String selection)
	{
		return db.query(BUILDING_TABLE, new String[] {COLUMN_ID, COLUMN_ABBREV, COLUMN_BNAME, COLUMN_BNUM, COLUMN_LAT, COLUMN_LONG}, COLUMN_BNAME + "=\"" + selection + "\"", null, null, null, null);
	}
	
	public Cursor fetchBuildingByID(String selection)
	{
		return db.query(BUILDING_TABLE, new String[] {COLUMN_ID, COLUMN_ABBREV, COLUMN_BNAME, COLUMN_BNUM, COLUMN_LAT, COLUMN_LONG}, COLUMN_BNUM + "=\"" + selection + "\"", null, null, null, null);
	}
	
	public Cursor fetchAllBuilding()
	{
		return db.query(BUILDING_TABLE, new String[] {COLUMN_ID, COLUMN_ABBREV, COLUMN_BNAME, COLUMN_BNUM, COLUMN_LAT, COLUMN_LONG}, null, null, null, null, COLUMN_BNAME);
	}
	
	public Cursor pullFullDirectory() 
	{
		return db.query(DIRECTORY_TABLE, new String[] {COLUMN_ID , COLUMN_LASTNAME,
				COLUMN_FIRSTNAME, COLUMN_MIDDLE, COLUMN_PHONE, COLUMN_TITLE, COLUMN_TITLE2, COLUMN_DEPARTMENT, COLUMN_OFFICE, COLUMN_EMAIL, COLUMN_BIO, COLUMN_WEB}, null, null, null, null, COLUMN_LASTNAME+" ASC, "+COLUMN_FIRSTNAME+" ASC");
	}

	public Cursor fetchDirectoryEntry(String selection)
	{
		return db.query(DIRECTORY_TABLE, new String[] {COLUMN_ID , COLUMN_LASTNAME,
				COLUMN_FIRSTNAME, COLUMN_MIDDLE, COLUMN_PHONE, COLUMN_TITLE, COLUMN_TITLE2, COLUMN_DEPARTMENT, COLUMN_OFFICE, COLUMN_EMAIL, COLUMN_BIO, COLUMN_WEB}, COLUMN_LASTNAME + " LIKE '" + selection + "%' OR "
		+ COLUMN_FIRSTNAME + " LIKE '" + selection + "%'", null, null, null, COLUMN_LASTNAME+" ASC, "+COLUMN_FIRSTNAME+" ASC");
	}
	
	public Cursor fetchSpecificRoom(String selection)
	{
		return db.query(ROOM_TABLE, new String[] {COLUMN_ID, COLUMN_ROOM, COLUMN_XPER, COLUMN_YPER, COLUMN_BUILDING, COLUMN_FNUM}, COLUMN_ROOM + "=\"" + selection + "\"", null, null, null, null);
	}
	
	public Cursor fetchFuzzyBuliding(String selection)
	{
		return db.query(BUILDING_TABLE, new String[]{COLUMN_ID,COLUMN_ABBREV, COLUMN_BNAME, COLUMN_BNUM, COLUMN_LAT, COLUMN_LONG}, COLUMN_BNAME + "=\"" + selection + "\"", null, null, null, null);
	}
	
	/**
	 * Both methods clear out these tables
	 */
	public void deleteDirectory()
	{
		db.delete(DIRECTORY_TABLE, null, null);
	}
	
	public void deleteBuildingTable()
	{
		db.delete(BUILDING_TABLE, null, null);
	}
	
	public Cursor checkIfDirectoryEmpty()
	{
		return db.rawQuery("SELECT COUNT(*) FROM directory", null);
	}
	
	public Cursor checkIfBuildingEmpty()
	{
		return db.rawQuery("SELECT COUNT(*) FROM buildings", null);
	}
	
	public void createRoomTable(String room, String xper, String yper, String building, String floor)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_ROOM, room);
		contentValues.put(COLUMN_XPER, xper);
		contentValues.put(COLUMN_YPER, yper);
		contentValues.put(COLUMN_BUILDING, building);
		contentValues.put(COLUMN_FNUM, floor);
		open();
		db.insertOrThrow(ROOM_TABLE, null, contentValues);	
		db.close();
	}

	public void bulkInsertRoom(InputStream input) throws IOException
	{
		BufferedInputStream stream = new BufferedInputStream(input);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		db.beginTransaction();
		try
		{
			String sql = "INSERT INTO " + ROOM_TABLE + " (Room_Num, X_Percent, Y_Percent, Building, FNUM) VALUES (?, ?, ?, ?, ?)";
			
			SQLiteStatement insert = db.compileStatement(sql);
			String line = "";
			String[] values;
			while((line = reader.readLine()) != null)
			{
				values = line.split("[,]");
				
				insert.bindString(1, values[0]);
				insert.bindString(2, values[1]);
				insert.bindString(3, values[2]);
				insert.bindString(4, values[3]);
				insert.bindString(5, values[4]);
				
				insert.execute();
			}
			db.setTransactionSuccessful();
		}
		finally
		{
			db.endTransaction();
		}
	}
	
	public void bulkInsertDirectory(InputStream input) throws IOException
	{
		BufferedInputStream stream = new BufferedInputStream(input);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		db.beginTransaction();
		try
		{
			String sql = "INSERT INTO " + DIRECTORY_TABLE + " (Last_Name, First_Name, Middle_Name, Title, Title_2, Department,"
					+ " Office_Location, Phone_Number, Email_Address) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			SQLiteStatement insert = db.compileStatement(sql);
			String line = "";
			String[] values;
			while((line = reader.readLine()) != null)
			{
				values = line.split("[,]");
				
				insert.bindString(1, values[0]);
				insert.bindString(2, values[1]);
				insert.bindString(3, values[2]);
				insert.bindString(4, values[3]);
				insert.bindString(5, values[4]);
				insert.bindString(6, values[5]);
				insert.bindString(7, values[6]);
				insert.bindString(8, values[7]);
				insert.bindString(9, values[8]);
				
				insert.execute();
			}
			db.setTransactionSuccessful();
		}
		finally
		{
			db.endTransaction();
		}
		
	}
	
	public void bulkInsertBuilding(InputStream input) throws IOException
	{
		BufferedInputStream stream = new BufferedInputStream(input);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		db.beginTransaction();
		try
		{
			String sql = "INSERT INTO " + BUILDING_TABLE + " (abbrev, name, bNum, lat, long) VALUES(?, ?, ?, ?, ?)";
			SQLiteStatement insert = db.compileStatement(sql);
			
			String line = "";
			String[] values;
			
			while((line = reader.readLine()) != null)
			{
				values = line.split("[,]");
				
				insert.bindString(1, values[0]);
				insert.bindString(2, values[1]);
				insert.bindString(3, values[2]);
				insert.bindString(4, values[3]);
				insert.bindString(5, values[4]);
				
				insert.execute();
			}
			
			db.setTransactionSuccessful();
		}
		finally
		{
			db.endTransaction();
		}
	}
}
