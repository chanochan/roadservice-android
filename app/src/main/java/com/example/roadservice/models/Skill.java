package com.example.roadservice.models;

import org.jetbrains.annotations.NotNull;

public class Skill {
    public int id;
    public String name;

    public Skill(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
