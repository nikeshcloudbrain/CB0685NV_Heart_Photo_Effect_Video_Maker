package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import static com.preference.provider.PreferenceProvider.context;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.MainAdapter;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ItemClickListener;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.NewOpenAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.api.LlpApiClient;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.LlpUpdateAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.VpnListModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.preference.PowerPreference;


import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unified.vpn.sdk.AuthMethod;
import unified.vpn.sdk.CompletableCallback;
import unified.vpn.sdk.HydraTransport;
import unified.vpn.sdk.OpenVpnTransportConfig;
import unified.vpn.sdk.RemainingTraffic;
import unified.vpn.sdk.SessionConfig;
import unified.vpn.sdk.TrackingConstants;
import unified.vpn.sdk.UnifiedSdk;
import unified.vpn.sdk.User;
import unified.vpn.sdk.VpnException;
import unified.vpn.sdk.VpnState;
import unified.vpn.sdk.VpnStateListener;

public class LlpVPN_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ItemClickListener itemClickListener;
    MainAdapter adapter;
    ImageView btn_connect1;
    TextView vpn_status;

    RelativeLayout lay_loader;


    List<VpnListModel> arrayList = new ArrayList<>();
    int timer;
    TextView vpn_country_name;
    CircleImageView cnt_image;
    String currentVPN = "US";
    TextView btn_con;

    @Override
    public void onBackPressed() {

      back();
    }


    public void back() {

        final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(LlpVPN_Activity.this);
        View mView = getLayoutInflater().inflate(R.layout.exit_layout, null);
        Button btn_no = (Button) mView.findViewById(R.id.btnno);
        Button btn_rate = (Button) mView.findViewById(R.id.btnRateus);
        Button btn_yes = (Button) mView.findViewById(R.id.btnyes);
        RelativeLayout adnative = mView.findViewById(R.id.ad_native);

        adnative.setVisibility(View.GONE);
        alert.setView(mView);
        final android.app.AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        alertDialog.setCanceledOnTouchOutside(false);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                rateus();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(LlpVPN_Activity.this, LlpExitActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        alertDialog.show();
    }

    public void rateus() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlm_vpn);
        LlpUpdateAds appData = new GsonBuilder().create().fromJson(PowerPreference.getDefaultFile().getString(Constant.mAds), LlpUpdateAds.class);
        this.arrayList.addAll(appData.getVpnListCountry());
        recyclerView = findViewById(R.id.select_country);
        vpn_country_name = findViewById(R.id.vpn_country_name);
        cnt_image = findViewById(R.id.flag);
        lay_loader = findViewById(R.id.lay_loader);


        Glide.with(this)
                .load(PowerPreference.getDefaultFile().getString(Constant.Default_Vpn_img))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("LOADDATA-->", "e : " + e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(cnt_image);

        vpn_country_name.setText(PowerPreference.getDefaultFile().getString(Constant.Default_Vpn_Country));


//        adapter1 = new AppsAdapter1(this, arrayList);
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen._5sdp);
//        recyclerView.addItemDecoration(itemDecoration);
//        recyclerView.setAdapter(adapter1);

