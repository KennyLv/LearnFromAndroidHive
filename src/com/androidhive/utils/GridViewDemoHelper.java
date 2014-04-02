package com.androidhive.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

public class GridViewDemoHelper {
	private Context _context;

	// constructor
	public GridViewDemoHelper(Context context) {
		this._context = context;
	}

	// Reading file paths from SDCard
	public ArrayList<String> getFilePathsFromSD() {
		ArrayList<String> filePaths = new ArrayList<String>();
		File directory = new File(
                android.os.Environment.getExternalStorageDirectory()
                        + File.separator + GridViewDemoAppConstant.PHOTO_ALBUM);
 
        // check for directory
        if (directory.isDirectory()) {
            // getting list of file paths
            File[] listFiles = directory.listFiles();
 
            // Check for count
            if (listFiles.length > 0) {
 
                // loop through all files
                for (int i = 0; i < listFiles.length; i++) {
 
                    // get file path
                    String filePath = listFiles[i].getAbsolutePath();
 
                    // check for supported file extension
                    if (IsSupportedFile(filePath)) {
                        // Add image path to array list
                        filePaths.add(filePath);
                    }
                }
            } else {
                // image directory is empty
                Toast.makeText( _context, GridViewDemoAppConstant.PHOTO_ALBUM + " is empty!", Toast.LENGTH_LONG).show();
            }
 
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
            alert.setTitle("Error!");
            alert.setMessage(GridViewDemoAppConstant.PHOTO_ALBUM + " directory path is not valid!");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
		return filePaths;
	}

	// Reading file paths from Assets
	public ArrayList<String> getFilePathsFromAssets() {
		ArrayList<String> filePaths = new ArrayList<String>();
		String str[] = null;
		try {
			str = _context.getAssets()
					.list(GridViewDemoAppConstant.PHOTO_ALBUM);
			if (str != null && str.length > 0) {

				// loop through all files
				for (int i = 0; i < str.length; i++) {
					if (IsSupportedFile(str[i])) {
						String absoultPath = GridViewDemoAppConstant.PHOTO_ALBUM + File.separator + str[i];
						filePaths.add(absoultPath);
					}
				}
			} else {
				Toast.makeText(_context, GridViewDemoAppConstant.PHOTO_ALBUM + " is empty.", Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			AlertDialog.Builder alert = new AlertDialog.Builder(_context);
			alert.setTitle("Error!");
			alert.setMessage(GridViewDemoAppConstant.PHOTO_ALBUM + " directory path is not valid!");
			alert.setPositiveButton("OK", null);
			alert.show();
		}

		return filePaths;
	}
	
	// Check supported file extensions
	private boolean IsSupportedFile(String filePath) {
		String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
				filePath.length());
		if (GridViewDemoAppConstant.FILE_EXTN.contains(ext.toLowerCase(Locale
				.getDefault()))) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * getting screen width
	 */
	@SuppressLint("NewApi")
	public int getScreenWidth() {
		int columnWidth;
		WindowManager wm = (WindowManager) _context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (java.lang.NoSuchMethodError ignore) { // Older device
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		columnWidth = point.x;
		return columnWidth;

		// DisplayMetrics dm = _context.getResources().getDisplayMetrics();
		// int w_screen = dm.widthPixels;
		// int h_screen = dm.heightPixels;

	}
}
