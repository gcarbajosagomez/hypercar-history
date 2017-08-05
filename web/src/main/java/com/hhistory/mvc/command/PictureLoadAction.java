package com.hhistory.mvc.command;

import com.hhistory.data.model.picture.Picture;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Enumeration of the different actions we can perform when loading {@link Picture}s
 *
 * Created by gonzalo on 8/30/16.
 */
@Slf4j
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
        Optional<PictureLoadAction> action = Stream.of(PictureLoadAction.values())
                                                   .filter(value -> value.getName().toLowerCase().equals(name.toLowerCase()))
                                                   .findFirst();
        if (!action.isPresent()) {
            log.error("No PictureLoadAction found for name: {}", name);
            return null;
        } return action.get();
    }
}
