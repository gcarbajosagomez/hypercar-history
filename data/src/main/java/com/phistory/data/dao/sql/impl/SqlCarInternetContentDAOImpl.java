package com.phistory.data.dao.sql.impl;

import com.phistory.data.dao.sql.SqlCarInternetContentDAO;
import com.phistory.data.model.car.CarInternetContent;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.phistory.data.model.GenericEntity.ID_FIELD;

/**
 * Data Access Object class for {@link CarInternetContent}s
 *
 * @author gonzalo
 */
@Transactional
@Repository
public class SqlCarInternetContentDAOImpl extends SqlDAOImpl<CarInternetContent, Long> implements SqlCarInternetContentDAO {

    @Autowired
    public SqlCarInternetContentDAOImpl(SessionFactory sessionFactory, EntityManager entityManager) {
        super(sessionFactory, entityManager);
    }

    @Override
    public List<CarInternetContent> getAll() {
        return super.getCurrentSession().createQuery("FROM CarInternetContent").list();
    }

    @Override
    public CarInternetContent getById(Long id) {
        Query q = super.getCurrentSession().createQuery("FROM CarInternetContent AS internetContent"
                                                  + " WHERE internetContent.id = :id");
        q.setParameter(ID_FIELD, id);
        CarInternetContent carInternetContent = (CarInternetContent) q.uniqueResult();

        return carInternetContent;
    }

    /**
     * Get all the {@link CarInternetContent}s whose {@link CarInternetContent#car#id} matches the
     * carId supplied
     *
     * @param carId
     * @return The {@link List<CarInternetContent>} if everything went well, null otherwise
     */
    public List<CarInternetContent> getByCarId(Long carId) {
        Query q = super.getCurrentSession().createQuery("FROM CarInternetContent AS internetContent"
                                                  + " WHERE internetContent.car.id = :carId");
        q.setParameter("carId", carId);
        List<CarInternetContent> carInternetContents = q.list();

        return carInternetContents;
    }

}
