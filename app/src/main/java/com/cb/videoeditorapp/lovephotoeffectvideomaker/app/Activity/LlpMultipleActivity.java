package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;



import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.EffectAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.FrameAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.StickersAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.ThemeAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.LargeNativeAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.ImageModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.TransitionModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Service.Service1Photo;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Service.Service2Photo;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Service.Service3Photo;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.BitmapStickerIconPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.DeleteIconEventPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.DrawableStickerPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.FlipHorizontallyEventPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.StickerPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.StickerViewPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Sticker.ZoomIconEventPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ApplicationPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.RecyclerItemClickListenerPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityMultiplePhotoBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.preference.PowerPreference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpGalleryActivity.drag_imageData;
import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpMusicActivity.getTime;
import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ApplicationPhoto.getContext;
import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ApplicationPhoto.horiHeight;
import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ApplicationPhoto.mHeight;
import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ApplicationPhoto.mWidth;
import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto.LogE;
import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Service.Service3Photo.m12636a;

public class LlpMultipleActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.img_editor_Back)
    ImageView img_editor_Back;
    @BindView(R.id.crd_export)
    RelativeLayout crd_export;
    @BindView(R.id.img_preview)
    ImageView img_preview;
    @BindView(R.id.mIvFrame)
    ImageView mIvFrame;
    @BindView(R.id.flMain)
    FrameLayout flMain;
    @BindView(R.id.img_overview)
    ImageView img_overview;
    @BindView(R.id.img_PlayPause)
    ImageView img_PlayPause;
    @BindView(R.id.tvCurrentTime)
    TextView tvCurrentTime;
    @BindView(R.id.seekbar_Play)
    SeekBar seekbar_Play;
    @BindView(R.id.tvEndTime)
    TextView tvEndTime;
    @BindView(R.id.lin_process)
    LinearLayout lin_process;
    @BindView(R.id.flVideoComponents)
    RelativeLayout flVideoComponents;
    @BindView(R.id.recycle_style)
    RecyclerView recycle_style;
    @BindView(R.id.lin_styletheme_item)
    LinearLayout lin_styletheme_item;
    @BindView(R.id.defalt_portrait)
    ImageView defalt_portrait;
    @BindView(R.id.txt_vertical)
    TextView txt_vertical;
    @BindView(R.id.defalt_square)
    ImageView defalt_square;
    @BindView(R.id.txt_square)
    TextView txt_square;
    @BindView(R.id.defalt_landscape)
    ImageView defalt_landscape;
    @BindView(R.id.txt_widescrn)
    TextView txt_widescrn;
    @BindView(R.id.lin_formate_item)
    LinearLayout lin_formate_item;
    @BindView(R.id.recycle_Frame)
    RecyclerView recycle_Frame;
    @BindView(R.id.lin_frame_item)
    LinearLayout lin_frame_item;
    @BindView(R.id.imgMoreEffect)
    ImageView imgMoreEffect;
    @BindView(R.id.recycle_Effect)
    RecyclerView recycle_Effect;
    @BindView(R.id.lin_effct_item)
    LinearLayout lin_effct_item;
    @BindView(R.id.recycle_strickers)
    RecyclerView recycle_strickers;
    @BindView(R.id.img_addText)
    ImageView img_addText;
    @BindView(R.id.txt_addText)
    TextView txt_addText;
    @BindView(R.id.lin_addText_clik)
    LinearLayout lin_addText_clik;
    @BindView(R.id.imd_addStrickers)
    AppCompatImageView imd_addStrickers;
    @BindView(R.id.txt_addStrickers)
    TextView txt_addStrickers;
    @BindView(R.id.lin_addStickers_clik)
    LinearLayout lin_addStickers_clik;

    @BindView(R.id.img_Style)
    ImageView img_Style;
    @BindView(R.id.txt_Style)
    TextView txt_Style;
    @BindView(R.id.lin_img_Style_click)
    LinearLayout lin_img_Style_click;
    @BindView(R.id.img_Effect)
    ImageView img_Effect;
    @BindView(R.id.txt_Effect)
    TextView txt_Effect;
    @BindView(R.id.lin_img_Effect_click)
    LinearLayout lin_img_Effect_click;
    @BindView(R.id.img_AddMusic)
    ImageView img_AddMusic;
    @BindView(R.id.txt_AddMusic)
    TextView txt_AddMusic;
    @BindView(R.id.lin_img_AddMusic_click)
    LinearLayout lin_img_AddMusic_click;
    @BindView(R.id.img_Formate)
    ImageView img_Formate;
    @BindView(R.id.txt_Formate)
    TextView txt_Formate;
    @BindView(R.id.lin_img_Formate_click)
    LinearLayout lin_img_Formate_click;
    @BindView(R.id.img_Framerecycle)
    ImageView img_Framerecycle;
    @BindView(R.id.txt_Framerecycle)
    TextView txt_Framerecycle;
    @BindView(R.id.lin_img_Framerecycle_click)
    LinearLayout lin_img_Framerecycle_click;
    @BindView(R.id.multieditlayout)
    FrameLayout multieditlayout;
    @BindView(R.id.llEdit)
    LinearLayout llEdit;

    @BindView(R.id.lleditArea)
    LinearLayout lleditArea;

    File[] listFile;
    private String[] listStickers;
    private String[] listFrame;

    private ArrayList<TransitionModelPhoto> themeSelectModels;
    private ArrayList<TransitionModelPhoto> durationSelectModels;
    private ArrayList<TransitionModelPhoto> FrameSelectModels;
    private ThemeAdapterAnimation themeAdapter;
    public EffectAdapterAnimation effectAdapter;
    private FrameAdapterAnimation frameAdapter;
    private StickersAdapterAnimation galleryAdapter;

    private int clickPositionTheme = 0;

    private long WAIT_TIME;
    private int currentIndex = 0;
    private int lovecurrentIndex = 0;
    private int speed = 9;
    private int startIndex;
    private int endIndex;
    public static Handler myHandler = new Handler();
    private Runnable myRunnable;
    private Dialog dialog;
    private ArrayList<Bitmap> selectedImages = new ArrayList<>();
    private ArrayList<ImageModelPhoto> effectImages = new ArrayList();
    private boolean isPlayPause = true;

    public static MediaPlayer mediaPlayer;
    private static String musicpath = null;
    String selEffect = "";
    private int durationCount;

    private String imgPath = null;
    public static StickerViewPhoto stickerView;

    public static String squer = "square";
    ActivityMultiplePhotoBinding binding;
    public static int f9024a = 720;
    public static int f9025b = 720;

    private String fileName;
    private String effectpath;

    private Handler mHandler = new Handler();
    private Runnable mRunnable;

    private boolean flg_break = false;
    private boolean flg_break_stop = false;
    private int videoQuality = 720;

    int pos = 1;
    String path = "";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMultiplePhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);
        squer = "square";

        if (squer.equalsIgnoreCase("horizontal")) {
            f9025b = 720;
            f9024a = 480;
        } else if (squer.equalsIgnoreCase("vertical")) {
            f9025b = 720;
            f9024a = 1280;
        } else if (squer.equalsIgnoreCase("square")) {
            f9025b = videoQuality;
            f9024a = videoQuality;
        }

        // FunctionsPhoto.LogW("format", squer);

        String outputVideoPath = Constant.getOutputPath(this) + File.separator + getString(R.string.mainFolder);
        File file = new File(outputVideoPath, getString(R.string.temp_folder));

        if (file.exists()) {
            deleteDir(file);
        }

        ArrayList<String> listItems = new ArrayList<String>();
        listItems.add("");

        listItems.size();

        getID();
        selEffect = Constant.themeListhum[1];
        SaveThumJpg();
        loadSticker();

        ImageCreate();

    }

    public void nextImage() {
        try {
            startIndex = 0;
            endIndex = effectImages.size() - 1;
            WAIT_TIME = speed * 10;
            seekbar_Play.setProgress((int) (currentIndex));
            //FunctionsPhoto.LogW("WAIT_TIME", "WAIT_TIME - " + WAIT_TIME + "\ncurrentIndex - " + currentIndex + "\nprogress - " + (WAIT_TIME * currentIndex) / 1000);
            seekbar_Play.setMax(effectImages.size());
            seekbar_Play.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    tvCurrentTime.setText(getTime(progress / speed));
                    currentIndex = progress;
                    FunctionsPhoto.LogW("yourprogress", String.valueOf(progress / speed));
                    if (mediaPlayer != null && fromUser) {
                        mediaPlayer.seekTo(((progress / speed) * 1000));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

//            int effectImageNo = effectImages.size();
//            int durationImageNo = durationCount * selectedImages.size();
//
//            //    FunctionsPhoto.LogW("totalImages", effectImageNo + "====" + durationImageNo);
//
//            int totalImageNo = effectImageNo + durationImageNo;
            final long duration = (effectImages.size() / speed);

            runOnUiThread(() -> tvEndTime.setText(getTime((int) duration)));

            float progress = (WAIT_TIME * currentIndex) / 1000;

//            FunctionsPhoto.LogW("progress", String.valueOf(progress));
//            FunctionsPhoto.LogW("path", String.valueOf(currentIndex));
//            FunctionsPhoto.LogW("path", String.valueOf(effectImages.get(currentIndex)));

            BitmapFactory.Options options = new BitmapFactory.Options();
            //  options.inSampleSize = 1;
            Bitmap imbm = BitmapFactory.decodeFile(effectImages.get(currentIndex).getEffectImagePath(), options);
            img_preview.setImageBitmap(imbm);

            EffectimgShow();

            currentIndex++;
            myHandler.postDelayed(myRunnable = () -> {
                if (currentIndex > endIndex) {
                    currentIndex = 0;
                    tvCurrentTime.setText(getTime(0));
                    myHandler.removeCallbacks(myRunnable);
                    img_PlayPause.setVisibility(View.VISIBLE);
                    isPlayPause = false;
                    seekbar_Play.setProgress(0);
                    pauseMusic();
                    getMedia(LlpMultipleActivity.this);
                } else {
                    nextImage();
                }
            }, WAIT_TIME);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EffectimgShow() {

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

//                String effectpath22 = listFile[clickPositionEffect].getAbsolutePath();
//                fileName = effectpath22.substring(effectpath22.lastIndexOf('/') + 1, effectpath22.length());
//                effectpath = listFile[clickPositionEffect].getAbsolutePath() + "/" + fileName;

                fileName = selEffect;
                effectpath = effectFile.getAbsolutePath() + "/" + fileName + "/" + fileName;

                File file = new File(effectpath);
                final File[] listFile2;
                listFile2 = file.listFiles();
                Collections.sort(Arrays.asList(listFile2));
                if (listFile2.length > 0) {

                    if (listFile2 != null) {
                        lovecurrentIndex = currentIndex % listFile2.length;

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        // options.inSampleSize = 1;
                        Bitmap imbm = BitmapFactory.decodeFile(listFile2[lovecurrentIndex].getAbsolutePath(), options);

                        if (squer.equalsIgnoreCase("horizontal")) {
                            imbm = Bitmap.createScaledBitmap(imbm, mWidth, horiHeight, false);
                        } else if (squer.equalsIgnoreCase("vertical")) {
                            imbm = Bitmap.createScaledBitmap(imbm, mWidth, mHeight, false);
                        } else if (squer.equalsIgnoreCase("square")) {
                            imbm = Bitmap.createScaledBitmap(imbm, videoQuality, videoQuality, false);
                        }
                        img_overview.setImageBitmap(imbm);

                    } else {
                    }

                }
                FunctionsPhoto.LogW("log", "log 2");
            } else {
                FunctionsPhoto.LogW("log", "log 4");
            }
        } else {
            FunctionsPhoto.LogW("log", "log 5");
        }


    }

    public void ImageCreate() {
        dilog_show();
        framimgchng(mIvFrame);
        effectImages.clear();
        currentIndex = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 0 to 18 3d and 19 to 45 2d

                FunctionsPhoto.LogW("effectImages", String.valueOf(effectImages.size()));
                if (clickPositionTheme < 27) {
                    FunctionsPhoto.LogW("farto", "------3d-----" + clickPositionTheme);
                    apply2dTransition(clickPositionTheme);
                } else {
                    FunctionsPhoto.LogW("farto", "------3d-----" + clickPositionTheme);
                    apply3dTransition(clickPositionTheme);
                }
            }
        }).start();

        runOnUiThread(() -> {
            isPlayPause = true;
            pauseMusic();
            lin_process.setVisibility(View.VISIBLE);
            mRunnable = () -> {
                if (effectImages.size() < 20) {
                    mHandler.postDelayed(mRunnable, 500);
                } else {
                    LogE("ImageCreate", "After 6 Second Successfully created Upto 21 Images.........");
//                            startPlayer();
                    dilogHide();
                    FunctionsPhoto.LogW("SavedImage", "nextImage");
                    tvCurrentTime.setText(getTime(0));
                    img_PlayPause.setVisibility(View.GONE);
                    nextImage();
                    getMedia(LlpMultipleActivity.this);
                    playMusic();

                }
            };
            mHandler.postDelayed(mRunnable, 500);
        });


    }

    private void getID() {

        stickerView = (StickerViewPhoto) findViewById(R.id.sticker_view);
        defalt_square.setColorFilter(getResources().getColor(R.color.transparent));
        txt_square.setTextColor(getResources().getColor(R.color.colorBlack));

        img_editor_Back.setOnClickListener(this);
        crd_export.setOnClickListener(this);

        lin_img_Style_click.setOnClickListener(this);
        lin_img_AddMusic_click.setOnClickListener(this);
        lin_img_Formate_click.setOnClickListener(this);
        lin_img_Framerecycle_click.setOnClickListener(this);
        lin_img_Effect_click.setOnClickListener(this);
        defalt_portrait.setOnClickListener(this);
        defalt_square.setOnClickListener(this);
        defalt_landscape.setOnClickListener(this);
        lin_addText_clik.setOnClickListener(this);
        lin_addStickers_clik.setOnClickListener(this);
        img_PlayPause.setOnClickListener(this);
        lin_process.setOnClickListener(this);
        imgMoreEffect.setOnClickListener(this);
        flVideoComponents.setOnClickListener(this);

        setLayoutParams();
        loadFrame();
        setUpThemeAdapter();
        stikerset();
    }

    public void framimgchng(ImageView img) {
        if (img.getDrawable() == null) {
            FunctionsPhoto.LogW("frmimg", "=====null====");
        } else {
            FunctionsPhoto.LogW("frmimg", "===notnull===");
            Drawable d = img.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();

            if (squer.equalsIgnoreCase("horizontal")) {
                bitmap = Bitmap.createScaledBitmap(bitmap, mWidth, horiHeight, false);

            } else if (squer.equalsIgnoreCase("vertical")) {
                bitmap = Bitmap.createScaledBitmap(bitmap, mWidth, mHeight, false);
            } else if (squer.equalsIgnoreCase("square")) {
                bitmap = Bitmap.createScaledBitmap(bitmap, videoQuality, videoQuality, false);
            }
            img.setImageBitmap(bitmap);
        }
    }

    public static void getMedia(Activity activity) {

        String mainFolder = activity.getResources().getString(R.string.mainFolder);
        File mainFile = new File(Constant.getFolderPath(ApplicationPhoto.getContext()) + "/" + mainFolder);
        if (!mainFile.exists())
            mainFile.mkdir();

        File subFile = new File(mainFile, activity.getString(R.string.temp_folder));
        if (!subFile.exists())
            subFile.mkdir();

        File nameFile = new File(subFile, "Video_music.mp3");

        try {
            if (nameFile.exists()) {
                musicpath = nameFile.getAbsolutePath();
                mediaPlayer = MediaPlayer.create(activity, Uri.parse(musicpath));
                mediaPlayer.setLooping(true);
                FunctionsPhoto.LogW("TrimeActivity", "music dn");
            } else {
                mediaPlayer = MediaPlayer.create(activity, R.raw._2);
                mediaPlayer.setLooping(true);
                FunctionsPhoto.LogW("TrimeActivity", "music not dn");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playMusic() {
        try {
            if (mediaPlayer != null) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pauseMusic() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.crd_export:
                pauseMusic();
                myHandler.removeCallbacks(myRunnable);
                createVideoFile();
                break;

            case R.id.img_editor_Back:
                onBackPressed();
                break;

            case R.id.lin_img_Style_click:
                comman_color();
                img_Style.setColorFilter(getResources().getColor(R.color.transparent));
                txt_Style.setTextColor(getResources().getColor(R.color.colorPrimary));
                lin_styletheme_item.setVisibility(View.VISIBLE);
                img_AddMusic.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_AddMusic.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Formate.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Formate.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Effect.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Effect.setTextColor(getResources().getColor(R.color.colorBlack));
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorBlack));
                img_addText.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addText.setTextColor(getResources().getColor(R.color.colorBlack));
                multieditlayout.setVisibility(View.VISIBLE);

                break;

            case R.id.lin_img_AddMusic_click:
                comman_color();

                currentIndex = 0;
                tvCurrentTime.setText(getTime(0));
                myHandler.removeCallbacks(myRunnable);
                img_PlayPause.setVisibility(View.VISIBLE);
                isPlayPause = false;
                seekbar_Play.setProgress(0);
                pauseMusic();
                getMedia(LlpMultipleActivity.this);

                img_AddMusic.setColorFilter(getResources().getColor(R.color.transparent));
                txt_AddMusic.setTextColor(getResources().getColor(R.color.colorPrimary));
                img_Formate.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Formate.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Effect.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Effect.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Style.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Style.setTextColor(getResources().getColor(R.color.colorBlack));
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorBlack));
                img_addText.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addText.setTextColor(getResources().getColor(R.color.colorBlack));
                multieditlayout.setVisibility(View.GONE);
                new InterAds().showInterAds(LlpMultipleActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(LlpMultipleActivity.this, LlpMusicActivity.class);
                        intent.putExtra("isSingle", false);
                        startActivityForResult(intent, 1001);
                    }
                });
                break;

            case R.id.lin_img_Formate_click:
                comman_color();
                img_Formate.setColorFilter(getResources().getColor(R.color.transparent));
                txt_Formate.setTextColor(getResources().getColor(R.color.colorPrimary));
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Effect.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Effect.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Style.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Style.setTextColor(getResources().getColor(R.color.colorBlack));
                img_AddMusic.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_AddMusic.setTextColor(getResources().getColor(R.color.colorBlack));
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorBlack));
                img_addText.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addText.setTextColor(getResources().getColor(R.color.colorBlack));
                lin_formate_item.setVisibility(View.VISIBLE);
                multieditlayout.setVisibility(View.VISIBLE);

                break;

            case R.id.lin_img_Framerecycle_click:
                comman_color();
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.transparent));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorPrimary));
                img_Effect.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Effect.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Style.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Style.setTextColor(getResources().getColor(R.color.colorBlack));
                img_AddMusic.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_AddMusic.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Formate.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Formate.setTextColor(getResources().getColor(R.color.colorBlack));
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorBlack));
                img_addText.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addText.setTextColor(getResources().getColor(R.color.colorBlack));
                lin_frame_item.setVisibility(View.VISIBLE);
                multieditlayout.setVisibility(View.VISIBLE);

                break;

         /*   case R.id.lin_img_editview_click:
                comman_color();
                img_editview.setColorFilter(getResources().getColor(R.color.transparent));
                txt_editview.setTextColor(getResources().getColor(R.color.colorPrimary));
                img_Effect.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Effect.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Style.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Style.setTextColor(getResources().getColor(R.color.colorBlack));
                img_AddMusic.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_AddMusic.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Formate.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Formate.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorBlack));
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorBlack));
                img_addText.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addText.setTextColor(getResources().getColor(R.color.colorBlack));
                lin_edit_item.setVisibility(View.VISIBLE);

                break;
*/
            case R.id.lin_img_Effect_click:
                comman_color();
                img_Effect.setColorFilter(getResources().getColor(R.color.transparent));
                txt_Effect.setTextColor(getResources().getColor(R.color.colorPrimary));
                img_Style.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Style.setTextColor(getResources().getColor(R.color.colorBlack));
                img_AddMusic.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_AddMusic.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Formate.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Formate.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorBlack));
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorBlack));
                img_addText.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addText.setTextColor(getResources().getColor(R.color.colorBlack));
                lin_effct_item.setVisibility(View.VISIBLE);
                multieditlayout.setVisibility(View.VISIBLE);


                break;

            case R.id.defalt_portrait:
                method_colcor_formate();
                defalt_portrait.setColorFilter(getResources().getColor(R.color.transparent));
                squer = "vertical";
                txt_vertical.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (flg_break_stop) {
                    flg_break_stop = false;
                    ImageCreate();

                } else {
                    flg_break = true;
                }
                break;

            case R.id.defalt_square:
                method_colcor_formate();
                defalt_square.setColorFilter(getResources().getColor(R.color.transparent));
                squer = "square";
                txt_square.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (flg_break_stop) {
                    flg_break_stop = false;
                    ImageCreate();

                } else {
                    flg_break = true;
                }
                break;


            case R.id.defalt_landscape:
                method_colcor_formate();
                defalt_landscape.setColorFilter(getResources().getColor(R.color.transparent));
                txt_widescrn.setTextColor(getResources().getColor(R.color.colorPrimary));
                squer = "horizontal";

                if (flg_break_stop) {
                    flg_break_stop = false;
                    ImageCreate();

                } else {
                    flg_break = true;
                }


                break;

            case R.id.lin_addText_clik:
                method_edit_formate();
                img_addText.setColorFilter(getResources().getColor(R.color.transparent));
                txt_addText.setTextColor(getResources().getColor(R.color.colorPrimary));
                img_Effect.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Effect.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Style.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Style.setTextColor(getResources().getColor(R.color.colorBlack));
                img_AddMusic.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_AddMusic.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Formate.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Formate.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorBlack));
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorBlack));
//                startActivityForResult(new Intent(MultipleActivityPhoto.this, TextEditActivityPhoto.class), 5555);
                multieditlayout.setVisibility(View.GONE);

                new InterAds().showInterAds(LlpMultipleActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        Intent i = new Intent(LlpMultipleActivity.this, LlpTextEditActivity.class);
                        i.putExtra("number", 1);
                        startActivity(i);
                    }
                });
                break;

            case R.id.lin_addStickers_clik:
                method_edit_formate();
                imd_addStrickers.setColorFilter(getResources().getColor(R.color.transparent));
                txt_addStrickers.setTextColor(getResources().getColor(R.color.colorPrimary));
                img_addText.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_addText.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Effect.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Effect.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Style.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Style.setTextColor(getResources().getColor(R.color.colorBlack));
                img_AddMusic.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_AddMusic.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Formate.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Formate.setTextColor(getResources().getColor(R.color.colorBlack));
                img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorBlack));
                txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorBlack));
                multieditlayout.setVisibility(View.VISIBLE);

                recycle_strickers.setVisibility(View.VISIBLE);
                lin_styletheme_item.setVisibility(View.GONE);
                lin_frame_item.setVisibility(View.GONE);
                lin_effct_item.setVisibility(View.GONE);

                break;

            case R.id.flVideoComponents:
                if (isPlayPause) {
                    img_PlayPause.setVisibility(View.VISIBLE);
                    isPlayPause = false;
                    myHandler.removeCallbacks(myRunnable);
                    pauseMusic();
                } else {
                    if (currentIndex > endIndex) {
                        currentIndex = 0;
                    } else {
                        img_PlayPause.setVisibility(View.GONE);
                        FunctionsPhoto.LogW("SavedImage", "PlayPause");
                        playMusic();
//                        preview(0);
                        nextImage();
                    }
                    isPlayPause = true;
                }
                break;

            case R.id.img_PlayPause:
                if (isPlayPause) {
                    img_PlayPause.setVisibility(View.VISIBLE);
                    isPlayPause = false;
                    myHandler.removeCallbacks(myRunnable);
                    pauseMusic();
                } else {
                    if (currentIndex > endIndex) {
                        currentIndex = 0;
                    } else {
                        img_PlayPause.setVisibility(View.GONE);
                        FunctionsPhoto.LogW("SavedImage", "PlayPause");
                        playMusic();
//                        preview(0);

                        nextImage();
                    }
                    isPlayPause = true;
                }
                break;

            case R.id.lin_process:
                FunctionsPhoto.LogW("lin_process", "");
                break;

            case R.id.imgMoreEffect:
                FunctionsPhoto adUtils = new FunctionsPhoto();
                new InterAds().showInterAds(LlpMultipleActivity.this, new InterAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed() {
                        startActivityForResult(new Intent(LlpMultipleActivity.this, LlpStoreActivity.class), 1);
                    }
                });
                break;
        }
    }

    public Bitmap createBitmapFromView(View view) {
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        Bitmap createBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        view.draw(new Canvas(createBitmap));
        int i = 10;
        int i2 = 10;

        if (squer.equalsIgnoreCase("horizontal")) {
            i = mWidth;
            i2 = horiHeight;
        } else if (squer.equalsIgnoreCase("vertical")) {
            i = mWidth;
            i2 = mHeight;
        } else if (squer.equalsIgnoreCase("square")) {
            i = videoQuality;
            i2 = videoQuality;
        }

        float f = (float) i;
        //   FunctionsPhoto.LogE("imgfrm","f === "+f);
        float width = (float) createBitmap.getWidth();
        //   FunctionsPhoto.LogE("imgfrm","width === "+width);
        float f2 = (float) i2;
        //  FunctionsPhoto.LogE("imgfrm","f2 === "+f2);
        float height = (float) createBitmap.getHeight();
        //  FunctionsPhoto.LogE("imgfrm","height === "+height);
        float max = Math.max(f / width, f2 / height);
        //   FunctionsPhoto.LogE("imgfrm","max === "+max);
        width *= max;
        //  FunctionsPhoto.LogE("imgfrm","width === "+width);
        max *= height;
        //  FunctionsPhoto.LogE("imgfrm","max === "+max);
        f = (f - width) / 2.0f;
        //  FunctionsPhoto.LogE("imgfrm","f === "+f);
        f2 = (f2 - max) / 2.0f;
        //  FunctionsPhoto.LogE("imgfrm","f2 === "+f2);
        RectF rectF = new RectF(f, f2, width + f, max + f2);
        // RectF rectF = new RectF(-360,0,1080,1280);
        // FunctionsPhoto.LogE("imgfrm","rectF === "+rectF);

        Bitmap resizedBitmap = Bitmap.createBitmap(i, i2, createBitmap.getConfig());


        new Canvas(resizedBitmap).drawBitmap(createBitmap, null, rectF, null);


        return resizedBitmap;
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    img_PlayPause.setVisibility(View.VISIBLE);
                    isPlayPause = false;
                    myHandler.removeCallbacks(myRunnable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRunnable != null && mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    public void onBackPressed() {

        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                startActivity(new Intent(getApplicationContext(), LlpHomeActivity.class));
                LlpMultipleActivity.this.finish();
            }
        });
