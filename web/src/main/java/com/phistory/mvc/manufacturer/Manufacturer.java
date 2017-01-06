package com.phistory.mvc.manufacturer;

/**
 * Created by gonzalo on 6/01/17.
 */
public enum Manufacturer {
    PAGANI("pagani");

    private String name;

    Manufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
