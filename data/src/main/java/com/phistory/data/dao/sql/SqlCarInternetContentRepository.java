package com.phistory.data.dao.sql;

import com.phistory.data.model.car.CarInternetContent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import static com.phistory.data.dao.sql.SqlCarInternetContentRepository.CAR_INTERNET_CONTENT_REPOSITORY;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
@Repository(value = CAR_INTERNET_CONTENT_REPOSITORY)
public interface SqlCarInternetContentRepository extends CrudRepository<CarInternetContent, Long> {

    String CAR_INTERNET_CONTENT_REPOSITORY = "carInternetContentRepository";
}
