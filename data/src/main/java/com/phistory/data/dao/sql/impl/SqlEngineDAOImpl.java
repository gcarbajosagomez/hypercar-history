package com.phistory.data.dao.sql.impl;

import com.phistory.data.dao.sql.SqlEngineDAO;
import com.phistory.data.model.engine.Engine;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.phistory.data.model.GenericEntity.ID_FIELD;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class SqlEngineDAOImpl extends SqlDAOImpl<Engine, Long> implements SqlEngineDAO
{
    @Autowired
    public SqlEngineDAOImpl(SessionFactory sessionFactory, EntityManager entityManager) {
        super(sessionFactory, entityManager);
    }

	@SuppressWarnings("unchecked")
	@Override
    public List<Engine> getAll()
    {
        return super.openSession().createQuery("FROM Engine").list();
    }
    
    @Override
    public Engine getById(Long id)
    {
        Query q = super.openSession().createQuery("FROM Engine AS engine"
                                               + " WHERE engine.id = :id");
        q.setParameter(ID_FIELD, id);
        return (Engine) q.uniqueResult();
    }
}