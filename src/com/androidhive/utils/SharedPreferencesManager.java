package com.androidhive.utils;

import java.util.HashMap;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesManager {
	// Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
    // Sharedpref file name
    private static final String PREF_NAME = "Login_Demo_Test";
    
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
     
    // Constructor
    public SharedPreferencesManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    
    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email){
    	editor.putBoolean(IS_LOGIN, true);
    	editor.putString(KEY_NAME, name);
    	editor.putString(KEY_EMAIL, email);
    	editor.commit();
    	/*
    	editor.putBoolean("key_name", true); // Storing boolean - true/false
		editor.putString("key_name", "string value"); // Storing string
		editor.putInt("key_name", "int value"); // Storing integer
		editor.putFloat("key_name", "float value"); // Storing float
		editor.putLong("key_name", "long value"); // Storing long
    	*/
    }
    
    /**
     * * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        return user;
        /*
        // returns stored preference value
		// If value is not present return second param value - In this case null
		pref.getString("key_name", null); // getting String
		pref.getInt("key_name", null); // getting Integer
		pref.getFloat("key_name", null); // getting Float
		pref.getLong("key_name", null); // getting Long
		pref.getBoolean("key_name", null); // getting boolean
        */
    }
    
    /**
     * * remove part of stored data
     * */
    public void deleteMail(){
    	editor.remove(KEY_EMAIL);
    	editor.commit();
    }
    
    /**
     * Clear session details
     * */
    public void logoutUser(){
    	editor.clear();
    	editor.commit();
    }
}
