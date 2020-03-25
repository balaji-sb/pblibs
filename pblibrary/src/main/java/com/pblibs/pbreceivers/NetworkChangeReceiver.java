package com.pblibs.pbreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.pblibs.utility.PBInternetUtils;

import static com.pblibs.utility.PBConstants.ZERO;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = PBInternetUtils.getInstance().getConnectivityStatusString();
        String[] networkStatus = status.split(",");
        try {
            if (ZERO == Integer.parseInt(networkStatus[1])) {
                //network error layout show visibility
                //MainActivity.network_error_layout.setVisibility(View.VISIBLE);
            } else {
                //network error layout hide visibility
                //MainActivity.network_error_layout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
