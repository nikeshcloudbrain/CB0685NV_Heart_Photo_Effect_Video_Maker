package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LlpAdApp {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("appname")
    @Expose
    private String appname;
    @SerializedName("appurl")
    @Expose
    private String appurl;
    @SerializedName("appicon")
    @Expose
    private String appicon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }

    public String getAppicon() {
        return appicon;
    }

    public void setAppicon(String appicon) {
        this.appicon = appicon;
    }

}
