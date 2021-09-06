package com.example.roadservice.backend.threads.global;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.global.RegionsResponse;
import com.example.roadservice.backend.io.accounts.RegisterResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;
import com.example.roadservice.models.County;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.Province;

import java.util.ArrayList;

public class RegionsThread extends BaseBackendThread {
    private static final String TAG = "RegionsThread";

    public RegionsThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected Object backendMethod() {
        try {
            RegionsResponse resp = new RoadServiceApi().regions();
            if (resp == null) {
                Log.d(TAG, "Empty response in regions thread");
                return resp;
            }
            ArrayList<Province> provinces = new ArrayList<>();
            ArrayList<County> counties = new ArrayList<>();
            for (RegionsResponse.Province province :
                    resp.provinces) {
                Province provinceModel = new Province(province.pk, province.name);
                provinces.add(provinceModel);
                Log.d(TAG, province.name);
                for (RegionsResponse.Province.County county :
                        province.counties) {
                    County countyModel = new County(county.pk, county.provinceId, county.name);
                    counties.add(countyModel);
                    Log.d(TAG, county.name);
                }
            }
            Database.setProvinces(provinces);
            Database.setCounties(counties);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return RegisterResponse.CODE;
    }
}
