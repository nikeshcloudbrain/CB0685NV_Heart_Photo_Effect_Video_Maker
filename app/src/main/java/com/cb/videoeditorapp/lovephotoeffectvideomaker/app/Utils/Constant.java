package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpExitActivity;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.BuildConfig;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.LargeNativeAds;
import com.preference.PowerPreference;

import java.io.File;

import unified.vpn.sdk.CompletableCallback;
import unified.vpn.sdk.TrackingConstants;
import unified.vpn.sdk.UnifiedSdk;
import unified.vpn.sdk.VpnException;


public class Constant {


    public static String ADS_NORMAL = "ADS_NORMAL";
    public static String ADS_TINY = "ADS_TINY";

    public static String DEF_VALUE = "#1af61459";

    public static String adsLog = "adsLog";
    public static String errorLog = "errorLog";
    public static String QINTER_COUNT = "QINTER_COUNT";
    public static String QMINI_COUNT = "QMINI_COUNT";
    public static String QLARGE_COUNT = "QLARGE_COUNT";
    public static String QBANNER_COUNT = "QBANNER_COUNT";
    public static String QLIST_COUNT = "QLIST_COUNT";
    public static Integer[] adsQurekaBanners = new Integer[]{R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner5, R.drawable.banner6, R.drawable.banner7};

    public static Integer[] adsQurekaInters = new Integer[]{R.drawable.qureka_inter1, R.drawable.qureka_inter2, R.drawable.qureka_inter3, R.drawable.qureka_inter4, R.drawable.qureka_inter5};
    public static Integer[] adsQurekaGifInters = new Integer[]{R.drawable.qureka_round1, R.drawable.qureka_round2, R.drawable.qureka_round3, R.drawable.qureka_round4, R.drawable.qureka_round5};

    public static final String isNotify = "isNotify";

    public static final String QUREKA_ADS = "QurekaLink";
    public static final String POLICY = "PolicyLink";

    public static final String CustomAdsOnOff = "CustomAdsOnOff";
    public static final String AppOpen = "AppOpen";

    public static final String FULL_SCREEN = "FullScreenOnOff";
    public static final String AdsOnOff = "AdsOnOff";
    public static final String PolicyOnOff = "PolicyOnOff";
    public static final String REWARDID = "GoogleRewardAds";
    public static final String GoogleRewardOnOff = "GoogleRewardOnOff";

    public static final String GoogleListNativeOnOff = "GoogleListNativeOnOff";

    public static final String GoogleBannerOnOff = "GoogleBannerOnOff";

    public static final String GoogleMiniNativeOnOff = "GoogleMiniNativeOnOff";
    public static final String GoogleLargeNativeOnOff = "GoogleLargeNativeOnOff";

    public static final String SERVER_INTERVAL_COUNT = "GoogleIntervalCount";
    public static final String APP_INTERVAL_COUNT = "APP_INTERVAL_COUNT";
    public static final String LoaderNativeOnOff = "LoaderNativeOnOff";


    public static final String BACK_ADS = "GoogleBackInterOnOff";
    public static final String SERVER_BACK_COUNT = "GoogleBackInterIntervalCount";
    public static final String APP_BACK_COUNT = "APP_BACK_COUNT";

    public static final String GoogleSplashOpenAdsOnOff = "GoogleSplashOpenAdsOnOff";
    public static final String GoogleExitSplashInterOnOff = "GoogleExitSplashInterOnOff";

    public static final String GoogleNativeTextOnOff = "GoogleNativeTextOnOff";
    public static final String GoogleNativeText = "GoogleNativeText";
    public static final String NativeTrans = "NativeTrans";
    public static final String NativeBackgroundColor = "NativeBackgroundColor";
    public static final String HomeNativeBackgroundColorOnOff = "HomeNativeBackgroundColorOnOff";
    public static final String AllPagesNativeBackgroundOnOff = "AllPagesNativeBackgroundOnOff";

    public static final String AllPagesNativeBackgroundCount = "AllPagesNativeBackgroundCount";
    public static final String GoogleWhichOneNative = "GoogleWhichOneNative";

    public static final String ListNativeWhichOne = "ListNativeWhichOne";
    public static final String ListNativeAfterCount = "ListNativeAfterCount";

