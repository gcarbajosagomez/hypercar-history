package com.phistory.data.dao.sql.impl;

import java.util.List;

import com.phistory.data.dao.SQLDAO;
import com.phistory.data.model.engine.Engine;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.phistory.data.model.GenericEntity.ID_FIELD;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class SQLEngineDAO extends SQLDAO<Engine, Long>
{
	@SuppressWarnings("unchecked")
	@Override
    public List<Engine> getAll()
    {
        return super.getCurrentSession().createQuery("FROM Engine").list();
    }
    
    @Override
    public Engine getById(Long id)
    {
        Query q = getCurrentSession().createQuery("FROM Engine AS engine"
                                               + " WHERE engine.id = :id");
        q.setParameter(ID_FIELD, id);
        return (Engine) q.uniqueResult();
    }
}