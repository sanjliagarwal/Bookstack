package com.example.sharingbooks;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SaveSharedPreferences {
	    static final String PREF_USER_NAME= "mail";

	    static SharedPreferences getSharedPreferences(Context ctx) {
	        return PreferenceManager.getDefaultSharedPreferences(ctx);
	    }

	    public static void setUserName(Context ctx, String mail) 
	    {
	        Editor editor = getSharedPreferences(ctx).edit();
	        editor.putString(PREF_USER_NAME, mail);
	        editor.commit();
	    }

	    public static String getUserName(Context ctx)
	    {
	        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
	    }
}
