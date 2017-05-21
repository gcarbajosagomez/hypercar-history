package com.phistory.mvc.cms.controller;

import com.phistory.data.model.car.Car;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.mvc.cms.command.*;
import com.phistory.mvc.cms.controller.util.CMSCarControllerUtil;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.CarInternetContentFormAdapter;
import com.phistory.mvc.cms.form.EditForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import com.phistory.mvc.cms.form.factory.impl.CarInternetContentFormFactory;
import com.phistory.mvc.cms.service.EntityManagementService;
import com.phistory.mvc.cms.service.crud.CrudService;
import com.phistory.mvc.cms.springframework.view.filler.CarEditModelFiller;
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

import static com.phistory.mvc.cms.command.EntityManagementQueryType.REMOVE_CAR;
import static com.phistory.mvc.cms.controller.CMSBaseController.CARS_URL;
import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.service.crud.impl.CarCrudService.CAR_CRUD_SERVICE;
import static com.phistory.mvc.cms.service.crud.impl.CarInternetContentCrudService.CAR_INTERNET_CONTENT_CRUD_SERVICE;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
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

    private CrudService                   carCrudService;
    private CrudService                   carInternetContentCrudService;
    private CMSCarControllerUtil          cmsCarControllerUtil;
    private EntityFormFactory             carFormFactory;
    private CarInternetContentFormFactory carInternetContentFormFactory;
    private ModelFiller                   carModelFiller;
    private CarEditModelFiller            carEditModelFiller;
    private ModelFiller                   pictureModelFiller;
    private EntityManagementService       entityManagementService;

    @Inject
    public CMSCarEditController(@Named(CAR_CRUD_SERVICE) CrudService carCrudService,
                                @Named(CAR_INTERNET_CONTENT_CRUD_SERVICE) CrudService carInternetContentCrudService,
                                CMSCarControllerUtil cmsCarControllerUtil,
                                EntityFormFactory carFormFactory,
                                CarInternetContentFormFactory carInternetContentFormFactory,
                                ModelFiller carModelFiller,
                                CarEditModelFiller carEditModelFiller,
                                ModelFiller pictureModelFiller,
                                EntityManagementService entityManagementService) {
        this.carCrudService = carCrudService;
        this.carInternetContentCrudService = carInternetContentCrudService;
        this.cmsCarControllerUtil = cmsCarControllerUtil;
        this.carFormFactory = carFormFactory;
        this.carInternetContentFormFactory = carInternetContentFormFactory;
        this.carModelFiller = carModelFiller;
        this.carEditModelFiller = carEditModelFiller;
        this.pictureModelFiller = pictureModelFiller;
        this.entityManagementService = entityManagementService;
    }

    @GetMapping(EDIT_URL)
    public ModelAndView handleEditCarDefault(Model model,
                                             @ModelAttribute(CAR_EDIT_FORM_COMMAND) EditFormCommand carFormEditCommand) {
        this.fillModel(model, carFormEditCommand);
        return new ModelAndView(CAR_EDIT_VIEW_NAME);
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

    private List<CarInternetContentForm> enrichCarInternetContentForms(List<CarInternetContentForm> carInternetContentForms,
                                                         Car car) {
        return carInternetContentForms.stream()
                                      .map(carInternetContentForm -> {
                                          carInternetContentForm.setCar(car);
                                          return carInternetContentForm;
                                      })
                                      .collect(Collectors.toList());
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
        Car car = (Car) super.getSqlCarRepository().findOne(carId);
        EditForm carEditForm = this.carFormFactory.buildFormFromEntity(car);

        return new CarEditFormCommand(carEditForm);
    }

    @ModelAttribute(CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND)
    public CarInternetContentEditFormCommand createCarInternetContentEditFormCommand(@PathVariable(ID) Long carId) {
        List<CarInternetContent> carInternetContents = super.getSqlCarInternetContentRepository().getByCarId(carId);
        List<CarInternetContentForm> carInternetContentForms =
                carInternetContents.stream()
                                   .map(this.carInternetContentFormFactory::buildFormFromEntity)
                                   .map(CarInternetContentFormAdapter::adapt)
                                   .collect(Collectors.toList());

        return new CarInternetContentEditFormCommand(carInternetContentForms);
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
        model = this.pictureModelFiller.fillModel(model);
        return model;
    }
}
