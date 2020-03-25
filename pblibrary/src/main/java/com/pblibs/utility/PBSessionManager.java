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
    private SharedPreferences mPreference;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private PBUtils mPbUtils;

    PBSessionManager() {
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

    public boolean isFirstTimeLaunch() {
        return mPreference.getBoolean(PBConstants.IS_FIRST_TIME_LAUNCH, true);
    }

    /**
     * To set the app is launched first time or not
     *
     * @param isFirstTime
     */

    public void setFirstTimeLaunch(boolean isFirstTime) {
        mEditor.putBoolean(PBConstants.IS_FIRST_TIME_LAUNCH, isFirstTime);
        mEditor.commit();
    }

    /**
     * To check user is logged in status
     *
     * @return
     */

    public boolean isUserLoggedIn() {
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

    public String getUserId() {
        return mPreference.getString(PBConstants.USER_ID, "");
    }

    /**
     * logout user
     *
     * @param callback
     */

    public void logoutUser(LogoutCallback callback) {
        mEditor.clear();
        mEditor.commit();
        callback.onLogoutSuccess();
    }

}
