package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EffectList {
    @SerializedName("effect")
    @Expose
    private List<Effect> effect = null;

    public List<Effect> getEffect() {
        return effect;
    }
}
