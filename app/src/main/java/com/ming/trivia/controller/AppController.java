package com.ming.trivia.controller;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    final private String TAG = AppController.class.getSimpleName();
    private static AppController myInstance;
    private RequestQueue requestQueue;

    public static synchronized AppController getInstance() {

        return myInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);

    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);

    }

    public void cancelAllRequest(Object tag) {
        getRequestQueue().cancelAll(tag);
    }


}
