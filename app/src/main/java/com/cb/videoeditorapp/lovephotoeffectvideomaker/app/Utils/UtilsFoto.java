package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.LargeNativeAds;
import com.preference.PowerPreference;

import net.lingala.zip4j.util.InternalZipConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UtilsFoto {

    public static Context mContext;
    public static OnAdClosedListener mOnAdClosedListener;
    public static AdRequest adRequest;
    public static InterstitialAd interstitialAd;

    public static UnifiedNativeAd nativeAd;

    public static int blurRadius = 12;
    public static boolean isDisplayData = false;
    public static String strExtension;
    public static Dialog dialog;

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

//        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, AdSize.SMART_BANNER.getHeightInPixels(activity), activity.getResources().getDisplayMetrics());
//        int newInt = (int) (height / Resources.getSystem().getDisplayMetrics().density);
//
//        txtAdSpace.setHeight(newInt);

        AdView mAdView = new AdView(activity);
        adContain.addView(mAdView);
        mAdView.setAdUnitId(activity.getString(R.string.BannerAd));
        mAdView.setAdSize(AdSize.SMART_BANNER);

        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (i == 3) {
                    UtilsFoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_NO_FILL");
                } else if (i == 0) {
                    UtilsFoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_INTERNAL_ERROR");
                } else if (i == 1) {
                    UtilsFoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_INVALID_REQUEST");
                } else if (i == 2) {
                    UtilsFoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_NETWORK_ERROR");
                } else {
                    UtilsFoto.LogW("Banner", "ADMOB_BANNERonAdFailedToLoad:" + i);
                }
                adContain.setVisibility(View.GONE);
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                UtilsFoto.LogW("Banner", "Load Admob Banner");
                adContain.setVisibility(View.VISIBLE);
            }
        });
    }

    public static void SetSmallBannerAds(Activity activity, final RelativeLayout adContain, final TextView txtAdSpace) {

//        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, AdSize.BANNER.getHeightInPixels(activity), activity.getResources().getDisplayMetrics());
//        int newInt = (int) (height / Resources.getSystem().getDisplayMetrics().density);
//
//        txtAdSpace.setHeight(newInt);

        AdView mAdView = new AdView(activity);
        adContain.addView(mAdView);
        mAdView.setAdUnitId(activity.getString(R.string.BannerAd));
        mAdView.setAdSize(AdSize.BANNER);

        mAdView.loadAd(new AdRequest.Builder()
                .addTestDevice("1496D8D93521B953E582537E8906F546") //Mi note 4
                .build());
        mAdView.setAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (i == 3) {
                    UtilsFoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_NO_FILL");
                } else if (i == 0) {
                    UtilsFoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_INTERNAL_ERROR");
                } else if (i == 1) {
                    UtilsFoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_INVALID_REQUEST");
                } else if (i == 2) {
                    UtilsFoto.LogW("Banner", "ADMOB_BANNER_ERROR_CODE_NETWORK_ERROR");
                } else {
                    UtilsFoto.LogW("Banner", "ADMOB_BANNERonAdFailedToLoad:" + i);
                }
                adContain.setVisibility(View.GONE);
            }

            public void onAdLoaded() {
                super.onAdLoaded();
                UtilsFoto.LogW("Banner", "Load Admob Banner");
                adContain.setVisibility(View.VISIBLE);
            }
        });
    }

    public static void sendAdmobRequest(final Context context) {

        interstitialAd = new InterstitialAd(context);

        interstitialAd.setAdUnitId(context.getResources().getString(R.string.InterAd));

        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("882DA2C29EEA8F4B8D96EF5C7B895484") //OPPO
                .addTestDevice("1D6B3A0EB5837FEB0011845658395325") //Samsung 6otu
                .addTestDevice("1496D8D93521B953E582537E8906F546") //Mi note 4
                .build();

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                UtilsFoto.LogW("sendAdmobRequest: ", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                UtilsFoto.LogW("sendAdmobRequest: ", "onAdFailedToLoad");

                sendAdmobRequest(context);
            }

            @Override
            public void onAdOpened() {
                UtilsFoto.LogW("sendAdmobRequest: ", "onAdOpened");
            }

            @Override
            public void onAdClicked() {
                UtilsFoto.LogW("sendAdmobRequest: ", "onAdClicked");
            }

            @Override
            public void onAdLeftApplication() {
                UtilsFoto.LogW("sendAdmobRequest: ", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                UtilsFoto.LogW("sendAdmobRequest: ", "onAdClosed");
                if (mOnAdClosedListener != null) {
                    mOnAdClosedListener.onAdClosed();
                }
                sendAdmobRequest(context);
            }
        });

        interstitialAd.loadAd(adRequest);
    }

    public static void showInterAdmobAd(Context context, OnAdClosedListener onAdClosedListener) {
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
            sendAdmobRequest(mContext);
            if (mOnAdClosedListener != null)
                mOnAdClosedListener.onAdClosed();
        }
    }

    public interface OnAdClosedListener {
        void onAdClosed();
    }

    public static void loadNativeAd(final Context context) {

//        AdLoader.Builder builder = new AdLoader.Builder(context, context.getString(R.string.NativeAd));
//
//        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
//            @Override
//            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
//                if (nativeAd != null) {
//                    nativeAd.destroy();
//                }
//                nativeAd = unifiedNativeAd;
//            }
//        });
//
//        VideoOptions videoOptions = new VideoOptions.Builder()
//                .setStartMuted(true)
//                .build();
//
//        NativeAdOptions adOptions = new NativeAdOptions.Builder()
//                .setVideoOptions(videoOptions)
//                .build();
//
//        builder.withNativeAdOptions(adOptions);
//
//        AdLoader adLoader = builder.withAdListener(new AdListener() {
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                UtilsFoto.LogW("NATIVE", "Failed to load native ad: " + errorCode);
//            }
//        }).build();
//
//        adLoader.loadAd(new AdRequest.Builder().build());
//
//        UtilsFoto.LogE("NATIVE", "Load Native Ads --- ");
    }

    public static void showNativeAd(final Context context, final FrameLayout frameLayout) {

        final UnifiedNativeAdView adView = (UnifiedNativeAdView) ((AppCompatActivity) context).getLayoutInflater()
                .inflate(R.layout.custom_layout_native_content_photo, null);
        AdLoader.Builder builder;
        builder = new AdLoader.Builder(context, context.getString(R.string.NativeAd));

        builder.forUnifiedNativeAd(unifiedNativeAd -> {
            if (nativeAd != null) {
                nativeAd.destroy();
            }
            nativeAd = unifiedNativeAd;

            populateUnifiedNativeAdView(unifiedNativeAd, adView);
//                frameLayout.removeAllViews();
            frameLayout.addView(adView);
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
//                Toast.makeText(context, "Failed to load native ad: "
//                        + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
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

    public static void notifyMediaScannerService(Activity context, String path) {
        MediaScannerConnection.scanFile(context,
                new String[]{path}, null,
                (path1, uri) -> {
                    UtilsFoto.LogE("ExternalStorage", "Scanned " + path1 + ":");
                    UtilsFoto.LogE("ExternalStorage", "-> uri=" + uri);

                    resetExternalStorageMedia(context);
                });
    }

    public static void resetExternalStorageMedia(Activity mContext) {
        if (Environment.isExternalStorageEmulated())
            return;
        Uri uri = Uri.parse("file://" + Environment.getExternalStorageDirectory());
        Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, uri);
        mContext.sendBroadcast(intent);
    }

    public static void dilogShow(Activity mContext) {

        try {

            if (dialog != null && dialog.isShowing())
                dialog.dismiss();

            dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.custom_dialog_save_video_photo);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {

                int ui_flags =
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

                dialog.getWindow().
                        setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                dialog.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
            }
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            FrameLayout nativeframe=dialog.findViewById(R.id.nativeAd);
            TextView adspace=dialog.findViewById(R.id.ad_space);
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    if(PowerPreference.getDefaultFile().getBoolean(Constant.LoaderNativeOnOff,false)){
                        new LargeNativeAds().showNativeAds(mContext, dialog);
                    }else {
                        nativeframe.setVisibility(View.GONE);
                        adspace.setVisibility(View.GONE);
                    }
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dilogHide(Activity mContext) {
        dialog.dismiss();
    }

    public static void copyFile(Context context, String str, String str2) {
        AssetManager assets = context.getAssets();
        try {
            if (!new File(str2).exists()) {
                new File(str2).createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream open = assets.open(str);
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    open.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            UtilsFoto.LogW("tag", e2.getMessage());
        }
    }

    public static void copyFileOrDir(Context context, String str, String str2) {
        AssetManager assets = context.getAssets();
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            String[] list = assets.list(str);
            if (list.length != 0) {
                for (String append : list) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                    sb.append(append);
                    copyFileOrDir(context, sb.toString(), str2);
                }
            } else if (str.contains(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
                String[] split = str.split(InternalZipConstants.ZIP_FILE_SEPARATOR);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                sb2.append(split[split.length - 1]);
                copyFile(context, str, sb2.toString());
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                sb3.append(str);
                copyFile(context, str, sb3.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            UtilsFoto.LogW("tag", "I/O Exception " + e);
        }
    }

    public static int getAssetFileCount(Context context, String str) {
        try {
            String[] list = context.getAssets().list(str);
            if (list != null) {
                return list.length;
            }
            return 0;
        } catch (IOException unused) {
            return 0;
        }
    }

    public static int getStorageFileCount(Context context, String str) {
        int i = 0;
        try {
            File[] listFiles = new File(str).listFiles();
            if (listFiles != null) {
                i = listFiles.length;
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
