package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BannerAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityStartPhotoBinding;
import com.preference.PowerPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LlpStartActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.imageStart)
    RelativeLayout imageStart;
    @BindView(R.id.imageCreation)
    RelativeLayout imageCreation;
    @BindView(R.id.imageShare)
    ImageView imageShare;
    @BindView(R.id.imagePrivacy)
    ImageView imagePrivacy;
    @BindView(R.id.imageRate)
    ImageView imageRate;

    ActivityStartPhotoBinding binding;

    private static final int PERMISSION_CODE = 200;
    private Dialog dialog;

    @Override
    protected void onResume() {
        super.onResume();

        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
new BannerAds().showBannerAds(this,binding.includedBanner.adBanner,binding.includedBanner.adSpaceBanner);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding=ActivityStartPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);
        onclick();
    }

    public void onclick() {

        imageStart.setOnClickListener(this);
        imageCreation.setOnClickListener(this);
        imageShare.setOnClickListener(this);
        imagePrivacy.setOnClickListener(this);
imageRate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.imageStart:
                new InterAds().showInterAds(LlpStartActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(LlpStartActivity.this, LlpHomeActivity.class);
                        startActivity(intent);
                    }
                });
                break;

            case R.id.imageCreation:
                if (checkPermission()) {
                    new InterAds().showInterAds(LlpStartActivity.this, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            Intent intent = new Intent(LlpStartActivity.this, LlpCreationActivity.class);
                            startActivity(intent);
                        }
                    });
                    FunctionsPhoto.LogW("Permission", "Permission already granted.");
                } else setPermission();
                break;

            case R.id.imageShare:
                String shareMsg = "Download " + getString(R.string.app_name) + " App : " + Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());

                Intent shareIntent = new Intent();
                shareIntent.setAction("android.intent.action.SEND");
                shareIntent.setType("text/plain");
                shareIntent.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name));
                shareIntent.putExtra("android.intent.extra.TEXT", shareMsg);
                startActivity(Intent.createChooser(shareIntent, "Share Application"));
                break;

            case R.id.imagePrivacy:
                Constant.gotoUrl(LlpStartActivity.this);
                break;

            case R.id.imageRate:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void setPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0) {

                    boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    boolean writeD = shouldShowRequestPermissionRationale(permissions[0]);
                    boolean readD = shouldShowRequestPermissionRationale(permissions[1]);
                    if (write && read) {
                        FunctionsPhoto.LogW("Permission", "Permission Granted, Now you can access Read State Permission Data.");
                        Intent intent = new Intent(LlpStartActivity.this, LlpCreationActivity.class);
                        startActivity(intent);

                    } else if (!writeD && !readD) {
                        forcePermissionDialog("You need to allow access to the permissions. Without this permission you can't access your storage. Are you sure deny this permission?",
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
                                oKCancelDialog("You need to allow access to the permissions",
                                        (dialog, which) -> {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                                        PERMISSION_CODE);
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

    private void oKCancelDialog(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LlpStartActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void forcePermissionDialog(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LlpStartActivity.this)
                .setTitle("Permission Denied")
                .setMessage(message)
                .setPositiveButton("Give Permission", okListener)
                .setNegativeButton("Deny Permission", null)
                .create()
                .show();
    }



    @Override
    public void onBackPressed() {
        dilog_show();

    }

    public void dilog_show() {


        try {

            if (dialog != null && dialog.isShowing())
                dialog.dismiss();

            dialog = new Dialog(LlpStartActivity.this);
            dialog.setContentView(R.layout.dialog_exit);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            AppCompatButton btncancel=(AppCompatButton)dialog.findViewById(R.id.btnCancel);
            AppCompatButton btnexit=(AppCompatButton)dialog.findViewById(R.id.btnExit);
            AppCompatButton btnrateUs=(AppCompatButton)dialog.findViewById(R.id.btnRate);

            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });
            btnexit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    new BackInterAds().showInterAds(LlpStartActivity.this, new BackInterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            Intent intent = new Intent(LlpStartActivity.this, LlpExitActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                           finish();


                        }
                    });
                }
            });

            btnrateUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +getPackageName()));
                    startActivity(i);
                }
            });

            if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {

                int ui_flags =
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

                dialog.getWindow().
                        setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                dialog.getWindow().getDecorView().setSystemUiVisibility(ui_flags);
            }
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
