package coles.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 
 * @author Matthew Boles
 *
 */

public class ViewFullDirectoryEntry extends Activity
{
	private TextView tvFirstName, tvTitle1, tvTitle2, tvDepartment, tvOffice, tvPhone, tvEmail;
	private String fName, lName, mName, title1, title2, department, office, phone;

	private TableRow title2Row, title1Row, departmentRow, officeRow, phoneRow;

	private RoomDBHelper dbHelper;
	private Cursor myCursor;

	@Override
	public void onCreate(Bundle SavedInstanceState)
	{
		super.onCreate(SavedInstanceState);
		setContentView(R.layout.directory_info_full);

		dbHelper = new RoomDBHelper(this);
		dbHelper.open();

		populateFields();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.directory_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.directory_call:
			try 
			{
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				//This works so you can use it for pulling the phone number from the database
				//String phoneTest = "1234567890";
				callIntent.setData(Uri.parse("tel:" + phone));
				startActivity(callIntent);
			} 
			catch (ActivityNotFoundException e)
			{
				Log.e("directory call", "Call failed", e);
			}
			return true;

		case R.id.directory_walktoroom:
			String roomAbbrev = office.substring(0,2);
			String roomNum = office.substring(3,6);

			Intent intent = new Intent(ViewFullDirectoryEntry.this, FloorMapActivity.class);



			if(roomAbbrev.equals("BB"))
			{

				myCursor = dbHelper.fetchRoom("bb_rooms", roomNum);

				Cursor c = myCursor;

				if(c != null)
				{
					if(c.moveToFirst())
					{
						Double xPer = c.getDouble(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_XPER));
						Double yPer = c.getDouble(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_YPER));

						intent.putExtra("xPer1", xPer);
						intent.putExtra("yPer1", yPer);
						intent.putExtra("room#", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_ROOM))); 
						intent.putExtra("floor", c.getInt(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_FNUM)));
						intent.putExtra("building", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_BUILDING)));

						dbHelper.close();

						finish();

						startActivity(intent);
					}
				}

				Log.e("Testing Substring", roomAbbrev + " " + roomNum);
			}
			else
			{
				popUpAlert();
				dbHelper.close();
			}
			return true;
		default:
			return true;
		}
	}

	private void populateFields()
	{
		//Setting up the views
		tvFirstName = (TextView)findViewById(R.id.first);
		tvTitle1 = (TextView)findViewById(R.id.title1);
		tvTitle2 = (TextView)findViewById(R.id.title2);
		tvDepartment = (TextView)findViewById(R.id.depart);
		tvOffice = (TextView)findViewById(R.id.office);
		tvPhone = (TextView)findViewById(R.id.phone);
		tvEmail = (TextView)findViewById(R.id.email);

		title2Row = (TableRow)findViewById(R.id.title2_row);
		title1Row = (TableRow)findViewById(R.id.title1_row);
		departmentRow = (TableRow)findViewById(R.id.department_row);
		officeRow = (TableRow)findViewById(R.id.office_row);
		phoneRow = (TableRow)findViewById(R.id.phone_row);

		//Getting the extras from the intent
		//Bundle b = getIntent().getExtras();
		fName = getIntent().getExtras().getString("first");
		lName = getIntent().getExtras().getString("last");
		mName = getIntent().getExtras().getString("middle");
		title1 = getIntent().getExtras().getString("t1");
		title2 = getIntent().getExtras().getString("t2");
		department = getIntent().getExtras().getString("department");
		
		if(department.contains(";"))
		{
			department = department.replace(";", ",");
		}
		
		office = getIntent().getExtras().getString("office");
		phone = getIntent().getExtras().getString("phone");

		//SetTitle
		setTitle("Coles College Directory");

		//Combining extras and the views
		tvFirstName.setText(lName + ", " + fName + " " + mName);
		tvTitle1.setText(title1);
		tvTitle2.setText(title2);
		tvDepartment.setText(department);
		tvOffice.setText("Office #: " + office);
		tvPhone.setText(phone);
		
		tvEmail.setVisibility(View.GONE);

		/*
		 * Figure out a way to check if fields are filled in or not 
		 * and depending on if they are filled in or not, set the visibility accordingly
		 */

		if(tvTitle1.getText().toString().equals(""))
		{
			title1Row.setVisibility(View.GONE);
		}

		if(tvTitle2.getText().toString().equals(""))
		{
			title2Row.setVisibility(View.GONE);
		}

		if(tvDepartment.getText().toString().equals(""))
		{
			departmentRow.setVisibility(View.GONE);
		}

		if(tvOffice.getText().toString().length()==10)
		{
			officeRow.setVisibility(View.GONE);
		}

		if(tvPhone.getText().toString().equals(""))
		{
			phoneRow.setVisibility(View.GONE);
		}

		tvPhone.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				try 
				{
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + phone));
					startActivity(callIntent);
				} 
				catch (ActivityNotFoundException e)
				{
					Log.e("directory call", "Call failed", e);
				}
			}

		});

		tvOffice.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {

				String roomAbbrev = office.substring(0,2);
				String roomNum = office.substring(3,6);

				Intent intent = new Intent(ViewFullDirectoryEntry.this, FloorMapActivity.class);



				if(roomAbbrev.equals("BB"))
				{

					myCursor = dbHelper.fetchRoom("bb_rooms", roomNum);

					Cursor c = myCursor;

					if(c != null)
					{
						if(c.moveToFirst())
						{
							Double xPer = c.getDouble(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_XPER));
							Double yPer = c.getDouble(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_YPER));

							intent.putExtra("xPer1", xPer);
							intent.putExtra("yPer1", yPer);
							intent.putExtra("room#", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_ROOM))); 
							intent.putExtra("floor", c.getInt(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_FNUM)));
							intent.putExtra("building", c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_BUILDING)));

							dbHelper.close();

							finish();

							startActivity(intent);
						}
					}

					Log.e("Testing Substring", roomAbbrev + " " + roomNum);
				}
				else
				{
					popUpAlert();
					dbHelper.close();
				}


			}

		});
	}

	private void popUpAlert()
	{
		AlertDialog.Builder officeNotAvailableBuild = new AlertDialog.Builder(ViewFullDirectoryEntry.this);
		officeNotAvailableBuild.setCancelable(false);
		officeNotAvailableBuild.setMessage("Directions To Office Are Not Available.");
		officeNotAvailableBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});

		AlertDialog officeNotAvailable = officeNotAvailableBuild.create();
		officeNotAvailable.show();
	}

}





