package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.preference.PowerPreference;


public class ListBannerAds {

    public void showListBannerAds(Activity activity, FrameLayout nativeAd, TextView adSpace) {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.AdsOnOff, true)) {
            if (PowerPreference.getDefaultFile().getInt(Constant.GoogleWhichOneNative, 0) == 0) {
                new BannerAds().showBannerAds(activity, nativeAd, adSpace);
            } else {
                new MiniNativeAds().showNativeAds(activity, nativeAd, adSpace);
            }
        } else {
            nativeAd.setVisibility(View.GONE);
            adSpace.setVisibility(View.GONE);
        }
    }

}
