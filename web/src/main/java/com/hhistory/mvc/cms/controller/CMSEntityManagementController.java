package com.hhistory.mvc.cms.controller;

import com.hhistory.mvc.cms.command.EntityManagementLoadCommand;
import com.hhistory.mvc.cms.command.EntityManagementQueryType;
import com.hhistory.mvc.propertyEditor.EntityManagementQueryTypePropertyEditor;
import com.hhistory.mvc.cms.service.EntityManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static com.hhistory.mvc.cms.controller.CMSBaseController.*;
import static com.hhistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller to handle
 * <p>
 * Created by Gonzalo Carbajosa on 3/12/16.
 */
@Secured(USER_ROLE)
@Slf4j
@RestController
@RequestMapping(value = CMS_CONTEXT + ENTITY_MANAGEMENT_URL + "/{" + ENTITY_MANAGEMENT_QUERY_ACTION + "}",
        method = GET)
public class CMSEntityManagementController extends CMSBaseController {
    private EntityManagementService entityManagementService;

    @Inject
    public CMSEntityManagementController(EntityManagementService entityManagementService) {
        this.entityManagementService = entityManagementService;
    }

    @RequestMapping
    public String handleReloadCars(
            @ModelAttribute(ENTITY_MANAGEMENT_LOAD_COMMAND) EntityManagementLoadCommand entityManagementLoadCommand) {
        this.entityManagementService.reloadEntities(entityManagementLoadCommand);
        return "Database entities successfully loaded in memory";
    }

    @ModelAttribute(ENTITY_MANAGEMENT_LOAD_COMMAND)
    public EntityManagementLoadCommand createQueryCommand(
            @PathVariable(ENTITY_MANAGEMENT_QUERY_ACTION) EntityManagementQueryType queryType,
            @RequestParam(value = CAR_ID, required = false) Long carId,
            @RequestParam(value = PICTURE_ID, required = false) Long pictureId,
            @RequestParam(value = CAR_INTERNET_CONTENT_ID, required = false) Long carInternetContentId,
            @RequestParam(value = MANUFACTURER_ID, required = false) Long manufacturerId) {

        return new EntityManagementLoadCommand(queryType,
                                               carId,
                                               pictureId,
                                               carInternetContentId,
                                               manufacturerId);
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(EntityManagementQueryType.class, new EntityManagementQueryTypePropertyEditor());
    }
}
