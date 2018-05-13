package com.hhistory.data.dao.sql;

import com.hhistory.data.model.car.Car;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface SqlCarDAO extends SqlDAO<Car> {

    String SQL_CAR_DAO = "sqlCarDAO";

    List<Car> getAllOrderedByProductionStartDate();

    Car getCarByPictureId(Long pictureId);
}
