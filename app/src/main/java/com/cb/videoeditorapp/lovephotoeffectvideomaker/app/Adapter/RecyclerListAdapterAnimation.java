package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;


import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Activity.LlpImageEditActivity;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.ImageModelPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.FunctionsPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.ItemMoveCallbackPhoto;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.RoundCornerLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class RecyclerListAdapterAnimation extends Adapter<RecyclerListAdapterAnimation.ViewHolder> implements ItemMoveCallbackPhoto.ItemTouchHelperContract {

    private Activity mContext;
    private ArrayList<ImageModelPhoto> mItems;

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItems, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);

    }

    @Override
    public void onRowSelected(ViewHolder myViewHolder) {

        myViewHolder.crdBg.setRadius(20);
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
    }

    @Override
    public void onRowClear(ViewHolder myViewHolder) {

        notifyDataSetChanged();
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);

        myViewHolder.crdBg.setRadius(20);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivThumb, ivEdit, ivDelete;
        private TextView textNumber;
        private RoundCornerLayout crdBg;
        View rowView;


        ViewHolder(View view) {
            super(view);
            rowView = itemView;
            textNumber = (TextView) view.findViewById(R.id.textNumber);
            ivThumb = (ImageView) view.findViewById(R.id.ivThumb);
            ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
            ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
            crdBg = view.findViewById(R.id.crdBg);
        }
    }

    public RecyclerListAdapterAnimation(Activity activity, ArrayList<ImageModelPhoto> arrayList) {
        this.mItems = arrayList;
        this.mContext = activity;

        FunctionsPhoto.LogE("length", String.valueOf(mItems.size()));
    }

    public ArrayList<ImageModelPhoto> getmItems() {
        return mItems;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_items_drag_photo, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull final ViewHolder itemViewHolder, final int i) {

        final ImageModelPhoto item = getItem(i);

        Log.e("TAG", item.imagePath + " HELL");
        ((RequestBuilder) ((RequestBuilder) Glide.with(this.mContext).load(item.imagePath).diskCacheStrategy(DiskCacheStrategy.NONE)).skipMemoryCache(true)).into(itemViewHolder.ivThumb);
//        Glide.with(mContext).load(item.imagePath).into(itemViewHolder.ivThumb);

        FunctionsPhoto.LogE("drag", item.imagePath);


        itemViewHolder.textNumber.setText(String.valueOf(i + 1));
        itemViewHolder.ivThumb.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                FunctionsPhoto.LogE("drag", "---*4--");
                return true;
            }
        });

        itemViewHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, LlpImageEditActivity.class).putExtra("image_path", item.getImagePath()));
            }
        });
        itemViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mItems.size() >= 3) {
                    File file = new File(item.getImagePath());
                    if (file.exists()) {
                        mItems.remove(i);
                        notifyDataSetChanged();
                        return;
                    }
                    return;
                }
                Toast.makeText(mContext, "Minimum 3 Photos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public ImageModelPhoto getItem(int i) {
        return mItems.get(i);
    }

    public int getItemCount() {
        return this.mItems.size();

    }
}
