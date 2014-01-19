package xmltosqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class XmlDBHelper 
{
	private DatabaseHandler xmlDBHelper;
	private SQLiteDatabase db;
	
	private final Context xmlContext;
	
	public XmlDBHelper (Context context)
	{
		this.xmlContext = context;
	}
	
	public XmlDBHelper open() throws SQLException
	{
		xmlDBHelper = new DatabaseHandler(xmlContext);
		db = xmlDBHelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		xmlDBHelper.close();
	}
	
	public void deleteAllRowsFromTextXMLTable()
	{
		db.delete(DatabaseHandler.TEXT, null, null);
	}
	
	
}
