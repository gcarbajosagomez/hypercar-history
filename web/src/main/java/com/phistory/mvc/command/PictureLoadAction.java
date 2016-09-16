package com.phistory.mvc.command;

import com.phistory.data.model.Picture;

import java.util.stream.Stream;

/**
 * Enumeration of the different actions we can perform when loading {@link Picture}s
 *
 * Created by gonzalo on 8/30/16.
 */
public enum PictureLoadAction {
    LOAD_CAR_PICTURE("picture"), LOAD_CAR_PREVIEW("preview"), LOAD_MANUFACTURER_LOGO("logo");

    private String name;

    PictureLoadAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PictureLoadAction map(String name) {
        return Stream.of(PictureLoadAction.values()).filter(action -> action.getName().toLowerCase().equals(name.toLowerCase()))
                                                    .findFirst()
                                                    .orElseGet(null);
    }
}
