package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class InstallReceiverPhoto extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        FunctionsPhoto.LogE("PlayReferral", "InstallReceiver_In----");
        String rawReferrer = intent.getStringExtra("referrer");
        if (rawReferrer != null) {
            Intent in = new Intent("referral");
            in.putExtra("referral", rawReferrer);
            context.sendBroadcast(in);
            //Toast.makeText(context, "refer_code :=> " + rawReferrer, Toast.LENGTH_SHORT).show();
            FunctionsPhoto.LogE("PlayReferral", "InstallReceiver_refer_code ==> " + rawReferrer);
        }
    }
}
