package com.phistory.data.dao.sql.impl;

import com.phistory.data.dao.sql.SqlManufacturerDAO;
import com.phistory.data.model.Manufacturer;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.phistory.data.model.GenericEntity.ID_FIELD;

/**
 * @author Gonzalo
 */
@Transactional
@Repository
public class SqlManufacturerDAOImpl extends SqlDAOImpl<Manufacturer, Long> implements SqlManufacturerDAO {

    @Autowired
    public SqlManufacturerDAOImpl(SessionFactory sessionFactory, EntityManager entityManager) {
        super(sessionFactory, entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Manufacturer> getAll() {
        return super.getCurrentSession().createQuery("FROM Manufacturer AS manufacturer"
                                                     + " ORDER BY manufacturer.name ASC")
                    .list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Manufacturer getById(Long id) {
        Query q = super.getCurrentSession().createQuery("FROM Manufacturer AS manufacturer"
                                                        + " WHERE manufacturer.id = :id");
        q.setParameter(ID_FIELD, id);
        return (Manufacturer) q.uniqueResult();
    }
}