package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpMultipleActivity.getImageFromAssetsFile;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.TransitionModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;

public class FrameAdapterAnimation extends RecyclerView.Adapter<FrameAdapterAnimation.ViewHolder> {
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context cont;
    private String[] listFrame;
    private ArrayList<TransitionModelPhoto> mBlackWhiteList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb,selectedFrame;

        public ViewHolder(View v) {
            super(v);
            ivThumb = (ImageView) v.findViewById(R.id.ivThumb);
            selectedFrame = (ImageView) v.findViewById(R.id.selectedFrame);
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

    public FrameAdapterAnimation(String[] strArr, ArrayList<TransitionModelPhoto> nameList, Context context) {
        listFrame = strArr;
        mBlackWhiteList = nameList;
        mPref = context.getSharedPreferences("frame", Context.MODE_PRIVATE);
        cont = context;
        mEditor = mPref.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_items_fream_photo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

     //   FunctionsPhoto.LogE("TrimeActivity", "****" + FRAMES.size());

        holder.ivThumb.setImageBitmap(getImageFromAssetsFile(cont, listFrame[position]));

        if (mBlackWhiteList.get(position).isSelected()) {
            holder.selectedFrame.setVisibility(View.VISIBLE);
            //  FunctionsPhoto.LogE("selection", "----" + mBlackWhiteList.get(position).isSelected());
        } else {
            holder.selectedFrame.setVisibility(View.GONE);
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