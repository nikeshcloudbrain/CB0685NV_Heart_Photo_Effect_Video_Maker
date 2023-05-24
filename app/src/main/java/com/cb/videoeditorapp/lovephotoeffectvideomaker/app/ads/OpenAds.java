package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpSplashActivity;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ApplicationPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.preference.PowerPreference;

import java.util.Objects;



public class OpenAds implements LifecycleObserver, android.app.Application.ActivityLifecycleCallbacks {

    @SuppressLint("StaticFieldLeak")
    public static OpenAds mAppAds;
    private static final String LOG_TAG = "AppOpenManager";
    private AppOpenAd appOpenAd1 = null;

    Dialog mDialog = null;
    private final ApplicationPhoto Application = ApplicationPhoto.getInstance();
    private Activity currentActivity;

    private static boolean isShowingAd = false;

    public interface OnAdClosedListener {
        public void onAdClosed();
    }

    public OpenAds() {
        if (mAppAds == null) {
            mAppAds = this;
            this.Application.registerActivityLifecycleCallbacks(this);
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        }
    }

    public void loadOpenAd() {
        AppOpenAd.AppOpenAdLoadCallback loadCallback1 = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(AppOpenAd ad) {
                OpenAds.this.appOpenAd1 = ad;
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                OpenAds.this.appOpenAd1 = null;
            }
        };

        final String appOpenAd = PowerPreference.getDefaultFile().getString(Constant.OPENAD,"123");
        AdRequest request = getAdRequest();
        AppOpenAd.load(Application, appOpenAd, request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback1);

    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public void showOpenAd(OnAdClosedListener onAdClosedListener) {

        if (appOpenAd1 != null && !isShowingAd) {

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAd1 = null;
                            isShowingAd = false;

                            if (onAdClosedListener != null)
                                onAdClosedListener.onAdClosed();

                            loadOpenAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            appOpenAd1 = null;
                            isShowingAd = false;

                            loadOpenAd();
                            showQurekaDialog(currentActivity, null);

                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };

            appOpenAd1.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd1.show(currentActivity);


        } else if (!isShowingAd) {
            loadOpenAd();
            showQurekaDialog(currentActivity, null);
        }
    }

    public void showQurekaDialog(Activity activity, OnAdClosedListener onAdClosedListener) {

        if (PowerPreference.getDefaultFile().getBoolean(Constant.QurekaOnOff, true) && PowerPreference.getDefaultFile().getBoolean(Constant.QurekaAppOpenOnOff, true)) {

            try {

                mDialog = new Dialog(activity);
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog.setContentView(R.layout.qureka_open);
                mDialog.setCancelable(false);
                Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                LinearLayout qurekaAdLayout = mDialog.findViewById(R.id.qurekaAdLayout);
                LinearLayout qurekaAdsClose = mDialog.findViewById(R.id.qurekaAdsClose);
                ImageView imageView = mDialog.findViewById(R.id.gif_inter_round);
                Glide.with(activity).asGif().load(R.drawable.qureka_round1).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
                mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        isShowingAd = true;
                    }
                });

                qurekaAdLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Constant.gotoAds(activity);
                    }
                });

                qurekaAdsClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });

                mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        isShowingAd = false;
                        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
                            activity.getWindow().getDecorView().setSystemUiVisibility(
                                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                        }
                    }
                });

                mDialog.show();

            } catch (Exception e) {
                Log.w("Catch", Objects.requireNonNull(e.getMessage()));
            }
        }
    }

    @OnLifecycleEvent(ON_START)
    public void onStart() {

        if (PowerPreference.getDefaultFile().getBoolean(Constant.AdsOnOff, true)) {
            if (!(currentActivity instanceof LlpSplashActivity)) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                } else {
                    showOpenAd(null);
                }
            }
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }

}
