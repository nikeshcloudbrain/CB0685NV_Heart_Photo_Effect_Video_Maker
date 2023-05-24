package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class FunctionsPhoto {

    public static Context mContext;
    public static OnAdClosedListener mOnAdClosedListener;
    public static AdRequest adRequest;
    public static InterstitialAd interstitialAd;

    public static UnifiedNativeAd nativeAd;
    public static boolean isDisplayData = true;

    public static void LogD(String title, String msg) {
        if (isDisplayData) Log.d(title, msg);
    }

    public static void LogE(String title, String msg) {
        if (isDisplayData) Log.e(title, msg);
    }

    public static void LogW(String title, String msg) {
        if (isDisplayData) Log.w(title, msg);
    }

    public static void LogI(String title, String msg) {
        if (isDisplayData) Log.i(title, msg);
    }

    public static void SetBannerAds(Activity activity, final RelativeLayout adContain, final TextView txtAdSpace) {

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, AdSize.SMART_BANNER.getHeightInPixels(activity), activity.getResources().getDisplayMetrics());
        int newInt = (int) (height / Resources.getSystem().getDisplayMetrics().density);

        txtAdSpace.setHeight(newInt);

        AdView mAdView = new AdView(activity);
        adContain.addView(mAdView);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(activity.getString(R.string.BannerAd));
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (i == 3) {
                    FunctionsPhoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_NO_FILL");
                } else if (i == 0) {
                    FunctionsPhoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_INTERNAL_ERROR");
                } else if (i == 1) {
                    FunctionsPhoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_INVALID_REQUEST");
                } else if (i == 2) {
                    FunctionsPhoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_NETWORK_ERROR");
                } else {
                    FunctionsPhoto.LogW("Banner", "ADMOB_BANNERonAdFailedToLoad:" + i);
                }
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                FunctionsPhoto.LogW("Banner", "Load Admob Banner");
            }
        });
    }

    public static void SetSmallBannerAds(Activity activity, final RelativeLayout adContain, final TextView txtAdSpace) {

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, AdSize.BANNER.getHeightInPixels(activity), activity.getResources().getDisplayMetrics());
        int newInt = (int) (height / Resources.getSystem().getDisplayMetrics().density);

        txtAdSpace.setHeight(newInt);

        AdView mAdView = new AdView(activity);
        adContain.addView(mAdView);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(activity.getString(R.string.BannerAd));
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (i == 3) {
                    FunctionsPhoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_NO_FILL");
                } else if (i == 0) {
                    FunctionsPhoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_INTERNAL_ERROR");
                } else if (i == 1) {
                    FunctionsPhoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_INVALID_REQUEST");
                } else if (i == 2) {
                    FunctionsPhoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_NETWORK_ERROR");
                } else {
                    FunctionsPhoto.LogW("Banner", "ADMOB_BANNERonAdFailedToLoad:" + i);
                }
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                FunctionsPhoto.LogW("Banner", "Load Admob Banner");
            }
        });
    }

    public static void showAd(Context context, OnAdClosedListener onAdClosedListener) {
        mContext = context;
        mOnAdClosedListener = onAdClosedListener;

        if (interstitialAd != null && interstitialAd.isLoaded()) {

//            if (isNeedToAdShow()) {
            interstitialAd.show();
//            } else {
//                if (mOnAdClosedListener != null)
//                    mOnAdClosedListener.onAdClosed();
//            }
        } else {
            loadAd(mContext);
            if (mOnAdClosedListener != null)
                mOnAdClosedListener.onAdClosed();
        }
    }

    public static void loadAd(final Context context) {

        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getResources().getString(R.string.InterAd));

        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("882DA2C29EEA8F4B8D96EF5C7B895484") //OPPO
                .addTestDevice("914F29368CA33B96715143FE4169E43E") //MI 6otu
                .addTestDevice("1D6B3A0EB5837FEB0011845658395325") //Samsung 6otu
                .build();

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                FunctionsPhoto.LogW("loadAd: ", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                FunctionsPhoto.LogW("loadAd: ", "onAdFailedToLoad");
                loadAd(context);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                FunctionsPhoto.LogW("loadAd: ", "onAdOpened");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                FunctionsPhoto.LogW("loadAd: ", "onAdClicked");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                FunctionsPhoto.LogW("loadAd: ", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                FunctionsPhoto.LogW("loadAd: ", "onAdClosed");

                if (mOnAdClosedListener != null) {
                    mOnAdClosedListener.onAdClosed();
                }
                loadAd(context);
            }
        });

        interstitialAd.loadAd(adRequest);
    }

    public interface OnAdClosedListener {
        void onAdClosed();
    }

    public static void loadNativeAd(final Context context) {

        AdLoader.Builder builder = new AdLoader.Builder(context, context.getString(R.string.NativeAd));

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
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
            public void onAdFailedToLoad(int errorCode) {
                FunctionsPhoto.LogW("NATIVE", "Failed to load native ad: " + errorCode);
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

        FunctionsPhoto.LogE("NATIVE", "Load Native Ads --- ");
    }

    public static void showNativeAd(final Context context, final FrameLayout frameLayout) {

        final UnifiedNativeAdView adView = (UnifiedNativeAdView) ((AppCompatActivity) context).getLayoutInflater()
                .inflate(R.layout.custom_layout_native_content_photo, null);

        if (nativeAd != null) {
            populateUnifiedNativeAdView(nativeAd, adView);
            frameLayout.removeAllViews();
            frameLayout.addView(adView);

            FunctionsPhoto.LogW("NATIVE", "Show Native Ads --- ");
        }else {
            loadNativeAd(context);
        }
    }

    private static void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
        VideoController vc = nativeAd.getVideoController();
    }

    public static String GetAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isConnected(Activity mActivity) {
        ConnectivityManager cm = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo == null || !netinfo.isConnectedOrConnecting()) {
            ShowNetworkDialog(mActivity).show();
            return false;
        }
        NetworkInfo wifi = cm.getNetworkInfo(1);
        NetworkInfo mobile = cm.getNetworkInfo(0);
        if (mobile != null && mobile.isConnectedOrConnecting()) {
            return true;
        }
        if (wifi == null || !wifi.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }

    private static AlertDialog.Builder ShowNetworkDialog(final Activity mActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mActivity.finish();
            }
        });
        return builder;
    }

}
