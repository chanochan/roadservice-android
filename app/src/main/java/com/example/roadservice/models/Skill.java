package com.example.roadservice.models;

public class Skill {
    public int id;
    public String name;

    public Skill(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
