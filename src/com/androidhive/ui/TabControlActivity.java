package com.androidhive.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fromandroidhive.androidlearn.R;

@SuppressLint("NewApi")
public class TabControlActivity extends FragmentActivity implements TabListener {
	private ViewPager viewPager;
    private ActionBar actionBar;
    private String[] tabs = { "TopRated", "Games", "Movies" };
    private TabsPagerAdapter mAdapter;
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_control);
		
		// Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
         
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
         
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		// on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public class TabsPagerAdapter extends FragmentPagerAdapter {
		 
	    public TabsPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }
	 
	    @Override
	    public Fragment getItem(int index) {
	 
	        switch (index) {
	        case 0:
	            // Top Rated fragment activity
	            return new TopRatedFragment();
	        case 1:
	            // Games fragment activity
	            return new GamesFragment();
	        case 2:
	            // Movies fragment activity
	            return new MoviesFragment();
	        }
	 
	        return null;
	    }
	 
	    @Override
	    public int getCount() {
	        // get item count - equal to number of tabs
	        return 3;
	    }
	}
	
	@SuppressLint("ValidFragment")
	public class TopRatedFragment extends Fragment {
		 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
	        rootView.setBackgroundColor(Color.RED);
	        TextView contentTxt = (TextView)rootView.findViewById(R.id.viewtxt);
	        contentTxt.setText("Design Top Screen");
	        return rootView;
	    }
	}
	
	@SuppressLint("ValidFragment")
	public class GamesFragment extends Fragment {
		 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
	        rootView.setBackgroundColor(Color.GREEN);
	        TextView contentTxt = (TextView)rootView.findViewById(R.id.viewtxt);
	        contentTxt.setText("Design Games Screen");
	        return rootView;
	    }
	}
	
	@SuppressLint("ValidFragment")
	public class MoviesFragment extends Fragment {
		 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
	        rootView.setBackgroundColor(Color.BLUE);
	        TextView contentTxt = (TextView)rootView.findViewById(R.id.viewtxt);
	        contentTxt.setText("Design Movies Screen");
	        return rootView;
	    }
	}
	
}
