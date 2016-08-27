package com.phistory.data.dao.impl;

import java.util.List;

import com.phistory.data.dao.generic.Dao;
import com.phistory.data.model.tyre.Tyre;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
