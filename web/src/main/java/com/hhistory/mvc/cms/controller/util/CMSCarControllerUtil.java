package com.hhistory.mvc.cms.controller.util;

import com.hhistory.mvc.cms.command.EntityManagementLoadCommand;
import com.hhistory.mvc.cms.controller.CMSCarController;
import com.hhistory.mvc.cms.controller.CMSCarEditController;
import com.hhistory.mvc.cms.service.EntityManagementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static com.hhistory.mvc.cms.command.EntityManagementQueryType.*;

/**
 * Set of utilities for {@link CMSCarController} and {@link CMSCarEditController}
 *
 * @author gonzalo
 */
@AllArgsConstructor(onConstructor = @__(@Inject))
@Component
@Slf4j
public class CMSCarControllerUtil {

    private EntityManagementService entityManagementService;

    public void reloadCarAndPictureDBEntities(Long carId) {
        EntityManagementLoadCommand entityManagementLoadCommand = new EntityManagementLoadCommand();
        entityManagementLoadCommand.setCarId(carId);

        entityManagementLoadCommand.setQueryType(RELOAD_CARS);
        this.entityManagementService.reloadEntities(entityManagementLoadCommand);

        //entityManagementLoadCommand.setQueryType(RELOAD_PICTURES);
        //this.entityManagementService.reloadEntities(entityManagementLoadCommand);

        entityManagementLoadCommand.setQueryType(RELOAD_CAR_INTERNET_CONTENTS);
        this.entityManagementService.reloadEntities(entityManagementLoadCommand);
    }
}
