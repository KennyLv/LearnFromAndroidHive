package com.androidhive.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.androidhive.utils.DatabaseHandler;
import com.androidhive.utils.DatabaseHandlerTestModel;
import com.androidhive.utils.XMLParser;
import com.fromandroidhive.androidlearn.R;

public class ListViewAssetXmlSQLiteActivity extends ListActivity {
	// All static variables
	static final String URL = "http://api.androidhive.info/pizza/?format=xml";
	static final String XMLFILENAME = "testdata.xml";
	// XML node keys
	static final String KEY_ITEM = "item"; // parent node
    static final String KEY_NUM = "phone_number";
    static final String KEY_NAME = "name";
    static final String KEY_PRICE = "price";
    static final String KEY_DESC = "description";
    ArrayList<HashMap<String, String>> menuItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_demo);
		
		menuItems = new ArrayList<HashMap<String, String>>();
		
		
		XMLParser parser = new XMLParser();
		// getting XML
		//String xml = parser.getXmlFromUrl(URL);
		String xml = parser.getXmlFromAssets(XMLFILENAME);
		// getting DOM element
		Document doc = parser.getDomElement(xml);
		NodeList nl = doc.getElementsByTagName(KEY_ITEM);		 
		/*
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put(KEY_NUM, parser.getValue(e, KEY_NUM));
            map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
            map.put(KEY_PRICE, "Rs." + parser.getValue(e, KEY_PRICE));
            map.put(KEY_DESC, parser.getValue(e, KEY_DESC));
            // adding HashList to ArrayList
            menuItems.add(map);
		}*/
		
		DatabaseHandler db = new DatabaseHandler(this);
		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);
			Log.d("Insert: ", String.valueOf(e));
			db.addModel(new DatabaseHandlerTestModel(parser.getValue(e, KEY_NAME), parser.getValue(e,KEY_PRICE), 
					parser.getValue(e, KEY_DESC),parser.getValue(e, KEY_NUM)));

		}
		
		List<DatabaseHandlerTestModel> models = db.getAllModels();
		for (int i = 0; i < models.size(); i++) {
			// creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            DatabaseHandlerTestModel model_i = models.get(i);
            // adding each child node to HashMap key => value
            map.put(KEY_NAME, model_i.getName());
            map.put(KEY_PRICE, model_i.getPrice());
            map.put(KEY_DESC, model_i.getDescription());
            map.put(KEY_NUM, model_i.getPhoneNumber());

			Log.d("Reading: ", model_i.getName());
            // adding HashList to ArrayList
            menuItems.add(map);
		}
		
		
		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, menuItems, R.layout.activity_list_view_item, 
				new String[] { KEY_NAME,KEY_DESC }, new int[] {R.id.name,R.id.des});
		setListAdapter(adapter);
		
		/*
		// storing string resources into Array
        String[] adobe_products = getResources().getStringArray(R.array.adobe_products);
        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_list_view_item, R.id.name, adobe_products));
        */
		
		
        ListView lv = getListView();
        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
				// selected item 
	            //String product = ((TextView) view).getText().toString();
	            Log.d("-=-=-index=-=-=-=-=-=-=", String.valueOf(index));
	            //Log.d("-=-=-product=-=-=-=-=-=-=", product);
	            Log.d("-=-=-=-des=-=-=-=-=-=",String.valueOf(menuItems.get(index).get(KEY_DESC)));
			}
        });
	}
}
