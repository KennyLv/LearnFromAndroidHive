package com.androidhive.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fromandroidhive.androidlearn.R;

public class ListViewFilterActivity extends Activity {
    ListView lv;
    EditText inputSearch;
    
    //ArrayAdapter<String> adapter;
    ListAdapter adapter;
    
    //String products[];
	ArrayList<HashMap<String, String>> productList;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_filter);

        lv = (ListView) findViewById(R.id.listview_filter_listview);
        inputSearch = (EditText) findViewById(R.id.listview_filter_inputsearch);
        
        //CASE 1
        String products[] = {"Dell Inspiron", 
        		"HTC One X", 
        		"HTC Wildfire S", 
        		"HTC Sense", 
        		"HTC Sensation XE",
        		"iPhone 4S", 
        		"Samsung Galaxy Note 800",
        		"Samsung Galaxy S3", 
        		"MacBook Air", 
        		"Mac Mini", 
        		"MacBook Pro"};
        //CASE 2
        productList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < products.length; i++) {
			// creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            // adding each child node to HashMap key => value
            map.put("TEST_NAME", products[i]);
            map.put("TEST_DES", String.valueOf(i) + " - -test");
            // adding HashList to ArrayList
            productList.add(map);
		}
		
		//CASE 1
        //adapter = new ArrayAdapter<String>(this, R.layout.activity_list_view_item, R.id.name, products);
		
		//CASE 2
        adapter = new SimpleAdapter(this, productList, R.layout.activity_list_view_item, 
				new String[] { "TEST_NAME","TEST_DES" }, new int[] {R.id.name,R.id.des});
        
        lv.setAdapter(adapter);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
            	((Filterable) adapter).getFilter().filter(cs);
            	//((Filterable) ListViewFilterActivity.this.adapter).getFilter().filter(cs);
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
	}
}
