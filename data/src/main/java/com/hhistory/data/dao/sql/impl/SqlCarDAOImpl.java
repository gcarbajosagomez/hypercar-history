package com.hhistory.data.dao.sql.impl;

import com.hhistory.data.dao.sql.SqlCarDAO;
import com.hhistory.data.model.car.Car;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
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
    public List<Car> getAllOrderedByProductionStartDate() {
        return super.getCurrentSession()
                    .createQuery("SELECT car.model AS model, " +
                                 "       car.id AS id " +
                                 "FROM Car AS car " +
                                 "ORDER BY car.productionStartDate ASC, " +
                                 "         car.model ASC")
                    .setResultTransformer(Transformers.aliasToBean(Car.class))
                    .list();
    }
}
