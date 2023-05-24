package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.DecryptEncryptPhoto.DecryptStr;
import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.DecryptEncryptPhoto.EncryptStr;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.StoreAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.BuildConfig;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ApplicationPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.UtilsFoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.LargeNativeAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.RewardedAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.api.LlpApiClient;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityStorePhotoBinding;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.encrypt.DecryptEncrypt;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.Effect;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.EffectList;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.preference.PowerPreference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdi.mzip.zip.ZipArchive;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LlpStoreActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.recycleStore)
    RecyclerView recycleStore;
    @BindView(R.id.effecttxtNoData)
    TextView effecttxtNoData;


    public long WAITING_TIME = 4000;
    public long mAdStartSec = 500;
    public long mAdWaitSec = WAITING_TIME;
    public Handler mHandler = new Handler();
    public Runnable mRunnable;
    public RewardedVideoAd mRewardedVideoAd;
    int VERSION = 0;
ActivityStorePhotoBinding binding;
    private Dialog dialog;
    private Dialog dialog2;
    public ProgressDialog mDialog;

    private StoreAdapterAnimation storetAdapter;
    private TextView txtProgress;

    private String id, zipUrl, folderName;
File Effectfile;
    public boolean isRewarded = false;
    public static boolean isavailable = false;

    public ArrayList<Effect> mEffectList = new ArrayList<>();
    public ArrayList<Effect> mEffectList2 = new ArrayList<>();

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
        binding=ActivityStorePhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);
        isavailable = false;

        ivBack.setOnClickListener(this);

        txtTitle.setText("Effect Store");

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);

        /*if (FunctionsPhoto.isConnected(this)) {
            GetEffectList();
        }*/
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

        GetEffect();
    }

    private void GetEffect(){
        @SuppressLint("HardwareIds") String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        JsonObject object = new JsonObject();
        object.addProperty("VersionCode", VERSION);
        object.addProperty("PkgName", getPackageName());
        object.addProperty("AndroidId", deviceId);

        LlpApiClient.getInstance().getApi2().getEffect(object).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("data", response.body()+" hel;l");

                final EffectList Effectdata = new GsonBuilder().create().fromJson((DecryptEncrypt.DecryptStr(response.body())),  EffectList.class);
                Log.d("Effectdata", DecryptEncrypt.DecryptStr(response.body()));
                Log.e("Effectdata", Effectdata.getEffect().get(1).getZip() );

                if(Effectdata.getEffect().size()>0){
                    for (int i=0;i<Effectdata.getEffect().size();i++){

                        String mainFolder =getString(R.string.mainFolder);
                        String effectFolder = getString(R.string.theme_folder);
                        final File mainFile = new File(getFilesDir().getAbsolutePath() + "/" + mainFolder + "/" + effectFolder);
                        Effectfile = new File(mainFile, Effectdata.getEffect().get(i).getName().toLowerCase() + "/" + Effectdata.getEffect().get(i).getName().toLowerCase() +  File.separator );
                        if(!Effectfile.exists()){

                            mEffectList.add(Effectdata.getEffect().get(i));
                            //mEffectList2.add(Effectdata.getEffect().get(i));
                        }

                    }

                    if(mEffectList.size()>0) {
                        recycleStore.setVisibility(View.VISIBLE);
                        effecttxtNoData.setVisibility(View.GONE);
                    }else{
                        effecttxtNoData.setVisibility(View.VISIBLE);
                        recycleStore.setVisibility(View.GONE);
                    }
                    isavailable = true;
                }else {
                    if (!isavailable) {
                        effecttxtNoData.setVisibility(View.VISIBLE);
                        recycleStore.setVisibility(View.GONE);
                    } else {
                        recycleStore.setVisibility(View.VISIBLE);
                        effecttxtNoData.setVisibility(View.GONE);
                    }
                }
                storetAdapter = new StoreAdapterAnimation(LlpStoreActivity.this, mEffectList);
                recycleStore.setLayoutManager(new GridLayoutManager(LlpStoreActivity.this, 3));
                recycleStore.setItemAnimator(new DefaultItemAnimator());
                recycleStore.setAdapter(storetAdapter);

                storetAdapter.setOnItemClickListener((i, eId, eZipUrl, eFolderName) -> {
                    dilog_show();
                    id = eId;
                    zipUrl = eZipUrl;
                    folderName = eFolderName;
                });
            }


            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("TAG","faild",t);
                UtilsFoto.LogE("Error: ", "Error: " + t.getMessage());

            }
        });
    }


    private void sendDownload(final String effectId) {
        final String URL = Constant.BASE_URL + Constant.BASE_EFFECTDOWNLOAD;
        StringRequest strReq = new StringRequest(Request.Method.POST, URL, response -> {
            try {
                String decResponce = DecryptStr(response);
                FunctionsPhoto.LogE(URL, "_Response" + decResponce);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            FunctionsPhoto.LogD("Error: ", "Error: " + error.getMessage());
            if (error instanceof TimeoutError || error instanceof NoConnectionError)
                Toast.makeText(LlpStoreActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constant.KEY_TOKEN, EncryptStr(PowerPreference.getDefaultFile().getString(Constant.KEY_TOKEN)));
                params.put(Constant.KEY_SEND_EFFECT_ID, EncryptStr(effectId));
                FunctionsPhoto.LogE(URL, "" + params);
                return params;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationPhoto.getInstance().addToRequestQueue(strReq, "string_req");
    }


    private class DownloadEffect extends AsyncTask<String, String, String> {

        private String fileName;
        private String folder;
        private boolean isDownloaded;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dilog_show_progress();
            UtilsFoto.dilogShow(LlpStoreActivity.this);
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

                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());

                String mainFolder = getString(R.string.mainFolder);
                String effectFolder = getString(R.string.theme_folder);

                folder = getFilesDir().getAbsolutePath() + File.separator + mainFolder + File.separator + effectFolder + File.separator;

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
                    //  FunctionsPhoto.LogD("down", "Progress: " + (int) ((total * 100) / lengthOfFile));
                    output.write(data, 0, count);
                }


                output.flush();
                output.close();
                input.close();
                return "" + folder + fileName;

            } catch (Exception e) {
                FunctionsPhoto.LogE("Error: ", e.getMessage());
            }

            return "Something went wrong";
        }


        protected void onProgressUpdate(String... progress) {
            TextView txtvlu = UtilsFoto.dialog.findViewById(R.id.txtvlu);
            txtvlu.setText("Downloading " + Integer.parseInt(progress[0]) + " %");
//            txtProgress.setText("Downloading " + Integer.parseInt(progress[0]) + " %");
        }


        @Override
        protected void onPostExecute(String message) {
            sendDownload(id);

            FunctionsPhoto.LogD("down", "Progress:----end---- " + message);
            FunctionsPhoto.LogD("down", "Progress:----folder---- " + folder);
            ZipArchive zipArchive = new ZipArchive();
            zipArchive.unzip(message, folder, "unlockziphrt");

            fileName = fileName.replace(".zip", "");

            ArrayList<String> data = new Gson().fromJson(PowerPreference.getDefaultFile().getString("mEffect", new Gson().toJson(new ArrayList<String>())), new TypeToken<ArrayList<String>>() {}.getType());
            data.add(folder + fileName);
            PowerPreference.getDefaultFile().putString("mEffect", new Gson().toJson(data));

            fileName = folder + fileName + File.separator + fileName + File.separator;
            Log.e("TAG", "doInBackground: "+fileName );

            File fmd = new File(message);
            if (fmd.exists()) {
                fmd.delete();
            }

            UtilsFoto.dilogHide(LlpStoreActivity.this);
            setResult(-1, new Intent().putExtra("effectPath", fileName));
            FunctionsPhoto.LogD("down", "Progress:----filePathh---- " + fileName);
            finish();
        }
    }

    public void dilog_show() {
        try {
            dialog = new Dialog(LlpStoreActivity.this);
            dialog.setContentView(R.layout.custom_dialog_ads_watch_photo);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            final TextView title = dialog.findViewById(R.id.title);
            final TextView txtDesc = dialog.findViewById(R.id.txtDesc);
            final ImageView imgclose = dialog.findViewById(R.id.pclose);
            FrameLayout nativeframe=dialog.findViewById(R.id.nativeAd);
            TextView adspace=dialog.findViewById(R.id.ad_space);
            final AppCompatButton btnWatch = dialog.findViewById(R.id.btnWatch);

            title.setText("Premium Effect");
            txtDesc.setText("Please watch full video ad to use this effect");

            btnWatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ShowRewardAds();
                    dilogHide();
                    new RewardedAds().showRewardAds(LlpStoreActivity.this, new RewardedAds.OnAdClosedListener() {
                        @Override
                        public void onAdClosed(boolean isRewarded, boolean isRewardVideo) {
                            if (isRewarded) {
//                                new DownloadEffect().execute(zipUrl);
                                new DownloadEffect().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,zipUrl);
                            }
                        }

                        @Override
                        public void onAdFailed() {
                            Log.e("TAG", "onAdFailed: Faild to load reward");
//                            new DownloadEffect().execute(zipUrl);
                            new DownloadEffect().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,zipUrl);

                        }

                        @Override
                        public void onAdTimer() {
                            Log.e("TAG", "onAdFailed: AdTimer to load reward");

//                            new DownloadEffect().execute(zipUrl);
                            new DownloadEffect().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,zipUrl);

                        }
                    });
                    if (dialog != null) {
                        dilogHide();
                    }
                }
            });

            imgclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog != null) {
                        dilogHide();
                    }
                }
            });

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {


                    if(PowerPreference.getDefaultFile().getBoolean(Constant.LoaderNativeOnOff,true)){
                        new LargeNativeAds().showNativeAds(LlpStoreActivity.this, dialog);
                    }else {
                        nativeframe.setVisibility(View.GONE);
                        adspace.setVisibility(View.GONE);
                    }
                }
            });

            dialog.show();
        } catch (Exception e) {
            FunctionsPhoto.LogW("Catch", e.getMessage());
        }


    }


    public void dilogHide() {
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {

            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {

                finish();
            }
        });

    }
}
