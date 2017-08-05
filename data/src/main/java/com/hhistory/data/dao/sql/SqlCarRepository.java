package com.hhistory.data.dao.sql;

import com.hhistory.data.model.car.Car;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static com.hhistory.data.dao.sql.SqlCarRepository.CAR_REPOSITORY;

/**
 * Created by Gonzalo Carbajosa on 19/03/17.
 */
@Repository(CAR_REPOSITORY)
public interface SqlCarRepository extends CrudRepository<Car, Long> {

    String CAR_REPOSITORY = "carRepository";

    @Modifying
    @Query("DELETE FROM Car car WHERE car.id = :carId")
    void delete(@Param("carId") Long carId);
}
