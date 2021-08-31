package com.example.roadservice.backend.io.specialist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RejectIssueResponse {
    public static int CODE = 301;

    @SerializedName("status")
    @Expose
    public boolean status;
}
