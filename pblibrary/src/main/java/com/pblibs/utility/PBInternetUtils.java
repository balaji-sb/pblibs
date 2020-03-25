package com.pblibs.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.pblibrary.proggyblast.R;
import com.pblibs.base.PBApplication;

import static com.pblibs.utility.PBConstants.ONE;
import static com.pblibs.utility.PBConstants.ZERO;

public class PBInternetUtils {

    private final static int TYPE_WIFI = 1;
    private final static int TYPE_MOBILE = 2;
    private final static int TYPE_NOT_CONNECTED = 0;
    private static PBInternetUtils mInstance;
    private Context mContext;

    public PBInternetUtils() {
        this.mContext = PBApplication.getInstance().getContext();
    }

    public static PBInternetUtils getInstance() {
        if (mInstance == null) {
            synchronized (PBInternetUtils.class) {
                if (mInstance == null) {
                    mInstance = new PBInternetUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * Check internet is connected or not
     *
     * @return
     */

    public boolean isInternetAvailable() {
        boolean isConnected = false;
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    /**
     * Get Internet connectivity status
     *
     * @return
     */

    public int getConnectivityStatus() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm != null ? cm.getActiveNetworkInfo() : null;
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    /**
     * Get Internet connectivity status as message
     *
     * @return
     */

    public String getConnectivityStatusString() {
        int conn = getConnectivityStatus();
        String status;
        if (conn == TYPE_WIFI) {
            status = mContext.getString(R.string.wifi_enabled) + ONE;
        } else if (conn == TYPE_MOBILE) {
            status = mContext.getString(R.string.mobiledata_enabled) + ONE;
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = mContext.getString(R.string.not_connected) + ZERO;
        } else {
            status = mContext.getString(R.string.not_connected) + ZERO;
        }
        return status;
    }

}
