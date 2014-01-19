package coles.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class advisingCenter extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advisingcenter);
		ImageButton home = (ImageButton)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myInt = new Intent(getApplicationContext(), main.class);
				startActivity(myInt);
				
			}
		});

		Button info = (Button) findViewById(R.id.info1);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
	    		Uri uri = Uri.parse("http://coles.kennesaw.edu/undergraduate/advising/");
				Intent i = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(i);
                
            }
        });
	}
}
