package com.example.roadservice.backend.io.accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    public static final int CODE = 102;

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("first_name")
    @Expose
    public String firstName;

    @SerializedName("last_name")
    @Expose
    public String lastName;

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("phone_number")
    @Expose
    public String phoneNumber;

    @SerializedName("role")
    @Expose
    public String role;
}
