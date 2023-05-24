package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.preference.PowerPreference;



public class BannerAds {

    @SuppressLint("StaticFieldLeak")
    public static Activity mActivity;

    public static AdView adView;
    public static boolean isLoaded = false;

    public void loadBannerAds(Activity activity) {
        final String Ad = PowerPreference.getDefaultFile().getString(Constant.BANNERID, "123");
        adView = new AdView(activity);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(Ad);
        isLoaded = false;
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                isLoaded = true;
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                isLoaded = false;
                adView = null;
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void showBannerAds(Activity activity, FrameLayout nativeAd, TextView adSpace) {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.AdsOnOff, true)) {
            if (PowerPreference.getDefaultFile().getBoolean(Constant.GoogleBannerOnOff, true) && adView != null && isLoaded) {

                nativeAd.removeAllViews();
                nativeAd.addView(adView);

                adSpace.setVisibility(View.GONE);
                nativeAd.setVisibility(View.VISIBLE);

                loadBannerAds(activity);
            } else {
                loadBannerAds(activity);

                if (PowerPreference.getDefaultFile().getBoolean(Constant.QurekaOnOff, true) && PowerPreference.getDefaultFile().getBoolean(Constant.QurekaBannerOnOff, true)) {
                    LinearLayout adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.qureka_banner, null);
                    ImageView imageViewMain = adView.findViewById(R.id.imageView);
                    setQureka(activity, imageViewMain, Constant.QBANNER_COUNT);

                    adView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Constant.gotoAds(activity);
                        }
                    });

                    nativeAd.removeAllViews();
                    nativeAd.addView(adView);

                    adSpace.setVisibility(View.GONE);
                    nativeAd.setVisibility(View.VISIBLE);

                } else {
                    nativeAd.setVisibility(View.GONE);
                    adSpace.setVisibility(View.VISIBLE);
                }
            }
        } else {
            nativeAd.setVisibility(View.GONE);
            adSpace.setVisibility(View.GONE);
        }
    }

    public void setQureka(Activity activity, ImageView imageViewMain, String isSmall) {

        if (PowerPreference.getDefaultFile().getInt(isSmall, 0) >= 5) {
            PowerPreference.getDefaultFile().putInt(isSmall, 0);
            setQureka(activity, imageViewMain, isSmall);
        } else {

            if (imageViewMain != null && !activity.isFinishing())
                Glide.with(activity).load(Constant.adsQurekaBanners[PowerPreference.getDefaultFile().getInt(isSmall, 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewMain);

            int top = PowerPreference.getDefaultFile().getInt(isSmall, 0) + 1;
            PowerPreference.getDefaultFile().putInt(isSmall, top);
        }
    }
}
