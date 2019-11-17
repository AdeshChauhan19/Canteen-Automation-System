package com.example.canteenautomationsystem.Controller;

public class Category {

    String name,id;

    public Category(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return  name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
