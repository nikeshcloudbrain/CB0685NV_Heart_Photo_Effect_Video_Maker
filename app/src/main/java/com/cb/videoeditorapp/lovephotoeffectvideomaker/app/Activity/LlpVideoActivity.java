package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.EffectAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.FrameAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.StickersAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.BitmapStickerIconPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.DeleteIconEventPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.DrawableStickerPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.FlipHorizontallyEventPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.StickerPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.StickerViewPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.ZoomIconEventPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ApplicationPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.CompressImage;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.RecyclerItemClickListenerPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.UtilsFoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.LargeNativeAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityVideoPhotoBinding;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.TransitionModelPhoto;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.preference.PowerPreference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import Jni.FFmpegCmd;
import VideoHandle.OnEditorListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import kr.pe.burt.android.lib.faimageview.FAImageView;

public class LlpVideoActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.img_editor_Back)
    ImageView img_editor_Back;
    @BindView(R.id.crd_export)
    RelativeLayout crd_export;
    @BindView(R.id.previewExoplayer)
    SimpleExoPlayerView previewExoplayer;
    @BindView(R.id.mIvFrame)
    ImageView mIvFrame;
    @BindView(R.id.flMain)
    FrameLayout flMain;
    @BindView(R.id.img_preview)
    FAImageView img_preview;
    @BindView(R.id.img_PlayPause)
    ImageView img_PlayPause;
    @BindView(R.id.flVideoComponents)
    RelativeLayout flVideoComponents;
    @BindView(R.id.imgMoreEffect)
    ImageView imgMoreEffect;
    @BindView(R.id.recycle_style)
    RecyclerView recycle_style;
    @BindView(R.id.lin_styletheme_item)
    LinearLayout lin_styletheme_item;
    @BindView(R.id.recycle_Frame)
    RecyclerView recycle_Frame;
    @BindView(R.id.lin_frame_item)
    LinearLayout lin_frame_item;
    @BindView(R.id.recycle_strickers)
    RecyclerView recycle_strickers;
    @BindView(R.id.lin_edit_item)
    LinearLayout lin_edit_item;
    @BindView(R.id.img_Style)
    ImageView img_Style;
    @BindView(R.id.txt_Style)
    TextView txt_Style;
    @BindView(R.id.lin_img_Style_click)
    LinearLayout lin_img_Style_click;
    @BindView(R.id.img_addText)
    ImageView img_addText;
    @BindView(R.id.txt_addText)
    TextView txt_addText;
    @BindView(R.id.lin_addText_clik)
    LinearLayout lin_addText_clik;
    @BindView(R.id.imd_addStrickers)
    ImageView imd_addStrickers;
    @BindView(R.id.txt_addStrickers)
    TextView txt_addStrickers;
    @BindView(R.id.lin_addStickers_clik)
    LinearLayout lin_addStickers_clik;
    @BindView(R.id.img_Framerecycle)
    ImageView img_Framerecycle;
    @BindView(R.id.txt_Framerecycle)
    TextView txt_Framerecycle;
    @BindView(R.id.lin_img_Framerecycle_click)
    LinearLayout lin_img_Framerecycle_click;
    @BindView(R.id.editlayout)
    LinearLayout editlayout;
    ArrayList<File> files = new ArrayList<>();
    File[] listFile;
    private String[] listItem;
    private String[] listFrame;

    private ArrayList<TransitionModelPhoto> FrameSelectModels;
    private EffectAdapterAnimation themeAdapter;
    private FrameAdapterAnimation frameAdapter;
    private StickersAdapterAnimation galleryAdapter;
    ActivityVideoPhotoBinding binding;
    private Dialog dialog;
    private TextView txtProgress;
    private boolean isPlayPause = true;

    private String imgPath = null;
    public static StickerViewPhoto stickerView;
    long durationlength;
    private SimpleExoPlayer ecoPlayer;
    DataSpec dataSpec;
    String selEffect = "";
    private String fileName, effectpath, outputTempPath;
    private int videoQuality = 720;

    int pos = 1;
    String path = "";

    @Override
    protected void onResume() {
        super.onResume();
        if (ecoPlayer == null) {
            getVideoPlayer(outputTempPath);
            img_PlayPause.setVisibility(View.GONE);
            isPlayPause = true;
        }
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
        binding = ActivityVideoPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String outputVideoPath = Constant.getOutputPath(this) + File.separator + getString(R.string.mainFolder);
        File file = new File(outputVideoPath, getString(R.string.temp_folder));
        if (file.exists()) {
            deleteDir(file);
        }

        getID();
        selEffect = Constant.themeListhum[1];
        SaveThumJpg();
        loadSticker();
        loadTheme();

        String videoPath = getIntent().getStringExtra("videoPath");
        blurVideo(videoPath);

    }


    public void loadTheme() {
        try {
//            dilogShow();
            UtilsFoto.dilogShow(LlpVideoActivity.this);
            isPlayPause = true;
            if (ecoPlayer != null) {

                ecoPlayer.setPlayWhenReady(false);
                img_preview.reset();
            }
            frameChange(mIvFrame);

            String mainFolder = getString(R.string.mainFolder);
            String effectFolder = getString(R.string.theme_folder);

            File mainFile = new File(getFilesDir().getAbsolutePath() + "/" + mainFolder);
            if (!mainFile.exists())
                mainFile.mkdirs();
            final File effectFile = new File(mainFile, effectFolder);
            if (!effectFile.exists())
                effectFile.mkdirs();

            if (effectFile.exists()) {

                listFile = effectFile.listFiles();
                if (listFile.length > 0) {

                    fileName = selEffect;
                    effectpath = effectFile.getAbsolutePath() + "/" + fileName + "/" + fileName;

                    File file = new File(effectpath);
                    final File[] listFile2;
                    listFile2 = file.listFiles();
                    Collections.sort(Arrays.asList(listFile2));
                    if (listFile2.length > 0) {

                        img_preview.setInterval(150);
                        img_preview.setLoop(true);
                        for (int i = 0; i < listFile2.length; i++) {

                            try {
                                Bitmap bitmap = CompressImage.compressImage(listFile2[i].getAbsolutePath());
                                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
                                img_preview.addImageFrame(bitmap);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                          /*  try {
                                Bitmap bitmap = Service2Photo.checkBitmap(listFile2[i].getAbsolutePath());
//                                bitmap = Bitmap.createScaledBitmap(bitmap, videoQuality, videoQuality, false);
                                img_preview.addImageFrame(bitmap);
                                FunctionsPhoto.LogW("ImageTheme", "log 5" + listFile2[i].getAbsolutePath());
                            } catch (Exception e) {
                            }*/
                        }
                    }
                    FunctionsPhoto.LogW("log", "log 2");
                } else {

                    FunctionsPhoto.LogW("log", "log 4");
                }
            } else {

                FunctionsPhoto.LogW("log", "log 5");
            }

//            dilogHide();
            UtilsFoto.dilogHide(LlpVideoActivity.this);

            if (ecoPlayer != null)
                ecoPlayer.setPlayWhenReady(true);
            img_preview.startAnimation();
            img_PlayPause.setVisibility(View.GONE);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getID() {

        stickerView = (StickerViewPhoto) findViewById(R.id.sticker_view);

        img_editor_Back.setOnClickListener(this);
        crd_export.setOnClickListener(this);

        lin_img_Style_click.setOnClickListener(this);
        lin_img_Framerecycle_click.setOnClickListener(this);
        lin_addText_clik.setOnClickListener(this);
        lin_addStickers_clik.setOnClickListener(this);
        img_PlayPause.setOnClickListener(this);
        flVideoComponents.setOnClickListener(this);
        imgMoreEffect.setOnClickListener(this);
        setLayoutParams();
        loadFrame();
        setUpTransitionAdapter();
        stikerset();
    }

    public void frameChange(ImageView img) {

        if (img.getDrawable() == null) {
            FunctionsPhoto.LogW("frmimg", "=====null====");
        } else {
            FunctionsPhoto.LogW("frmimg", "===notnull===");
            Drawable d = img.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();

            bitmap = Bitmap.createScaledBitmap(bitmap, videoQuality, videoQuality, false);

            img.setImageBitmap(bitmap);
        }
    }

    private void getVideoPlayer(String videoPath) {

        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            ecoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            dataSpec = new DataSpec(Uri.parse(videoPath));

            final FileDataSource fileDataSource = new FileDataSource();
            try {
                fileDataSource.open(dataSpec);

            } catch (FileDataSource.FileDataSourceException e) {
                e.printStackTrace();
            }

            DataSource.Factory factory = new DataSource.Factory() {
                @Override
                public DataSource createDataSource() {
                    return fileDataSource;
                }
            };
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.crd_export:
                mergeImageVideo();
                if (ecoPlayer != null)
                    ecoPlayer.setPlayWhenReady(false);
                break;

            case R.id.img_editor_Back:
                onBackPressed();
                break;

            case R.id.lin_img_Style_click:
                comman_color();
                img_Style.setColorFilter(getResources().getColor(R.color.transparent));
                txt_Style.setTextColor(getResources().getColor(R.color.colorPrimary));
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorBlack));
                img_addText.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addText.setTextColor(getResources().getColor(R.color.colorBlack));
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorBlack));
                editlayout.setVisibility(View.VISIBLE);

                lin_styletheme_item.setVisibility(View.VISIBLE);

                break;

            case R.id.lin_img_Framerecycle_click:
                comman_color();
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.transparent));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorPrimary));
                img_addText.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addText.setTextColor(getResources().getColor(R.color.colorBlack));
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Style.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Style.setTextColor(getResources().getColor(R.color.colorBlack));
                editlayout.setVisibility(View.VISIBLE);

                lin_frame_item.setVisibility(View.VISIBLE);
                break;


            case R.id.lin_addText_clik:
                comman_color();
                img_addText.setColorFilter(getResources().getColor(R.color.transparent));
                txt_addText.setTextColor(getResources().getColor(R.color.colorPrimary));
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Style.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Style.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorBlack));
                editlayout.setVisibility(View.GONE);
                new InterAds().showInterAds(LlpVideoActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent i = new Intent(LlpVideoActivity.this, LlpTextEditActivity.class);
                        i.putExtra("number", 2);
                        startActivity(i);
                    }
                });

                break;

            case R.id.lin_addStickers_clik:
                comman_color();
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.transparent));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorPrimary));
                img_Style.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Style.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorBlack));
                img_addText.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addText.setTextColor(getResources().getColor(R.color.colorBlack));
                editlayout.setVisibility(View.VISIBLE);

                recycle_strickers.setVisibility(View.VISIBLE);
                lin_edit_item.setVisibility(View.VISIBLE);
                break;

            case R.id.flVideoComponents:
                if (isPlayPause) {

                    img_preview.stopAnimation();
                    img_PlayPause.setVisibility(View.VISIBLE);
                    isPlayPause = false;

                    if (ecoPlayer != null)
                        ecoPlayer.setPlayWhenReady(false);
//                    pauseMusic();
                } else {

                    img_preview.startAnimation();
                    img_PlayPause.setVisibility(View.GONE);
                    FunctionsPhoto.LogW("SavedImage", "PlayPause");
//                    playMusic();
                    if (ecoPlayer != null)
                        ecoPlayer.setPlayWhenReady(true);

                    isPlayPause = true;
                }
                break;

            case R.id.img_PlayPause:
                if (isPlayPause) {

                    img_preview.stopAnimation();
                    img_PlayPause.setVisibility(View.VISIBLE);
                    isPlayPause = false;
                    if (ecoPlayer != null)
                        ecoPlayer.setPlayWhenReady(false);
                } else {
                    img_preview.startAnimation();
                    img_PlayPause.setVisibility(View.GONE);
                    FunctionsPhoto.LogW("SavedImage", "PlayPause");
//                    playMusic();
                    if (ecoPlayer != null)
                        ecoPlayer.setPlayWhenReady(true);
                    isPlayPause = true;
                }
                break;

            case R.id.imgMoreEffect:
                new InterAds().showInterAds(LlpVideoActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        startActivityForResult(new Intent(LlpVideoActivity.this, LlpStoreActivity.class), 1);
                    }
                });
                break;
        }
    }

    public Bitmap createBitmapFromView(View view) {
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        Bitmap createBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        view.draw(new Canvas(createBitmap));
        int i = videoQuality;
        int i2 = videoQuality;

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

//    ---------------------------------------------------------------------------------

    private void setLayoutParams() {
        try {
            ViewTreeObserver vto = previewExoplayer.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {

                    previewExoplayer.getViewTreeObserver().removeOnPreDrawListener(this);

                    int imgFinalWidth = previewExoplayer.getMeasuredWidth();
                    int imgFinalHeight = previewExoplayer.getMeasuredHeight();

                    FunctionsPhoto.LogE("onPreDraw: ", "imgFinalWidth => " + imgFinalWidth);

                    if (imgFinalWidth > 720)
                        videoQuality = 720;
                    else
                        videoQuality = imgFinalWidth;

                    flVideoComponents.getLayoutParams().width = imgFinalWidth;
                    flVideoComponents.getLayoutParams().height = imgFinalWidth;

                    flMain.getLayoutParams().width = imgFinalWidth;
                    flMain.getLayoutParams().height = imgFinalWidth;

                    stickerView.getLayoutParams().height = imgFinalHeight;

//                    img_selected.getLayoutParams().width = imgFinalWidth;
//                    img_selected.getLayoutParams().height = imgFinalWidth;

                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFrame(Bitmap bitmap) {
        if (bitmap == null) {
            this.mIvFrame.setImageBitmap(null);
            return;
        }
        //this.newbmp_framee = bitmap;
        setbitmap_method(bitmap);
    }

    private void setbitmap_method(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, videoQuality, videoQuality, false);


            this.mIvFrame.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case 1:
                    if (data != null && data.hasExtra("effectPath")) {
                        String effectPath = data.getStringExtra("effectPath");
                        Log.e("onActivityResult: ", "effectPath => " + effectPath);

                        if (effectPath != null && effectPath.length() > 0 && effectPath.charAt(effectPath.length() - 1) == '/') {
                            effectPath = effectPath.substring(0, effectPath.length() - 1);
                        }

                        effectpath = effectPath;
                        Log.e("onActivityResult: ", "effectPath => " + effectPath);

                        selEffect = effectPath.substring(effectPath.lastIndexOf("/") + 1);
                        Log.e("onActivityResult: ", "selEffect => " + selEffect);
                        path = effectPath.substring(0, effectPath.lastIndexOf("/"));
                    }
                    SaveThumJpg();


                    break;
            }
        }
    }

    private void comman_color() {
        recycle_strickers.setVisibility(View.GONE);

        img_addText.setColorFilter(getResources().getColor(R.color.colorWhite));
        txt_addText.setTextColor(getResources().getColor(R.color.colorWhite));

        imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorWhite));
        txt_addStrickers.setTextColor(getResources().getColor(R.color.colorWhite));

        img_Style.setColorFilter(getResources().getColor(R.color.colorWhite));
        txt_Style.setTextColor(getResources().getColor(R.color.colorWhite));
        lin_styletheme_item.setVisibility(View.GONE);

        img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorWhite));
        txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorWhite));
        lin_frame_item.setVisibility(View.GONE);
    }

    private void setUpTransitionAdapter() {
        FrameSelectModels = new ArrayList<TransitionModelPhoto>();
        FrameSelectModels.clear();
        for (int i = 0; i < Constant.Frames.length; i++) {
            TransitionModelPhoto effectModel = new TransitionModelPhoto();
            FrameSelectModels.add(effectModel);
        }

        frameAdapter = new FrameAdapterAnimation(listFrame, FrameSelectModels, this);
        recycle_Frame.setHasFixedSize(true);
        recycle_Frame.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recycle_Frame.setAdapter(frameAdapter);
        frameAdapter.setSelected(0);

        recycle_Frame.addOnItemTouchListener(
                new RecyclerItemClickListenerPhoto(getApplicationContext(), new RecyclerItemClickListenerPhoto.OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            Bitmap bitmap = getImageFromAssetsFile(LlpVideoActivity.this, listFrame[position]);
                            frameAdapter.setSelected(position);
                            if (position == 0) {
                                setFrame(null);
                            } else {
                                setFrame(bitmap);
                            }

                            FunctionsPhoto.LogW("pos", String.valueOf(position));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
    }

    public void SaveThumJpg() {

        String mainFolder = getString(R.string.mainFolder);
        String effectFolder = getString(R.string.theme_folder);

        File mainFile = new File(getFilesDir().getAbsolutePath() + "/" + mainFolder);
        if (!mainFile.exists())
            mainFile.mkdirs();
        File effectFile = new File(mainFile, effectFolder);
        if (!effectFile.exists())
            effectFile.mkdirs();

        if (effectFile.exists()) {
            final File[] listFile;
            files = new ArrayList<>();

            listFile = effectFile.listFiles();
            if (listFile != null && listFile.length > 0) {

                for (int i = 0; i < listFile.length; i++) {
                    ArrayList<String> data = new Gson().fromJson(PowerPreference.getDefaultFile().getString("mEffect", new Gson().toJson(new ArrayList<String>())), new TypeToken<ArrayList<String>>() {
                    }.getType());
                    if (data.contains(listFile[i].getAbsolutePath())) {
                        if (path.equalsIgnoreCase("")) {
                            pos = 1;
                        } else if (path.equalsIgnoreCase(listFile[i].getAbsolutePath())) {
                            pos = files.size();
                        }

                        files.add(listFile[i]);
                    }
                }

                themeAdapter = new EffectAdapterAnimation(files, this, pos);
                recycle_style.setLayoutManager(new GridLayoutManager((Context) this, 1, RecyclerView.HORIZONTAL, false));
                recycle_style.setItemAnimator(new DefaultItemAnimator());
                recycle_style.setAdapter(themeAdapter);

                loadTheme();

                FunctionsPhoto.LogW("selection", "log 2");

                themeAdapter.setOnItemClickListener(new EffectAdapterAnimation.setOnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        try {
                            String effectpath11 = files.get(position).getAbsolutePath();
                            String fileName = effectpath11.substring(effectpath11.lastIndexOf('/') + 1);
                            selEffect = fileName;

                            loadTheme();
                            FunctionsPhoto.LogW("selection", String.valueOf(position));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            } else {

                FunctionsPhoto.LogW("log", "log 4");
            }
        } else {

            FunctionsPhoto.LogW("log", "log 5");
        }

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
                    new RecyclerItemClickListenerPhoto(getApplicationContext(), new RecyclerItemClickListenerPhoto.OnItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onItemClick(View view, int position) {

                            Bitmap bitmap = getImageFromAssetsFile(LlpVideoActivity.this, listItem[position]);

                            Drawable d = new BitmapDrawable(getResources(), bitmap);
                            stickerView.addSticker(new DrawableStickerPhoto(d), StickerPhoto.Position.CENTER | StickerPhoto.Position.CENTER);
                        }
                    })
            );
        }
    }

    private void loadFrame() {
        try {
            listFrame = getAssets().list("frames");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (listFrame != null) {
            List arrayList = new ArrayList();
            for (String str : listFrame) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("frames/");
                stringBuilder.append(str);
                arrayList.add(stringBuilder.toString());
            }
            listFrame = (String[]) arrayList.toArray(new String[arrayList.size()]);

        }
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

    private void stikerset() {


//        FunctionsPhoto.LogE("hhh", "-1--" + mIvFrame.getMeasuredHeight());
//        FunctionsPhoto.LogE("hhh", "--1--" + mIvFrame.getMeasuredWidth());
//        FunctionsPhoto.LogE("hhh", "---" + mIvFrame.getLayoutParams().width);
//        FunctionsPhoto.LogE("hhh", "----" + mIvFrame.getLayoutParams().height);
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
//stickerView.removeAllSticker();
/* if (sticker instanceof TextStickerPhoto) {
((TextStickerPhoto) sticker).setTextColor(Color.RED);
stickerView.replace(sticker);
stickerView.invalidate();
}*/
// Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
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
/* if (sticker instanceof TextStickerPhoto) {
// ((TextStickerPhoto) sticker).setTextColor(Color.BLUE);
stickerView.replace(sticker);
stickerView.invalidate();*//*

}*/
                FunctionsPhoto.LogD("stiker", "onDoubleTapped: double tap will be with two click");

            }
        });

    }

    public String saveImage() {

        stickerView.setLocked(true);

        return saveImageFile(createBitmapFromView(flMain));
    }

    public String saveImageFile(Bitmap bitmap) {
        FileOutputStream out = null;
        final String filename = getFilename();
        try {
            out = new FileOutputStream(filename);
//              bitmap = Bitmap.createScaledBitmap(bitmap, AudioActivity.w, AudioActivity.h, false);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

           /*` new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    // This method will be executed once the timer is over
                    Intent i = new Intent(getApplicationContext(), SaveActivityPhoto.class);
                    i.putExtra("share", filename);
                    startActivity(i);
                }
            }, 2000); */

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;
    }

    private String getFilename() {
        File file = new File(Constant.getFolderPath(this), getResources().getString(R.string.mainFolder) + "/" + getResources().getString(R.string.temp_folder));
        if (!file.exists()) {
            file.mkdirs();
        }

        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".png");
        return uriSting;
    }

