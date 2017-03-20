package com.phistory.data.dao.sql;

import com.phistory.data.model.car.Car;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface SqlCarDAO extends SqlDAO<Car> {

    List<Car> getAllOrderedByProductionStartDate();
}
