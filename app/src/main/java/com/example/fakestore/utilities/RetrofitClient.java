package com.example.fakestore.utilities;



import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Api api;
    int TIMEOUT_IN_SECONDS = 120;
    int CONNECT_TIMEOUT_IN_SECONDS = 10;
    int READ_TIMEOUT_IN_SECONDS = 20;
    int WRITE_TIMEOUT_IN_SECONDS = 20;
    private static RetrofitClient instance;

    public RetrofitClient() {
        /*
            addConverterFactory converts the response into model class object
         */
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().callTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS).connectTimeout(CONNECT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS).writeTimeout(WRITE_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        api = retrofit.create(Api.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Api getApi() {
        return api;
    }


}
