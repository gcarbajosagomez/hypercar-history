package com.phistory.data.dao.impl;

import java.util.List;

import com.phistory.data.model.transmission.Transmission;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.phistory.data.dao.generic.Dao;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class TransmissionDao extends Dao<Transmission, Long>
{
	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings("unchecked")
	@Override
    public List<Transmission> getAll()
    {
        List<Transmission> transmissions = null;        
        transmissions = getCurrentSession().createQuery("FROM Transmission").list();
        
        return transmissions;        
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public Transmission getById(Long id)
    {
        Transmission transmission = null;
        
        Query q = getCurrentSession().createQuery("FROM Transmission "
                                               + " WHERE id = :id");
        q.setParameter("id", id);
        transmission = (Transmission) q.uniqueResult();
        
        return transmission;        
    }
}
