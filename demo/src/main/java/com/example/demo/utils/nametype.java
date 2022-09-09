package com.example.demo.utils;

public class nametype {
    public String name;
    public String type;

    public nametype(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "nametype{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
