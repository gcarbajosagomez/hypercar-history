package com.phistory.data.model.car;

import com.phistory.data.model.engine.Engine;

/**
 * Enumeration of the different {@link Engine} layouts a {@link Car} can have
 * <p>
 * Created by gonzalo on 12/02/17.
 */
public enum CarEngineDisposition {

    LONGITUDINAL("longitudinal"),
    TRANSVERSAL("transversal");

    private String name;

    CarEngineDisposition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
