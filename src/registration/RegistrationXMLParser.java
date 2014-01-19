package registration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import FAQs.FAQText;
import FAQs.FAQTextHandler;
import android.util.Log;

public class RegistrationXMLParser {
	
	public RegistrationXMLParser(){
	}
	
	public ArrayList<RegistrationText> getText(){
		ArrayList<RegistrationText> textArray = new ArrayList<RegistrationText>();
		
		try {
			//SAXParserFactory spf = SAXParserFactory.newInstance();
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			Log.d("Test","Pass1");
			//SAXParser sp = spf.newSAXParser();
			Log.d("Test","Pass2");
			//XMLReader xr = sp.getXMLReader();
			Log.d("Test","Pass3");
			URL uri = new URL("http://130.218.51.52/ws/get_registration_client.php");
			Log.d("Test","Pass4");
			RegistrationTextHandler dataHandler = new RegistrationTextHandler();
			Log.d("Test","Pass5");
			xmlReader.setContentHandler(dataHandler);
			Log.d("Test","Pass6");
			xmlReader.parse(new InputSource(uri.openStream()));
			Log.d("Test","Pass7");
			textArray = dataHandler.getData();
			} 
		catch (ParserConfigurationException pce) {
			Log.e("SAX XML", "sax parse error", pce);
		} 
		catch (SAXException se) {
			Log.e("SAX XML", "sax error", se);
		} 
		catch (IOException ioe) {
			Log.d("SAX XML", "sax parse io error", ioe);
			textArray = new ArrayList<RegistrationText>();
			textArray.add(new RegistrationText(999, "Management", "Managementbody",
							"IO error 77 / Can not open URL http://10.58.50.44/ws/get_text_client_client.php"));
		}
		Log.d("FAQActivity","Parsed faqs table");
		return textArray;
		}
	
}
