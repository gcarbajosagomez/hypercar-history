package com.hhistory.mvc.manufacturer;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by gonzalo on 6/01/17.
 */
public enum Manufacturer {
    PAGANI("pagani", "p");

    private String name;
    private String shortName;

    Manufacturer(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public static Optional<Manufacturer> mapShortName(String name) {
        return Stream.of(Manufacturer.values())
                     .filter(value -> value.getShortName()
                                           .toLowerCase()
                                           .equals(name.toLowerCase()))
                     .findFirst();
    }
}
