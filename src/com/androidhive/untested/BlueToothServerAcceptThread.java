package com.androidhive.untested;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class BlueToothServerAcceptThread extends Thread {
	private final BluetoothServerSocket mmServerSocket;
	private BluetoothAdapter mBluetoothAdapter;

	private String NAME = "";
	private UUID MY_UUID = UUID.fromString("b716ed09-51d7-41fc-8f7b-7c0d35246441");
	
    public BlueToothServerAcceptThread() {  
        // Use a temporary object that is later assigned to mmServerSocket,  
        // because mmServerSocket is final  
        BluetoothServerSocket tmp = null;  
        try {  
        	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			// MY_UUID is the app's UUID string, also used by the client code  
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);  
        } catch (IOException e) { }  
        mmServerSocket = tmp;  
    }  
  
    public void run() {  
        BluetoothSocket socket = null;  
        // Keep listening until exception occurs or a socket is returned  
        while (true) {  
            try {  
                socket = mmServerSocket.accept();  
            } catch (IOException e) {  
                break;  
            }  
            // If a connection was accepted  
            if (socket != null) {  
                try {
                    //// Do work to manage the connection (in a separate thread)  
                    //TODO : manageConnectedSocket(socket);  
					mmServerSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
                break;  
            }  
        }  
    }  
  
    /** Will cancel the listening socket, and cause the thread to finish */  
    public void cancel() {  
        try {  
            mmServerSocket.close();  
        } catch (IOException e) { }  
    }  
}
