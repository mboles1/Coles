package coles.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class newsActivity extends Activity
	 implements OnClickListener{
	    	@Override
			public void onCreate(Bundle savedInstanceState) {
	    	    super.onCreate(savedInstanceState);
	    	    setContentView(R.layout.news);
	 
	 
	            
	         
	    	Button programsBTN = (Button) findViewById(R.id.news0);
	        programsBTN.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {
	                Intent i = new Intent(getApplicationContext(), MessageList.class);
	                startActivity(i);
	                
	            }
	        });
	        
	        programsBTN = (Button) findViewById(R.id.news00);
	        programsBTN.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {
	                Intent i = new Intent(getApplicationContext(), eventListActivity.class);
	                startActivity(i);
	                
	            }
	        });
	        
	        
	        programsBTN = (Button) findViewById(R.id.news1);
	        programsBTN.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {
	            	Uri uri = Uri.parse("http://coles.kennesaw.edu/news/2011/1116-KSUs-part-time-and-Executive-MBAs-recognized-in-Bloomberg-Businessweek-rankings.htm");
	        		Intent i = new Intent(Intent.ACTION_VIEW, uri);
	        		startActivity(i);
	                
	            }
	        });
	        programsBTN = (Button) findViewById(R.id.news2);
	        programsBTN.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {
	            	Uri uri = Uri.parse("http://coles.kennesaw.edu/news/2012/0202-Georgia-manufacturing-index-up-in-January.htm");
	        		Intent i = new Intent(Intent.ACTION_VIEW, uri);
	        		startActivity(i);
	                
	            }
	        });
	        programsBTN = (Button) findViewById(R.id.news3);
	        programsBTN.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {
	            	Uri uri = Uri.parse("http://coles.kennesaw.edu/news/2012/0120-Kennesaw-State-students-work-as-promoters-agents-for-new-local-artists.htm");
	        		Intent i = new Intent(Intent.ACTION_VIEW, uri);
	        		startActivity(i);
	                
	            }
	        });
	        programsBTN = (Button) findViewById(R.id.events1);
	        programsBTN.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {
	            	Uri uri = Uri.parse("http://coles.kennesaw.edu/breakfast/");
	        		Intent i = new Intent(Intent.ACTION_VIEW, uri);
	        		startActivity(i);
	                
	            }
	        });
	        programsBTN = (Button) findViewById(R.id.events2);
	        programsBTN.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {
	            	Uri uri = Uri.parse("http://coles.kennesaw.edu/graduate/mba/dalton.htm");
	        		Intent i = new Intent(Intent.ACTION_VIEW, uri);
	        		startActivity(i);
	                
	            }
	        });
	        programsBTN = (Button) findViewById(R.id.events3);
	        programsBTN.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {
	            	Uri uri = Uri.parse("http://coles.kennesaw.edu/admissions-events.htm");
	        		Intent i = new Intent(Intent.ACTION_VIEW, uri);
	        		startActivity(i);
	                
	            }
	        });
	        
	       
	    }
	    	

			//@Override
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
	    
	    
	    
	}

