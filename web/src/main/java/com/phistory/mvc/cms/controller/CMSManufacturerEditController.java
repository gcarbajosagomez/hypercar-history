package com.phistory.mvc.cms.controller;

import com.phistory.data.model.Manufacturer;
import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import com.phistory.mvc.cms.command.ManufacturerEditFormCommand;
import com.phistory.mvc.cms.controller.util.CMSManufacturerControllerUtil;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import com.phistory.mvc.cms.form.ManufacturerEditForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import com.phistory.mvc.cms.form.factory.impl.ManufacturerFormFactory;
import com.phistory.mvc.cms.service.EntityManagementService;
import com.phistory.mvc.cms.service.crud.CrudService;
import com.phistory.mvc.cms.service.crud.impl.ManufacturerCrudService;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import com.phistory.mvc.springframework.view.filler.sql.ManufacturerModelFiller;
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

import static com.phistory.mvc.cms.command.EntityManagementQueryType.REMOVE_MANUFACTURERS;
import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.controller.CMSBaseController.MANUFACTURERS_URL;
import static com.phistory.mvc.cms.service.crud.impl.ManufacturerCrudService.*;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
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
    private ModelFiller                   pictureModelFiller;
    private EntityManagementService       entityManagementService;

    @Inject
    public CMSManufacturerEditController(@Named(MANUFACTURER_CRUD_SERVICE) CrudService manufacturerCrudService,
                                         CMSManufacturerControllerUtil cmsManufacturerControllerUtil,
                                         ManufacturerFormFactory manufacturerFormFactory,
                                         ManufacturerModelFiller manufacturerModelFiller,
                                         ModelFiller pictureModelFiller,
                                         EntityManagementService entityManagementService) {
        this.cmsManufacturerControllerUtil = cmsManufacturerControllerUtil;
        this.manufacturerCrudService = manufacturerCrudService;
        this.manufacturerFormFactory = manufacturerFormFactory;
        this.manufacturerModelFiller = manufacturerModelFiller;
        this.pictureModelFiller = pictureModelFiller;
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
        this.pictureModelFiller.fillModel(model);
        return model;
    }
}
