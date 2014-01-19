package coles.app;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class FloorMapWithQRActivity extends Activity 
{
	//private float touchX = 0.00f;
	//private float touchY = 0.00f;
	private int origFloor;
	private int newFloor;
	private int qrFloor;
	private int wt;
	private int ht;
	private int x;
	private int y;
	private int x2;
	private int y2;
	
	private String origFloorString;
	private String qrFloorString;
	
	Animation myFadeInAnimation;
	Animation myFadeOutAnimation;
	Animation myFadeInAnimation2;

	Animation myFadeOutAnimation2;

	private static final int firstFloor = 1;
	private static final int secondFloor = 2;
	private static final int thirdFloor = 3;
	private static final int fourthFloor = 4;


	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.floormapwithqr);

		//Bundle extras = getIntent().getExtras();

		Bundle extras = getIntent().getExtras();

		qrFloor = extras.getInt("floor2");

		ProgressTask task = new ProgressTask();
		task.execute(qrFloor);
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
			//Toast.makeText(getApplicationContext(), "" + touchX + ", " + touchY + " Percentage: " + formatter.format(touchPercentX) +"% x"+  formatter.format(100-touchPercentY)+"%" , 5).show();
		}
		return super.onTouchEvent(e);
	}
	
	
	private void setOneBackgroundAnimation(double xPer, double yPer)
	{
		View content = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
		Log.d("DISPLAY", content.getWidth() + " x " + content.getHeight());

		DisplayMetrics displayMetrics =  new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		ht = displayMetrics.heightPixels;
		wt = displayMetrics.widthPixels;
		
		ImageView myImageView = (ImageView) findViewById(R.id.arrow);
		
		myImageView.setVisibility(View.GONE);
		
		ImageView myImageView2 = (ImageView) findViewById(R.id.arrowred); 
		Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.tween);

		x = (int)(wt * xPer);
		y = (int)( ht * yPer);

		myImageView2.setPadding(x, 0, 0, y);
		myImageView2.startAnimation(myFadeInAnimation);

	}
	
	
	private void setBackgroundAnimations(Double xPer, Double yPer, Double xPer2, Double yPer2) 
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

		x = (int)(wt * xPer);
		y = (int) (ht * yPer);

		x2 = (int) (wt* xPer2);
		y2 = (int) (ht * yPer2);

		//Toast.makeText(getApplicationContext(), "Window Size: " +wt + "x" + ht + " Coordinates:" + x + "x" + y, 5).show();

		myImageView.setPadding(x, 0, 0, y);
		myImageView2.setPadding(x2, 0, 0, y2);
		myImageView.startAnimation(myFadeInAnimation);
		myImageView2.startAnimation(myFadeInAnimation2);
	}

	private void popUpAlert(String qeFloor, String origFloor) 
	{
		AlertDialog.Builder popUpBuild = new AlertDialog.Builder(this);

		popUpBuild.setMessage("You are on floor number " + qrFloor + ".  The room you are looking for is on floor number " + origFloor + ".");
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


	private class ProgressTask extends AsyncTask <Integer, Void, Integer>
	{

		FrameLayout frame = (FrameLayout) findViewById(R.id.RootViewQR);
		private ProgressDialog dialog;

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			dialog = ProgressDialog.show(FloorMapWithQRActivity.this, "Loading", "Loading Please Wait");
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

			if(dialog.isShowing())
			{
				dialog.dismiss();
			}


			if(result == 1)
			{
				frame.setBackgroundResource(R.drawable.bb_1st_smaller);
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

			double percentX = extras.getDouble("xPer1");
			double percentY = extras.getDouble("yPer1");
			double percentX2 = extras.getDouble("xPer2");
			double percentY2 = extras.getDouble("yPer2");
			
			newFloor = extras.getInt("floor2");
			origFloor = extras.getInt("floor1");
			
			origFloorString = Integer.toString(origFloor);
			qrFloorString = Integer.toString(newFloor);
			
			if(newFloor == origFloor)
			{
				setBackgroundAnimations(percentX, percentY, percentX2, percentY2);
				popUpAlert(qrFloorString, origFloorString);
			}
			
			else
			{
				setOneBackgroundAnimation(percentX2, percentY2);
				popUpAlert(qrFloorString, origFloorString);
			}
			
			
			

			
		}



	}

}
