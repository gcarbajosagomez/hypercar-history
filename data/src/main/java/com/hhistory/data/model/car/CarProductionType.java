package com.hhistory.data.model.car;

/**
 * Enumeration of the different production types a {@link Car} can have
 *
 * @author gonzalo
 */
public enum CarProductionType {

    SERIES_RUN("seriesRun"),
    LIMITED_EDITION("limitedEdition"),
    ONE_OFF("oneOff"),
    PROTOTYPE("prototype");

    private String name;

    CarProductionType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
