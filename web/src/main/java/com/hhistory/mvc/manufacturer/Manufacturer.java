package com.hhistory.mvc.manufacturer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Optional;
import java.util.stream.Stream;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

/**
 * Created by gonzalo on 6/01/17.
 */
@JsonFormat(shape = OBJECT)
public enum Manufacturer {
    PAGANI("pagani", "p"),
    BUGATTI("bugatti", "b"),
    KOENIGSEGG("koenigsegg", "k");

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

    @Override
    public String toString() {
        return this.getName();
    }
}
