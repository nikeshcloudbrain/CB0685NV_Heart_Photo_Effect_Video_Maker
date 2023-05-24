package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity;

import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpGalleryActivity.drag_imageData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter.RecyclerListAdapterAnimation;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ItemMoveCallbackPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.BackInterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.InterAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.ads.ListBannerAds;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.databinding.ActivitySwipePhotoBinding;
import com.preference.PowerPreference;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LlpSwipeActivity extends AppCompatActivity {

    @BindView(R.id.img_dreg_Back)
    ImageView img_dreg_Back;
    @BindView(R.id.crd_dragNxt)
    LinearLayout crd_dragNxt;
    @BindView(R.id.ProgressBarsaveData)
    ProgressBar ProgressBarsaveData;
    @BindView(R.id.recycle_dregimg)
    RecyclerView recycle_dregimg;

ActivitySwipePhotoBinding binding;
    private RecyclerListAdapterAnimation adapter;

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerListAdapterAnimation recyclerListAdapter = this.adapter;
        if (recyclerListAdapter != null) {
            recyclerListAdapter.notifyDataSetChanged();
            Log.e("onResume: ", "spider");
        }
        crd_dragNxt.setVisibility(View.VISIBLE);
        ProgressBarsaveData.setVisibility(View.GONE);
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
        binding=ActivitySwipePhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this);


        img_dreg_Back.setOnClickListener(v -> onBackPressed());
        crd_dragNxt.setOnClickListener(v -> {
            crd_dragNxt.setVisibility(View.GONE);
            ProgressBarsaveData.setVisibility(View.VISIBLE);

            new InterAds().showInterAds(LlpSwipeActivity.this, new InterAds.OnAdClosedListener() {
                @Override
                public void onAdClosed() {
                Intent intent = new Intent(LlpSwipeActivity.this, LlpMultipleActivity.class);
                startActivity(intent);
                }
            });
        });
        init();
    }

    private void init() {

        recycle_dregimg.setLayoutManager(new GridLayoutManager((Context) this, 3, RecyclerView.VERTICAL, false));
        recycle_dregimg.setItemAnimator(new DefaultItemAnimator());

        adapter = new RecyclerListAdapterAnimation(LlpSwipeActivity.this, drag_imageData);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallbackPhoto(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        adapter.notifyDataSetChanged();
        touchHelper.attachToRecyclerView(recycle_dregimg);
        recycle_dregimg.setAdapter(adapter);
    }



    @Override
    public void onBackPressed() {
        new BackInterAds().showInterAds(this, new BackInterAds.OnAdClosedListener() {
            @Override
            public void onAdClosed() {
                LlpSwipeActivity.this.finish();
            }
        });
        super.onBackPressed();
    }
}
