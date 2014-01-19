package coles.app;

import java.util.List;
import xmltosqlite.Text2;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 
 *         MenuActivity displays a menu The menu items come from the static
 *         getMenu() method of the MysqlText class
 */
public class MenuActivity extends Activity implements OnClickListener {

	static List<Text2> menu;
	private final Context context = this;
	final boolean SOURCE_IS_MYSQL = true;
	final boolean SOURCE_IS_SQLLITE = false;
	private TextView subtitle;
	Text2 t;
	private String link = "";
	private int count;
	private LinearLayout m_layout;
	public static boolean undergrad;
	public static boolean grad;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.realundergrad);

		subtitle = (TextView) findViewById(R.id.subtitle);
		ImageButton home = (ImageButton) findViewById(R.id.homeBTN);
		home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent goHome = new Intent(getApplicationContext(), main.class);
				startActivity(goHome);
			}
		});
		findViewById(R.id.scrollView1);
		findViewById(R.id.buttonll);
		m_layout = (LinearLayout) this.findViewById(R.id.LinearLayout1);
		m_layout.setPadding(20, 140, 20, 0);

		int text_id = getIntent().getExtras().getInt("text_id");
		int menu_no = getIntent().getExtras().getInt("menu_no");
		String heading = getIntent().getExtras().getString("heading_item");
		String textBody = " "; 
		textBody = getIntent().getExtras().getString("text_body");
		link="";
		link = getIntent().getExtras().getString("link");
		

		////////////////////////////////////////////////////////////////////////////

		Log.d("MenuActivity", "text_id is " + Integer.toString(text_id) + " and menu_no is " + Integer.toString(menu_no));
		if(text_id != 1)
			subtitle.setText(heading);

		////////////////////////////////////////////////////////////////////////////

		//		// test size of Text databasesqLiteTableSize
		//		int sqLiteTableSize = MysqlText.sqLiteTableSize(context);
		//		Log.d("MenuActivity", "sqLiteTableSize = " + sqLiteTableSize);
		//		int mySqlTableSize = MysqlText.mySqlTableSize();
		//		Log.d("MenuActivity", "mySqlTableSize = " + mySqlTableSize);
		//		

		//		boolean reloadTextTable = false;
		//		if (sqLiteTableSize < 30) {
		//			reloadTextTable = true;
		//		}
		//		if (sqLiteTableSize != mySqlTableSize) {
		//			reloadTextTable = true;
		//		}
		//		if (reloadTextTable) {
		//			MysqlText.resetLoadFlag();
		//			if (mySqlTableSize > 30) {
		//				//pull from server
		//				(new MysqlText()).loadSqlLiteTextTable(getApplicationContext(),
		//						SOURCE_IS_MYSQL);
		//			} else {
		//				//pull from local
		//				(new MysqlText()).loadSqlLiteTextTable(getApplicationContext(),
		//						SOURCE_IS_SQLLITE);
		//			}
		//		}


		menu = MysqlText.getMenu(context, text_id);
		sortMenuList(menu);
		count = menu.size();
		//int section_count = count;

		// if count == 0 this is nothing to display
		// and a decision is needed for how the app should handle it
		// I suggest jump to text_id of 0
		if (menu_no == 1) {
			TextView title = (TextView) findViewById(R.id.title);
			title.setText("Academic Programs");
			subtitle.setText("Coles College of Business");

		}
		if(undergrad){
			TextView title = (TextView) findViewById(R.id.title);
			title.setText("Undergraduate Programs");
//			if (menu_no == 1) {
//				title = (TextView) findViewById(R.id.title);
//				title.setText("Academic Programs");
//
//			}
		}
		else if(grad){
			TextView title = (TextView) findViewById(R.id.title);
			title.setText("Graduate Programs");
//			if (menu_no == 1) {
//				title = (TextView) findViewById(R.id.title);
//				title.setText("Academic Programs");
//
//			}
		}
		
		if (text_id == 2) {
			TextView title = (TextView) findViewById(R.id.title);
			title.setText("News and Events");
			subtitle.setText("Coles College of Business");
		}
		if (text_id == 4) {
			TextView title = (TextView) findViewById(R.id.title);
			title.setText("Maps and Directions");
			subtitle.setText("Coles College of Business");
		}

		if(count<1)
		{
			Intent i = new Intent(getApplicationContext(), WebViewActivity.class);
			Bundle b = new Bundle();
			b.putString("theText", textBody);
			b.putString("heading", heading);
			b.putString("link", link);
			b.putInt("text_id", text_id);
			b.putInt("count", count);
			b.putInt("menu_no", menu_no);
			i.putExtras(b);
			startActivity(i);
			finish();
		}
		// if count == 1 there is no choice because there is only one option
		// and a decision is needed for how the app should handle it
		// I suggest that a special no-choice Activity be displayed

		// if count > 1 then a menu of choices can be displayed

		// loop through the array list
		// test the properties of each element and decide what to do with it
		for (int i = 0; i < count; i++) {

			if (menu.get(i).link.length() > 0) {
			}

			if (count > 0) {
				final TextView tv = formatMenuItem(count, i);
				m_layout.addView(tv, new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));
				tv.setOnClickListener(my_click_listener);			

			} else {
				final TextView tv = new TextView(MenuActivity.this);
				tv.setText(menu.get(i).heading);
				tv.setId(79999 + i);
				tv.setTextSize(20);
				tv.setGravity(Gravity.CENTER);
				m_layout.addView(tv, new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));
			}

		}
	}

	private String convert(String str) 
	{ 
		str = str.replace("&", " & "); 
		str = str.replace("&gt;", ">"); 
		str = str.replace("&lt;", "<"); 
		str = str.replace("&quot;", "\""); 
		str = str.replace("&#039;", "'");
		str = str.replace("\\r\\n", "");
		str = str.replace("<br/>", "\n");
		str = str.replace("<br>", "");
		str = str.replace("<ul>", "");
		str = str.replace("</ul>", "");
		str = str.replace("<strong>", "");
		str = str.replace("</strong>", "");
		str = str.replace("<u>", "");
		str = str.replace("</u>", "");
		str = str.replace("<p>", "");
		str = str.replace("</p>", "\n\n");																														
		str = str.replace("<br/>", "");
		str = str.replace("</li>", "\n");
		str = str.replace("\\'", "'");
		str = str.replace("•", Html.fromHtml("&#8226"));
		str = str.replace("<li>", Html.fromHtml("&#8226"));
		str = str.replace("\"Business-Undecided\"", " \"Business-Undecided\" ");
		str = str.replace("\"Academic Programs & Majors\"", " \"Academic Programs & Majors\" ");
		str = str.replace("\"Best Buys\"", " \"Best Buys\" ");
		str = str.replace("\"ideal\"", " \"ideal\" ");
		str = str.replace("\"More Info\"", " \"More Info\" ");
		str = str.replace("fair-style", "\"fair-style\"");
		str = str.replace("\"Quick Links\"", " \"Quick Links\" ");
		str = str.replace("\"online only\"", " \"online only\" ");
		str = str.replace("\"admitted to Coles\"", " \"admitted to Coles\" ");
		str = str.replace("\"Admission Checklist\"", " \"Quick Links\" ");
		str = str.replace("\"Sophomore GPA Calculator\"", " \"Sophomore GPA Calculator\" ");
		str = str.replace("\"matriculation date\"", " \"matriculation date\" ");
		return str; 
	}

	public void sortMenuList(List<Text2> menuList)
	{
		boolean needAnotherPass = true;
		
		for(int h = 1; h <= menuList.size() && needAnotherPass; h++)
		{
			needAnotherPass = false;
			
			for(int i = 0; i < menuList.size() - h; i++)
			{
				if(menuList.get(i).item_order > menuList.get(i + 1).item_order)
				{
					Text2 temp = menuList.get(i);
					
					Text2 textObject = menuList.get(i + 1);
					menuList.set(i, textObject);
					
					menuList.set(i + 1, temp);
					
					needAnotherPass = true;
				}
			}
		}
	}

	private TextView formatMenuItem(int count, int i) {
		final TextView tv = new TextView(MenuActivity.this);
		tv.setId(i);
		tv.setTag(menu.get(i));
		tv.setText(convert(menu.get(i).heading));
		tv.setTextSize(20);
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(Color.BLACK);
		tv.setBackgroundResource(R.drawable.menu);
		return tv;
	}

	private OnClickListener my_click_listener = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			Text2 tagText = (Text2) v.getTag();
			Log.d("MenuActivity", "click " + 			v.getId());
			Log.d("MenuActivity", "text_id " + 			tagText.id);
			Log.d("MenuActivity", "menu_no " + 			tagText.menu_no);
			Log.d("MenuActivity", "item_order " + 		tagText.item_order);
			Log.d("MenuActivity", "(String)heading " + 	tagText.heading);
			Log.d("MenuActivity", "(String)body " + 	tagText.body);
			Log.d("MenuActivity", "(String)link " + 	tagText.link);
			tagText.setHeading(							tagText.getHeading());


			if (tagText.id == 5) {
				(new MysqlText()).scanSqlLiteTextTable(context);
			}

			if (tagText.id == 7) {
				(new MysqlText()).scanSqlLiteTextTable(context);
			}

			if (tagText.id == 8) {
				(new MysqlText()).scanSqlLiteTextTable(context);
			}

			Log.d("MenuActivity", "value of getTag link = " + tagText.link);
			// set the MenuActivity class as the overall default next activity

			Intent i = new Intent(getApplicationContext(), MenuActivity.class);
			// direct to the appropriate activity if the item is NOT a submenu

			if (tagText.heading.equals("FAQs")) 
			{
				if(!isNetworkAvailable())
				{
					popUpAlert("FAQs");
					i = new Intent(getApplication(), main.class);
				}
				else if(isNetworkAvailable())
				{
					i = new Intent(getApplicationContext(), FAQActivity.class);
				}
			}
			if (tagText.heading.equals("News")) {
				i = new Intent(getApplicationContext(), MessageNews.class);
			}
			if (tagText.heading.equals("Events")) {
				i = new Intent(getApplicationContext(), MessageEvents.class);
			}
			if (tagText.heading.equals("Undergraduate")) {
				undergrad=true;
				grad=false;
			}
			if (tagText.heading.equals("Graduate")) {
				grad=true;
				undergrad=false;
			}
			if (tagText.heading.equals("Admission Checklist")) {
				i = new Intent(getApplicationContext(), Applyp4Activity.class);
			}
			if (tagText.heading.equals("GPA Calculator")) {
				i = new Intent(getApplicationContext(), Applyp3Activity.class);
			}
			if (tagText.heading.equals("Advising Traffic")) {
				i = new Intent(getApplicationContext(),
						TrafficClassActivity.class);
			}
			if (tagText.heading.equals("Registration")) {
				if(!isNetworkAvailable())
				{
					popUpAlert("Registration");
					i = new Intent(getApplication(), main.class);
				}
				else if(isNetworkAvailable())
				{
					i = new Intent(getApplicationContext(), Registration.class);
				}
			}
			if (tagText.heading.equals("Walk to Room")) {
				i = new Intent(getApplicationContext(), WalkToRmActivity.class);
			}
			if (tagText.heading.equals("Walk to Building")) {
				i = new Intent(getApplicationContext(),WalkToBldgActivity.class);
			}
			if (tagText.heading.equals("KSU Library")) {
				Uri uri = Uri.parse("http://www.kennesaw.edu/library/mobile");
				i = new Intent(Intent.ACTION_VIEW, uri);
			}
			if (tagText.heading.equals("KSU Registrar")) {
				Uri uri = Uri.parse("https://web.kennesaw.edu/registrar/");
				i = new Intent(Intent.ACTION_VIEW, uri);
			}


			i.putExtra("text_id", 			tagText.id);
			i.putExtra("menu_no", 			tagText.getMenu_no());
			i.putExtra("item_order", 		tagText.getItem_order());
			i.putExtra("heading_item", 		tagText.getHeading());
			i.putExtra("text_body", 		tagText.getBody());
			i.putExtra("link", 				tagText.getLink());
			Log.d("What", Integer.toString(	tagText.id));
			startActivity(i);
		}
	};

	@Override
	public void onClick(View arg0) {
	}
	
	private boolean isNetworkAvailable()
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
		if(activeNetworkInfo == null)
		{
			return false;
		}
		else
		{
			return activeNetworkInfo.isConnected();
		}
	}
	
	private void popUpAlert(String message) 
	{
		AlertDialog.Builder popUpBuild = new AlertDialog.Builder(this);

		popUpBuild.setMessage(message + " is not available offline.");
		popUpBuild.setCancelable(false);
		popUpBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});

		final AlertDialog popUpAlert = popUpBuild.create();

		popUpAlert.show();
	}
}
