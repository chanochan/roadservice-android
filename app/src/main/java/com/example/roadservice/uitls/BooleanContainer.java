package com.example.roadservice.uitls;

public class BooleanContainer {
    private boolean value;

    public BooleanContainer(boolean value) {
        this.value = value;
    }

    public void set() {
        value = true;
    }

    public void reset() {
        value = false;
    }

    public boolean get() {
        return value;
    }
}
