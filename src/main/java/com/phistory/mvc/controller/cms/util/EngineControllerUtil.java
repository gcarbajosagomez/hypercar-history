package com.phistory.mvc.controller.cms.util;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.command.EngineFormEditCommand;
import com.phistory.mvc.cms.form.creator.EngineFormCreator;
import com.tcp.data.dao.impl.EngineDao;
import com.tcp.data.model.engine.Engine;

/**
 * Set of utilities for the EngineController class
 * 
 * @author gonzalo
 *
 */
@Component 
public class EngineControllerUtil {
	
	@Inject
	private EngineDao engineDao;
	@Inject()
	private EngineFormCreator engineFormCreator;
	
	/**
	 * Handle the deletion of an engine.
	 * 
	 * @param command
	 * @throws Exception
	 */
	public void deleteEngine(EngineFormEditCommand command) throws Exception
    {
        if (command.getEngineForm() != null)
        {        	
            Engine engine = engineFormCreator.createEntityFromForm(command.getEngineForm());            
            engineDao.delete(engine);
        }
    }
}
