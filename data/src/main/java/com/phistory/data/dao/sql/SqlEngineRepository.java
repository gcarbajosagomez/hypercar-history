package com.phistory.data.dao.sql;

import com.phistory.data.model.engine.Engine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import static com.phistory.data.dao.sql.SqlEngineRepository.ENGINE_REPOSITORY;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
@Repository(ENGINE_REPOSITORY)
public interface SqlEngineRepository extends CrudRepository<Engine, Long> {

    String ENGINE_REPOSITORY = "engineRepository";
}
