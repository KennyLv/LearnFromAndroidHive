package com.androidhive.ui.options;

import com.androidhive.androidlearn.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class NavHomeFragment extends Fragment {
	public NavHomeFragment(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.activity_navigation_drawer_frag_home, container, false);
          
        return rootView;
    }
}
