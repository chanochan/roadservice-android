package com.example.roadservice.backend.threads.specialist;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.specialist.PendingIssueResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;
import com.example.roadservice.models.Database;

import java.util.ArrayList;
import java.util.List;

public class PendingIssuesListThread extends BaseBackendThread {
    private static final String TAG = "PendingIssuesListThread";

    public PendingIssuesListThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected List<PendingIssueResponse> backendMethod() {
        try {
            List<PendingIssueResponse> resp = new RoadServiceApi().pendingIssuesList();
            if (resp == null)
                Log.d(TAG, "Empty response in here!!");
            else
                return resp;
            System.out.println(Database.getToken(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    protected int getResponseCode() {
        return PendingIssueResponse.CODE;
    }
}