//        new Handler(Looper.getMainLooper()).postDelayed(this::refreshAdapter, 250);
        btn_connect1 = (ImageView) findViewById(R.id.btn_connect1);
        vpn_status = (TextView) findViewById(R.id.vpn_status);
        currentVPN = PowerPreference.getDefaultFile().getString(Constant.VpnCountryMain, PowerPreference.getDefaultFile().getString(Constant.Default_Country_code, "us"));

        btn_connect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpn_status.setText("Connecting..");
                lay_loader.setVisibility(View.VISIBLE);
                vpn_status.setTextColor(ContextCompat.getColor(context, R.color.blue));
                PowerPreference.getDefaultFile().putString(Constant.VpnCountryMain, currentVPN);
                login();
            }
        });


        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(String s) {
                // Notify adapter
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                currentVPN = s;
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this, arrayList, itemClickListener);
        recyclerView.setAdapter(adapter);

        try {
            btn_con = (TextView) findViewById(R.id.vcontinue);
            btn_con.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lay_loader.setVisibility(View.VISIBLE);
                    gotoSkip();
                }
            });


        } catch (Exception e) {
            Log.w("Catch", Objects.requireNonNull(e.getMessage()));
        }
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
                Log.e("TAG", e.toString());
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
                .withVirtualLocation(PowerPreference.getDefaultFile().getString(Constant.VpnCountryMain,
                        currentVPN))
                .build(), new CompletableCallback() {
            @Override
            public void complete() {
                btn_connect1.setImageResource(R.drawable.vp_connected);

                vpn_status.setText("Connected");
                vpn_status.setTextColor(ContextCompat.getColor(context, R.color.connect_vpn));
                lay_loader.setVisibility(View.VISIBLE);
               gotoSkip();

                Toast.makeText(LlpVPN_Activity.this, "VPN Connected Successfully. You can continue to application", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void error(@NonNull VpnException e) {
                Log.e("TAG", e.toString());
                 gotoSkip();
            }
        });
    }

    public void gotoSkip() {
        if (PowerPreference.getDefaultFile().getInt(Constant.VpnDialogTime, 0) == 1) {
            PowerPreference.getDefaultFile().putBoolean(Constant.Vpnaccept, true);
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                lay_loader.setVisibility(View.GONE);

                if(PowerPreference.getDefaultFile().getInt(Constant.AppOpen,1)==1){
                    new InterAds().showSplashAd(LlpVPN_Activity.this, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            if (PowerPreference.getDefaultFile().getBoolean(Constant.CustomAdsOnOff, true))
                                startActivity(new Intent(LlpVPN_Activity.this, LlpSkipActivity.class));
                            else
                                startActivity(new Intent(LlpVPN_Activity.this, LlpStartActivity.class));
                        }
                    });
                }else if(PowerPreference.getDefaultFile().getInt(Constant.AppOpen,2)==2){
                    new NewOpenAds().showOpenAd(LlpVPN_Activity.this, new NewOpenAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            if (PowerPreference.getDefaultFile().getBoolean(Constant.CustomAdsOnOff, true))
                                startActivity(new Intent(LlpVPN_Activity.this, LlpSkipActivity.class));
                            else
                                startActivity(new Intent(LlpVPN_Activity.this, LlpStartActivity.class));
                        }
                    });
                }else {
                    if (PowerPreference.getDefaultFile().getBoolean(Constant.CustomAdsOnOff, true))
                        startActivity(new Intent(LlpVPN_Activity.this, LlpSkipActivity.class));
                    else
                        startActivity(new Intent(LlpVPN_Activity.this, LlpStartActivity.class));
                }
            }
        }, 4000);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isvpn()) {
            btn_connect1.setImageResource(R.drawable.vp_connected);
            vpn_status.setText("Connected");
            vpn_status.setTextColor(ContextCompat.getColor(context, R.color.connect_vpn));

            if (btn_con != null) {
                btn_con.setVisibility(View.VISIBLE);
            }
        } else {
            btn_connect1.setImageResource(R.drawable.vp_connectedn);
            vpn_status.setText("Not Connected");
            vpn_status.setTextColor(ContextCompat.getColor(context, R.color.teal_200));
            btn_con.setVisibility(View.GONE);
        }

    }

    public boolean isvpn() {
        String iface = "";
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    iface = networkInterface.getName();
                Log.d("DEBUG", "IFACE NAME: " + iface);
                if (iface.contains("tun") || iface.contains("ppp") || iface.contains("pptp")) {
                    return true;
                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        return false;
    }

    public void getSize() {
        UnifiedSdk.getInstance().getBackend().remainingTraffic(new unified.vpn.sdk.Callback<RemainingTraffic>() {
            @Override
            public void success(@NonNull RemainingTraffic remainingTraffic) {
                disconnected(remainingTraffic.getTrafficUsed());
            }

            @Override
            public void failure(@NonNull VpnException e) {
                disconnected(0);
            }
        });
    }

    public void disconnected(long data) {
        @SuppressLint("HardwareIds") String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        JsonObject object = new JsonObject();
        object.addProperty("AndroidId", deviceId);
        object.addProperty("Country", PowerPreference.getDefaultFile().getString(Constant.VpnCountryMain, PowerPreference.getDefaultFile().getString(Constant.Default_Country_code, "us")));
        object.addProperty("DisconnectType", 1);
        object.addProperty("UsedMb", data);

        LlpApiClient.getInstance().getApi2().vpnApi(object).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    public void connected() {

        UnifiedSdk.addVpnStateListener(new VpnStateListener() {
            @Override
            public void vpnStateChanged(@NonNull VpnState vpnState) {
                if (vpnState.name().equalsIgnoreCase("DISCONNECTING")) {
                    if (PowerPreference.getDefaultFile().getBoolean(Constant.isVpnAuto, true)) {
                        getSize();
                    }
                }
            }

            @Override
            public void vpnError(@NonNull VpnException e) {
            }
        });
        @SuppressLint("HardwareIds") String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        JsonObject object = new JsonObject();
        object.addProperty("AndroidId", deviceId);
        object.addProperty("Country", PowerPreference.getDefaultFile().getString(Constant.VpnCountryMain, PowerPreference.getDefaultFile().getString(Constant.Default_Country_code, "us")));

        LlpApiClient.getInstance().getApi2().vpnApi(object).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                gotoSkip();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                gotoSkip();
            }
        });

    }

    public void notConnected(String error) {

        @SuppressLint("HardwareIds") String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        JsonObject object = new JsonObject();
        object.addProperty("AndroidId", deviceId);
        object.addProperty("Country", PowerPreference.getDefaultFile().getString(Constant.VpnCountryMain, PowerPreference.getDefaultFile().getString(Constant.Default_Country_code, "us")));
        object.addProperty("NotConnected", 1);
        object.addProperty("VpnResponse", error);

        LlpApiClient.getInstance().getApi2().vpnApi(object).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                gotoSkip();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                gotoSkip();
            }
        });
    }
}