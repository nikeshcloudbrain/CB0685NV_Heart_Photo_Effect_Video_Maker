package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.LlpCheckService;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.BuildConfig;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BannerAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.LargeNativeAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.MiniNativeAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.NewOpenAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.OpenAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.RewardedAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.api.LlpApiClient;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivitySplashPhotoBinding;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.DialogInternetBinding;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.encrypt.DecryptEncrypt;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.LlpUpdateAds;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.preference.PowerPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import unified.vpn.sdk.AuthMethod;
import unified.vpn.sdk.ClientInfo;
import unified.vpn.sdk.CompletableCallback;
import unified.vpn.sdk.HydraTransport;
import unified.vpn.sdk.HydraTransportConfig;
import unified.vpn.sdk.OpenVpnTransportConfig;
import unified.vpn.sdk.ProcessUtils;
import unified.vpn.sdk.SdkNotificationConfig;
import unified.vpn.sdk.SessionConfig;
import unified.vpn.sdk.TrackingConstants;
import unified.vpn.sdk.TransportConfig;
import unified.vpn.sdk.UnifiedSdk;
import unified.vpn.sdk.UnifiedSdkConfig;
import unified.vpn.sdk.User;
import unified.vpn.sdk.VpnException;

public class LlpSplashActivity extends AppCompatActivity {

    @BindView(R.id.layoutRegular)
    LinearLayout layoutRegular;
    @BindView(R.id.appTitle)
    TextView appTitle;
    @BindView(R.id.appDesc)
    TextView appDesc;
    @BindView(R.id.appUpdate)
    AppCompatTextView appUpdate;
    @BindView(R.id.appSkip)
    AppCompatTextView appSkip;
    @BindView(R.id.layoutChange)
    LinearLayout layoutChange;

    private String mReferral = "NA";
    ActivitySplashPhotoBinding binding;
    int VERSION = 0;
    public static Activity activity;
    private static final String CHANNEL_ID = "vpn";

    Dialog dialog;

