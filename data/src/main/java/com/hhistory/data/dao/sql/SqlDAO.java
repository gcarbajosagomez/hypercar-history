package com.hhistory.data.dao.sql;

import com.hhistory.data.command.SearchCommand;
import com.hhistory.data.model.GenericEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface SqlDAO<TYPE extends GenericEntity> {

    List<TYPE> getByCriteria(SearchCommand searchCommand);

    Session getCurrentSession();
}
