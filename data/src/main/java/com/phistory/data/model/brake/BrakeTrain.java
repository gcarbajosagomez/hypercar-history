package com.phistory.data.model.brake;

/**
 * @author Gonzalo
 */
public enum BrakeTrain {
    FRONT("front"),
    REAR("rear");

    private String name;

    BrakeTrain(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
