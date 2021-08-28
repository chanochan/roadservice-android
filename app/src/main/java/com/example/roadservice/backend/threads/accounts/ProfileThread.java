package com.example.roadservice.backend.threads.accounts;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.accounts.ProfileRequest;
import com.example.roadservice.backend.io.accounts.ProfileResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class ProfileThread extends BaseBackendThread {
    public ProfileThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected ProfileResponse backendMethod() {
        try {
            return new RoadServiceApi().profile((ProfileRequest) request);
        } catch (Exception e) {
            // TODO handle exception
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return ProfileResponse.CODE;
    }
}
