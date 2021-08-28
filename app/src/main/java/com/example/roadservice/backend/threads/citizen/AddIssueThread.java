package com.example.roadservice.backend.threads.citizen;

import android.os.Handler;

import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.citizen.AddIssueRequest;
import com.example.roadservice.backend.io.citizen.AddIssueResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class AddIssueThread extends BaseBackendThread {
    public AddIssueThread(Handler handler, AddIssueRequest request) {
        super(handler, request);
    }

    @Override
    protected AddIssueResponse backendMethod() {
        return null;
    }

    @Override
    protected int getResponseCode() {
        // TODO
        return LoginResponse.CODE;
    }
}
