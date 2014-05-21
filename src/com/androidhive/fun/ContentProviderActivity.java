package com.androidhive.fun;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.androidhive.androidlearn.R;

public class ContentProviderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_provider);
		
		
		
		ContentResolver resolver =  getContentResolver();
		Uri uri = Uri.parse("content://com.androidhive.providertest/tester");
		//content://com.android.contacts/contacts //��������������ϵ����ϢUri
		//content://com.android.contacts/data/phones //��ϵ�˵绰Uri
		//content://com.android.contacts/data/emails //��ϵ��Email Uri
		
		//ע���¼���������ݱ仯
		resolver.registerContentObserver(uri, true, new DataChangeObserver(new Handler()));
		
		/*
		//���һ����¼
		ContentValues values = new ContentValues();
		values.put("name", "linjiqin");
		values.put("age", 25);
		resolver.insert(uri, values);
		
		
		//��ȡtester�������м�¼
		Cursor cursor = resolver.query(uri, null, null, null, "id desc");
		while(cursor.moveToNext()){
		   Log.i("ContentTest", "id="+ cursor.getInt(0)+ ",name="+ cursor.getString(1));
		}
		//��idΪ1�ļ�¼��name�ֶ�ֵ������Ϊzhangsan
		ContentValues updateValues = new ContentValues();
		updateValues.put("name", "zhangsan");
		Uri updateIdUri = ContentUris.withAppendedId(uri, 2);
		resolver.update(updateIdUri, updateValues, null, null);
		//ɾ��idΪ2�ļ�¼
		Uri deleteIdUri = ContentUris.withAppendedId(uri, 2);
		resolver.delete(deleteIdUri, null, null);
		*/
		
	}
	
	public class DataChangeObserver extends ContentObserver{
	   public DataChangeObserver(Handler handler) {
	      super(handler);
	   }
	   public void onChange(boolean selfChange) {
		   Log.i("ContentTest", "data chenaged...");
	   }
	}
}
