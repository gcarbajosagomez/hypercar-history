package com.phistory.mvc.cms.controller;

import com.phistory.data.model.car.Car;
import com.phistory.mvc.cms.command.CarEditFormCommand;
import com.phistory.mvc.cms.command.CarInternetContentEditFormCommand;
import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.controller.util.CMSCarControllerUtil;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.service.crud.CrudService;
import com.phistory.mvc.cms.springframework.view.filler.CarEditModelFiller;
import com.phistory.mvc.controller.CarListController;
import com.phistory.mvc.controller.util.CarControllerUtil;
import com.phistory.mvc.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.phistory.mvc.cms.controller.CMSBaseController.CARS_URL;
import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.service.crud.impl.CarCrudService.CAR_CRUD_SERVICE;
import static com.phistory.mvc.cms.service.crud.impl.CarInternetContentCrudService.CAR_INTERNET_CONTENT_CRUD_SERVICE;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;

@Secured(USER_ROLE)
@Controller
@RequestMapping(CMS_CONTEXT + CARS_URL)
@Slf4j
public class CMSCarController extends CMSBaseController {

    private CrudService                carCrudService;
    private CrudService                carInternetContentCrudService;
    private CarListController          carListController;
    private ModelFiller                carModelFiller;
    private ModelFiller                pictureModelFiller;
    private CarEditModelFiller         carEditModelFiller;
    private AbstractCarListModelFiller sqlCarListModelFiller;
    private CMSCarControllerUtil       cmsCarControllerUtil;
    private CarControllerUtil          carControllerUtil;

    @Inject
    public CMSCarController(@Named(CAR_CRUD_SERVICE) CrudService carCrudService,
                            @Named(CAR_INTERNET_CONTENT_CRUD_SERVICE) CrudService carInternetContentCrudService,
                            CarListController carListController,
                            ModelFiller carModelFiller,
                            ModelFiller pictureModelFiller,
                            CarEditModelFiller carEditModelFiller,
                            AbstractCarListModelFiller sqlCarListModelFiller,
                            CMSCarControllerUtil cmsCarControllerUtil,
                            CarControllerUtil carControllerUtil) {
        this.carCrudService = carCrudService;
        this.carInternetContentCrudService = carInternetContentCrudService;
        this.carListController = carListController;
        this.carModelFiller = carModelFiller;
        this.pictureModelFiller = pictureModelFiller;
        this.carEditModelFiller = carEditModelFiller;
        this.sqlCarListModelFiller = sqlCarListModelFiller;
        this.cmsCarControllerUtil = cmsCarControllerUtil;
        this.carControllerUtil = carControllerUtil;
    }

    @GetMapping
    public ModelAndView handleCarsList(Model model,
                                       PaginationDTO paginationDTO) {
        try {
            this.carControllerUtil.fillCarListModel(this.sqlCarListModelFiller, model, paginationDTO);
            return new ModelAndView();
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }

    @GetMapping(value = {"/" + PAGINATION_URL})
    @ResponseBody
    public PaginationDTO handlePagination(Model model, PaginationDTO paginationDTO) {
        return this.carListController.handlePagination(model, paginationDTO);
    }

    @GetMapping(value = EDIT_URL)
    public ModelAndView handleNewCar(Model model,
                                     @ModelAttribute(CAR_EDIT_FORM_COMMAND) EditFormCommand carFormEditCommand) {
        try {
            this.fillModel(model, carFormEditCommand);
            return new ModelAndView(CAR_EDIT_VIEW_NAME);
        } catch (Exception e) {
            log.error(e.toString(), e);
            CrudOperationDTO crudOperationDTO = new CrudOperationDTO();
            crudOperationDTO.addErrorMessage(e.toString());

            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }

    @PostMapping(value = SAVE_URL)
    public ModelAndView handleSaveNewCar(Model model,
                                         @Valid @ModelAttribute(CAR_EDIT_FORM_COMMAND) EditFormCommand carFormEditCommand,
                                         BindingResult carFormEditCommandResult,
                                         @Valid @ModelAttribute(CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND) CarInternetContentEditFormCommand
                                                 carInternetContentEditFormCommand,
                                         BindingResult carInternetContentEditFormCommandResult) {

        CrudOperationDTO crudOperationDTO = this.carCrudService.saveOrEditEntity(carFormEditCommand,
                                                                                 carFormEditCommandResult);
        model.addAttribute(CRUD_OPERATION_DTO, crudOperationDTO);

        Car car = (Car) crudOperationDTO.getEntity();
        List<CarInternetContentForm> enrichedCarInternetContentForms =
                this.enrichCarInternetContentForms(carInternetContentEditFormCommand.getEditForms(),
                                                   car);

        carInternetContentEditFormCommand.setEditForms(enrichedCarInternetContentForms);

        CrudOperationDTO carInternetContentCrudOperationDTO =
                this.carInternetContentCrudService.saveOrEditEntity(carInternetContentEditFormCommand.adapt(),
                                                                    carInternetContentEditFormCommandResult);
        model.addAttribute(CAR_INTERNET_CONTENTS_CRUD_OPERATION_DTO, carInternetContentCrudOperationDTO);

        Optional.ofNullable(car)
                .map(Car::getId)
                .ifPresent(carId -> {
                    try {
                        this.cmsCarControllerUtil.reloadCarAndPictureDBEntities(carId);
                    } catch (Exception e) {
                        log.error("There was an error while saving new car: {}",
                                  car.toString(),
                                  e);
                    }
                });

        this.fillModel(model, carFormEditCommand);
        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    private List<CarInternetContentForm> enrichCarInternetContentForms(List<CarInternetContentForm> carInternetContentForms,
                                                                       Car car) {
        return carInternetContentForms.stream()
                                      .map(carInternetContentForm -> {
                                          carInternetContentForm.setCar(car);
                                          return carInternetContentForm;
                                      })
                                      .collect(Collectors.toList());
    }

    @ModelAttribute(CAR_EDIT_FORM_COMMAND)
    public EditFormCommand createCarEditFormCommand() {
        return new CarEditFormCommand();
    }

    @ModelAttribute(CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND)
    public CarInternetContentEditFormCommand createCarInternetContentEditFormCommand() {
        return new CarInternetContentEditFormCommand();
    }

    @Override
    protected Model fillModel(Model model, EditFormCommand carFormEditCommand) {
        model = this.carModelFiller.fillModel(model);
        model = this.carEditModelFiller.fillCarEditModel(model, carFormEditCommand);
        model = this.pictureModelFiller.fillModel(model);
        return model;
    }
}
