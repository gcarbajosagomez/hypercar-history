package com.phistory.data.dao.sql;

import com.phistory.data.model.Manufacturer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import static com.phistory.data.dao.sql.SqlManufacturerRepository.MANUFACTURER_REPOSITORY;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
@Repository(MANUFACTURER_REPOSITORY)
public interface SqlManufacturerRepository extends CrudRepository<Manufacturer, Long> {

    String MANUFACTURER_REPOSITORY = "manufacturerRepository";
}
