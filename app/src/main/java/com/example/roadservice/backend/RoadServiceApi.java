package com.example.roadservice.backend;

import com.example.roadservice.backend.io.accounts.LoginRequest;
import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.accounts.ProfileRequest;
import com.example.roadservice.backend.io.accounts.ProfileResponse;
import com.example.roadservice.backend.io.accounts.RegisterRequest;
import com.example.roadservice.backend.io.accounts.RegisterResponse;
import com.example.roadservice.backend.io.citizen.CurrentIssueRequest;
import com.example.roadservice.backend.io.citizen.CurrentIssueResponse;
import com.example.roadservice.backend.io.team.UpdateLocationRequest;
import com.example.roadservice.backend.io.team.UpdateLocationResponse;

import java.io.IOException;

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

    public LoginResponse login(LoginRequest reqData) throws Exception {
        Call<LoginResponse> call = api.login(reqData);
        Response<LoginResponse> response = call.execute();
        return response.body();
    }

    public ProfileResponse profile(ProfileRequest reqData) throws Exception {
        Call<ProfileResponse> call = api.profile();
        Response<ProfileResponse> response = call.execute();
        return response.body();
    }

    public RegisterResponse register(RegisterRequest reqData) throws Exception {
        Call<RegisterResponse> call = api.register(reqData);
        Response<RegisterResponse> response = call.execute();
        return response.body();
    }

    public CurrentIssueResponse currentIssue(CurrentIssueRequest reqData) throws IOException {
        Call<CurrentIssueResponse> call = api.currentIssue();
        Response<CurrentIssueResponse> response = call.execute();
        return response.body();
    }
}