//    ________________________________________________________________________________

    private void blurVideo(String mVideoPath) {

        //dilogShow();
        UtilsFoto.dilogShow(LlpVideoActivity.this);
        txtProgress = UtilsFoto.dialog.findViewById(R.id.txtvlu);
        File mainFile = new File(Constant.getFolderPath(ApplicationPhoto.getContext()) + "/" + getResources().getString(R.string.mainFolder));
        if (!mainFile.exists())
            mainFile.mkdir();

        File tempVideo = new File(mainFile, getString(R.string.temp_video_folder));
        if (!tempVideo.exists()) {
            tempVideo.mkdirs();
        } else {
            deleteDir(tempVideo);
            tempVideo.mkdirs();
        }

        File tempVideoName = new File(tempVideo, "temp_video.mp4");
        outputTempPath = tempVideoName.getAbsolutePath();

        String[] cmdString = {"-y", "-i", mVideoPath, "-filter_complex", "[0:v]scale=" + videoQuality + ":" + videoQuality + ",setdar=1:1,gblur=sigma=20[bg];[0:v]scale=-1:" + videoQuality + "[ov];[bg][ov]overlay=(W-w)/2:0[mix]", "-map", "[mix]", "-map", "0:a?", "-r", "25", outputTempPath};

        FunctionsPhoto.LogE("cmd", "------>" + cmdString);
       /* durationlength = 1000000000;
        long duration = getDuration(mVideoPath) * durationlength;
        execCmd(cmdString, duration, new OnEditorListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getVideoPlayer(outputTempPath);
//                        dilogHide();
                        UtilsFoto.dilogHide(LapVideoActivity.this);
                        txtProgress.setText("");
                    }
                });
                FunctionsPhoto.LogW("ffmpeg", "onSuccess()   ==> ");
            }

            @Override
            public void onFailure() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (durationlength <= 10000000) {
                            durationlength = durationlength * 100;
                            String videoPath = getIntent().getStringExtra("videoPath");
                            blurVideo(videoPath);
                        }
//                        dilogHide();
                        UtilsFoto.dilogHide(LapVideoActivity.this);

                        FunctionsPhoto.LogW("ffmpeg", "onFailure()   ==> ");
                    }
                });
            }

            @Override
            public void onProgress(final float progress) {
                runOnUiThread(() -> {
                    int percent = (int) (progress * 100);
                    if (0 <= percent && percent <= 100) {
                        txtProgress.setText("Processing " + percent + " %");

                    } else
                        FunctionsPhoto.LogW("ffmpeg", "onProgress() ********************" + percent + "********************");
                });
            }
        });
*/

        long duration = getDuration(mVideoPath) * 1000000;
        execCmd(cmdString, duration, new OnEditorListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    getVideoPlayer(outputTempPath);
                    UtilsFoto.dilogHide(LlpVideoActivity.this);
                    txtProgress.setText("");
                });
                UtilsFoto.LogW("ffmpeg", "onSuccess()   ==> ");
            }

            @Override
            public void onFailure() {
                runOnUiThread(() -> {
                    UtilsFoto.dilogHide(LlpVideoActivity.this);
                    UtilsFoto.LogW("ffmpeg", "onFailure()   ==> ");
                });
            }

            @Override
            public void onProgress(final float progress) {
                runOnUiThread(() -> {
                    int percent = (int) (progress * 100);
                    if (0 <= percent && percent <= 100) {
                        txtProgress.setText("Processing " + percent + " %");
                    } else
                        UtilsFoto.LogW("ffmpeg", "onProgress() ********************" + percent + "********************");
                });
            }
        });


    }


    public void mergeImageVideo() {

//        dilogShow();

        String effectPath = effectpath + File.separator + fileName + "_%03d.png";

        File dCimFile = new File(Constant.getOutputPath(this));

        if (!dCimFile.exists()) {
            dCimFile.mkdir();
        }

        File subFile = new File(dCimFile, getResources().getString(R.string.my_creation));
        if (!subFile.exists())
            subFile.mkdir();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String name = "Video_";
        String fileExtn = ".mp4";

        File nameVideoFile = new File(subFile, name + timeStamp + fileExtn);
        final String filePath = nameVideoFile.getAbsolutePath();

        final String cmdString;
        imgPath = saveImage();

        final long duration = getDuration(outputTempPath);

        cmdString = "-y -i " + outputTempPath + " -r 9 -loop 1 -i " + effectPath + " -i " + imgPath + " -filter_complex [0]scale=" + videoQuality + ":" + videoQuality + ",setsar=1[0_scalled];[1]scale=" + videoQuality + ":" + videoQuality + ",setsar=1[1_scalled];[2]scale=" + videoQuality + ":" + videoQuality + ",setsar=1[2_scalled];[0_scalled][1_scalled]overlay=0:0[a1];[a1][2_scalled]overlay=0:0 -c:v libx264 -t " + duration + " -pix_fmt yuv420p " + filePath;

        FunctionsPhoto.LogE("cmd", "------>" + cmdString);

        new InterAds().showInterAds(LlpVideoActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(LlpVideoActivity.this, LlpProgressActivity.class);
                intent.putExtra("cmdString", cmdString);
                intent.putExtra("filePath", filePath);
                intent.putExtra("duration", duration);
                intent.putExtra("result", 2);
                startActivity(intent);
                finish();
            }
        });

    }

    public static void execCmd(String[] cmd, long duration, final OnEditorListener onEditorListener) {
        FFmpegCmd.exec(cmd, duration, new OnEditorListener() {
            @Override
            public void onSuccess() {
                onEditorListener.onSuccess();
            }

            @Override
            public void onFailure() {
                onEditorListener.onFailure();
            }

            @Override
            public void onProgress(final float progress) {
                onEditorListener.onProgress(progress);
            }
        });
    }

    private long getDuration(String videoPath) {
        long duration = 0;
        try {
            MediaMetadataRetriever mFFmpegMediaMetadataRetriever = new MediaMetadataRetriever();
            mFFmpegMediaMetadataRetriever.setDataSource(videoPath);
            String mVideoDuration = mFFmpegMediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            duration = Long.parseLong(mVideoDuration);
        } catch (Exception e) {
            FunctionsPhoto.LogE("Exception", "------>" + e.getMessage());
        }
        return duration / 1000;
    }

    public void dilogShow() {
        try {

            if (dialog != null && dialog.isShowing())
                dialog.dismiss();

            dialog = new Dialog(LlpVideoActivity.this);
            dialog.setContentView(R.layout.custom_dialog_save_video_photo);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            txtProgress = dialog.findViewById(R.id.txtvlu);

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
            FrameLayout nativeframe = dialog.findViewById(R.id.nativeAd);
            TextView adspace = dialog.findViewById(R.id.ad_space);
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    if (PowerPreference.getDefaultFile().getBoolean(Constant.LoaderNativeOnOff, false)) {
                        new LargeNativeAds().showNativeAds(LlpVideoActivity.this, dialog);
                    } else {
                        nativeframe.setVisibility(View.GONE);
                        adspace.setVisibility(View.GONE);
                    }
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dilogHide() {
        dialog.dismiss();
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (ecoPlayer != null) {
            ecoPlayer.release();
            ecoPlayer = null;
        }
    }

    @Override
    public void onBackPressed() {
        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                startActivity(new Intent(getApplicationContext(), LlpHomeActivity.class));
                LlpVideoActivity.this.finish();
            }
        });

    }
}