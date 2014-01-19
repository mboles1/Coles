package coles.app;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import registration.*;

public class Registration extends ListActivity 
{

	RegistrationXMLParser regtext;
	RegistrationDatabaseHandler dbreg;
	List<RegistrationText> regStrings;

	private String title, description, link;

	private String convert(String str) 
	{ 
		str = str.replace("&amp;", "&"); 
		str = str.replace("&gt;", ">"); 
		str = str.replace("&lt;", "<"); 
		str = str.replace("&apos;", "'"); 
		str = str.replace("&quot;", "\""); 
		str = str.replace("&#039;", "'");
		str = str.replace("\\r\\n", "\n");
		str = str.replace("null", "");
		str = str.replace("\\", "");
		return str; 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		regtext = new RegistrationXMLParser();
		dbreg = new RegistrationDatabaseHandler(this);

		regStrings = regtext.getText();
		int count = regStrings.size();
		for (RegistrationText regtxt: regStrings)
		{
			dbreg.addText(new RegistrationText(regtxt.id, regtxt.title, regtxt.description, regtxt.link));
		}

		findViewById(R.id.theBTN);	
		findViewById(R.id.LinearLayout1);
		findViewById(R.id.buttonll);


		ImageButton home = (ImageButton)findViewById(R.id.homeBTN);
		home.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				Intent myInt = new Intent(getApplicationContext(), main.class);
				startActivity(myInt);

			}
		});

		final ArrayList<String> registrationInfo = new ArrayList<String>();

		ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, R.layout.registration_list_item, R.id.registrationListInfo, registrationInfo);

		for(int i =0;i<count; i++)
		{
			registrationInfo.add(regStrings.get(i).id + ". " + convert(regStrings.get(i).title));
		}

		// Assign adapter to ListView
		setListAdapter(listViewAdapter);
	}

	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);

		Intent i = new Intent(Registration.this, RegistrationInfo.class);

		title = convert(regStrings.get(position).title);
		description = convert(regStrings.get(position).description);
		link = "";
		if(position == 0 || position == 1)
		{
			Log.d("title", title);
			Log.d("description", description);
			Log.d("link", link);

			i.putExtra("t", title);
			i.putExtra("d", description);
			i.putExtra("l", link);

			startActivity(i);
		}
		else
		{
			link = convert(regStrings.get(position).link);
			Log.d("title", title);
			Log.d("description", description);
			Log.d("link", link);

			i.putExtra("t", title);
			i.putExtra("d", description);
			i.putExtra("l", link);

			startActivity(i);
		}
		

	}

	/*
	 * Recreate the list when the activity has finished
	 */
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);

		switch(requestCode)
		{
		case 0:
			break;
		}
	}

}
