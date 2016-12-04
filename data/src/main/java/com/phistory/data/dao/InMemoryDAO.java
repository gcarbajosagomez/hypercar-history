package com.phistory.data.dao;

import com.phistory.data.model.GenericEntity;

/**
 * Created by gonzalo on 11/4/16.
 */
public interface InMemoryDAO<TYPE extends GenericEntity, IDENTIFIER> {
    int LOAD_ENTITIES_DELAY = 1800000;

    /**
     * Load all the {@link TYPE} entities there are on the DB
     */
    void loadEntitiesFromDB();

    /**
     * Load the {@link TYPE} entity from the DB whose {@link GenericEntity#getId()} matches the supplied {@code id}
     */
    void loadEntityFromDB(IDENTIFIER id);

    /**
     * Remove the {@link TYPE} entity from the memory cache whose {@link GenericEntity#getId()} matches the supplied {@code id}
     */
    void removeEntity(IDENTIFIER id);

    /**
     * Get the {@link TYPE} whose {@link TYPE#getId()} matches the {@code id} supplied
     *
     * @param id
     * @return The {@link TYPE} found if any, null otherwise
     */
    TYPE getById(IDENTIFIER id);
}
