package com.androidhive.fun;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

import com.androidhive.androidlearn.R;
import com.androidhive.ui.GridViewDemoActivity;

public class NotificationActivity extends Activity {

	private Context mContext;
	private Button showButton, cancelButton;
	private Notification mNotification;
	private NotificationManager mNotificationManager;
	private final static int NOTIFICATION_ID = 0x0001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);

		mContext = NotificationActivity.this;
		mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

		//mNotification = new Notification(R.drawable.notify, "You have a message!", System.currentTimeMillis());
		mNotification = new Notification();
		mNotification.icon = R.drawable.notify;
		mNotification.tickerText = "You have a message!";
		mNotification.when = System.currentTimeMillis();
		/*
		* 添加声音
		* notification.defaults |=Notification.DEFAULT_SOUND;
		* 或者使用以下几种方式
		* notification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");
		* notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
		* 如果想要让声音持续重复直到用户对通知做出反应，则可以在notification的flags字段增加"FLAG_INSISTENT"
		* 如果notification的defaults字段包括了"DEFAULT_SOUND"属性，则这个属性将覆盖sound字段中定义的声音
		*/
		mNotification.defaults = Notification.DEFAULT_SOUND;
		/*
		* 添加振动
		* notification.defaults |= Notification.DEFAULT_VIBRATE;
		* 或者可以定义自己的振动模式：
		* long[] vibrate = {0,100,200,300}; //0毫秒后开始振动，振动100毫秒后停止，再过200毫秒后再次振动300毫秒
		* notification.vibrate = vibrate;
		* long数组可以定义成想要的任何长度
		* 如果notification的defaults字段包括了"DEFAULT_VIBRATE",则这个属性将覆盖vibrate字段中定义的振动

		* 添加LED灯提醒
		* notification.defaults |= Notification.DEFAULT_LIGHTS;
		* 或者可以自己的LED提醒模式:
		* notification.ledARGB = 0xff00ff00;
		* notification.ledOnMS = 300; //亮的时间
		* notification.ledOffMS = 1000; //灭的时间
		* notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		*/
		
		/*
		* 更多的特征属性
		* notification.flags |= FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知
		* notification.flags |= FLAG_INSISTENT; //重复发出声音，直到用户响应此通知
		* notification.flags |= FLAG_ONGOING_EVENT; //将此通知放到通知栏的"Ongoing"即"正在运行"组中
		* notification.flags |= FLAG_NO_CLEAR; //表明在点击了通知栏中的"清除通知"后，此通知不清除，
		* //经常与FLAG_ONGOING_EVENT一起使用
		* notification.number = 1; //number字段表示此通知代表的当前事件数量，它将覆盖在状态栏图标的顶部
		* //如果要使用此字段，必须从1开始
		* notification.iconLevel = ; //
		*/
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;

		showButton = (Button) findViewById(R.id.noti_btn_send);
		cancelButton = (Button) findViewById(R.id.noti_btn_cancel);
		showButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(mContext, GridViewDemoActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PendingIntent mContentIntent = PendingIntent.getActivity(mContext, 0, mIntent, 0);

				RemoteViews contentView = new RemoteViews(getPackageName(),R.layout.activity_notification_selfdesign);
				contentView.setImageViewResource(R.id.notification_icon, R.drawable.notify);
				contentView.setTextViewText(R.id.notification_title, "Love you...");
				contentView.setTextViewText(R.id.notification_text, "This message is in a custom expanded view");

				//mNotification.setLatestEventInfo(mContext, "I Love You", "conetnt goes here...", mContentIntent);
				
				mNotification.contentView = contentView;
				mNotification.contentIntent = mContentIntent;
				
				mNotificationManager.notify(NOTIFICATION_ID, mNotification);
			}

		});
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mNotificationManager.cancel(NOTIFICATION_ID);
			}
		});

	}

}
