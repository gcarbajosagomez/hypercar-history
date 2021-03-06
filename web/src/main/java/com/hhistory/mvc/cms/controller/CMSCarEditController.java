package com.hhistory.mvc.cms.controller;

import com.hhistory.data.model.car.Car;
import com.hhistory.data.model.car.CarInternetContent;
import com.hhistory.mvc.cms.command.CarEditFormCommand;
import com.hhistory.mvc.cms.command.CarInternetContentEditFormCommand;
import com.hhistory.mvc.cms.command.EditFormCommand;
import com.hhistory.mvc.cms.command.EntityManagementLoadCommand;
import com.hhistory.mvc.cms.controller.util.CMSCarControllerUtil;
import com.hhistory.mvc.cms.dto.CrudOperationDTO;
import com.hhistory.mvc.cms.form.CarEditForm;
import com.hhistory.mvc.cms.form.CarInternetContentForm;
import com.hhistory.mvc.cms.form.CarInternetContentFormAdapter;
import com.hhistory.mvc.cms.form.EditForm;
import com.hhistory.mvc.cms.form.factory.EntityFormFactory;
import com.hhistory.mvc.cms.form.factory.impl.CarInternetContentFormFactory;
import com.hhistory.mvc.cms.service.EntityManagementService;
import com.hhistory.mvc.cms.service.crud.CrudService;
import com.hhistory.mvc.cms.springframework.view.filler.CarEditModelFiller;
import com.hhistory.mvc.springframework.view.filler.ModelFiller;
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

import static com.hhistory.mvc.cms.command.EntityManagementQueryType.REMOVE_CAR;
import static com.hhistory.mvc.cms.controller.CMSBaseController.CARS_URL;
import static com.hhistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.hhistory.mvc.cms.service.crud.impl.CarCrudService.CAR_CRUD_SERVICE;
import static com.hhistory.mvc.cms.service.crud.impl.CarInternetContentCrudService.CAR_INTERNET_CONTENT_CRUD_SERVICE;
import static com.hhistory.mvc.controller.BaseControllerData.ID;
import static com.hhistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @author Gonzalo
 */
@Secured(USER_ROLE)
@Controller
@RequestMapping(CMS_CONTEXT + CARS_URL + "/{" + ID + "}")
@Slf4j
public class CMSCarEditController extends CMSBaseController {

    private CrudService                         carCrudService;
    private CrudService                         carInternetContentCrudService;
    private CMSCarControllerUtil                cmsCarControllerUtil;
    private EntityFormFactory<Car, CarEditForm> carFormFactory;
    private CarInternetContentFormFactory       carInternetContentFormFactory;
    private ModelFiller                         carModelFiller;
    private CarEditModelFiller                  carEditModelFiller;
    private EntityManagementService             entityManagementService;

    @Inject
    public CMSCarEditController(@Named(CAR_CRUD_SERVICE) CrudService carCrudService,
                                @Named(CAR_INTERNET_CONTENT_CRUD_SERVICE) CrudService carInternetContentCrudService,
                                CMSCarControllerUtil cmsCarControllerUtil,
                                EntityFormFactory carFormFactory,
                                CarInternetContentFormFactory carInternetContentFormFactory,
                                ModelFiller carModelFiller,
                                CarEditModelFiller carEditModelFiller,
                                EntityManagementService entityManagementService) {
        this.carCrudService = carCrudService;
        this.carInternetContentCrudService = carInternetContentCrudService;
        this.cmsCarControllerUtil = cmsCarControllerUtil;
        this.carFormFactory = carFormFactory;
        this.carInternetContentFormFactory = carInternetContentFormFactory;
        this.carModelFiller = carModelFiller;
        this.carEditModelFiller = carEditModelFiller;
        this.entityManagementService = entityManagementService;
    }

