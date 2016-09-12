package com.phistory.mvc.cms.command;

import com.phistory.data.model.car.Car;

import java.util.stream.Stream;

/**
 * Enumeration of the different materials a {@link Car}'s body can be made of
 *
 * Created by gonzalo on 9/11/16.
 */
public enum CarBodyMaterial {
    CARBON_FIBER("carbonFiber"), ALUMINIUM("aluminium"), TITANIUM("titanium"), FIBER_GLASS("fiberGlass"), STEEL("steel");

    private String name;

    CarBodyMaterial(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CarBodyMaterial map(String name) {
        return Stream.of(CarBodyMaterial.values())
                     .filter(carBodyMaterial -> carBodyMaterial.getName().toLowerCase().equals(name.toLowerCase()))
                     .findFirst()
                     .get();
    }
}
