package com.example.roadservice.backend.threads.citizen;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.citizen.AddIssueRequest;
import com.example.roadservice.backend.io.citizen.AddIssueResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class AddIssueThread extends BaseBackendThread {
    public AddIssueThread(Handler handler, AddIssueRequest request) {
        super(handler, request);
    }

    @Override
    protected AddIssueResponse backendMethod() {
        try {
            AddIssueResponse resp = new RoadServiceApi().addIssue((AddIssueRequest) request);
            if (resp == null) {
                Log.d("SHIT", "Empty response in add issue thread!!");
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
        return AddIssueResponse.CODE;
    }
}
