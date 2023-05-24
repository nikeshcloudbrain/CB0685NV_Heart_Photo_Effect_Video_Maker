package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.preference.PowerPreference;

import java.util.ArrayList;
import java.util.Objects;



public class ListNativeAds {

    private static ArrayList<NativeAd> gNativeAd = new ArrayList<>();
    private static String ad_type;

    public void loadNativeAds(Activity activity, Dialog dialog) {
        final String nativeAdstr = PowerPreference.getDefaultFile().getString(Constant.NATIVEID, "123");

        AdLoader.Builder builder = new AdLoader.Builder(activity, nativeAdstr);
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd natives) {

                gNativeAd.clear();
                gNativeAd.add(natives);
            }
        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError errorCode) {
                Log.e(Constant.adsLog, "loadNativeAds failed" + errorCode.toString());
                gNativeAd.clear();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }


    public void showListNativeAds(Activity activity, FrameLayout nativeAd, TextView adSpace) {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.AdsOnOff, true)) {
            if (PowerPreference.getDefaultFile().getInt(Constant.ListNativeWhichOne, 1) == 1) {
                showLargeNativeAds(activity, nativeAd, adSpace);
            } else {
                showMiniNativeAds(activity, nativeAd, adSpace);
            }
        } else {
            nativeAd.setVisibility(View.GONE);
            adSpace.setVisibility(View.GONE);
        }
    }

    public void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {

        if (adView.findViewById(R.id.ad_media) != null) {
            MediaView mediaView = adView.findViewById(R.id.ad_media);
            mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            adView.setMediaView(mediaView);

        }
        if (adView.findViewById(R.id.ad_headline) != null)
            adView.setHeadlineView(adView.findViewById(R.id.ad_headline));

        if (adView.findViewById(R.id.ad_body) != null)
            adView.setBodyView(adView.findViewById(R.id.ad_body));

        if (adView.findViewById(R.id.ad_call_to_action) != null)
            adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));

        if (adView.findViewById(R.id.ad_app_icon) != null)
            adView.setIconView(adView.findViewById(R.id.ad_app_icon));

        if (adView.findViewById(R.id.ad_stars) != null)
            adView.setStarRatingView(adView.findViewById(R.id.ad_stars));


        if (nativeAd.getStarRating() == null) {
            if (adView.getStarRatingView() != null)
                Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.GONE);
        } else {
            if (adView.getStarRatingView() != null) {
                Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.VISIBLE);
                ((RatingBar) adView.getStarRatingView()).setRating(Float.parseFloat(String.valueOf(nativeAd.getStarRating())));
            }
        }

        if (nativeAd.getHeadline() == null) {
            if (adView.getHeadlineView() != null)
                Objects.requireNonNull(adView.getHeadlineView()).setVisibility(View.GONE);
        } else {
            if (adView.getHeadlineView() != null) {
                Objects.requireNonNull(adView.getHeadlineView()).setVisibility(View.VISIBLE);
                ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
            }
        }

        if (nativeAd.getBody() == null) {
            if (adView.getBodyView() != null)
                Objects.requireNonNull(adView.getBodyView()).setVisibility(View.GONE);
        } else {
            if (adView.getBodyView() != null) {
                Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
                ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            }
        }

        if (nativeAd.getCallToAction() == null) {
            if (adView.getCallToActionView() != null)
                Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            if (adView.getCallToActionView() != null) {
                Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
                if (PowerPreference.getDefaultFile().getBoolean(Constant.GoogleNativeTextOnOff, true)) {
                    ((TextView) adView.getCallToActionView()).setText(PowerPreference.getDefaultFile().getString(Constant.GoogleNativeText, "OPEN"));
                } else {
                    ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
                }
            }
        }

        if (nativeAd.getIcon() == null) {
            if (adView.getIconView() != null)
                Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            if (adView.getIconView() != null) {
                ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                        nativeAd.getIcon().getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }
        }

        adView.setNativeAd(nativeAd);
    }

    public void setQureka(Activity activity, ImageView imageViewMain, ImageView imageViewBG, ImageView imageViewGif, String isSmall) {

        if (PowerPreference.getDefaultFile().getInt(isSmall, 0) >= 5) {
            PowerPreference.getDefaultFile().putInt(isSmall, 0);
            setQureka(activity, imageViewMain, imageViewBG, imageViewGif, isSmall);
        } else {
            if (imageViewBG != null && !activity.isFinishing())
                Glide.with(activity).load(Constant.adsQurekaInters[PowerPreference.getDefaultFile().getInt(isSmall, 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewBG);

            if (imageViewMain != null && !activity.isFinishing())
                Glide.with(activity).load(Constant.adsQurekaInters[PowerPreference.getDefaultFile().getInt(isSmall, 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewMain);

            if (imageViewGif != null && !activity.isFinishing())
                Glide.with(activity).asGif().load(Constant.adsQurekaGifInters[PowerPreference.getDefaultFile().getInt(isSmall, 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewGif);


            int top = PowerPreference.getDefaultFile().getInt(isSmall, 0) + 1;
            PowerPreference.getDefaultFile().putInt(isSmall, top);
        }
    }

    public void showLargeNativeAds(Activity activity, FrameLayout nativeAd, TextView adSpace) {

        LinearLayout adView = null;

        if (PowerPreference.getDefaultFile().getBoolean(Constant.AdsOnOff, true)) {

            if (PowerPreference.getDefaultFile().getBoolean(Constant.GoogleListNativeOnOff, true) && gNativeAd.size() > 0) {

                adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.ads_native_large, null);

                NativeAd lovalNative = gNativeAd.get(0);

                populateUnifiedNativeAdView(lovalNative, adView.findViewById(R.id.uadview));

                nativeAd.removeAllViews();
                nativeAd.addView(adView);

                adSpace.setVisibility(View.GONE);
                nativeAd.setVisibility(View.VISIBLE);

                loadNativeAds(activity, null);

            } else {

                loadNativeAds(activity, null);

                if (PowerPreference.getDefaultFile().getBoolean(Constant.QurekaOnOff, true) && PowerPreference.getDefaultFile().getBoolean(Constant.QurekaListNativeOnOff, true)) {

                    adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.qureka_native_large, null);

                    ImageView imageViewMain = adView.findViewById(R.id.qurekaAds1);
                    ImageView imageViewBG = adView.findViewById(R.id.qurekaAds);
                    ImageView imageViewGif = adView.findViewById(R.id.gif_inter_round);

                    setQureka(activity, imageViewMain, imageViewBG, imageViewGif, Constant.QLIST_COUNT);

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
                    adSpace.setVisibility(View.GONE);
                }
            }
        } else {
            nativeAd.setVisibility(View.GONE);
            adSpace.setVisibility(View.GONE);
        }
    }

    public void showMiniNativeAds(Activity activity, FrameLayout nativeAd, TextView adSpace) {

        LinearLayout adView = null;

        if (PowerPreference.getDefaultFile().getBoolean(Constant.AdsOnOff, true)) {

            if (PowerPreference.getDefaultFile().getBoolean(Constant.GoogleListNativeOnOff, true) &&
                    gNativeAd.size() > 0) {

                adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.ads_native_mini, null);

                NativeAd lovalNative = gNativeAd.get(0);

                populateUnifiedNativeAdView(lovalNative, adView.findViewById(R.id.uadview));

                nativeAd.removeAllViews();
                nativeAd.addView(adView);

                adSpace.setVisibility(View.GONE);
                nativeAd.setVisibility(View.VISIBLE);

                loadNativeAds(activity, null);

            } else {

                loadNativeAds(activity, null);

                if (PowerPreference.getDefaultFile().getBoolean(Constant.QurekaOnOff, true) && PowerPreference.getDefaultFile().getBoolean(Constant.QurekaListNativeOnOff, true)) {

                    adView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.qureka_native_mini, null);

                    ImageView imageViewMain = adView.findViewById(R.id.qurekaAds1);
                    ImageView imageViewBG = adView.findViewById(R.id.qurekaAds);
                    ImageView imageViewGif = adView.findViewById(R.id.gif_inter_round);

                    setQureka(activity, imageViewMain, imageViewBG, imageViewGif, Constant.QLIST_COUNT);

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
                    adSpace.setVisibility(View.GONE);
                }
            }
        } else {
            nativeAd.setVisibility(View.GONE);
            adSpace.setVisibility(View.GONE);
        }
    }
}
