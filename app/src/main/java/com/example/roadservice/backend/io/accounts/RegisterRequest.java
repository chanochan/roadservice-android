package com.example.roadservice.backend.io.accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("first_name")
    @Expose
    public String firstName;

    @SerializedName("last_name")
    @Expose
    public String lastName;

    @SerializedName("phone_number")
    @Expose
    public String phoneNumber;

    @SerializedName("password")
    @Expose
    public String password;

    public RegisterRequest(String firstName, String lastName, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
