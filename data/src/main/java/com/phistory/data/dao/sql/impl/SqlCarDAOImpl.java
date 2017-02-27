package com.phistory.data.dao.sql.impl;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.dao.sql.SqlCarDAO;
import com.phistory.data.model.car.Car;
import com.phistory.data.query.command.SimpleDataConditionCommand;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.phistory.data.model.GenericEntity.ID_FIELD;

/**
 * @author Gonzalo
 */
@Transactional
@Repository
public class SqlCarDAOImpl extends SqlDAOImpl<Car, Long> implements SqlCarDAO {
    @Autowired
    public SqlCarDAOImpl(SessionFactory sessionFactory, EntityManager entityManager) {
        super(sessionFactory, entityManager);
    }

    @Override
    public List<Car> getAll() {
        SearchCommand searchCommand = new SearchCommand(Car.class,
                                                        null,
                                                        null,
                                                        null,
                                                        null,
                                                        null,
                                                        0,
                                                        0);
        List<Car> cars = super.getByCriteria(searchCommand);

        return cars;
    }

    public List<Car> getAllOrderedByProductionStartDate() {
        Query q = super.getCurrentSession().createQuery("SELECT car.model AS model, car.id AS id"
                                                        + " FROM Car AS car"
                                                        + " ORDER BY car.productionStartDate ASC,"
                                                        + "          car.model ASC");

        q.setResultTransformer(new AliasToBeanResultTransformer(Car.class));
        return q.list();
    }

    @Override
    public Car getById(Long carId) {
        Car car = null;

        Map<String, SimpleDataConditionCommand> conditionMap = new LinkedHashMap<>();
        Object[] values = {new Double(carId)};
        conditionMap.put(ID_FIELD,
                         new SimpleDataConditionCommand((SimpleDataConditionCommand.EntityConditionType.EQUAL), values));

        SearchCommand searchCommand = new SearchCommand(Car.class,
                                                        null,
                                                        conditionMap,
                                                        null,
                                                        null,
                                                        null,
                                                        0,
                                                        0);

        List<Car> results = getByCriteria(searchCommand);

        if (!results.isEmpty()) {
            car = results.get(0);
        }

        return car;
    }
}
