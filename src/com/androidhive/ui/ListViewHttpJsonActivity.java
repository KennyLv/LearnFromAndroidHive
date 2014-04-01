package com.androidhive.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.androidhive.utils.HTTPServiceHandler;
import com.fromandroidhive.androidlearn.R;

public class ListViewHttpJsonActivity extends ListActivity {
	private ProgressDialog pDialog;
	 
    // URL to get contacts JSON
    private static String url = "http://api.androidhive.info/contacts/";
 
    // contacts JSONArray
    JSONArray contacts = null;
    
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_demo);
		
        getListView().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.json_name)).getText().toString();
                String cost = ((TextView) view.findViewById(R.id.json_email)).getText().toString();
                String description = ((TextView) view.findViewById(R.id.json_mobile)).getText().toString();
                Log.d("JSON Obj : " , name + "-" + cost + "-" + description);
            }
        });

        // Calling async task to get json
        contactList = new ArrayList<HashMap<String, String>>();
        new GetContacts().execute();
	}

	 /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        // JSON Node names
        private static final String TAG_CONTACTS = "contacts";
        private static final String TAG_ID = "id";
        private static final String TAG_NAME = "name";
        private static final String TAG_EMAIL = "email";
        //private static final String TAG_ADDRESS = "address";
        //private static final String TAG_GENDER = "gender";
        private static final String TAG_PHONE = "phone";
        private static final String TAG_PHONE_MOBILE = "mobile";
        //private static final String TAG_PHONE_HOME = "home";
        //private static final String TAG_PHONE_OFFICE = "office";
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListViewHttpJsonActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
        	HTTPServiceHandler sh = new HTTPServiceHandler();
 
            // Making a request to url and getting response
        	/*
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", ""));
            params.add(new BasicNameValuePair("email", ""));
            params.add(new BasicNameValuePair("password", ""));
            String jsonStr = sh.makeHttpCall(url, HTTPServiceHandler.GET, params);
            */
            String jsonStr = sh.makeHttpCall(url, HTTPServiceHandler.GET);
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_CONTACTS);
 
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                         
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String email = c.getString(TAG_EMAIL);
                        //String address = c.getString(TAG_ADDRESS);
                        //String gender = c.getString(TAG_GENDER);
 
                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject(TAG_PHONE);
                        String mobile = phone.getString(TAG_PHONE_MOBILE);
                        //String home = phone.getString(TAG_PHONE_HOME);
                        //String office = phone.getString(TAG_PHONE_OFFICE);
 
                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        contact.put(TAG_ID, id);
                        contact.put(TAG_NAME, name);
                        contact.put(TAG_EMAIL, email);
                        contact.put(TAG_PHONE_MOBILE, mobile);
 
                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(ListViewHttpJsonActivity.this, contactList, R.layout.activity_list_view_json, 
            		new String[] { TAG_NAME, TAG_EMAIL, TAG_PHONE_MOBILE }, 
            		new int[] { R.id.json_name, R.id.json_email, R.id.json_mobile });
            setListAdapter(adapter);
        }
    }
    
}
