package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.BuildConfig;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.LargeNativeAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.ModelMusicListModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.MusicFileStoreFoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.onClickAudio;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ApplicationPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.UtilsFoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.RewardedAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.api.LlpApiClient;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityMusicPhotoBinding;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.encrypt.DecryptEncrypt;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.Audio;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.AudioList;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.preference.PowerPreference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

import VideoHandle.EpEditor;
import VideoHandle.EpVideo;
import VideoHandle.OnEditorListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.apptik.widget.MultiSlider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LlpMusicActivity extends AppCompatActivity implements onClickAudio, View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.btnOnlineMusic)
    Button btnOnlineMusic;
    @BindView(R.id.btn_music)
    Button btn_music;
    @BindView(R.id.btn_filemanager)
    Button btn_filemanager;
    @BindView(R.id.online_rv_music)
    RecyclerView online_rv_music;
    @BindView(R.id.tv_retry_data_online)
    Button tv_retry_data_online;
    @BindView(R.id.tv_no_data_online)
    TextView tv_no_data_online;
    @BindView(R.id.online_music_progress)
    ProgressBar online_music_progress;
    @BindView(R.id.relative_music_music)
    RelativeLayout relative_music_music;
    @BindView(R.id.music_lstReview)
    RecyclerView music_lstReview;
    @BindView(R.id.tv_no_data_music)
    TextView tv_no_data_music;
    @BindView(R.id.music_internl_rel)
    RelativeLayout music_internl_rel;
    @BindView(R.id.root_lstReview)
    RecyclerView root_lstReview;
    @BindView(R.id.tv_root)
    LinearLayout tv_root;
    @BindView(R.id.ll_listview_top)
    RecyclerView ll_listview_top;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;
    @BindView(R.id.file_manger_rel)
    RelativeLayout file_manger_rel;
    @BindView(R.id.range_slider5)
    MultiSlider range_slider5;
    @BindView(R.id.left_pointer_music)
    TextView left_pointer_music;
    @BindView(R.id.right_pointer_music)
    TextView right_pointer_music;
    @BindView(R.id.card_add_music)
    LinearLayout card_add_music;


    private ArrayList<ModelMusicListModelPhoto> getvideo_list = new ArrayList();
    private ArrayList<MusicFileStoreFoto> musicFileMDS = new ArrayList();
    private ArrayList<Audio> onlineAudioMDS = new ArrayList();

    public static MediaPlayer mp;
    private String filepath;
    public static GridAdapter gridAdapter;
    public static OnlineMusicAdp onlineMusicAdp;
    public static AudioFolderAdapter audioFolderAdapter;
    public static int mStartMs, mEndMs;
    private Runnable r;
    private Handler mHandler = new Handler();
    private int mProgressStatus;
    int filzero;
    Dialog dialog;
    int VERSION = 0;
    RewardedVideoAd mRewardedVideoAd;
    boolean isRewarded = false;
    public long WAITING_TIME = 4000;
    public long mAdStartSec = 500;
    public long mAdWaitSec = WAITING_TIME;
    public Runnable mRunnable;
    public static String Music_Audio_Url, Music_Save_Name;
    public static int Online_pos_old = -1;
    public static int pos_music_grid = -1;
    public static int folder_pos_old = -1;
    public static int DownOnline_pos_old = 0;
    File Audiofile;
ActivityMusicPhotoBinding binding;

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
        binding=ActivityMusicPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);

        filzero = 1;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getLoaderManager().initLoader(15, null, this);
