package coles.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Minors extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.majors);

		TextView title = (TextView) findViewById(R.id.subtitle);
		title.setText("Minors");
		TextView desc = (TextView) findViewById(R.id.description);
		desc.setText("Minors and Certificates text here");
		
		ImageButton home = (ImageButton)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myInt = new Intent(getApplicationContext(), main.class);
				startActivity(myInt);
				
			}
		});
		Button info = (Button) findViewById(R.id.web);
		info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri
						.parse("http://coles.kennesaw.edu/undergraduate/programs/minors.htm");
				Intent i = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(i);

			}
		});

	}

}
