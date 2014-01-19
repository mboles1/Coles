package coles.app;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


/**
 * @author Wayne
 *
 */
public class Applyp1Activity extends Activity {

		@Override
		public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
        setContentView(R.layout.applyp1);

//		Button btnInstruct = (Button) findViewById(R.id.instructBTN);
//		btnInstruct.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				Intent myInt = new Intent(getApplicationContext(), Applyp2Activity.class);
//				startActivity(myInt);					
//			}
//		});
		Button btnWorksheet = (Button) findViewById(R.id.worksheetBTN);
		btnWorksheet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myInt = new Intent(getApplicationContext(), Applyp3Activity.class);
				startActivity(myInt);					
			}
		});
		Button btnChecklist = (Button) findViewById(R.id.checklistBTN);
		btnChecklist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myInt = new Intent(getApplicationContext(), Applyp4Activity.class);
				startActivity(myInt);					
			}
		});

		}
	}
