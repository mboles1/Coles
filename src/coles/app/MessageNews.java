package coles.app;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ImageButton;

/**
 * 	Changed the loadFeed method to just parse and not interact with GUI.
 * 	Parsing is now done as a background thread.
 *	After parsing is done, ArrayList is returned and passed to setList method.
 *	SetList method is called after background thread is done executing.
 *	A progress dialog will be on the screen during the processing to let the user know what the activity 
 *	is loading.  After processing is complete, dialog is dismissed.
 */



public class MessageNews extends ListActivity {
	
	private List<Message> messages;
 
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.newsfeed);
        //loadFeed(ParserType.ANDROID_SAX);
        
        Background background = new Background();
        background.execute();
        
        ImageButton home = (ImageButton) findViewById(R.id.homeButton);
		home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myInt = new Intent(getApplicationContext(), main.class);
				startActivity(myInt);
			}
		});
     

    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, ParserType.ANDROID_SAX.ordinal(), 
				ParserType.ANDROID_SAX.ordinal(), R.string.android_sax);
		menu.add(Menu.NONE, ParserType.SAX.ordinal(), ParserType.SAX.ordinal(),
				R.string.sax);
		menu.add(Menu.NONE, ParserType.DOM.ordinal(), ParserType.DOM.ordinal(), 
				R.string.dom);
		menu.add(Menu.NONE, ParserType.XML_PULL.ordinal(), 
				ParserType.XML_PULL.ordinal(), R.string.pull);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		ParserType type = ParserType.values()[item.getItemId()];
		ArrayAdapter<String> adapter =
			(ArrayAdapter<String>) this.getListAdapter();
		if (adapter.getCount() > 0){
			adapter.clear();
		}
		this.loadFeed(type);
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent viewMessage = new Intent(Intent.ACTION_VIEW, 
				Uri.parse(messages.get(position).getLink().toExternalForm()));
		this.startActivity(viewMessage);
	}

	
	
	private ArrayList<String> loadFeed(ParserType type){
    	try{
    		Log.i("AndroidNews", "ParserType="+type.name());
	    	FeedParser parser = FeedParserFactory.getParser(type);
	    	long start = System.currentTimeMillis();
	    	messages = parser.parse();
	    	long duration = System.currentTimeMillis() - start;
	    	Log.i("AndroidNews", "Parser duration=" + duration);
	    	String xml = writeXml();
	    	Log.i("AndroidNews", xml);
	    	
	    	
	    	ArrayList<String> titles = new ArrayList<String>(messages.size());
	    	for (Message msg : messages){
	    		titles.add(msg.getTitle());
	    	}
	    	
	    	return titles;
	    	
	    	
    	} catch (Throwable t){
    		Log.e("AndroidNews",t.getMessage(),t);
    		ArrayList<String> error = new ArrayList<String>();
    		return error;
    	}
    }
    
	private String writeXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "messages");
			serializer.attribute("", "number", String.valueOf(messages.size()));
			for (Message msg: messages){
				serializer.startTag("", "message");
				serializer.attribute("", "date", msg.getDate());
				serializer.startTag("", "title");
				serializer.text(msg.getTitle());
				serializer.endTag("", "title");
				serializer.startTag("", "url");
				serializer.text(msg.getLink().toExternalForm());
				serializer.endTag("", "url");
				serializer.startTag("", "body");
				serializer.text(msg.getDescription());
				serializer.endTag("", "body");
				serializer.endTag("", "message");
			}
			serializer.endTag("", "messages");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	private void setList(ArrayList<String> listMessage)
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, listMessage);
	    this.setListAdapter(adapter);
	}
	
	private class Background extends AsyncTask<Void, Void, ArrayList<String>>
	{
		private ProgressDialog progress;
		
		@Override
		protected void onPreExecute()
		{
			progress = ProgressDialog.show(MessageNews.this, "Loading News Feed", "Loading");
		}
		
		
		@Override
		protected ArrayList<String> doInBackground(Void... params) 
		{
			return loadFeed(ParserType.ANDROID_SAX);
		}

		@Override
		protected void onPostExecute(ArrayList<String> s)
		{
			super.onPostExecute(s);
			
			if(progress.isShowing())
			{
				progress.dismiss();
			}
			
			setList(s);
			
		}
		
		
	}
	
}