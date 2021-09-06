package com.example.roadservice.backend;

import com.example.roadservice.models.Database;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static final String BASE_URL = "http://185.97.117.79:1337/";
    private static Retrofit api;

    public static Retrofit getApi() {
        if (api == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
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
                    .addInterceptor(logging)
                    .build();
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .setPrettyPrinting()
                    .create();
            api = new retrofit2.Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .build();
        }
        return api;
    }

}
