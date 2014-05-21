package com.androidhive.utils;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener {
	private final Context mContext;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	// Declaring a Location Manager
	protected LocationManager locationManager;

	public GPSTracker(Context context) {
		this.mContext = context;
		locationManager = (LocationManager) mContext
				.getSystemService(LOCATION_SERVICE);
	}

	public Location getLocation() {
		try {

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			// LocationManager.PASSIVE_PROVIDER

			// List<String> providers = locationManager.getAllProviders();

			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_COARSE); // ACCURACY_FINE
															// �ֲھ�ȷ�� || �ϸ߾�ȷ��
			criteria.setPowerRequirement(Criteria.POWER_LOW);// POWER_HING �õ��
																// || �õ��
			criteria.setAltitudeRequired(false); // �Ƿ���Ҫ�߶���Ϣ
			criteria.setBearingRequired(false); // �Ƿ���Ҫ��λ��Ϣ
			criteria.setSpeedRequired(false); // �Ƿ���Ҫ�ٶ���Ϣ
			criteria.setCostAllowed(false); // �Ƿ������������
			String blp = locationManager.getBestProvider(criteria, false);

			LocationProvider lp = locationManager.getProvider(blp); // LocationManager.GPS_PROVIDER
			lp.getAccuracy(); // ��þ���
			lp.getName(); // �������
			lp.getPowerRequirement(); // ��õ�Դ����
			lp.hasMonetaryCost(); // ��Ǯ�Ļ�����ѵ�
			lp.requiresCell(); // �Ƿ���Ҫ���ʻ�վ����
			lp.requiresNetwork(); // �Ƿ���ҪIntent��������
			lp.requiresSatellite(); // �Ƿ���Ҫ��������
			lp.supportsAltitude(); // �Ƿ��ܹ��ṩ�߶���Ϣ
			lp.supportsBearing(); // �Ƿ��ܹ��ṩ������Ϣ
			lp.supportsSpeed(); // �Ƿ��ܹ��ṩ�ٶ���Ϣ

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
				Log.d("GPS", "GPS_PROVIDER & NETWORK_PROVIDER disabled...");
			} else {
				this.canGetLocation = true;
				// First get location from Network Provider
				if (isNetworkEnabled) {

					Log.d("GPS", "NETWORK_PROVIDER is enabled...");

					if (locationManager != null) {
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();// ��ȡ����
							longitude = location.getLongitude();// ���γ��
							float accuracy = location.getAccuracy();// ��þ�ȷ��
							double altitude = location.getAltitude();// ��ø߶�
							float bearing = location.getBearing();// ��÷���
							float speed = location.getSpeed();// ����ٶ�

							Log.d("GPS", "NETWORK_PROVIDER works...");

						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled && location == null) {

					Log.d("GPS", "NETWORK_PROVIDER doesn't work, GPS_PROVIDER is enabled ...");

					if (locationManager != null) {
						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();

							Log.d("GPS", "GPS_PROVIDER works...");

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app
	 * */
	public void registerLocationUpdates() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
				MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app
	 * */
	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GPSTracker.this);
		}
	}

	/**
	 * Function to get latitude
	 * */
	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}
		// return latitude
		return latitude;
	}

	/**
	 * Function to get longitude
	 * */
	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}
		// return longitude
		return longitude;
	}

	/**
	 * Function to check GPS/wifi enabled
	 * 
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	/**
	 * Function to show settings alert dialog On pressing Settings button will
	 * lauch Settings Options
	 * */
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Setting Dialog Message
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						mContext.startActivity(intent);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	@Override
	public void onLocationChanged(Location arg0) {
		if (arg0 != null) {
			latitude = arg0.getLatitude();
			longitude = arg0.getLongitude();
			Log.d("GPS", String.valueOf(latitude) + String.valueOf(longitude));
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		Log.d("GPS", "provider Disabled ...");

	}

	@Override
	public void onProviderEnabled(String arg0) {
		Log.d("GPS", "provider Enabled ...");
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		Log.d("GPS", "on gps Status Changed ...");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
