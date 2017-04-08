package com.phistory.mvc.cms.controller;

import com.phistory.data.model.Manufacturer;
import com.phistory.mvc.cms.command.ManufacturerFormEditCommand;
import com.phistory.mvc.cms.controller.util.CMSManufacturerControllerUtil;
import com.phistory.mvc.model.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import com.phistory.mvc.springframework.view.filler.sql.ManufacturerModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Map;

import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.controller.CMSBaseController.MANUFACTURERS_URL;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Secured(USER_ROLE)
@Slf4j
@Controller
@RequestMapping(value = CMS_CONTEXT + MANUFACTURERS_URL)
public class CMSManufacturerController extends CMSBaseController {

    private ManufacturerModelFiller       manufacturerModelFiller;
    private ModelFiller                   pictureModelFiller;
    private CMSManufacturerControllerUtil cmsManufacturerControllerUtil;

    @Inject
    public CMSManufacturerController(ManufacturerModelFiller manufacturerModelFiller,
                                     ModelFiller pictureModelFiller,
                                     CMSManufacturerControllerUtil cmsManufacturerControllerUtil) {
        this.manufacturerModelFiller = manufacturerModelFiller;
        this.pictureModelFiller = pictureModelFiller;
        this.cmsManufacturerControllerUtil = cmsManufacturerControllerUtil;
    }

    @RequestMapping(method = GET)
    public ModelAndView handleListManufacturers(Model model,
                                                PaginationDTO manufacturersPaginationDTO) {
        try {
            this.manufacturerModelFiller.fillPaginatedModel(model, manufacturersPaginationDTO);
            this.pictureModelFiller.fillModel(model);

            return new ModelAndView();
        } catch (Exception e) {
            log.error(e.toString(), e);

            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }

    @RequestMapping(method = POST,
            consumes = APPLICATION_JSON,
            produces = APPLICATION_JSON)
    @ResponseBody
    public Map<String, Object> handlePagination(@RequestBody PaginationDTO paginationDTO) {
        return this.cmsManufacturerControllerUtil.createPaginationData(paginationDTO);
    }

    @RequestMapping(value = EDIT_URL,
            method = GET)
    public ModelAndView handleNewManufacturer(Model model) {
        try {
            ManufacturerFormEditCommand manufacturerFormEditCommand = new ManufacturerFormEditCommand();
            model.addAttribute(MANUFACTURER_EDIT_FORM_COMMAND, manufacturerFormEditCommand);

            this.manufacturerModelFiller.fillModel(model);
            this.pictureModelFiller.fillModel(model);
        } catch (Exception e) {
            log.error(e.toString(), e);
            model.addAttribute(EXCEPTION_MESSAGE, e.toString());

            return new ModelAndView(ERROR_VIEW_NAME);
        }

        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }

    @RequestMapping(value = SAVE_URL,
            method = POST)
    @ResponseBody
    public ModelAndView handleSaveNewManufacturer(Model model,
                                                  @Valid @ModelAttribute(MANUFACTURER_EDIT_FORM_COMMAND) ManufacturerFormEditCommand command,
                                                  BindingResult result) {
        if (!result.hasErrors()) {
            try {
                Manufacturer manufacturer = this.cmsManufacturerControllerUtil.saveOrEditManufacturer(command);

                this.cmsManufacturerControllerUtil.reloadManufacturerDBEntities(manufacturer.getId());
                String successMessage = super.getMessageSource()
                                             .getMessage(ENTITY_SAVED_SUCCESSFULLY_RESULT_MESSAGE,
                                                         new Object[] {manufacturer.toString()},
                                                         LocaleContextHolder.getLocale());
                model.addAttribute(SUCCESS_MESSAGE, successMessage);
            } catch (Exception e) {
                model.addAttribute(EXCEPTION_MESSAGE, e.toString());
            } finally {
                manufacturerModelFiller.fillModel(model);
                pictureModelFiller.fillModel(model);
            }
        }

        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }
}
