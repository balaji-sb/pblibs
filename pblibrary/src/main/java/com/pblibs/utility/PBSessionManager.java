package com.pblibs.utility;

import android.content.Context;
import android.content.SharedPreferences;
import com.pblibs.base.PBApplication;
import com.pblibs.pbinterfaces.LogoutCallback;

/**
 * Created  by Proggy Blast on 9/8/19.
 */

public class PBSessionManager {

    private static PBSessionManager mInstance;
    private static SharedPreferences mPreference;
    private static SharedPreferences.Editor mEditor;
    private Context mContext;
    private PBUtils mPbUtils;

    private PBSessionManager() {
        mContext = PBApplication.getInstance().getContext();
        mPbUtils = PBUtils.getInstance();
        mPreference = mContext.getSharedPreferences(PBConstants.PREF_NAME, PBConstants.PRIVATE_MODE);
        mEditor = mPreference.edit();
    }

    public static PBSessionManager getInstance() {
        if (mInstance == null) {
            synchronized (PBSessionManager.class) {
                if (mInstance == null) {
                    mInstance = new PBSessionManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * To check if the app is launch first time or not
     *
     * @return
     */

    public static boolean isFirstTimeLaunch() {
        return mPreference.getBoolean(PBConstants.IS_FIRST_TIME_LAUNCH, true);
    }

    /**
     * To set the app is launched first time or not
     *
     * @param isFirstTime
     */

    public static void setFirstTimeLaunch(boolean isFirstTime) {
        mEditor.putBoolean(PBConstants.IS_FIRST_TIME_LAUNCH, isFirstTime);
        mEditor.commit();
    }

    /**
     * To check user is logged in status
     *
     * @return
     */

    public static boolean isUserLoggedIn() {
        if (getUserId().isEmpty() || getUserId().trim().length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * To chcek if user id is available or not
     *
     * @return
     */

    public static String getUserId() {
        return mPreference.getString(PBConstants.USER_ID, "");
    }

    /**
     * logout user
     *
     * @param callback
     */

    public static void logoutUser(LogoutCallback callback) {
        mEditor.clear();
        mEditor.commit();
        callback.onLogoutSuccess();
    }

    /**
     * set the string param with value
     */

    public static void setString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * set the string param with value
     */

    public static String getString(String key, String defaultValue) {
        return mPreference.getString(key, defaultValue);
    }

    /**
     * set the float param with value
     */

    public static void setFloat(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    /**
     * set the string param with value
     */

    public static float getFloat(String key, float defaultValue) {
        return mPreference.getFloat(key, defaultValue);
    }

    /**
     * set the boolean param with value
     */

    public static void setBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    /**
     * get the boolean param with value
     */

    public static boolean getBoolean(String key, boolean defaultValue) {
        return mPreference.getBoolean(key, defaultValue);
    }

    /**
     * set the int param with value
     */

    public static void setInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    /**
     * get the int param with value
     */

    public static int getInt(String key, int defaultValue) {
        return mPreference.getInt(key, defaultValue);
    }

}
