package com.example.roadservice.backend;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.roadservice.R;
import com.example.roadservice.RoadServiceApplication;

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
                Context ctx = RoadServiceApplication.getAppContext();
                SharedPreferences sp = ctx.getSharedPreferences(
                        ctx.getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE
                );
                String token = sp.getString("AUTH", "");
                Request newRequest;
                if (token.length() == 0)
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
