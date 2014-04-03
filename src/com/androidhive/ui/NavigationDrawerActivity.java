package com.androidhive.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidhive.ui.options.NavHomeFragment;
import com.androidhive.ui.options.NavHotFragment;
import com.androidhive.ui.options.NavPhotoFragment;
import com.fromandroidhive.androidlearn.R;

public class NavigationDrawerActivity extends Activity {
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
 
    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
 
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
 
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation_drawer);
		
		mTitle = mDrawerTitle = getTitle();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_drawer_list_slider_menu);
 
        navDrawerItems = new ArrayList<NavDrawerItem>();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, "50+"));
        // Recycle the typed array
        navMenuIcons.recycle();
 
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.navdrawer, menu);
	        return true;
	    }
	 
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // toggle nav drawer on selecting action bar app icon/title
	        if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
	        // Handle action bar actions click
	        switch (item.getItemId()) {
	        case R.id.action_settings:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
	 
	    /***
	     * Called when invalidateOptionsMenu() is triggered
	     */
	    @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	        // if nav drawer is opened, hide the action items
	        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
	        return super.onPrepareOptionsMenu(menu);
	    }
	 
	    @SuppressLint("NewApi")
		@Override
	    public void setTitle(CharSequence title) {
	        mTitle = title;
	        getActionBar().setTitle(mTitle);
	    }
	 
	    /**
	     * When using the ActionBarDrawerToggle, you must call it during
	     * onPostCreate() and onConfigurationChanged()...
	     */
	 
	    @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mDrawerToggle.syncState();
	    }
	 
	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        // Pass any configuration change to the drawer toggls
	        mDrawerToggle.onConfigurationChanged(newConfig);
	    }
	    
	    @SuppressLint("NewApi")
		private void displayView(int position) {
	        // update the main content by replacing fragments
	        Fragment fragment = null;
	        switch (position) {
		        case 0:
		            fragment = new NavHomeFragment();
		            break;
		        case 1:
		            fragment = new NavPhotoFragment();
		            break;
		        case 2:
		            fragment = new NavHotFragment();
		            break;
		 
		        default:
		            break;
	        }
	 
	        if (fragment != null) {
	            FragmentManager fragmentManager = getFragmentManager();
	            fragmentManager.beginTransaction().replace(R.id.nav_drawer_frame_container, fragment).commit();
	 
	            // update selected item and title, then close the drawer
	            mDrawerList.setItemChecked(position, true);
	            mDrawerList.setSelection(position);
	            setTitle(navMenuTitles[position]);
	            mDrawerLayout.closeDrawer(mDrawerList);
	        } else {
	            // error in creating fragment
	            Log.e("Nav Drawer Activity", "Error in creating fragment");
	        }
	    }
	
	class SlideMenuClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        // display view for selected nav drawer item
	        displayView(position);
	    }
	}
	
	class NavDrawerListAdapter extends BaseAdapter {
	     
	    private Context context;
	    private ArrayList<NavDrawerItem> navDrawerItems;
	     
	    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
	        this.context = context;
	        this.navDrawerItems = navDrawerItems;
	    }
	 
	    @Override
	    public int getCount() {
	        return navDrawerItems.size();
	    }
	 
	    @Override
	    public Object getItem(int position) {       
	        return navDrawerItems.get(position);
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        if (convertView == null) {
	            LayoutInflater mInflater = (LayoutInflater)
	                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.activity_navigation_drawer_list_item, null);
	        }
	          
	        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.nav_drawer_list_icon);
	        TextView txtTitle = (TextView) convertView.findViewById(R.id.nav_drawer_list_title);
	        TextView txtCount = (TextView) convertView.findViewById(R.id.nav_drawer_list_counter);
	          
	        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
	        txtTitle.setText(navDrawerItems.get(position).getTitle());
	         
	        // displaying count
	        // check whether it set visible or not
	        if(navDrawerItems.get(position).getCounterVisibility()){
	            txtCount.setText(navDrawerItems.get(position).getCount());
	        }else{
	            // hide the counter view
	            txtCount.setVisibility(View.GONE);
	        }
	         
	        return convertView;
	    }
	 
	}
	
	
	
	
	class NavDrawerItem {
	     
	    private String title;
	    private int icon;
	    private String count = "0";
	    private boolean isCounterVisible = false;
	     
	    public NavDrawerItem(){}
	 
	    public NavDrawerItem(String title, int icon){
	        this.title = title;
	        this.icon = icon;
	    }
	     
	    public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count){
	        this.title = title;
	        this.icon = icon;
	        this.isCounterVisible = isCounterVisible;
	        this.count = count;
	    }
	     
	    public String getTitle(){
	        return this.title;
	    }
	     
	    public int getIcon(){
	        return this.icon;
	    }
	     
	    public String getCount(){
	        return this.count;
	    }
	     
	    public boolean getCounterVisibility(){
	        return this.isCounterVisible;
	    }
	     
	    public void setTitle(String title){
	        this.title = title;
	    }
	     
	    public void setIcon(int icon){
	        this.icon = icon;
	    }
	     
	    public void setCount(String count){
	        this.count = count;
	    }
	     
	    public void setCounterVisibility(boolean isCounterVisible){
	        this.isCounterVisible = isCounterVisible;
	    }
	}
}
