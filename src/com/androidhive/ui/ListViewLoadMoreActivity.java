package com.androidhive.ui;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.androidhive.utils.XMLParser;
import com.androidhive.androidlearn.R;

public class ListViewLoadMoreActivity extends ListActivity {

    ProgressDialog pDialog;
    ListView lv;
    ListViewAdapter adapter;
    ArrayList<HashMap<String, String>> menuItems;
    LayoutInflater inflater;
    
    XMLParser parser;
    Document doc;
    String xml;
    
    int current_page = 0;
    int currentPosition = -1;
 
    private String URL = "http://api.androidhive.info/list_paging/?page=1";
 
    // XML node keys
    static final String KEY_ITEM = "item";
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_DES = "des";
 
    // Flag for current page
 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_demo);

		menuItems = new ArrayList<HashMap<String, String>>();
		parser = new XMLParser();
		
		lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                Log.d("name",name);
            }
        });
        lv.setOnScrollListener(new OnScrollListener() {  
            @Override  
            public void onScrollStateChanged(AbsListView view, int scrollState) { 
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					Log.d("scrollState","已经停止：SCROLL_STATE_IDLE");

					int lastVisibleItem = lv.getLastVisiblePosition();
					if((lv.getCount() -1 ) == lastVisibleItem){
						
						Log.d("scrollState"," Scroll to the end, load more?");
					}
					
					break;
				case OnScrollListener.SCROLL_STATE_FLING:
					Log.d("scrollState","开始滚动：SCROLL_STATE_FLING");
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					Log.d("scrollState","正在滚动：SCROLL_STATE_TOUCH_SCROLL");
					break;
				}
            }  
              
            @Override  
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) { 
            	/*
                //firstVisibleItem表示在现时屏幕第一个ListItem(部分显示的ListItem也算) 在整个ListView的位置（下标从0开始）
                Log.i("firstVisibleItem", String.valueOf(firstVisibleItem));  
                //visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数
                Log.i("visibleItemCount", String.valueOf(visibleItemCount));  
                //totalItemCount表示ListView的ListItem总数
                Log.i("totalItemCount", String.valueOf(totalItemCount));
                //listView.getFirstVisiblePosition()表示在现时屏幕第一个ListItem(第一个ListItem部分显示也算) 在整个ListView的位置（下标从0开始）
                Log.i("firstPosition", String.valueOf(lv.getFirstVisiblePosition()));  
                //listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem(最后ListItem要完全显示出来才算) 在整个ListView的位置（下标从0开始）                 
                Log.i("lasPosition", String.valueOf(lv.getLastVisiblePosition()));
                */
            }
        });  
        
        // Getting adapter
        inflater = (LayoutInflater)ListViewLoadMoreActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adapter = new ListViewAdapter(menuItems);
        lv.setAdapter(adapter);

        // LoadMore button
        Button btnLoadMore = new Button(this);
        btnLoadMore.setText("Load More");
        lv.addFooterView(btnLoadMore);
        
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Starting a new async task
                new loadMoreListView().execute();
            }
        });

        new loadMoreListView().execute();
	}
	
	class ListViewAdapter extends BaseAdapter {
	    private ArrayList<HashMap<String, String>> data;
	 
	    public ListViewAdapter(ArrayList<HashMap<String, String>> d) {
	        data=d;
	    }
	 
	    public int getCount() {
	        return data.size();
	    }
	 
	    public Object getItem(int position) {
	        return position;
	    }
	 
	    public long getItemId(int position) {
	        return position;
	    }

		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View vi=convertView;
	        if(convertView==null){
	            vi = inflater.inflate(R.layout.activity_list_view_item, null);
	        }
	        TextView name = (TextView)vi.findViewById(R.id.name);
	        TextView des = (TextView)vi.findViewById(R.id.des);
	        
	        HashMap<String, String> item = new HashMap<String, String>();
	        item = data.get(position);

	        name.setText(item.get(KEY_NAME));
	        des.setText(item.get(KEY_DES));
	        return vi;
	    }
	}
	
	class loadMoreListView extends AsyncTask<Void, Void, Void> {
		 
        @Override
        protected void onPreExecute() {
            // Showing progress dialog before sending http request
            pDialog = new ProgressDialog(ListViewLoadMoreActivity.this);
            pDialog.setMessage("Please wait..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        protected Void doInBackground(Void... unused) {
            //runOnUiThread(new Runnable() {
                //public void run() {
                    // increment current page
                    current_page += 1;
 
                    // Next page request
                    URL = "http://api.androidhive.info/list_paging/?page=" + current_page;
 
                    xml = parser.getXmlFromUrl(URL); // getting XML
                    doc = parser.getDomElement(xml); // getting DOM element
 
                    NodeList nl = doc.getElementsByTagName(KEY_ITEM);
                    // looping through all item nodes <item>
                    for (int i = 0; i < nl.getLength(); i++) {
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        Element e = (Element) nl.item(i);
 
                        // adding each child node to HashMap key => value
                        map.put(KEY_ID, parser.getValue(e, KEY_ID));
                        map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
                        map.put(KEY_DES, "des: page " + String.valueOf(current_page) + ", num " + String.valueOf(i));
 
                        // adding HashList to ArrayList
                        menuItems.add(map);
                    }
 
                //}
            //});
 
            return (null);
        }
 
        protected void onPostExecute(Void unused) {
            // closing progress dialog
            pDialog.dismiss();

            try{
            	currentPosition = lv.getFirstVisiblePosition();
            }catch(NullPointerException e){
            	
            }

            // Appending new data to menuItems ArrayList
            adapter = new ListViewAdapter(menuItems);
            lv.setAdapter(adapter);
            // Setting new scroll position
            lv.setSelectionFromTop(currentPosition + 1, 0);

        }
    }
	
	
}
