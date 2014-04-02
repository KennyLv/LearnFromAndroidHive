package com.androidhive.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.androidhive.utils.GridViewDemoAppConstant;
import com.androidhive.utils.GridViewDemoHelper;
import com.fromandroidhive.androidlearn.R;

public class GridViewDemoActivity extends Activity {
	private GridViewDemoHelper utils;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private GridViewImageAdapter adapter;
    private GridView gridView;
    private int columnWidth;
    Context mContext;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_view_demo);
		
		mContext = getApplicationContext();
		
		utils = new GridViewDemoHelper(mContext);
		
		gridView = (GridView) findViewById(R.id.grid_view);
		
		Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GridViewDemoAppConstant.GRID_PADDING, r.getDisplayMetrics());
 
        columnWidth = (int) ((utils.getScreenWidth() - ((GridViewDemoAppConstant.NUM_OF_COLUMNS + 1) * padding)) / GridViewDemoAppConstant.NUM_OF_COLUMNS);
 
        gridView.setNumColumns(GridViewDemoAppConstant.NUM_OF_COLUMNS);
        
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
		

        //imagePaths = utils.getFilePathsFromSD();
        imagePaths = utils.getFilePathsFromAssets();
        
        // Gridview adapter
        adapter = new GridViewImageAdapter(GridViewDemoActivity.this, imagePaths,columnWidth);
         // setting grid view adapter
        gridView.setAdapter(adapter);
		
		
	}
	
	class GridViewImageAdapter extends BaseAdapter {
		 
	    private Activity _activity;
	    private ArrayList<String> _filePaths = new ArrayList<String>();
	    private int imageWidth;
	 
	    public GridViewImageAdapter(Activity activity, ArrayList<String> filePaths,
	            int imageWidth) {
	        this._activity = activity;
	        this._filePaths = filePaths;
	        this.imageWidth = imageWidth;
	    }
	 
	    @Override
	    public int getCount() {
	        return this._filePaths.size();
	    }
	 
	    @Override
	    public Object getItem(int position) {
	        return this._filePaths.get(position);
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {
	            imageView = new ImageView(_activity);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	 
	        // get screen dimensions
	        //Bitmap image = decodeSDFile(_filePaths.get(position), imageWidth,imageWidth);
	        Bitmap image = decodeAssetsFile(_filePaths.get(position), imageWidth,imageWidth);
	 
	        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
	                imageWidth));
	        imageView.setImageBitmap(image);
	 
	        // image view click listener
	        imageView.setOnClickListener(new OnImageClickListener(position));
	 
	        return imageView;
	    }
	 
	    class OnImageClickListener implements OnClickListener {
	 
	        int _postion;
	 
	        // constructor
	        public OnImageClickListener(int position) {
	            this._postion = position;
	        }
	 
	        @Override
	        public void onClick(View v) {
	            // on selecting grid view image
	            // launch full screen activity
	            Intent i = new Intent(_activity, GridViewDemoDetailActivity.class);
	            i.putExtra("position", _postion);
	            _activity.startActivity(i);
	        }
	    }
	 
	    /*
	     * Resizing image size
	     */
	    public Bitmap decodeAssetsFile(String filePath, int WIDTH, int HIGHT) {
	        InputStream is = null;
			
			try {
				is = mContext.getAssets().open(filePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
 
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, o);
 
			final int REQUIRED_WIDTH = WIDTH;
			final int REQUIRED_HIGHT = HIGHT;
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HIGHT){
			    scale *= 2;
			}
			
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(is, null, o2);
	    }
	    
	    //Need absolute path
	    public Bitmap decodeSDFile(String filePath, int WIDTH, int HIGHT) {
	        try {
	 
	            File f = new File(filePath);
	 
	            BitmapFactory.Options o = new BitmapFactory.Options();
	            o.inJustDecodeBounds = true;
	            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
	 
	            final int REQUIRED_WIDTH = WIDTH;
	            final int REQUIRED_HIGHT = HIGHT;
	            int scale = 1;
	            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HIGHT){
	                scale *= 2;
	            }
	            BitmapFactory.Options o2 = new BitmapFactory.Options();
	            o2.inSampleSize = scale;
	            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	    
	}
	
}
