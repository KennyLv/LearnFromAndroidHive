package com.androidhive.fun;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.androidhive.utils.ConnectionDetector;
import com.androidhive.utils.GPSTracker;
import com.androidhive.androidlearn.R;

public class NetworkStatusGPSDemoActivity extends Activity {
	Button btnShowLocation;
	Button resetLocation;
	Button checkNetwork;

	GPSTracker gps;
	ConnectionDetector cd;

	Boolean isInternetPresent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gpsdemo);

		btnShowLocation = (Button) findViewById(R.id.gps_but_getlocation);
		resetLocation = (Button) findViewById(R.id.gps_but_reset);
		checkNetwork = (Button) findViewById(R.id.network_check_but);

		gps = new GPSTracker(NetworkStatusGPSDemoActivity.this);
		cd = new ConnectionDetector(getApplicationContext());

		btnShowLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (gps.canGetLocation()) { // gps enabled} // return boolean
											// true/false
					double latitude = gps.getLatitude();
					double longitude = gps.getLongitude();
					Toast.makeText(
							getApplicationContext(),
							"Your Location is - \nLat: " + latitude
									+ "\nLong: " + longitude, Toast.LENGTH_LONG)
							.show();
				} else {
					gps.showSettingsAlert();
				}
			}
		});

		resetLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gps.stopUsingGPS();
			}
		});

		checkNetwork.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// get Internet status
				isInternetPresent = cd.isConnectingToInternet();
				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					// make HTTP requests
					showAlertDialog(NetworkStatusGPSDemoActivity.this,
							"Internet Connection",
							"You have internet connection", true);
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(NetworkStatusGPSDemoActivity.this,
							"No Internet Connection",
							"You don't have internet connection.", false);
				}
			}
		});

	}

	/**
	 * Function to display simple Alert Dialog
	 * 
	 * @param context - application context
	 * @param title   - alert dialog title
	 * @param message - alert message
	 * @param status  - success/failure (used to set icon)
	 * */
	
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context)
			.setTitle(title)
			.setMessage(message)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
	
				}
			}).create();
		alertDialog.show();
		
		/*
		final String[] arrayFruit = new String[] { "苹果", "橘子", "草莓", "香蕉" };
		Dialog listDialog = new AlertDialog.Builder(context)
				.setTitle("你喜欢吃哪种水果？")
				.setIcon(R.drawable.ic_launcher)
				.setItems(arrayFruit, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(NetworkStatusGPSDemoActivity.this,
								arrayFruit[which], Toast.LENGTH_SHORT).show();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).create();
		listDialog.show();
		*/
	}

}
