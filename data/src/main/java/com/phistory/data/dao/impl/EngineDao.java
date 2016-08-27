package com.phistory.data.dao.impl;

import java.util.List;

import com.phistory.data.dao.generic.Dao;
import com.phistory.data.model.engine.Engine;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class EngineDao extends Dao<Engine, Long>
{
	@SuppressWarnings("unchecked")
	@Override
    public List<Engine> getAll()
    {
        List<Engine> engines = null;        
        engines = getCurrentSession().createQuery("FROM Engine").list();
        
        return engines;        
    }
    
    @Override
    public Engine getById(Long id)
    {
        Engine engine = null;
        
        Query q = getCurrentSession().createQuery("FROM Engine AS engine"
                                               + " WHERE engine.id = :id");
        q.setParameter("id", id);
        engine = (Engine) q.uniqueResult();
        
        return engine;        
    }
}