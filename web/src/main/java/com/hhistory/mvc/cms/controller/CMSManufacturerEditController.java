package com.hhistory.mvc.cms.controller;

import com.hhistory.data.model.Manufacturer;
import com.hhistory.mvc.cms.command.EditFormCommand;
import com.hhistory.mvc.cms.command.EntityManagementLoadCommand;
import com.hhistory.mvc.cms.command.ManufacturerEditFormCommand;
import com.hhistory.mvc.cms.controller.util.CMSManufacturerControllerUtil;
import com.hhistory.mvc.cms.dto.CrudOperationDTO;
import com.hhistory.mvc.cms.form.ManufacturerEditForm;
import com.hhistory.mvc.cms.form.factory.EntityFormFactory;
import com.hhistory.mvc.cms.form.factory.impl.ManufacturerFormFactory;
import com.hhistory.mvc.cms.service.EntityManagementService;
import com.hhistory.mvc.cms.service.crud.CrudService;
import com.hhistory.mvc.cms.springframework.view.filler.ManufacturerModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import java.util.Optional;

import static com.hhistory.mvc.cms.command.EntityManagementQueryType.REMOVE_MANUFACTURERS;
import static com.hhistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.hhistory.mvc.cms.controller.CMSBaseController.MANUFACTURERS_URL;
import static com.hhistory.mvc.cms.service.crud.impl.ManufacturerCrudService.MANUFACTURER_CRUD_SERVICE;
import static com.hhistory.mvc.controller.BaseControllerData.ID;
import static com.hhistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @author Gonzalo
 */
@Secured(USER_ROLE)
@Controller
@Slf4j
@RequestMapping(CMS_CONTEXT + MANUFACTURERS_URL + "/{" + ID + "}")
public class CMSManufacturerEditController extends CMSBaseController {

    private CMSManufacturerControllerUtil cmsManufacturerControllerUtil;
    private CrudService                   manufacturerCrudService;
    private EntityFormFactory             manufacturerFormFactory;
    private ManufacturerModelFiller       manufacturerModelFiller;
    private EntityManagementService       entityManagementService;

    @Inject
    public CMSManufacturerEditController(@Named(MANUFACTURER_CRUD_SERVICE) CrudService manufacturerCrudService,
                                         CMSManufacturerControllerUtil cmsManufacturerControllerUtil,
                                         ManufacturerFormFactory manufacturerFormFactory,
                                         ManufacturerModelFiller manufacturerModelFiller,
                                         EntityManagementService entityManagementService) {
        this.cmsManufacturerControllerUtil = cmsManufacturerControllerUtil;
        this.manufacturerCrudService = manufacturerCrudService;
        this.manufacturerFormFactory = manufacturerFormFactory;
        this.manufacturerModelFiller = manufacturerModelFiller;
        this.entityManagementService = entityManagementService;
    }

    @GetMapping(value = EDIT_URL)
    public ModelAndView handleEditManufacturer(Model model) {
        this.fillModel(model);
        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }

    @RequestMapping(value = EDIT_URL, method = {POST, PUT})
    @ResponseBody
    public ModelAndView handleEditManufacturer(Model model,
                                               @Valid @ModelAttribute(MANUFACTURER_EDIT_FORM_COMMAND) EditFormCommand command,
                                               BindingResult result) {

        CrudOperationDTO crudOperationDTO = this.manufacturerCrudService.saveOrEditEntity(command, result);
        model.addAttribute(CRUD_OPERATION_DTO, crudOperationDTO);
        Manufacturer manufacturer = (Manufacturer) crudOperationDTO.getEntity();

        Optional.ofNullable(manufacturer)
                .map(Manufacturer::getId)
                .ifPresent(manufacturerId -> {
                    try {
                        this.cmsManufacturerControllerUtil.reloadManufacturerDBEntities(manufacturerId);
                    } catch (Exception e) {
                        crudOperationDTO.addErrorMessage(e.toString());
                        log.error("There was an error while editing manufacturer {}",
                                  manufacturer.toString(),
                                  e.getMessage());
                    }
                });

        this.fillModel(model);
        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }

    @DeleteMapping(value = DELETE_URL)
    @ResponseBody
    public ModelAndView handleDeleteManufacturer(Model model,
                                                 @ModelAttribute(MANUFACTURER_EDIT_FORM_COMMAND) EditFormCommand command,
                                                 BindingResult bindingResult) {

        CrudOperationDTO crudOperationDTO = this.manufacturerCrudService.deleteEntity(command, bindingResult);
        model.addAttribute(CRUD_OPERATION_DTO, crudOperationDTO);
        Manufacturer manufacturer = (Manufacturer) crudOperationDTO.getEntity();

        Optional.ofNullable(manufacturer)
                .map(Manufacturer::getId)
                .ifPresent(manufacturerId -> {
                    try {
                        EntityManagementLoadCommand entityManagementLoadCommand = new EntityManagementLoadCommand();
                        entityManagementLoadCommand.setManufacturerId(manufacturerId);
                        entityManagementLoadCommand.setQueryType(REMOVE_MANUFACTURERS);
                        this.entityManagementService.reloadEntities(entityManagementLoadCommand);

                        model.addAttribute(MANUFACTURER_EDIT_FORM_COMMAND, new ManufacturerEditFormCommand());
                    } catch (Exception e) {
                        log.error("There was an error while deleting manufacturer {}",
                                  manufacturer.toString(),
                                  e);
                    }
                });

        this.fillModel(model);

        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }

    @ModelAttribute(MANUFACTURER_EDIT_FORM_COMMAND)
    public ManufacturerEditFormCommand createCarEditFormCommand(@PathVariable(ID) Long manufacturerId) {
        Manufacturer manufacturer = (Manufacturer) super.getSqlManufacturerRepository().findOne(manufacturerId);
        ManufacturerEditForm manufacturerEditForm =
                (ManufacturerEditForm) this.manufacturerFormFactory.buildFormFromEntity(manufacturer);
        ManufacturerEditFormCommand command = new ManufacturerEditFormCommand(manufacturerEditForm);

        return command;
    }

    @Override
    protected Model fillModel(Model model) {
        this.manufacturerModelFiller.fillModel(model);
        return model;
    }
}
