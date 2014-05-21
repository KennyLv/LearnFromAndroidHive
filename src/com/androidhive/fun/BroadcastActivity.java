package com.androidhive.fun;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.androidhive.interfaces.ICounterService;
import com.androidhive.utils.CounterService;
import com.androidhive.androidlearn.R;

public class BroadcastActivity extends Activity {
	private final static String LOG_TAG = "com.androidhive.fun.BroadcastActivity";
	private Button startButton = null;
	private Button resumeButton = null;
	private Button stopButton = null;
	private TextView counterText = null;

	private ICounterService counterService = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_broadcast);

		startButton = (Button) findViewById(R.id.broadcast_startcounter);
		resumeButton = (Button) findViewById(R.id.broadcast_resumecounter);
		stopButton = (Button) findViewById(R.id.broadcast_stopcounter);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				if(counterService != null) {
					if(counterService.isStopped()){
						counterService.startCounter(0);
					}
                }
			}
		});
		resumeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				if(counterService != null) {
					if(counterService.isStopped()){
						counterService.resumeCounter(); 
					}
                }  
			}
		});
		stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(counterService != null) {
					if(!counterService.isStopped()){
						counterService.stopCounter(); 
					}
                }  
			}
		});

		counterText = (TextView) findViewById(R.id.broadcast_countertxt);
		Intent bindIntent = new Intent(BroadcastActivity.this, CounterService.class);  
        bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);
      
        Log.i(LOG_TAG, "Broadcast Activity onCreate.");
	}
	
	 @Override   
     public void onResume() {  
         super.onResume();
         IntentFilter counterActionFilter = new IntentFilter(CounterService.BROADCAST_COUNTER_ACTION); 
         registerReceiver(counterBroadcastReceiver, counterActionFilter);
         Log.i(LOG_TAG, "Broadcast Activity onResume.");
     }  
   
     @Override  
     public void onPause() {  
         super.onPause();  
         unregisterReceiver(counterBroadcastReceiver);  
         Log.i(LOG_TAG, "Broadcast Activity onPause.");
     }  
   
     @Override  
     public void onDestroy() {  
         super.onDestroy();  
         unbindService(serviceConnection);  
         Log.i(LOG_TAG, "Broadcast Activity onDestroy.");
     }  
   
     private BroadcastReceiver counterBroadcastReceiver = new BroadcastReceiver(){  
		@Override
		public void onReceive(Context context, Intent intent) {
			 int counter = intent.getIntExtra(CounterService.COUNTER_VALUE, 0);  
             String text = String.valueOf(counter);  
             counterText.setText(text);  
               
             Log.i(LOG_TAG, "Receive counter event");  
		}  
     };  
       
     private ServiceConnection serviceConnection = new ServiceConnection() { 
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			counterService = ((CounterService.CounterServiceBinder)service).getService();
			Log.i(LOG_TAG, "Counter Service Connected");  
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			counterService = null;  
			Log.i(LOG_TAG, "Counter Service Disconnected");  
		}  
     };  

}
