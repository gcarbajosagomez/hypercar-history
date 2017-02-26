package com.phistory.data.dao.inmemory;

import com.phistory.data.model.Manufacturer;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface InMemoryManufacturerDAO extends InMemoryDAO<Manufacturer, Long> {

    Manufacturer getByName(String manufacturerName);
}
