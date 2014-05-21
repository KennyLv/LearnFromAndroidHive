package com.androidhive.fun;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.androidhive.androidlearn.R;

public class BlueToothActivity extends Activity {
	private BluetoothAdapter adapter;
	LinearLayout thisLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blue_tooth);
		
		adapter = BluetoothAdapter.getDefaultAdapter();
		//直接打开蓝牙  
		adapter.enable();  
		/*
		//关闭蓝牙  
		adapter.disable();
		 
		//打开蓝牙设置面板  
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);  
		startActivityForResult(intent, 0x1);
		
		//打开蓝牙发现功能（默认打开120秒，可以将时间最多延长至300秒）  
		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);  
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);//设置持续时间（最多300秒）
		*/
		

        LayoutInflater inflater = (LayoutInflater)BlueToothActivity.this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        thisLayout = (LinearLayout) inflater.inflate(R.layout.activity_blue_tooth, null);
	}

	@Override
	public void onResume() {
		super.onResume();
		adapter.startDiscovery();
		// 注册BroadcastReceiver  
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND); //找到设备
		/*
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);  //开始搜索
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED); //搜索结束
		filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);  
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);  
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);  
		filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
		filter.addAction(BluetoothDevice.ACTION_UUID); 
		filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);*/
		
		registerReceiver(mReceiver, filter);
        Log.d("BlueTooth","register Receiver for mReceiver ...");
	}

	@Override
	public void onPause() { 
        super.onPause();
        adapter.cancelDiscovery();
		unregisterReceiver(mReceiver); 
	}
	
	// 创建一个接收ACTION_FOUND广播的BroadcastReceiver  
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
	    public void onReceive(Context context, Intent intent) {  
	        String action = intent.getAction();
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	            final BluetoothDevice bDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE); 
	            //BluetoothClass bClass = intent.getParcelableExtra(BluetoothDevice.EXTRA_CLASS);

	            String deviceInfo="";
	            
	            /*
				Bundle b = intent.getExtras();
	            Object[] lstName = b.keySet().toArray();  
	            // 显示所有收到的消息及其细节  
	            for (int i = 0; i < lstName.length; i++) {  
	                String keyName = lstName[i].toString();  
	                deviceInfo += keyName + " - " + String.valueOf(b.get(keyName)) + "\n";  
	            }*/
	            
	            deviceInfo = bDevice.getName() + " \n " +  bDevice.getAddress();
	            TextView textView = new TextView(BlueToothActivity.this);
	            textView.setText(deviceInfo);
	            textView.setHeight(200);

	            textView.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View arg0, MotionEvent event) {

						adapter.cancelDiscovery();
						if (bDevice.getBondState() == BluetoothDevice.BOND_NONE) {
							Log.d("Bluetooth","BOND_NONE .... ");
							try {
								createBond(bDevice.getClass(), bDevice);
							} catch (Exception e) {
								e.printStackTrace();
							}  							
						}else if(bDevice.getBondState() == BluetoothDevice.BOND_BONDED){
							Log.d("Bluetooth","BOND_BONDED .... ");
							/*
							try {
								//bDevice.getUuids();
								BluetoothSocket btSocket = bDevice.createRfcommSocketToServiceRecord(UUID.fromString(""));
								btSocket.connect();
								btSocket.getInputStream();
								btSocket.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							*/
						}else{
							Log.d("Bluetooth","BOND_BONDING .... ");
						}
						
						//打开配对界面
						//Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
						//createBondMethod.invoke(device);
						
						//
						//BluetoothDevice device = adapter.getRemoteDevice(address);
						//m_ChatService.connect(device);
						
						return false;
					}
	            	
	            });
	            thisLayout.addView(textView);
	            //thisLayout.invalidate();
	            //thisLayout.postInvalidate();
	            //thisLayout.refreshDrawableState();
	            BlueToothActivity.this.setContentView(thisLayout);

	        }  
	    }  
	};

	// 自动配对设置Pin值
	static public boolean autoBond(Class btClass, BluetoothDevice btDevice, String strPin)
	        throws Exception {
	    Method autoBondMethod = btClass.getMethod("setPin", new Class[] { byte[].class });
	    Boolean result = (Boolean) autoBondMethod.invoke(btDevice, new Object[] { strPin.getBytes() });
	    return result;
	}
	
	/** 
     * 与设备配对 参考源码：platform/packages/apps/Settings.git 
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java 
     */
	static public boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception {
	    Method createBondMethod = btClass.getMethod("createBond");
	    Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
	    return returnValue.booleanValue();
	}
  
    /** 
     * 与设备解除配对 参考源码：platform/packages/apps/Settings.git 
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java 
     */  
    static public boolean removeBond(Class btClass,BluetoothDevice btDevice) throws Exception {  
        Method removeBondMethod = btClass.getMethod("removeBond");  
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);  
        return returnValue.booleanValue();  
    }

    
    /** 
     *  反射获取隐藏API
     */  
    static public void printAllInform(Class clsShow) {  
        try {  
            // 取得所有方法  
            Method[] hideMethod = clsShow.getMethods();  
            int i = 0;  
            for (; i < hideMethod.length; i++) {  
                Log.e("method name", hideMethod[i].getName());  
            }  
            // 取得所有常量  
            Field[] allFields = clsShow.getFields();  
            for (i = 0; i < allFields.length; i++) {  
                Log.e("Field name", allFields[i].getName());  
            }  
        } catch (SecurityException e) {  
            // throw new RuntimeException(e.getMessage());  
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {  
            // throw new RuntimeException(e.getMessage());  
            e.printStackTrace();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }
    
    
}
