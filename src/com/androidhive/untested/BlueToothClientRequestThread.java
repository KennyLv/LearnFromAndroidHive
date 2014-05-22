package com.androidhive.untested;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class BlueToothClientRequestThread extends Thread {  
    private final BluetoothSocket mmSocket;  
    private final BluetoothDevice mmDevice;
	private BluetoothAdapter mBluetoothAdapter;
	
	private UUID MY_UUID = UUID.fromString("b716ed09-51d7-41fc-8f7b-7c0d35246441");
    
    public BlueToothClientRequestThread(BluetoothDevice device) {  
        // Use a temporary object that is later assigned to mmSocket,  
        // because mmSocket is final  
        BluetoothSocket tmp = null;  
        mmDevice = device;  
  
        // Get a BluetoothSocket to connect with the given BluetoothDevice  
        try {  
        	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            // MY_UUID is the app's UUID string, also used by the server code  
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);  
        } catch (IOException e) { }  
        mmSocket = tmp;  
    }  

    public void run() {  
        // Cancel discovery because it will slow down the connection  
        mBluetoothAdapter.cancelDiscovery();  
  
        try {  
            // Connect the device through the socket. This will block  
            // until it succeeds or throws an exception  
            mmSocket.connect();  
        } catch (IOException connectException) {  
            // Unable to connect; close the socket and get out  
            try {  
                mmSocket.close();  
            } catch (IOException closeException) { }  
            return;  
        }  
  
        //// Do work to manage the connection (in a separate thread)  
        //TODO : manageConnectedSocket(mmSocket);  
    }  
  
    /** Will cancel an in-progress connection, and close the socket */  
    public void cancel() {  
        try {  
            mmSocket.close();  
        } catch (IOException e) { }  
    }  

}
