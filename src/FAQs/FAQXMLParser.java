package FAQs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;


import android.util.Log;

public class FAQXMLParser {

	// Data field

	// empty constructor
	public FAQXMLParser() {
	}

	// constructor

	// methods
	public ArrayList<FAQText> getText() {
		ArrayList<FAQText> textArray = new ArrayList<FAQText>();

		try {
			//SAXParserFactory spf = SAXParserFactory.newInstance();
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			Log.d("Test","Pass1");
			//SAXParser sp = spf.newSAXParser();
			Log.d("Test","Pass2");
			//XMLReader xr = sp.getXMLReader();
			Log.d("Test","Pass3");
			URL uri = new URL("http://130.218.51.52/ws/get_faqs_client.php");
			Log.d("Test","Pass4");
			FAQTextHandler dataHandler = new FAQTextHandler();
			Log.d("Test","Pass5");
			xmlReader.setContentHandler(dataHandler);
			Log.d("Test","Pass6");
			xmlReader.parse(new InputSource(uri.openStream()));
			Log.d("Test","Pass7");
			textArray = dataHandler.getData();
			for(FAQText text: textArray){
			Log.d("FAQTest",""+ text.id);
			}
		} 
		catch (ParserConfigurationException pce) {
			Log.e("SAX XML", "sax parse error", pce);
		} 
		catch (SAXException se) {
			Log.e("SAX XML", "sax error", se);
		} 
		catch (IOException ioe) {
			Log.d("SAX XML", "sax parse io error", ioe);
			textArray = new ArrayList<FAQText>();
			textArray.add(new FAQText(999, "Management",
							"IO error 77 / Can not open URL http://10.58.50.44/ws/get_text_client_client.php"));
		}
		Log.d("FAQActivity","Parsed faqs table");
		return textArray;
	}
}
