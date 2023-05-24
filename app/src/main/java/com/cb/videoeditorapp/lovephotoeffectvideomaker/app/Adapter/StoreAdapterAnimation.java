package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.Effect;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StoreAdapterAnimation extends RecyclerView.Adapter<StoreAdapterAnimation.ViewHolder> {
    private Context cont;
    public setOnItemClickListener sClickListener;

    public ArrayList<Effect> mEffectList;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivEffectThumb;
        TextView txtDownload;
        LinearLayout store;
        public ViewHolder(View v) {
            super(v);
            ivEffectThumb = (ImageView) v.findViewById(R.id.ivEffectThumb);
            store = (LinearLayout) v.findViewById(R.id.store);

            txtDownload = v.findViewById(R.id.txtDownload);
            txtDownload.setOnClickListener(this);
        }

        public void onClick(View v) {
            sClickListener.onItemClick(getAdapterPosition(), mEffectList.get(getAdapterPosition()).getId(), mEffectList.get(getAdapterPosition()).getZip(), mEffectList.get(getAdapterPosition()).getFolderName());
        }
    }

    public StoreAdapterAnimation(Context context, ArrayList<Effect> mEffect) {
        cont = context;
        mEffectList = mEffect;
//        mtempEffectList=mtempEffect;
    }

    public interface setOnItemClickListener {
        void onItemClick(int i, String id, String zipUrl, String folderName);
    }

    public void setOnItemClickListener(setOnItemClickListener clickListener) {
        sClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_items_store_photo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Effect mPack = mEffectList.get(position);



            String url = mPack.getThumb();
            FunctionsPhoto.LogE("thumb", url);
            holder.txtDownload.setVisibility(View.VISIBLE);
            holder.ivEffectThumb.setVisibility(View.VISIBLE);
            Glide.with(cont).load(url).into(holder.ivEffectThumb);


    }

    @Override
    public int getItemCount() {
        return mEffectList.size();
    }
}