//        super.onBackPressed();
    }

    int f6296e;
    ArrayList<ImageModelPhoto> f6295d = drag_imageData;
    ArrayList f9151c;
    public static File f6552a = new File(Constant.getFolderPath(ApplicationPhoto.getContext()));
    public static File f6553b = new File(f6552a, getContext().getString(R.string.mainFolder));
    public static final File f6554c = new File(f6553b, getContext().getString(R.string.temp_folder));
    public String f6224c;
    public static boolean f6292a = false;

    public static boolean multieffct = true;
    public static int[] ramdom3dNumbers = {27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45};
    public static int[] randomNumbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

    private void apply2dTransition(int effectPos) {
        flg_break = false;

        FunctionsPhoto.LogW("Exception", "==== 1");

        String mainFolder = getString(R.string.mainFolder);
        String subFolder = getString(R.string.temp_folder);
        String effectFolder = getString(R.string.effect_folder);

        File mainFile = new File(Constant.getFolderPath(ApplicationPhoto.getContext()) + "/" + mainFolder);
        File subFile = new File(mainFile, subFolder);
        File effectFile = new File(subFile, effectFolder);

        if (effectFile.exists())
            deleteDir(effectFile);

        FunctionsPhoto.LogW("effectPos", "----2d------" + String.valueOf(effectPos));

        int width = 0;
        int height = 0;
        if (squer.equalsIgnoreCase("horizontal")) {
            width = 720;
            height = 480;
        } else if (squer.equalsIgnoreCase("vertical")) {
            width = 720;
            height = 1280;
        } else if (squer.equalsIgnoreCase("square")) {
            width = videoQuality;
            height = videoQuality;
        }

        Bitmap bitmap;
        int i;
        System.currentTimeMillis();
        Bitmap bitmap2 = null;
        this.f6296e = this.f6295d.size();
        int i2 = 0;

        FunctionsPhoto.LogW("Exception", "==== 2");

        while (i2 < this.f6295d.size() - 1) {
            File a = m8480a(getString(R.string.effect_folder), i2);
            if (flg_break) {
                break;
            }

            if (multieffct) {
                if (clickPositionTheme == 0) {
                    effectPos = randomNumbers[new Random().nextInt(randomNumbers.length)];
                }
            }

            FunctionsPhoto.LogW("Exception", "==== 3");
            if (i2 == 0) {
                Bitmap a2 = m8263a(f6295d.get(i2).getImagePath());
                Bitmap a3 = m8260a(a2, width, height);
                Bitmap a4 = m8262a(a2, a3, width, height, 1.0f, 0.0f);
                a3.recycle();
                a2.recycle();
                System.gc();
                bitmap = a4;
            } else {
                if (bitmap2 == null || bitmap2.isRecycled()) {
                    Bitmap a5 = m8263a((f6295d.get(i2)).getImagePath());
                    Bitmap a6 = m8260a(a5, width, height);
                    Bitmap a7 = m8262a(a5, a6, width, height, 1.0f, 0.0f);
                    a6.recycle();
                    a5.recycle();
                    bitmap2 = a7;
                }
                bitmap = bitmap2;
            }
            Bitmap a8 = m8263a((f6295d.get(i2 + 1)).getImagePath());
            Bitmap a9 = m8260a(a8, width, height);
            Bitmap a10 = m8262a(a8, a9, width, height, 1.0f, 0.0f);
            a9.recycle();
            a8.recycle();
            System.gc();
            Service1Photo.m8223a();
// C2010b bVar = (C2010b) this.f6294c.f5512p.mo8139a().get(i2 % this.f6294c.f5512p.mo8139a().size());
            int i3 = 0;
            int i4 = i2;
            while (true) {
                if (((float) i3) >= Service1Photo.f6235a) {
                    break;
                }

                FunctionsPhoto.LogW("Exception", "==== 4");
                if (flg_break) {

                    break;
                }

                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Canvas canvas = new Canvas(createBitmap);
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);


                switch (effectPos) {
                    case 0:
                        canvas.drawBitmap(photoE6(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 1:
                        canvas.drawBitmap(photoE1(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 2:
                        canvas.drawBitmap(photoE2(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 3:
                        canvas.drawBitmap(photoE3(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 4:
                        canvas.drawBitmap(photoE4(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 5:
                        canvas.drawBitmap(photoE5(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 6:
                        canvas.drawBitmap(photoE6(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 7:
                        canvas.drawBitmap(photoE7(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 8:
                        canvas.drawBitmap(photoE8(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 9:
                        canvas.drawBitmap(photoE9(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 10:
                        canvas.drawBitmap(photoE10(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 11:
                        canvas.drawBitmap(photoE11(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 12:
                        canvas.drawBitmap(photoE12(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 13:
                        canvas.drawBitmap(photoE13(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 14:
                        canvas.drawBitmap(photoE14(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 15:
                        canvas.drawBitmap(photoE15(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 16:
                        canvas.drawBitmap(photoE16(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 17:
                        canvas.drawBitmap(photoE17(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 18:
                        canvas.drawBitmap(photoE18(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 19:
                        canvas.drawBitmap(photoE19(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 20:
                        canvas.drawBitmap(photoE20(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 21:
                        canvas.drawBitmap(photoE21(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 22:
                        canvas.drawBitmap(photoE22(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 23:
                        canvas.drawBitmap(photoE23(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 24:
                        canvas.drawBitmap(photoE24(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 25:
                        canvas.drawBitmap(photoE25(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                    case 26:
                        canvas.drawBitmap(photoE26(width, height, i3), 0.0f, 0.0f, paint);
                        break;
                }


                FunctionsPhoto.LogW("Exception", "==== 5");

                Bitmap createBitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(createBitmap2);
                canvas2.drawBitmap(a10, 0.0f, 0.0f, null);
                canvas2.drawBitmap(createBitmap, 0.0f, 0.0f, new Paint());

                if (flg_break) {

                    break;
                }
                File file = new File(a, String.format("img%02d.jpg", i3));
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    if (flg_break) {

                        break;
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    createBitmap2.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }

                if (flg_break) {

                    break;
                }
                i = i4;
                boolean z = false;

                ImageModelPhoto imageData = new ImageModelPhoto();
                imageData.setEffectImagePath(file.getAbsolutePath());
                effectImages.add(imageData);
                //  FunctionsPhoto.LogW("ImagePath Create", file.getAbsolutePath());
                i3++;
                i4 = i;

                FunctionsPhoto.LogW("Exception", "==== 6");
            }
            i2 = i4 + 1;
            bitmap2 = a10;
        }

        FunctionsPhoto.LogW("Exception", "==== 7");
        f6292a = true;

        if (flg_break) {
            flg_break_stop = false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lin_process.setVisibility(View.VISIBLE);
                    ImageCreate();
                }
            });

        } else {
            flg_break_stop = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    lin_process.setVisibility(View.GONE);
                }
            });
        }

    }

    //    ----------------------------- 2d effect-------------------------------------

    public Bitmap photoE1(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = (((float) i) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        float f2 = (((float) i2) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        Path path = new Path();
        path.moveTo((((float) i) / 2.0f) + f, 0.0f);
        path.lineTo((((float) i) / 2.0f) + f, (((float) i2) / 2.0f) - f2);
        path.lineTo((float) i, (((float) i2) / 2.0f) - f2);
        path.lineTo((float) i, (((float) i2) / 2.0f) + f2);
        path.lineTo((((float) i) / 2.0f) + f, (((float) i2) / 2.0f) + f2);
        path.lineTo((((float) i) / 2.0f) + f, (float) i2);
        path.lineTo((((float) i) / 2.0f) - f, (float) i2);
        path.lineTo((((float) i) / 2.0f) - f, (((float) i2) / 2.0f) - f2);
        path.lineTo(0.0f, (((float) i2) / 2.0f) - f2);
        path.lineTo(0.0f, (((float) i2) / 2.0f) + f2);
        path.lineTo((((float) i) / 2.0f) - f, f2 + (((float) i2) / 2.0f));
        path.lineTo((((float) i) / 2.0f) - f, 0.0f);
        path.close();
        canvas.drawPath(path, paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE2(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        float f = (float) ((i / Service1Photo.f6236b) * i3);
        float f2 = (float) ((i2 / Service1Photo.f6236b) * i3);
        Canvas canvas = new Canvas(createBitmap);
        Path path = new Path();
        path.moveTo(0.0f, (float) i2);
        path.cubicTo(0.0f, (float) i2, (((float) i) / 2.0f) - f, (((float) i2) / 2.0f) - f2, (float) i, 0.0f);
        path.cubicTo((float) i, 0.0f, (((float) i) / 2.0f) + f, (((float) i2) / 2.0f) + f2, 0.0f, (float) i2);
        path.close();
        canvas.drawPath(path, paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE3(int i, int i2, int i3) {
        Paint paint = new Paint(1);
        paint.setColor(-16777216);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float a = Service1Photo.m8222a(i * 2, i2 * 2);
        float f = (a / ((float) Service1Photo.f6236b)) * ((float) i3);
        paint.setColor(-16777216);
        canvas.drawColor(-16777216);
        paint.setColor(0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawCircle(((float) i) / 2.0f, ((float) i2) / 2.0f, a - f, paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE4(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawCircle(0.0f, (float) i2, (((float) Math.sqrt((double) ((i * i) + (i2 * i2)))) / ((float) ((int) (22.0f - 1.0f)))) * ((float) i3), paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE5(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawCircle(((float) i) / 2.0f, ((float) i2) / 2.0f, (Service1Photo.m8222a(i * 2, i2 * 2) / ((float) Service1Photo.f6236b)) * ((float) i3), paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE6(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawCircle((float) i, (float) i2, (((float) Math.sqrt((double) ((i * i) + (i2 * i2)))) / ((float) Service1Photo.f6236b)) * ((float) i3), paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE7(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Path path = new Path();
        float f = (((float) i) / ((float) Service1Photo.f6236b)) * ((float) i3);
        float f2 = (((float) i2) / ((float) Service1Photo.f6236b)) * ((float) i3);
        path.moveTo(((float) i) / 2.0f, (((float) i2) / 2.0f) - f2);
        path.lineTo((((float) i) / 2.0f) + f, ((float) i2) / 2.0f);
        path.lineTo(((float) i) / 2.0f, (((float) i2) / 2.0f) + f2);
        path.lineTo((((float) i) / 2.0f) - f, ((float) i2) / 2.0f);
        path.lineTo(((float) i) / 2.0f, (((float) i2) / 2.0f) - f2);
        path.close();
        canvas.drawPath(path, paint);
        return createBitmap;
    }

    public Bitmap photoE8(int i, int i2, int i3) {
        Paint paint = new Paint(1);
        paint.setColor(-16777216);
        paint.setColor(0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-16777216);
        Path path = new Path();
        float f = ((float) i) - ((((float) i) / ((float) Service1Photo.f6236b)) * ((float) i3));
        float f2 = ((float) i2) - ((((float) i2) / ((float) Service1Photo.f6236b)) * ((float) i3));
        path.moveTo(((float) i) / 2.0f, (((float) i2) / 2.0f) - f2);
        path.lineTo((((float) i) / 2.0f) + f, ((float) i2) / 2.0f);
        path.lineTo(((float) i) / 2.0f, (((float) i2) / 2.0f) + f2);
        path.lineTo((((float) i) / 2.0f) - f, ((float) i2) / 2.0f);
        path.lineTo(((float) i) / 2.0f, (((float) i2) / 2.0f) - f2);
        path.close();
        paint.setColor(0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawPath(path, paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE9(int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = (((float) i2) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        float f2 = (((float) i) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        RectF rectF = new RectF(-f2, 0.0f, f2, (float) i2);
        RectF rectF2 = new RectF(0.0f, -f, (float) i, f);
        RectF rectF3 = new RectF(((float) i) - f2, 0.0f, f2 + ((float) i), (float) i2);
        RectF rectF4 = new RectF(0.0f, ((float) i2) - f, (float) i, f + ((float) i2));
        canvas.drawOval(rectF, Service1Photo.f6239e);
        canvas.drawOval(rectF2, Service1Photo.f6239e);
        canvas.drawOval(rectF3, Service1Photo.f6239e);
        canvas.drawOval(rectF4, Service1Photo.f6239e);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE10(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = (((float) i) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        float f2 = (((float) i2) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        Path path = new Path();
        path.moveTo(0.0f, f2);
        path.lineTo(0.0f, 0.0f);
        path.lineTo(f, 0.0f);
        path.lineTo((float) i, ((float) i2) - f2);
        path.lineTo((float) i, (float) i2);
        path.lineTo(((float) i) - f, (float) i2);
        path.lineTo(0.0f, f2);
        path.close();
        path.moveTo(((float) i) - f, 0.0f);
        path.lineTo((float) i, 0.0f);
        path.lineTo((float) i, f2);
        path.lineTo(f, (float) i2);
        path.lineTo(0.0f, (float) i2);
        path.lineTo(0.0f, ((float) i2) - f2);
        path.lineTo(((float) i) - f, 0.0f);
        path.close();
        canvas.drawPath(path, paint);
        return createBitmap;
    }

    public Bitmap photoE11(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        float f = (float) ((i / Service1Photo.f6236b) * i3);
        float f2 = (float) ((i2 / Service1Photo.f6236b) * i3);
        Canvas canvas = new Canvas(createBitmap);
        Path path = new Path();
        path.moveTo((float) (i / 2), 0.0f);
        path.lineTo(((float) (i / 2)) - f, 0.0f);
        path.lineTo(((float) (i / 2)) - (f / 2.0f), (float) (i2 / 6));
        path.lineTo(((float) (i / 2)) - (f / 2.0f), (float) (i2 - (i2 / 6)));
        path.lineTo(((float) (i / 2)) - f, (float) i2);
        path.lineTo(((float) (i / 2)) + f, (float) i2);
        path.lineTo(((float) (i / 2)) + (f / 2.0f), (float) (i2 - (i2 / 6)));
        path.lineTo(((float) (i / 2)) + (f / 2.0f), (float) (i2 / 6));
        path.lineTo(((float) (i / 2)) + f, 0.0f);
        path.lineTo(((float) (i / 2)) - f, 0.0f);
        path.close();
        canvas.drawPath(path, paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE12(int i, int i2, int i3) {
        float a = Service1Photo.m8222a(i, i2);
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        RectF rectF = new RectF();
        rectF.set((((float) i) / 2.0f) - a, (((float) i2) / 2.0f) - a, (((float) i) / 2.0f) + a, a + (((float) i2) / 2.0f));
        float f = (90.0f / ((float) Service1Photo.f6236b)) * ((float) i3);
        canvas.drawArc(rectF, 90.0f, f, true, paint);
        canvas.drawArc(rectF, 180.0f, f, true, paint);
        canvas.drawArc(rectF, 270.0f, f, true, paint);
        canvas.drawArc(rectF, 360.0f, f, true, paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE13(int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        float f = (float) (i / Service1Photo.f6236b);
        float f2 = (float) (i2 / Service1Photo.f6236b);
        for (int i4 = 0; i4 < Service1Photo.f6240f.length; i4++) {
            int nextInt = Service1Photo.f6241g.nextInt(Service1Photo.f6240f[i4].length);
            while (Service1Photo.f6240f[i4][nextInt] == 1) {
                nextInt = Service1Photo.f6241g.nextInt(Service1Photo.f6240f[i4].length);
            }
            Service1Photo.f6240f[i4][nextInt] = 1;
            for (int i5 = 0; i5 < Service1Photo.f6240f[i4].length; i5++) {
                if (Service1Photo.f6240f[i4][i5] == 1) {
                    canvas.drawRoundRect(new RectF(((float) i4) * f, ((float) i5) * f2, (((float) i4) + 1.0f) * f, (((float) i5) + 1.0f) * f2), 0.0f, 0.0f, paint);
                }
            }
        }
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE14(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = (((float) i) / ((float) Service1Photo.f6236b)) * ((float) i3);
        float f2 = (((float) i2) / ((float) Service1Photo.f6236b)) * ((float) i3);
        Path path = new Path();
        path.moveTo(((float) i) - f, 0.0f);
        path.lineTo((float) i, 0.0f);
        path.lineTo((float) i, f2);
        path.lineTo(f, (float) i2);
        path.lineTo(0.0f, (float) i2);
        path.lineTo(0.0f, ((float) i2) - f2);
        path.lineTo(((float) i) - f, 0.0f);
        path.close();
        canvas.drawPath(path, paint);
        return createBitmap;
    }

    public Bitmap photoE15(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = (((float) i) / ((float) Service1Photo.f6236b)) * ((float) i3);
        float f2 = (((float) i2) / ((float) Service1Photo.f6236b)) * ((float) i3);
        Path path = new Path();
        path.moveTo(0.0f, f2);
        path.lineTo(0.0f, 0.0f);
        path.lineTo(f, 0.0f);
        path.lineTo((float) i, ((float) i2) - f2);
        path.lineTo((float) i, (float) i2);
        path.lineTo(((float) i) - f, (float) i2);
        path.lineTo(0.0f, f2);
        path.close();
        canvas.drawPath(path, paint);
        return createBitmap;
    }

    public Bitmap photoE16(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        float f = (((float) i) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        float f2 = (((float) i2) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        new Canvas(createBitmap).drawRect(new RectF(((float) (i / 2)) - f, ((float) (i2 / 2)) - f2, f + ((float) (i / 2)), f2 + ((float) (i2 / 2))), paint);
        return createBitmap;
    }

    public Bitmap photoE17(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = (((float) i) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        float f2 = (((float) i2) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(0.0f, (float) i2);
        path.lineTo(f, (float) i2);
        path.lineTo(f, 0.0f);
        path.moveTo((float) i, (float) i2);
        path.lineTo((float) i, 0.0f);
        path.lineTo(((float) i) - f, 0.0f);
        path.lineTo(((float) i) - f, (float) i2);
        path.moveTo(f, f2);
        path.lineTo(f, 0.0f);
        path.lineTo(((float) i) - f, 0.0f);
        path.lineTo(((float) i) - f, f2);
        path.moveTo(f, ((float) i2) - f2);
        path.lineTo(f, (float) i2);
        path.lineTo(((float) i) - f, (float) i2);
        path.lineTo(((float) i) - f, ((float) i2) - f2);
        canvas.drawPath(path, paint);
        return createBitmap;
    }

    public Bitmap photoE18(int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        float f = ((float) i2) / 10.0f;
        float f2 = (((float) i3) * f) / ((float) Service1Photo.f6236b);
        for (int i4 = 0; i4 < 10; i4++) {
            canvas.drawRect(new Rect(0, (int) (((float) i4) * f), i, (int) ((((float) i4) * f) + f2)), paint);
        }
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE19(int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        float f = ((float) i) / 10.0f;
        float f2 = ((float) i3) * (f / ((float) Service1Photo.f6236b));
        for (int i4 = 0; i4 < 10; i4++) {
            canvas.drawRect(new Rect((int) (((float) i4) * f), 0, (int) ((((float) i4) * f) + f2), i2), paint);
        }
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE20(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawCircle(0.0f, 0.0f, (((float) Math.sqrt((double) ((i * i) + (i2 * i2)))) / ((float) Service1Photo.f6236b)) * ((float) i3), paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE21(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = ((float) Service1Photo.f6236b) / 2.0f;
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, (((float) i) / (((float) Service1Photo.f6236b) / 2.0f)) * ((float) i3), ((float) i2) / 2.0f), 0.0f, 0.0f, paint);
        if (((float) i3) >= 0.5f + f) {
            canvas.drawRoundRect(new RectF(((float) i) - (((float) ((int) (((float) i3) - f))) * (((float) i) / (((float) (Service1Photo.f6236b - 1)) / 2.0f))), ((float) i2) / 2.0f, (float) i, (float) i2), 0.0f, 0.0f, paint);
        }
        return createBitmap;
    }

    public Bitmap photoE22(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawCircle((float) i, 0.0f, (((float) Math.sqrt((double) ((i * i) + (i2 * i2)))) / ((float) Service1Photo.f6236b)) * ((float) i3), paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE23(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = (((float) i) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        float f2 = (((float) i2) / (((float) Service1Photo.f6236b) * 2.0f)) * ((float) i3);
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(f, 0.0f);
        path.lineTo(f, f2);
        path.lineTo(0.0f, f2);
        path.lineTo(0.0f, 0.0f);
        path.close();
        path.moveTo((float) i, 0.0f);
        path.lineTo(((float) i) - f, 0.0f);
        path.lineTo(((float) i) - f, f2);
        path.lineTo((float) i, f2);
        path.lineTo((float) i, 0.0f);
        path.close();
        path.moveTo((float) i, (float) i2);
        path.lineTo(((float) i) - f, (float) i2);
        path.lineTo(((float) i) - f, ((float) i2) - f2);
        path.lineTo((float) i, ((float) i2) - f2);
        path.lineTo((float) i, (float) i2);
        path.close();
        path.moveTo(0.0f, (float) i2);
        path.lineTo(f, (float) i2);
        path.lineTo(f, ((float) i2) - f2);
        path.lineTo(0.0f, ((float) i2) - f2);
        path.lineTo(0.0f, 0.0f);
        path.close();
        canvas.drawPath(path, paint);
        mo13902a(canvas);
        return createBitmap;
    }

    public Bitmap photoE24(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = (((float) i) / ((float) Service1Photo.f6236b)) * ((float) i3);
        float f2 = (((float) i2) / ((float) Service1Photo.f6236b)) * ((float) i3);
        Path path = new Path();
        path.moveTo(((float) i) / 2.0f, ((float) i2) / 2.0f);
        path.lineTo(0.0f, (float) i2);
        path.lineTo(f, (float) i2);
        path.close();
        path.moveTo(((float) i) / 2.0f, ((float) i2) / 2.0f);
        path.lineTo((float) i, (float) i2);
        path.lineTo((float) i, ((float) i2) - f2);
        path.close();
        path.moveTo(((float) i) / 2.0f, ((float) i2) / 2.0f);
        path.lineTo((float) i, 0.0f);
        path.lineTo(((float) i) - f, 0.0f);
        path.close();
        path.moveTo(((float) i) / 2.0f, ((float) i2) / 2.0f);
        path.lineTo(0.0f, 0.0f);
        path.lineTo(0.0f, f2);
        path.close();
        canvas.drawPath(path, paint);
        return createBitmap;
    }

    public Bitmap photoE25(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = (((float) i) / ((float) Service1Photo.f6236b)) * ((float) i3);
        float f2 = (((float) i2) / ((float) Service1Photo.f6236b)) * ((float) i3);
        Path path = new Path();
        path.moveTo(0.0f, f2);
        path.lineTo(f, 0.0f);
        path.lineTo(0.0f, 0.0f);
        path.close();
        path.moveTo(((float) i) - f, (float) i2);
        path.lineTo((float) i, ((float) i2) - f2);
        path.lineTo((float) i, (float) i2);
        path.close();
        canvas.drawPath(path, paint);
        return createBitmap;
    }

    public Bitmap photoE26(int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = (((float) i) / ((float) Service1Photo.f6236b)) * ((float) i3);
        float f2 = (((float) i2) / ((float) Service1Photo.f6236b)) * ((float) i3);
        Path path = new Path();
        path.moveTo(0.0f, ((float) i2) - f2);
        path.lineTo(f, (float) i2);
        path.lineTo(0.0f, (float) i2);
        path.close();
        path.moveTo(((float) i) - f, 0.0f);
        path.lineTo((float) i, f2);
        path.lineTo((float) i, 0.0f);
        path.close();
        canvas.drawPath(path, paint);
        return createBitmap;
    }

    public static Bitmap m8263a(String str) {
        int i = 1;
        int i2 = 0;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // options.inSampleSize = 1;
        BitmapFactory.decodeFile(str, options);

        Bitmap decodeFile = BitmapFactory.decodeFile(str, new BitmapFactory.Options());
        try {
            String attribute = new ExifInterface(str).getAttribute("Orientation");
            if (attribute != null) {
                i = Integer.parseInt(attribute);
            }
            if (i == 6) {
                i2 = 90;
            }
            if (i == 3) {
                i2 = 180;
            }
            if (i == 8) {
                i2 = 270;
            }
            Matrix matrix = new Matrix();
            matrix.setRotate((float) i2, ((float) decodeFile.getWidth()) / 2.0f, ((float) decodeFile.getHeight()) / 2.0f);
            return Bitmap.createBitmap(decodeFile, 0, 0, options.outWidth, options.outHeight, matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap m8260a(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == i && height == i2) {
            return bitmap;
        }
        float max = Math.max(((float) i) / ((float) width), ((float) i2) / ((float) height));
        float f = ((float) width) * max;
        float f2 = ((float) height) * max;
        float f3 = (((float) i) - f) / 2.0f;
        float f4 = (((float) i2) - f2) / 2.0f;
        RectF rectF = new RectF(f3, f4, f + f3, f2 + f4);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, bitmap.getConfig());
        new Canvas(createBitmap).drawBitmap(bitmap, null, rectF, null);
        return createBitmap;
    }

    public void mo13902a(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(50.0f);
        paint.setColor(-65536);
    }

    public static File m8480a(String str, int i) {
        File file = new File(m8479a(str), String.format("IMG_%03d", Integer.valueOf(i)));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File m8479a(String str) {
        File file = new File(f6554c, str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static Bitmap m8262a(Bitmap bitmap, Bitmap bitmap2, int i, int i2, float f, float f2) {
        Bitmap copy = bitmap2.copy(bitmap2.getConfig(), true);
        Canvas canvas = new Canvas(m8254a(copy, 25, true));
        Paint paint = new Paint();
        new Rect(0, 0, i, i2);
        canvas.drawBitmap(m8261a(bitmap, i, i2, f, f2), 0.0f, 0.0f, paint);
        paint.setColor(-1996488705);
        paint.setStrokeWidth(120.0f);
        paint.setTextSize(120.0f);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        return copy;
    }

    private static Bitmap m8261a(Bitmap bitmap, int i, int i2, float f, float f2) {
        float f3;
        float f4 = 0.0f;
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        Canvas canvas = new Canvas(createBitmap);
        float f5 = ((float) i) / width;
        float f6 = ((float) i2) / height;
        float f7 = (((float) i2) - (height * f5)) / 2.0f;
        if (f7 < 0.0f) {
            f5 = ((float) i2) / height;
            f3 = (((float) i) - (width * f6)) / 2.0f;
        } else {
            float f8 = f7;
            f3 = 0.0f;
            f4 = f8;
        }
        Matrix matrix = new Matrix();
        matrix.postTranslate(f3 * f, f4 + f2);
        //   FunctionsPhoto.LogD("translation", "xTranslation :" + f3 + " yTranslation :" + f4);
        matrix.preScale(f5, f5);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, matrix, paint);
        return createBitmap;
    }

    public static Bitmap m8254a(Bitmap bitmap, int i, boolean z) {
        Bitmap copy;
        if (z) {
            copy = bitmap;
        } else {
            copy = bitmap.copy(bitmap.getConfig(), true);
        }
        if (i < 1) {
            return null;
        }
        int width = copy.getWidth();
        int height = copy.getHeight();
        int[] iArr = new int[(width * height)];
        copy.getPixels(iArr, 0, width, 0, 0, width, height);
        int i2 = width - 1;
        int i3 = height - 1;
        int i4 = width * height;
        int i5 = i + i + 1;
        int[] iArr2 = new int[i4];
        int[] iArr3 = new int[i4];
        int[] iArr4 = new int[i4];
        int[] iArr5 = new int[Math.max(width, height)];
        int i6 = (i5 + 1) >> 1;
        int i7 = i6 * i6;
        int[] iArr6 = new int[(i7 * 256)];
        for (int i8 = 0; i8 < i7 * 256; i8++) {
            iArr6[i8] = i8 / i7;
        }
        int[][] iArr7 = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i5, 3});
        int i9 = i + 1;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (true) {
            int i13 = i10;
            if (i13 >= height) {
                break;
            }
            int i14 = 0;
            int i15 = 0;
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            for (int i23 = -i; i23 <= i; i23++) {
                int i24 = iArr[Math.min(i2, Math.max(i23, 0)) + i12];
                int[] iArr8 = iArr7[i23 + i];
                iArr8[0] = (16711680 & i24) >> 16;
                iArr8[1] = (65280 & i24) >> 8;
                iArr8[2] = i24 & 255;
                int abs = i9 - Math.abs(i23);
                i21 += iArr8[0] * abs;
                i20 += iArr8[1] * abs;
                i19 += abs * iArr8[2];
                if (i23 > 0) {
                    i15 += iArr8[0];
                    i22 += iArr8[1];
                    i14 += iArr8[2];
                } else {
                    i18 += iArr8[0];
                    i17 += iArr8[1];
                    i16 += iArr8[2];
                }
            }
            int i25 = i21;
            int i26 = i20;
            int i27 = i19;
            int i28 = i12;
            int i29 = i;
            for (int i30 = 0; i30 < width; i30++) {
                iArr2[i28] = iArr6[i25];
                iArr3[i28] = iArr6[i26];
                iArr4[i28] = iArr6[i27];
                int i31 = i25 - i18;
                int i32 = i26 - i17;
                int i33 = i27 - i16;
                int[] iArr9 = iArr7[((i29 - i) + i5) % i5];
                int i34 = i18 - iArr9[0];
                int i35 = i17 - iArr9[1];
                int i36 = i16 - iArr9[2];
                if (i13 == 0) {
                    iArr5[i30] = Math.min(i30 + i + 1, i2);
                }
                int i37 = iArr[iArr5[i30] + i11];
                iArr9[0] = (16711680 & i37) >> 16;
                iArr9[1] = (65280 & i37) >> 8;
                iArr9[2] = i37 & 255;
                int i38 = i15 + iArr9[0];
                int i39 = i22 + iArr9[1];
                int i40 = i14 + iArr9[2];
                i25 = i31 + i38;
                i26 = i32 + i39;
                i27 = i33 + i40;
                i29 = (i29 + 1) % i5;
                int[] iArr10 = iArr7[i29 % i5];
                i18 = i34 + iArr10[0];
                i17 = i35 + iArr10[1];
                i16 = i36 + iArr10[2];
                i15 = i38 - iArr10[0];
                i22 = i39 - iArr10[1];
                i14 = i40 - iArr10[2];
                i28++;
            }
            i10 = i13 + 1;
            i11 += width;
            i12 = i28;
        }
        for (int i41 = 0; i41 < width; i41++) {
            int i42 = 0;
            int i43 = (-i) * width;
            int i44 = 0;
            int i45 = 0;
            int i46 = 0;
            int i47 = 0;
            int i48 = -i;
            int i49 = 0;
            int i50 = 0;
            int i51 = 0;
            int i52 = 0;
            while (i48 <= i) {
                int max = Math.max(0, i43) + i41;
                int[] iArr11 = iArr7[i48 + i];
                iArr11[0] = iArr2[max];
                iArr11[1] = iArr3[max];
                iArr11[2] = iArr4[max];
                int abs2 = i9 - Math.abs(i48);
                int i53 = (iArr2[max] * abs2) + i51;
                int i54 = (iArr3[max] * abs2) + i50;
                int i55 = (iArr4[max] * abs2) + i49;
                if (i48 > 0) {
                    i44 += iArr11[0];
                    i52 += iArr11[1];
                    i42 += iArr11[2];
                } else {
                    i47 += iArr11[0];
                    i46 += iArr11[1];
                    i45 += iArr11[2];
                }
                if (i48 < i3) {
                    i43 += width;
                }
                i48++;
                i49 = i55;
                i50 = i54;
                i51 = i53;
            }
            int i56 = i50;
            int i57 = i51;
            int i58 = i49;
            int i59 = i41;
            int i60 = i42;
            int i61 = i52;
            int i62 = i44;
            int i63 = i45;
            int i64 = i46;
            int i65 = i47;
            int i66 = i;
            for (int i67 = 0; i67 < height; i67++) {
                iArr[i59] = (-16777216 & iArr[i59]) | (iArr6[i57] << 16) | (iArr6[i56] << 8) | iArr6[i58];
                int i68 = i57 - i65;
                int i69 = i56 - i64;
                int i70 = i58 - i63;
                int[] iArr12 = iArr7[((i66 - i) + i5) % i5];
                int i71 = i65 - iArr12[0];
                int i72 = i64 - iArr12[1];
                int i73 = i63 - iArr12[2];
                if (i41 == 0) {
                    iArr5[i67] = Math.min(i67 + i9, i3) * width;
                }
                int i74 = iArr5[i67] + i41;
                iArr12[0] = iArr2[i74];
                iArr12[1] = iArr3[i74];
                iArr12[2] = iArr4[i74];
                int i75 = i62 + iArr12[0];
                int i76 = i61 + iArr12[1];
                int i77 = i60 + iArr12[2];
                i57 = i68 + i75;
                i56 = i69 + i76;
                i58 = i70 + i77;
                i66 = (i66 + 1) % i5;
                int[] iArr13 = iArr7[i66];
                i65 = i71 + iArr13[0];
                i64 = i72 + iArr13[1];
                i63 = i73 + iArr13[2];
                i62 = i75 - iArr13[0];
                i61 = i76 - iArr13[1];
                i60 = i77 - iArr13[2];
                i59 += width;
            }
        }
        copy.setPixels(iArr, 0, width, 0, 0, width, height);
        return copy;
    }

//    ------------------------- 2d done-----------------------------------

//    ------------------------- 3d effect-----------------------------------

    public int f9034k = Integer.MAX_VALUE;
    public String f9153e;
    public static boolean f9149a = false;
    public static boolean f9027d = false;
    public static File f11764a = new File(Constant.getFolderPath(ApplicationPhoto.getContext()));
    public ArrayList<String> f9036m = new ArrayList<>();
    public static File f11765b = new File(f11764a, getContext().getString(R.string.mainFolder));
    public boolean f9032i = false;
    public static final File f11766c = new File(f11765b, getContext().getString(R.string.temp_folder));
    public static boolean f9026c = false;

    private void apply3dTransition(int position3d) {
        flg_break = false;

        String mainFolder = getString(R.string.mainFolder);
        String subFolder = getString(R.string.temp_folder);
        String effectFolder = getString(R.string.effect_folder);

        File mainFile = new File(Constant.getFolderPath(ApplicationPhoto.getContext()) + "/" + mainFolder);
        File subFile = new File(mainFile, subFolder);
        File effectFile = new File(subFile, effectFolder);

        if (effectFile.exists())
            deleteDir(effectFile);


        boolean z;
        String str;

        this.f9151c = f6295d;

        f9149a = false;
        int i = 0;
        int i2 = 0;

        if (squer.equalsIgnoreCase("horizontal")) {
            i = 720;
            i2 = 480;
            f9025b = 720;
            f9024a = 480;
        } else if (squer.equalsIgnoreCase("vertical")) {
            i = 720;
            i2 = 1280;
            f9025b = 720;
            f9024a = 1280;
        } else if (squer.equalsIgnoreCase("square")) {
            i = videoQuality;
            i2 = videoQuality;
            f9025b = videoQuality;
            f9024a = videoQuality;
        }

        System.currentTimeMillis();
        this.f9151c.size();
        Bitmap bitmap = null;
        int i3 = 0;

        loop0:
        while (true) {

            char c = 0;
            int i4 = 1;
            if (i3 >= this.f9151c.size()) {
                break;
            }
            File a = m12044a(getContext().getString(R.string.effect_folder), i3);

            if (multieffct) {
                if (clickPositionTheme == 27) {
                    position3d = ramdom3dNumbers[new Random().nextInt(ramdom3dNumbers.length)];

                    LogE("position3d", String.valueOf(position3d));
                }
            }
            if (i3 == 0) {
                Bitmap a2 = Service2Photo.checkBitmap((this.f6295d.get(i3)).getImagePath());
                Bitmap a3 = Service2Photo.scaleCenterCrop(a2, i, i2);
                bitmap = Service2Photo.m6618a(a2, a3, i, i2, 1.0f, 0.0f);
                a3.recycle();
                a2.recycle();
                System.gc();
                LogE("pathimg", "---1--->");
            } else if (bitmap == null || bitmap.isRecycled()) {

                Bitmap a4 = Service2Photo.checkBitmap((this.f6295d.get(i3)).getImagePath());
                Bitmap a5 = Service2Photo.scaleCenterCrop(a4, i, i2);
                bitmap = Service2Photo.m6618a(a4, a5, i, i2, 1.0f, 0.0f);
                a5.recycle();
                a4.recycle();
                LogE("pathimg", "---2--->");
            }
            Bitmap bitmap2 = bitmap;
            if (flg_break) {

                break;
            }

            StringBuilder a6 = m44a("");
            a6.append(f9027d);
            LogE("isBreak_servicess  333", a6.toString());
            Bitmap a7 = Service2Photo.checkBitmap((this.f6295d.get(i3)).getImagePath());
            LogE("pathimg", "---3--->");
            if (flg_break) {

                break;
            }


            Bitmap a8 = Service2Photo.scaleCenterCrop(a7, i, i2);
            bitmap = Service2Photo.m6618a(a7, a8, i, i2, 1.0f, 0.0f);
            a8.recycle();
            a7.recycle();
            System.gc();
            if (flg_break) {

                break;
            }
            m12636a();


            switch (position3d) {
                case 27:
                    photo3d_0402();
                    break;
                case 28:
                    photo3d_0102(bitmap2, bitmap);
                    break;
                case 29:
                    photo3d_0202(bitmap2, bitmap);
                    break;
                case 30:
                    photo3d_0302();
                    break;
                case 31:
                    photo3d_0402();
                    break;
                case 32:
                    photo3d_0502();
                    break;
                case 33:
                    photo3d_0602();
                    break;
                case 34:
                    photo3d_0702(bitmap2, bitmap);
                    break;
                case 35:
                    photo3d_0802(bitmap2, bitmap);
                    break;
                case 36:
                    photo3d_0902(bitmap2, bitmap);
                    break;
                case 37:
                    photo3d_1002(bitmap2, bitmap);
                    break;
                case 38:
                    photo3d_1102(bitmap2, bitmap);
                    break;
                case 39:
                    photo3d_1202(bitmap2, bitmap);
                    break;
                case 40:
                    photo3d_1302(bitmap2, bitmap);
                    break;
                case 41:
                    photo3d_1402(bitmap2, bitmap);
                    break;
                case 42:
                    photo3d_1502();
                    break;
                case 43:
                    photo3d_1602();
                    break;
                case 44:
                    photo3d_1702();
                    break;
                case 45:
                    photo3d_1802();
                    break;
            }


            int i5 = 0;
            int i6 = i3;
            while (true) {


                float f = (float) i5;
                if (f >= Service3Photo.f12402b) {
                    break;
                }
                str = "MultiImage";
                if (!mo7388a() || flg_break) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.f9153e);
                    sb.append(" break");
                    LogE(str, sb.toString());

                    break;
                } else {

                    if (flg_break) {

                        break;
                    }

                    Bitmap a9 = null;
                    if (position3d == 27) {
                        a9 = photo3d_0401(bitmap2, bitmap, i5);
                    } else if (position3d == 28) {
                        a9 = photo3d_0101(i5);
                    } else if (position3d == 29) {
                        a9 = photo3d_0201(i5);
                    } else if (position3d == 30) {
                        a9 = photo3d_0301(bitmap2, bitmap, i5);
                    } else if (position3d == 31) {
                        a9 = photo3d_0401(bitmap2, bitmap, i5);
                    } else if (position3d == 32) {
                        a9 = photo3d_0501(bitmap2, bitmap, i5);
                    } else if (position3d == 33) {
                        a9 = photo3d_0601(bitmap2, bitmap, i5);
                    } else if (position3d == 34) {
                        a9 = photo3d_0701(i5);
                    } else if (position3d == 35) {
                        a9 = photo3d_0801(i5);
                    } else if (position3d == 36) {
                        a9 = photo3d_0901(i5);
                    } else if (position3d == 37) {
                        a9 = photo3d_1001(i5);
                    } else if (position3d == 38) {
                        a9 = photo3d_1101(i5);
                    } else if (position3d == 39) {
                        a9 = photo3d_1201(i5);
                    } else if (position3d == 40) {
                        a9 = photo3d_1301(i5);
                    } else if (position3d == 41) {
                        a9 = photo3d_1401(i5);
                    } else if (position3d == 42) {
                        a9 = photo3d_1501(bitmap2, bitmap, i5);
                    } else if (position3d == 43) {
                        a9 = photo3d_1601(bitmap2, bitmap, i5);
                    } else if (position3d == 44) {
                        a9 = photo3d_1701(bitmap2, bitmap, i5);
                    } else if (position3d == 45) {
                        a9 = photo3d_1801(bitmap2, bitmap, i5);
                    }

                    if (flg_break) {
                        break;
                    }

                    if (mo7388a()) {
                        Object[] objArr = new Object[i4];
                        objArr[c] = Integer.valueOf(i5);
                        File file = new File(a, String.format("img%02d.jpg", objArr));

                        try {
                            if (file.exists()) {
                                file.delete();
                            }
                            if (flg_break) {

                                break;
                            }
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                a9.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                fileOutputStream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        boolean z2 = false;

                        while (this.f9032i) {
                            LogE(str, "application.isEditModeEnable :");

                            int i7 = this.f9034k;
                            if (i7 != Integer.MAX_VALUE) {
                                z2 = true;
                                i6 = i7;
                            }
                            if (flg_break) {
                                LogE(str, "application.isEditModeEnable Break:");
                                z = true;

                                break loop0;
                            }
                        }

                        if (z2) {
                            ArrayList arrayList = new ArrayList();
                            arrayList.addAll(this.f9036m);
                            this.f9036m.clear();
                            int min = Math.min(arrayList.size(), Math.max(0, i6 - i6) * 30);
                            int i8 = 0;

                            while (i8 < min) {
                                ArrayList arrayList2 = arrayList;
                                this.f9036m.add(f6295d.get(i8).getImagePath());
                                i8++;
                                arrayList = arrayList2;
                            }
                            this.f9034k = Integer.MAX_VALUE;
                        }

                        if (!mo7388a() || f9026c) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(this.f9153e);
                            sb2.append(" :");

                        } else {
                            this.f9036m.add(file.getAbsolutePath());
                            if (f == Service3Photo.f12402b - 1.0f) {
                                for (int i9 = 0; i9 < 8 && mo7388a() && !f9026c; i9++) {
                                    this.f9036m.add(file.getAbsolutePath());
                                }
                            }
                            i5++;
                            c = 0;
                            i4 = 1;
                        }
                        ImageModelPhoto imageData = new ImageModelPhoto();
                        imageData.setEffectImagePath(file.getAbsolutePath());
                        effectImages.add(imageData);

                    }
                }
            }
            StringBuilder sb22 = new StringBuilder();
            sb22.append(this.f9153e);
            sb22.append(" :");


            i3 = i6 + 1;


        }


        z = true;

        f9149a = z;


        if (flg_break) {
            flg_break_stop = false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lin_process.setVisibility(View.VISIBLE);
                    ImageCreate();
                }
            });

        } else {
            flg_break_stop = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    lin_process.setVisibility(View.GONE);
                }
            });
        }

    }

    public static File m12044a(String str, int i) {
        File file = new File(m12043a(str), String.format("IMG_%03d", i));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File m12043a(String str) {
        File file = new File(f11766c, str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static StringBuilder m44a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    public final boolean mo7388a() {
        return true;
    }

    public Bitmap photo3d_0101(int i) {
        Service3Photo.m12637a910(Service3Photo.f12403c - i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12642c(new Canvas(createBitmap));
        return createBitmap;
    }

    public void photo3d_0102(Bitmap bitmap, Bitmap bitmap2) {
        Service3Photo.f12414n = 8;
        Service3Photo.f12404d = 1;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
        Service3Photo.m12639a910(bitmap2, bitmap);
    }

    public Bitmap photo3d_0201(int i) {
        Service3Photo.m12637a910(i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12642c(new Canvas(createBitmap));
        return createBitmap;
    }

    public void photo3d_0202(Bitmap bitmap, Bitmap bitmap2) {
        Service3Photo.f12414n = 8;
        Service3Photo.f12404d = 0;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
        Service3Photo.m12639a910(bitmap, bitmap2);
    }

    public Bitmap photo3d_0301(Bitmap bitmap, Bitmap bitmap2, int i) {
        Service3Photo.m12637a(Service3Photo.f12403c - i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Service3Photo.m12638a(bitmap2, bitmap, canvas, false);
        return createBitmap;
    }

    public void photo3d_0302() {
        Service3Photo.f12404d = 1;
        Service3Photo.f12414n = 8;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
    }

    public Bitmap photo3d_0401(Bitmap bitmap, Bitmap bitmap2, int i) {
        Service3Photo.m12637a(i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Service3Photo.m12638a(bitmap, bitmap2, canvas, false);
        return createBitmap;
    }

    public void photo3d_0402() {
        Service3Photo.f12414n = 8;
        Service3Photo.f12404d = 1;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
    }

    public Bitmap photo3d_0501(Bitmap bitmap, Bitmap bitmap2, int i) {
        Service3Photo.m12637a(i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Service3Photo.m12638a(bitmap, bitmap2, canvas, false);
        return createBitmap;
    }

    public void photo3d_0502() {
        Service3Photo.f12414n = 1;
        Service3Photo.f12404d = 8;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
    }

    public Bitmap photo3d_0601(Bitmap bitmap, Bitmap bitmap2, int i) {
        Service3Photo.m12637a(Service3Photo.f12403c - i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Service3Photo.m12638a(bitmap2, bitmap, canvas, false);
        return createBitmap;
    }

    public void photo3d_0602() {
        Service3Photo.f12414n = 8;
        Service3Photo.f12404d = 0;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
    }

    public Bitmap photo3d_0701(int i) {
        // Service3Photo.f12405e = MultipleActivityPhoto.this;
        Service3Photo.m12637a(Service3Photo.f12403c - i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12640a(new Canvas(createBitmap));
        return createBitmap;
    }

    public void photo3d_0702(Bitmap bitmap, Bitmap bitmap2) {
        Service3Photo.f12414n = 4;
        //  Service3Photo.f12405e = this;
        Service3Photo.f12404d = 1;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
        Service3Photo.m12639a(bitmap2, bitmap);
    }

    public Bitmap photo3d_0801(int i) {

        Service3Photo.m12637a(i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12640a(new Canvas(createBitmap));
        return createBitmap;
    }

    public void photo3d_0802(Bitmap bitmap, Bitmap bitmap2) {
        Service3Photo.f12414n = 4;
        Service3Photo.f12404d = 1;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
        Service3Photo.m12639a(bitmap, bitmap2);
    }

    public Bitmap photo3d_0901(int i) {

        Service3Photo.m12637a(i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12640a(new Canvas(createBitmap));
        return createBitmap;
    }

    public void photo3d_0902(Bitmap bitmap, Bitmap bitmap2) {
        Service3Photo.f12414n = 4;
        Service3Photo.f12404d = 0;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
        Service3Photo.m12639a(bitmap, bitmap2);
    }

    public Bitmap photo3d_1001(int i) {
        // Service3Photo.f12405e = MultipleActivityPhoto.this;
        Service3Photo.m12637a(Service3Photo.f12403c - i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12640a(new Canvas(createBitmap));
        return createBitmap;
    }

    public void photo3d_1002(Bitmap bitmap, Bitmap bitmap2) {
        Service3Photo.f12414n = 4;
        //  Service3Photo.f12405e = this;
        Service3Photo.f12404d = 0;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
        Service3Photo.m12639a(bitmap2, bitmap);
    }

    public Bitmap photo3d_1101(int i) {
        Service3Photo.m12637a_11_12_13_14(Service3Photo.f12403c - i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12641b(new Canvas(createBitmap));
        return createBitmap;
    }

    public void photo3d_1102(Bitmap bitmap, Bitmap bitmap2) {
        Service3Photo.f12414n = 8;
        Service3Photo.f12404d = 1;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
        Service3Photo.m12639a(bitmap2, bitmap);
    }

    public Bitmap photo3d_1201(int i) {
        Service3Photo.m12637a_11_12_13_14(i);


        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12641b(new Canvas(createBitmap));
        return createBitmap;
    }

    public void photo3d_1202(Bitmap bitmap, Bitmap bitmap2) {
        Service3Photo.f12414n = 8;
        Service3Photo.f12404d = 1;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
        Service3Photo.m12639a(bitmap, bitmap2);
    }

    public Bitmap photo3d_1301(int i) {
        Service3Photo.m12637a_11_12_13_14(i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12641b(new Canvas(createBitmap));
        return createBitmap;
    }

    public void photo3d_1302(Bitmap bitmap, Bitmap bitmap2) {
        Service3Photo.f12414n = 8;
        Service3Photo.f12404d = 0;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
        Service3Photo.m12639a(bitmap, bitmap2);
    }

    public Bitmap photo3d_1401(int i) {
        Service3Photo.m12637a_11_12_13_14(Service3Photo.f12403c - i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12641b(new Canvas(createBitmap));
        return createBitmap;
    }

    public void photo3d_1402(Bitmap bitmap, Bitmap bitmap2) {
        Service3Photo.f12414n = 8;
        Service3Photo.f12404d = 0;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
        Service3Photo.m12639a(bitmap2, bitmap);
    }

    public Bitmap photo3d_1501(Bitmap bitmap, Bitmap bitmap2, int i) {
        Service3Photo.m12637a(Service3Photo.f12403c - i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12638a(bitmap2, bitmap, new Canvas(createBitmap), true);
        return createBitmap;
    }

    public void photo3d_1502() {
        Service3Photo.f12414n = 8;
        Service3Photo.f12404d = 1;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
    }

    public Bitmap photo3d_1601(Bitmap bitmap, Bitmap bitmap2, int i) {
        Service3Photo.m12637a(i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12638a(bitmap, bitmap2, new Canvas(createBitmap), true);
        return createBitmap;
    }

    public void photo3d_1602() {
        Service3Photo.f12414n = 8;
        Service3Photo.f12404d = 1;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
    }

    public Bitmap photo3d_1701(Bitmap bitmap, Bitmap bitmap2, int i) {
        Service3Photo.m12637a(i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12638a(bitmap, bitmap2, new Canvas(createBitmap), true);
        return createBitmap;
    }

    public void photo3d_1702() {
        Service3Photo.f12414n = 1;
        Service3Photo.f12404d = 8;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
    }

    public Bitmap photo3d_1801(Bitmap bitmap, Bitmap bitmap2, int i) {
        Service3Photo.m12637a(Service3Photo.f12403c - i);
        Bitmap createBitmap = Bitmap.createBitmap(f9025b, f9024a, Bitmap.Config.ARGB_8888);
        Service3Photo.m12638a(bitmap2, bitmap, new Canvas(createBitmap), true);
        return createBitmap;
    }

    public void photo3d_1802() {
        Service3Photo.f12414n = 1;
        Service3Photo.f12404d = 8;
        Service3Photo.f12412l = new Camera();
        Service3Photo.f12413m = new Matrix();
    }

//    ------------------------- 3d done-----------------------------------

    private void setFrame(Bitmap bitmap) {
        if (bitmap == null) {
            mIvFrame.setImageBitmap(null);
            return;
        }
        //this.newbmp_framee = bitmap;
        setbitmap_method(bitmap);
    }

    private void setbitmap_method(Bitmap bitmap) {
        if (bitmap != null) {
            if (squer.equalsIgnoreCase("horizontal")) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 720, 480, false);
            } else if (squer.equalsIgnoreCase("vertical")) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 720, 1280, false);
            } else if (squer.equalsIgnoreCase("square")) {
                bitmap = Bitmap.createScaledBitmap(bitmap, videoQuality, videoQuality, false);
            }

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
                case 1001:
                    getMedia(LlpMultipleActivity.this);
                    break;
            }
        }
    }

    private void setLayoutParams() {
        try {
            ViewTreeObserver vto = img_preview.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {

                    img_preview.getViewTreeObserver().removeOnPreDrawListener(this);

                    int imgFinalWidth = img_preview.getMeasuredWidth();
                    int imgFinalHeight = img_preview.getMeasuredHeight();

                    LogE("onPreDraw: ", "imgFinalWidth => " + imgFinalWidth);

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

                    f9024a = videoQuality;
                    f9025b = videoQuality;

                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void comman_color() {
        method_edit_formate();
        img_AddMusic.setColorFilter(getResources().getColor(R.color.colorWhite));
        txt_AddMusic.setTextColor(getResources().getColor(R.color.colorWhite));

        img_Formate.setColorFilter(getResources().getColor(R.color.colorWhite));
        txt_Formate.setTextColor(getResources().getColor(R.color.colorWhite));
        lin_formate_item.setVisibility(View.GONE);

        img_Effect.setColorFilter(getResources().getColor(R.color.colorWhite));
        txt_Effect.setTextColor(getResources().getColor(R.color.colorWhite));
        lin_effct_item.setVisibility(View.GONE);

        img_Style.setColorFilter(getResources().getColor(R.color.colorWhite));
        txt_Style.setTextColor(getResources().getColor(R.color.colorWhite));
        lin_styletheme_item.setVisibility(View.GONE);

        img_Framerecycle.setColorFilter(getResources().getColor(R.color.colorWhite));
        txt_Framerecycle.setTextColor(getResources().getColor(R.color.colorWhite));
        lin_frame_item.setVisibility(View.GONE);

    }

    private void method_colcor_formate() {
        defalt_landscape.setColorFilter(getResources().getColor(R.color.colorBlack));
        defalt_portrait.setColorFilter(getResources().getColor(R.color.colorBlack));
        defalt_square.setColorFilter(getResources().getColor(R.color.colorBlack));
        txt_vertical.setTextColor(getResources().getColor(R.color.edit_txt_clr));
        txt_square.setTextColor(getResources().getColor(R.color.edit_txt_clr));
        txt_widescrn.setTextColor(getResources().getColor(R.color.edit_txt_clr));
    }

    private void method_edit_formate() {
        recycle_strickers.setVisibility(View.GONE);
        img_addText.setColorFilter(getResources().getColor(R.color.colorWhite));
        imd_addStrickers.setColorFilter(getResources().getColor(R.color.colorWhite));
        txt_addText.setTextColor(getResources().getColor(R.color.colorWhite));
        txt_addStrickers.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    private void setUpThemeAdapter() {

        themeSelectModels = new ArrayList<TransitionModelPhoto>();
        themeSelectModels.clear();
        for (int i = 0; i < Constant.IMAGE.length; i++) {
            TransitionModelPhoto effectModel = new TransitionModelPhoto();
            themeSelectModels.add(effectModel);
        }

        themeAdapter = new ThemeAdapterAnimation(themeSelectModels, this);
        recycle_style.setLayoutManager(new GridLayoutManager((Context) this, 1, RecyclerView.HORIZONTAL, false));
        recycle_style.setItemAnimator(new DefaultItemAnimator());
        recycle_style.setAdapter(themeAdapter);
        themeAdapter.setSelected(0);

        recycle_style.addOnItemTouchListener(
                new RecyclerItemClickListenerPhoto(getApplicationContext(), new RecyclerItemClickListenerPhoto.OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            if (flg_break_stop) {
                                themeAdapter.setSelected(position);
                                if (position == 0 || position == 27) {
                                    multieffct = true;
                                    clickPositionTheme = position;
                                } else {
                                    multieffct = false;
                                    clickPositionTheme = position;
                                }
                                flg_break_stop = false;
                                ImageCreate();

                            } else {
                                themeAdapter.setSelected(position);
                                if (position == 0 || position == 27) {
                                    multieffct = true;
                                    clickPositionTheme = position;
                                } else {
                                    multieffct = false;
                                    clickPositionTheme = position;
                                }
                                flg_break = true;
                            }
                            //    FunctionsPhoto.LogW("clickPositionTheme", String.valueOf(position));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );

        durationSelectModels = new ArrayList<TransitionModelPhoto>();
        durationSelectModels.clear();
        for (int i = 0; i < Constant.Duration.length; i++) {
            TransitionModelPhoto effectModel = new TransitionModelPhoto();
            durationSelectModels.add(effectModel);
        }

        FrameSelectModels = new ArrayList<TransitionModelPhoto>();
        FrameSelectModels.clear();
        Log.e("TAG", "Frame Length: " + Constant.Frames.length);
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
                            Bitmap bitmap = getImageFromAssetsFile(LlpMultipleActivity.this, listFrame[position]);
                            frameAdapter.setSelected(position);
                            if (position == 0) {
                                setFrame(null);
                            } else {
                                setFrame(bitmap);
                            }

                            //    FunctionsPhoto.LogW("pos", String.valueOf(position));
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
            ArrayList<File> files = new ArrayList<>();
            listFile = effectFile.listFiles();
            if (listFile != null && listFile.length > 0) {

                for (int i = 0; i < listFile.length; i++) {
                    ArrayList<String> data = new Gson().fromJson(PowerPreference.getDefaultFile().getString("mEffect", new Gson().toJson(new ArrayList<String>())), new TypeToken<ArrayList<String>>() {
                    }.getType());
                    if (data.contains(listFile[i].getAbsolutePath())) {
                        Log.e("TAG", path + "]n" + listFile[i].getAbsolutePath());
                        if (path.equalsIgnoreCase("")) {
                            pos = 1;
                        } else if (path.equalsIgnoreCase(listFile[i].getAbsolutePath())) {
                            pos = files.size();
                        }
                        files.add(listFile[i]);
                    }
                }
                // FunctionsPhoto.LogW("log", "log 3" + listFile[i].getAbsolutePath());

                effectAdapter = new EffectAdapterAnimation(files, this, pos);
                recycle_Effect.setLayoutManager(new GridLayoutManager((Context) this, 1, RecyclerView.HORIZONTAL, false));
                recycle_Effect.setItemAnimator(new DefaultItemAnimator());
                recycle_Effect.setAdapter(effectAdapter);


                if (!isPlayPause) {
                    if (currentIndex > endIndex) {
                        currentIndex = 0;
                    } else {
                        img_PlayPause.setVisibility(View.GONE);
                        FunctionsPhoto.LogW("SavedImage", "PlayPause");
                        playMusic();
                        nextImage();
                    }
                    isPlayPause = true;
                }

                effectAdapter.setOnItemClickListener(new EffectAdapterAnimation.setOnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        try {
//                            clickPositionEffect = position;

                            if (!isPlayPause) {
                                if (currentIndex > endIndex) {
                                    currentIndex = 0;
                                } else {
                                    img_PlayPause.setVisibility(View.GONE);
                                    FunctionsPhoto.LogW("SavedImage", "PlayPause");
                                    playMusic();
                                    nextImage();
                                }
                                isPlayPause = true;
                            }

                            String effectpath22 = files.get(position).getAbsolutePath();
                            String fileName = effectpath22.substring(effectpath22.lastIndexOf('/') + 1, effectpath22.length());
//                            effectpath = listFile[position].getAbsolutePath() + "/" + fileName;
                            selEffect = fileName;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                FunctionsPhoto.LogW("log", "log 2");


            } else {

                FunctionsPhoto.LogW("log", "log 4");
            }
        } else {

            FunctionsPhoto.LogW("log", "log 5");
        }


    }

    private void loadSticker() {
        try {
            listStickers = getAssets().list("strickers11");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (listStickers != null) {
            List arrayList = new ArrayList();
            for (String str : listStickers) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("strickers11/");
                stringBuilder.append(str);
                arrayList.add(stringBuilder.toString());
            }
            listStickers = (String[]) arrayList.toArray(new String[arrayList.size()]);
            galleryAdapter = new StickersAdapterAnimation(listStickers, this);
            recycle_strickers.setHasFixedSize(true);
            recycle_strickers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            recycle_strickers.setAdapter(galleryAdapter);

            recycle_strickers.addOnItemTouchListener(
                    new RecyclerItemClickListenerPhoto(getApplicationContext(), new RecyclerItemClickListenerPhoto.OnItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onItemClick(View view, int position) {

                            Bitmap bitmap = getImageFromAssetsFile(LlpMultipleActivity.this, listStickers[position]);
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
        //new  File(getString(R.string.mainFolder),"temp").mkdirs();

        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".png");
        return uriSting;
    }

    private void createVideoFile() {

//        dilog_show();

        String mainFolder = getString(R.string.mainFolder);
        String subFolder = getString(R.string.temp_folder);
        String effectFolder = getString(R.string.effect_folder);

        File mainFile = new File(Constant.getFolderPath(this) + "/" + getResources().getString(R.string.mainFolder));
        if (!mainFile.exists())
            mainFile.mkdir();

        File subFile = new File(mainFile, subFolder);
        File effectFile = new File(subFile, effectFolder);


        File[] listFileSub2;
        listFileSub2 = effectFile.listFiles();
        Collections.sort(Arrays.asList(listFileSub2));
        LogE("ffmpeg_cmd", "---2--->" + listFileSub2.length);


        try {
            File root = new File(Constant.getFolderPath(this) + "/" + getResources().getString(R.string.mainFolder));
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, subFolder);
            if (gpxfile.exists()) {
                gpxfile.mkdir();

            }
            File txtFile = new File(gpxfile, "cn1.txt");
            if (txtFile.exists()) {
                txtFile.delete();
            }
            FileWriter writer = new FileWriter(txtFile);


            for (int i = 0; i < listFileSub2.length; i++) {

                writer.append("file '" + listFileSub2[i].getAbsolutePath() + "/img%02d.jpg'\n");

            }
            writer.flush();
            writer.close();

            createVideo(txtFile.getAbsolutePath());


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void createVideo(String txtFilePath) {
        imgPath = saveImage();
        int getdur = (effectImages.size() / speed) + 1;

        String mainpath = Constant.getFolderPath(this) + File.separator + getString(R.string.mainFolder);
        File file = new File(mainpath, getString(R.string.temp_folder));
        if (!file.exists()) {
            file.mkdirs();
        }

        // ---------- final video---------

        File dCimFile = new File(Constant.getOutputPath(this));

        if (!dCimFile.exists()) {
            dCimFile.mkdir();
        }

        File subFile = new File(dCimFile, getResources().getString(R.string.my_creation));
        if (!subFile.exists())
            subFile.mkdir();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String name = "Video_";
        String fileVExtn = ".mp4";

        File nameVFile = new File(subFile, name + timeStamp + fileVExtn);

        final String filePath = nameVFile.getAbsolutePath();

        // ---------- final video done---------

        // ---------- temp audio ---------

        File tAfile = new File(mainpath, getString(R.string.temp_audio_folder));
        if (!tAfile.exists()) {
            tAfile.mkdirs();
        }
        File AnameFile = new File(tAfile, "temp.mp3");

        // ---------- temp audio done---------
        // ---------- user audio---------

        String namemusic = "Video_music";
        String fileExtn = ".mp3";
        File nameFile = new File(file, namemusic + fileExtn);
        // ---------- user audio done---------
        // ---------- effect path---------

        String effectPath = effectpath
                + File.separator + fileName + "_%03d.png";
        // ---------- effect path done---------

        final StringBuilder cmdString = new StringBuilder();
        cmdString.append("-y ");
        cmdString.append("-r 9");
        cmdString.append(" -f ");
        cmdString.append("concat ");
        cmdString.append("-safe ");
        cmdString.append("0 ");
        cmdString.append("-i ");
        cmdString.append(txtFilePath);
        cmdString.append(" -loop 1 -r 9 -i ");
        cmdString.append(effectPath);
        cmdString.append(" -loop 1 -i ");
        cmdString.append(imgPath);
        String filePatha;
        if (nameFile.exists()) {
            filePatha = nameFile.getAbsolutePath();
        } else {
            filePatha = AnameFile.getAbsolutePath();
        }
        cmdString.append(" -filter_complex amovie=" + filePatha + ":loop=0,asetpts=N/SR/TB[a];");
        cmdString.append("[0:v]scale=" + videoQuality + ":" + videoQuality + "[simage];" +
                "[1:v]scale=" + videoQuality + ":" + videoQuality + "[seffect];" +
                "[2:v]scale=" + videoQuality + ":" + videoQuality + "[sframe];");

        cmdString.append("[simage][seffect]overlay=0:0[a1];[a1][sframe]overlay=0:0");
        cmdString.append(" -strict ");
        cmdString.append("experimental ");
        cmdString.append("-map 0:v -map [a] -c:v copy -c:a aac");
        cmdString.append(" -t ");
        cmdString.append(getdur);
        cmdString.append(" -c:v ");
        cmdString.append("libx264 ");
        cmdString.append("-preset ultrafast ");
        cmdString.append("-pix_fmt ");
        cmdString.append("yuv420p ");
        cmdString.append(filePath);

        final long duration = getdur * 1000000;

        new InterAds().showInterAds(LlpMultipleActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(LlpMultipleActivity.this, LlpProgressActivity.class);
                intent.putExtra("cmdString", cmdString.toString());
                intent.putExtra("filePath", filePath);
                intent.putExtra("duration", duration);
                intent.putExtra("result", 1);
                startActivity(intent);
                finish();
            }
        });
    }

    public void dilog_show() {
        try {

            if (dialog != null && dialog.isShowing())
                dialog.dismiss();

            dialog = new Dialog(LlpMultipleActivity.this);
            dialog.setContentView(R.layout.custom_dialog_save_video_photo);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

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
                        new LargeNativeAds().showNativeAds(LlpMultipleActivity.this, dialog);
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

}
