package com.androidhive.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.androidhive.androidlearn.R;

public class GifViewDemoActivity extends Activity {
	GifView gf1 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gif_view_demo);
		
		gf1 = (GifView) findViewById(R.id.demo_gifview); 
		//// 设置Gif图片源  
		//setGifImage(byte[] gif)
		//setGifImage(InputStream is)
		//setGifImage(int resId)
		gf1.setGifImage(R.drawable.skateboard); 
		// 添加监听器  
		gf1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				gf1.showAnimation();
			}
		}); 
		// 设置显示的大小，拉伸或者压缩  
		gf1.setShowDimension(290, 278); 
		//// 设置加载方式：
		//先加载后显示
		//GifImageType.WAIT_FINISH
		//边加载边显示
		//GifImageType.SYNC_DECODER
		//只显示第一帧再显示  
		gf1.setGifImageType(GifImageType.COVER);
		gf1.showCover();
	}
}
