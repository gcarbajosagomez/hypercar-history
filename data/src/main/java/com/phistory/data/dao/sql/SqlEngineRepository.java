package com.phistory.data.dao.sql;

import com.phistory.data.model.engine.Engine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
@Repository
public interface SqlEngineRepository extends CrudRepository<Engine, Long> {
}
