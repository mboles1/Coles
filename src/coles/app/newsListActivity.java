package coles.app;

//import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ListView;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;

public class newsListActivity extends ListActivity 
{

	public final String RSSFEEDOFCHOICE = "http://coles.kennesaw.edu/newsfeed.xml";
	
	public final String tag = "RSSReader";
	private RSSFeed feed = null;
	
	/** Called when the activity is first created. */
	 @Override	
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.newsrss);
        
        // go get our feed!
       // feed = getFeed(RSSFEEDOFCHOICE);

        // display UI
        UpdateDisplay();
        
    }

    
  
   
    //@Override 
    public void onListItemClick(AdapterView parent, View v, int position, long id)
    {
    	
    	//super.onListItemClick(parent, v, position, id);
    	Intent itemintent = new Intent(this,RSSFeed.class);
         
    	Bundle b = new Bundle();
    	b.putString("title", feed.getItem(position).getTitle());
    	 b.putString("description", feed.getItem(position).getDescription());
    	b.putString("link", feed.getItem(position).getLink());
    	 b.putString("pubdate", feed.getItem(position).getPubDate());
    	 
    itemintent.putExtra("android.intent.extra.INTENT", b);
         
       startSubActivity(itemintent,0);
   } 
    
    private void UpdateDisplay()
    {
        TextView feedtitle = (TextView) findViewById(R.id.feedtitle);
        TextView feedpubdate = (TextView) findViewById(R.id.feedpubdate);
        ListView itemlist = (ListView) findViewById(R.id.itemlist);
  
        
        if (feed == null)
        {
        	feedtitle.setText("No RSS Feed Available");
        	return;
        }
        
        feedtitle.setText(feed.getTitle());
        feedpubdate.setText(feed.getPubDate());

        ArrayAdapter<RSSItem> adapter = new ArrayAdapter<RSSItem>(this,android.R.layout.simple_list_item_1,feed.getAllItems());

        itemlist.setAdapter(adapter);
        
       // itemlist.setOnItemClickListener(this);
        
        itemlist.setSelection(0);
        
    }
    
   


	private void startSubActivity(Intent itemintent, int i) {
		// TODO Auto-generated method stub
		
	}
}   
