package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.CustomAdsAdapter;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.LargeNativeAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.LlpAdApp;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.LlpUpdateAds;
import com.google.gson.GsonBuilder;
import com.preference.PowerPreference;


import java.util.ArrayList;
import java.util.List;

public class LlpSkipActivity extends AppCompatActivity {
    String data;
    private RecyclerView recycler;
    private CustomAdsAdapter CustomAdsAdapter;
    Button btncontinue;
    List<LlpAdApp> arrayList = new ArrayList<>();


    @Override
    protected void onResume() {
        super.onResume();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new LargeNativeAds().showNativeAds(this, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlm_skip);

        recycler = findViewById(R.id.rvad);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));

        data = PowerPreference.getDefaultFile().getString("key");

        //http request
        LlpUpdateAds appData = new GsonBuilder().create().fromJson(PowerPreference.getDefaultFile().getString(Constant.mAds), LlpUpdateAds.class);
        this.arrayList.addAll(appData.getCustomadsData());

        CustomAdsAdapter = new CustomAdsAdapter(arrayList, getApplicationContext());
        recycler.setAdapter(CustomAdsAdapter);


        btncontinue = (Button) findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InterAds().showInterAds(LlpSkipActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent i = new Intent(getApplicationContext(), LlpStartActivity.class);
                        startActivity(i);
                    }
                });
            }
        });


    }



    public void showDialog(Activity activity) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(LlpSkipActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.exit_layout, null);
        Button btn_no = (Button) mView.findViewById(R.id.btnno);
        Button btn_rate = (Button) mView.findViewById(R.id.btnRateus);
        Button btn_yes = (Button) mView.findViewById(R.id.btnyes);
        FrameLayout nativeframe=mView.findViewById(R.id.nativeAd);
        TextView adspace=mView.findViewById(R.id.ad_space);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
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
                if (PowerPreference.getDefaultFile().getBoolean(Constant.GoogleExitSplashInterOnOff, true)) {
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


            }
        });
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                if(PowerPreference.getDefaultFile().getBoolean(Constant.LoaderNativeOnOff,false)){
                    new LargeNativeAds().showNativeAds(LlpSkipActivity.this, alertDialog);
                }else {
                    nativeframe.setVisibility(View.GONE);
                    adspace.setVisibility(View.GONE);
                }
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
    public void onBackPressed() {

                showDialog(LlpSkipActivity.this);

    }

}