package coles.app;

import coles.app.FloorMapActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.*;

public class SearchActivity extends Activity {
	String rbtnMatchBy1 ="Building Name", rbtnMatchBy2 ="Office Name";
	String inputText1, inputText2;
	Double percentX, percentY;
	Integer room;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {    
    	super.onCreate(savedInstanceState);    
    	setContentView(R.layout.searchmain);
    	final EditText entry1 = (EditText) findViewById(R.id.entry1);
    	entry1.setOnKeyListener(new OnKeyListener() {    
    		@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {        
    			// If the event is a key-down event on the "enter" button        
    			if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {          
    				// Perform action on key press          
//    				Toast.makeText(Coles_appActivity.this, entry1.getText(), Toast.LENGTH_SHORT).show();          
    				return true;
				}
    			return false;
    		}
		});
    		
    	final EditText entry2 = (EditText) findViewById(R.id.entry2);
    	entry2.setOnKeyListener(new OnKeyListener() {    
    		@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {        
    			// If the event is a key-down event on the "enter" button        
    			if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {          
    				// Perform action on key press          
//    				Toast.makeText(Coles_appActivity.this, entry2.getText(), Toast.LENGTH_SHORT).show();          
    				return true;        
				}
    			return false;
    		}
		});
    	
    	final Button btnSearch = (Button) findViewById(R.id.btnSearch); 
    	btnSearch.setOnClickListener(new OnClickListener() { 
            @Override
			public void onClick(View v) 
            { 	
                // move to another activity screen
                Intent i = new Intent(getApplicationContext(), FloorMapActivity.class);
               i.putExtra("percentX", .35);
               i.putExtra("percentY", .75);
               i.putExtra("room", 402);
                startActivity(i);
            } 
        }); 
    	
    	RadioButton rBtnBldgName = (RadioButton)findViewById(R.id.rBtnBldgName);
    	RadioButton rBtnClgName = (RadioButton)findViewById(R.id.rBtnClgName);
    	RadioButton rBtnOffName = (RadioButton)findViewById(R.id.rBtnOffName);
    	RadioButton rBtnRmNum = (RadioButton)findViewById(R.id.rBtnRmNum);
    	
    	rBtnBldgName.setOnClickListener(radioButtonOnClickListener);
    	rBtnClgName.setOnClickListener(radioButtonOnClickListener);
    	rBtnOffName.setOnClickListener(radioButtonOnClickListener2);
    	rBtnRmNum.setOnClickListener(radioButtonOnClickListener2);

    }
    
    private OnClickListener radioButtonOnClickListener = new OnClickListener() {    
    	@Override
		public void onClick(View v) {
    		RadioButton rButton = (RadioButton) v;
    		rbtnMatchBy1 = (String)rButton.getText();
		}
	};
	
    private OnClickListener radioButtonOnClickListener2 = new OnClickListener() {    
    	@Override
		public void onClick(View v) {
    		RadioButton rButton = (RadioButton) v;
    		rbtnMatchBy2 = (String)rButton.getText();
		}
	};
}
