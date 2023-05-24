package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.preference.PowerPreference;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityTlmQurekaInterBinding;


public class LlpQurekaInterActivity extends AppCompatActivity {


    public boolean isBackAds = false;
    boolean isClicked = false;
    ActivityTlmQurekaInterBinding binding;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTlmQurekaInterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null && getIntent().hasExtra(Constant.BACK_ADS)) {
            isBackAds = getIntent().getBooleanExtra(Constant.BACK_ADS, true);
        }

        setQureka(this, binding.qurekaAds, binding.qurekaAds1, binding.gifInterRound);
        binding.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBackAds) {
                    if (BackInterAds.mOnAdClosedListener != null)
                        BackInterAds.mOnAdClosedListener.onAdClosed();
                } else {
                    if (InterAds.mOnAdClosedListener != null)
                        InterAds.mOnAdClosedListener.onAdClosed();
                }
                Constant.gotoAds(LlpQurekaInterActivity.this);
                finish();
            }
        });

        binding.qurekaAdsClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PowerPreference.getDefaultFile().getBoolean(Constant.QurekaInterCloseOnOff, true)) {
                    onBackPressed();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isBackAds) {
            if (BackInterAds.mOnAdClosedListener != null)
                BackInterAds.mOnAdClosedListener.onAdClosed();
        } else {
            if (InterAds.mOnAdClosedListener != null)
                InterAds.mOnAdClosedListener.onAdClosed();
        }
        finish();
    }
}