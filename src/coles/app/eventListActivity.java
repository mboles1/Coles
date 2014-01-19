package coles.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class eventListActivity extends Activity
	 implements OnClickListener{
	    	@Override
			public void onCreate(Bundle savedInstanceState) {
	    	    super.onCreate(savedInstanceState);
	    	    setContentView(R.layout.eventlist);
	    	    
	    	    ImageButton home = (ImageButton) findViewById(R.id.homeBTN);
	    		home.setOnClickListener(new View.OnClickListener() {

	    			@Override
					public void onClick(View v) {
	    				Intent myInt = new Intent(getApplicationContext(), main.class);
	    				startActivity(myInt);
	    			}
	    		});

	
	    	Button programsBTN = (Button) findViewById(R.id.news1);
		        programsBTN.setOnClickListener(new View.OnClickListener() {
		            @Override
					public void onClick(View v) {
		            	Uri uri = Uri.parse("https://epay.kennesaw.edu/C20923_ustores/web/product_detail.jsp?PRODUCTID=1065");
		        		Intent i = new Intent(Intent.ACTION_VIEW, uri);
		        		startActivity(i);
		                
		            }
		        });
		        programsBTN = (Button) findViewById(R.id.news2);
		        programsBTN.setOnClickListener(new View.OnClickListener() {
		            @Override
					public void onClick(View v) {
		            	Uri uri = Uri.parse("http://coles.kennesaw.edu/graduate/mba/dalton.htm");
		        		Intent i = new Intent(Intent.ACTION_VIEW, uri);
		        		startActivity(i);
		                
		            }
		        });
		        programsBTN = (Button) findViewById(R.id.news3);
		        programsBTN.setOnClickListener(new View.OnClickListener() {
		            @Override
					public void onClick(View v) {
		            	Uri uri = Uri.parse("http://coles.kennesaw.edu/graduate/mba/information-sessions.htm");
		        		Intent i = new Intent(Intent.ACTION_VIEW, uri);
		        		startActivity(i);
		                
		            }
		        });
		        
		        programsBTN = (Button) findViewById(R.id.news4);
		        programsBTN.setOnClickListener(new View.OnClickListener() {
		            @Override
					public void onClick(View v) {
		            	Uri uri = Uri.parse("http://coles.kennesaw.edu/news/2012/0210-Education-Abroad-Broadens-Students-Horizons.htm");
		        		Intent i = new Intent(Intent.ACTION_VIEW, uri);
		        		startActivity(i);
		                
		            }
		        });
		        
		        programsBTN = (Button) findViewById(R.id.news5);
		        programsBTN.setOnClickListener(new View.OnClickListener() {
		            @Override
					public void onClick(View v) {
		            	Uri uri = Uri.parse("http://coles.kennesaw.edu/news/2012/0207-Back-to-School-at-the-Galleria.htm");
		        		Intent i = new Intent(Intent.ACTION_VIEW, uri);
		        		startActivity(i);
		                
		            }
		        });
		        
		        programsBTN = (Button) findViewById(R.id.news6);
		        programsBTN.setOnClickListener(new View.OnClickListener() {
		            @Override
					public void onClick(View v) {
		            	Uri uri = Uri.parse("http://coles.kennesaw.edu/news/2012/0202-Georgia-manufacturing-ticks-up.htm");
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