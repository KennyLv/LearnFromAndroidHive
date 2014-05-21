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
		//content://com.android.contacts/contacts //操作的数据是联系人信息Uri
		//content://com.android.contacts/data/phones //联系人电话Uri
		//content://com.android.contacts/data/emails //联系人Email Uri
		
		//注册事件，监测数据变化
		resolver.registerContentObserver(uri, true, new DataChangeObserver(new Handler()));
		
		/*
		//添加一条记录
		ContentValues values = new ContentValues();
		values.put("name", "linjiqin");
		values.put("age", 25);
		resolver.insert(uri, values);
		
		
		//获取tester表中所有记录
		Cursor cursor = resolver.query(uri, null, null, null, "id desc");
		while(cursor.moveToNext()){
		   Log.i("ContentTest", "id="+ cursor.getInt(0)+ ",name="+ cursor.getString(1));
		}
		//把id为1的记录的name字段值更改新为zhangsan
		ContentValues updateValues = new ContentValues();
		updateValues.put("name", "zhangsan");
		Uri updateIdUri = ContentUris.withAppendedId(uri, 2);
		resolver.update(updateIdUri, updateValues, null, null);
		//删除id为2的记录
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
