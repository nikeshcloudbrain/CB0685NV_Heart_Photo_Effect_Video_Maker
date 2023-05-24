package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import static com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpMultipleActivity.getImageFromAssetsFile;

public class StickersAdapterAnimation extends RecyclerView.Adapter<StickersAdapterAnimation.ViewHolder> {


    private Context context;
    private String[] listItem;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_sticker;

        public ViewHolder(View v) {
            super(v);
            item_sticker = (ImageView) v.findViewById(R.id.item_sticker);

        }
    }


    public StickersAdapterAnimation(String[] strArr, Context context) {

        this.listItem = strArr;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_items_stickers_photo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.item_sticker.setImageBitmap(getImageFromAssetsFile(this.context, this.listItem[position]));

        //FunctionsPhoto.LogE("img",""+listItem[position]);
    }

    @Override
    public int getItemCount() {
        return listItem.length;
    }
}