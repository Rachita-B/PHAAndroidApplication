package com.example.allphaapplication;
//using SharedPreferenes. Inside SharedPreferences we will store the data of currently logged in user. So that we can use it in our application.
//here for this class we are using a singleton pattern

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

//import com.google.mlkit.common.sdkinternal.SharedPrefManager;

public class  SharedPrefmanager{

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";

    private static  SharedPrefmanager mInstance;
    private static Context mCtx;

    private SharedPrefmanager(Context context) {
        mCtx = context;
    }

    public static synchronized  SharedPrefmanager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new  SharedPrefmanager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(UserLogin user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_GENDER, user.getGender());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public UserLogin getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserLogin(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_GENDER, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        //  mCtx.startActivity(new Intent(mCtx, MainActivity.class));
        mCtx.startActivity(new Intent(mCtx, MakingCalls.class));
    }
}

//}