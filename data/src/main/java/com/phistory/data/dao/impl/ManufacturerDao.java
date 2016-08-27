package com.tcp.data.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcp.data.dao.generic.Dao;
import com.tcp.data.model.Manufacturer;

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