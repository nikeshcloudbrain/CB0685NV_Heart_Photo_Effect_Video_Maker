package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model;

import java.util.Date;

public class FileItemModelPhoto {
    private String date;
    private Date date1;
    public long length;
    private String fst_file_name;
    private String pathFile;

//    public FileItemModelPhoto(String str, String str2, String str3, boolean z, boolean z2, boolean z3, long j, Date date, boolean z4) {
//        this.name = str;
//        this.pathFile = str2;
//        this.date = str3;
//        this.isFile = z;
//        this.isDirectory = z2;
//        this.isSelected = z3;
//        this.length = j;
//        this.date1 = date;
//        this.isHidden = z4;
//    }


    public String getFst_file_name() {
        return fst_file_name;
    }

    public void setFst_file_name(String fst_file_name) {
        this.fst_file_name = fst_file_name;
    }

    public String getPathFile() {
        return this.pathFile;
    }

    public String getDate() {
        return this.date;
    }

    public long getLength() {
        return this.length;
    }

    public Date getDate1() {
        return this.date1;
    }

}
