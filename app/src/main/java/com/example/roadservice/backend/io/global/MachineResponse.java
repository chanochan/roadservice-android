package com.example.roadservice.backend.io.global;

import com.example.roadservice.models.Machine;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MachineResponse {
    public static final int CODE = 2;

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

    public Machine toMachine() {
        return new Machine(id, name);
    }
}
