package coles.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;

public class EditEntry extends Activity
{
	private TextView tvFirstName, tvTitle1, tvTitle2, tvDepartment, tvOffice, tvPhone, tvEmail;
	private String fName, lName, mName, title1, title2, department, office, phone, email, bio, web, the_comma;

	private TableRow title2Row, title1Row, departmentRow, officeRow, phoneRow, emailRow;

	@Override
	public void onCreate(Bundle SavedInstanceState)
	{
		super.onCreate(SavedInstanceState);
		setContentView(R.layout.edit_entry);

		populateFields();

	}

	private void populateFields()
	{
		//Setting up the views
		tvFirstName = (TextView)findViewById(R.id.first);
		tvTitle1 = (TextView)findViewById(R.id.title1);
		tvTitle2 = (TextView)findViewById(R.id.title2);
		tvDepartment = (TextView)findViewById(R.id.depart);
		tvOffice = (TextView)findViewById(R.id.office);
		tvPhone = (TextView)findViewById(R.id.phone);
		tvEmail = (TextView)findViewById(R.id.email);

		title2Row = (TableRow)findViewById(R.id.title2_row);
		title1Row = (TableRow)findViewById(R.id.title1_row);
		departmentRow = (TableRow)findViewById(R.id.department_row);
		officeRow = (TableRow)findViewById(R.id.office_row);
		phoneRow = (TableRow)findViewById(R.id.phone_row);
		emailRow = (TableRow)findViewById(R.id.email_row);

		//Getting the extras from the intent
		//Bundle b = getIntent().getExtras();
		fName = getIntent().getExtras().getString("first");
		lName = getIntent().getExtras().getString("last");
		mName = getIntent().getExtras().getString("middle");
		title1 = getIntent().getExtras().getString("t1");
		title2 = getIntent().getExtras().getString("t2");
		department = getIntent().getExtras().getString("department");
		office = getIntent().getExtras().getString("office");
		phone = getIntent().getExtras().getString("phone");
		email = getIntent().getExtras().getString("email");
		
		//SetTitle
		setTitle("Coles Directory");
		
		//Combining extras and the views
		tvFirstName.setText(lName + ", " + fName + " " + mName);
		tvTitle1.setText(title1);
		tvTitle2.setText(title2);
		tvDepartment.setText(department);
		tvOffice.setText("Office #: " + office);
		tvPhone.setText(phone);
		tvEmail.setText(email);

		/*
		 * Figure out a way to check if fields are filled in or not 
		 * and depending on if they are filled in or not, set the visibility accordingly
		 */

		
		if(tvTitle1.getText().toString().equals(""))
		{
			title1Row.setVisibility(View.GONE);
		}
		
		if(tvTitle2.getText().toString().equals(""))
		{
			title2Row.setVisibility(View.GONE);
		}

		if(tvDepartment.getText().toString().equals(""))
		{
			departmentRow.setVisibility(View.GONE);
		}
		
		if(tvOffice.getText().toString().equals(""))
		{
			officeRow.setVisibility(View.GONE);
		}
		
		if(tvPhone.getText().toString().equals(""))
		{
			phoneRow.setVisibility(View.GONE);
		}
		
		if(tvEmail.getText().toString().equals(""))
		{
			emailRow.setVisibility(View.GONE);
		}
		
		tvPhone.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				try 
				{
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					//This works so you can use it for pulling the phone number from the database
					//String phoneTest = "1234567890";
					callIntent.setData(Uri.parse("tel:" + phone));
					startActivity(callIntent);
				} 
				catch (ActivityNotFoundException e)
				{
					Log.e("helloandroid dialing example", "Call failed", e);
				}
			}

		});
	}


}





