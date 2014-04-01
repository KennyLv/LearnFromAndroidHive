package com.androidhive.ui;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidhive.utils.ImageLoader;
import com.androidhive.utils.XMLParser;
import com.fromandroidhive.androidlearn.R;

public class ListViewCustomActivity extends ListActivity {
	// All static variables
    static final String URL = "http://api.androidhive.info/music/music.xml";
    // XML node keys
    static final String KEY_SONG = "song"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";
    static final String KEY_DURATION = "duration";
    static final String KEY_THUMB_URL = "thumb_url";
    ListView list;
    LazyAdapter adapter;
    LayoutInflater inflater=null;
    Typeface tf;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_demo);		
		inflater = (LayoutInflater)ListViewCustomActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Font path
        String fontPath = "fonts/fears.ttf"; 
        tf = Typeface.createFromAsset(getAssets(), fontPath);
		
		final ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();		
		XMLParser parser = new XMLParser();
        //String xml = parser.getXmlFromUrl(URL); // getting XML from URL
        String xml = parser.getXmlFromAssets("ListViewCustomActivity.xml");        
        Document doc = parser.getDomElement(xml); // getting DOM element 
        NodeList nl = doc.getElementsByTagName(KEY_SONG);
        // looping through all song nodes &lt;song&gt;
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key =&gt; value
            map.put(KEY_ID, parser.getValue(e, KEY_ID));
            map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
            map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
            map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
 
            // adding HashList to ArrayList
            songsList.add(map);
        }
		
		
		//activity_list_view_item_custom
		list = getListView();
		
        // Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, songsList);
        list.setAdapter(adapter);
 
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
 
			@Override
			public void onItemClick(AdapterView<?> parent, View view,  int position, long id) {
				// TODO Auto-generated method stub
				
				Intent player = new Intent(ListViewCustomActivity.this, PlayerScreenVideoActivity.class);
				//Intent player = new Intent(ListViewCustomActivity.this, PlayerScreenOrientationActivity.class);
				HashMap<String, String> songInfo = songsList.get(position);
				player.putExtra(KEY_ID, songInfo.get(KEY_ID));
				player.putExtra(KEY_TITLE, songInfo.get(KEY_TITLE));
				player.putExtra(KEY_ARTIST, songInfo.get(KEY_ARTIST));
				player.putExtra(KEY_THUMB_URL, songInfo.get(KEY_THUMB_URL));
                startActivity(player);
			}
		});
	}
	
	class LazyAdapter extends BaseAdapter {
		 
	    private ArrayList<HashMap<String, String>> data;
	    public ImageLoader imageLoader; 
	 
	    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
	        data=d;
	        imageLoader=new ImageLoader(a.getApplicationContext());
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
	            vi = inflater.inflate(R.layout.activity_list_view_item_custom, null);
	        }
	        TextView title = (TextView)vi.findViewById(R.id.title); // title
	        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
	        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
	        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
	 
	        HashMap<String, String> song = new HashMap<String, String>();
	        song = data.get(position);

	        // Applying font
	        title.setTypeface(tf);
	        // Setting all values in listview
	        title.setText(song.get(KEY_TITLE));
	        
	        artist.setText(song.get(KEY_ARTIST));
	        duration.setText(song.get(KEY_DURATION));
	        
	        imageLoader.DisplayImage(song.get(KEY_THUMB_URL), thumb_image);
	        return vi;
	    }

	}

}
