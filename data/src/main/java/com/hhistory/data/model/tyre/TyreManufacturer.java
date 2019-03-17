package com.hhistory.data.model.tyre;

/**
 * Enumeration of the different tyre manufacturers there are
 * <p>
 * Created by gonzalo on 4/12/16.
 */
public enum TyreManufacturer {

    PIRELLI("Pirelli"),
    MICHELIN("Michelin"),
    DUNLOP("Dunlop"),
    BRIDGESTONE("Bridgestone"),
    CONTINENTAL("Continental"),
    KUMHO("Kumho"),
    TOYO("Toyo"),
    YOKOHAMA("Yokohama"),
    MAXXIS("Maxxis"),
    GOODYEAR("Goodyear"),
    UNKNOWN("Unknown");

    private String name;

    TyreManufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
