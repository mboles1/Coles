package coles.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TrafficClassActivity extends Activity {
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	private ImageView light;
	private int color;
	private int count = 0;
	private int manual;
	private int realStatus;
	private int people;
	private int icon = R.drawable.red;
	private int state;
	private Intent trafficservice;
	private String string;
	private TextView currentWaitTV;
	private boolean status = true;
	private CheckBox statusN;
	TrafficIntentService checkloop;
	private CheckedTextView cv1;
	private CheckBox cb1;
	
	private TextView red, yellow, green;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.trafficlayout);
		
		ImageButton home = (ImageButton) findViewById(R.id.homeBTN);
		home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myInt = new Intent(getApplicationContext(), main.class);
				startActivity(myInt);
			}
		});

		trafficservice = new Intent(this, TrafficIntentService.class);
		statusN = (CheckBox) findViewById(R.id.checkBox1);
		light = (ImageView) findViewById(R.id.light);
		currentWaitTV = (TextView) findViewById(R.id.waitTimeTV);
		
		red = (TextView)findViewById(R.id.redText);
		yellow = (TextView)findViewById(R.id.yellowText);
		green = (TextView)findViewById(R.id.greenText);
		
		cv1 = (CheckedTextView) findViewById(R.id.checkedTextView1);
		checkloop = new TrafficIntentService();

		if (AppStatus.getInstance(this).isOnline(this)) {
			Log.d("CheckStatus", Integer.toString(TrafficHttpClient.getPage()));
			if (TrafficHttpClient.getPage() == 404) {
				closed();
			} else {
				currentWaitTV.append(" " + TrafficHttpClient.getDate());
				updateResultsInUi();
			}
		} else {
			Toast internet = Toast.makeText(TrafficClassActivity.this,
					"No internet connection is detected", Toast.LENGTH_LONG);
			internet.setGravity(Gravity.TOP, internet.getXOffset() / 2,
					internet.getYOffset() / 2);
			internet.show();
			status = false;
			closed();
		}
	}

	@Override
	public void onStart() {
		super.onStart();


		if (checkloop.getLoop()) {
			Log.d("Check", "serviceLoop is true");
			statusN.setChecked(true);
		}

		else{
			statusN.setChecked(false);
			Log.d("Check", "serviceLoop returned false");
		}
		
		builder = new AlertDialog.Builder(this);

		builder.setMessage(
				"Would you like to receive status bar notification as status changes?")
				.setCancelable(false)
				.setPositiveButton("Run",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								startService(trafficservice);
							}
						})
				.setNegativeButton("Off",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								stopService(trafficservice);

							}
						});
		alert = builder.create();

		CheckedTextView statusBTN = (CheckedTextView) findViewById(R.id.checkedTextView1);
		statusBTN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (status == true) {
					if (TrafficHttpClient.getDate() == "0") {
						closed();
					} else {
						currentWaitTV.append(TrafficHttpClient.getDate());
						//line6.setText("Current Wait Time in Lobby");
						red.setText("Red: Long");
						yellow.setText("Yellow: Average");
						green.setText("Green: Little or None");
						updateResultsInUi();
					}
					// closed();
				} else {
				}
			}
		});

		statusN.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				if(isChecked){
					startService(trafficservice);
				}
				
				else if (isChecked == false){
					stopService(trafficservice);
				}
			}
		});

	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    save(statusN.isChecked());
	}

	@Override
	public void onResume() {
	    super.onResume();
	    statusN.setChecked(load());
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}

	public void closed() {

		light.setImageDrawable(getResources().getDrawable(R.drawable.red));
		currentWaitTV.setText("Advising is closed/not available at this time");
		red.setText("");
		yellow.setText("");
		green.setText("");
		//currentWaitTV.setGravity(17);
		}

	public int updateResultsInUi() {
		try {

			manual = TrafficHttpClient.getManual();
			people = TrafficHttpClient.getCount();
			realStatus = TrafficHttpClient.getStatus();
			Log.d("checkcolor", "doesn't work");
			color = TrafficHttpClient.getColor();
			Log.d("checkcolor", "doesn't work");
		} catch (IllegalAccessError e) {
			manual = 0;
			people = 9;
		}
		if(realStatus == 0){
			closed();
		}
		else{
		if (manual == 0) {
			if (people <= 3) {
				light.setImageDrawable(getResources().getDrawable(
						R.drawable.green));
				state = R.drawable.green;
			} else if (people > 3 && people < 7) {
				light.setImageDrawable(getResources().getDrawable(
						R.drawable.yellow));
				state = R.drawable.yellow;
			} else {
				light.setImageDrawable(getResources().getDrawable(
						R.drawable.red));
				state = R.drawable.red;
			}
		} else if (manual == 1) {
			Log.d("check", "color not reached");

			if (color == 3) {
				//light.setImageDrawable(getResources().getDrawable(
				//		R.drawable.green));
				//line2.setText("Traffic is Low");
				state = R.drawable.green;
			} else if (TrafficHttpClient.getColor() == 2) {
				//light.setImageDrawable(getResources().getDrawable(
				//		R.drawable.yellow));
				//line2.setText("Traffic is Moderate");
				state = R.drawable.yellow;

			} else if (TrafficHttpClient.getColor() == 1) {
				//light.setImageDrawable(getResources().getDrawable(
				//		R.drawable.red));
				//line2.setText("Traffic is High");
				state = R.drawable.red;
			}
		}
		}
		Toast statuschecked = Toast.makeText(this, "Status updated",
				Toast.LENGTH_SHORT);
		statuschecked.show();
		return state;
	}
	
	

	public void SetNotification(int icon2) {
		final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		icon = icon2;
		string = Integer.toString(icon);
		Log.d("Check", string);

		Notification note = new Notification(this.icon, "Traffic Alert",
				System.currentTimeMillis());
		note.defaults |= Notification.DEFAULT_SOUND;
		PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(
				this, TrafficClassActivity.class), 0);

		note.setLatestEventInfo(this, "Traffic Status has Changed", "", intent);
		note.number = ++count;
		notificationManager.notify(7331, note);

		string = Integer.toString(note.number);
		Log.d("Check", "count " + string);
	}
	
	private void save(final boolean isChecked) {
	    SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putBoolean("check", isChecked);
	    editor.commit();
	}

	private boolean load() { 
	    SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
	    return sharedPreferences.getBoolean("check", false);
	}
	
}