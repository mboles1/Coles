package coles.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchResultActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		Button search = (Button)findViewById(R.id.button1);
        search.setOnClickListener(new View.OnClickListener() {
			
				@Override
				public void onClick(View v) {
					Intent myInt = new Intent(getApplicationContext(), FloorMapActivity.class);
					startActivity(myInt);
					
				}
			});
	}
}
