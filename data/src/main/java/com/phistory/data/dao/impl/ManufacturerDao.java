package com.phistory.data.dao.impl;

import java.util.List;

import com.phistory.data.model.Manufacturer;
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
public class ManufacturerDao extends Dao<Manufacturer, Long>
{
	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings("unchecked")
	@Override
    public List<Manufacturer> getAll()
    {
        List<Manufacturer> manufacturers = null;
        
        manufacturers = getCurrentSession().createQuery("FROM Manufacturer AS manufacturer"
                                                     + " ORDER BY manufacturer.name ASC").list();
        
        return manufacturers;        
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public Manufacturer getById(Long id)
    {
        Manufacturer manufacturer = null;
        
        Query q = getCurrentSession().createQuery("FROM Manufacturer AS manufacturer"
                                               + " WHERE manufacturer.id = :id");
        q.setParameter("id", id);
        manufacturer = (Manufacturer) q.uniqueResult();
        
        return manufacturer;        
    }
}