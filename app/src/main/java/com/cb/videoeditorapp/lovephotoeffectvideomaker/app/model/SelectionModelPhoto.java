package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model;

public class SelectionModelPhoto {
    int fileIndex;
    String filepath;
    int folderIndex;

    public SelectionModelPhoto(int i, int i2, String str) {
        this.folderIndex = i;
        this.fileIndex = i2;
        this.filepath = str;
    }

    public int getFolderIndex() {
        return this.folderIndex;
    }

    public void setFolderIndex(int i) {
        this.folderIndex = i;
    }

    public int getFileIndex() {
        return this.fileIndex;
    }

    public void setFileIndex(int i) {
        this.fileIndex = i;
    }

    public String getFilepath() {
        return this.filepath;
    }

    public void setFilepath(String str) {
        this.filepath = str;
    }
}
