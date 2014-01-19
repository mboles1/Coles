package coles.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class FAQInfo extends Activity
{
	private TextView question, answer;
	private String questionString;
	private String answerString;
	private ScrollView faqScrollView;
	
	
	private Bundle extras;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_info_layout);
		
		extras = getIntent().getExtras();
		
		faqScrollView = (ScrollView)findViewById(R.id.registrationScrollView);
		faqScrollView.setScrollbarFadingEnabled(false);
		
		question = (TextView)findViewById(R.id.registrationTitleTV);
		question.setText("question Should be Here");
		answer = (TextView)findViewById(R.id.registrationDescriptionTV);
		answer.setText("answer should be here");
		
		questionString = "question Should be Here";
		answerString = "answer should be here";
		
		questionString = extras.getString("t");
		answerString = extras.getString("d");
		
		question.setText(questionString);
		answer.setText(answerString);
		Log.d("newquestion", questionString);
		Log.d("newanswer", answerString);
		
		Button moreInfo = (Button) findViewById(R.id.moreRegistrationInfo);
		
		moreInfo.setVisibility(View.GONE);
	}
	
}
