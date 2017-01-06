package com.phistory.mvc.cms.command;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Enumeration of the different types of entity management DB queries (which entity class to load)
 * <p>
 * Created by gonzalo on 3/12/16.
 */
@Slf4j
public enum EntityManagementQueryType {
    RELOAD_CARS("reloadCars"),
    REMOVE_CAR("removeCar"),
    RELOAD_PICTURES("reloadPictures"),
    REMOVE_PICTURE("removePicture"),
    RELOAD_CAR_INTERNET_CONTENTS("reloadCarInternetContents"),
    REMOVE_CAR_INTERNET_CONTENTS("removeCarInternetContent"),
    RELOAD_MANUFACTURERS("reloadManufacturers"),
    REMOVE_MANUFACTURERS("removeManufacturers");

    private String name;

    public String getName() {
        return name;
    }

    EntityManagementQueryType(String name) {
        this.name = name;
    }

    public static EntityManagementQueryType map(String name) {
        Optional<EntityManagementQueryType> action = Stream.of(EntityManagementQueryType.values())
                                                           .filter(value -> value.getName()
                                                                                 .toLowerCase()
                                                                                 .equals(name.toLowerCase()))
                                                           .findFirst();
        if (!action.isPresent()) {
            log.error("No PictureLoadAction found for name: {}", name);
            return null;
        }
        return action.get();
    }
}
