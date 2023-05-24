package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model;

public class RatioModelPhoto {
    int height;
    int icon;
    boolean select;
    String text;
    int width;

    public RatioModelPhoto(int i, int i2, int i3, String str, boolean z) {
        this.icon = i;
        this.width = i2;
        this.height = i3;
        this.text = str;
        this.select = z;
    }

    public boolean isSelect() {
        return this.select;
    }

    public void setSelect(boolean z) {
        select = z;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int i) {
        icon = i;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int i) {
        width = i;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int i) {
        height = i;
    }

    public String getText() {
        return text;
    }

    public void setText(String str) {
        text = str;
    }
}
