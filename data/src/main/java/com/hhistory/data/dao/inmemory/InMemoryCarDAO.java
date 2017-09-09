package com.hhistory.data.dao.inmemory;

import com.hhistory.data.command.CarQueryCommand;
import com.hhistory.data.model.Manufacturer;
import com.hhistory.data.model.car.Car;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface InMemoryCarDAO extends InMemoryDAO<Car, Long> {

    Car getCarByPictureId(Long pictureId);

    Car getByQueryCommand(CarQueryCommand queryCommand);

    List<Car> getByQueryCommandOrderedByProductionStartDate(CarQueryCommand queryCommand);

    long countVisibleCars(Manufacturer manufacturer);
}
