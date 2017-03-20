package com.phistory.data.dao.sql;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.model.GenericEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface SqlDAO<TYPE extends GenericEntity> {

    List<TYPE> getByCriteria(SearchCommand searchCommand);

    Session getCurrentSession();
}
