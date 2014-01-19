package coles.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WebViewActivity extends Activity{
	private String textBody=null;
	private String heading, link;
	private int text_id, menu_no;
	private WebView wv;
	private boolean undergrad = MenuActivity.undergrad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		
		TextView title = (TextView) findViewById(R.id.title);
		TextView subtitle = (TextView) findViewById(R.id.subtitle);
		
		if(undergrad){
			title.setText("Undergraduate Programs");
		}
		else{
			title.setText("Graduate Programs");
			undergrad=false;
		}
		
		ImageButton home = (ImageButton) findViewById(R.id.homeBTN);
		home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent goHome = new Intent(getApplicationContext(), main.class);
				startActivity(goHome);
			}
		});
		
		wv = (WebView) findViewById(R.id.webView1123);	
		Bundle b = getIntent().getExtras();
		textBody = b.getString("theText");
		text_id = b.getInt("text_id");
		heading = b.getString("heading");
		subtitle.setText(heading);
		link = b.getString("link"); 
		Button info = (Button) findViewById(R.id.theBTN);
		info.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse(link);
				Intent i = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(i);
			}
		});
		menu_no = b.getInt("menu_no");
		try
		{
			if(textBody != null && textBody.contains("\\r\\n") || textBody.contains("\\") || textBody.contains("&") || textBody.contains(".*\\\"|\\\".*"))
			{
				textBody = textBody.replace("\\r\\n", "");
				textBody = textBody.replace("\\", "");
				textBody = textBody.replace("&", " & ");
				textBody = textBody.replace(".*\\\"|\\\".*", "THIS IS WHERE IM EDITING");
			}
			else if(textBody == null)
			{
				popUpAlert();
			}
		}
		catch(Exception e)
		{
			setContentView(R.layout.realmain);
			popUpAlert();
		}
		
		textBody = "<style>body{color:#FFFFFF;}</style>"+textBody;
		wv.setVerticalScrollBarEnabled(true);
		wv.setBackgroundColor(Color.BLACK);
		
		wv.loadData(textBody, "text/html", null);
	}
	
	private void popUpAlert() 
	{
		AlertDialog.Builder popUpBuild = new AlertDialog.Builder(this);

		popUpBuild.setMessage("There has been an issue.  Please make sure that you have an Internet or Data connection present.  If no connection is present, please wait until a connection is present and restart the application.");
		popUpBuild.setCancelable(false);
		popUpBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				Intent intent = new Intent(WebViewActivity.this, main.class);
				startActivity(intent);
				finish();
				dialog.dismiss();
				
			}
		});

		final AlertDialog popUpAlert = popUpBuild.create();

		popUpAlert.show();
	}
	
}
