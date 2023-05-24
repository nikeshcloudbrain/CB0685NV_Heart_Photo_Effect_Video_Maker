package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.preference.PowerPreference;


public class LlpExitActivity extends AppCompatActivity {

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlm_exit);
        Constant.stopVpn();
        getWindow().setLayout(-1, -1);
        getWindow().setBackgroundDrawable(null);
        setFinishOnTouchOutside(false);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    System.exit(0);
                } catch (Exception e) {
                    finishAffinity();

                    e.printStackTrace();
                }
            }
        }, 2000);
    }
}