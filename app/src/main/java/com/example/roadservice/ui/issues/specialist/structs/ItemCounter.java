package com.example.roadservice.ui.issues.specialist.structs;

public class ItemCounter {
    private Object obj;
    private int count;

    public ItemCounter(Object obj, int count) {
        this.obj = obj;
        this.count = count;
    }

    public ItemCounter(Object obj) {
        this.obj = obj;
        this.count = 0;
    }

    public Object getObj() {
        return obj;
    }

    public int getCount() {
        return count;
    }

    public void increase() {
        this.count++;
    }

    public void decrease() {
        if (this.count > 0) this.count--;
    }
}
