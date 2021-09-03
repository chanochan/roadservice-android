package com.example.roadservice.backend.io.global;

import com.example.roadservice.models.Machine;
import com.example.roadservice.models.MissionType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MissionTypeResponse {
    public static final int CODE = 3;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MissionType toMissionType() {
        return new MissionType(id, name);
    }
}
