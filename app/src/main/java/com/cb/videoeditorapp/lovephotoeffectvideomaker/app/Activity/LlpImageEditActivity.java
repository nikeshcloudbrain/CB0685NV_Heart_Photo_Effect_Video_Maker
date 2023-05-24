package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.StickersAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.RatioModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Service.Service2Photo;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.BitmapStickerIconPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.DeleteIconEventPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.DrawableStickerPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.FlipHorizontallyEventPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.StickerPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.StickerViewPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.ZoomIconEventPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.RecyclerItemClickListenerPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityImageEditPhotoBinding;
import com.google.android.gms.common.ConnectionResult;
import com.preference.PowerPreference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLuminanceThresholdFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSepiaToneFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSolarizeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToneCurveFilter;


public class LlpImageEditActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.layNxt)
    LinearLayout layNxt;
    @BindView(R.id.main_image)
    ImageView main_image;
    @BindView(R.id.bitmapSize)
    FrameLayout bitmapSize;
    @BindView(R.id.cropRecylerview)
    RecyclerView cropRecylerview;
    @BindView(R.id.cropRecylerviewRL)
    RelativeLayout cropRecylerviewRL;
    @BindView(R.id.recycle_strickers)
    RecyclerView recycle_strickers;
    @BindView(R.id.edit_rotate_close)
    ImageView edit_rotate_close;
    @BindView(R.id.edit_rotate_done)
    ImageView edit_rotate_done;
    @BindView(R.id.rotate_edit_left)
    ImageView rotate_edit_left;
    @BindView(R.id.rotate_edit_right)
    ImageView rotate_edit_right;
    @BindView(R.id.rotate_edit_horizontal)
    ImageView rotate_edit_horizontal;
    @BindView(R.id.rotate_edit_vertical)
    ImageView rotate_edit_vertical;
    @BindView(R.id.rotateRl)
    LinearLayout rotateRl;
    @BindView(R.id.brightnessSeek)
    SeekBar brightnessSeek;
    @BindView(R.id.brightnessLeftTxt)
    TextView brightnessLeftTxt;
    @BindView(R.id.brightnessViewLL)
    LinearLayout brightnessViewLL;
    @BindView(R.id.contrastSeek)
    SeekBar contrastSeek;
    @BindView(R.id.contrastLeftTxt)
    TextView contrastLeftTxt;
    @BindView(R.id.contrastViewLL)
    LinearLayout contrastViewLL;
    @BindView(R.id.bottomedit)
    LinearLayout bottomedit;
    @BindView(R.id.selectadjust)
    LinearLayout selectadjust;
    @BindView(R.id.filter0)
    ImageView filter0;
    @BindView(R.id.filter1)
    ImageView filter1;
    @BindView(R.id.filter2)
    ImageView filter2;
    @BindView(R.id.filter4)
    ImageView filter4;
    @BindView(R.id.filter5)
    ImageView filter5;
    @BindView(R.id.filter7)
    ImageView filter7;
    @BindView(R.id.filter8)
    ImageView filter8;
    @BindView(R.id.filter9)
    ImageView filter9;
    @BindView(R.id.filter10)
    ImageView filter10;
    @BindView(R.id.filter15)
    ImageView filter15;
    @BindView(R.id.filter17)
    ImageView filter17;
    @BindView(R.id.filterViewRL)
    RelativeLayout filterViewRL;
    @BindView(R.id.stickerLayout)
    LinearLayout stickerLayout;
    @BindView(R.id.rotateImg)
    ImageView rotateImg;
    @BindView(R.id.rotateTxt)
    TextView rotateTxt;
    @BindView(R.id.rotateLL)
    LinearLayout rotateLL;
    @BindView(R.id.adjustmentImg)
    ImageView adjustmentImg;
    @BindView(R.id.adjustmentTxt)
    TextView adjustmentTxt;
    @BindView(R.id.adjustmentLL)
    LinearLayout adjustmentLL;
    @BindView(R.id.brightnessLL)
    TextView brightnessLL;

    @BindView(R.id.contrastLL)
    TextView contrastLL;
    @BindView(R.id.cropImg)
    ImageView cropImg;
    @BindView(R.id.cropTxt)
    TextView cropTxt;
    @BindView(R.id.cropLL)
    LinearLayout cropLL;
    @BindView(R.id.stickerImg)
    ImageView stickerImg;
    @BindView(R.id.stickerTxt)
    TextView stickerTxt;
    @BindView(R.id.stickerLL)
    LinearLayout stickerLL;
    @BindView(R.id.textImg)
    ImageView textImg;
    @BindView(R.id.textTxt)
    TextView textTxt;
    @BindView(R.id.textLL)
    LinearLayout textLL;
    @BindView(R.id.filterImg)
    ImageView filterImg;
    @BindView(R.id.filterTxt)
    TextView filterTxt;
    @BindView(R.id.filterLL)
    LinearLayout filterLL;



    private String[] listItem;
    private StickersAdapterAnimation galleryAdapter;

    public int bitmap_hight = 720;
    public int bitmap_width = 720;
    public Bitmap blur_Bitmap;
    public int brightness = 0;
    public int contrast = 0;

    public static StickerViewPhoto stickerView;
    ArrayList<RatioModelPhoto> cropArrayList;
