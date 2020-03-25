package com.pblibs.pbrestclient;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.pblibs.pbinterfaces.PBNetworkCallback;
import com.pblibs.utility.PBConstants;
import com.pblibs.utility.PBSessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created  by Proggy Blast on 7/7/19.
 */

public class VolleyNetworkFetch {

    private static volatile VolleyNetworkFetch mInstance;
    private Context mContext;

    private VolleyNetworkFetch(Context context) {
        mContext = context;
    }

    public static VolleyNetworkFetch getInstance(Context context) {
        if (mInstance == null) {
            synchronized (VolleyNetworkFetch.class) {
                if (mInstance == null) {
                    mInstance = new VolleyNetworkFetch(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * Make Volley request using String Request
     *
     * @param method
     * @param url
     * @param hashMap
     * @param callback
     */

    public void makeStringRequest(int method, String url, final HashMap<String, String> hashMap,
                                  final PBNetworkCallback callback) {

        StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return hashMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String contentType = PBSessionManager.getInstance().getString(PBConstants.CONTENT_TYPE,
                        PBConstants.URL_ENCODED_TYPE);
                params.put(PBConstants.CONTENT_TYPE, contentType);
                String token = PBSessionManager.getInstance().getString(PBConstants.TOKEN, "");
                if (!token.isEmpty() && !token.equals("null")) {
                    params.put(PBConstants.AUTHORIZATION, "Bearer " + token);
                }
                return params;
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    /**
     * To make json object request and response success with jsonobject
     *
     * @param url
     * @param jsonObject
     * @param callback
     */

    public void makeJSONObjectRequest(String url, JSONObject jsonObject, final PBNetworkCallback callback) {

        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String contentType = PBSessionManager.getInstance().getString(PBConstants.CONTENT_TYPE,
                        PBConstants.URL_ENCODED_TYPE);
                params.put(PBConstants.CONTENT_TYPE, contentType);
                String token = PBSessionManager.getInstance().getString(PBConstants.TOKEN, "");
                if (!token.isEmpty() && !token.equals("null")) {
                    params.put(PBConstants.AUTHORIZATION, "Bearer " + token);
                }
                return params;
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    /**
     * To make json array request and response success with jsonobject
     *
     * @param method
     * @param url
     * @param jsonArray
     * @param callback
     */

    public void makeJSONArrayRequest(int method, String url, final JSONArray jsonArray,
                                     final PBNetworkCallback callback) {

        JsonArrayRequest request = new JsonArrayRequest(method, url, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String contentType = PBSessionManager.getInstance().getString(PBConstants.CONTENT_TYPE,
                        PBConstants.URL_ENCODED_TYPE);
                params.put(PBConstants.CONTENT_TYPE, contentType);
                String token = PBSessionManager.getInstance().getString(PBConstants.TOKEN, "");
                if (!token.isEmpty() && !token.equals("null")) {
                    params.put(PBConstants.AUTHORIZATION, "Bearer " + token);
                }
                return params;
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

}
