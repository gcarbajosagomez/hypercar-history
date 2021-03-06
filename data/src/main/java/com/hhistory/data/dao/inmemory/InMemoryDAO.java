package com.hhistory.data.dao.inmemory;

import com.hhistory.data.model.GenericEntity;

import java.util.List;

/**
 * Created by gonzalo on 11/4/16.
 */
public interface InMemoryDAO<TYPE extends GenericEntity, IDENTIFIER> {

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

    List<TYPE> getEntities();
}
