package com.phistory.data.dao.sql;

import com.phistory.data.model.car.CarInternetContent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
@Repository
public interface SqlCarInternetContentRepository extends CrudRepository<CarInternetContent, Long> {
}
