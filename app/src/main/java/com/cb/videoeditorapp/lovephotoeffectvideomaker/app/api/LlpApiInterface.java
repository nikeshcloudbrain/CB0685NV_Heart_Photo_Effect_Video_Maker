package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LlpApiInterface {

    @POST("update.php")
    @Headers({"Content-Type: application/json"})
    Call<String> getUpdateResponse(@Body JsonObject requestBody);

    @POST("vpn.php")
    @Headers({"Content-Type: application/json;"})
    Call<String> vpnApi(@Body JsonObject JsonObject);

    @POST("song.php")
    @Headers({"Content-Type: application/json;"})
    Call<String> getMusic(@Body JsonObject JsonObject);

    @POST("effect.php")
    @Headers({"Content-Type: application/json;"})
    Call<String> getEffect(@Body JsonObject JsonObject);

}
