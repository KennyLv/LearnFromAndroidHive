package com.androidhive.ui.options;

import com.fromandroidhive.androidlearn.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class NavPhotoFragment extends Fragment {
	public NavPhotoFragment(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.activity_navigation_drawer_frag_photo, container, false);
          
        return rootView;
    }
}
