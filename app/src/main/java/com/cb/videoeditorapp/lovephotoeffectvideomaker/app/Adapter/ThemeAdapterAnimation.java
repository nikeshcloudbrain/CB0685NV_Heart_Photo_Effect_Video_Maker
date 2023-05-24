package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant.IMAGE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.TransitionModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;

public class ThemeAdapterAnimation extends RecyclerView.Adapter<ThemeAdapterAnimation.ViewHolder> {
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private ArrayList<TransitionModelPhoto> mBlackWhiteList;
    private Context cont;
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb, selectedtheme_img;

        public ViewHolder(View v) {
            super(v);
            ivThumb = (ImageView) v.findViewById(R.id.ivThumb);
            selectedtheme_img = (ImageView) v.findViewById(R.id.selectedtheme_img);
        }
    }

    public void add(int position, TransitionModelPhoto item) {
        mBlackWhiteList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mBlackWhiteList.indexOf(item);
        mBlackWhiteList.remove(position);
        notifyItemRemoved(position);
    }

    public ThemeAdapterAnimation(ArrayList<TransitionModelPhoto> nameList, Context context) {
        mBlackWhiteList = nameList;
        mPref = context.getSharedPreferences("theme", Context.MODE_PRIVATE);
        cont = context;
        mEditor = mPref.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_items_transition_photo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
   //     FunctionsPhoto.LogE("selection", "" + mBlackWhiteList.get(position).isSelected());

        holder.ivThumb.setImageResource(IMAGE[position]);

        if (mBlackWhiteList.get(position).isSelected()) {
            holder.selectedtheme_img.setVisibility(View.VISIBLE);
          //  FunctionsPhoto.LogE("selection", "----" + mBlackWhiteList.get(position).isSelected());
        } else {
            holder.selectedtheme_img.setVisibility(View.GONE);
          //  FunctionsPhoto.LogE("selection", "*****" + mBlackWhiteList.get(position).isSelected());
        }
    }

    public void setSelected(int pos) {
        try {
            if (mBlackWhiteList.size() > 1) {
                mBlackWhiteList.get(mPref.getInt("position", 0)).setSelected(false);
                mEditor.putInt("position", pos);
                mEditor.commit();
            }
            mBlackWhiteList.get(pos).setSelected(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mBlackWhiteList.size();
    }
}