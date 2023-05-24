package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ApplicationPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.LargeNativeAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.preference.PowerPreference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LlpHomeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgSingleImage)
    LinearLayout imgSingleImage;
    @BindView(R.id.imgMultiImage)
    LinearLayout imgMultiImage;
    @BindView(R.id.imgSingleVideo)
    LinearLayout imgSingleVideo;
    @BindView(R.id.nativeAd)
    FrameLayout nativeAd;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private int perNo;
    private ArrayList arrayThemeList;
    private byte[] buffer = null;

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
        setContentView(R.layout.activity_home_photo);
        ButterKnife.bind(this);

        imgSingleImage.setOnClickListener(this);
        imgSingleVideo.setOnClickListener(this);
        imgMultiImage.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        txtTitle.setText("Select Photo Edit");


        if (checkPermission()) {
//            new DownloadTheme().execute();
            new DownloadTheme().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            storeRAWFilesToInternalStorage();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSingleImage:
                perNo = 0;
                if (checkPermission()) {
                    OpenIntent(0);
                } else requestPermission();

                break;

            case R.id.imgMultiImage:
                perNo = 1;
                if (checkPermission()) {
                    OpenIntent(1);
                } else requestPermission();

                break;

            case R.id.imgSingleVideo:
                perNo = 2;
                if (checkPermission()) {
                    OpenIntent(2);
                } else requestPermission();
                break;

            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    private class DownloadTheme extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.gc();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 0; i < Constant.themeListhum.length; i++) {
                    arrayThemeList = new ArrayList();
                    String strthum = Constant.themeListhum[i];
                    SaveThumJpg(getImageFromAssetsFile(LlpHomeActivity.this, strthum + "/" + getString(R.string.theme_img_name)), strthum);
                    try {
                        for (String str2 : getAssets().list(strthum + "/" + strthum)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(strthum + "/" + strthum);
                            sb.append(File.separator);
                            sb.append(str2);
                            arrayThemeList.add(sb.toString());
                        }
                    } catch (Exception unused) {
                        FunctionsPhoto.LogW("Catch", unused.getMessage());
                    }

                    for (int j = 0; j < arrayThemeList.size(); j++) {
                        SaveImagesJpg(getImageFromAssetsFile(LlpHomeActivity.this, (String) arrayThemeList.get(j)), j, strthum);
                    }
                }
            } catch (Exception unused) {
                unused.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public Bitmap getImageFromAssetsFile(Context mContext, String fileName) {
        Bitmap image = null;
        AssetManager am = mContext.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void SaveImagesJpg(Bitmap mBitmap, int number, String effectName) {

        String mainFolder = getString(R.string.mainFolder);
        String effectFolder = getString(R.string.theme_folder);

        File mainFile = new File(getFilesDir().getAbsolutePath() + "/" + mainFolder);
        if (!mainFile.exists())
            mainFile.mkdirs();
        File effectFile = new File(mainFile, effectFolder);
        if (!effectFile.exists())
            effectFile.mkdirs();
        File effectFol = new File(effectFile, effectName + "/" + effectName);

        if (!effectFol.exists())
            effectFol.mkdirs();

        File file = new File(effectFol, String.format(effectName + "_%03d.png", number));
        try {
            if (file.exists()) {
                file.delete();
            }
            OutputStream fileOutputStream = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();

            ArrayList<String> data = new Gson().fromJson(PowerPreference.getDefaultFile().getString("mEffect", new Gson().toJson(new ArrayList<String>())), new TypeToken<ArrayList<String>>() {}.getType());
            data.add(effectFile + File.separator + effectName);
            PowerPreference.getDefaultFile().putString("mEffect", new Gson().toJson(data));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void SaveThumJpg(Bitmap mBitmap, String effectName) {

        String mainFolder = getString(R.string.mainFolder);
        String effectFolder = getString(R.string.theme_folder);

        File mainFile = new File(getFilesDir().getAbsolutePath() + "/" + mainFolder);
        if (!mainFile.exists())
            mainFile.mkdirs();
        File effectFile = new File(mainFile, effectFolder);
        if (!effectFile.exists())
            effectFile.mkdirs();
        File effectFol = new File(effectFile, effectName);

        if (!effectFol.exists())
            effectFol.mkdirs();

        File file = new File(effectFol, getString(R.string.theme_img_name));
        if (file.exists()) {
            file.delete();
        }
        try {
            OutputStream fileOutputStream = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();

            ArrayList<String> data = new Gson().fromJson(PowerPreference.getDefaultFile().getString("mEffect", new Gson().toJson(new ArrayList<String>())), new TypeToken<ArrayList<String>>() {}.getType());
            data.add(effectFile + File.separator + effectName);
            PowerPreference.getDefaultFile().putString("mEffect", new Gson().toJson(data));
        } catch (Exception e) {
            FunctionsPhoto.LogW("Catch", " " + e.getMessage());
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    public void storeRAWFilesToInternalStorage() {
        buffer = new byte[3000000];

        String mainFolder = getString(R.string.mainFolder);
        File mainFile = new File(Constant.getFolderPath(ApplicationPhoto.getContext()) + "/" + mainFolder);
        if (!mainFile.exists())
            mainFile.mkdir();

        File subFile = new File(mainFile, getString(R.string.temp_audio_folder));
        if (!subFile.exists())
            subFile.mkdir();

        InputStream fileStream = getResources().openRawResource(R.raw._2);
        try {
            fileStream.read(buffer);
            File fileWithinMyDir = new File(subFile, "temp.mp3");
            FileOutputStream outputStream = new FileOutputStream(fileWithinMyDir);

            outputStream.write(buffer);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    boolean writeD = shouldShowRequestPermissionRationale(permissions[0]);
                    boolean readD = shouldShowRequestPermissionRationale(permissions[1]);
                    if (write && read) {
                        FunctionsPhoto.LogW("Permission", "Permission Granted, Now you can access Read State Permission Data.");

//                        new DownloadTheme().execute();
                        new DownloadTheme().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        storeRAWFilesToInternalStorage();

                        if (perNo == 0) {
                            OpenIntent(0);
                        } else if (perNo == 1) {
                            OpenIntent(1);
                        } else if (perNo == 2) {
                            OpenIntent(2);
                        }

                    } else if (!writeD && !readD) {
                        rePermissionDialog("You need to allow access to the permissions. Without this permission you can't access your storage. Are you sure deny this permission?",
                                (dialog, which) -> {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                });

                        FunctionsPhoto.LogW("Permission", "Permission Deny Dialog");
                    } else {
                        FunctionsPhoto.LogW("Permission", "Permission Denied, You cannot access Read State Permission Data.");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) ||
                                    shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {

                                showMessageOKCancel("You need to allow access to the permissions",
                                        (dialog, which) -> {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                                        PERMISSION_REQUEST_CODE);
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LlpHomeActivity.this)

                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void rePermissionDialog(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LlpHomeActivity.this)
                .setTitle("Permission Denied")
                .setMessage(message)
                .setPositiveButton("Give Permission", okListener)
                .setNegativeButton("Deny Permission", null)
                .create()
                .show();
    }

    private void OpenIntent(final int category) {

        new InterAds().showInterAds(LlpHomeActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                if (category == 0) {
                    Intent i = new Intent(LlpHomeActivity.this, LlpGalleryActivity.class);
                    i.putExtra("isSingle", true);
                    startActivity(i);
                } else if (category == 1) {
                    Intent i = new Intent(LlpHomeActivity.this, LlpGalleryActivity.class);
                    i.putExtra("isSingle", false);
                    startActivity(i);
                } else if (category == 2) {
                    Intent i = new Intent(LlpHomeActivity.this, LlpGalleryVideoActivity.class);
                    startActivity(i);

                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                Intent i = new Intent(LlpHomeActivity.this, LlpStartActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
