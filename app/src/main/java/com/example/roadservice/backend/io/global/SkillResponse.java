package com.example.roadservice.backend.io.global;

import com.example.roadservice.models.Skill;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkillResponse {
    public static final int CODE = 1;

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

    public Skill toSkill() {
        return new Skill(id, name);
    }
}
