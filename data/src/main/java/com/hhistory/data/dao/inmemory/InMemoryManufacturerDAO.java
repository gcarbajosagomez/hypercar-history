package com.hhistory.data.dao.inmemory;

import com.hhistory.data.model.Manufacturer;

import java.util.Optional;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface InMemoryManufacturerDAO extends InMemoryDAO<Manufacturer, Long> {

    Optional<Manufacturer> getByName(String manufacturerName);
}
