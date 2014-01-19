package coles.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

public class TrafficAlertDialog {

	private AlertDialog.Builder builder;
	private AlertDialog alert;
	private TrafficIntentService setLoop;
	
	public void AlertDialog(){
		
		try{
		setLoop = new TrafficIntentService();
		builder.setMessage("Would you like to receive status bar notification as status changes?")
		   .setCancelable(false)
		   .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			   @Override
			public void onClick(DialogInterface dialog, int id) {
				   setLoop.setLoop(true);
			   }
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					setLoop.setLoop(false);
				}
			});
	alert = builder.create();
	alert.show();
		}
		catch (IllegalAccessError e){
			Log.d("Check", "Give up, it doesn't work");
		}
	}
}
