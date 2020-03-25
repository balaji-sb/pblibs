package com.pblibs.pbreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.pblibrary.proggyblast.R;
import com.pblibs.base.PBApplication;
import com.pblibs.utility.PBFileOperations;
import com.pblibs.utility.PBUtils;
import org.json.JSONObject;

import java.util.Calendar;

import static com.pblibs.utility.PBConstants.*;

/**
 * Created  by Proggy Blast on 7/7/19.
 */

public class RestartReceiver extends BroadcastReceiver {

    private static final String TAG = RestartReceiver.class.getSimpleName();
    private PBUtils pbUtils = PBUtils.getInstance();
    private PBFileOperations pbFileOperations = PBFileOperations.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.w(TAG, context.getString(R.string.device_switched_on));
            String imei = pbUtils.getImeiNumber(context, ZERO);
            String time = pbUtils.formatDate(null, DATE_FORMAT_1, null, Calendar.getInstance(), false);
            String fileNameTime = pbUtils.formatDate(null, DATE_FORMAT_1, null, Calendar.getInstance(), true);
            String fileName = imei + UNDER_SCORE + fileNameTime + TYPE_JSON;
            //TODO: Need to call setappname function while to use this receiver
            String directory = PBApplication.getInstance().getAppName();
            boolean isAppend = pbFileOperations.isFileAppend(directory, true, time);
            JSONObject object = new JSONObject();
            object.put(IMEI, imei);
            object.put(TIME, time);
            object.put(MESSAGE, context.getString(R.string.device_switched_on));
            pbFileOperations.writeToFile(directory, fileName, object, true, isAppend);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
