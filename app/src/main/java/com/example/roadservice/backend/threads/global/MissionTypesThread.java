package com.example.roadservice.backend.threads.global;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.global.MissionTypeResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.MissionType;

import java.util.ArrayList;
import java.util.List;

public class MissionTypesThread extends BaseBackendThread {
    public MissionTypesThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected Object backendMethod() {
        try {
            List<MissionTypeResponse> resp = new RoadServiceApi().missionTypes();
            if (resp == null) {
                Log.d("SHIT", "Empty response in mission types thread");
                return null;
            }
            List<MissionType> missionTypes = new ArrayList<>();
            for (MissionTypeResponse respItem : resp)
                missionTypes.add(respItem.toMissionType());
            Database.setMissionTypes(missionTypes);
            return resp;
        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return MissionTypeResponse.CODE;
    }
}
