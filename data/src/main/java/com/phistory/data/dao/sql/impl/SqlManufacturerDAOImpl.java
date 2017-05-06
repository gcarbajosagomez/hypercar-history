package com.phistory.data.dao.sql.impl;

import com.phistory.data.dao.sql.SqlManufacturerDAO;
import com.phistory.data.model.Manufacturer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static com.phistory.data.dao.sql.impl.SqlManufacturerDAOImpl.SQL_MANUFACTURER_DAO;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
@Transactional
@Component(SQL_MANUFACTURER_DAO)
public class SqlManufacturerDAOImpl extends AbstractSqlDAO<Manufacturer> implements SqlManufacturerDAO {

    @Inject
    public SqlManufacturerDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
