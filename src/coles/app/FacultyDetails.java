package coles.app;

import java.util.ArrayList;
import java.util.List;





import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FacultyDetails extends ListActivity {
	protected TextView employeeNameText;
    protected TextView titleText;
    protected TextView officeText;
    
    protected int employeeId;
    protected List <EmployeeAction> actions;
    protected EmployeeActionAdapter adapter;
    protected int managerId;




@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.faculty_detailes);
   
    employeeId = getIntent().getIntExtra("EMPLOYEE_ID", 0);
    SQLiteDatabase db = (new DataBaseHelper(this)).getWritableDatabase();
    Cursor cursor = db.rawQuery("SELECT emp._id, emp.firstName, emp.lastName, emp.title, emp.officePhone, emp.cellPhone, emp.email, emp.managerId, mgr.firstName managerFirstName, mgr.lastName managerLastName FROM employee emp LEFT OUTER JOIN employee mgr ON emp.managerId = mgr._id WHERE emp._id = ?",
                            new String[]{""+employeeId});

    if (cursor.getCount() == 1)
    {
            cursor.moveToFirst();
   
            employeeNameText = (TextView) findViewById(R.id.employeeName);
            employeeNameText.setText(cursor.getString(cursor.getColumnIndex("firstName")) + " " + cursor.getString(cursor.getColumnIndex("lastName")));
            
            
            
            titleText = (TextView) findViewById(R.id.title);
            titleText.setText(cursor.getString(cursor.getColumnIndex("title")));

            actions = new ArrayList<EmployeeAction>();
           
            String officePhone = cursor.getString(cursor.getColumnIndex("officePhone"));
            if (officePhone != null) {
                    actions.add(new EmployeeAction("Call office", officePhone, EmployeeAction.ACTION_CALL));
            }
            
            String cellPhone = cursor.getString(cursor.getColumnIndex("cellPhone"));
            if (cellPhone != null) {
                    actions.add(new EmployeeAction("Directions / Building / Office", cellPhone, EmployeeAction.ACTION_CALL));
                    actions.add(new EmployeeAction("SMS", cellPhone, EmployeeAction.ACTION_SMS));
            }
    
            String email = cursor.getString(cursor.getColumnIndex("email"));
            if (email != null) {
                    actions.add(new EmployeeAction("Email", email, EmployeeAction.ACTION_EMAIL));
            }
           /* String office = cursor.getString(cursor.getColumnIndex("office"));
            if (office != null) {
                    actions.add(new EmployeeAction("Office", office, EmployeeAction.ACTION_OFFICE));
            }
            /*managerId = cursor.getInt(cursor.getColumnIndex("managerId"));
	        if (managerId>0) {
	        	actions.add(new EmployeeAction("View manager", cursor.getString(cursor.getColumnIndex("managerFirstName")) + " " + cursor.getString(cursor.getColumnIndex("managerLastName")), EmployeeAction.ACTION_VIEW));
	        }

	        cursor = db.rawQuery("SELECT count(*) FROM employee WHERE managerId = ?", 
					new String[]{""+employeeId});
	        cursor.moveToFirst();
	        int count = cursor.getInt(0);
	        if (count>0) {
	        	actions.add(new EmployeeAction("View direct reports", "(" + count + ")", EmployeeAction.ACTION_REPORTS));
	        } */

	        adapter = new EmployeeActionAdapter();
	        setListAdapter(adapter);

           
    }

}


@Override
public void onListItemClick(ListView parent, View view, int position, long id) {
    
        EmployeeAction action = actions.get(position);

        
        switch (action.getType()) {

            case EmployeeAction.ACTION_CALL:  
                    Uri callUri = Uri.parse("tel:" + action.getData());  
                   Intent intent = new Intent(Intent.ACTION_CALL, callUri); 
                startActivity(intent);
                    break;

            case EmployeeAction.ACTION_EMAIL:  
            Intent intent2 = new Intent(Intent.ACTION_SEND);
            intent2.setType("plain/text");
            intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{action.getData()});
            startActivity(intent2);
            break;
            
            case EmployeeAction.ACTION_SMS:  
                    Uri smsUri = Uri.parse("sms:" + action.getData());  
                    Intent intent3 = new Intent(Intent.ACTION_VIEW, smsUri); 
                startActivity(intent3);
                    break;
           

            case EmployeeAction.ACTION_VIEW:  
                    Intent intent4 = new Intent(this, FacultyDetails.class);
                    intent4.putExtra("EMPLOYEE_ID", managerId);
                    startActivity(intent4);
                    break;
        }
}    

class EmployeeActionAdapter extends ArrayAdapter<EmployeeAction> {

    EmployeeActionAdapter() {
                    super(FacultyDetails.this, R.layout.action_list_item, actions);
            }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            EmployeeAction action = actions.get(position);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.action_list_item, parent, false);
            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText(action.getLabel());
            TextView data = (TextView) view.findViewById(R.id.data);
            data.setText(action.getData());
            return view;
    }
    
}

}





