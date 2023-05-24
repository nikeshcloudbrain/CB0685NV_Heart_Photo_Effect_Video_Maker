package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.CropImageView.CropMode;
import com.isseiaoki.simplecropview.CropImageView.RotateDegrees;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.preference.PowerPreference;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityCropMultilePhotoBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LlpCropMultipleActivity extends AppCompatActivity {
    static String image_path;
    ActivityCropMultilePhotoBinding binding;
    private final OnClickListener btnListener = view -> {
        switch (view.getId()) {
            case R.id.button16_9:
                LlpCropMultipleActivity.this.cropImageView.setCropMode(CropMode.RATIO_16_9);
                return;
            case R.id.button1_1:
                LlpCropMultipleActivity.this.cropImageView.setCropMode(CropMode.SQUARE);
                return;
            case R.id.button3_4:
                LlpCropMultipleActivity.this.cropImageView.setCropMode(CropMode.RATIO_3_4);
                return;
            case R.id.button4_3:
                LlpCropMultipleActivity.this.cropImageView.setCropMode(CropMode.RATIO_4_3);
                return;
            case R.id.button9_16:
                LlpCropMultipleActivity.this.cropImageView.setCropMode(CropMode.RATIO_9_16);
                return;
            case R.id.buttonCustom:
                LlpCropMultipleActivity.this.cropImageView.setCustomRatio(7, 5);
                return;
            case R.id.buttonDone:
                LlpCropMultipleActivity.this.cropImage();
                return;
            case R.id.buttonFitImage:
                LlpCropMultipleActivity.this.cropImageView.setCropMode(CropMode.FIT_IMAGE);
                return;
            case R.id.buttonFree:
                LlpCropMultipleActivity.this.cropImageView.setCropMode(CropMode.FREE);
                return;
            case R.id.buttonPickImage:
                onBackPressed();
                return;
            case R.id.buttonRotateLeft:
                LlpCropMultipleActivity.this.cropImageView.rotateImage(RotateDegrees.ROTATE_M90D);
                return;
            case R.id.buttonRotateRight:
                LlpCropMultipleActivity.this.cropImageView.rotateImage(RotateDegrees.ROTATE_90D);
                return;
            default:
                return;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        if (PowerPreference.getDefaultFile().getBoolean(Constant.FULL_SCREEN, true)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        new ListBannerAds().showListBannerAds(this, binding.includedBanner.nativeAdMini, binding.includedBanner.adSpaceMini);

    }
    private final CropCallback mCropCallback = new CropCallback() {
        public void onSuccess(Bitmap bitmap) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(LlpCropMultipleActivity.image_path));
                bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
                Log.e("spideronSuccess: ", LlpCropMultipleActivity.image_path);
                Intent intent = getIntent();
                intent.putExtra("image_path", LlpCropMultipleActivity.image_path);
                setResult(-1, intent);
                finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (OutOfMemoryError e3) {
                e3.printStackTrace();
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }

        public void onError(Throwable th) {
            FunctionsPhoto.LogW("spideronError: ", th.getMessage());
        }
    };
    @BindView(R.id.cropImageView)
    CropImageView cropImageView;
    @BindView(R.id.buttonFitImage)
    Button buttonFitImage;
    @BindView(R.id.button1_1)
    Button button1_1;
    @BindView(R.id.button3_4)
    Button button3_4;
    @BindView(R.id.button4_3)
    Button button4_3;
    @BindView(R.id.button9_16)
    Button button9_16;
    @BindView(R.id.button16_9)
    Button button16_9;
    @BindView(R.id.buttonCustom)
    Button buttonCustom;
    @BindView(R.id.buttonFree)
    Button buttonFree;
    @BindView(R.id.buttonPickImage)
    ImageView buttonPickImage;
    @BindView(R.id.buttonRotateLeft)
    ImageView buttonRotateLeft;
    @BindView(R.id.buttonRotateRight)
    ImageView buttonRotateRight;
    @BindView(R.id.buttonDone)
    ImageView buttonDone;


    private RectF mFrameRect = null;
    private final LoadCallback mLoadCallback = new LoadCallback() {
        public void onError(Throwable th) {
        }

        public void onSuccess() {
        }
    };
    private Uri mSourceUri = null;

    public void cropImage() {
        cropImageView.crop(this.mSourceUri).execute(this.mCropCallback);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding=ActivityCropMultilePhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);
        bindViews();
        image_path = getIntent().getStringExtra("image_path");
        mSourceUri = Uri.fromFile(new File(image_path));
        cropImageView.load(this.mSourceUri).initialFrameRect(mFrameRect).useThumbnail(true).execute(this.mLoadCallback);

    }

    private void bindViews() {
        buttonDone.setOnClickListener(btnListener);
        buttonFitImage.setOnClickListener(btnListener);
        button1_1.setOnClickListener(btnListener);
        button3_4.setOnClickListener(btnListener);
        button4_3.setOnClickListener(btnListener);
        button9_16.setOnClickListener(btnListener);
        button16_9.setOnClickListener(btnListener);
        buttonFree.setOnClickListener(btnListener);
        buttonPickImage.setOnClickListener(btnListener);
        buttonRotateLeft.setOnClickListener(btnListener);
        buttonRotateRight.setOnClickListener(btnListener);
        buttonCustom.setOnClickListener(btnListener);
    }

    @Override
    public void onBackPressed() {
        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });
    }
}
