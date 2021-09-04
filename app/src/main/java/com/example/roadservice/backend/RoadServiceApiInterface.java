package com.example.roadservice.backend;

import com.example.roadservice.backend.io.accounts.ChangePasswordRequest;
import com.example.roadservice.backend.io.accounts.ChangePasswordResponse;
import com.example.roadservice.backend.io.accounts.LoginRequest;
import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.accounts.ProfileResponse;
import com.example.roadservice.backend.io.accounts.RegisterRequest;
import com.example.roadservice.backend.io.accounts.RegisterResponse;
import com.example.roadservice.backend.io.citizen.AddIssueRequest;
import com.example.roadservice.backend.io.citizen.AddIssueResponse;
import com.example.roadservice.backend.io.citizen.CurrentIssueResponse;
import com.example.roadservice.backend.io.citizen.RateIssueRequest;
import com.example.roadservice.backend.io.citizen.RateIssueResponse;
import com.example.roadservice.backend.io.global.MachineResponse;
import com.example.roadservice.backend.io.global.MissionTypeResponse;
import com.example.roadservice.backend.io.global.RegionsResponse;
import com.example.roadservice.backend.io.global.SkillResponse;
import com.example.roadservice.backend.io.specialist.CreateMissionRequest;
import com.example.roadservice.backend.io.specialist.CreateMissionResponse;
import com.example.roadservice.backend.io.specialist.PendingIssueResponse;
import com.example.roadservice.backend.io.specialist.RejectIssueRequest;
import com.example.roadservice.backend.io.specialist.RejectIssueResponse;
import com.example.roadservice.backend.io.team.CurrentMissionResponse;
import com.example.roadservice.backend.io.team.EndMissionRequest;
import com.example.roadservice.backend.io.team.EndMissionResponse;
import com.example.roadservice.backend.io.team.UpdateLocationRequest;
import com.example.roadservice.backend.io.team.UpdateLocationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RoadServiceApiInterface {
    @GET("/api/regions/")
    Call<RegionsResponse> regions();

    @GET("/api/machinery-types/")
    Call<List<MachineResponse>> machines();

    @GET("/api/specialities/")
    Call<List<SkillResponse>> skills();

    @GET("/api/mission-types/")
    Call<List<MissionTypeResponse>> missionTypes();

    @POST("/api/serviceman/update-location/")
    Call<UpdateLocationResponse> updateLocation(@Body UpdateLocationRequest data);

    @POST("/api-token-auth/")
    Call<LoginResponse> login(@Body LoginRequest data);

    @GET("/api/user/")
    Call<ProfileResponse> profile();

    @POST("/api/signup/")
    Call<RegisterResponse> register(@Body RegisterRequest data);

    @POST("/api/change-password/")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest reqData);

    @GET("/api/citizen/issue/")
    Call<CurrentIssueResponse> currentIssue();

    @POST("/api/citizen/rate-issue/")
    Call<RateIssueResponse> rateIssue(@Body RateIssueRequest data);

    @POST("/api/citizen/report-issue/")
    Call<AddIssueResponse> addIssue(@Body AddIssueRequest data);

    @GET("/api/expert/issues/")
    Call<List<PendingIssueResponse>> pendingIssuesList();

    @POST("/api/expert/reject-issue/")
    Call<RejectIssueResponse> rejectIssue(@Body RejectIssueRequest data);

    @POST("/api/expert/accept-issue/")
    Call<CreateMissionResponse> createMission(@Body CreateMissionRequest data);

    @GET("/api/serviceman/mission/")
    Call<CurrentMissionResponse> currentMission();

    @POST("/api/serviceman/finish-mission/")
    Call<EndMissionResponse> endMission(@Body EndMissionRequest data);
}
