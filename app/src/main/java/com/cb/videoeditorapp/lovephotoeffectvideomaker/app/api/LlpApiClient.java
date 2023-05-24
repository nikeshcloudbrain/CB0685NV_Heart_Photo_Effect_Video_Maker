package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.api;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Utils.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LlpApiClient {

    public static String BASE_URL;
    private static LlpApiClient llpApiClient;
    private Retrofit retrofit;

    public LlpApiClient() {
        BASE_URL = Constant.getMainApi();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        chain -> {
                            Request original = chain.request();

                            Request.Builder requestBuilder = original.newBuilder()
                                    .method(original.method(), original.body());

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                ).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    public static synchronized LlpApiClient getInstance() {
        if (llpApiClient == null) {
            llpApiClient = new LlpApiClient();
        }
        return llpApiClient;
    }

    public LlpApiInterface getApi2() {
        return retrofit.create(LlpApiInterface.class);
    }

}
