package com.androidhive.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "modelManager";
	private static final CursorFactory CURSOR_FACTORY = null;
	// Contacts table name
	private static final String TABLE_MODELS = "models";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PRICE = "price";
	private static final String KEY_DES = "description";
	private static final String KEY_PH_NO = "phone_number";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, CURSOR_FACTORY, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MODELS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_PRICE + " TEXT," + KEY_DES + " TEXT," + KEY_PH_NO	+ " TEXT)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODELS);
		onCreate(db);
	}

	// Adding new model
	public void addModel(DatabaseHandlerTestModel model) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, model.getName());
		values.put(KEY_PRICE, model.getPrice());
		values.put(KEY_DES, model.getDescription());
		values.put(KEY_PH_NO, model.getPhoneNumber());

		// Inserting Row
		db.insert(TABLE_MODELS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single model
	public DatabaseHandlerTestModel getModel(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MODELS, new String[] { KEY_ID, KEY_NAME,
				KEY_PH_NO }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		DatabaseHandlerTestModel model = new DatabaseHandlerTestModel(
				Integer.parseInt(cursor.getString(0)), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), cursor.getString(4));
		// return model
		return model;
	}

	// Getting All Models
	public List<DatabaseHandlerTestModel> getAllModels() {
		List<DatabaseHandlerTestModel> modelList = new ArrayList<DatabaseHandlerTestModel>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_MODELS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				DatabaseHandlerTestModel model = new DatabaseHandlerTestModel();
				model.setID(Integer.parseInt(cursor.getString(0)));
				model.setName(cursor.getString(1));
				model.setPrice(cursor.getString(2));
				model.setDescription(cursor.getString(3));
				model.setPhoneNumber(cursor.getString(4));
				// Adding model to list
				modelList.add(model);
			} while (cursor.moveToNext());
		}

		// return model list
		return modelList;
	}

	// Getting models Count
	public int getModelsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_MODELS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		// return model
		return cursor.getCount();
	}

	// Updating single model
	public int updateModel(DatabaseHandlerTestModel model) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, model.getName());
		values.put(KEY_PRICE, model.getPrice());
		values.put(KEY_DES, model.getDescription());
		values.put(KEY_PH_NO, model.getPhoneNumber());

		// updating row
		return db.update(TABLE_MODELS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(model.getID()) });
	}

	// Deleting single model
	public void deleteModel(DatabaseHandlerTestModel model) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MODELS, KEY_ID + " = ?",
				new String[] { String.valueOf(model.getID()) });
		db.close();
	}
}