    public static final String QurekaOnOff = "QurekaOnOff";
    public static final String QurekaBannerOnOff = "QurekaBannerOnOff";
    public static final String QurekaAppOpenOnOff = "QurekaAppOpenOnOff";
    public static final String QurekaInterOnOff = "QurekaInterOnOff";
    public static final String QurekaBackInterOnOff = "QurekaBackInterOnOff";
    public static final String QurekaInterCloseOnOff = "QurekaInterCloseOnOff";
    public static final String QurekaMiniNativeOnOff = "QurekaMiniNativeOnOff";
    public static final String QurekaLargeNativeOnOff = "QurekaLargeNativeOnOff";
    public static final String QurekaListNativeOnOff = "QurekaListNativeOnOff";
    public static final String QurekaRewardOnOff = "QurekaRewardOnOff";


    public static final String ShowDialogBeforeAds = "ShowDialogBeforeAds";
    public static final String DialogTimeInSec = "DialogTimeInSec";
    public static final String VpnDialogTime = "VpnDialogTime";

    public static final String VpnOnOff = "VpnOnOff";
    public static final String VpnDialog = "VpnDialog";

    public static final String isVpnAuto = "vpnAuto";
    public static String Vpnaccept = "Vpnaccept";

    public static String Default_Vpn_Country = "default_vpn_country";
    public static String Default_Country_code = "default_country_code";
    public static String Default_Vpn_img = "default_vpn_img";

    public static String VpnCountryMain = "VpnCountryMain";

    public static final String OneSignalAppId = "OneSignalAppId";

    public static String BANNERID = "GoogleBannerAds";
    public static String OPENAD = "GoogleAppOpenAds";
    public static String INTERID = "GoogleInterAds";
    public static String NATIVEID = "GoogleNativeAds";

    public static String isPolicyChecked = "isPolicyChecked";
    public static String SHARE_WHATSAPP = "com.whatsapp";
    public static String SHARE_FACEBOOK = "com.facebook.katana";
    public static String SHARE_INSTA = "com.instagram.android";
    public static String Music_download = "Music_download";
    public static String BASE_URL = "http://139.59.25.230/nv/AnimationNV651/V1/";

    public static String FULL_URL_ADS = "http://thevideoanimation.site/photo-video-maker-with-effect/ads/API/ads.php";
    public static String KEY_ADS_BANNER = "banner";
    public static String KEY_ADS_ARRAY_ALLAPP = "allapp";
    public static String KEY_ADS_ARRAY_IMG = "img";
    public static String KEY_ADS_ARRAY_URL = "url";
    public static String KEY_ADS_ARRAY_NAME = "name";


    public static String KEY_TOKEN = "usertoken";


    public static String BASE_EFFECTDOWNLOAD = "animationDown.php";
    public static String KEY_SEND_EFFECT_ID = "effect_id";

    public static String mAds = "mAds";

    public static void Log(String message) {
        Log.e("errorLog", message);
    }


    public static void showErrorLog(String message) {
        Log.e("errorLog", message);
    }


