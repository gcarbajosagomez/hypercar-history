package com.hhistory.data.model.brake;

public enum BrakeType {
    DRUM("drum"),
    DISC("disc");

    private String name;

    BrakeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
