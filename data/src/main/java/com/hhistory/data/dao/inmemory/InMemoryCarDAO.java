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

    /**
     * Get all {@link Car}s whose {@link Car#visible} is true ordered by their {@link Car#productionStartDate} descending
     *
     * @return
     */
    List<Car> getAllVisibleOrderedByProductionStartDate(Manufacturer manufacturer);

    long countVisibleCars(Manufacturer manufacturer);
}