    public static void gotoAds(Context context) {
        try {
            String packageName = "com.android.chrome";
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorMain));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(context, Uri.parse(PowerPreference.getDefaultFile().getString(Constant.QUREKA_ADS, "https://1064.win.qureka.com/")));
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    public static boolean checkInternet(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static void gotoTerms(Context context) {
        try {
            String packageName = "com.android.chrome";
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.MainColor));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(context, Uri.parse(PowerPreference.getDefaultFile().getString(Constant.POLICY, "https://1064.win.qureka.com/")));
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    public static native String getMainApi();

    public static void logout() {

        try {
            UnifiedSdk.getInstance().getBackend().logout(new CompletableCallback() {
                @Override
                public void complete() {

                }

                @Override
                public void error(@NonNull VpnException e) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void stopVpn() {
        try {
            UnifiedSdk.getInstance().getVpn().stop(TrackingConstants.GprReasons.M_UI, new CompletableCallback() {
                @Override
                public void complete() {
                    logout();
                }

                @Override
                public void error(@NonNull VpnException e) {
                    logout();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void showRateDialog(Activity activity, boolean isStart) {
        try {
            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.exit_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            final AppCompatButton btnCancel = dialog.findViewById(R.id.btnno);
            final AppCompatButton btnRate = dialog.findViewById(R.id.btnRateus);
            final AppCompatButton btnExit = dialog.findViewById(R.id.btnyes);
            FrameLayout nativeframe=dialog.findViewById(R.id.nativeAd);
            TextView adspace=dialog.findViewById(R.id.ad_space);
            if (!isStart)
                btnExit.setVisibility(View.GONE);

            btnRate.setOnClickListener(view -> {
                if (dialog != null) {
                    dialog.dismiss();
                }
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID));
                activity.startActivity(i);
            });

            btnExit.setOnClickListener(view -> {
                if (dialog != null) {
                    dialog.dismiss();
                }

                if (PowerPreference.getDefaultFile().getBoolean(Constant.GoogleExitSplashInterOnOff)) {
                    PowerPreference.getDefaultFile().putInt(Constant.APP_INTERVAL_COUNT, 0);
                    new InterAds().showInterAds(activity, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            Intent intent = new Intent(activity, LlpExitActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    });
                } else {
                    Intent intent = new Intent(activity, LlpExitActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });

            btnCancel.setOnClickListener(view -> {
                if (dialog != null) {
                    dialog.dismiss();
                }
            });

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    if(PowerPreference.getDefaultFile().getBoolean(Constant.LoaderNativeOnOff,false)){
                        new LargeNativeAds().showNativeAds(activity, dialog);
                    }else {
                        nativeframe.setVisibility(View.GONE);
                        adspace.setVisibility(View.GONE);
                    }
                }
            });

            dialog.show();


        } catch (Exception e) {
            Log.e("Catch", e.getMessage());
        }

    }

    public static int[] IMAGE = {
            R.drawable.photo_2d_00,
            R.drawable.photo_2d_01,
            R.drawable.photo_2d_02,
            R.drawable.photo_2d_03,
            R.drawable.photo_2d_04,
            R.drawable.photo_2d_05,
            R.drawable.photo_2d_06,
            R.drawable.photo_2d_07,
            R.drawable.photo_2d_08,
            R.drawable.photo_2d_09,
            R.drawable.photo_2d_10,
            R.drawable.photo_2d_11,
            R.drawable.photo_2d_12,
            R.drawable.photo_2d_13,
            R.drawable.photo_2d_14,
            R.drawable.photo_2d_15,
            R.drawable.photo_2d_16,
            R.drawable.photo_2d_17,
            R.drawable.photo_2d_18,
            R.drawable.photo_2d_19,
            R.drawable.photo_2d_20,
            R.drawable.photo_2d_21,
            R.drawable.photo_2d_22,
            R.drawable.photo_2d_23,
            R.drawable.photo_2d_24,
            R.drawable.photo_2d_25,
            R.drawable.photo_2d_26,

            R.drawable.photo_3d_01,
            R.drawable.photo_3d_02,
            R.drawable.photo_3d_03,
            R.drawable.photo_3d_05,
            R.drawable.photo_3d_04,
            R.drawable.photo_3d_07,
            R.drawable.photo_3d_06,
            R.drawable.photo_3d_09,
            R.drawable.photo_3d_08,
            R.drawable.photo_3d_11,
            R.drawable.photo_3d_10,
            R.drawable.photo_3d_13,
            R.drawable.photo_3d_12,
            R.drawable.photo_3d_14,
            R.drawable.photo_3d_15,
            R.drawable.photo_3d_16,
            R.drawable.photo_3d_17,
            R.drawable.photo_3d_19,
            R.drawable.photo_3d_18
    };

    public static String[] Frames = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"
    };

    public static String[] Duration = new String[]{
            "0", "1", "2", "3", "4", "5"
    };

    public static String[] themeListhum = {
            "a", "up", "ovelty"
    };


  /*  private void setVideoAssetsPath() {
        String AppCacheDirPath = "";

        File dCimDirPath = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).getAbsolutePath());

        if (!dCimDirPath.exists())
            dCimDirPath.mkdir();



        ArrayImage.mainFolder = AppCacheDirPath + File.separator + ".PhotoEffectAnimation";
        ArrayImage.myCreationFolder = dCimDirPath + File.separator + "PhotoEffectAnimation";

    }*/

    public static String getFolderPath(Context context) {

     /*   String path = context.getCacheDir() + "/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }*/
        String AppCacheDirPath = "";

        if (context.getFilesDir().exists())
            AppCacheDirPath = context.getFilesDir().getAbsolutePath();
        else
            AppCacheDirPath = context.getExternalCacheDir().getAbsolutePath();

        return AppCacheDirPath;
    }

    public static String getOutputPath(Context context) {

        String path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).getAbsolutePath();

        Log.e("TAG", path);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getAbsolutePath();
    }


    public static void gotoUrl(Context context) {
        try {
            String packageName = "com.android.chrome";
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(context, Uri.parse(PowerPreference.getDefaultFile().getString(Constant.POLICY, "")));
        } catch (Exception e) {
            Log.e("log", e.toString());
        }
    }

}
