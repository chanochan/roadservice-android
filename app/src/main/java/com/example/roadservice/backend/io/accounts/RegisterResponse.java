package com.example.roadservice.backend.io.accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    public static final int CODE = 101;

    @SerializedName("status")
    @Expose
    public boolean status;
}
