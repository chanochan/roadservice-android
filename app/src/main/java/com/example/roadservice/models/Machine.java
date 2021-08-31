package com.example.roadservice.models;

public class Machine {
    public int id;
    public String name;

    public Machine(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
