package com.phistory.mvc.cms.controller;

import static com.phistory.mvc.cms.command.EntityManagementQueryType.REMOVE_MANUFACTURERS;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.controller.CMSBaseController.MANUFACTURERS_URL;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.inject.Inject;
import javax.validation.Valid;

import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import com.phistory.mvc.cms.controller.util.CMSManufacturerControllerUtil;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import com.phistory.mvc.cms.service.EntityManagementService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.cms.command.ManufacturerFormEditCommand;
import com.phistory.mvc.cms.form.ManufacturerForm;
import com.phistory.mvc.cms.form.factory.ManufacturerFormFactory;
import com.phistory.mvc.springframework.view.filler.sql.ManufacturerModelFiller;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import com.phistory.data.model.Manufacturer;

/**
 * @author Gonzalo
 */
@Secured(USER_ROLE)
@Controller
@Slf4j
@RequestMapping(value = CMS_CONTEXT + MANUFACTURERS_URL + "/{" + ID + "}")
public class CMSManufacturerEditController extends CMSBaseController {

    private CMSManufacturerControllerUtil cmsManufacturerControllerUtil;
    private EntityFormFactory             manufacturerFormFactory;
    private ManufacturerModelFiller       manufacturerModelFiller;
    private ModelFiller                   pictureModelFiller;
    private EntityManagementService       entityManagementService;

    @Inject
    public CMSManufacturerEditController(CMSManufacturerControllerUtil cmsManufacturerControllerUtil,
                                         ManufacturerFormFactory manufacturerFormFactory,
                                         ManufacturerModelFiller manufacturerModelFiller,
                                         ModelFiller pictureModelFiller,
                                         EntityManagementService entityManagementService) {
        this.cmsManufacturerControllerUtil = cmsManufacturerControllerUtil;
        this.manufacturerFormFactory = manufacturerFormFactory;
        this.manufacturerModelFiller = manufacturerModelFiller;
        this.pictureModelFiller = pictureModelFiller;
        this.entityManagementService = entityManagementService;
    }

    @RequestMapping(value = EDIT_URL,
            method = GET)
    public ModelAndView handleEditManufacturer(Model model) {
        this.fillModel(model);
        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }

    @RequestMapping(value = EDIT_URL,
            method = {POST, PUT})
    @ResponseBody
    public ModelAndView handleEditManufacturer(Model model,
                                               @Valid @ModelAttribute(MANUFACTURER_EDIT_FORM_COMMAND) ManufacturerFormEditCommand command,
                                               BindingResult result) {
        if (!result.hasErrors()) {
            try {
                Manufacturer manufacturer = this.cmsManufacturerControllerUtil.saveOrEditManufacturer(command);
                this.cmsManufacturerControllerUtil.reloadManufacturerDBEntities(manufacturer.getId());

                String successMessage = super.getMessageSource()
                                             .getMessage(ENTITY_EDITED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                                         new Object[] {manufacturer.toString()},
                                                         LocaleContextHolder.getLocale());
                model.addAttribute(SUCCESS_MESSAGE, successMessage);
            } catch (Exception e) {
                model.addAttribute(EXCEPTION_MESSAGE, e.toString());
                log.error("There was an error while editing manufacturer {}",
                          command.getManufacturerForm().getName(),
                          e.getMessage());
            } finally {
                this.fillModel(model);
            }
        } else {
            String errorMessage = super.getMessageSource()
                                       .getMessage(ENTITY_CONTAINED_ERRORS_TEXT_SOURCE_KEY,
                                                   null,
                                                   LocaleContextHolder.getLocale());

            model.addAttribute(EXCEPTION_MESSAGE, errorMessage);
        }

        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }

    @RequestMapping(value = DELETE_URL,
            method = DELETE)
    @ResponseBody
    public ModelAndView handleDeleteManufacturer(Model model,
                                                 @ModelAttribute(MANUFACTURER_EDIT_FORM_COMMAND) ManufacturerFormEditCommand command) {

        ManufacturerForm manufacturerForm = command.getManufacturerForm();

        if (manufacturerForm != null && manufacturerForm.getId() != null) {
            try {
                cmsManufacturerControllerUtil.deleteManufacturer(command);

                EntityManagementLoadCommand entityManagementLoadCommand = new EntityManagementLoadCommand();
                entityManagementLoadCommand.setManufacturerId(manufacturerForm.getId());
                entityManagementLoadCommand.setQueryType(REMOVE_MANUFACTURERS);
                this.entityManagementService.reloadEntities(entityManagementLoadCommand);

                String successMessage = super.getMessageSource()
                                             .getMessage(ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                                         new Object[] {manufacturerForm.getName()},
                                                         LocaleContextHolder.getLocale());

                model.addAttribute(SUCCESS_MESSAGE, successMessage);
                model.addAttribute(MANUFACTURER_EDIT_FORM_COMMAND, new ManufacturerFormEditCommand());
            } catch (Exception e) {
                log.error("There was an error while deleting manufacturer {}",
                          manufacturerForm.getName(),
                          e.getMessage());
                model.addAttribute(EXCEPTION_MESSAGE, e.toString());
            } finally {
                this.fillModel(model);
            }
        }

        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }

    @ModelAttribute(value = MANUFACTURER_EDIT_FORM_COMMAND)
    public ManufacturerFormEditCommand createCarEditFormCommand(@PathVariable(ID) Long manufacturerId) {
        Manufacturer manufacturer = (Manufacturer) super.getSqlManufacturerRepository().findOne(manufacturerId);
        ManufacturerForm manufacturerForm = (ManufacturerForm) this.manufacturerFormFactory.createFormFromEntity(manufacturer);
        ManufacturerFormEditCommand command = new ManufacturerFormEditCommand(manufacturerForm);

        return command;
    }

    private void fillModel(Model model) {
        this.manufacturerModelFiller.fillModel(model);
        this.pictureModelFiller.fillModel(model);
    }
}
