package com.phistory.data.dao.sql;

import com.phistory.data.model.car.CarInternetContent;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface SqlCarInternetContentDAO extends SqlDAO<CarInternetContent> {

    List<CarInternetContent> getByCarId(Long carId);
}
