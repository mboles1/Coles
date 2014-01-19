package coles.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegistrationInfo extends Activity
{
	private TextView title, description;
	private Button moreInfo;
	private String titleString;
	private String descriptionString;
	private String moreInfoString;
	
	private Bundle extras;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_info_layout);
		
		extras = getIntent().getExtras();
		
		title = (TextView)findViewById(R.id.registrationTitleTV);
		title.setText("Title Should be Here");
		description = (TextView)findViewById(R.id.registrationDescriptionTV);
		description.setText("Description should be here");
		moreInfo = (Button)findViewById(R.id.moreRegistrationInfo);
		
		titleString = "Title Should be Here";
		descriptionString = "Description should be here";
		
		titleString = extras.getString("t");
		descriptionString = extras.getString("d");
		moreInfoString = extras.getString("l");
		
		title.setText(titleString);
		description.setText(descriptionString);
		Log.d("newTitle", titleString);
		Log.d("newDescription", descriptionString);
		Log.d("newLink", moreInfoString);
		
		if(moreInfoString.equals(""))
		{
			moreInfo.setVisibility(View.GONE);
		}
		else
		{
			moreInfo.setVisibility(View.VISIBLE);
		}
		
		moreInfo.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				//Uri uri = Uri.parse(moreInfoString);
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(moreInfoString));
				startActivity(i);
			}
			
		});
	}
	
}
