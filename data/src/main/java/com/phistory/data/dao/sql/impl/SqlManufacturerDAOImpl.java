package com.phistory.data.dao.sql.impl;

import com.phistory.data.dao.sql.SqlManufacturerDAO;
import com.phistory.data.model.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
@Transactional
@Component
public class SqlManufacturerDAOImpl extends SqlDAOImpl<Manufacturer> implements SqlManufacturerDAO {

    @Autowired
    public SqlManufacturerDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
