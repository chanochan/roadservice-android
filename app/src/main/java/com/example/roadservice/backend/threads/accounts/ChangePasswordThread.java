package com.example.roadservice.backend.threads.accounts;

import android.os.Handler;

import com.example.roadservice.backend.io.accounts.ChangePasswordResponse;
import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class ChangePasswordThread extends BaseBackendThread {
    public ChangePasswordThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected ChangePasswordResponse backendMethod() {
        return null;
    }

    @Override
    protected int getResponseCode() {
        // TODO
        return LoginResponse.CODE;
    }
}
