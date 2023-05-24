package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class BgColorAdapterAnimation extends BaseAdapter {
    int[] bgColor;
    LayoutInflater inflter;
    Activity mContext;

    public long getItemId(int i) {
        return (long) i;
    }

    public BgColorAdapterAnimation(int[] iArr, Activity activity) {
        this.bgColor = iArr;
        this.mContext = activity;
        this.inflter = LayoutInflater.from(activity);
    }

    public int getCount() {
        return this.bgColor.length;
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.custom_item_text_bgcolor_photo, null);
            CircleImageView imageView =  view.findViewById(R.id.imgBg);


            if (i == 0) {
                imageView.setImageResource(R.drawable.bgtrans_photo);
            } else {
                imageView.setColorFilter(this.bgColor[i]);
            }
//            i = this.mContext.getWindowManager().getDefaultDisplay();
//            Point point = new Point();
//            i.getSize(point);
//            i = point.x;
            imageView.requestLayout();
            return view;
        }
        throw new AssertionError();
    }
}
