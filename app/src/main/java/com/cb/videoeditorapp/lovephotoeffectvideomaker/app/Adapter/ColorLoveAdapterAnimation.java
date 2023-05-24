package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ColorLoveAdapterAnimation extends BaseAdapter {
    LayoutInflater inflter;
    int[] listColor;
    Context mContext;

    public long getItemId(int i) {
        return (long) i;
    }

    public ColorLoveAdapterAnimation(int[] iArr, Context context) {
        this.listColor = iArr;
        this.mContext = context;
        this.inflter = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.listColor.length;
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.custom_item_text_bgcolor_photo, null);
            ((CircleImageView) view.findViewById(R.id.imgBg)).setColorFilter(this.listColor[i]);
            return view;
        }
        throw new AssertionError();
    }
}
