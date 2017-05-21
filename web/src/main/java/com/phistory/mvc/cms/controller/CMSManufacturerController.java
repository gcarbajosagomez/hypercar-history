package com.phistory.mvc.cms.controller;

import com.phistory.data.model.Manufacturer;
import com.phistory.mvc.cms.command.ManufacturerEditFormCommand;
import com.phistory.mvc.cms.controller.util.CMSManufacturerControllerUtil;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import com.phistory.mvc.cms.service.crud.CrudService;
import com.phistory.mvc.dto.PaginationDTO;
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

import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.controller.CMSBaseController.MANUFACTURERS_URL;
import static com.phistory.mvc.cms.service.crud.impl.ManufacturerCrudService.MANUFACTURER_CRUD_SERVICE;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Secured(USER_ROLE)
@Slf4j
@Controller
@RequestMapping(CMS_CONTEXT + MANUFACTURERS_URL)
public class CMSManufacturerController extends CMSBaseController {

    private ManufacturerModelFiller       manufacturerModelFiller;
    private ModelFiller                   pictureModelFiller;
    private CMSManufacturerControllerUtil cmsManufacturerControllerUtil;
    private CrudService                   manufacturerCrudService;

    @Inject
    public CMSManufacturerController(@Named(MANUFACTURER_CRUD_SERVICE) CrudService manufacturerCrudService,
                                     ManufacturerModelFiller manufacturerModelFiller,
                                     ModelFiller pictureModelFiller,
                                     CMSManufacturerControllerUtil cmsManufacturerControllerUtil) {
        this.manufacturerModelFiller = manufacturerModelFiller;
        this.pictureModelFiller = pictureModelFiller;
        this.cmsManufacturerControllerUtil = cmsManufacturerControllerUtil;
        this.manufacturerCrudService = manufacturerCrudService;
    }

    @GetMapping
    public ModelAndView handleListManufacturers(Model model,
                                                PaginationDTO manufacturersPaginationDTO) {
        try {
            model = this.manufacturerModelFiller.fillPaginatedModel(model, manufacturersPaginationDTO);
            this.pictureModelFiller.fillModel(model);

            return new ModelAndView();
        } catch (Exception e) {
            log.error(e.toString(), e);

            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }

    @PostMapping(consumes = APPLICATION_JSON,
            produces = APPLICATION_JSON)
    @ResponseBody
    public PaginationDTO handlePagination(@RequestBody PaginationDTO paginationDTO) {
        return this.cmsManufacturerControllerUtil.createPaginationData(paginationDTO);
    }

    @GetMapping(EDIT_URL)
    public ModelAndView handleNewManufacturer(Model model) {
        try {
            ManufacturerEditFormCommand manufacturerEditFormCommand = new ManufacturerEditFormCommand();
            model.addAttribute(MANUFACTURER_EDIT_FORM_COMMAND, manufacturerEditFormCommand);

            model = this.manufacturerModelFiller.fillModel(model);
            this.pictureModelFiller.fillModel(model);
        } catch (Exception e) {
            log.error(e.toString(), e);

            return new ModelAndView(ERROR_VIEW_NAME);
        }

        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }

    @PostMapping(SAVE_URL)
    @ResponseBody
    public ModelAndView handleSaveNewManufacturer(Model model,
                                                  @Valid @ModelAttribute(MANUFACTURER_EDIT_FORM_COMMAND) ManufacturerEditFormCommand command,
                                                  BindingResult result) {

        CrudOperationDTO crudOperationDTO = this.manufacturerCrudService.saveOrEditEntity(command, result);
        Manufacturer manufacturer = (Manufacturer) crudOperationDTO.getEntity();
        model.addAttribute(CRUD_OPERATION_DTO, crudOperationDTO);

        Optional.ofNullable(manufacturer)
                .map(Manufacturer::getId)
                .ifPresent(manufacturerId -> {
                    try {
                        this.cmsManufacturerControllerUtil.reloadManufacturerDBEntities(manufacturerId);
                    } catch (Exception e) {
                        crudOperationDTO.addErrorMessage(e.toString());
                        log.error("There was an error while saving new manufacturer {}",
                                  manufacturer.toString(),
                                  e.getMessage());
                    }
                });

        model = this.manufacturerModelFiller.fillModel(model);
        this.pictureModelFiller.fillModel(model);
        return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
    }
}
