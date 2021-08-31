package com.example.roadservice.backend.threads.specialist;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.specialist.CreateMissionRequest;
import com.example.roadservice.backend.io.specialist.CreateMissionResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class CreateMissionThread extends BaseBackendThread {
    public CreateMissionThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected CreateMissionResponse backendMethod() {
        try {
            CreateMissionResponse resp = new RoadServiceApi().createMission((CreateMissionRequest) request);
            if (resp == null) {
                Log.d("SHIT", "Empty response in create mission!!");
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
        return CreateMissionResponse.CODE;
    }
}
