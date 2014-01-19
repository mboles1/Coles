package coles.app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import xmltosqlite.DatabaseHandler;
import xmltosqlite.XmlDBHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class ColesActivity extends Activity 
{
	private static final int waitTime = 1500;
	private static RoomDBHelper dbHelp;
	private XmlDBHelper xmlDBHelp;
	private Cursor myCursor;

	private boolean isConnected;

	/**
	 * Written by Matthew Boles/
	 * 
	 *
	/**
	 * Let the splash page do the loading of the databases.
	 * Don't do the handler in this case because it was finishing before the database was able to 
	 * load completely which is will crash the system.
	 * Also seems like you can't have null values for INT/DOUBLE when reading from .csv file into 
	 * database because I was getting an indexOutOfBound at the points where numbers were supposed to be
	 * in the .csv file but nothing was there.  After putting some values in, everything seems to work out.
	 * 
	 */

	private String getTimeStamp() throws Exception
	{
		URL timestamp;
		BufferedInputStream bStream;
		BufferedReader reader;

		timestamp = new URL ("http://130.218.51.52/ws/get_timestamp.php");
		
		HttpURLConnection connection = (HttpURLConnection) timestamp.openConnection();
		if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
		{
			bStream = new BufferedInputStream(timestamp.openStream());
			reader = new BufferedReader(new InputStreamReader(bStream));

			String s;
			String returnedStamp;
			returnedStamp = "";
			while((s = reader.readLine()) != null)
			{
				returnedStamp = s;
			}

			return returnedStamp;
		}
		else
		{
			return "No Connection";
		}
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		isConnected = isNetworkAvailable();

		xmlDBHelp = new XmlDBHelper(this);
		xmlDBHelp.open();

		dbHelp = new RoomDBHelper(this);
		dbHelp.open();

		/**
		 * First check for connection.
		 * If no connection, check for empty directory table.
		 * If empty table, load all tables.
		 * Else proceed.
		 * 
		 * If there is a connection, check time-stamp against table entry.
		 * If time-stamps match, proceed. 
		 * Else empty tables and load tables from URL.
		 */
		if(!isConnected)
		{
			Log.e("Connectivity", "No connection");
			myCursor = dbHelp.checkIfDirectoryEmpty();
			Cursor c = myCursor;
			if(c != null)
			{
				c.moveToFirst();
				if(c.getInt(0) == 0)
				{
					xmlDBHelp.deleteAllRowsFromTextXMLTable();
					dbHelp.deleteDirectory();
					dbHelp.deleteBuildingTable();

					xmlDBHelp.close();


					LoadingOfflineDatabase offlineTask = new LoadingOfflineDatabase();
					offlineTask.execute();
				}
				else
				{
					Handler handler = new Handler();

					Runnable action = new Runnable()
					{
						@Override
						public void run() 
						{
							Intent intent = new Intent(ColesActivity.this, main.class);

							startActivity(intent);
							xmlDBHelp.close();
							dbHelp.close();
							finish();
						}
					};

					handler.postDelayed(action, waitTime);
				}
			}
		}
		else
		{
			Log.e("Connectivity", "Connection present.");

			myCursor = dbHelp.fetchTimeStamp();
			Cursor timeStampCursor = myCursor;

			if(timeStampCursor != null)
			{
				if(timeStampCursor.moveToFirst())
				{
					try
					{
						String dbTimeStamp = timeStampCursor.getString(timeStampCursor.getColumnIndexOrThrow(RoomDBHelper.COLUMN_TIMESTAMP));
						String serverTimeStampString = getTimeStamp();
						Log.e("DatabaseTimeStamp", dbTimeStamp);
						Log.e("Server Timestamp", serverTimeStampString);

						if(dbTimeStamp.equalsIgnoreCase(serverTimeStampString))
						{

							Handler handler = new Handler();

							Runnable action = new Runnable()
							{
								@Override
								public void run() 
								{
									Intent intent = new Intent(ColesActivity.this, main.class);

									startActivity(intent);
									xmlDBHelp.close();
									dbHelp.close();
									finish();
								}
							};

							handler.postDelayed(action, waitTime);

							Log.e("TimeStampCompare", "Server and Entry Match");
						}
						else if(!dbTimeStamp.equalsIgnoreCase(serverTimeStampString))
						{
							/**
							 * Drop and update the database tables
							 */
							Log.e("TimeStampCompare", "Server and Entry Do Not Match");

							xmlDBHelp.deleteAllRowsFromTextXMLTable();
							xmlDBHelp.close();

							dbHelp.deleteDirectory();
							dbHelp.deleteBuildingTable();
							dbHelp.updateTimeStamp(serverTimeStampString);


							try 
							{
								LoadingOnlineDatabase onlineTask = new LoadingOnlineDatabase();
								onlineTask.execute();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}

						}

					}

					catch(Exception e)
					{

					}
				}
			}

			else
			{
				popUpAlert();
			}

		}

	}

	private class loadOnlineXML implements Runnable
	{
		private DatabaseHandler onlineDB;
		private URL url;
		private String urlAddress;

		public loadOnlineXML()
		{
			onlineDB = new DatabaseHandler(getApplicationContext());
			urlAddress = "http://130.218.51.52/ws/get_text_client.php";
		}

		@Override
		public void run() 
		{
			try
			{
				url = new URL(urlAddress);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
				{
					onlineDB.bulkInsertText(getResources().openRawResource(R.raw.client_text));
					Log.e("RESPONSE CODE NOT OK", "Loading offline version");
				}
				else
				{
					onlineDB.bulkInsertText(connection.getInputStream());
				}
				connection.disconnect();
			}
			catch(Exception error)
			{
				Log.e("Bulk XML", error.getMessage() + " ");
			}
			finally
			{
				onlineDB.close();
			}
		}

	}

	private class loadOfflineXML implements Runnable
	{
		private DatabaseHandler offlineDB;

		public loadOfflineXML()
		{
			offlineDB = new DatabaseHandler(getApplicationContext());
		}

		@Override
		public void run() 
		{
			try
			{
				offlineDB.bulkInsertText(getResources().openRawResource(R.raw.client_text));
			}
			catch(Exception error)
			{
				Log.e("Bulk XML", error.getMessage() + " ");
			}
			finally
			{
				offlineDB.close();
			}
		}

	}

	private class BulkInsertDirectoryOnline implements Runnable
	{
		private URL url;
		private String urlAddress;

		public BulkInsertDirectoryOnline()
		{

			urlAddress = "http://130.218.51.52/globalFile/coles_directory.csv";
		}

		@Override
		public void run() 
		{
			try
			{
				url = new URL(urlAddress);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
				{
					dbHelp.bulkInsertDirectory(getResources().openRawResource(R.raw.coles_directory));
				}
				else
				{
					dbHelp.bulkInsertDirectory(connection.getInputStream());
				}
				connection.disconnect();
			}
			catch(IOException error)
			{
				Log.e("Bulk Directory", error.getMessage() + " ");
			}

		}

	}

	private class BulkInsertBuildingOnline implements Runnable
	{
		private URL url;
		private String urlAddress;

		public BulkInsertBuildingOnline()
		{
			urlAddress = "http://130.218.51.52/globalFile/building_table.csv";
		}

		@Override
		public void run() 
		{
			try
			{
				url = new URL(urlAddress);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
				{
					dbHelp.bulkInsertBuilding(getResources().openRawResource(R.raw.building_table));
				}
				else
				{
					dbHelp.bulkInsertBuilding(connection.getInputStream());
				}
				connection.disconnect();
			}
			catch(IOException error)
			{
				Log.e("Bulk Directory", error.getMessage() + " ");
			}

		}

	}

	private class BulkInsertDirectoryOffline implements Runnable
	{

		public BulkInsertDirectoryOffline()
		{
		}

		@Override
		public void run() 
		{
			try
			{
				dbHelp.bulkInsertDirectory(getResources().openRawResource(R.raw.coles_directory));
			}
			catch(IOException error)
			{
				Log.e("Bulk Insert Issue", error.getMessage() + " ");
			}
		}

	}

	private class BulkInsertBuildingOffline implements Runnable
	{

		public BulkInsertBuildingOffline()
		{
		}

		@Override
		public void run() 
		{
			try
			{
				dbHelp.bulkInsertBuilding(getResources().openRawResource(R.raw.building_table));
			}
			catch(IOException error)
			{
				Log.e("Bulk Insert Issue", error.getMessage() + " ");
			}
			
		}
	}

	private boolean isNetworkAvailable()
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
		if(activeNetworkInfo == null)
		{
			return false;
		}
		else
		{
			return activeNetworkInfo.isConnected();
		}
	}

	private void popUpAlert() 
	{
		AlertDialog.Builder popUpBuild = new AlertDialog.Builder(this);

		popUpBuild.setMessage("An error occured while loading the application.");
		popUpBuild.setCancelable(false);
		popUpBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				finish();
			}
		});

		final AlertDialog popUpAlert = popUpBuild.create();

		popUpAlert.show();
	}

	private class LoadingOfflineDatabase extends AsyncTask<Void, Void, Integer>
	{

		@Override
		protected Integer doInBackground(Void... arg0)
		{
			try 
			{

				Thread directoryThread = new Thread(new BulkInsertDirectoryOffline());
				Thread buildingThread = new Thread(new BulkInsertBuildingOffline());
				Thread xmlThread = new Thread(new loadOfflineXML());
				directoryThread.start();
				buildingThread.start();
				xmlThread.start();

				xmlThread.join();
				buildingThread.join();
				directoryThread.join();

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return 0;
		}

		@Override
		protected void onPostExecute(Integer Result)
		{
			super.onPostExecute(Result);
			dbHelp.close();
			Intent intent = new Intent(ColesActivity.this, main.class);

			startActivity(intent);
			finish();
		}
	}

	private class LoadingOnlineDatabase extends AsyncTask<Void, Void, Integer>
	{
		private long startTime, endTime, total;

		@Override
		protected Integer doInBackground(Void... params) 
		{
			startTime = System.currentTimeMillis();
			int i =0;

			try
			{
				Thread directoryThread = new Thread(new BulkInsertDirectoryOnline());
				Thread buildingThread = new Thread(new BulkInsertBuildingOnline());
				Thread xmlThread = new Thread(new loadOnlineXML());
				directoryThread.start();
				buildingThread.start();
				xmlThread.start();

				xmlThread.join();
				buildingThread.join();
				directoryThread.join();

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			return i;

		}

		@Override
		protected void onPostExecute(Integer result)
		{
			super.onPostExecute(result);
			dbHelp.close();
			endTime = System.currentTimeMillis();
			total = endTime - startTime;
			Log.e("Total Time:", total + "ms");
			Intent intent = new Intent(ColesActivity.this, main.class);
			startActivity(intent);
			finish();

		}

	}
}
