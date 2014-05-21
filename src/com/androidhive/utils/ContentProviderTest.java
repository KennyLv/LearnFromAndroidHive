package com.androidhive.utils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class ContentProviderTest extends ContentProvider {
	
	static UriMatcher  uriMatcher;
	static final int signleCode = 1;
	static final int multiCode = 2;
	static {
		// 常量UriMatcher.NO_MATCH表示不匹配任何路径的返回码
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.androidhive.providertest", "tester", signleCode);
		uriMatcher.addURI("com.androidhive.providertest", "tester/#", multiCode);
	}
	
	 private DatabaseHandler dbOpenHelper = null;
	
	@Override
	public boolean onCreate() {
		 dbOpenHelper = new DatabaseHandler(this.getContext());
		 return true;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		
		long id = 0;
		switch (uriMatcher.match(uri)) { //"content://com.androidhive.providertest/tester/10"
			case signleCode:
				id = db.insert("tester", "name", values);// 返回的是记录的行号，主键为int，实际上就是主键值
				return ContentUris.withAppendedId(uri, id);
			case multiCode:
				id = db.insert("tester", "name", values);
				String path = uri.toString();
				//long updatedId = ContentUris.parseId(uri);
				return Uri.parse(path.substring(0, path.lastIndexOf("/"))+id); // 替换掉id
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3, String arg4) {
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		
		//TODO : ...
		
		return 0;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) { 
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		int count = 0;
		switch (uriMatcher.match(uri)) {
        case signleCode:
            count = db.delete("person", selection, selectionArgs);
            break;
        case multiCode:
            // 下面的方法用于从URI中解析出id
            long personid = ContentUris.parseId(uri);
            String where = "id=" + personid;// 删除指定id的记录
            where += !TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "";// 把其它条件附加上
            count = db.delete("person", where, selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
		//ContentProvider发生数据变化时，通知注册在此URI上的访问者
		getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return count;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}
}
