package com.androidhive.utils;

import com.androidhive.interfaces.ICounterService;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class CounterService extends Service implements ICounterService {
	private final static String LOG_TAG = "com.androidhive.interfaces.ICounterService";

	public final static String BROADCAST_COUNTER_ACTION = "com.androidhive.interfaces.ICounterService.COUNTER_ACTION";
	public final static String COUNTER_VALUE = "com.androidhive.interfaces.ICounterService.value";

	private boolean isStop = true;
	private Integer lastVal;

	private final IBinder binder = new CounterServiceBinder();
	
	public class CounterServiceBinder extends Binder {
		public CounterService getService() {
			return CounterService.this;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}
	
	@Override
	public void startCounter(int initVal) {
		Log.i(LOG_TAG, "Counter Service Started.");
		AsyncTask<Integer, Integer, Integer> task = new AsyncTask<Integer, Integer, Integer>() {
			@Override
			protected Integer doInBackground(Integer... vals) {
				Integer initCounter = vals[0];//parameter
				isStop = false;
				while (!isStop) {
					publishProgress(initCounter);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					initCounter++;
				}
				return initCounter;
			}
			@Override
			protected void onProgressUpdate(Integer... values) {//progress
				super.onProgressUpdate(values);
				int counter = values[0];
				
				Intent intent = new Intent(BROADCAST_COUNTER_ACTION);
				intent.putExtra(COUNTER_VALUE, counter);
				sendBroadcast(intent);
			}

			@Override
			protected void onPostExecute(Integer val) {//result
				lastVal = val;
				int counter = val;
				
				//Intent intent = new Intent(BROADCAST_COUNTER_ACTION);
				//intent.putExtra(COUNTER_VALUE, counter);
				//sendBroadcast(intent);
				
				Log.i(LOG_TAG, "Counter Service onPostExecute.");
			}
		};
		task.execute(initVal);
	}
	
	@Override
	public void resumeCounter(){
		startCounter(lastVal);
	}
	
	@Override
	public void stopCounter() {
		isStop = true;
	}
	
	@Override
	public boolean isStopped(){
		return isStop;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(LOG_TAG, "Counter Service Created.");
	}

	@Override
	public void onDestroy() {
		Log.i(LOG_TAG, "Counter Service Destroyed.");
	}
}
