package com.phistory.data.dao.sql.impl;

import java.util.List;

import com.phistory.data.model.Manufacturer;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.phistory.data.dao.DAO;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class ManufacturerDAO extends DAO<Manufacturer, Long>
{
	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings("unchecked")
	@Override
    public List<Manufacturer> getAll() {
        return getCurrentSession().createQuery("FROM Manufacturer AS manufacturer"
                                            + " ORDER BY manufacturer.name ASC").list();
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public Manufacturer getById(Long id)
    {
        Query q = getCurrentSession().createQuery("FROM Manufacturer AS manufacturer"
                                               + " WHERE manufacturer.id = :id");
        q.setParameter("id", id);
        return (Manufacturer) q.uniqueResult();
    }
}