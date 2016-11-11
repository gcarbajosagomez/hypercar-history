package com.phistory.data.dao;

/**
 * Created by gonzalo on 11/4/16.
 */
public interface InMemoryDAO<TYPE> {
    int LOAD_ENTITIES_DELAY = 600000;

    /**
     * Load all the {@link TYPE} entities there are on the DB
     */
    void loadEntitiesFromDB();
}