/*
        sendRewardRequest();
*/
        getId();
    }

    private void getId() {
        root_lstReview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        online_music_progress.setVisibility(View.VISIBLE);

        txtTitle.setText("Music List");

        ivBack.setOnClickListener(this);
        btn_music.setOnClickListener(this);
        btnOnlineMusic.setOnClickListener(this);
        btn_filemanager.setOnClickListener(this);
        tv_root.setOnClickListener(this);
        card_add_music.setOnClickListener(this);
tv_retry_data_online.setOnClickListener(this);
        getFst_Folder("/storage/emulated/0");
        UtilsFoto.loadNativeAd(this);
        change_btn_color(btnOnlineMusic);
        startSplash();
    }

    public void startSplash() {
        PackageManager manager = getPackageManager();
        PackageInfo info = null;

        try {
            info = manager.getPackageInfo(getPackageName(), 0);
            VERSION = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Constant.Log(e.toString());
            VERSION = BuildConfig.VERSION_CODE;
        }

        getMusic();
    }
    private void getMusic(){
        @SuppressLint("HardwareIds") String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        JsonObject object = new JsonObject();
        object.addProperty("VersionCode", VERSION);
        object.addProperty("PkgName", getPackageName());
        object.addProperty("AndroidId", deviceId);

        LlpApiClient.getInstance().getApi2().getMusic(object).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("data", response.body()+" hel;l");

                final AudioList appData = new GsonBuilder().create().fromJson((DecryptEncrypt.DecryptStr(response.body())),  AudioList.class);
                Log.d("appdata", DecryptEncrypt.DecryptStr(response.body()));
                Log.e("appdata", appData.getAudio().get(1).getAudioName() );
                try{
                    if (appData.getAudio().size() >= 0) {
                            online_rv_music.setVisibility(View.VISIBLE);
                            tv_no_data_online.setVisibility(View.GONE);
                            tv_retry_data_online.setVisibility(View.GONE);
                            online_rv_music.setLayoutManager(new LinearLayoutManager(LlpMusicActivity.this));
                            for (int i = 0; i < appData.getAudio().size(); i++) {
                                onlineAudioMDS.add(appData.getAudio().get(i));
                            }

                        Log.e("appdata", onlineAudioMDS.toString() );
                            onlineMusicAdp = new OnlineMusicAdp(onlineAudioMDS);
                            online_rv_music.setAdapter(onlineMusicAdp);

                        } else {
                            online_rv_music.setVisibility(View.GONE);
                            tv_no_data_online.setVisibility(View.VISIBLE);
                            tv_retry_data_online.setVisibility(View.VISIBLE);
                        }
                        online_music_progress.setVisibility(View.GONE);


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("appdata", "Faild: ",e );
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
               Log.e("TAG","faild",t);
                UtilsFoto.LogE("Error: ", "Error: " + t.getMessage());
                online_music_progress.setVisibility(View.GONE);
                online_rv_music.setVisibility(View.GONE);
                tv_no_data_online.setVisibility(View.VISIBLE);
                tv_retry_data_online.setVisibility(View.VISIBLE);
                if (t instanceof TimeoutError || t instanceof NoConnectionError)
                    Toast.makeText(LlpMusicActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFst_Folder(String str) {
        File dir = new File(str);
        if (dir.exists()) {
            musicFileMDS.clear();
            if (dir.listFiles() != null) {
                for (File f : dir.listFiles()) {
                    if (f.getName().endsWith(".mp3") || f.getName().endsWith(".MP3") || f.isDirectory()) {
                        if (!f.getName().startsWith(".")) {
                            MusicFileStoreFoto objfileitem = new MusicFileStoreFoto();
                            objfileitem.setFileName(f.getAbsolutePath());
                            musicFileMDS.add(objfileitem);

                            ll_listview_top.setLayoutManager(new LinearLayoutManager(this));
                            audioFolderAdapter = new AudioFolderAdapter(musicFileMDS);
                            tv_no_data.setVisibility(View.GONE);
                            ll_listview_top.setVisibility(View.VISIBLE);
                            ll_listview_top.setAdapter(audioFolderAdapter);
                            audioFolderAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;

            case R.id.btn_music:
                change_btn_color(btn_music);
                file_manger_rel.setVisibility(View.GONE);
                relative_music_music.setVisibility(View.GONE);
                music_internl_rel.setVisibility(View.VISIBLE);
                break;

            case R.id.btnOnlineMusic:
                change_btn_color(btnOnlineMusic);
                file_manger_rel.setVisibility(View.GONE);
                music_internl_rel.setVisibility(View.GONE);
                relative_music_music.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_filemanager:
                change_btn_color(btn_filemanager);
                file_manger_rel.setVisibility(View.VISIBLE);
                music_internl_rel.setVisibility(View.GONE);
                relative_music_music.setVisibility(View.GONE);
                break;
            case R.id.tv_root:
                getFst_Folder("/storage/emulated/0");
                break;
            case R.id.tv_retry_data_online:
                online_music_progress.setVisibility(View.VISIBLE);
                tv_no_data_online.setVisibility(View.GONE);
                tv_retry_data_online.setVisibility(View.GONE);
                startSplash();
                break;
            case R.id.card_add_music:

                if (mp != null) {
                    mp.pause();
                    mp.release();
                    mp = null;
                }

                if (filepath != null) {
                    if (filzero == 0) {
                        Toast.makeText(LlpMusicActivity.this, "Please Select Correct Audio File", Toast.LENGTH_SHORT).show();
                    } else {
                        if (filepath.endsWith(".mp3")) {
                            executeAudio(filepath);
                        } else {
                            Toast.makeText(LlpMusicActivity.this, "Please Select only mp3 Audio file", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LlpMusicActivity.this, "Please Select Audio File", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    private void executeAudio(String path) {

        String mainFolder = getString(R.string.mainFolder);
        File mainFile = new File(Constant.getFolderPath(ApplicationPhoto.getContext()) + "/" + mainFolder);
        if (!mainFile.exists())
            mainFile.mkdir();

        File subFile = new File(mainFile, getString(R.string.temp_folder));
        if (!subFile.exists())
            subFile.mkdir();

        String name = "Video_music";
        String fileExtn = ".mp3";

        File nameFile = new File(subFile, name + fileExtn);

        if (nameFile.exists()) {
            if (nameFile.delete()) {
                UtilsFoto.LogE("TrimeActivity", "file delete");

            } else {
                UtilsFoto.LogE("TrimeActivity", "file not Deleted ");
            }
            UtilsFoto.LogE("TrimeActivity", "exit");
        } else {
            UtilsFoto.LogE("TrimeActivity", "not exit");
        }

        final String filePath = nameFile.getAbsolutePath();

        EpVideo epVideo = new EpVideo(path);
        epVideo.clip((mStartMs / 1000), (mEndMs - mStartMs) / 1000);
        EpEditor.OutputOption outputOption = new EpEditor.OutputOption(filePath);

        EpEditor.exec(epVideo, outputOption, new OnEditorListener() {
            @Override
            public void onSuccess() {

                UtilsFoto.LogE("TrimeActivity", "TrimeActivity***************************  onSuccess   1");
                runOnUiThread(() -> {
                    UtilsFoto.LogE("TrimeActivity", "TrimeActivity***************************  onSuccess   2");
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    setResult(RESULT_OK);
                    finish();
                });
            }

            @Override
            public void onFailure() {
                UtilsFoto.LogE("TrimeActivity", "TrimeActivity***************************  onFailure   1");
            }

            @Override
            public void onProgress(final float progress) {
                UtilsFoto.LogE("TrimeActivity", "TrimeActivity***********---------------------------------------onProgress");
            }
        });
    }

    private void change_btn_color(Button chngbg) {

        btn_music.setBackground(getResources().getDrawable(R.drawable.btn_music_unselect_photo));
        btn_music.setTextColor(getResources().getColor(R.color.colorBlack));
        btnOnlineMusic.setBackground(getResources().getDrawable(R.drawable.btn_music_unselect_photo));
        btnOnlineMusic.setTextColor(getResources().getColor(R.color.colorBlack));
        btn_filemanager.setBackground(getResources().getDrawable(R.drawable.btn_music_unselect_photo));
        btn_filemanager.setTextColor(getResources().getColor(R.color.colorBlack));

        chngbg.setBackground(getResources().getDrawable(R.drawable.btn_music_select_anlm));
        chngbg.setTextColor(getResources().getColor(R.color.colorWhite));

    }

    @Override
    public void onBackPressed() {

        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                if (mp != null) {
                    mp.pause();
                    mp.release();
                    mp = null;
                }
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        if (mp != null) {
            mp.pause();
        }
        super.onPause();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.MediaColumns.DATE_ADDED);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        try {
            getvideo_list.clear();
            cursor.moveToFirst();
            do {
                try {
                    if (!(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)).endsWith(".mp3") && cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)).endsWith(".MP3"))) {
                        ModelMusicListModelPhoto musicList = new ModelMusicListModelPhoto();
                        musicList.setFilepath(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
                        musicList.setFilesize(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
                        getvideo_list.add(musicList);
                    }
                } catch (Exception loader2) {
                    loader2.printStackTrace();
                }
            } while (cursor.moveToNext());

            music_lstReview.setLayoutManager(new LinearLayoutManager(this));
            gridAdapter = new GridAdapter(getvideo_list);
            music_lstReview.setAdapter(gridAdapter);

            if (getvideo_list.size() > 0) {
                music_lstReview.setVisibility(View.VISIBLE);
                tv_no_data_music.setVisibility(View.GONE);
            } else {
                music_lstReview.setVisibility(View.GONE);
                tv_no_data_music.setVisibility(View.VISIBLE);
            }
        } catch (Exception loader22) {
            loader22.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onclick(String str, int i, int i2) {
        filepath = str;

        if (i2 == 0) {
            new_method_play(str);
        } else if (i2 == 1) {
            performVideoViewClick_music();
        }
    }

    private void new_method_play(String str) {

        try {
            if (mp != null) {
                mp.reset();

            }
            mp = MediaPlayer.create(getApplicationContext(), Uri.parse(str));
            if (mp != null) {
                mp.setLooping(true);
            }
            mp.start();
            UtilsFoto.LogE("pathh", "--3--");

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(String.valueOf(Uri.parse(str)));
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long timeInmillisec = Long.parseLong(time);
            final long duration = timeInmillisec / 1000;

            left_pointer_music.setText("00:00:00");
            right_pointer_music.setText(getTime((int) duration));

            range_slider5.setMin(0);
            range_slider5.setMax((int) duration);
            range_slider5.setEnabled(true);

            mStartMs = 0;
            mEndMs = 0;
            filzero = 1;
            mp.setOnPreparedListener(mp -> {
                range_slider5.setOnThumbValueChangeListener(new MultiSlider.SimpleChangeListener() {
                    @Override
                    public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int
                            thumbIndex, int value) {
                        try {
                            if (thumbIndex == 0) {
                                left_pointer_music.setText(getTime(value));
                                mStartMs = value * 1000;
                                long exoplyStrt = (int) value * 1000;
                                mp.seekTo((int) exoplyStrt);
                            } else {
                                right_pointer_music.setText(getTime(value));
                                mEndMs = value * 1000;
                            }
                        } catch (Exception e) {
                        }
                    }
                });
                range_slider5.repositionThumbs();

                final Handler handler = new Handler();
                handler.postDelayed(r = () -> {
                    try {
                        if (mEndMs != 0) {

                            if (mp.getCurrentPosition() >= mEndMs)
                                mp.seekTo(mStartMs);


                            handler.postDelayed(r, 1000);
                        } else {
                            mEndMs = (int) (duration * 1000);
                            if (mp.getCurrentPosition() >= mEndMs)
                                mp.seekTo(mStartMs);

                            handler.postDelayed(r, 1000);
                        }

                    } catch (Exception e) {
                        UtilsFoto.LogE("fmp", "startTrim----00---: : " + e.getMessage());

                    }
                }, 1000);
            });
        } catch (Exception e) {
            UtilsFoto.LogE("path", "----" + e.getMessage());
            left_pointer_music.setText("00:00");
            right_pointer_music.setText("00:00");
            filzero = 0;
            range_slider5.setMin(0);
            range_slider5.setMax(0);
            range_slider5.setEnabled(false);
        }
    }

    public static String getTime(int seconds) {
        int hr = seconds / 3600;
        int rem = seconds % 3600;
        int mn = rem / 60;
        int sec = rem % 60;
        return String.format("%02d", mn) + ":" + String.format("%02d", sec);
    }

    private void performVideoViewClick_music() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.pause();
                return;
            }
            mp.start();
        }
    }

    // adpter
    public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {
        private ArrayList<ModelMusicListModelPhoto> arrayList;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView iv_music_pause;
            ImageView iv_play;
            LottieAnimationView img_song_ply;
            LinearLayout rl_main;
            TextView tv_musicduaration;
            TextView tv_musicname;

            public MyViewHolder(View view) {
                super(view);
                rl_main = (LinearLayout) view.findViewById(R.id.rl_main);
                iv_play = (ImageView) view.findViewById(R.id.iv_play);
                img_song_ply = view.findViewById(R.id.img_song_ply);
                iv_music_pause = (ImageView) view.findViewById(R.id.iv_music_pause);
                tv_musicname = (TextView) view.findViewById(R.id.tv_musicname);
                tv_musicduaration = (TextView) view.findViewById(R.id.tv_musicduaration);
            }
        }

        public GridAdapter(ArrayList<ModelMusicListModelPhoto> arrayList) {
            this.arrayList = arrayList;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_items_audio_list_photo, viewGroup, false));
        }

        @RequiresApi(api = 16)
        public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {

            myViewHolder.tv_musicname.setText(new File(this.arrayList.get(i).getFilepath()).getName());

            try {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(this.arrayList.get(i).getFilepath());
                String stringForTime = stringForTime(Integer.parseInt(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));
                File file = new File(this.arrayList.get(i).getFilepath());

                double bytes = file.length();
                double kilobytes = (bytes / 1024);
                double megabytes = (kilobytes / 1024);
                myViewHolder.tv_musicduaration.setText(stringForTime + " | " + String.format("%.2f", megabytes) + " MB");

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (pos_music_grid != i) {
                    UtilsFoto.LogE("onBindView", "---" + pos_music_grid + " != " + i);
                    myViewHolder.iv_play.setVisibility(View.GONE);
                    myViewHolder.img_song_ply.setVisibility(View.GONE);
                    myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorBlack));
                    myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorBlack));
                    myViewHolder.iv_music_pause.setVisibility(View.VISIBLE);
                } else if (mp != null) {
                    UtilsFoto.LogE("onBindView", "--mp---");

                    myViewHolder.iv_play.setVisibility(View.GONE);
                    myViewHolder.img_song_ply.setVisibility(View.VISIBLE);
                    myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorPrimary));
                    myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorPrimary));
                    myViewHolder.iv_music_pause.setVisibility(View.GONE);
                } else {
                    UtilsFoto.LogE("onBindView", "--els---");
                    myViewHolder.iv_music_pause.setVisibility(View.VISIBLE);
                    myViewHolder.iv_play.setVisibility(View.GONE);
                    myViewHolder.img_song_ply.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(e2);
                UtilsFoto.LogE("onBindView", stringBuilder.toString());
            }
            myViewHolder.img_song_ply.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (pos_music_grid == i) {
                        if (mp != null) {
                            if (mp.isPlaying()) {

                                myViewHolder.iv_music_pause.setVisibility(View.VISIBLE);
                                myViewHolder.iv_play.setVisibility(View.GONE);
                                myViewHolder.img_song_ply.setVisibility(View.GONE);
                                myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorBlack));
                                myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorBlack));
                            } else {
                                myViewHolder.iv_music_pause.setVisibility(View.GONE);
                                myViewHolder.iv_play.setVisibility(View.GONE);
                                myViewHolder.img_song_ply.setVisibility(View.VISIBLE);
                                myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorPrimary));
                                myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorPrimary));
                            }
                        }
                        onclick(new File(GridAdapter.this.arrayList.get(i).getFilepath()).getAbsolutePath(), i, 1);
                    }
                }
            });
            myViewHolder.iv_music_pause.setOnClickListener(view -> {
                pos_music_grid = i;
                Online_pos_old = -1;
                folder_pos_old = -1;
                resetdata();

                myViewHolder.iv_music_pause.setVisibility(View.GONE);
                myViewHolder.iv_play.setVisibility(View.GONE);
                myViewHolder.img_song_ply.setVisibility(View.VISIBLE);
                myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorBlack));
                myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorBlack));
                onclick(new File(GridAdapter.this.arrayList.get(i).getFilepath()).getAbsolutePath(), i, 0);

            });

            myViewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    pos_music_grid = i;
                    Online_pos_old = -1;
                    folder_pos_old = -1;
                    resetdata();

                    myViewHolder.iv_music_pause.setVisibility(View.GONE);
                    myViewHolder.iv_play.setVisibility(View.GONE);
                    myViewHolder.img_song_ply.setVisibility(View.VISIBLE);
                    myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorPrimary));
                    myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorPrimary));
                    onclick(new File(GridAdapter.this.arrayList.get(i).getFilepath()).getAbsolutePath(), i, 0);

                }
            });
        }

        public int getItemCount() {
            return arrayList.size();
        }
    }

    private void resetdata() {

        if (getvideo_list.size() > 0) {
            gridAdapter.notifyDataSetChanged();
        }
        if (onlineAudioMDS.size() > 0) {
            onlineMusicAdp.notifyDataSetChanged();
        }
        if (musicFileMDS.size() > 0) {
            audioFolderAdapter.notifyDataSetChanged();
        }
    }

    public class AudioFolderAdapter extends RecyclerView.Adapter<AudioFolderAdapter.MyViewHolder> {
        private ArrayList<MusicFileStoreFoto> musicFileMDS;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView img_foldr, img_pus;
            LinearLayout clik_folder_lin;
            TextView txt_foldrnm;
            LottieAnimationView img_foldersong_ply;

            public MyViewHolder(View view) {
                super(view);

                img_foldr = view.findViewById(R.id.img_foldr);
                txt_foldrnm = view.findViewById(R.id.txt_foldrnm);
                clik_folder_lin = view.findViewById(R.id.clik_folder_lin);
                img_pus = view.findViewById(R.id.img_pus);
                img_foldersong_ply = view.findViewById(R.id.img_foldersong_ply);

            }
        }

        public AudioFolderAdapter(ArrayList<MusicFileStoreFoto> musicFileMDS) {
            this.musicFileMDS = musicFileMDS;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_items_audio_folder_photo, viewGroup, false));
        }

        @RequiresApi(api = 16)
        public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {

            final String folder_nm = new File(musicFileMDS.get(i).getFileName()).getName();
            myViewHolder.txt_foldrnm.setText(folder_nm);
            if (folder_nm.endsWith(".mp3") || folder_nm.endsWith(".MP3")) {
                myViewHolder.img_foldr.setImageResource(R.drawable.ic_music_play_photo);

                try {
                    if (folder_pos_old != i) {
                        myViewHolder.img_pus.setVisibility(View.GONE);
                        myViewHolder.img_foldersong_ply.setVisibility(View.GONE);
                        myViewHolder.img_foldr.setVisibility(View.VISIBLE);
                        UtilsFoto.LogE("img", "-4-");
                    } else if (mp != null) {
                        myViewHolder.img_pus.setVisibility(View.VISIBLE);
                        myViewHolder.img_foldersong_ply.setVisibility(View.VISIBLE);
                        myViewHolder.img_foldr.setVisibility(View.GONE);
                        UtilsFoto.LogE("img", "-5-");
                    } else {
                        myViewHolder.img_foldr.setVisibility(View.VISIBLE);
                        UtilsFoto.LogE("img", "-6-");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(e2);
                }
            } else {
                myViewHolder.img_foldr.setImageResource(R.drawable.ic_folder_photo);
            }

            myViewHolder.clik_folder_lin.setOnClickListener(v -> {
                if (folder_nm.endsWith(".mp3") || folder_nm.endsWith(".MP3")) {
                    folder_pos_old = i;
                    pos_music_grid = -1;
                    Online_pos_old = -1;
                    resetdata();
                    onclick(new File(AudioFolderAdapter.this.musicFileMDS.get(i).getFileName()).getAbsolutePath(), i, 0);
                    UtilsFoto.LogE("img", "-44-");

                } else {
                    File sdCardRoot = new File(new File((musicFileMDS.get(i)).getFileName()).getAbsolutePath());
                    File dir = new File(sdCardRoot.getAbsolutePath());
                    if (dir.exists()) {
                        musicFileMDS.clear();
                        if (dir.listFiles() != null) {
                            UtilsFoto.LogE("song", "-5-");

                            if (dir.listFiles().length > 0) {
                                UtilsFoto.LogE("song", "-55-");
                                // UtilsFoto.LogE("song", "-55-" + dir.listFiles().length);
                                for (File f : dir.listFiles()) {
                                    if (f.getName().endsWith(".mp3") || f.getName().endsWith(".MP3") || f.isDirectory()) {
                                        UtilsFoto.LogE("song", "-7-");
                                        if (!f.getName().startsWith(".")) {
                                            UtilsFoto.LogE("song", "-8-");

                                            MusicFileStoreFoto objfileitem = new MusicFileStoreFoto();
                                            objfileitem.setFileName(f.getAbsolutePath());
                                            musicFileMDS.add(objfileitem);
                                            AudioFolderAdapter.this.notifyDataSetChanged();
                                        }

                                        tv_no_data.setVisibility(View.GONE);
                                        ll_listview_top.setVisibility(View.VISIBLE);

                                    } else {

                                        UtilsFoto.LogE("song", "-555  5-");
                                        tv_no_data.setVisibility(View.VISIBLE);
                                        ll_listview_top.setVisibility(View.GONE);
                                    }

                                    if (musicFileMDS.size() > 0) {
                                        tv_no_data.setVisibility(View.GONE);
                                        ll_listview_top.setVisibility(View.VISIBLE);
                                    } else {
                                        tv_no_data.setVisibility(View.VISIBLE);
                                        ll_listview_top.setVisibility(View.GONE);
                                    }
                                }
                            } else {
                                tv_no_data.setVisibility(View.VISIBLE);
                                ll_listview_top.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(LlpMusicActivity.this, "No Data 3", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LlpMusicActivity.this, "No Data 2", Toast.LENGTH_SHORT).show();
                    }
                    AudioFolderAdapter.this.notifyDataSetChanged();
                }
            });

            myViewHolder.img_pus.setOnClickListener(v -> {

                if (folder_pos_old == i) {
                    if (mp != null) {
                        if (mp.isPlaying()) {
                            myViewHolder.img_foldr.setVisibility(View.VISIBLE);
                            myViewHolder.img_pus.setVisibility(View.GONE);
                            myViewHolder.img_foldersong_ply.setVisibility(View.GONE);
                            UtilsFoto.LogE("img", "-7-");
                        } else {
                            myViewHolder.img_foldr.setVisibility(View.GONE);
                            myViewHolder.img_pus.setVisibility(View.VISIBLE);
                            myViewHolder.img_foldersong_ply.setVisibility(View.VISIBLE);
                            UtilsFoto.LogE("img", "-8-");
                        }
                    }
                    onclick(new File(AudioFolderAdapter.this.musicFileMDS.get(i).getFileName()).getAbsolutePath(), i, 1);
                }
            });
        }

        public int getItemCount() {
            return musicFileMDS.size();
        }
    }

    public class OnlineMusicAdp extends RecyclerView.Adapter<OnlineMusicAdp.MyViewHolder> {
        private ArrayList<Audio> arrayList;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView iv_music_pause;
            ImageView iv_music_download;
            ImageView iv_play;
            LottieAnimationView img_song_ply;
            LinearLayout rl_main;
            TextView tv_musicduaration;
            TextView tv_musicname;

            public MyViewHolder(View view) {
                super(view);
                rl_main = view.findViewById(R.id.rl_main);
                iv_play = view.findViewById(R.id.iv_play);
                img_song_ply = view.findViewById(R.id.img_song_ply);
                iv_music_pause = view.findViewById(R.id.iv_music_pause);
                iv_music_download = view.findViewById(R.id.iv_music_download);

                tv_musicname = view.findViewById(R.id.tv_musicname);
                tv_musicduaration = view.findViewById(R.id.tv_musicduaration);
            }
        }

        public OnlineMusicAdp(ArrayList<Audio> arrayList) {
            this.arrayList = arrayList;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_items_audio_list_photo, viewGroup, false));
        }

        @RequiresApi(api = 16)
        public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {

            myViewHolder.tv_musicname.setText(arrayList.get(i).getAudioName());
            myViewHolder.tv_musicduaration.setText(arrayList.get(i).getAudioDuration());
            try {
                String mainFolder = getString(R.string.mainFolder);
                String audio_Download_folder = getString(R.string.audio_Download_folder);
                Music_Save_Name = arrayList.get(i).getFileName();
                if (Online_pos_old != i) {
                    final File mainFile = new File(getFilesDir().getAbsolutePath() + "/" + mainFolder + "/" + audio_Download_folder);

                    Audiofile = new File(mainFile, Music_Save_Name + ".mp3");
                    if (Audiofile.exists()) {
                        myViewHolder.iv_play.setVisibility(View.GONE);
                        myViewHolder.img_song_ply.setVisibility(View.GONE);
                        myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorBlack));
                        myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorBlack));
                        myViewHolder.iv_music_pause.setVisibility(View.VISIBLE);
                        myViewHolder.iv_music_download.setVisibility(View.GONE);

                    } else {
                        myViewHolder.iv_play.setVisibility(View.GONE);
                        myViewHolder.img_song_ply.setVisibility(View.GONE);
                        myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorBlack));
                        myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorBlack));
                        myViewHolder.iv_music_pause.setVisibility(View.GONE);
                        myViewHolder.iv_music_download.setVisibility(View.VISIBLE);

                    }

                } else if (mp != null) {
                    myViewHolder.iv_play.setVisibility(View.GONE);
                    myViewHolder.img_song_ply.setVisibility(View.VISIBLE);
                    myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorPrimary));
                    myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorPrimary));
                    myViewHolder.iv_music_pause.setVisibility(View.GONE);
                    myViewHolder.iv_music_download.setVisibility(View.GONE);

                } else {

                    myViewHolder.iv_music_pause.setVisibility(View.VISIBLE);
                }

            } catch (Exception e2) {
                e2.printStackTrace();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(e2);
                //    UtilsFoto.LogE("onBindView", stringBuilder.toString());
            }

            String mainFolder = getString(R.string.mainFolder);
            String audio_Download_folder = getString(R.string.audio_Download_folder);
            final File mainFile = new File(getFilesDir().getAbsolutePath() + "/" + mainFolder + "/" + audio_Download_folder);


            myViewHolder.img_song_ply.setOnClickListener(view -> {
                if (Online_pos_old == i) {

                    Music_Audio_Url = arrayList.get(i).getAudioUrl();
                    Music_Save_Name = arrayList.get(i).getFileName();
                    Audiofile = new File(mainFile, Music_Save_Name + ".mp3");

                    if (Audiofile.exists()) {
                        if (mp != null) {
                            if (mp.isPlaying()) {
                                myViewHolder.iv_music_pause.setVisibility(View.VISIBLE);
                                myViewHolder.iv_play.setVisibility(View.GONE);
                                myViewHolder.img_song_ply.setVisibility(View.GONE);
                                myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorBlack));
                                myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorBlack));
                            } else {
                                myViewHolder.iv_music_pause.setVisibility(View.GONE);
                                myViewHolder.iv_play.setVisibility(View.GONE);
                                myViewHolder.img_song_ply.setVisibility(View.VISIBLE);
                                myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorPrimary));
                                myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorPrimary));
                            }
                        }
                        onclick(Audiofile.getAbsolutePath(), i, 1);
                    } else {

                        if (mp != null) {
                            if (mp.isPlaying()) {
                                mp.pause();
                            }
                        }
                        Primium_dilog_show();
                    }

                }
            });

            myViewHolder.iv_music_download.setOnClickListener(view -> {

                Music_Audio_Url = arrayList.get(i).getAudioUrl();
                Music_Save_Name = arrayList.get(i).getFileName();
                Audiofile = new File(mainFile, Music_Save_Name + ".mp3");

                if (Audiofile.exists()) {
                    Online_pos_old = i;

                    pos_music_grid = -1;
                    folder_pos_old = -1;
                    resetdata();

                    myViewHolder.iv_music_pause.setVisibility(View.GONE);
                    myViewHolder.iv_play.setVisibility(View.GONE);
                    myViewHolder.img_song_ply.setVisibility(View.VISIBLE);
                    myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorPrimary));
                    myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorPrimary));
                    onclick(Audiofile.getAbsolutePath(), i, 0);

                } else {

                    DownOnline_pos_old = i;
                    if (mp != null) {
                        if (mp.isPlaying()) {
                            mp.pause();
                        }
                    }
                    Primium_dilog_show();
                }
            });
            myViewHolder.iv_music_pause.setOnClickListener(view -> {
                UtilsFoto.LogE("durat", "----10----");

                Music_Audio_Url = arrayList.get(i).getAudioUrl();
                Music_Save_Name = arrayList.get(i).getFileName();
                Audiofile = new File(mainFile, Music_Save_Name + ".mp3");

                if (Audiofile.exists()) {
                    UtilsFoto.LogE("durat", "----11----");
                    Online_pos_old = i;

                    pos_music_grid = -1;
                    folder_pos_old = -1;
                    resetdata();
                    myViewHolder.iv_music_pause.setVisibility(View.GONE);
                    myViewHolder.iv_play.setVisibility(View.GONE);
                    myViewHolder.img_song_ply.setVisibility(View.VISIBLE);
                    myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorPrimary));
                    myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorPrimary));
                    onclick(Audiofile.getAbsolutePath(), i, 0);

                } else {
                    UtilsFoto.LogE("durat", "----12----");
                    DownOnline_pos_old = i;
                    if (mp != null) {
                        if (mp.isPlaying()) {
                            mp.pause();
                        }
                    }

                    Primium_dilog_show();
                }
            });
            myViewHolder.rl_main.setOnClickListener(view -> {
                UtilsFoto.LogE("durat", "----10----");

                Music_Audio_Url = arrayList.get(i).getAudioUrl();
                Music_Save_Name = arrayList.get(i).getFileName();
                Audiofile = new File(mainFile, Music_Save_Name + ".mp3");

                if (Audiofile.exists()) {
                    UtilsFoto.LogE("durat", "----11----");
                    Online_pos_old = i;

                    pos_music_grid = -1;
                    folder_pos_old = -1;
                    resetdata();
                    myViewHolder.iv_music_pause.setVisibility(View.GONE);
                    myViewHolder.iv_play.setVisibility(View.GONE);
                    myViewHolder.img_song_ply.setVisibility(View.VISIBLE);
                    myViewHolder.tv_musicname.setTextColor(getResources().getColor(R.color.colorPrimary));
                    myViewHolder.tv_musicduaration.setTextColor(getResources().getColor(R.color.colorPrimary));
                    onclick(Audiofile.getAbsolutePath(), i, 0);

                } else {
                    UtilsFoto.LogE("durat", "----12----");
                    DownOnline_pos_old = i;
                    if (mp != null) {
                        if (mp.isPlaying()) {
                            mp.pause();
                        }
                    }

                    Primium_dilog_show();
                }
            });
        }

        public int getItemCount() {
            return arrayList.size();
        }
    }

    // download audio using url

    private class DownloadMusic extends AsyncTask<String, String, String> {

        private String fileName;
        private String folder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            UtilsFoto.dilogShow(LlpMusicActivity.this);
            TextView wait = UtilsFoto.dialog.findViewById(R.id.wait);
            wait.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int lengthOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                fileName = Music_Save_Name + ".mp3";

                String mainFolder = getString(R.string.mainFolder);
                String audioFolder = getString(R.string.audio_Download_folder);

                folder = getFilesDir().toString() + File.separator + mainFolder + File.separator + audioFolder + File.separator;

                File directory = new File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
                }
                OutputStream output = new FileOutputStream(folder + fileName);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    //  UtilsFoto.LogD("down", "Progress: " + (int) ((total * 100) / lengthOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
                return "" + folder + fileName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Something went wrong";
        }


        protected void onProgressUpdate(String... progress) {

            TextView txtvlu = UtilsFoto.dialog.findViewById(R.id.txtvlu);
            txtvlu.setText("Downloading " + Integer.parseInt(progress[0]) + " %");
        }

        @Override
        protected void onPostExecute(String message) {
            Online_pos_old = DownOnline_pos_old;
            UtilsFoto.dilogHide(LlpMusicActivity.this);
            pos_music_grid = -1;
            folder_pos_old = -1;
            resetdata();
            onclick(message, 1, 0);
        }
    }

    @Override
    protected void onDestroy() {
        if (mp != null) {
            mp.pause();
            mp.release();
            mp = null;
        }
        super.onDestroy();
    }

    public static String stringForTime(int i) {
        if (i > 0) {
            if (i < 86400000) {
                i /= 1000;
                int i2 = i % 60;
                int i3 = (i / 60) % 60;
                i /= 3600;
                Formatter formatter = new Formatter(new StringBuilder(), Locale.getDefault());
                if (i > 0) {
                    return formatter.format("%d:%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i3), Integer.valueOf(i2)}).toString();
                }
                return formatter.format("%02d:%02d", new Object[]{Integer.valueOf(i3), Integer.valueOf(i2)}).toString();
            }
        }
        return "00:00";
    }

    public void Primium_dilog_show() {


        try {

            if (dialog != null && dialog.isShowing())
                dialog.dismiss();

            dialog = new Dialog(LlpMusicActivity.this);
            dialog.setContentView(R.layout.custom_dialog_ads_watch_photo);
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

            final TextView title = dialog.findViewById(R.id.title);
            final TextView txtDesc = dialog.findViewById(R.id.txtDesc);
            final AppCompatButton btnWatch = dialog.findViewById(R.id.btnWatch);
            final ImageView imgclose = dialog.findViewById(R.id.pclose);
            FrameLayout nativeframe=dialog.findViewById(R.id.nativeAd);
            TextView adspace=dialog.findViewById(R.id.ad_space);
            title.setText("Premium Music");
            txtDesc.setText("Please watch full video ad to use this music");



            btnWatch.setOnClickListener(view -> {
                dilogHide();
//                ShowRewardAds();

                new RewardedAds().showRewardAds(LlpMusicActivity.this, new RewardedAds.OnAdClosedListener() {
                    @Override
                    public void onAdClosed(boolean isRewarded, boolean isRewardVideo) {
                        if (isRewarded) {
                            new DownloadMusic().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Music_Audio_Url);
                        }
                    }

                    @Override
                    public void onAdFailed() {
                        new DownloadMusic().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Music_Audio_Url);
                    }

                    @Override
                    public void onAdTimer() {
                        new DownloadMusic().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Music_Audio_Url);
                    }
                });
            });

            imgclose.setOnClickListener(view -> {
                dialog.dismiss();
                if (mp != null) {
                    mp.start();
                }
            });

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    if(PowerPreference.getDefaultFile().getBoolean(Constant.LoaderNativeOnOff,false)){
                        new LargeNativeAds().showNativeAds(LlpMusicActivity.this, dialog);
                    }else {
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


}
