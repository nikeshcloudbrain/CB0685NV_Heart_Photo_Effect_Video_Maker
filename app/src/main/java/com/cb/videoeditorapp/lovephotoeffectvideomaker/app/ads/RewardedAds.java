package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import com.preference.PowerPreference;

import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class RewardedAds {

    @SuppressLint("StaticFieldLeak")
    public static Activity mActivity;

    Dialog mDialog = null;

    public static RewardedAd mRewardedVideoAd;
    public static OnAdClosedListener mOnAdClosedListener;

    public static CountDownTimer timer = null;
    public static boolean isRewarded = false;

    public interface OnAdClosedListener {
        public void onAdClosed(boolean isRewarded, boolean isRewardVideo);

        public void onAdFailed();

        public void onAdTimer();
    }

    public void loadRewardAds(Activity activity) {
        final String interAd = PowerPreference.getDefaultFile().getString(Constant.REWARDID);
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(activity, interAd, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);

                mRewardedVideoAd = rewardedAd;
                Constant.showErrorLog("onAdLoaded");

                mRewardedVideoAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Constant.showErrorLog("The ad failed to show.");
                        mRewardedVideoAd = null;

                        if (PowerPreference.getDefaultFile().getBoolean(Constant.QurekaOnOff, true) && PowerPreference.getDefaultFile().getBoolean(Constant.QurekaRewardOnOff, true)) {
                            showQurekaDialog(mActivity, mOnAdClosedListener);
                        } else {
                            if (mOnAdClosedListener != null)
                                mOnAdClosedListener.onAdFailed();
                        }


                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        mRewardedVideoAd = null;
                        Constant.showErrorLog("The ad was shown.");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();

                        Constant.showErrorLog("onAdClosed");
                        loadRewardAds(activity);

                        if (isRewarded) {
                            if (mOnAdClosedListener != null) {
                                mOnAdClosedListener.onAdClosed(true, true);
                            }
                        } else {
                            if (mOnAdClosedListener != null) {
                                mOnAdClosedListener.onAdClosed(false, true);
                            }
                        }

                    }
                });

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mRewardedVideoAd = null;
                Constant.showErrorLog("onAdFailedToLoad");
            }
        });
    }

    public void showRewardAds(Activity context, OnAdClosedListener onAdClosedListener) {
        Constant.showErrorLog("showInterAds");
        mActivity = context;
        mOnAdClosedListener = onAdClosedListener;
        isRewarded = false;

        if (PowerPreference.getDefaultFile().getBoolean(Constant.AdsOnOff, true)) {
            if (mRewardedVideoAd != null && PowerPreference.getDefaultFile().getBoolean(Constant.GoogleRewardOnOff, true)) {
                mRewardedVideoAd.show(context, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        isRewarded = true;
                    }
                });
            } else {
                loadRewardAds(context);

                if (PowerPreference.getDefaultFile().getBoolean(Constant.QurekaOnOff, true) && PowerPreference.getDefaultFile().getBoolean(Constant.QurekaRewardOnOff, true)) {
                    showQurekaDialog(context, onAdClosedListener);
                } else {
                    if (mOnAdClosedListener != null)
                        mOnAdClosedListener.onAdFailed();
                }
            }
        } else {
            if (mOnAdClosedListener != null)
                mOnAdClosedListener.onAdFailed();
        }
    }


    public void setQureka(Activity activity, ImageView imageViewMain, ImageView imageViewBG, ImageView imageViewGif) {
        if (PowerPreference.getDefaultFile().getInt("qCount", 0) >= 5) {
            PowerPreference.getDefaultFile().putInt("qCount", 0);
            setQureka(activity, imageViewMain, imageViewBG, imageViewGif);
        } else {
            if (!activity.isFinishing()) {
                Glide.with(activity).load(Constant.adsQurekaInters[PowerPreference.getDefaultFile().getInt("qCount", 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewBG);
                Glide.with(activity).load(Constant.adsQurekaInters[PowerPreference.getDefaultFile().getInt("qCount", 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewMain);
                Glide.with(activity).asGif().load(Constant.adsQurekaGifInters[PowerPreference.getDefaultFile().getInt("qCount", 0)])
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewGif);
            }
            int top = PowerPreference.getDefaultFile().getInt("qCount", 0) + 1;
            PowerPreference.getDefaultFile().putInt("qCount", top);
        }
    }

    public void showQurekaDialog(Activity activity, OnAdClosedListener onAdClosedListener) {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.QurekaOnOff, true) && PowerPreference.getDefaultFile().getBoolean(Constant.QurekaRewardOnOff, true)) {

            try {
                mDialog = new Dialog(activity);
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog.setContentView(R.layout.activity_tlm_qureka_inter);
                mDialog.setCancelable(true);
                Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.setCanceledOnTouchOutside(true);
                mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                RelativeLayout qurekaAdLayout = mDialog.findViewById(R.id.rlMain);
                ImageView qurekaAdsClose = mDialog.findViewById(R.id.qurekaAdsClose);
                ImageView qurekaAds = mDialog.findViewById(R.id.qurekaAds);
                ImageView qurekaAds1 = mDialog.findViewById(R.id.qurekaAds1);
                GifImageView gifInterRound = mDialog.findViewById(R.id.gif_inter_round);

                setQureka(activity, qurekaAds, qurekaAds1, gifInterRound);

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

                        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
                            activity.getWindow().getDecorView().setSystemUiVisibility(
                                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                        }

                        if (mOnAdClosedListener != null) {
                            mOnAdClosedListener.onAdClosed(true, true);
                        }

                    }
                });

                mDialog.show();

            } catch (Exception e) {
                Log.w("Catch", Objects.requireNonNull(e.getMessage()));
            }
        }
    }


}
