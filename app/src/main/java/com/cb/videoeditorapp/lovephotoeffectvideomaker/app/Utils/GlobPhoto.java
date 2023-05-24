package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils;

import android.app.Activity;
import android.content.Context;

import java.io.File;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

public class GlobPhoto {
    public static int blurRadius = 12;
    public static GPUImageFilter gpuImageFilter = null;
    public static boolean showUpdate = true;
    public static String zipPath;

    public static String getOnlineMusicPath(Context context) {
        String absolutePath = context.getFilesDir().getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        sb.append(absolutePath);
        sb.append(File.separator);
        sb.append("OnlineMusic");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdir();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(file.getAbsolutePath());
        sb2.append(File.separator);
        return sb2.toString();
    }

    public static String getZipPath(Activity activity) {
        String absolutePath = activity.getFilesDir().getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        sb.append(absolutePath);
        sb.append(File.separator);
        sb.append("Theme");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdir();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(file.getAbsolutePath());
        sb2.append(File.separator);
        return sb2.toString();
    }

    public static String getStickerPath(Activity activity) {
        String absolutePath = activity.getFilesDir().getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        sb.append(absolutePath);
        sb.append(File.separator);
        sb.append("Sticker");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdir();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(file.getAbsolutePath());
        sb2.append(File.separator);
        return sb2.toString();
    }
}
