package coles.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class WalkToBldgActivity extends Activity implements LocationListener
{
	private Button startSearch;
	private Button viewCampusMap;
	private Spinner buildingSpin;
	private String building_name;
	private String etBuildingAbbrev;
	private String etBuildingNumber;

	private int position;
	
	private AutoCompleteTextView buildingAbbrevField;

	private double latitude_dest, longitude_dest;
	private double user_lat, user_long;

	private static RoomDBHelper dbHelp;
	private Cursor myCursor;

	private ArrayList<String> buildingArrayList;

	private LocationManager locationManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{    
		super.onCreate(savedInstanceState);    
		setContentView(R.layout.walktobldg);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		dbHelp = new RoomDBHelper(this);
		dbHelp.open();

		InitViews();
	}

	private void WalkToBuildingLocationServices()
	{
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if(location != null)
		{
			user_lat = (location.getLatitude());
			user_long = (location.getLongitude());
		}


		startSearch = (Button)findViewById(R.id.search_button_building);
		startSearch.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				myCursor = dbHelp.fetchBuilding(building_name);

				Cursor c = myCursor;

				if(c != null)
				{
					if(c.moveToFirst())
					{
						latitude_dest = c.getDouble(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_LAT));
						longitude_dest = c.getDouble(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_LONG));

						Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, 
								Uri.parse("http://maps.google.com/maps?saddr="+user_lat+","+user_long
										+"&daddr="+latitude_dest+","+longitude_dest)); 

						dbHelp.close();

						finish();

						startActivity(mapIntent); 
					}
				}
			}

		});
	}

	private void InitViews() 
	{
		WalkToBuildingLocationServices();

		buildingSpin = (Spinner)findViewById(R.id.building_spinner);

		viewCampusMap = (Button)findViewById(R.id.view_campus_map);
		viewCampusMap.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				try
				{
					String pdfPath = "http://docs.google.com/gview?embedded=true&url=http://www.kennesaw.edu/admissions/pdfs/ksu_2d_map.pdf";
					Uri url = Uri.parse(pdfPath);
					Intent intent = new Intent(Intent.ACTION_VIEW, url);

					finish();
					startActivity(intent);

				}
				catch(Exception error)
				{
					Log.e("URI Campus Map", error.getMessage() + "");
				}

			}

		});

		buildingAbbrevField = (AutoCompleteTextView)findViewById(R.id.building_abbrev);
		buildingAbbrevField.setOnEditorActionListener(new OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) 
			{
				if(actionId == EditorInfo.IME_ACTION_DONE)
				{
					InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return false;
			}

		});

		buildingAbbrevField.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				buildingAbbrevField.setText("");
			}

		});

		buildingAbbrevField.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void afterTextChanged(Editable s) 
			{
				String searchFieldName = buildingAbbrevField.getText().toString();
				int i = buildingArrayList.indexOf(searchFieldName);
				buildingSpin.setSelection(i);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) 
			{

			}

		});
		buildingAbbrevField.setThreshold(2);

		myCursor = dbHelp.fetchAllBuilding();
		startManagingCursor(myCursor);

		Cursor c = myCursor;

		buildingArrayList = new ArrayList<String>();

		if(c!=null)
		{
			/**
			 * Have to do a while loop here because Cursor.moveToNext() returns true if next record is found.
			 * If statement will only execute once.
			 * Building Name Column of records are added to arrayList and that arrayList is used for the adapter.
			 */
			while(c.moveToNext())
			{
				buildingArrayList.add(c.getString(c.getColumnIndexOrThrow(RoomDBHelper.COLUMN_BNAME)));
			}
		}


		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, buildingArrayList);
		buildingAbbrevField.setAdapter(arrayAdapter);

		String[] FROM = new String[]{RoomDBHelper.COLUMN_BNAME, RoomDBHelper.COLUMN_ABBREV, RoomDBHelper.COLUMN_BNUM};
		int[] TO = new int[]{android.R.id.text1};
		SimpleCursorAdapter spinAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, myCursor, FROM, TO);
		spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		buildingSpin.setAdapter(spinAdapter);
		String Burruss = "Burruss Building";
		position = arrayAdapter.getPosition(Burruss);
		buildingSpin.setSelection(position);

		buildingSpin.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id)
			{
				parent.getContext();

				Cursor spinC = (Cursor)buildingSpin.getSelectedItem();
				building_name = spinC.getString(spinC.getColumnIndexOrThrow(RoomDBHelper.COLUMN_BNAME));

				etBuildingAbbrev = spinC.getString(spinC.getColumnIndexOrThrow(RoomDBHelper.COLUMN_ABBREV));
				etBuildingNumber = spinC.getString(spinC.getColumnIndexOrThrow(RoomDBHelper.COLUMN_BNUM));

				if(etBuildingAbbrev.equals(""))
				{
					buildingAbbrevField.setText("Building # " + etBuildingNumber);
				}
				else
				{
					buildingAbbrevField.setText(etBuildingAbbrev + " " + "Building # " + etBuildingNumber);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1, this);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) 
	{
		user_lat = (location.getLatitude());
		user_long = (location.getLongitude());
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}
}
