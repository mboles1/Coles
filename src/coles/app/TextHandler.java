package coles.app;

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
public class TextHandler extends DefaultHandler {
	private String TAG = "TextHandler";
	
	// each tag needs its own boolean to indicate processing is "in" that specific tag
	private boolean _text_row;
	private boolean _menu_no;
	private boolean _item_order;
	private boolean _heading;
	private boolean _body;
	private boolean _link;

	// in this case we save each piece of data and add them to the collection as a group
	private int id;
	private int menu_no;
	private int item_order;
	private String str_menu_no;
	private String str_item_order;
	private String heading = "";
	private String body = "";
	private String link = "";

	// this holds the data
	private ArrayList<Text> _data = new ArrayList<Text>();

	/**
	 * Returns the data array
	 * 
	 * @return
	 */
	public ArrayList<Text> getData() {
		return _data;
	}

	/**    * 
	 * This gets called when the xml document is first opened    *    *
	 * 
	 *  @throws SAXException
	 */	
	@Override
	public void startDocument() throws SAXException {
		_data = new ArrayList<Text>();
		_text_row = false;
		_menu_no = false;
		_item_order = false;
		_heading = false;
		_body = false;
		_link = false;
	}

	/**
	 * Called when it's finished handling the document
	 * 
	 * @throws SAXException
	 */
	@Override
	public void endDocument() throws SAXException { }

	/**    
	 * This gets called at the start of an element. Here we're also setting the booleans to true if it's at that specific tag. (so we 
	 * know where we are)
	 * 
	 * @param namespaceURI
	 * @param localName
	 * @param qName
	 * @param atts
	 * @throws SAXException
	 */
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if(localName.equals("text_row")) {
			_text_row = true;
			try {
				id = Integer.parseInt(atts.getValue("id"));
			} catch (NumberFormatException e) {
				id = -1;
			}
		} else
			if(localName.equals("menu_no")) {
				_menu_no = true;
			} else
				if(localName.equals("item_order")) {
					_item_order = true;
				} else
					if(localName.equals("text_heading")) {
						_heading = true;
					} else
						if(localName.equals("text_body")) {
							_body = true;
						} else
							if(localName.equals("link")) {
								_link = true;
			}
		}
	
	/**
	 * Called at the end of the element. Setting the booleans to false, so we know that we've just left that tag.
	 * 
	 *  @param namespaceURI
	 *  @param localName
	 *  @param qName
	 *  
	 *  @throws SAXException
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
//		Log.d(TAG, localName);
		if(localName.equals("text_row")) {
			_text_row = false;
			Text t = new Text(id, menu_no, item_order, heading, body, link);
			_data.add(t); // add the row to the collection
			Log.d(TAG, t.toString());
			str_menu_no = "";
			str_item_order = "";
			heading = "";
			body = ""; 
			link = ""; 
		} else if(localName.equals("menu_no")) {
//			Log.d(TAG, localName + ", " + str_menu_no);
			try {
				menu_no = Integer.parseInt(str_menu_no);
			} catch (NumberFormatException e) {
				menu_no = 0;
			}
			_menu_no = false;
		} else if(localName.equals("item_order")) {
//			Log.d(TAG, localName + ", " + str_item_order);
			try {
				item_order = Integer.parseInt(str_item_order);
			} catch (NumberFormatException e) {
				item_order = 1;
			}
			_item_order = false;
		} else if(localName.equals("text_heading")) {
//			Log.d(TAG, localName + ", " + heading);
			_heading = false;
		} else if(localName.equals("text_body")) {
			_body = false;
		} else if(localName.equals("link")) {
			_link = false;
		}
	}

	/**
	 * Calling when we're within an element. Here we're checking to see if there is any content in the tags that we're interested in
	 * and populating it in the Config object.
	 * 
	 * @param ch
	 * @param start
	 * @param length
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		String chars = new String(ch, start, length);
		chars = chars.trim();

//		Log.d(TAG, "in the characters method .. " + chars + ", " + chars.length() + "");
//		Log.d(TAG, "_heading .. " + _heading );
//		Log.d(TAG, "_menu_no .. " + _menu_no );
//		Log.d(TAG, "_item_order .. " + _item_order );
		if(_heading) {
//			Log.d(TAG, "_heading true." );
			heading += chars;
			if (heading.contains("&")) {
				Log.d(TAG, "string contains & : " + heading);
			}
		} else if(_body) {
//			Log.d(TAG, "_body true." );
			body += chars;
		} else if(_link) {
//			Log.d(TAG, "_link true." );
			link += chars;
		} else if(_menu_no) {
//			Log.d(TAG, "_menu_no true." );
			if (chars.length() > 0) {
				str_menu_no += chars;
				Log.d(TAG, "str_menu_no in the characters method .. " + chars + ", " + chars.length() + "");
			}
		} else if(_item_order) {
//			Log.d(TAG, "_item_order true." );
			if (chars.length() > 0) {
				str_item_order += chars;
//				Log.d(TAG, "str_item_order in the characters method .. " + chars + ", " + chars.length() + "");
			}
		} else if(_text_row) {
//			Log.d(TAG, "_text_row true." );
			// do nothing
		}
	}
}
