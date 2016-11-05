package com.phistory.data.dao.inmemory;

import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by gonzalo on 11/4/16.
 */
@EnableScheduling
public interface InMemoryDAO<TYPE> {
    int LOAD_ENTITIES_DELAY = 600000;

    /**
     * Load all the {@link TYPE} entities there are on the DB
     */
    void loadEntitiesFromDB();
}
