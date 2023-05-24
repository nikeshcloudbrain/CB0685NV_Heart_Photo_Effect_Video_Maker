package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.preference.PowerPreference;

import java.io.File;
import java.util.ArrayList;

public class EffectAdapterAnimation extends RecyclerView.Adapter<EffectAdapterAnimation.ViewHolder> {
    private ArrayList<File> mBlackWhiteList;
    private Context cont;
    public setOnItemClickListener sClickListener;
    int pos = 1;


    public void refresh(int position) {
        this.pos = position - 1;
        Log.d("appdata", "refresh: " + pos);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb, selectedtheme_img;
        RelativeLayout relMain;

        public ViewHolder(View v) {
            super(v);
            ivThumb = (ImageView) v.findViewById(R.id.ivThumb);
            selectedtheme_img = (ImageView) v.findViewById(R.id.selectedtheme_img);
            relMain = v.findViewById(R.id.relMain);
        }


    }

    public interface setOnItemClickListener {
        void onItemClick(int i);
    }

    public void setOnItemClickListener(setOnItemClickListener clickListener) {
        sClickListener = clickListener;
    }

    public EffectAdapterAnimation(ArrayList<File> nameList, Context context, int pos) {
        mBlackWhiteList = nameList;
        cont = context;
        this.pos = pos;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_items_effect_photo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (holder.getAdapterPosition() == pos) {
            holder.selectedtheme_img.setVisibility(View.VISIBLE);
        } else {
            holder.selectedtheme_img.setVisibility(View.GONE);
        }


        String imagname = mBlackWhiteList.get(position).getAbsolutePath() + "/" + cont.getResources().getString(R.string.theme_img_name);
        //  FunctionsPhoto.LogE("selection", "" + imagname);
        BitmapFactory.Options options = new BitmapFactory.Options();
        // options.inSampleSize = 1;
        Bitmap imbm = BitmapFactory.decodeFile(imagname, options);
        holder.ivThumb.setImageBitmap(imbm);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    ArrayList<String> data = new Gson().fromJson(PowerPreference.getDefaultFile().getString("mEffect", new Gson().toJson(new ArrayList<String>())), new TypeToken<ArrayList<String>>() {
                    }.getType());

                    if (data.contains(mBlackWhiteList.get(position).getAbsolutePath())) {
                        pos = holder.getAdapterPosition();
                        sClickListener.onItemClick(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mBlackWhiteList.size();
    }
}