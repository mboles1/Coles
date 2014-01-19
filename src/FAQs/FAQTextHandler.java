package FAQs;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

/*
 * From: http://www.jondev.net/articles/Android_XML_SAX_Parser_Example
 * 
 * In the original code that I have copied, this class is the DataHandler class.
 * Data refers to the data type, but we have several data types, e.g. majors, traffic, buildings
 * So we need a new name for this class. The actual function is to transform the XML based on the
 * XML tags, such as id, name and description, so a more descriptive class name might be something
 * like MajorsXmlParseHandler, which is kind of long. 
 */
public class FAQTextHandler extends DefaultHandler {
	private String TAG = "FAQHandler";

	// each tag needs its own boolean to indicate processing is "in" that
	// specific tag
	private boolean _faq;
	private boolean _id;
	private boolean _question;
	private boolean _answer;

	// in this case we save each piece of data and add them to the collection as
	// a group
	private int id;
	private String question;
	private String answer;

	// this holds the data
	private ArrayList<FAQText> _data = new ArrayList<FAQText>();

	/**
	 * Returns the data array
	 * 
	 * @return
	 */
	public ArrayList<FAQText> getData() {
		return _data;
	}

	/**
	 * * This gets called when the xml document is first opened * *
	 * 
	 * @throws SAXException
	 */
	@Override
	public void startDocument() throws SAXException {
		_data = new ArrayList<FAQText>();
		_faq = false;
		_id = false;
		_question = false;
		_answer = false;
	}

	/**
	 * Called when it's finished handling the document
	 * 
	 * @throws SAXException
	 */
	@Override
	public void endDocument() throws SAXException {
	}

	/**
	 * This gets called at the start of an element. Here we're also setting the
	 * booleans to true if it's at that specific tag. (so we know where we are)
	 * 
	 * @param namespaceURI
	 * @param localName
	 * @param qName
	 * @param atts
	 * @throws SAXException
	 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals("faq")) {
			_faq = true;
			try {
				id = Integer.parseInt(atts.getValue("id"));
			} catch (NumberFormatException e) {
				id = -1;
				
			}
		} else if (localName.equals("question")) {
			_question = true;
		} else if (localName.equals("answer")) {
			_answer = true;
		}
	}

	/**
	 * Called at the end of the element. Setting the booleans to false, so we
	 * know that we've just left that tag.
	 * 
	 * @param namespaceURI
	 * @param localName
	 * @param qName
	 * 
	 * @throws SAXException
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		// Log.d(TAG, localName);
		if (localName.equals("faq")) {
			_faq = false;
			FAQText t = new FAQText(id, question, answer);
			_data.add(t); // add the row to the collection
			Log.d(TAG, t.toString());
			question = "";
			answer = "";
		} 
		else if (localName.equals("question")) {
			_question = false;
		} else if (localName.equals("answer")) {
			_answer = false;
		}
	}

	/**
	 * Calling when we're within an element. Here we're checking to see if there
	 * is any content in the tags that we're interested in and populating it in
	 * the Config object.
	 * 
	 * @param ch
	 * @param start
	 * @param length
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		String chars = new String(ch, start, length);
		chars = chars.trim();

		// Log.d(TAG, "in the characters method .. " + chars + ", " +
		// chars.length() + "");
		// Log.d(TAG, "_heading .. " + _heading );
		// Log.d(TAG, "_menu_no .. " + _menu_no );
		// Log.d(TAG, "_item_order .. " + _item_order );
		if (_question) {
			// Log.d(TAG, "_question true." );
			question += chars;
		} else if (_answer) {
			// Log.d(TAG, "_answer true." );
			answer += chars;
		} else if (_id) {
			// Log.d(TAG, "_id true." );
			// do nothing
		} else if (_faq){
			
		}
	}
}
