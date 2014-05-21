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
		// ����UriMatcher.NO_MATCH��ʾ��ƥ���κ�·���ķ�����
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
				id = db.insert("tester", "name", values);// ���ص��Ǽ�¼���кţ�����Ϊint��ʵ���Ͼ�������ֵ
				return ContentUris.withAppendedId(uri, id);
			case multiCode:
				id = db.insert("tester", "name", values);
				String path = uri.toString();
				//long updatedId = ContentUris.parseId(uri);
				return Uri.parse(path.substring(0, path.lastIndexOf("/"))+id); // �滻��id
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
            // ����ķ������ڴ�URI�н�����id
            long personid = ContentUris.parseId(uri);
            String where = "id=" + personid;// ɾ��ָ��id�ļ�¼
            where += !TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "";// ����������������
            count = db.delete("person", where, selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
		//ContentProvider�������ݱ仯ʱ��֪ͨע���ڴ�URI�ϵķ�����
		getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return count;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}
}
