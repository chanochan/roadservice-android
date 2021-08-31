package com.example.roadservice.backend.io.specialist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RejectIssueRequest {
    @SerializedName("issue")
    @Expose
    public int issue;

    public RejectIssueRequest(int issue) {
        this.issue = issue;
    }
}
