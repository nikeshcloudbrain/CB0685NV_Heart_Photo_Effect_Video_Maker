package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model;

import java.util.ArrayList;

public class FolderModelPhoto {
    String folderName;
    ArrayList<ImageModelPhoto> imageData;


    public String getFolderName() {
        return folderName;
    }


    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }



    public ArrayList<ImageModelPhoto> getImageData() {
        return this.imageData;
    }

    public void setImageData(ArrayList<ImageModelPhoto> arrayList) {
        this.imageData = arrayList;
    }

}