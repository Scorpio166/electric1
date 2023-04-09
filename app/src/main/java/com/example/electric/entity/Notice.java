package com.example.electric.entity;

public class Notice {
    private String name;
    private String time;
    private String type;
    private int isDo;//0表示未受理

    public int getIsDo() {
        return isDo;
    }

    public void setIsDo(int isDo) {
        this.isDo = isDo;
    }

    public Notice(String name, String time, String type, int isDo) {
        this.name = name;
        this.time = time;
        this.type = type;
        this.isDo = isDo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
