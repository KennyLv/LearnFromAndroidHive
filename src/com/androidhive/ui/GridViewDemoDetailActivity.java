package com.androidhive.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.androidhive.utils.GridViewDemoHelper;
import com.androidhive.utils.TouchImageView;
import com.androidhive.androidlearn.R;

public class GridViewDemoDetailActivity extends Activity {
	GridViewDemoHelper utils;
	ViewPager viewPager;
	ArrayList<String> imagePaths;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Òþ²Ø±êÌâÀ¸  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		//Òþ²Ø×´Ì¬À¸  
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		setContentView(R.layout.activity_grid_view_demo_detail);
		
        Intent i = getIntent();
        int position = i.getIntExtra("position",0);
        
        utils = new GridViewDemoHelper(getApplicationContext());
        imagePaths = utils.getFilePathsFromAssets();
		
		PagerAdapter pagerAdapter = new FullScreenImageAdapter(GridViewDemoDetailActivity.this,imagePaths);
		viewPager = (ViewPager) findViewById(R.id.gridview_detial_pager);
        viewPager.setAdapter(pagerAdapter);
        
        viewPager.setCurrentItem(position);
	}
	
	
	
	class FullScreenImageAdapter extends PagerAdapter {
		 
		    private Activity _activity;
		    private ArrayList<String> _imagePaths;
		    private LayoutInflater inflater;
		 
		    // constructor
		    public FullScreenImageAdapter(Activity activity, ArrayList<String> imagePaths) {
		        this._activity = activity;
		        this._imagePaths = imagePaths;
		    }
		 
		    @Override
		    public int getCount() {
		        return this._imagePaths.size();
		    }
		 
		    @Override
		    public boolean isViewFromObject(View view, Object object) {
		        return view == ((RelativeLayout) object);
		    }
		     
		    @Override
		    public Object instantiateItem(ViewGroup container, int position) {
		        inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        View viewLayout = inflater.inflate(R.layout.activity_grid_view_demo_detail_image, container, false);
		  
		        ///ImageView imgDisplay = (ImageView) viewLayout.findViewById(R.id.gridview_detail_img_display);
		        TouchImageView imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.gridview_detail_img_display);
		        Button btnClose = (Button) viewLayout.findViewById(R.id.gridview_detail_btn_close);
		         
		        BitmapFactory.Options options = new BitmapFactory.Options();
		        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		        
		        /*
		        String imgAbsoultePath = GridViewDemoAppConstant.ASSETS_PATH +  _imagePaths.get(position);
		        Log.d("-=-=-=-=-=-=-=",imgAbsoultePath);
		        Bitmap bitmap = BitmapFactory.decodeFile(imgAbsoultePath, options);
		        */
		        
		        InputStream is = null;
				
				try {
					is = _activity.getApplicationContext().getAssets().open(_imagePaths.get(position));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        Bitmap bitmap=BitmapFactory.decodeStream(is);
		        
		        
		        
		        imgDisplay.setImageBitmap(bitmap);
		         
		        // close button click event
		        btnClose.setOnClickListener(new View.OnClickListener() {            
		            @Override
		            public void onClick(View v) {
		                _activity.finish();
		            }
		        });
		  
		        ((ViewPager) container).addView(viewLayout);
		  
		        return viewLayout;
		    }
		     
		    @Override
		    public void destroyItem(ViewGroup container, int position, Object object) {
		        ((ViewPager) container).removeView((RelativeLayout) object);
		    }
		}
}
