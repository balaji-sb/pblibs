package com.pblibs.base;

/**
 * Created  by Proggy Blast on 7/7/19.
 */

import android.app.Application;
import android.content.Context;

public class PBApplication extends Application {

    public static final String TAG = PBApplication.class.getSimpleName();
    public static PBApplication mInstance;
    private Context mContext;
    private String mAppName;
    private String mPackageName;

    public static PBApplication getInstance() {
        if (mInstance == null) {
            synchronized (PBApplication.class) {
                if (mInstance == null) {
                    mInstance = new PBApplication();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        this.mAppName = appName;
    }

    public String getPackageName() {
        if(mContext!=null){
            this.mPackageName=mContext.getPackageName();
        }
        return mPackageName;
    }
}
