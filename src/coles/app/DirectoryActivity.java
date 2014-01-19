package coles.app;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
/**
 * 
 * @author Matthew Boles
 *
 */

public class DirectoryActivity extends ListActivity 
{

	private static final int ACTIVITY_EDIT = 0;
	private Cursor myCursor;
	private EditText search;
	private String test;

	private RoomDBHelper dbHelp;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directory);

		dbHelp = new RoomDBHelper(this);

		dbHelp.open();

		fillData();

		search = (EditText)findViewById(R.id.searchBar);

		search.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void afterTextChanged(Editable arg0) 
			{
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) 
			{
				dbHelp.open();
				fillData();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) 
			{
				//dbHelp.open();
				test = search.getText().toString();
				filterData(test);
			}

		});

		dbHelp.close();
	}


	private void fillData()
	{
		// Get all of the rows from the database and create the item list
		myCursor = dbHelp.pullFullDirectory();
		startManagingCursor(myCursor);

		// Create an array to specify the fields we want to display in the list
		String[] FROM = new String[]{RoomDBHelper.COLUMN_LASTNAME, RoomDBHelper.COLUMN_FIRSTNAME};

		// and an array of the fields we want to bind those fields to
		int[] TO = new int[]{R.id.last_name, R.id.first_name};

		// Now create a simple cursor adapter and set it to display
		SimpleCursorAdapter notes = 
				new SimpleCursorAdapter(this, R.layout.directory_item, myCursor, FROM, TO);

		setListAdapter(notes);


	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		Cursor c = myCursor;
		c.moveToPosition(position);

		Bundle b = new Bundle();


		b.putString("first", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_FIRSTNAME)));

		Intent i = new Intent (DirectoryActivity.this, ViewFullDirectoryEntry.class);
		//i.putExtra(DBHelper.COLUMN_LASTNAME, id);

		i.putExtra("last", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_LASTNAME)));
		i.putExtra("middle", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_MIDDLE)));
		i.putExtra("department", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_DEPARTMENT)));
		i.putExtra("email", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_EMAIL)));
		i.putExtra("office", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_OFFICE)));
		i.putExtra("phone", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_PHONE)));
		i.putExtra("t1", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_TITLE)));
		i.putExtra("t2", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_TITLE2)));

		i.putExtras(b);

		//i.putExtra(DBHelper.COLUMN_FIRSTNAME, c.getString(c.getColumnIndexOrThrow(DBHelper.COLUMN_FIRSTNAME)));
		startActivityForResult(i, ACTIVITY_EDIT);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);

		switch(requestCode)
		{
		case ACTIVITY_EDIT:
			dbHelp.open();
			search.setText("");
			fillData();
			break;
		}
	}


	private void filterData(String s)
	{
		// Get all of the rows from the database and create the item list
		myCursor = dbHelp.fetchDirectoryEntry(s);
		//myCursor = dbHelp.fetchDescription("new table");
		startManagingCursor(myCursor);

		// Create an array to specify the fields we want to display in the list (only TITLE)
		String[] FROM = new String[]{RoomDBHelper.COLUMN_LASTNAME, RoomDBHelper.COLUMN_FIRSTNAME};

		// and an array of the fields we want to bind those fields to (in this case just text1)
		int[] TO = new int[]{R.id.last_name, R.id.first_name};

		// Now create a simple cursor adapter and set it to display
		SimpleCursorAdapter notes = 
				new SimpleCursorAdapter(this, R.layout.directory_item, myCursor, FROM, TO);

		setListAdapter(notes);

		notes.getFilter().filter(s);

		notes.notifyDataSetChanged();
	}

}