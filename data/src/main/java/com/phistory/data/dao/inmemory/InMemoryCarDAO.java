package com.phistory.data.dao.inmemory;

import com.phistory.data.command.CarQueryCommand;
import com.phistory.data.model.car.Car;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface InMemoryCarDAO extends InMemoryDAO<Car, Long> {

    Car getCarByPictureId(Long pictureId);

    Car getByQueryCommand(CarQueryCommand queryCommand);

    List<Car> getAllVisibleOrderedByProductionStartDate();

    long countVisibleCars();
}
