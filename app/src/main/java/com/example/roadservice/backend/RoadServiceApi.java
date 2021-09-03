package com.example.roadservice.backend;

import com.example.roadservice.backend.io.accounts.LoginRequest;
import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.accounts.ProfileRequest;
import com.example.roadservice.backend.io.accounts.ProfileResponse;
import com.example.roadservice.backend.io.accounts.RegisterRequest;
import com.example.roadservice.backend.io.accounts.RegisterResponse;
import com.example.roadservice.backend.io.citizen.AddIssueRequest;
import com.example.roadservice.backend.io.citizen.AddIssueResponse;
import com.example.roadservice.backend.io.citizen.CurrentIssueRequest;
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

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RoadServiceApi {
    private static final String TAG = "API";
    private final RoadServiceApiInterface api;

    public RoadServiceApi() {
        api = RetrofitInstance.getApi().create(RoadServiceApiInterface.class);
    }

    public RegionsResponse regions() throws IOException {
        Call<RegionsResponse> call = api.regions();
        Response<RegionsResponse> response = call.execute();
        return response.body();
    }

    public List<MachineResponse> machines() throws IOException {
        Call<List<MachineResponse>> call = api.machines();
        Response<List<MachineResponse>> response = call.execute();
        return response.body();
    }

    public List<SkillResponse> skills() throws IOException {
        Call<List<SkillResponse>> call = api.skills();
        Response<List<SkillResponse>> response = call.execute();
        return response.body();
    }

    public List<MissionTypeResponse> missionTypes() throws IOException {
        Call<List<MissionTypeResponse>> call = api.missionTypes();
        Response<List<MissionTypeResponse>> response = call.execute();
        return response.body();
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

    public AddIssueResponse addIssue(AddIssueRequest reqData) throws IOException {
        Call<AddIssueResponse> call = api.addIssue(reqData);
        Response<AddIssueResponse> response = call.execute();
        return response.body();
    }

    public RateIssueResponse rateIssue(RateIssueRequest reqData) throws IOException {
        Call<RateIssueResponse> call = api.rateIssue(reqData);
        Response<RateIssueResponse> response = call.execute();
        return response.body();
    }

    public List<PendingIssueResponse> pendingIssuesList() throws IOException {
        Call<List<PendingIssueResponse>> call = api.pendingIssuesList();
        Response<List<PendingIssueResponse>> response = call.execute();
        return response.body();
    }

    public RejectIssueResponse rejectIssue(RejectIssueRequest reqData) throws IOException {
        Call<RejectIssueResponse> call = api.rejectIssue(reqData);
        Response<RejectIssueResponse> response = call.execute();
        return response.body();
    }

    public CreateMissionResponse createMission(CreateMissionRequest reqData) throws IOException {
        Call<CreateMissionResponse> call = api.createMission(reqData);
        Response<CreateMissionResponse> response = call.execute();
        return response.body();
    }

    public CurrentMissionResponse currentMission() throws IOException {
        Call<CurrentMissionResponse> call = api.currentMission();
        Response<CurrentMissionResponse> response = call.execute();
        if (response.code() == 200) {
            CurrentMissionResponse resp = response.body();
            if (resp != null)
                resp.status = true;
            return resp;
        }
        CurrentMissionResponse resp = new CurrentMissionResponse();
        resp.status = false;
        return resp;
    }

    public EndMissionResponse endMission(EndMissionRequest reqData) throws IOException {
        Call<EndMissionResponse> call = api.endMission(reqData);
        Response<EndMissionResponse> response = call.execute();
        return response.body();
    }
}
