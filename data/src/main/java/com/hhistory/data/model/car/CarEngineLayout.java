package com.hhistory.data.model.car;

import com.hhistory.data.model.engine.Engine;

/**
 * Enumeration of the different {@link Engine} position layouts a {@link Car} can have
 */
public enum CarEngineLayout {

    FRONT_ENGINED("frontEngined"),
    MID_ENGINED("midEngined"),
    REAR_ENGINED("rearEngined");

    private String name;

    CarEngineLayout(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
