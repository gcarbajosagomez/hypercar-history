package com.phistory.data.model.tyre;

/**
 * Created by gonzalo on 4/12/16.
 */
public enum TyreType {

    SPORT("sport"),
    GENERAL_PURPOSE("generalPurpose"),
    TRACK("track"),
    RALLY("rally"),
    DRIFT("drift");

    private String name;

    TyreType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
