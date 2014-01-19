package coles.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 *
 * This is the application check list activity
 * @author Wayne 3/23/2012
 *
 */
public class Applyp4Activity extends Activity {
	private static final String CHK_STATE = "ChkState";
	private SharedPreferences chk_state;
	private static final int NUMBER_OF_STEPS = 9;
	private boolean [] stepDone = new boolean[NUMBER_OF_STEPS];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.applyp4);
	    
	    ImageButton home = (ImageButton)findViewById(R.id.homeBTN);
        home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myInt = new Intent(getApplicationContext(), main.class);
				startActivity(myInt);
				
			}
		});
	
		String strIdx = "";
		
		final CheckBox chkArray[] = {
				(CheckBox) findViewById(R.id.CheckView01),
				(CheckBox) findViewById(R.id.CheckView02),
				(CheckBox) findViewById(R.id.CheckView03),
				(CheckBox) findViewById(R.id.CheckView04),
				(CheckBox) findViewById(R.id.CheckView05),
				(CheckBox) findViewById(R.id.CheckView06),
				(CheckBox) findViewById(R.id.CheckView07),
				(CheckBox) findViewById(R.id.CheckView08),
				(CheckBox) findViewById(R.id.CheckView09)
		};
		chk_state = getSharedPreferences(CHK_STATE, MODE_PRIVATE);

		for (int i = 0; i < NUMBER_OF_STEPS; i++)
		{
		    chkArray[i].setOnClickListener(chkbox_listener);
			strIdx = "chkNumber" + String.valueOf(i);
			stepDone[i] = chk_state.getBoolean(strIdx, false);
		    chkArray[i].setChecked(stepDone[i]);
		}
	}
	
    private void updateState(int id, boolean isChecked)
    {
		int chkNumber = chkIndex(id);
    	String strIdx = "chkNumber" + String.valueOf(chkNumber);
		setStateVar(strIdx, isChecked);
		stepDone[chkNumber] = isChecked;
		
		int countChecked = 0;
		for (int i = 0; i < NUMBER_OF_STEPS; i++)
		{
			if (stepDone[i])
			{
				countChecked++;
			}
		}
    	queToast(countChecked + " of " + NUMBER_OF_STEPS + " are checked.");
	}
    
	private final <T> void setStateVar(final String key, final T value) {
		SharedPreferences.Editor edit = chk_state.edit();
		if(value.getClass().equals(String.class)) {
		    edit.putString(key, (String)value); 
		} else if (value.getClass().equals(Boolean.class)) {
		    edit.putBoolean(key, (Boolean)value); 
		} else if (value.getClass().equals(Integer.class)) {
		    edit.putInt(key, (Integer)value); 
		} else if (value.getClass().equals(Long.class)) {
		    edit.putLong(key, (Long)value); 
		} else if (value.getClass().equals(Float.class)) {
		    edit.putFloat(key, (Float)value); 
		}
		edit.commit();
	}
    
     private int chkIndex(int i)
    {
    	int result = 0;
    	switch (i)
    	{
    	case R.id.CheckView01:
			result = 0;
	    	break;
    	case R.id.CheckView02:
			result = 1;
	    	break;
    	case R.id.CheckView03:
			result = 2;
	    	break;
    	case R.id.CheckView04:
			result = 3;
	    	break;
    	case R.id.CheckView05:
			result = 4;
	    	break;
    	case R.id.CheckView06:
			result = 5;
	    	break;
    	case R.id.CheckView07:
			result = 6;
	    	break;
    	case R.id.CheckView08:
			result = 7;
	    	break;
    	case R.id.CheckView09:
			result = 8;
	    	break;
    	}
    	return result;
	}
    private OnClickListener chkbox_listener = new OnClickListener() 
    {
        @Override
		public void onClick(View v) 
        {
        	updateState(((CheckBox)v).getId(), ((CheckBox)v).isChecked());
    	}
    };
    
	private void queToast(String s) {
		final Context context2 = getApplicationContext();
	    final int duration2 = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context2, s, duration2);
		toast.setGravity(Gravity.TOP|Gravity.LEFT, 30, 50);
		toast.show();
	}
}