package com.example.whatsup.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    static WebServiceAPI api;

    public static WebServiceAPI getAPI(String url) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + url + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(WebServiceAPI.class);
        return api;
    }
    public static void updateServerUrl(String url) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        // Update the Retrofit instance with the new server URL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + url + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(WebServiceAPI.class);
    }


}