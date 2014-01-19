package coles.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MapsDirectionsWelcome extends Activity
{

	/*
	 * Written by Matthew Boles
	 */
	
	private Button building, room;
	private Intent i;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_dir_layout);
		
		InitViews();
		
	}

	private void InitViews() 
	{
		
		
		building = (Button)findViewById(R.id.walk_to_build_b);
		room = (Button)findViewById(R.id.walk_to_room_b);
		
		building.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				i = null;
				
	    		i = new Intent(MapsDirectionsWelcome.this, WalkToBldgActivity.class);
			    
	    		startActivity(i);
			}
			
		});
		
		room.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				i = null;
				
				i = new Intent(MapsDirectionsWelcome.this, WalkToRmActivity.class);
				
				startActivity(i);
			}
			
		});
	}
	
}
