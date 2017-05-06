package com.phistory.data.dao.sql;

import com.phistory.data.model.Manufacturer;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
public interface SqlManufacturerDAO extends SqlDAO<Manufacturer> {

    String SQL_MANUFACTURER_DAO = "sqlManufacturerDAO";
}
