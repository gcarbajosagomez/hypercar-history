package com.phistory.data.dao.sql;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.model.GenericEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface SqlDAO<TYPE extends GenericEntity, IDENTIFIER> {

    /**
     * Get all the entities of a given type
     *
     * @return The list of entities
     */
   List<TYPE> getAll();

    /**
     * Get an entity by id
     *
     * @param id
     * @return The given entity
     */
    TYPE getById(IDENTIFIER id);

    List<TYPE> getByCriteria(SearchCommand searchCommand);

    Session openSession();

    void saveOrEdit(TYPE entity);

    void delete(TYPE entity);
}
