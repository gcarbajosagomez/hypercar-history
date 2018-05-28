package com.hhistory.data.dao.sql.impl;

import com.hhistory.data.dao.sql.SqlCarDAO;
import com.hhistory.data.model.car.Car;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static com.hhistory.data.dao.sql.impl.SqlCarDAOImpl.SQL_CAR_DAO;

/**
 * @author Gonzalo
 */
@Transactional
@Component(SQL_CAR_DAO)
public class SqlCarDAOImpl extends AbstractSqlDAO<Car> implements SqlCarDAO {

    @Inject
    public SqlCarDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Car getCarByPictureId(Long pictureId) {
        TypedQuery<Car> query = super.getEntityManager()
                                     .createQuery("SELECT car AS car " +
                                                  "FROM Picture AS picture " +
                                                  "WHERE picture.id = :pictureId",
                                                  Car.class);

        query.setParameter("pictureId", pictureId);

        return query.getSingleResult();
    }
}
