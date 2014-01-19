package coles.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Integer;

/**
 * 
 * This is the GPA Worksheet activity.
 * @author Wayne
 *
 */
public class Applyp3Activity extends Activity {
	private static final int NUMBER_OF_COURSES = 8;
	private static final int NUMBER_OF_PLUS_MINUS_BUTTONS = 16;
	private static final int NUMBER_OF_RADIO_BUTTONS = 35;
	private static final String GPA_STATE = "GpaState";
	private SharedPreferences gpa_state;
	private int catalogID;
	private int [] qualityPts = new int[NUMBER_OF_COURSES];
	private int [] credHrs = new int[NUMBER_OF_COURSES];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.applyp3);
		MysqlText text = new MysqlText();
		MysqlText.loadText();


	    // create some local variables for convenience
		String strIdx = "";
	    
		final RadioButton btnArray[] = {
				(RadioButton) findViewById(R.id.catTerm1),
				(RadioButton) findViewById(R.id.catTerm2),
				(RadioButton) findViewById(R.id.catTerm3),
				(RadioButton) findViewById(R.id.grade_A_02),
				(RadioButton) findViewById(R.id.grade_B_02),
				(RadioButton) findViewById(R.id.grade_C_02),
				(RadioButton) findViewById(R.id.grade_NA_02),
				(RadioButton) findViewById(R.id.grade_A_03),
				(RadioButton) findViewById(R.id.grade_B_03),
				(RadioButton) findViewById(R.id.grade_C_03),
				(RadioButton) findViewById(R.id.grade_NA_03),
				(RadioButton) findViewById(R.id.grade_A_04),
				(RadioButton) findViewById(R.id.grade_B_04),
				(RadioButton) findViewById(R.id.grade_C_04),
				(RadioButton) findViewById(R.id.grade_NA_04),
				(RadioButton) findViewById(R.id.grade_A_05),
				(RadioButton) findViewById(R.id.grade_B_05),
				(RadioButton) findViewById(R.id.grade_C_05),
				(RadioButton) findViewById(R.id.grade_NA_05),
				(RadioButton) findViewById(R.id.grade_A_06),
				(RadioButton) findViewById(R.id.grade_B_06),
				(RadioButton) findViewById(R.id.grade_C_06),
				(RadioButton) findViewById(R.id.grade_NA_06),
				(RadioButton) findViewById(R.id.grade_A_07),
				(RadioButton) findViewById(R.id.grade_B_07),
				(RadioButton) findViewById(R.id.grade_C_07),
				(RadioButton) findViewById(R.id.grade_NA_07),
				(RadioButton) findViewById(R.id.grade_A_08),
				(RadioButton) findViewById(R.id.grade_B_08),
				(RadioButton) findViewById(R.id.grade_C_08),
				(RadioButton) findViewById(R.id.grade_NA_08),
				(RadioButton) findViewById(R.id.grade_A_09),
				(RadioButton) findViewById(R.id.grade_B_09),
				(RadioButton) findViewById(R.id.grade_C_09),
				(RadioButton) findViewById(R.id.grade_NA_09)
		};
		
		btnArray[0].setVisibility(View.GONE);
		
		final Button plusMinusArray[] = {
				(Button) findViewById(R.id.crHrPlus02),
				(Button) findViewById(R.id.crHrMinus02),
				(Button) findViewById(R.id.crHrPlus03),
				(Button) findViewById(R.id.crHrMinus03),
				(Button) findViewById(R.id.crHrPlus04),
				(Button) findViewById(R.id.crHrMinus04),
				(Button) findViewById(R.id.crHrPlus05),
				(Button) findViewById(R.id.crHrMinus05),
				(Button) findViewById(R.id.crHrPlus06),
				(Button) findViewById(R.id.crHrMinus06),
				(Button) findViewById(R.id.crHrPlus07),
				(Button) findViewById(R.id.crHrMinus07),
				(Button) findViewById(R.id.crHrPlus08),
				(Button) findViewById(R.id.crHrMinus08),
				(Button) findViewById(R.id.crHrPlus09),
				(Button) findViewById(R.id.crHrMinus09)
		};

		// initialize listeners and private class variables
	    gpa_state = getSharedPreferences(GPA_STATE, MODE_PRIVATE);
	    catalogID = gpa_state.getInt("catalogID", 3);
		btnArray[catalogID - 1].setChecked(true);
		for (int i = 0; i < btnArray.length; i++)
			btnArray[i].setOnClickListener(radio_listener);
		for (int i = 0; i < NUMBER_OF_COURSES; i++)
		{
			plusMinusArray[i * 2].setOnClickListener(plusMinus_listener); 
			plusMinusArray[i * 2 + 1].setOnClickListener(plusMinus_listener); 
			strIdx = "qualityPts" + String.valueOf(i);
			qualityPts[i] = gpa_state.getInt(strIdx, 0); // valid values are 0, 2, 3 and 4
			strIdx = "credHrs" + String.valueOf(i);
			credHrs[i] = gpa_state.getInt(strIdx, 0); // controlled with the plus/minus buttons
			int grade = 3; // corresponds to NA
			if (qualityPts[i] > 0) { // range 2 - 4
				grade = 4 - qualityPts[i]; // range 0 - 2
			}
			int crsIdx = (i * 4) + grade + 3; // range 3 - 27
//			if(crsIdx<0 || crsIdx>=btnArray.length){
//				crsIdx=btnArray.length;
//			}
			Log.d("Index is: ", Integer.toString(crsIdx));
			btnArray[crsIdx].setChecked(true);
		}
    	refreshGUI();
	}
    
    private OnClickListener radio_listener = new OnClickListener() 
    {
        @Override
		public void onClick(View v) 
        {
			updateState(v.getId());
	    	refreshGUI();
    	}
    };
    
    private void updateState(int id)
    {
    	int courseNum = courseNumber(id); // 1 - 9
		int qualPts = 0;
		int courseIdx = 0;
		String strIdx = "";
    	if (courseNum < 2)
    	{
			catalogID = catalogTerm(id);
			setStateVar("catalogID", catalogID);
    	}
    	else
    	{
    		qualPts = qualityPoints(id); // valid values are 0, 2, 3 and 4
    		courseIdx = courseNum - 2;
	    	strIdx = "qualityPts" + String.valueOf(courseIdx);
			setStateVar(strIdx, qualPts);
			qualityPts[courseIdx] = qualPts;
    	}
	}

    private int catalogTerm(int i)
    {
    	int result = 0;
    	switch (i)
    	{
//		case R.id.catTerm1:
//			result = 1;
//			break;
		case R.id.catTerm2:
			result = 2;
			break;
		case R.id.catTerm3:
			result = 3;
			break;
    	}
    	return result;
    }
    
    private void updateCreditHours(int i)
    {
    	switch (i)
    	{
		case R.id.crHrPlus02:
			credHrs[0] = credHrs[0] + 1;
			setStateVar("credHrs0", credHrs[0]);
			break;
		case R.id.crHrMinus02:
			if (credHrs[0] > 0) {
				credHrs[0] = credHrs[0] - 1;
			}
			setStateVar("credHrs0", credHrs[0]);
			break;
		case R.id.crHrPlus03:
			credHrs[1] = credHrs[1] + 1;
			setStateVar("credHrs1", credHrs[1]);
			break;
		case R.id.crHrMinus03:
			if (credHrs[1] > 0) {
				credHrs[1] = credHrs[1] - 1;
			}
			setStateVar("credHrs1", credHrs[1]);
			break;
		case R.id.crHrPlus04:
			credHrs[2] = credHrs[2] + 1;
			setStateVar("credHrs2", credHrs[2]);
			break;
		case R.id.crHrMinus04:
			if (credHrs[2] > 0) {
				credHrs[2] = credHrs[2] - 1;
			}
			setStateVar("credHrs2", credHrs[2]);
			break;
		case R.id.crHrPlus05:
			credHrs[3] = credHrs[3] + 1;
			setStateVar("credHrs3", credHrs[3]);
			break;
		case R.id.crHrMinus05:
			if (credHrs[3] > 0) {
				credHrs[3] = credHrs[3] - 1;
			}
			setStateVar("credHrs3", credHrs[3]);
			break;
		case R.id.crHrPlus06:
			credHrs[4] = credHrs[4] + 1;
			setStateVar("credHrs4", credHrs[4]);
			break;
		case R.id.crHrMinus06:
			if (credHrs[4] > 0) {
				credHrs[4] = credHrs[4] - 1;
			}
			setStateVar("credHrs4", credHrs[4]);
			break;
		case R.id.crHrPlus07:
			credHrs[5] = credHrs[5] + 1;
			setStateVar("credHrs5", credHrs[5]);
			break;
		case R.id.crHrMinus07:
			if (credHrs[5] > 0) {
				credHrs[5] = credHrs[5] - 1;
			}
			setStateVar("credHrs5", credHrs[5]);
			break;
		case R.id.crHrPlus08:
			credHrs[6] = credHrs[6] + 1;
			setStateVar("credHrs6", credHrs[6]);
			break;
		case R.id.crHrMinus08:
			if (credHrs[6] > 0) {
				credHrs[6] = credHrs[6] - 1;
			}
			setStateVar("credHrs6", credHrs[6]);
			break;
		case R.id.crHrPlus09:
			credHrs[7] = credHrs[7] + 1;
			setStateVar("credHrs7", credHrs[7]);
			break;
		case R.id.crHrMinus09:
			if (credHrs[7] > 0) {
				credHrs[7] = credHrs[7] - 1;
			}
			setStateVar("credHrs7", credHrs[7]);
			break;
    	}
    }
    
	private void refreshGUI() {
    	boolean allAreNonZero = true;
    	int qualityPoints = 0;
    	int totalQualityPoints = 0;
    	int totalCreditHours = 0;
    	int coursesConsidered = 0;
    	double gpa = 0;
    	double required_gpa = 0;

		for (int courseNum = 0; courseNum < NUMBER_OF_COURSES; courseNum++) {
			smallMessage(courseNum);
		}

    	CharSequence result = "";
    	switch (catalogID)
    	{
    	case 1:
    		coursesConsidered = 7;
        	required_gpa = 2.71;
			result = getString(R.string.applyCatTerm1);
    		break;
    	case 2:
    		coursesConsidered = 8;
        	required_gpa = 2.875;
			result = getString(R.string.applyCatTerm2);
    		break;
    	case 3:
    		coursesConsidered = 8;
        	required_gpa = 3.0;
			result = getString(R.string.applyCatTerm3);
    		break;
    	}

    	if (coursesConsidered > 7) {
			TextView tv = (TextView) findViewById(R.id.GPAView09);
			tv.setVisibility(View.VISIBLE);
    	} else {
			TextView tv = (TextView) findViewById(R.id.GPAView09);
			tv.setVisibility(View.GONE);
    	}
    	
    	for (int i = 0; i < coursesConsidered; i++)
    	{
    		qualityPoints = qualityPts [i] * credHrs [i];
    		if (qualityPoints < 1) {
    			allAreNonZero = false;
    		}
    		totalQualityPoints = totalQualityPoints + qualityPoints;
        	totalCreditHours = totalCreditHours + credHrs [i];
    	}

    	gpa = (double) totalQualityPoints / (double) totalCreditHours;
    	String gpa_msg = " meets the required ";
    	if (gpa < required_gpa)
    		gpa_msg = " does not meet the required ";
		gpa_msg = String.format("%.02f %s %.02f", gpa, gpa_msg, required_gpa);
		if (allAreNonZero) {
			result = result + 
    			"\nTotal credit hours entered: " + totalCreditHours +
    			".\nTotal quality points entered: " + totalQualityPoints +
    			".\nGPA: " + String.format("%.02f",	gpa) +
    			".\n" + gpa_msg + ".";
		} else {
			result = result + 
	    			"\nEach course must have credit hours and a grade.";
		}
//	    queToast(result.toString());
		TextView tot = (TextView) findViewById(R.id.GPAgrandMessage);
		tot.setText(result);
    }
    
	private void smallMessage(int courseNum) {
    	int chID = 0;
		switch (courseNum)
    	{
		case 0:
			chID = R.id.GPAcredhrs02;
			break;
		case 1:
			chID = R.id.GPAcredhrs03;
			break;
		case 2:
			chID = R.id.GPAcredhrs04;
			break;
		case 3:
			chID = R.id.GPAcredhrs05;
			break;
		case 4:
			chID = R.id.GPAcredhrs06;
			break;
		case 5:
			chID = R.id.GPAcredhrs07;
			break;
		case 6:
			chID = R.id.GPAcredhrs08;
			break;
		case 7:
			chID = R.id.GPAcredhrs09;
			break;
    	}
    	if (chID != 0)
    	{
    		TextView ch = (TextView) findViewById(chID);
    		ch.setText(" " + credHrs[courseNum] + " ");
    	}
	}
    
    private int qualityPoints(int i)
    {
    	int result = 0;
    	if (i == R.id.grade_A_02 ||
    		i == R.id.grade_A_03 ||
    		i == R.id.grade_A_04 ||
    		i == R.id.grade_A_05 ||
    		i == R.id.grade_A_06 ||
    		i == R.id.grade_A_07 ||
    		i == R.id.grade_A_08 ||
    		i == R.id.grade_A_09)
    	{
    		result = 4;
    	}
    	if (i == R.id.grade_B_02 ||
    		i == R.id.grade_B_03 ||
    		i == R.id.grade_B_04 ||
    		i == R.id.grade_B_05 ||
    		i == R.id.grade_B_06 ||
    		i == R.id.grade_B_07 ||
    		i == R.id.grade_B_08 ||
    		i == R.id.grade_B_09)
    	{
    		result = 3;
    	}
    	if (i == R.id.grade_C_02 ||
    		i == R.id.grade_C_03 ||
    		i == R.id.grade_C_04 ||
    		i == R.id.grade_C_05 ||
    		i == R.id.grade_C_06 ||
    		i == R.id.grade_C_07 ||
    		i == R.id.grade_C_08 ||
    		i == R.id.grade_C_09)
    	{
    		result = 2;
    	}
    	return result;
    }
    
    private int courseNumber(int i)
    {
    	int result = 0;
    	if (i == R.id.catTerm2 ||
    		i == R.id.catTerm3)
    	{
    		result = 1;
    	}
    	if (i == R.id.grade_A_02 ||
    		i == R.id.grade_B_02 ||
    		i == R.id.grade_C_02 ||
    		i == R.id.grade_NA_02)
    	{
    		result = 2;
    	}
    	if (i == R.id.grade_A_03 ||
    		i == R.id.grade_B_03 ||
    		i == R.id.grade_C_03 ||
    		i == R.id.grade_NA_03)
    	{
    		result = 3;
    	}
    	if (i == R.id.grade_A_04 ||
    		i == R.id.grade_B_04 ||
    		i == R.id.grade_C_04 ||
    		i == R.id.grade_NA_04)
    	{
    		result = 4;
    	}
    	if (i == R.id.grade_A_05 ||
    		i == R.id.grade_B_05 ||
    		i == R.id.grade_C_05 ||
    		i == R.id.grade_NA_05)
    	{
    		result = 5;
    	}
    	if (i == R.id.grade_A_06 ||
    		i == R.id.grade_B_06 ||
    		i == R.id.grade_C_06 ||
    		i == R.id.grade_NA_06)
    	{
    		result = 6;
    	}
    	if (i == R.id.grade_A_07 ||
    		i == R.id.grade_B_07 ||
    		i == R.id.grade_C_07 ||
    		i == R.id.grade_NA_07)
    	{
    		result = 7;
    	}
    	if (i == R.id.grade_A_08 ||
    		i == R.id.grade_B_08 ||
    		i == R.id.grade_C_08 ||
    		i == R.id.grade_NA_08)
    	{
    		result = 8;
    	}
    	if (i == R.id.grade_A_09 ||
    		i == R.id.grade_B_09 ||
    		i == R.id.grade_C_09 ||
    		i == R.id.grade_NA_09)
    	{
    		result = 9;
    	}
    	return result;
    }

	private final <T> void setStateVar(final String key, final T value) {
		SharedPreferences.Editor edit = gpa_state.edit();
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
    
    private OnClickListener plusMinus_listener = new OnClickListener() 
    {
        @Override
		public void onClick(View v) 
        {
        	updateCreditHours(v.getId());
        	refreshGUI();
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
