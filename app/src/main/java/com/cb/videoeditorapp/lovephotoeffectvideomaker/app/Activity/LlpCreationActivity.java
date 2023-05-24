package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import android.app.PendingIntent;
import android.app.RecoverableSecurityException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.SaveFileListAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.preference.PowerPreference;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivityCreationPhotoBinding;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LlpCreationActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.rvCreation)
    RecyclerView rvCreation;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
   ActivityCreationPhotoBinding binding;
    ActivityResultLauncher<IntentSenderRequest> loginResultHandler;
    SaveFileListAdapterAnimation rcAdapter;

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
        binding=ActivityCreationPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);


        loginResultHandler = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    refresh();
                }
            }
        });

        txtTitle.setText("My Creation");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        setUpAdapter();
    }

    private void setUpAdapter() {

        GridLayoutManager lLayoutlLayout = new GridLayoutManager(this, 1);
        rvCreation.setHasFixedSize(true);
        rvCreation.setLayoutManager(lLayoutlLayout);

        File dCimFile = new File(Constant.getOutputPath(this));
        String dirName = getString(R.string.my_creation);

        if (dCimFile.exists()) {
            File subFile = new File(dCimFile, dirName);
            if (subFile.exists()) {
                File[] listFile;
                listFile = subFile.listFiles();
                if (listFile.length > 0) {
                    rcAdapter = new SaveFileListAdapterAnimation(this, listFile, dirName, new SaveFileListAdapterAnimation.onItemClick() {
                        @Override
                        public void onClick(String path) {
                            delete(path);
                            refresh();
                        }
                    });
                    rvCreation.setAdapter(rcAdapter);
                    txtNoData.setVisibility(View.GONE);
                } else {
                    txtNoData.setVisibility(View.VISIBLE);
                }
            } else {
                txtNoData.setVisibility(View.VISIBLE);
            }
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    public void refresh() {

        File dCimFile = new File(Constant.getOutputPath(this));
        String dirName = getString(R.string.my_creation);

        if (dCimFile.exists()) {
            File subFile = new File(dCimFile, dirName);
            if (subFile.exists()) {
                File[] listFile;
                listFile = subFile.listFiles();
                if (listFile!=null & listFile.length > 0) {

                    rcAdapter.refresh(listFile, dirName);
                    txtNoData.setVisibility(View.GONE);
                } else {
                    rvCreation.setVisibility(View.GONE);

                    txtNoData.setVisibility(View.VISIBLE);
                }
            } else {
                rvCreation.setVisibility(View.GONE);
                txtNoData.setVisibility(View.VISIBLE);
            }
        } else {
            rvCreation.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {

        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                Intent ii = new Intent(LlpCreationActivity.this, LlpStartActivity.class);
                startActivity(ii);
                finish();
            }
        });
    }


    public long getFilePathToMediaID(String songPath, Context context)
    {
        long id = 0;
        ContentResolver cr = context.getContentResolver();

        Uri uri = MediaStore.Files.getContentUri("external");
        String selection = MediaStore.Audio.Media.DATA;
        String[] selectionArgs = {songPath};
        String[] projection = {MediaStore.Audio.Media._ID};
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        Cursor cursor = cr.query(uri, projection, selection + "=?", selectionArgs, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                id = Long.parseLong(cursor.getString(idIndex));
            }
        }

        return id;
    }

    public void delete(String path) {

        File tempFile=new File(path);
        long mediaID=getFilePathToMediaID(tempFile.getAbsolutePath(),  this);
        Uri Uri_one = ContentUris.withAppendedId( MediaStore.Video.Media.getContentUri("external"),mediaID);

        ContentResolver contentResolver = getContentResolver();


        try {
            contentResolver.delete(Uri_one, null, null);
            refresh();
        } catch (Exception e) {

            PendingIntent pendingIntent = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                ArrayList<Uri> collection = new ArrayList<>();
                collection.add(Uri_one);
                pendingIntent = MediaStore.createDeleteRequest(contentResolver, collection);

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (e instanceof RecoverableSecurityException) {
                    RecoverableSecurityException exception = (RecoverableSecurityException) e;
                    pendingIntent = exception.getUserAction().getActionIntent();
                }
            }

            if (pendingIntent != null) {
                IntentSender sender = pendingIntent.getIntentSender();
                IntentSenderRequest request = new IntentSenderRequest.Builder(sender).build();
                loginResultHandler.launch(request);
            }
        }
    }
}
