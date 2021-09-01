package com.example.roadservice.backend;

import com.example.roadservice.models.Database;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(chain -> {
                String token = Database.getToken(null);
                Request newRequest;
                if (token == null)
                    newRequest = chain.request().newBuilder()
                            .build();
                else
                    newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Token " + token)
                            .build();
                return chain.proceed(newRequest);
            })
            .build();

    private static final String BASE_URL = "http://kharkhar.tk:8080/";
    private static Retrofit api;

    public static Retrofit getApi() {
        if (api == null) {
            api = new retrofit2.Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return api;
    }

}
