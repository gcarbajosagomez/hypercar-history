package com.phistory.data.service;

import com.phistory.data.dao.sql.SqlContentSearchDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Default implementation of {@link EntityIndexingService}
 * <p>
 * Created by Gonzalo Carbajosa on 19/03/17.
 */
@Component
@Slf4j
public class EntityIndexingServiceImpl implements EntityIndexingService, InitializingBean {

    private SqlContentSearchDAO sqlContentSearchDAO;

    @Autowired
    public EntityIndexingServiceImpl(SqlContentSearchDAO sqlContentSearchDAO) {
        this.sqlContentSearchDAO = sqlContentSearchDAO;
    }

    @Override
    public void indexEntities() {
        log.info("Indexing DB entities");
        this.sqlContentSearchDAO.indexPreviouslyStoredDatabaseRecords();
        log.info("DB entities successfully indexed");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.indexEntities();
    }
}
