package coles.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;

public class dummyActivity extends Activity
{
	String test;
	EditText dummyField;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dummy_layout);
		
		dummyField = (EditText)findViewById(R.id.dummy_field);
		
		Bundle b = getIntent().getExtras();
		
		test = b.getString("test");
		
		dummyField.setText(test);
	}
	
	protected class test extends AsyncTask<Integer, Integer, Integer>
	{

		@Override
		protected Integer doInBackground(Integer... params) 
		{
			int whatever;
			
			return null;
		}
		
	}
}
