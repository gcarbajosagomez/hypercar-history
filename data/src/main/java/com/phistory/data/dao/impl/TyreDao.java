package com.tcp.data.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcp.data.dao.generic.Dao;
import com.tcp.data.model.tyre.Tyre;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class TyreDao extends Dao<Tyre, Long>
{
	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings("unchecked")
	@Override
    public List<Tyre> getAll()
    {
        List<Tyre> tyres = null;       
        tyres = getCurrentSession().createQuery("FROM Tyre").list();
        
        return tyres;        
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public Tyre getById(Long id)
    {
        Tyre tyre = null;
        
        Query q = getCurrentSession().createQuery("FROM Tyre AS tyre"
                                               + " WHERE tyre.id = :id");
        q.setParameter("id", id);
        tyre = (Tyre) q.uniqueResult();
        
        return tyre;        
    }
}