    @GetMapping(EDIT_URL)
    public ModelAndView handleEditCarDefault(Model model,
                                             @ModelAttribute(CAR_EDIT_FORM_COMMAND) EditFormCommand carFormEditCommand) {
        this.fillModel(model, carFormEditCommand);
        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    /**
     * Fill the supplied {@link Model}
     *
     * @param model
     * @param carFormEditCommand
     */
    @Override
    protected Model fillModel(Model model, EditFormCommand carFormEditCommand) {
        model = this.carModelFiller.fillModel(model);
        model = this.carEditModelFiller.fillCarEditModel(model, carFormEditCommand);
        return model;
    }

    @RequestMapping(value = EDIT_URL,
            method = {POST, PUT})
    @ResponseBody
    public ModelAndView handleEditCar(Model model,
                                      @Valid @ModelAttribute(CAR_EDIT_FORM_COMMAND) EditFormCommand carFormEditCommand,
                                      BindingResult carFormEditCommandResult,
                                      @Valid @ModelAttribute(CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND) CarInternetContentEditFormCommand carInternetContentEditFormCommand,
                                      BindingResult carInternetContentEditFormCommandResult) {

        CrudOperationDTO carCrudOperationDTO = this.carCrudService.saveOrEditEntity(carFormEditCommand,
                                                                                    carFormEditCommandResult);
        model.addAttribute(CRUD_OPERATION_DTO, carCrudOperationDTO);

        Car car = (Car) carCrudOperationDTO.getEntity();
        CrudOperationDTO carInternetContentCrudOperationDTO = new CrudOperationDTO();
        if (!carInternetContentEditFormCommandResult.hasErrors()) {
            carInternetContentCrudOperationDTO = this.saveOrEditCarInternetContentForms(carInternetContentEditFormCommand,
                                                                                        carInternetContentEditFormCommandResult,
                                                                                        car);
        } else {
            carInternetContentCrudOperationDTO =
                    this.carInternetContentCrudService.addBindingResultErrors(carInternetContentEditFormCommandResult,
                                                                              carInternetContentCrudOperationDTO);
        }

        model.addAttribute(CAR_INTERNET_CONTENTS_CRUD_OPERATION_DTO, carInternetContentCrudOperationDTO);

        Optional.ofNullable(car)
                .map(Car::getId)
                .ifPresent(carId -> {
                    try {
                        this.cmsCarControllerUtil.reloadCarAndPictureDBEntities(car.getId());
                    } catch (Exception e) {
                        log.error("There was an error while editing car: {}",
                                  car.toString(),
                                  e);
                    }
                });

        this.fillModel(model, carFormEditCommand);
        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    private CrudOperationDTO saveOrEditCarInternetContentForms(
            CarInternetContentEditFormCommand carInternetContentEditFormCommand,
            BindingResult carInternetContentEditFormCommandResult,
            Car car) {
        List<CarInternetContentForm> enrichedCarInternetContentForms =
                this.enrichCarInternetContentForms(carInternetContentEditFormCommand.getEditForms(),
                                                   car);
        carInternetContentEditFormCommand.setEditForms(enrichedCarInternetContentForms);

        return this.carInternetContentCrudService.saveOrEditEntity(carInternetContentEditFormCommand.adapt(),
                                                                   carInternetContentEditFormCommandResult);
    }

    private List<CarInternetContentForm> enrichCarInternetContentForms(List<CarInternetContentForm> carInternetContentForms,
                                                                       Car car) {
        return carInternetContentForms.stream()
                                      .map(carInternetContentForm -> {
                                          carInternetContentForm.setCar(car);
                                          return carInternetContentForm;
                                      })
                                      .toList();
    }

    @DeleteMapping(DELETE_URL)
    @ResponseBody
    public ModelAndView handleDeleteCar(Model model,
                                        @ModelAttribute(CAR_EDIT_FORM_COMMAND) EditFormCommand carFormEditCommand,
                                        BindingResult result) {

        CrudOperationDTO crudOperationDTO = this.carCrudService.deleteEntity(carFormEditCommand, result);
        model.addAttribute(CRUD_OPERATION_DTO, crudOperationDTO);
        Car car = (Car) crudOperationDTO.getEntity();

        Optional.ofNullable(car)
                .map(Car::getId)
                .ifPresent(carId -> {
                    try {
                        EntityManagementLoadCommand entityManagementLoadCommand =
                                EntityManagementLoadCommand.builder()
                                                           .queryType(REMOVE_CAR)
                                                           .carId(carId)
                                                           .build();
                        this.entityManagementService.reloadEntities(entityManagementLoadCommand);

                        model.addAttribute(CAR_EDIT_FORM_COMMAND, new CarEditFormCommand());
                    } catch (Exception e) {
                        log.error("There was an error while deleting car: {}",
                                  car.toString(),
                                  e);
                    }
                });

        this.fillModel(model, carFormEditCommand);
        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    @ModelAttribute(CAR_EDIT_FORM_COMMAND)
    public EditFormCommand createCarEditFormCommand(@PathVariable(ID) Long carId) {
        return super.getSqlCarRepository()
                    .findById(carId)
                    .map(car -> {
                        EditForm carEditForm = this.carFormFactory.buildFormFromEntity(car);
                        return new CarEditFormCommand(carEditForm);
                    })
                    .orElse(new CarEditFormCommand());
    }

    @ModelAttribute(CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND)
    public CarInternetContentEditFormCommand createCarInternetContentEditFormCommand(@PathVariable(ID) Long carId) {
        List<CarInternetContent> carInternetContents = super.getSqlCarInternetContentRepository().getByCarId(carId);
        List<CarInternetContentForm> carInternetContentForms =
                carInternetContents.stream()
                                   .map(this.carInternetContentFormFactory::buildFormFromEntity)
                                   .map(CarInternetContentFormAdapter::adapt)
                                   .toList();

        return new CarInternetContentEditFormCommand(carInternetContentForms);
    }
}
