package coles.app;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class FloorMapActivity extends Activity
{
	private int wt;
	private int ht;
	private String contents;
	Animation myFadeInAnimation;
	Animation myFadeOutAnimation;
	private static final int fourthFloor = 4;
	private static final int thirdFloor = 3;
	private static final int secondFloor = 2;
	private static final int firstFloor = 1;



	/*
	 * AsyncTask params <Params, Progress, Result>
	 * Params:info passed to be used in task
	 * Progress: type of progress that is accomplished
	 * Result:result returned after task has finished
	 * 
	 * Basically for this activity you need all your method in the other thread because of
	 * we are changing the background around and all.  Without having the methods all in the thread,
	 * doesn't let the ProgressDialog show for some reason (I think because of the animation stuff)
	 * 
	 */



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.floormap);

		//popUpAlert();
		/*
		 * You'll have to test on actual phone to see if there is a hang up like on the emulator
		 * If there is, then have to figure out async tasks and all that stuff.
		 */

		Bundle extras = getIntent().getExtras();

		String roomNumString = extras.getString("room#");
		int roomNumInt = extras.getInt("floor");

		setTitle("Walk to BB-" + roomNumString);

		ProgressTask task = new ProgressTask();
		task.execute(roomNumInt);

		
		final Button btnWalkToRm = (Button) findViewById(R.id.btnWalkRm); 

		btnWalkToRm.setOnClickListener(new OnClickListener() 
		{ 
			@Override
			public void onClick(View v) 
			{ 
				IntentIntegrator integrator = new IntentIntegrator(FloorMapActivity.this);
				integrator.initiateScan();
			} 
		});
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

		try 
		{
			if (scanResult != null) 
			{
				
				contents = scanResult.getContents();
				scanResult.getFormatName();
				
				Intent i = null;
				i = new Intent(FloorMapActivity.this, FloorMapWithQRActivity.class);

				String[] values;
				String delimiter = ":";
				values = contents.split(delimiter);
				double xPer2 = Double.parseDouble(values[1]);
				double yPer2 = Double.parseDouble(values[2]);
				int floor2 = Integer.parseInt(values[3]);

				Bundle extras = getIntent().getExtras();

				double xPer1 = extras.getDouble("xPer1");
				double yPer1 = extras.getDouble("yPer1");
				int floor1 = extras.getInt("floor");
				extras.getString("building");
				String room = extras.getString("room#");

				Bundle b = new Bundle();
				b.putString("room", room);
				b.putDouble("xPer1", xPer1);
				b.putDouble("yPer1", yPer1);

				b.putDouble("xPer2", xPer2);
				b.putDouble("yPer2", yPer2);

				b.putInt("floor2", floor2);
				b.putInt("floor1", floor1);
				b.putString("QR", contents);

				i.putExtras(b);
				finish();

				startActivity(i);

			}
			else
			{
				
			}

		} catch (Exception e) {

		}
	}
	
	//async <type of params, progressvalue, resultValue>
	//									  , doInBackground



	private void setBackgroundAnimations(Double xPer, Double yPer, Double xPer2, Double yPer2, Double xPer3, Double yPer3) 
	{
		View content = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
		Log.d("DISPLAY", content.getWidth() + " x " + content.getHeight());

		DisplayMetrics displayMetrics =  new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


		ht = displayMetrics.heightPixels;
		wt = displayMetrics.widthPixels;
		
		ImageView myImageView = (ImageView) findViewById(R.id.arrow);
		Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.tween);
		
		ImageView myImageView2 = (ImageView) findViewById(R.id.arrowred); 
		Animation myFadeInAnimation2 = AnimationUtils.loadAnimation(this, R.anim.tween);
		
		ImageView elevatorSet2 = (ImageView) findViewById(R.id.arrowred2);
		Animation myFadeAnimation3 = AnimationUtils.loadAnimation(this, R.anim.tween);
		
		int x = (int)(wt * xPer);
		int y = (int) (ht * yPer);

		int x2 = (int) (wt* xPer2);
		int y2 = (int) (ht * yPer2);
		
		int x3 = (int) (wt* xPer3);
		int y3 = (int) (ht * yPer3);

		myImageView.setPadding(x, 0, 0, y);
		myImageView2.setPadding(x2, 0, 0, y2);
		elevatorSet2.setPadding(x3, 0, 0, y3);
		
		myImageView.startAnimation(myFadeInAnimation);
		myImageView2.startAnimation(myFadeInAnimation2);
		elevatorSet2.startAnimation(myFadeAnimation3);
	}


	private void popUpAlert(String roomNumString, int floorNumber) 
	{
		AlertDialog.Builder popUpBuild = new AlertDialog.Builder(this);

		popUpBuild.setMessage("Destination is room " + roomNumString + " on floor " + floorNumber + ". For walking directions, find QR code by nearest elevator and click QR button at the corner of this screen.");
		popUpBuild.setCancelable(false);
		popUpBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.cancel();
			}
		});

		final AlertDialog popUpAlert = popUpBuild.create();

		popUpAlert.show();
	}



	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		if(e.getAction() == MotionEvent.ACTION_DOWN)
		{
			float touchX = e.getX();
			float touchY = e.getY();

			float touchPercentX = touchX/wt * 100;
			float touchPercentY = touchY/ht * 100;
			NumberFormat formatter = new DecimalFormat("#0.00");

			Log.e("Percentages", "" + touchX + ", " + touchY + " Percentage: " + formatter.format(touchPercentX) +"% x"+  formatter.format(100-touchPercentY)+"%");
		}
		
		return super.onTouchEvent(e);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.floor_map_menu, menu);
		return true;
	}


	class MyLocationListener implements LocationListener 
	{ 
		@Override
		public void onLocationChanged(Location loc) 
		{ 
			loc.getLatitude(); 
			loc.getLongitude(); 

			String Text = "My current location is: " + "Latitud = " + loc.getLatitude() + 
					"Longitud = " + loc.getLongitude(); 
			Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show(); 
		} 

		@Override
		public void onProviderDisabled(String provider) 
		{ 
			Toast.makeText( getApplicationContext(),"Gps Disabled", Toast.LENGTH_SHORT ).show(); 
		} 

		@Override
		public void onProviderEnabled(String provider) 
		{ 
			Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show(); 
		} 

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) 
		{ 

		} 
	}

	private class ProgressTask extends AsyncTask <Integer, Void, Integer>
	{

		FrameLayout frame = (FrameLayout) findViewById(R.id.RootView);
		private ProgressDialog dialog;

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			dialog = ProgressDialog.show(FloorMapActivity.this, "Loading", "Loading Please Wait");

		}

		@Override
		protected Integer doInBackground(Integer... params)
		{
			//Params is set up as an array
			int start = params[0];
			int i = 0;

			/*
			 * This should make it a little faster when setting the background depending room number entered into search 
			 * Will put in error message when trying to search for an invalid room number.
			 */

			if(start < firstFloor)
			{
				//Error message (dialog probably)
			}
			else if(start == firstFloor)
			{
				i=1;
			}
			else if(start == secondFloor)
			{
				i=2;
			}
			else if(start == thirdFloor)
			{
				i=3;
			}
			else if(start == fourthFloor)
			{
				i=4;
			}
			else
			{
				//Error Message
			}


			return i;


		}

		@Override
		protected void onPostExecute(Integer result)
		{

			super.onPostExecute(result);

			double xPer2 = 0.696;
			double yPer2 = 0.315;
			
			double xPer3 = 0.245;
			double yPer3 = 0.470;
			
			if(dialog.isShowing())
			{
				dialog.dismiss();
			}

			if(result == 1)
			{
				frame.setBackgroundResource(R.drawable.bb_1st_smaller);
				xPer2 = 0.626;
				yPer2 = 0.38;
				
				xPer3 = 0.285;
				yPer3 = .500;
			}
			else if(result == 2)
			{
				frame.setBackgroundResource(R.drawable.bb_2nd_smaller);
			}
			else if(result == 3)
			{
				frame.setBackgroundResource(R.drawable.bb_3rd_smaller);
			}
			else
			{
				frame.setBackgroundResource(R.drawable.bb_4th_smaller);
			}

			Bundle extras = getIntent().getExtras();

			String roomNumString = extras.getString("room#");
			int floorNumber = extras.getInt("floor");
			double xPer = extras.getDouble("xPer1");
			double yPer = extras.getDouble("yPer1");

			

			setBackgroundAnimations(xPer, yPer, xPer2, yPer2, xPer3, yPer3);

			popUpAlert(roomNumString, floorNumber);
		}

	}


}


