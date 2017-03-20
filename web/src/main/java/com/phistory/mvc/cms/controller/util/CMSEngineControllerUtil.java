package com.phistory.mvc.cms.controller.util;

import com.phistory.data.dao.sql.SqlEngineRepository;
import com.phistory.data.model.engine.Engine;
import com.phistory.mvc.cms.command.EngineFormEditCommand;
import com.phistory.mvc.cms.form.creator.EngineFormCreator;
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
    private EngineFormCreator   engineFormCreator;

    @Inject
    public CMSEngineControllerUtil(SqlEngineRepository sqlEngineRepository,
                                   EngineFormCreator engineFormCreator) {
        this.sqlEngineRepository = sqlEngineRepository;
        this.engineFormCreator = engineFormCreator;
    }

    /**
     * Handle the deletion of an engine.
     *
     * @param command
     * @throws Exception
     */
    public void deleteEngine(EngineFormEditCommand command) throws Exception {
        if (command.getEngineForm() != null) {
            Engine engine = this.engineFormCreator.createEntityFromForm(command.getEngineForm());
            this.sqlEngineRepository.delete(engine);
        }
    }
}
