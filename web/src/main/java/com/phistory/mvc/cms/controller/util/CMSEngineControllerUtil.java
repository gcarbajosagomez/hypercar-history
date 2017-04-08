package com.phistory.mvc.cms.controller.util;

import com.phistory.data.dao.sql.SqlEngineRepository;
import com.phistory.data.model.engine.Engine;
import com.phistory.mvc.cms.command.EngineFormEditCommand;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Set of utilities for the EngineController class
 *
 * @author gonzalo
 */
@Component
public class CMSEngineControllerUtil {

    private SqlEngineRepository sqlEngineRepository;
    private EntityFormFactory   engineFormFactory;

    @Inject
    public CMSEngineControllerUtil(SqlEngineRepository sqlEngineRepository,
                                   EntityFormFactory engineFormFactory) {
        this.sqlEngineRepository = sqlEngineRepository;
        this.engineFormFactory = engineFormFactory;
    }

    /**
     * Handle the deletion of an engine.
     *
     * @param command
     * @throws Exception
     */
    public void deleteEngine(EngineFormEditCommand command) throws Exception {
        if (command.getEngineForm() != null) {
            Engine engine = (Engine) this.engineFormFactory.createEntityFromForm(command.getEngineForm());
            this.sqlEngineRepository.delete(engine);
        }
    }
}
