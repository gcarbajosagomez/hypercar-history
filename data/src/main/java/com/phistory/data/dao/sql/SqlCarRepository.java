package com.phistory.data.dao.sql;

import com.phistory.data.model.car.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gonzalo Carbajosa on 19/03/17.
 */
@Repository
public interface SqlCarRepository extends CrudRepository<Car, Long> {
}
