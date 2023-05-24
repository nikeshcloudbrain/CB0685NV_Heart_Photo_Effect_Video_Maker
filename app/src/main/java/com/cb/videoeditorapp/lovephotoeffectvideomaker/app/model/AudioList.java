package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AudioList {
    @SerializedName("audio")
    @Expose
    private List<Audio> audio = null;

    public List<Audio> getAudio() {
        return audio;
    }
}
