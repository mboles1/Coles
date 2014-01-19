package coles.app;

import java.util.ArrayList;
import java.util.List;

import FAQs.FAQDatabaseHandler;
import FAQs.FAQText;
import FAQs.FAQXMLParser;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class FAQActivity extends ListActivity {
	
	FAQXMLParser faqtext;
	FAQDatabaseHandler dbfaq;
	List<FAQText> faqStrings;
	private String question;
	private String answer;
	
	private String convert(String str) 
	{ 
	  str = str.replace("&amp;", "&"); 
	  str = str.replace("&gt;", ">"); 
	  str = str.replace("&lt;", "<");
	  str = str.replace("&apos;", "'");
	  str = str.replace("&quot;", "\""); 
	  str = str.replace("&#039;", "'");
	  str = str.replace("\\r\\n", "\n");
	  str = str.replace("null", "");
	  str = str.replace("\\", "");

	  return str; 
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		
		faqtext = new FAQXMLParser();
		dbfaq = new FAQDatabaseHandler(this);
		
		faqStrings = faqtext.getText();
		int count = faqStrings.size();
		for (FAQText faqtxt: faqStrings){
      	dbfaq.addText(new FAQText(faqtxt.id,faqtxt.question,faqtxt.answer));
        }
		
		//final ListView lv = (ListView) findViewById(R.id.RegListView);
		//final LinearLayout m_layout = (LinearLayout) this.findViewById(R.id.LinearLayout1);
		//final boolean is_clicked = false;
		TextView title = (TextView) findViewById(R.id.subtitle);
		title.setText("FAQs");
		

		ImageButton home = (ImageButton)findViewById(R.id.homeBTN);
        home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myInt = new Intent(getApplicationContext(), main.class);
				startActivity(myInt);
				
			}
		});
        
        ArrayList<String> faqArrayList = new ArrayList<String>();
        
        for(int i =0;i<count; i++){
        	faqStrings.get(i).question.replace("null", "");
        	faqArrayList.add(convert(faqStrings.get(i).question));
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.registration_list_item, R.id.registrationListInfo, faqArrayList);
        
        setListAdapter(adapter);
	}
        
		
        
        protected void onListItemClick(ListView l, View v, int position, long id)
    	{
    		super.onListItemClick(l, v, position, id);

    		Intent i = new Intent(FAQActivity.this, FAQInfo.class);

    		question = convert(faqStrings.get(position).question);
    		answer = convert(faqStrings.get(position).answer);
    		
    			Log.d("question", question);
    			Log.d("answer", answer);

    			i.putExtra("t", question);
    			i.putExtra("d", answer);

    			startActivity(i);
    		}
    		

    	/*
    	 * Recreate the list when the activity has finished
    	 */
    	protected void onActivityResult(int requestCode, int resultCode,
    			Intent intent)
    	{
    		super.onActivityResult(requestCode, resultCode, intent);

    		switch(requestCode)
    		{
    		case 0:
    			//dbHelp.open();
    			//fillData();
    			break;
    		}
    	}

}
