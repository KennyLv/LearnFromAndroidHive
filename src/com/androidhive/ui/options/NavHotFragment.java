package com.androidhive.ui.options;

import com.androidhive.androidlearn.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class NavHotFragment extends Fragment {
	public NavHotFragment(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.activity_navigation_drawer_frag_hot, container, false);
          
        return rootView;
    }
}
