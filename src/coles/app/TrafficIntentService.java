package coles.app;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

public class TrafficIntentService extends IntentService {

	private boolean loop;
	private int temp;
	private int manual;
	private int people;
	private int state;
	private int icon;
	private int count;
	private NotificationManager notificationManager;
	private Notification note;
	private PendingIntent intent;

	public TrafficIntentService() {
		super("TrafficIntent");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Check", "service started");
		loop = true;
		// push background
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		super.stopSelf();
		loop = false;
		stopSelf();
		Log.d("Check", "service destroyed");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		temp = R.drawable.red;
		while (loop) {
			setLoop(true);
			Log.d("Check", "service loop running");
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (!(AppStatus.getInstance(this).isOnline(this))) {

			} else {

				if (TrafficHttpClient.getStatus() == 1) {
					try {
						if (temp != updateResultsInUi()) {
							// string = Boolean.toString(temp !=
							// updateResultsInUi());
							Log.d("Check", "updated");
							temp = updateResultsInUi();

							if (loop) {

								SetNotification(updateResultsInUi());

								try {

								} catch (IllegalAccessError e) {
									Log.d("Check", "intent alert show failed");
								}
								Log.d("Check", "if running");
							} else {
								Log.d("Check", "else failed");
							}
						}

					} catch (IllegalArgumentException e) {
						Log.d("Check", "failed here");
					}
				}
			}
		}
	}

	public int updateResultsInUi() {
		manual = TrafficHttpClient.getManual();
		people = TrafficHttpClient.getCount();
		if (manual == 0) {
			if (people <= 3) {
				state = R.drawable.green;
			} else if (people > 3 && people < 7) {
				state = R.drawable.yellow;
			} else {
				state = R.drawable.red;
			}
		} else if (manual == 1) {
			Log.d("Check", "getColor not reached");
			if (TrafficHttpClient.getColor() == 3) {
				state = R.drawable.green;
			} else if (TrafficHttpClient.getColor() == 2) {
				state = R.drawable.yellow;
			} else if (TrafficHttpClient.getColor() == 1) {
				state = R.drawable.red;
			}
		}
		return state;
	}

	public void SetNotification(int icon2) {
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		this.icon = icon2;
		note = new Notification(this.icon, "Traffic Alert",
				System.currentTimeMillis());
		note.defaults |= Notification.DEFAULT_SOUND;
		intent = PendingIntent.getActivity(this, 0, new Intent(this,
				TrafficClassActivity.class), 0);
		switch (icon2) {
		case R.drawable.green:
			note.setLatestEventInfo(this, "Advising Center Traffic",
					"Traffic is Low", intent);
			break;
		case R.drawable.yellow:
			note.setLatestEventInfo(this, "Advising Center Traffic",
					"Traffic is Moderate", intent);
			break;
		case R.drawable.red:
			note.setLatestEventInfo(this, "Advising Center Traffic",
					"Traffic is High", intent);
			break;
		}
		note.number = ++count;
		notificationManager.notify(7331, note);
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	public boolean getLoop(){
		return loop;
	}
}
