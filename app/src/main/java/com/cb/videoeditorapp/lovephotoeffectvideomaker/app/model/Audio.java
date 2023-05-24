package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Audio {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("audio_name")
    @Expose
    private String audioName;
    @SerializedName("audio_duration")
    @Expose
    private String audioDuration;
    @SerializedName("file_name")
    @Expose
    private String fileName;
    @SerializedName("audio_url")
    @Expose
    private String audioUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(String audioDuration) {
        this.audioDuration = audioDuration;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
