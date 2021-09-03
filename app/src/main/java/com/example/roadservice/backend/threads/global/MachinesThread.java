package com.example.roadservice.backend.threads.global;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.global.MachineResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.Machine;

import java.util.ArrayList;
import java.util.List;

public class MachinesThread extends BaseBackendThread {
    public MachinesThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected Object backendMethod() {
        try {
            List<MachineResponse> resp = new RoadServiceApi().machines();
            if (resp == null) {
                Log.d("SHIT", "Empty response in machines thread");
                return resp;
            }
            List<Machine> machines = new ArrayList<>();
            for (MachineResponse respItem : resp)
                machines.add(respItem.toMachine());
            Database.setMachines(machines);
            Log.d("MACHINES", machines.toString());
            return resp;
        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return MachineResponse.CODE;
    }
}
