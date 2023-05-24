package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.LargeNativeAds;
import com.preference.PowerPreference;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityProgressPhotoBinding;

import java.io.File;

import VideoHandle.EpEditor;
import VideoHandle.OnEditorListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LlpProgressActivity extends AppCompatActivity {

    @BindView(R.id.txtProgress)
    TextView txtProgress;

ActivityProgressPhotoBinding binding;
    String cmdString, filePath;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private long duration;
    private int result;
    int mprogress;
    private Handler mHandler = new Handler();
    private int mProgressStatus;

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
        binding=ActivityProgressPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);

        Intent i = getIntent();
        cmdString = i.getStringExtra("cmdString");
        filePath = i.getStringExtra("filePath");
        duration = i.getLongExtra("duration", 0);
        result = i.getIntExtra("result", 0);


        ivBack.setVisibility(View.GONE);
        txtTitle.setText("Video Progress");

        if (result == 0) {
            singleImage();
        } else if (result == 1) {
            multiImage();
        } else if (result == 2) {
            videoImage();
        }

        mProgressStatus = 0;
        progressBar.setProgress(mProgressStatus);

      /*  new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... params) {
                while (mProgressStatus < 100) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mProgressStatus +=mprogress;
                    mHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(mProgressStatus);
                        }
                    });
                }
                return null;
            }

        }.execute();*/




    }


    private void singleImage() {

        FunctionsPhoto.LogW("ProgressActivityPhoto", "onSuccess()   ==> 2");
        EpEditor.execCmd(cmdString, duration, new OnEditorListener() {
            @Override
            public void onSuccess() {

                runOnUiThread(() -> {
                    openActivity();
                    String outputVideoPath = Constant.getOutputPath(LlpProgressActivity.this) + File.separator + getString(R.string.mainFolder);
                    File file = new File(outputVideoPath, getString(R.string.temp_folder));
                    if (file.exists()) {
                        deleteDir(file);
                    }
                });
                FunctionsPhoto.LogW("ffmpeg", "onSuccess()   ==> ");
            }

            @Override
            public void onFailure() {
                runOnUiThread(() -> FunctionsPhoto.LogW("ffmpeg", "onFailure()   ==> "));
            }

            @Override
            public void onProgress(final float progress) {

                runOnUiThread(() -> {
                    int percent = (int) (progress * 100);
                    if (0 <= percent && percent <= 100) {
                        txtProgress.setText(percent + " %");
                        mprogress= percent;
                        progressBar.setProgress(mprogress);

                    } else
                        FunctionsPhoto.LogW("ffmpeg", "onProgress() ********************" + percent + "********************");
                });
            }
        });
    }

    private void multiImage() {
        EpEditor.execCmd(cmdString, duration, new OnEditorListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    openActivity();
                    String outputVideoPath = Constant.getOutputPath(LlpProgressActivity.this) + File.separator + getString(R.string.mainFolder);
                    File file = new File(outputVideoPath, getString(R.string.temp_folder));
                    if (file.exists()) {
                        deleteDir(file);
                    }
                });
                FunctionsPhoto.LogW("ffmpeg", "onSuccess()   ==> ");
            }

            @Override
            public void onFailure() {
                runOnUiThread(() -> FunctionsPhoto.LogW("ffmpeg", "onFailure()   ==> "));
            }

            @Override
            public void onProgress(final float progresss) {
                runOnUiThread(() -> {
                    int percent = (int) (progresss * 100);
                    if (0 <= percent && percent <= 100) {
                        txtProgress.setText(percent + " %");
                        mprogress= percent;
                        progressBar.setProgress(mprogress);

                    } else
                        FunctionsPhoto.LogW("ffmpeg", "onProgress() ********************" + percent + "********************");
                });
            }
        });
    }

    private void videoImage() {
        EpEditor.execCmd(cmdString, duration * 1000000, new OnEditorListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {

                    openActivity();

                    String outputVideoPath = Constant.getOutputPath(LlpProgressActivity.this) + File.separator + getString(R.string.mainFolder);
                    File file = new File(outputVideoPath, getString(R.string.temp_video_folder));
                    if (file.exists()) {
                        deleteDir(file);
                    }
                });
                FunctionsPhoto.LogW("ffmpeg", "onSuccess()   ==> ");
            }

            @Override
            public void onFailure() {
                runOnUiThread(() -> FunctionsPhoto.LogW("ffmpeg", "onFailure()   ==> "));
            }

            @Override
            public void onProgress(final float progress) {
                runOnUiThread(() -> {
                    int percent = (int) (progress * 100);
                    if (0 <= percent && percent <= 100) {
                        txtProgress.setText(percent + " %");
                        mprogress= percent;
                        progressBar.setProgress(mprogress);
                    } else
                        FunctionsPhoto.LogW("ffmpeg", "onProgress() ********************" + percent + "********************");
                });
            }
        });
    }

    private void openActivity() {
        new InterAds().showInterAds(LlpProgressActivity.this, new InterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
            Intent intent = new Intent(LlpProgressActivity.this, LlpSaveActivity.class);
            intent.putExtra("finlpath", filePath);
            intent.putExtra("onBack", true);
            startActivity(intent);
            finish();
            }
        });
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
    public void onBackPressed() {
    }
}
