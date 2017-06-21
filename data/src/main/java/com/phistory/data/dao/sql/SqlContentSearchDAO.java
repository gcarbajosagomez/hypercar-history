package com.phistory.data.dao.sql;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.model.GenericEntity;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface SqlContentSearchDAO {

    /**
     * Trigger the index process of the previously stored objects in the database
     */
    void indexPreviouslyStoredDatabaseRecords();

    /**
     * Search content with Hibernate Search
     *
     * @param searchCommand
     * @return ContentSearchDto filled with the content entities to search
     */
    List<GenericEntity> searchContent(SearchCommand searchCommand);
}
