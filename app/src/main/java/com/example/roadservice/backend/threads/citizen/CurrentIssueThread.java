package com.example.roadservice.backend.threads.citizen;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.accounts.LoginRequest;
import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.citizen.CurrentIssueRequest;
import com.example.roadservice.backend.io.citizen.CurrentIssueResponse;
import com.example.roadservice.backend.io.team.CurrentMissionResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class CurrentIssueThread extends BaseBackendThread {
    public CurrentIssueThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected CurrentIssueResponse backendMethod() {
        try {
            CurrentIssueResponse resp = new RoadServiceApi().currentIssue((CurrentIssueRequest) request);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return CurrentIssueResponse.CODE;
    }
}
