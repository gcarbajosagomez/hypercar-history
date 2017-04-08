package com.phistory.data.dao.sql;

import com.phistory.data.model.car.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import static com.phistory.data.dao.sql.SqlCarRepository.CAR_REPOSITORY;

/**
 * Created by Gonzalo Carbajosa on 19/03/17.
 */
@Repository(CAR_REPOSITORY)
public interface SqlCarRepository extends CrudRepository<Car, Long> {

    String CAR_REPOSITORY = "carRepository";
}
