package com.example.roadservice.backend;

import retrofit2.Call;
import retrofit2.Response;

public class RoadServiceApi {
    private static final String TAG = "API";
    private final RoadServiceApiInterface api;

    public RoadServiceApi() {
        api = RetrofitInstance.getApi().create(RoadServiceApiInterface.class);
    }

    public UpdateLocationResponse updateLocation(UpdateLocationRequest reqData) throws Exception {
        Call<UpdateLocationResponse> call = api.updateLocation(reqData);
        Response<UpdateLocationResponse> response = call.execute();
        return response.body();
    }
}
