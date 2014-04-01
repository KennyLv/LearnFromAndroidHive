package com.androidhive.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidhive.utils.ImageLoader;
import com.fromandroidhive.androidlearn.R;

public class PlayerScreenOrientationActivity extends Activity {
	MediaPlayer mp;
	
	String song_id;
	String song_title;
	String song_artist;
	String song_thumburl;
	
	String song_strPath;
	
	TextView song_title_view;
	ImageView song_art_view;
	ImageButton but_play;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_screen_orientation);

		Intent intent = getIntent();
		song_id = intent.getStringExtra("id");
		song_title = intent.getStringExtra("title");
		song_artist = intent.getStringExtra("artist");
		song_thumburl = intent.getStringExtra("thumb_url");
		
		song_strPath = "file:///android_asset/mp3/Country_Road.mp3";
		
		song_title_view = (TextView)findViewById(R.id.player_title);
		song_art_view = (ImageView)findViewById(R.id.player_art);
		but_play = (ImageButton) findViewById(R.id.player_btnPlay);
		
		song_title_view.setText(song_title);
		ImageLoader imageLoader=new ImageLoader(PlayerScreenOrientationActivity.this.getApplicationContext());
		imageLoader.DisplayImage(song_thumburl, song_art_view);

		//mp = new MediaPlayer();
		mp = MediaPlayer.create(PlayerScreenOrientationActivity.this, R.raw.country_road);
		
		but_play.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(mp.isPlaying()){
					Log.d("===","is playing ...");
					
					// Pause audio
					mp.pause();	
					but_play.setImageDrawable(getResources().getDrawable(R.drawable.player_play));
				}else{
					Log.d("===","is stopped ...");
					mp.start();	

					/*
					if (mp != null) {
					 	mp.stop();
					 	//mp.reset();
					}

					try {
						////Get from local
						mp.setDataSource(song_strPath);
						mp.prepareAsync();
						
						//// Get from SD card:
						//File file = new File(song_strPath); 
						//FileInputStream fis = new FileInputStream(file); 
						//mp.setDataSource(fis.getFD()); 
						//mp.prepare();
						
						
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// Play audio
					//mp.start();		 
					// Pause audio
					//mp.pause();		 
					// Reset mediaplayer
					//mp.reset();		 
					// Get song length duration - in milliseconds
					//mp.getDuration();		 
					// Get current duration - in milliseconds
					//mp.getCurrentPosition();		 
					// Move song to particular second - used for Forward or Backward
					//mp.seekTo(3000); // position in milliseconds		 
					// Check if song is playing or not
					//mp.isPlaying(); // returns true or false
					*/
					
					/*
					 // get current song position
		                int currentPosition = mp.getCurrentPosition();
		                // check if seekBackward time is greater than 0 sec
		                if(currentPosition - seekBackwardTime >= 0){
		                    // forward song
		                    mp.seekTo(currentPosition - seekBackwardTime);
		                }else{
		                    // backward to starting position
		                    mp.seekTo(0);
		                }
					 */
					
										
					but_play.setImageDrawable(getResources().getDrawable(R.drawable.player_stop));
				}
			}
		});
		
		
	}
}
