package com.hhistory.data.dao.sql;

import com.hhistory.data.model.car.Car;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface SqlCarDAO extends SqlDAO<Car> {

    String SQL_CAR_DAO = "sqlCarDAO";

    Car getCarByPictureId(Long pictureId);
}
