package com.example.roadservice.backend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RoadServiceApiInterface {
    @POST("/update-location/")
    Call<UpdateLocationResponse> updateLocation(@Body UpdateLocationRequest data);
}
