package com.androidhive.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.util.Log;

public class FlashlightController {
    private boolean isFlashOn;
    private boolean hasFlash;
	
    private Camera camera;
    Parameters params;
    
    MediaPlayer mp;
    Context context;
    
    
    public FlashlightController(Context _context){
    	this.context = _context;
    }
    
    public Boolean isSupport(){
    	hasFlash = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    	if(!hasFlash){
            Log.d("Error: ", "Sorry, your device doesn't support flash light!");
    	}
		return hasFlash;
    }
    
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }
    
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            //playSound();
             
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
             
            // changing button/switch image
            //toggleButtonImage();
        }
     
    }
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            //playSound();
             
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
             
            // changing button/switch image
            //toggleButtonImage();
        }
    }
    
    private void releaseCamera() {
         
        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
    
    
    /*
    private void playSound(){
        if(isFlashOn){
            mp = MediaPlayer.create(MainActivity.this, R.raw.light_switch_off);
        }else{
            mp = MediaPlayer.create(MainActivity.this, R.raw.light_switch_on);
        }
        mp.setOnCompletionListener(new OnCompletionListener() {
     
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        }); 
        mp.start();
    }
    */
}
