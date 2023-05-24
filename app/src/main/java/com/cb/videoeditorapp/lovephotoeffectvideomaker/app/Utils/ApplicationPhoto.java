package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils;

import android.content.Context;
import android.text.TextUtils;


import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ApplicationPhoto extends android.app.Application {

    public static final String TAG = ApplicationPhoto.class
            .getSimpleName();

    public static ApplicationPhoto mInstance;
    public static Context mActivity;
    public static ApplicationPhoto application;

    private RequestQueue mRequestQueue;

    static {
        System.loadLibrary("native-lib");
    }

    public static final int mWidth = 720;
    public static final int mHeight = 1280;
    public static final int horiHeight = 480;
    public static final int mformat800 = 400;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mActivity = this;
        application = this;
       /* FunctionsPhoto.loadNativeAd(this);
        FunctionsPhoto.loadAd(this);*/
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized ApplicationPhoto getInstance() {
        if (application == null)
            application = new ApplicationPhoto();
        return application;
    }

    public static synchronized Context getContext() {
        return getInstance().getApplicationContext();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}