package com.pblibs.pbrestclient;

import android.content.Context;
import com.pblibs.base.PBApplication;
import com.pblibs.pbinterfaces.PBNetworkCallback;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created  by Proggy Blast on 8/9/19.
 */

public class NetworkApiLayer implements PBNetworkCallback {

    private static NetworkApiLayer mInstance;
    private Context mContext;
    private VolleyNetworkFetch volleyNetworkFetch;
    private PBNetworkCallback mCallback;

    public NetworkApiLayer() {
        mContext = PBApplication.getInstance().getContext();
        volleyNetworkFetch = VolleyNetworkFetch.getInstance(mContext);
    }

    public static NetworkApiLayer getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkApiLayer();
        }
        return mInstance;
    }

    /**
     * Make common json object request
     *
     * @param object
     * @param url
     * @param callback
     */

    public void makeJsonObjectRequest(JSONObject object, String url, PBNetworkCallback callback) {
        mCallback = callback;
        volleyNetworkFetch.makeJSONObjectRequest(url, object, this);
    }

    /**
     * Make String request
     *
     * @param method
     * @param hashMap
     * @param url
     * @param callback
     */

    public void makeStringRequest(int method, HashMap<String, String> hashMap, String url, PBNetworkCallback callback) {
        mCallback = callback;
        volleyNetworkFetch.makeStringRequest(method, url, hashMap, this);
    }

    @Override
    public void onSuccess(Object result) {
        mCallback.onSuccess(result);
    }

    @Override
    public void onError(Object error) {
        mCallback.onError(error);
    }
}
