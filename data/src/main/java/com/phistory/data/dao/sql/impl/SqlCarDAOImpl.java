package com.phistory.data.dao.sql.impl;

import com.phistory.data.dao.sql.SqlCarDAO;
import com.phistory.data.model.car.Car;
import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Gonzalo
 */
@Transactional
@Component("sqlCarDAO")
public class SqlCarDAOImpl extends AbstractSqlDAO<Car> implements SqlCarDAO {

    @Inject
    public SqlCarDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Car> getAllOrderedByProductionStartDate() {
        Query query = super.getCurrentSession()
                           .createQuery("SELECT car.model AS model, car.id AS id"
                                        + " FROM Car AS car"
                                        + " ORDER BY car.productionStartDate ASC,"
                                        + "          car.model ASC");

        query.setResultTransformer(new AliasToBeanResultTransformer(Car.class));
        return query.list();
    }
}
