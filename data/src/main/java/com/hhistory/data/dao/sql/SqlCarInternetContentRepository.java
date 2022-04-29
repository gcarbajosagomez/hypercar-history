package com.hhistory.data.dao.sql;

import com.hhistory.data.model.car.CarInternetContent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hhistory.data.dao.sql.SqlCarInternetContentRepository.CAR_INTERNET_CONTENT_REPOSITORY;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
@Repository(value = CAR_INTERNET_CONTENT_REPOSITORY)
public interface SqlCarInternetContentRepository extends CrudRepository<CarInternetContent, Long> {

    String CAR_INTERNET_CONTENT_REPOSITORY = "carInternetContentRepository";

    @Query("""
            SELECT carInternetContent
            FROM CarInternetContent carInternetContent JOIN carInternetContent.car car
            WHERE car.id = :carId""")
    List<CarInternetContent> getByCarId(@Param("carId") Long carId);
}
