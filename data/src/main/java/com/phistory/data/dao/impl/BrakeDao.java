package com.phistory.data.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.phistory.data.dao.generic.Dao;
import com.phistory.data.model.brake.Brake;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class BrakeDao extends Dao<Brake, Long>
{	
	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings("unchecked")
	@Override
    public List<Brake> getAll()
    {
        List<Brake> brakes = null;       
        brakes = getCurrentSession().createQuery("FROM Brake").list();
        
        return brakes;        
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public Brake getById(Long id)
    {           
        Query q = getCurrentSession().createQuery("FROM Brake AS brake"
                                               + " WHERE brake.id = :id");
        q.setParameter("id", id);
        
        return (Brake) q.uniqueResult();
    }
}
