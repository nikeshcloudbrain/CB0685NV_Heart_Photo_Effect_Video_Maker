package com.cb.videoeditorapp.lovephotoeffectvideomaker.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.preference.PowerPreference;

public class LlpCheckService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        PowerPreference.getDefaultFile().putBoolean("running", false);
        Constant.stopVpn();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        PowerPreference.getDefaultFile().putBoolean("running", false);
        Constant.stopVpn();
        super.onDestroy();
    }
}