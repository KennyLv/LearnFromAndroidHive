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
		* �������
		* notification.defaults |=Notification.DEFAULT_SOUND;
		* ����ʹ�����¼��ַ�ʽ
		* notification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");
		* notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
		* �����Ҫ�����������ظ�ֱ���û���֪ͨ������Ӧ���������notification��flags�ֶ�����"FLAG_INSISTENT"
		* ���notification��defaults�ֶΰ�����"DEFAULT_SOUND"���ԣ���������Խ�����sound�ֶ��ж��������
		*/
		mNotification.defaults = Notification.DEFAULT_SOUND;
		/*
		* �����
		* notification.defaults |= Notification.DEFAULT_VIBRATE;
		* ���߿��Զ����Լ�����ģʽ��
		* long[] vibrate = {0,100,200,300}; //0�����ʼ�񶯣���100�����ֹͣ���ٹ�200������ٴ���300����
		* notification.vibrate = vibrate;
		* long������Զ������Ҫ���κγ���
		* ���notification��defaults�ֶΰ�����"DEFAULT_VIBRATE",��������Խ�����vibrate�ֶ��ж������

		* ���LED������
		* notification.defaults |= Notification.DEFAULT_LIGHTS;
		* ���߿����Լ���LED����ģʽ:
		* notification.ledARGB = 0xff00ff00;
		* notification.ledOnMS = 300; //����ʱ��
		* notification.ledOffMS = 1000; //���ʱ��
		* notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		*/
		
		/*
		* �������������
		* notification.flags |= FLAG_AUTO_CANCEL; //��֪ͨ���ϵ����֪ͨ���Զ������֪ͨ
		* notification.flags |= FLAG_INSISTENT; //�ظ�����������ֱ���û���Ӧ��֪ͨ
		* notification.flags |= FLAG_ONGOING_EVENT; //����֪ͨ�ŵ�֪ͨ����"Ongoing"��"��������"����
		* notification.flags |= FLAG_NO_CLEAR; //�����ڵ����֪ͨ���е�"���֪ͨ"�󣬴�֪ͨ�������
		* //������FLAG_ONGOING_EVENTһ��ʹ��
		* notification.number = 1; //number�ֶα�ʾ��֪ͨ����ĵ�ǰ�¼�����������������״̬��ͼ��Ķ���
		* //���Ҫʹ�ô��ֶΣ������1��ʼ
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
