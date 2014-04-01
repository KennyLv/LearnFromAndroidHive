package com.androidhive.ui;

import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.fromandroidhive.androidlearn.R;

public class PlayerScreenVideoActivity extends Activity {
	MediaPlayer mp;
	
	String song_id;
	String song_title;
	String song_artist;

	String video_url;
	
	TextView song_title_view;
	SurfaceView video_view;
	SurfaceHolder surfaceHolder;
	ImageButton but_play;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_screen_video);

		Intent intent = getIntent();
		song_id = intent.getStringExtra("id");
		song_title = intent.getStringExtra("title");
		
		video_url ="http://daily3gp.com/vids/family_guy_penis_car.3gp";
		
		song_title_view = (TextView)findViewById(R.id.player_title);
		video_view = (SurfaceView)findViewById(R.id.player_video);
		but_play = (ImageButton) findViewById(R.id.player_btnPlay);
		
		song_title_view.setText(song_title);
		
		mp = new MediaPlayer();
		try {
			mp.setDataSource(video_url);
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		surfaceHolder = video_view.getHolder();
		surfaceHolder.addCallback(new Callback(){

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
    			Log.d("---","=====surfaceChanged======================");
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
    			Log.d("---","=====surfaceCreated======================");
    			
    			mp.setDisplay(surfaceHolder); 
    			try {
					mp.prepareAsync();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
    			Log.d("---","=====surfaceDestroyed======================");
			}
		});
		

		
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);  
        mp.setOnBufferingUpdateListener(new OnBufferingUpdateListener(){
			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
    			Log.d("---","=====onBufferingUpdate======================");
			}
        });
       
        
        mp.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
    			Log.d("---","=====onPrepared======================");
                mp.start();
            }
		});
		
		but_play.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(mp.isPlaying()){
					Log.d("===","is playing ...");
					
					mp.stop();	
					mp.release();
					
					but_play.setImageDrawable(getResources().getDrawable(R.drawable.player_play));
				}else{
					Log.d("===","is stopped ...");

					mp.start();

					but_play.setImageDrawable(getResources().getDrawable(R.drawable.player_stop));
				}
			}
		});
	}
}
