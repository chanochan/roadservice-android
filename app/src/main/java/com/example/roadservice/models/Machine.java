package com.example.roadservice.models;

import org.jetbrains.annotations.NotNull;

public class Machine {
    public int id;
    public String name;

    public Machine(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
