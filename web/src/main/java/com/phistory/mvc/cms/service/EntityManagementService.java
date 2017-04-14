package com.phistory.mvc.cms.service;

import com.phistory.mvc.cms.command.EntityManagementLoadCommand;

/**
 * Created by Gonzalo Carbajosa on 14/04/17.
 */
public interface EntityManagementService {

    void reloadEntities(EntityManagementLoadCommand entityManagementLoadCommand);
}
