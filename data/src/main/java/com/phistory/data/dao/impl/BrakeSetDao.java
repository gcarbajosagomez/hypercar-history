package com.tcp.data.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcp.data.dao.generic.Dao;
import com.tcp.data.model.brake.BrakeSet;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class BrakeSetDao extends Dao<BrakeSet, Long>
{
	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings("unchecked")
	@Override
    public List<BrakeSet> getAll()
    {
        List<BrakeSet> brakeSets = null;       
        brakeSets = getCurrentSession().createQuery("FROM BrakeSet").list();
        
        return brakeSets;        
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public BrakeSet getById(Long id)
    {
        BrakeSet brakeSet = null;
        
        Query q = getCurrentSession().createQuery("FROM BrakeSet AS brakeSet"
                                               + " WHERE brakeSet.id = :id");
        q.setParameter("id", id);
        brakeSet = (BrakeSet) q.uniqueResult();
        
        return brakeSet;        
    }
}
