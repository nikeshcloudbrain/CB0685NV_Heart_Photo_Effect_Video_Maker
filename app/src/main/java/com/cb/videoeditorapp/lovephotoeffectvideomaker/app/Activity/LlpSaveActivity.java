package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivitySavePhotoBinding;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.preference.PowerPreference;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LlpSaveActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.previewExoplayer)
    SimpleExoPlayerView previewExoplayer;
    @BindView(R.id.imgWhatsapp)
    ImageView imgWhatsapp;
    @BindView(R.id.imgFb)
    ImageView imgFb;
    @BindView(R.id.imgInsta)
    ImageView imgInsta;
    @BindView(R.id.imgMore)
    ImageView imgMore;
   ActivitySavePhotoBinding binding;

    private SimpleExoPlayer ecoPlayer;
    DataSpec dataSpec;
    private String yourRealPath;
    private boolean onBack;

    @Override
    protected void onResume() {
        super.onResume();
        if (ecoPlayer == null)
            initializePlayer();
        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new ListBannerAds().showListBannerAds(this, binding.includedBanner.nativeAdMini, binding.includedBanner.adSpaceMini);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySavePhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);

        onClick();
        initializePlayer();
    }

    private void onClick() {

        txtTitle.setText("Share");

        ivBack.setOnClickListener(this);
        imgWhatsapp.setOnClickListener(this);
        imgFb.setOnClickListener(this);
        imgInsta.setOnClickListener(this);
        imgMore.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        if (onBack) {
                            Intent ii = new Intent(LlpSaveActivity.this, LlpHomeActivity.class);
                            startActivity(ii);
                            finish();
                        } else {
                            Intent ii = new Intent(LlpSaveActivity.this, LlpCreationActivity.class);
                            startActivity(ii);
                            finish();
                        }
                    }
                });
                break;

            case R.id.imgWhatsapp:
                ShareVideo("WhatsApp", Constant.SHARE_WHATSAPP, yourRealPath);
                break;

            case R.id.imgFb:
                ShareVideo("Facebook", Constant.SHARE_FACEBOOK, yourRealPath);
                break;

            case R.id.imgInsta:
                ShareVideo("Instagram", Constant.SHARE_INSTA, yourRealPath);
                break;

            case R.id.imgMore:
                try {
                    String shareMsg = "Download " + getString(R.string.app_name) + " App : " + Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                    File mFile = new File(yourRealPath);
                    Uri uri = Uri.fromFile(mFile);
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent videoshare = new Intent(Intent.ACTION_SEND);
                    videoshare.setType("video/*");
                    videoshare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    videoshare.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    videoshare.putExtra(Intent.EXTRA_TEXT, shareMsg);
                    videoshare.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(videoshare);
                } catch (Exception e) {
                }
                break;
        }
    }

    private void initializePlayer() {
        yourRealPath = getIntent().getStringExtra("finlpath");
        onBack = getIntent().getBooleanExtra("onBack", true);

        notifyMediaScannerService(yourRealPath);
        resetExternalStorageMedia();

        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            ecoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            dataSpec = new DataSpec(Uri.parse(yourRealPath));

            final FileDataSource fileDataSource = new FileDataSource();
            try {
                fileDataSource.open(dataSpec);

            } catch (FileDataSource.FileDataSourceException e) {
                e.printStackTrace();
            }

            DataSource.Factory factory = () -> fileDataSource;
            final MediaSource audioSource = new ExtractorMediaSource(fileDataSource.getUri(),
                    factory, new DefaultExtractorsFactory(), null, null);
            final LoopingMediaSource loopingSource = new LoopingMediaSource(audioSource);


            ecoPlayer.prepare(loopingSource);
            ecoPlayer.setPlayWhenReady(true);
            previewExoplayer.setPlayer(ecoPlayer);
            previewExoplayer.setVisibility(View.VISIBLE);

            ecoPlayer.setRepeatMode(ecoPlayer.REPEAT_MODE_ALL);

        } catch (Exception e) {
            FunctionsPhoto.LogE("MainAcvtivity", " exoplayer error " + e.toString());
        }
    }


    public void resetExternalStorageMedia() {
        if (Environment.isExternalStorageEmulated())
            return;
        Uri uri = Uri.parse("file://" + Environment.getExternalStorageDirectory());
        Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, uri);

        sendBroadcast(intent);
    }

    public void notifyMediaScannerService(String path) {
        MediaScannerConnection.scanFile(LlpSaveActivity.this,
                new String[]{path}, null,
                (path1, uri) -> {
                    FunctionsPhoto.LogE("ExternalStorage", "Scanned " + path1 + ":");
                    FunctionsPhoto.LogE("ExternalStorage", "-> uri=" + uri);
                });
    }


    public void ShareVideo(String name, String shareFlag, String createdVideo) {
        File mFile = new File(createdVideo);
        if (mFile.exists()) {
            PackageManager pm = getPackageManager();
            boolean isInstalled = isAppInstall(shareFlag, pm);

                try {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());

                    String shareMsg = "Download " + getString(R.string.app_name) + " App : " + Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());

                    Uri uri = Uri.fromFile(mFile);
                    Intent videoshare = new Intent(Intent.ACTION_SEND);
                    videoshare.setType("video/*");
                    if (shareFlag != null) videoshare.setPackage(shareFlag);
                    videoshare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    videoshare.putExtra(Intent.EXTRA_STREAM, uri);
                    videoshare.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    videoshare.putExtra(Intent.EXTRA_TEXT, shareMsg);
                    startActivity(videoshare);
                } catch (Exception e) {
                    Toast.makeText(LlpSaveActivity.this, name+" Not Installed", Toast.LENGTH_SHORT).show();

                }

        } else {
            Toast.makeText(LlpSaveActivity.this, "Something went wrong...!!", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isAppInstall(String packageName, PackageManager packageManager) {
        boolean found = true;
        try {
            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

            found = false;
        }

        return found;
    }

    @Override
    public void onBackPressed() {
        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                if (onBack) {
                    Intent ii = new Intent(LlpSaveActivity.this, LlpHomeActivity.class);
                    startActivity(ii);
                    finish();
                } else {
                    Intent ii = new Intent(LlpSaveActivity.this, LlpCreationActivity.class);
                    startActivity(ii);
                    finish();
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        if (ecoPlayer != null) {
            ecoPlayer.release();
            ecoPlayer = null;
        }
    }



}
