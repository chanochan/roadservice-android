package com.example.roadservice.backend.threads.accounts;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.accounts.LoginRequest;
import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class LoginThread extends BaseBackendThread {
    public LoginThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected LoginResponse backendMethod() {
        try {
            LoginResponse resp = new RoadServiceApi().login((LoginRequest) request);
            if (resp == null) {
                Log.d("SHIT", "Empty response in here!!");
            }
            return resp;
        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return LoginResponse.CODE;
    }
}