ActivityImageEditPhotoBinding binding;
    String image_path;
    LayoutInflater inflater;
    public Bitmap main_bitmap;
    public Bitmap original_Bitmap;
    int[] rotate = {90, 180, 270, 360};
    int rotateIndex = -1;
    Bitmap rotate_bitmap;
    int rrotateIndex = -1;
    public Bitmap temp_bitmap;

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
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding=ActivityImageEditPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        image_path = getIntent().getStringExtra("image_path");

        cropAdd();
        findviewbyid();
        filter();


        original_Bitmap = Service2Photo.checkBitmap(image_path);
        temp_bitmap = original_Bitmap;
        main_bitmap = original_Bitmap;
        blur_Bitmap = main_bitmap;
        main_image.setImageBitmap(blur_Bitmap);

        cropLL.setOnClickListener(view -> {
            main_color_change();
            cropImg.setColorFilter(getResources().getColor(R.color.transparent));
            cropTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
            selectadjust.setVisibility(View.GONE);
            new InterAds().showInterAds(LlpImageEditActivity.this, new InterAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            startActivityForResult(new Intent(LlpImageEditActivity.this, LlpCropMultipleActivity.class).putExtra("image_path", image_path), 444);

                        }
                    });
        });
        rotateLL.setOnClickListener(view -> {
            main_color_change();
            rotateImg.setColorFilter(getResources().getColor(R.color.transparent));
            rotateTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
            rotate_bitmap = main_bitmap;
            rotateRl.setVisibility(View.VISIBLE);
            selectadjust.setVisibility(View.GONE);
            bottomedit.setVisibility(View.GONE);

        });
        brightnessLL.setOnClickListener(view -> {
            main_color_change();
            brightnessLL.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shape_select) );
            contrastLL.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shape_unselect) );
            adjustmentImg.setColorFilter(getResources().getColor(R.color.transparent));
            adjustmentTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
            brightnessViewLL.setVisibility(View.VISIBLE);
            selectadjust.setVisibility(View.VISIBLE);

        });

        adjustmentLL.setOnClickListener(view -> {
            main_color_change();
            adjustmentImg.setColorFilter(getResources().getColor(R.color.transparent));
            adjustmentTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
            brightnessViewLL.setVisibility(View.VISIBLE);
            selectadjust.setVisibility(View.VISIBLE);
            bottomedit.setVisibility(View.VISIBLE);

        });

        stickerLL.setOnClickListener(view -> {
            main_color_change();
            stickerImg.setColorFilter(getResources().getColor(R.color.transparent));
            stickerTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
            recycle_strickers.setVisibility(View.VISIBLE);
            selectadjust.setVisibility(View.GONE);
            bottomedit.setVisibility(View.VISIBLE);

        });

        textLL.setOnClickListener(view -> {
            main_color_change();
            textImg.setColorFilter(getResources().getColor(R.color.transparent));
            textTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
            selectadjust.setVisibility(View.GONE);
            bottomedit.setVisibility(View.GONE);

            new InterAds().showInterAds(LlpImageEditActivity.this, new InterAds.OnAdClosedListener() {
                @Override
                public void onAdClosed() {
                Intent i = new Intent(LlpImageEditActivity.this, LlpTextEditActivity.class);
                i.putExtra("number", 3);
                startActivity(i);
                }
            });
        });
        contrastLL.setOnClickListener(view -> {
            main_color_change();
            brightnessLL.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shape_unselect) );
            contrastLL.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shape_select) );
            adjustmentImg.setColorFilter(getResources().getColor(R.color.transparent));
            adjustmentTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
            contrastViewLL.setVisibility(View.VISIBLE);
            selectadjust.setVisibility(View.VISIBLE);

        });
        filterLL.setOnClickListener(view -> {
            main_color_change();
            filterImg.setColorFilter(getResources().getColor(R.color.transparent));
            filterTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
            filterViewRL.setVisibility(View.VISIBLE);
            selectadjust.setVisibility(View.GONE);
            bottomedit.setVisibility(View.VISIBLE);

        });
        rotate_edit_left.setOnClickListener(view -> {
            rotate_color_change();
            rotate_edit_left.setColorFilter(getResources().getColor(R.color.colorPrimary));
            if (rrotateIndex == 3) {
                rrotateIndex = 0;
            } else {
                rrotateIndex++;
            }
            setRotation(rotate[rrotateIndex]);
        });
        rotate_edit_right.setOnClickListener(view -> {
            rotate_color_change();
            rotate_edit_right.setColorFilter(getResources().getColor(R.color.colorPrimary));
            if (rotateIndex == 3) {
                rotateIndex = 0;
            } else {
                rotateIndex++;
            }
            setRotation(rotate[rotateIndex]);
        });
        rotate_edit_horizontal.setOnClickListener(view -> {
            rotate_color_change();
            rotate_edit_horizontal.setColorFilter(getResources().getColor(R.color.colorPrimary));
            Matrix matrix = new Matrix();
            matrix.preScale(-1.0f, 1.0f);
            main_bitmap = Bitmap.createBitmap(main_bitmap, 0, 0, main_bitmap.getWidth(), main_bitmap.getHeight(), matrix, true);
            main_image.setImageBitmap(main_bitmap);
        });
        rotate_edit_vertical.setOnClickListener(view -> {
            rotate_color_change();
            rotate_edit_vertical.setColorFilter(getResources().getColor(R.color.colorPrimary));
            Matrix matrix = new Matrix();
            matrix.preScale(1.0f, -1.0f);
            LlpImageEditActivity imageEditActivity = LlpImageEditActivity.this;
            imageEditActivity.main_bitmap = Bitmap.createBitmap(imageEditActivity.main_bitmap, 0, 0, main_bitmap.getWidth(), main_bitmap.getHeight(), matrix, true);
            main_image.setImageBitmap(main_bitmap);
        });
        edit_rotate_close.setOnClickListener(view -> {
            rotate_color_change();
            rotateRl.setVisibility(View.GONE);
            main_image.setImageBitmap(main_bitmap);
        });
        edit_rotate_done.setOnClickListener(view -> {
            rotate_color_change();
            rotateRl.setVisibility(View.GONE);
            main_bitmap = rotate_bitmap;
            main_image.setImageBitmap(main_bitmap);
        });
      ivBack.setOnClickListener(view -> onBackPressed());
    }

    private class CropAdapter extends RecyclerView.Adapter<CropAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgRatio;
            TextView textRatio;

            public ViewHolder(View view) {
                super(view);
                imgRatio = view.findViewById(R.id.imgRatio);
                textRatio = view.findViewById(R.id.textRatio);
            }
        }

        public int getItemCount() {
            return cropArrayList.size();
        }

        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.imgRatio.setImageResource(cropArrayList.get(i).getIcon());
            viewHolder.textRatio.setText(cropArrayList.get(i).getText());
            if (cropArrayList.get(i).isSelect()) {
                viewHolder.imgRatio.setBackground(getResources().getDrawable(R.drawable.edit_ratio_bg_hover));
            } else {
                viewHolder.imgRatio.setBackground(getResources().getDrawable(R.drawable.edit_ratio_bg_photo));
            }
            viewHolder.imgRatio.setOnClickListener(view -> {
                for (int i1 = 0; i1 < cropArrayList.size(); i1++) {
                    cropArrayList.get(i1).setSelect(false);
                }
                cropArrayList.get(i).setSelect(true);
                notifyDataSetChanged();
                viewHolder.imgRatio.setBackground(getResources().getDrawable(R.drawable.edit_ratio_bg_hover));
                bitmap_width = cropArrayList.get(i).getWidth();
                bitmap_hight = cropArrayList.get(i).getHeight();
                temp_bitmap = Service2Photo.scaleCenterCrop(original_Bitmap, bitmap_width, bitmap_hight);
                main_bitmap = Service2Photo.ConvetrSameSize(original_Bitmap, temp_bitmap, bitmap_width, bitmap_hight, 1.0f, 0.0f);
                blur_Bitmap = main_bitmap;
                main_image.setImageBitmap(main_bitmap);
                LayoutParams layoutParams = new LayoutParams(bitmap_width, bitmap_width);
                layoutParams.addRule(13);
//                    bitmapSize.setLayoutParams(layoutParams);
            });
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(inflater.inflate(R.layout.custom_item_ratio_photo, viewGroup, false));
        }
    }

    public static Bitmap doBrightness2(Bitmap bitmap, int i) {

        float f = ((float) i) / 128.0f;
        float f2 = f + 1.0f;
        float f3 = ((-0.5f * f2) + 0.5f) * 255.0f;

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(f);

        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);

        return createBitmap;
    }

    public static Bitmap changeBitmapContrastBrightness(Bitmap bitmap, int i) {

        float f = ((float) i) / 128.0f;
        float[] fArr = new float[0];
        float f2 = f + 1.0f;
        float f3 = ((-0.5f * f2) + 0.5f) * 255.0f;

        ColorMatrix colorMatrix = new ColorMatrix(new float[]{f2, 0.0f, 0.0f, 0.0f, f3, 0.0f, f2, 0.0f, 0.0f, f3, 0.0f, 0.0f, f2, 0.0f, f3, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public void setRotation(int i) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) i);
        Bitmap bitmap = main_bitmap;
        rotate_bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), main_bitmap.getHeight(), matrix, true);
        main_image.setImageBitmap(rotate_bitmap);
    }

    private void filter() {
        filter0.setOnClickListener(view -> {
            main_bitmap = blur_Bitmap;
            main_image.setImageBitmap(main_bitmap);
        });
        filter1.setOnClickListener(view -> {
            GPUImage gPUImage = new GPUImage(LlpImageEditActivity.this);
            gPUImage.setImage(blur_Bitmap);
            gPUImage.setFilter(new GPUImageGrayscaleFilter());
            main_bitmap = gPUImage.getBitmapWithFilterApplied();
            main_image.setImageBitmap(main_bitmap);
        });
        filter2.setOnClickListener(view -> {
            GPUImage gPUImage = new GPUImage(LlpImageEditActivity.this);
            gPUImage.setImage(blur_Bitmap);
            gPUImage.setFilter(new GPUImageSolarizeFilter());
            main_bitmap = gPUImage.getBitmapWithFilterApplied();
            main_image.setImageBitmap(main_bitmap);
        });
        filter4.setOnClickListener(view -> {
            GPUImage gPUImage = new GPUImage(LlpImageEditActivity.this);
            gPUImage.setImage(blur_Bitmap);
            gPUImage.setFilter(new GPUImageToneCurveFilter());
            main_bitmap = gPUImage.getBitmapWithFilterApplied();
            main_image.setImageBitmap(main_bitmap);
        });
        filter5.setOnClickListener(view -> {
            GPUImage gPUImage = new GPUImage(LlpImageEditActivity.this);
            gPUImage.setImage(blur_Bitmap);
            gPUImage.setFilter(new GPUImageHueFilter());
            main_bitmap = gPUImage.getBitmapWithFilterApplied();
            main_image.setImageBitmap(main_bitmap);
        });
        filter7.setOnClickListener(view -> {
            GPUImage gPUImage = new GPUImage(LlpImageEditActivity.this);
            gPUImage.setImage(blur_Bitmap);
            gPUImage.setFilter(new GPUImageCrosshatchFilter());
            main_bitmap = gPUImage.getBitmapWithFilterApplied();
            main_image.setImageBitmap(main_bitmap);
        });
        filter8.setOnClickListener(view -> {
            GPUImage gPUImage = new GPUImage(LlpImageEditActivity.this);
            gPUImage.setImage(blur_Bitmap);
            gPUImage.setFilter(new GPUImageColorInvertFilter());
            main_bitmap = gPUImage.getBitmapWithFilterApplied();
            main_image.setImageBitmap(main_bitmap);
        });
        filter9.setOnClickListener(view -> {
            GPUImage gPUImage = new GPUImage(LlpImageEditActivity.this);
            gPUImage.setImage(blur_Bitmap);
            gPUImage.setFilter(new GPUImageLuminanceThresholdFilter());
            main_bitmap = gPUImage.getBitmapWithFilterApplied();
            main_image.setImageBitmap(main_bitmap);
        });
        filter10.setOnClickListener(view -> {
            GPUImage gPUImage = new GPUImage(LlpImageEditActivity.this);
            gPUImage.setImage(blur_Bitmap);
            gPUImage.setFilter(new GPUImageSepiaToneFilter());
            main_bitmap = gPUImage.getBitmapWithFilterApplied();
            main_image.setImageBitmap(main_bitmap);
        });
        filter15.setOnClickListener(view -> {
            GPUImage gPUImage = new GPUImage(LlpImageEditActivity.this);
            gPUImage.setImage(blur_Bitmap);
            gPUImage.setFilter(new GPUImageGaussianBlurFilter());
            main_bitmap = gPUImage.getBitmapWithFilterApplied();
            main_image.setImageBitmap(main_bitmap);
        });
        filter17.setOnClickListener(view -> {
            GPUImage gPUImage = new GPUImage(LlpImageEditActivity.this);
            gPUImage.setImage(blur_Bitmap);
            gPUImage.setFilter(new GPUImageSketchFilter());
            main_bitmap = gPUImage.getBitmapWithFilterApplied();
            main_image.setImageBitmap(main_bitmap);
        });
    }

    private void cropAdd() {
        cropArrayList = new ArrayList<>();
        ArrayList<RatioModelPhoto> arrayList = cropArrayList;
        RatioModelPhoto ratio = new RatioModelPhoto(R.drawable.edit_hover_cover_photo, 820, 360, "cover", false);
        arrayList.add(ratio);
        ArrayList<RatioModelPhoto> arrayList2 = cropArrayList;
        RatioModelPhoto ratio2 = new RatioModelPhoto(R.drawable.edit_hover_cover_ti_photo, ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED, 500, "cover", false);
        arrayList2.add(ratio2);
        ArrayList<RatioModelPhoto> arrayList3 = cropArrayList;
        RatioModelPhoto ratio3 = new RatioModelPhoto(R.drawable.edit_hover_r_1_1_photo, 400, 400, "1:1", true);
        arrayList3.add(ratio3);
        ArrayList<RatioModelPhoto> arrayList4 = cropArrayList;
        RatioModelPhoto ratio4 = new RatioModelPhoto(R.drawable.edit_hover_r_1_1_19, 566, 1080, "1:19", false);
        arrayList4.add(ratio4);
        ArrayList<RatioModelPhoto> arrayList5 = cropArrayList;
        RatioModelPhoto ratio5 = new RatioModelPhoto(R.drawable.edit_hover_r_2_3_photo, 600, 900, "2:3", false);
        arrayList5.add(ratio5);
        ArrayList<RatioModelPhoto> arrayList6 = cropArrayList;
        RatioModelPhoto ratio6 = new RatioModelPhoto(R.drawable.edit_hover_r_3_2_photo, 900, 600, "3:2", false);
        arrayList6.add(ratio6);
        ArrayList<RatioModelPhoto> arrayList7 = cropArrayList;
        RatioModelPhoto ratio7 = new RatioModelPhoto(R.drawable.edit_hover_r_3_4_photo, 900, 1200, "3:4", false);
        arrayList7.add(ratio7);
        ArrayList<RatioModelPhoto> arrayList8 = cropArrayList;
        RatioModelPhoto ratio8 = new RatioModelPhoto(R.drawable.edit_hover_r_4_3_photo, 1200, 675, "4:3", false);
        arrayList8.add(ratio8);
        ArrayList<RatioModelPhoto> arrayList9 = cropArrayList;
        RatioModelPhoto ratio9 = new RatioModelPhoto(R.drawable.edit_edit_hover_r_4_5_photo, 1080, 1350, "4:5", false);
        arrayList9.add(ratio9);
        ArrayList<RatioModelPhoto> arrayList10 = cropArrayList;
        RatioModelPhoto ratio10 = new RatioModelPhoto(R.drawable.edit_hover_r_5_3_photo, 1350, 900, "5:3", false);
        arrayList10.add(ratio10);
        ArrayList<RatioModelPhoto> arrayList11 = cropArrayList;
        RatioModelPhoto ratio11 = new RatioModelPhoto(R.drawable.edit_hover_r_5_4_photo, 1350, 1200, "5:4", false);
        arrayList11.add(ratio11);
        ArrayList<RatioModelPhoto> arrayList12 = cropArrayList;
        RatioModelPhoto ratio12 = new RatioModelPhoto(R.drawable.edit_hover_r_5_7_photo, 1350, 2100, "5:7", false);
        arrayList12.add(ratio12);
        ArrayList<RatioModelPhoto> arrayList13 = cropArrayList;
        RatioModelPhoto ratio13 = new RatioModelPhoto(R.drawable.edit_hover_r_7_5_photo, 2100, 1350, "7:5", false);
        arrayList13.add(ratio13);
        ArrayList<RatioModelPhoto> arrayList14 = cropArrayList;
        RatioModelPhoto ratio14 = new RatioModelPhoto(R.drawable.edit_hover_r_9_16_photo, 1080, 1920, "9:16", false);
        arrayList14.add(ratio14);
        ArrayList<RatioModelPhoto> arrayList15 = cropArrayList;
        RatioModelPhoto ratio15 = new RatioModelPhoto(R.drawable.edit_hover_r_16_9_photo, 1920, 1080, "16:19", false);
        arrayList15.add(ratio15);
    }

    private void findviewbyid() {
        stickerView = findViewById(R.id.sticker_view);

        cropRecylerview.setHasFixedSize(true);
        cropRecylerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        cropRecylerview.setAdapter(new CropAdapter());

        brightnessSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                brightness = seekBar.getProgress();
                main_bitmap = doBrightness2(blur_Bitmap, (int) (brightness * 2.5));
                main_image.setImageBitmap(main_bitmap);
//                main_image.setColorFilter(setBrightness((brightness * 2)));

                TextView textView = brightnessLeftTxt;
                StringBuilder sb = new StringBuilder();
                sb.append(brightness);
                sb.append("%");
                textView.setText(sb.toString());
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        contrastSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                contrast = seekBar.getProgress();
                main_bitmap = LlpImageEditActivity.changeBitmapContrastBrightness(blur_Bitmap, contrast);
//                main_bitmap = ImageEditActivityPhoto.adjustedContrast(blur_Bitmap, contrast);
                main_image.setImageBitmap(main_bitmap);
                TextView textView = contrastLeftTxt;
                StringBuilder sb = new StringBuilder();
                sb.append(contrast);
                sb.append("%");
                textView.setText(sb.toString());
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        layNxt.setOnClickListener(view -> {
            stickerView.setLocked(true);
            saveImageFile(createBitmapFromView(bitmapSize));
        });

        stikerset();
        loadSticker();
    }

    private void loadSticker() {
        try {
            listItem = getAssets().list("strickers11");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (listItem != null) {
            List arrayList = new ArrayList();
            for (String str : listItem) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("strickers11/");
                stringBuilder.append(str);
                arrayList.add(stringBuilder.toString());
            }
            listItem = (String[]) arrayList.toArray(new String[arrayList.size()]);
            galleryAdapter = new StickersAdapterAnimation(listItem, this);
            recycle_strickers.setHasFixedSize(true);
            recycle_strickers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            recycle_strickers.setAdapter(galleryAdapter);

            recycle_strickers.addOnItemTouchListener(
                    new RecyclerItemClickListenerPhoto(getApplicationContext(), (view, position) -> {
                        Bitmap bitmap = getImageFromAssetsFile(LlpImageEditActivity.this, listItem[position]);
                        Drawable d = new BitmapDrawable(getResources(), bitmap);
                        stickerView.addSticker(new DrawableStickerPhoto(d), StickerPhoto.Position.CENTER | StickerPhoto.Position.CENTER);
                    })
            );
        }
    }

    private void stikerset() {

        final BitmapStickerIconPhoto deleteIcon = new BitmapStickerIconPhoto(ContextCompat.getDrawable(this,
                R.drawable.sticker_ic_close_photo),
                BitmapStickerIconPhoto.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEventPhoto());

        BitmapStickerIconPhoto zoomIcon = new BitmapStickerIconPhoto(ContextCompat.getDrawable(this,
                R.drawable.sticker_ic_scale_photo),
                BitmapStickerIconPhoto.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEventPhoto());

        BitmapStickerIconPhoto flipIcon = new BitmapStickerIconPhoto(ContextCompat.getDrawable(this,
                R.drawable.sticker_ic_flip_photo),
                BitmapStickerIconPhoto.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEventPhoto());
        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon/*, heartIcon*/));

        stickerView.setBackgroundColor(Color.TRANSPARENT);
        stickerView.setLocked(false);
        stickerView.setConstrained(true);


        stickerView.setOnStickerOperationListener(new StickerViewPhoto.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull StickerPhoto sticker) {

                FunctionsPhoto.LogD("stiker", "onStickerAdded");
            }

            @Override
            public void onStickerClicked(@NonNull StickerPhoto sticker) {
                FunctionsPhoto.LogD("stiker", "onStickerClicked");
            }

            @Override
            public void onStickerDeleted(@NonNull StickerPhoto sticker) {
                FunctionsPhoto.LogD("stiker", "onStickerDeleted");
            }

            @Override
            public void onStickerDragFinished(@NonNull StickerPhoto sticker) {
                FunctionsPhoto.LogD("stiker", "onStickerDragFinished");
            }

            @Override
            public void onStickerTouchedDown(@NonNull StickerPhoto sticker) {
                FunctionsPhoto.LogD("stiker", "onStickerTouchedDown");
            }

            @Override
            public void onStickerZoomFinished(@NonNull StickerPhoto sticker) {
                FunctionsPhoto.LogD("stiker", "onStickerZoomFinished");
            }

            @Override
            public void onStickerFlipped(@NonNull StickerPhoto sticker) {
                FunctionsPhoto.LogD("stiker", "onStickerFlipped");
            }

            @Override
            public void onStickerDoubleTapped(@NonNull StickerPhoto sticker) {
                FunctionsPhoto.LogD("stiker", "onDoubleTapped: double tap will be with two click");

            }
        });

    }

    public static Bitmap getImageFromAssetsFile(Context mContext, String fileName) {
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


    public void saveImageFile(Bitmap bitmap) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(image_path));
            bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
            finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Bitmap createBitmapFromView(View view) {
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        Bitmap createBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Config.ARGB_8888);

        view.draw(new Canvas(createBitmap));
        int i = view.getWidth();
        int i2 = view.getHeight();

        float f = (float) i;
        float width = (float) createBitmap.getWidth();
        float f2 = (float) i2;
        float height = (float) createBitmap.getHeight();
        float max = Math.max(f / width, f2 / height);
        width *= max;
        max *= height;
        f = (f - width) / 2.0f;
        f2 = (f2 - max) / 2.0f;
        RectF rectF = new RectF(f, f2, width + f, max + f2);
        Bitmap resizedBitmap = Bitmap.createBitmap(i, i2, createBitmap.getConfig());
        new Canvas(resizedBitmap).drawBitmap(createBitmap, null, rectF, null);
        return resizedBitmap;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 444) {
            image_path = getIntent().getStringExtra("image_path");
            BitmapFactory.Options options = new BitmapFactory.Options();
            // options.inSampleSize = 1;
            original_Bitmap = BitmapFactory.decodeFile(image_path,options);
            temp_bitmap = Service2Photo.scaleCenterCrop(original_Bitmap, bitmap_width, bitmap_hight);
            main_bitmap = Service2Photo.ConvetrSameSize(original_Bitmap, temp_bitmap, bitmap_width, bitmap_hight, 1.0f, 0.0f);
            Bitmap bitmap = main_bitmap;
            blur_Bitmap = bitmap;
            main_image.setImageBitmap(bitmap);
        }
    }

    public void main_color_change() {
        cropImg.setColorFilter(getResources().getColor(R.color.colorBlack));
        rotateImg.setColorFilter(getResources().getColor(R.color.colorBlack));
        adjustmentImg.setColorFilter(getResources().getColor(R.color.colorBlack));
        stickerImg.setColorFilter(getResources().getColor(R.color.colorBlack));
        textImg.setColorFilter(getResources().getColor(R.color.colorBlack));
        filterImg.setColorFilter(getResources().getColor(R.color.colorBlack));
        cropTxt.setTextColor(getResources().getColor(R.color.colorBlack));
        textTxt.setTextColor(getResources().getColor(R.color.colorBlack));
        stickerTxt.setTextColor(getResources().getColor(R.color.colorBlack));
        rotateTxt.setTextColor(getResources().getColor(R.color.colorBlack));
        adjustmentTxt.setTextColor(getResources().getColor(R.color.colorBlack));
        filterTxt.setTextColor(getResources().getColor(R.color.colorBlack));
        cropRecylerviewRL.setVisibility(View.GONE);
        rotateRl.setVisibility(View.GONE);
        brightnessViewLL.setVisibility(View.GONE);
        recycle_strickers.setVisibility(View.GONE);
        contrastViewLL.setVisibility(View.GONE);
        stickerLayout.setVisibility(View.GONE);
        filterViewRL.setVisibility(View.GONE);
        rotate_color_change();
    }

    public void rotate_color_change() {
        rotate_edit_left.setColorFilter(getResources().getColor(R.color.colorBlack));
        rotate_edit_right.setColorFilter(getResources().getColor(R.color.colorBlack));
        rotate_edit_horizontal.setColorFilter(getResources().getColor(R.color.colorBlack));
        rotate_edit_vertical.setColorFilter(getResources().getColor(R.color.colorBlack));
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
