package com.phistory.data.dao.sql;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.model.GenericEntity;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface SqlContentSearchDAO {

    void indexPreviouslyStoredDatabaseRecords();

    List<GenericEntity> searchContent(SearchCommand searchCommand);
}
