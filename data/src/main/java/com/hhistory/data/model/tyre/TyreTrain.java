package com.hhistory.data.model.tyre;

/**
 * @author Gonzalo
 */
public enum TyreTrain {

    FRONT("front"),
    REAR("rear");

    private String name;

    TyreTrain(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
