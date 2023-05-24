package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.AppsModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AppsAdapterAnimation extends RecyclerView.Adapter<AppsAdapterAnimation.ViewHolder> {
    private Context cont;
    public setOnItemClickListener sClickListener;

    public ArrayList<AppsModelPhoto> mAppList;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivEffectThumb;
        TextView txtName;

        public ViewHolder(View v) {
            super(v);
            ivEffectThumb = v.findViewById(R.id.ivEffectThumb);
            txtName = v.findViewById(R.id.txtName);
            ivEffectThumb.setOnClickListener(this);
        }

        public void onClick(View v) {
            sClickListener.onItemClick(getAdapterPosition());
        }
    }

    public AppsAdapterAnimation(Context context, ArrayList<AppsModelPhoto> mAppList) {
        cont = context;
        this.mAppList = mAppList;
    }

    public interface setOnItemClickListener {
        void onItemClick(int i);
    }

    public void setOnItemClickListener(setOnItemClickListener clickListener) {
        sClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_items_apps_photo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppsModelPhoto mPack = mAppList.get(position);
        String url = mPack.getImg();
        Glide.with(cont).load(url).into(holder.ivEffectThumb);
        holder.txtName.setText(mPack.getName());
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }
}