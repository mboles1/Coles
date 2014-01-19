package xmltosqlite;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import android.util.Log;

public class XMLParser {

	// Data field

	// empty constructor
	public XMLParser() {
	}

	// constructor

	// methods
	public ArrayList<Text2> getOfflineText(BufferedInputStream bufferedStream)
	{
		ArrayList<Text2> textArray = new ArrayList<Text2>();
		
		try 
		{
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			
			TextHandler dataHandler = new TextHandler();
			xmlReader.setContentHandler(dataHandler);
			xmlReader.parse(new InputSource(bufferedStream));
			
			textArray = dataHandler.getData();
		} 
		catch (ParserConfigurationException pce)
		{
			Log.e("SAX XML", "sax parse error", pce);
		} 
		catch (SAXException se)
		{
			Log.e("SAX XML", "sax error", se);
		} 
		catch (IOException ioe) 
		{
			Log.d("SAX XML", "sax parse io error", ioe);
			textArray = new ArrayList<Text2>();
			textArray.add(new Text2(99, 99, 99, "Management", "Management","IO error 77 / Can not open URL http://10.58.50.44/ws/get_text_client.php"));
		}
		return textArray;
	}
	
	public ArrayList<Text2> getText() 
	{
		ArrayList<Text2> textArray = new ArrayList<Text2>();

		try 
		{
			// SAXParserFactory spf = SAXParserFactory.newInstance();
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
					.getXMLReader();
			Log.d("Test", "Pass1");
			// SAXParser sp = spf.newSAXParser();
			Log.d("Test", "Pass2");
			// XMLReader xr = sp.getXMLReader();
			Log.d("Test", "Pass3");
			URL uri = new URL("http://130.218.51.52/ws/get_text_client.php");
			Log.d("Test", "Pass4");
			TextHandler dataHandler = new TextHandler();
			Log.d("Test", "Pass5");
			xmlReader.setContentHandler(dataHandler);
			Log.d("Test", "Pass6");
			xmlReader.parse(new InputSource(uri.openStream()));
			Log.d("Test", "Pass7");
			textArray = dataHandler.getData();
			Log.d("Test", "Pass8");
		} 
		catch (ParserConfigurationException pce)
		{
			Log.e("SAX XML", "sax parse error", pce);
		} 
		catch (SAXException se)
		{
			Log.e("SAX XML", "sax error", se);
		} 
		catch (IOException ioe) 
		{
			Log.d("SAX XML", "sax parse io error", ioe);
			textArray = new ArrayList<Text2>();
			textArray.add(new Text2(99, 99, 99, "Management", "Management","IO error 77 / Can not open URL http://10.58.50.44/ws/get_text_client.php"));
		}
		return textArray;
	}
}
