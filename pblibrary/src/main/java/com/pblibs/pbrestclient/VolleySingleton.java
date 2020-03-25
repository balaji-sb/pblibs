package com.pblibs.pbrestclient;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created  by Proggy Blast on 7/7/19.
 */

public class VolleySingleton {

    private static volatile VolleySingleton mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;
    private VolleySingleton(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    public static VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            synchronized (VolleySingleton.class) {
                if (mInstance == null) {
                    mInstance = new VolleySingleton(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * Add request into queue
     *
     * @param request
     */

    public void addToRequestQueue(Request request) {
        mRequestQueue.add(request);
    }


}
