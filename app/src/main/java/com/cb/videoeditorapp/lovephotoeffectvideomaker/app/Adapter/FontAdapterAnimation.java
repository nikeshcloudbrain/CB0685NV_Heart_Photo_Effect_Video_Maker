package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;


public class FontAdapterAnimation extends BaseAdapter {
    LayoutInflater inflter;
    String[] listfont;
    Context mContext;

    public long getItemId(int i) {
        return (long) i;
    }

    public FontAdapterAnimation(String[] strArr, Context context) {
        this.listfont = strArr;
        this.mContext = context;
        this.inflter = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.listfont.length;
    }

    public Object getItem(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater == null) {
            return view;
        }
        view = layoutInflater.inflate(R.layout.custom_item_text_font_photo, null);
        ((TextView) view.findViewById(R.id.txtFont)).setTypeface(Typeface.createFromAsset(this.mContext.getAssets(), this.listfont[i]));
        return view;
    }
}
