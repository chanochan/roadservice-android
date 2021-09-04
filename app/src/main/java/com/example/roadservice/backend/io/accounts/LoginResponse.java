package com.example.roadservice.backend.io.accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    public static final int CODE = 100;

    @SerializedName("token")
    @Expose
    public String token;
}
