package com.tcp.data.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcp.data.dao.generic.Dao;
import com.tcp.data.model.tyre.TyreSet;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class TyreSetDao extends Dao<TyreSet, Long>
{
	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings("unchecked")
	@Override
    public List<TyreSet> getAll() 
    {
        List<TyreSet> tyreSets = null;       
        tyreSets = getCurrentSession().createQuery("FROM TyreSet").list();
        
        return tyreSets;        
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public TyreSet getById(Long id)
    {
        TyreSet tyreSet = null;
        
        Query q = getCurrentSession().createQuery("FROM TyreSet AS tyreSet"
                                               + " WHERE tyreSet.id = :id");
        q.setParameter("id", id);
        tyreSet = (TyreSet) q.uniqueResult();
        
        return tyreSet;        
    }
}
