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
		//// ����GifͼƬԴ  
		//setGifImage(byte[] gif)
		//setGifImage(InputStream is)
		//setGifImage(int resId)
		gf1.setGifImage(R.drawable.skateboard); 
		// ��Ӽ�����  
		gf1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				gf1.showAnimation();
			}
		}); 
		// ������ʾ�Ĵ�С���������ѹ��  
		gf1.setShowDimension(290, 278); 
		//// ���ü��ط�ʽ��
		//�ȼ��غ���ʾ
		//GifImageType.WAIT_FINISH
		//�߼��ر���ʾ
		//GifImageType.SYNC_DECODER
		//ֻ��ʾ��һ֡����ʾ  
		gf1.setGifImageType(GifImageType.COVER);
		gf1.showCover();
	}
}
