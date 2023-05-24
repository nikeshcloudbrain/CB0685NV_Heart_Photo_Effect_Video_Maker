package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model;

import android.text.TextUtils;

public class ImageModelPhoto {
    Boolean Selected;
    int count;
    public String folderName;
    public long id;
    public String imageAlbum;
    public int imageCount = 0;
    public String imagePath;
    public String effectImagePath;
    public String moreEffectPathImage;

    public String getMoreEffectPathImage() {
        return moreEffectPathImage;
    }

    public void setMoreEffectPathImage(String moreEffectPathImage) {
        this.moreEffectPathImage = moreEffectPathImage;
    }

    public Boolean getSelected() {
        return this.Selected;
    }

    public void setSelected(Boolean bool) {
        this.Selected = bool;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String str) {
        this.imagePath = str;
    }

    public String getImageAlbum() {
        return this.imageAlbum;
    }

    public void setImageAlbum(String str) {
        this.imageAlbum = str;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String toString() {
        if (TextUtils.isEmpty(this.imagePath)) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImageModelPhoto { imagePath=");
        stringBuilder.append(this.imagePath);
        stringBuilder.append(",folderName=");
        stringBuilder.append(this.folderName);
        stringBuilder.append(",imageCount=");
        stringBuilder.append(this.imageCount);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public void countadd() {
        this.count++;
    }

    public void countremove() {
        this.count--;
        if (getCount() == 0) {
            setSelected(Boolean.valueOf(false));
        }
    }

    public String getEffectImagePath() {
        return effectImagePath;
    }

    public void setEffectImagePath(String effectImagePath) {
        this.effectImagePath = effectImagePath;
    }
}
