package com.phoenixmarketcity.android.phoenix.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.phoenixmarketcity.android.phoenix.PhoenixApplication;

/**
 * Created by neeraj.nayan on 06/08/15.
 */
public class Preferences {
    private final String PREFERENCE_FILE_SUFFIX = "_preferences";
    private static final String PREF_USER_ID = "pref_user_id";
    private static final String PREF_ACCESS_TOKEN = "pref_user_id";
    private static final String PREF_GOOGLE_PLUS_NAME = "pref_google_plus_name";
    private static final String PREF_GOOGLE_PLUS_EMAIL_ID = "pref_google_plus_email_id";
    private static final String PREF_GOOGLE_PLUS_PHOTO_URL = "pref_google_plus_photoUrl";
    private static final String PREF_SENT_TOKEN_TO_SERVER = "pref_sent_token_to_server";

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEdit;

    private static class Holder {
        static final Preferences INSTANCE = new Preferences();
    }

    // Returns singleton instance
    public static Preferences getInstance() {
        return Holder.INSTANCE;
    }

    private Preferences() {
        Application app = PhoenixApplication.getInstance();
        mPrefs = app.getSharedPreferences(app.getPackageName() +
                PREFERENCE_FILE_SUFFIX, Context.MODE_PRIVATE);
        mEdit = mPrefs.edit();
    }

    //--------- Credentials STARTS -----------
    public static String getUserId() {
        return Preferences.getInstance().mPrefs.getString(PREF_USER_ID, "");
    }

    public static void setUserId(String userId) {
        Preferences.getInstance().mEdit.putString(PREF_USER_ID, userId);
        save();
    }

    public static String getAccessToken() {
        return Preferences.getInstance().mPrefs.getString(PREF_ACCESS_TOKEN, "");
    }

    public static void setAccessToken(String accessToken) {
        Preferences.getInstance().mEdit.putString(PREF_ACCESS_TOKEN, accessToken);
        save();
    }
    //---------- Credentials ENDS ------------


    public static String getGooglePlusName() {
        return Preferences.getInstance().mPrefs.getString(PREF_GOOGLE_PLUS_NAME, "");
    }

    public static void setGooglePlusName(String name) {
        Preferences.getInstance().mEdit.putString(PREF_GOOGLE_PLUS_NAME, name);
        save();
    }

    public static String getGooglePlusEmailId() {
        return Preferences.getInstance().mPrefs.getString(PREF_GOOGLE_PLUS_EMAIL_ID, "");
    }

    public static void setGooglePlusEmailId(String emailId) {
        Preferences.getInstance().mEdit.putString(PREF_GOOGLE_PLUS_EMAIL_ID, emailId);
        save();
    }

    public static String getGooglePlusPhotoUrl() {
        return Preferences.getInstance().mPrefs.getString(PREF_GOOGLE_PLUS_PHOTO_URL, "");
    }

    public static void setGooglePlusPhotoUrl(String photoUrl) {
        Preferences.getInstance().mEdit.putString(PREF_GOOGLE_PLUS_PHOTO_URL, photoUrl);
        save();
    }

    public static boolean getGCMSentTokenToServer() {
        return Preferences.getInstance().mPrefs.getBoolean(PREF_SENT_TOKEN_TO_SERVER, false);
    }

    public static void setGCMSentTokenToServer(boolean value) {
        Preferences.getInstance().mEdit.putBoolean(PREF_SENT_TOKEN_TO_SERVER, value);
        save();
    }




    // Save content
    private static void save() {
        Preferences.getInstance().mEdit.apply();
    }
}