    private boolean isMyServiceRunning(Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                Log.e("ServiceStatus", "Running");
                return true;
            }
        }
        Log.e("ServiceStatus", "Not running");
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.stopVpn();
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySplashPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            Log.d("Encry", DecryptEncrypt.encryptKey("7QIbSmfoQaeYTReW"));
            Log.d("Encry", DecryptEncrypt.encryptKey("WO0AFzVmYKgHhjMu"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Start();
    }

    public void Start() {
        PowerPreference.getDefaultFile().putBoolean(Constant.isNotify, false);
        ButterKnife.bind(this);

        PowerPreference.getDefaultFile().putBoolean("isClosed", false);

        PowerPreference.getDefaultFile().putBoolean(Constant.isVpnAuto, true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isMyServiceRunning(LlpCheckService.class))
                    startService(new Intent(LlpSplashActivity.this, LlpCheckService.class));

                PowerPreference.getDefaultFile().putBoolean("running", true);
            }
        }, 1000);

        activity = this;

        startSplash();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Start();
    }

    public void startSplash() {
        PackageManager manager = getPackageManager();
        PackageInfo info = null;

        try {
            info = manager.getPackageInfo(getPackageName(), 0);
            VERSION = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Constant.Log(e.toString());
            VERSION = BuildConfig.VERSION_CODE;
        }
        registerReceiver(broadcastReceiver, new IntentFilter("referral"));

        callUpdateApi();
    }

    public DialogInternetBinding network_dialog(String text) {
        dialog = new Dialog(this);
        DialogInternetBinding binding = DialogInternetBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        binding.txtError.setText(text);
        return binding;
    }




    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Sample VPN";
            String description = "VPN notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void callUpdateApi() {

        if (Constant.checkInternet(this)) {

            @SuppressLint("HardwareIds") String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            JsonObject object = new JsonObject();
            object.addProperty("VersionCode", VERSION);
            object.addProperty("PkgName", getPackageName());
            object.addProperty("AndroidId", deviceId);

            LlpApiClient.getInstance().getApi2().getUpdateResponse(object)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                            try {
                                Log.e("data", response.body() + " hel;l");

                                final LlpUpdateAds appData = new GsonBuilder().create().fromJson((DecryptEncrypt.DecryptStr(response.body())), LlpUpdateAds.class);
                                Log.d("appdata", DecryptEncrypt.DecryptStr(response.body()));

                                Gson gson = new Gson();
                                String adsString = gson.toJson(appData);
                                PowerPreference.getDefaultFile().putString(Constant.mAds, adsString);
                                PowerPreference.getDefaultFile().putString(Constant.Default_Vpn_Country, appData.getVpnDefaultCountry().getName());
                                PowerPreference.getDefaultFile().putString(Constant.Default_Country_code, appData.getVpnDefaultCountry().getCode());
                                PowerPreference.getDefaultFile().putString(Constant.Default_Vpn_img, appData.getVpnDefaultCountry().getFlag());
                                PowerPreference.getDefaultFile().putInt(Constant.AppOpen, appData.getAppOpen());

                                PowerPreference.getDefaultFile().putInt(Constant.VpnDialogTime, appData.getVpnDialogTime());
                                PowerPreference.getDefaultFile().putBoolean(Constant.VpnOnOff, appData.getVpnOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.VpnDialog, appData.getVpnDialog());
                                PowerPreference.getDefaultFile().putBoolean(Constant.LoaderNativeOnOff, appData.getLoaderNativeOnOff());

                                if (PowerPreference.getDefaultFile().getBoolean(Constant.VpnOnOff, false)) {
                                    if (!PowerPreference.getDefaultFile().getBoolean(Constant.VpnDialog, true)) {
                                        PowerPreference.getDefaultFile().putString(Constant.VpnCountryMain, appData.getVpnDefaultCountry().getCode());
                                    }
                                }
                                PowerPreference.getDefaultFile().putInt(Constant.SERVER_INTERVAL_COUNT, appData.getGoogleIntervalCount());
                                PowerPreference.getDefaultFile().putInt(Constant.APP_INTERVAL_COUNT, 0);

                                PowerPreference.getDefaultFile().putInt(Constant.SERVER_BACK_COUNT, appData.getGoogleBackInterIntervalCount());
                                PowerPreference.getDefaultFile().putInt(Constant.APP_BACK_COUNT, 0);

                                PowerPreference.getDefaultFile().putBoolean(Constant.CustomAdsOnOff, appData.getCustomAdsOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.FULL_SCREEN, appData.getFullScreenOnOff());

                                PowerPreference.getDefaultFile().putBoolean(Constant.PolicyOnOff, appData.getPolicyOnOff());

                                PowerPreference.getDefaultFile().putBoolean(Constant.BACK_ADS, appData.getGoogleBackInterOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.GoogleSplashOpenAdsOnOff, appData.getGoogleSplashOpenAdsOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.GoogleExitSplashInterOnOff, appData.getGoogleExitSplashInterOnOff());

                                PowerPreference.getDefaultFile().putString(Constant.QUREKA_ADS, appData.getQurekaLink());
                                PowerPreference.getDefaultFile().putString(Constant.POLICY, appData.getPolicyLink());

                                PowerPreference.getDefaultFile().putBoolean(Constant.GoogleBannerOnOff, appData.getGoogleBannerOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.GoogleMiniNativeOnOff, appData.getGoogleMiniNativeOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.GoogleLargeNativeOnOff, appData.getGoogleLargeNativeOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.GoogleListNativeOnOff, appData.getGoogleListNativeOnOff());

                                PowerPreference.getDefaultFile().putInt(Constant.GoogleWhichOneNative, appData.getGoogleWhichOneNative());
                                PowerPreference.getDefaultFile().putInt(Constant.ListNativeWhichOne, appData.getListNativeWhichOne());
                                PowerPreference.getDefaultFile().putInt(Constant.ListNativeAfterCount, appData.getListNativeAfterCount());

                                PowerPreference.getDefaultFile().putBoolean(Constant.QurekaBannerOnOff, appData.getQurekaBannerOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.QurekaInterOnOff, appData.getQurekaInterOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.QurekaBackInterOnOff, appData.getQurekaBackInterOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.QurekaMiniNativeOnOff, appData.getQurekaMiniNativeOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.QurekaLargeNativeOnOff, appData.getQurekaLargeNativeOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.QurekaListNativeOnOff, appData.getQurekaListNativeOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.QurekaAppOpenOnOff, appData.getQurekaAppOpenOnOff());

                                PowerPreference.getDefaultFile().putBoolean(Constant.QurekaInterCloseOnOff, appData.getQurekaInterCloseOnOff());

                                PowerPreference.getDefaultFile().putString(Constant.GoogleNativeText, appData.getGoogleNativeText());
                                PowerPreference.getDefaultFile().putBoolean(Constant.GoogleNativeTextOnOff, appData.getGoogleNativeTextOnOff());

                                PowerPreference.getDefaultFile().putString(Constant.NativeBackgroundColor, appData.getNativeBackgroundColor());
                                PowerPreference.getDefaultFile().putInt(Constant.AllPagesNativeBackgroundCount, appData.getAllPagesNativeBackgroundCount());

                                PowerPreference.getDefaultFile().putBoolean(Constant.HomeNativeBackgroundColorOnOff, appData.getHomeNativeBackgroundColorOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.AllPagesNativeBackgroundOnOff, appData.getAllPagesNativeBackgroundOnOff());

                                PowerPreference.getDefaultFile().putBoolean(Constant.QurekaOnOff, appData.getQurekaOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.AdsOnOff, appData.getAdsOnOff());

                                PowerPreference.getDefaultFile().putString(Constant.OneSignalAppId, appData.getOneSignalAppId());

                                PowerPreference.getDefaultFile().putBoolean(Constant.ShowDialogBeforeAds, appData.getShowDialogBeforeAds());
                                PowerPreference.getDefaultFile().putDouble(Constant.DialogTimeInSec, appData.getDialogTimeInSec());

                                PowerPreference.getDefaultFile().putString(Constant.INTERID, appData.getGoogleInterAds());
                                PowerPreference.getDefaultFile().putString(Constant.NATIVEID, appData.getGoogleNativeAds());
                                PowerPreference.getDefaultFile().putString(Constant.OPENAD, appData.getGoogleAppOpenAds());
                                PowerPreference.getDefaultFile().putString(Constant.BANNERID, appData.getGoogleBannerAds());
                                PowerPreference.getDefaultFile().putString(Constant.REWARDID, appData.getGoogleRewardAds());
                                PowerPreference.getDefaultFile().putBoolean(Constant.GoogleRewardOnOff, appData.getGoogleRewardOnOff());
                                PowerPreference.getDefaultFile().putBoolean(Constant.QurekaRewardOnOff, appData.getQurekaRewardOnOff());


                                if (ProcessUtils.isMainProcess(LlpSplashActivity.this)) {
                                    try {
                                        ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                                        Bundle bundle = ai.metaData;
                                        String myApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
                                        ai.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", appData.getGoogleAppIdAds());
                                    } catch (PackageManager.NameNotFoundException e) {
                                        Log.e("TAG", "Failed to load meta-data, NameNotFound: " + e.getMessage());
                                    } catch (NullPointerException e) {
                                        Log.e("TAG", "Failed to load meta-data, NullPointer: " + e.getMessage());
                                    }

                                    MobileAds.initialize(LlpSplashActivity.this, appData.getGoogleAppIdAds());
                                }

                                new InterAds().loadInterAds(LlpSplashActivity.this);
                                new BackInterAds().loadInterAds(LlpSplashActivity.this);
                                new MiniNativeAds().loadNativeAds(LlpSplashActivity.this, null);
                                new LargeNativeAds().loadNativeAds(LlpSplashActivity.this, null);
                                new NewOpenAds().loadOpenAd(LlpSplashActivity.this);
                                new OpenAds().loadOpenAd();
                                new BannerAds().loadBannerAds(LlpSplashActivity.this);
                                new RewardedAds().loadRewardAds(LlpSplashActivity.this);


                                ArrayList<String> arrayList = new ArrayList<>();

                                arrayList.add(0, LlpSkipActivity.class.getName());
                                arrayList.add(1, LlpStartActivity.class.getName());
                                arrayList.add(2, LlpHomeActivity.class.getName());
                                arrayList.add(3, LlpCreationActivity.class.getName());
                                arrayList.add(4, LlpCropMultipleActivity.class.getName());
                                arrayList.add(5, LlpCropSingleActivity.class.getName());
                                arrayList.add(6, LlpGalleryActivity.class.getName());
                                arrayList.add(7, LlpGalleryVideoActivity.class.getName());
                                arrayList.add(8, LlpImageEditActivity.class.getName());
                                arrayList.add(9, LlpMultipleActivity.class.getName());
                                arrayList.add(10, LlpMusicActivity.class.getName());
                                arrayList.add(11, LlpProgressActivity.class.getName());
                                arrayList.add(12, LlpSaveActivity.class.getName());
                                arrayList.add(13, LlpSingleActivity.class.getName());
                                arrayList.add(14, LlpStoreActivity.class.getName());
                                arrayList.add(15, LlpSwipeActivity.class.getName());
                                arrayList.add(16, LlpTextEditActivity.class.getName());
                                arrayList.add(17, LlpPrivacyPolicyActivity.class.getName());
                                arrayList.add(18, LlpQurekaInterActivity.class.getName());
                                arrayList.add(19, LlpVideoActivity.class.getName());


                                HashMap<String, String> hashMap = new HashMap<>();
                                int total = PowerPreference.getDefaultFile().getInt(Constant.AllPagesNativeBackgroundCount, 0);
                                if (PowerPreference.getDefaultFile().getBoolean(Constant.AllPagesNativeBackgroundOnOff, true)) {
                                    if (total < arrayList.size()) {
                                        Collections.shuffle(arrayList);
                                        for (int i = 0; i < total; i++) {
                                            if (!hashMap.containsKey(arrayList.get(i)))
                                                hashMap.put(arrayList.get(i), arrayList.get(i));
                                        }
                                    } else {
                                        for (int i = 0; i < arrayList.size(); i++) {
                                            hashMap.put(arrayList.get(i), arrayList.get(i));
                                        }
                                    }
                                }
                                PowerPreference.getDefaultFile().putMap(Constant.NativeTrans, hashMap);

                                createNotificationChannel();

                                ClientInfo clientInfo = ClientInfo.newBuilder()
                                        .addUrl(appData.getVpnUrl())
                                        .carrierId(appData.getVpnCarrierId())
                                        .build();

                                List<TransportConfig> transportConfigList = new ArrayList<>();
                                transportConfigList.add(HydraTransportConfig.create());
                                transportConfigList.add(OpenVpnTransportConfig.tcp());
                                transportConfigList.add(OpenVpnTransportConfig.udp());
                                UnifiedSdk.update(transportConfigList, CompletableCallback.EMPTY);

                                SdkNotificationConfig notificationConfig = SdkNotificationConfig.newBuilder()
                                        .title(getResources().getString(R.string.app_name))
                                        .channelId(CHANNEL_ID)
                                        .build();

                                UnifiedSdk.update(notificationConfig);

                                UnifiedSdkConfig config = UnifiedSdkConfig.newBuilder().build();
                                UnifiedSdk.getInstance(clientInfo, config);


                                if (!appData.getTitle().equals("")) {
                                    binding.txtName.setText(appData.getTitle());
                                    binding.txtName.setVisibility(View.VISIBLE);
                                }

                                if (!appData.getDescription().equals("")) {
                                    binding.txtDes.setText(appData.getDescription());
                                    binding.txtDes.setVisibility(View.VISIBLE);
                                }

                                if (!appData.getButtonName().equals("")) {
                                    binding.btnUpdate.setText(appData.getButtonName());
                                }

                                if (!appData.getButtonSkip().equals("")) {
                                    binding.btnSkip.setText(appData.getButtonSkip());
                                }

                                String flag = appData.getFlag();
                                boolean flagCheck = true;

                                if (flag.equals("NORMAL")) {
                                    binding.cvUpdate.setVisibility(View.GONE);
                                    flagCheck = true;
                                } else if (flag.equals("SKIP")) {

                                    if (VERSION != appData.getVersion()) {
                                        binding.btnUpdate.setVisibility(View.VISIBLE);
                                        binding.cvUpdate.setVisibility(View.VISIBLE);
                                        binding.btnSkip.setVisibility(View.VISIBLE);
                                        flagCheck = false;
                                    } else {
                                        binding.cvUpdate.setVisibility(View.GONE);
                                        flagCheck = true;
                                    }
                                } else if (flag.equals("MOVE")) {
                                    binding.btnUpdate.setVisibility(View.VISIBLE);
                                    binding.btnSkip.setVisibility(View.GONE);
                                    binding.cvUpdate.setVisibility(View.VISIBLE);
                                    flagCheck = false;

                                } else if (flag.equals("FORCE")) {
                                    if (VERSION != appData.getVersion()) {
                                        binding.btnUpdate.setVisibility(View.VISIBLE);
                                        binding.btnSkip.setVisibility(View.GONE);
                                        binding.cvUpdate.setVisibility(View.VISIBLE);
                                        flagCheck = false;
                                    } else {
                                        binding.cvUpdate.setVisibility(View.GONE);
                                    }
                                }

                                binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (flag.equals("MOVE")) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appData.getLink())));
                                        } else {
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                                            } catch (ActivityNotFoundException e) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                                            }
                                        }
                                    }
                                });

                                binding.btnSkip.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        binding.cvUpdate.setVisibility(View.GONE);

                                        fetchFiles();
                                    }
                                });

                                if (flagCheck) {

                                    fetchFiles();
                                }

                            } catch (Exception e) {
                                Constant.Log(e.toString());
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call
                                , Throwable t) {
                            network_dialog(t.getMessage()).btnRetry.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    if (Constant.checkInternet(LlpSplashActivity.this)) {
                                        callUpdateApi();
                                    } else dialog.show();
                                }
                            });
                        }
                    });
        } else {
            network_dialog(getResources().getString(R.string.network_error)).btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (Constant.checkInternet(LlpSplashActivity.this)) {
                        callUpdateApi();
                    } else dialog.show();
                }
            });
        }
    }

    public void fetchFiles() {
        if (PowerPreference.getDefaultFile().getBoolean(Constant.VpnOnOff, false)) {
            if (!PowerPreference.getDefaultFile().getBoolean(Constant.VpnDialog, false)) {
                login();
            } else if (PowerPreference.getDefaultFile().getInt(Constant.VpnDialogTime, 1) == 1 && PowerPreference.getDefaultFile().getBoolean(Constant.Vpnaccept, false)) {
                login();
            } else {
                boolean checked = PowerPreference.getDefaultFile().getBoolean(Constant.isPolicyChecked, false);
                if (PowerPreference.getDefaultFile().getBoolean(Constant.PolicyOnOff, true) && !checked)
                    startActivity(new Intent(LlpSplashActivity.this, LlpPrivacyPolicyActivity.class));
                else
                    startActivity(new Intent(LlpSplashActivity.this, LlpVPN_Activity.class));
            }
        } else
            gotoSkip();
    }

    public void gotoSkip() {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {


                if (PowerPreference.getDefaultFile().getBoolean(Constant.PolicyOnOff, true) && !PowerPreference.getDefaultFile().getBoolean(Constant.isPolicyChecked))
                    startActivity(new Intent(LlpSplashActivity.this, LlpPrivacyPolicyActivity.class));
                else {
                    if(PowerPreference.getDefaultFile().getInt(Constant.AppOpen,1)==1){
                        new InterAds().showSplashAd(LlpSplashActivity.this, new InterAds.OnAdClosedListener() {
                            @Override
                            public void onAdClosed() {
                                if (PowerPreference.getDefaultFile().getBoolean(Constant.CustomAdsOnOff, true))
                                    startActivity(new Intent(LlpSplashActivity.this, LlpSkipActivity.class));
                                else
                                    startActivity(new Intent(LlpSplashActivity.this, LlpStartActivity.class));
                            }
                        });
                    }else if(PowerPreference.getDefaultFile().getInt(Constant.AppOpen,2)==2){
                        new NewOpenAds().showOpenAd(LlpSplashActivity.this, new NewOpenAds.OnAdClosedListener() {
                            @Override
                            public void onAdClosed() {
                                if (PowerPreference.getDefaultFile().getBoolean(Constant.CustomAdsOnOff, true))
                                    startActivity(new Intent(LlpSplashActivity.this, LlpSkipActivity.class));
                                else
                                    startActivity(new Intent(LlpSplashActivity.this, LlpStartActivity.class));
                            }
                        });
                    }else {
                        if (PowerPreference.getDefaultFile().getBoolean(Constant.CustomAdsOnOff, true))
                            startActivity(new Intent(LlpSplashActivity.this, LlpSkipActivity.class));
                        else
                            startActivity(new Intent(LlpSplashActivity.this, LlpStartActivity.class));
                    }




                }

            }

        }, 4000);
    }


    public void login() {
        AuthMethod authMethod = AuthMethod.anonymous();
        UnifiedSdk.getInstance().getBackend().login(authMethod, new unified.vpn.sdk.Callback<User>() {
            @Override
            public void success(@NonNull User user) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startVpn();
                    }
                }, 2000);
            }

            @Override
            public void failure(@NonNull VpnException e) {
                Log.e("TAG HELL", e.toString() + " hell");
                gotoSkip();

            }
        });
    }


    public void startVpn() {
        List<String> fallbackOrder = new ArrayList<>();
        fallbackOrder.add(HydraTransport.TRANSPORT_ID);
        fallbackOrder.add(OpenVpnTransportConfig.tcp().getName());
        fallbackOrder.add(OpenVpnTransportConfig.udp().getName());

        UnifiedSdk.getInstance().getVpn().start(new SessionConfig.Builder()
                .withReason(TrackingConstants.GprReasons.M_UI)
                .withTransportFallback(fallbackOrder)
                .withTransport(HydraTransport.TRANSPORT_ID)
                .withVirtualLocation(PowerPreference.getDefaultFile().getString(Constant.VpnCountryMain, PowerPreference.getDefaultFile().getString(Constant.Default_Country_code, "us")))
                .build(), new CompletableCallback() {
            @Override
            public void complete() {
                gotoSkip();
            }

            @Override
            public void error(@NonNull VpnException e) {
                Log.e("TAG HELL", e.toString() + " hell");
                gotoSkip();
                ;
            }
        });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            FunctionsPhoto.LogE("PlayReferral", "broadcastReceiver_In----");
            Bundle b = intent.getExtras();
            mReferral = b.getString("referral");
            FunctionsPhoto.LogE("PlayReferral", "broadcastReceiver_codea------------" + mReferral);
        }
    };


}
