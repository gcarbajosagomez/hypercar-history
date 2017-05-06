package com.phistory.mvc.cms.controller;

import com.phistory.data.model.car.Car;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.mvc.cms.command.CarEditFormCommand;
import com.phistory.mvc.cms.command.CarInternetContentEditFormCommand;
import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import com.phistory.mvc.cms.controller.util.CMSCarControllerUtil;
import com.phistory.mvc.cms.form.CarEditForm;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import com.phistory.mvc.cms.form.factory.impl.CarInternetContentFormFactory;
import com.phistory.mvc.cms.service.EntityManagementService;
import com.phistory.mvc.cms.springframework.view.filler.CarEditModelFiller;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
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

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.phistory.mvc.cms.command.EntityManagementQueryType.REMOVE_CAR;
import static com.phistory.mvc.cms.controller.CMSBaseController.CARS_URL;
import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Gonzalo
 */
@Secured(USER_ROLE)
@Controller
@RequestMapping(value = CMS_CONTEXT + CARS_URL + "/{" + ID + "}")
@Slf4j
public class CMSCarEditController extends CMSBaseController {

    private CMSCarControllerUtil          carControllerUtil;
    private EntityFormFactory             carFormFactory;
    private CarInternetContentFormFactory carInternetContentFormFactory;
    private ModelFiller                   carModelFiller;
    private CarEditModelFiller            carEditModelFiller;
    private ModelFiller                   pictureModelFiller;
    private EntityManagementService       entityManagementService;

    @Inject
    public CMSCarEditController(CMSCarControllerUtil carControllerUtil,
                                EntityFormFactory carFormFactory,
                                CarInternetContentFormFactory carInternetContentFormFactory,
                                ModelFiller carModelFiller,
                                CarEditModelFiller carEditModelFiller,
                                ModelFiller pictureModelFiller,
                                EntityManagementService entityManagementService) {
        this.carControllerUtil = carControllerUtil;
        this.carFormFactory = carFormFactory;
        this.carInternetContentFormFactory = carInternetContentFormFactory;
        this.carModelFiller = carModelFiller;
        this.carEditModelFiller = carEditModelFiller;
        this.pictureModelFiller = pictureModelFiller;
        this.entityManagementService = entityManagementService;
    }

    @RequestMapping(value = EDIT_URL,
            method = GET)
    public ModelAndView handleEditCarDefault(Model model,
                                             @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) EditFormCommand carFormEditCommand) {
        this.fillModel(model, carFormEditCommand);
        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    @RequestMapping(value = EDIT_URL,
            method = {POST, PUT})
    @ResponseBody
    public ModelAndView handleEditCar(Model model,
                                      @Valid @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) EditFormCommand carFormEditCommand,
                                      BindingResult carFormEditCommandResult,
                                      @Valid @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND) CarInternetContentEditFormCommand
                                              carInternetContentEditFormCommand,
                                      BindingResult carInternetContentEditFormCommandResult) {

        try {
            if (!carFormEditCommandResult.hasErrors()) {
                Car car = this.carControllerUtil.saveOrEditCar(carFormEditCommand)
                                                .orElseThrow(Exception::new);

                String successMessage = super.getMessageSource()
                                             .getMessage(ENTITY_EDITED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                                         new Object[] {car.toString()},
                                                         LocaleContextHolder.getLocale());

                if (!carInternetContentEditFormCommandResult.hasErrors()) {
                    this.carControllerUtil.saveCarInternetEditCommand(carInternetContentEditFormCommand);
                }

                this.carControllerUtil.reloadCarAndPictureDBEntities(car.getId());
                model.addAttribute(SUCCESS_MESSAGE, successMessage);
            } else {
                String errorMessage = super.getMessageSource()
                                           .getMessage(ENTITY_CONTAINED_ERRORS_TEXT_SOURCE_KEY,
                                                       null,
                                                       LocaleContextHolder.getLocale());

                model.addAttribute(EXCEPTION_MESSAGE, errorMessage);
            }
        } catch (Exception e) {
            log.error("There was an error while editing car with model: {}",
                      ((CarEditForm) carFormEditCommand.getEditForm()).getModel(),
                      e);
            model.addAttribute(EXCEPTION_MESSAGE, e.toString());
        } finally {
            this.fillModel(model, carFormEditCommand);
            return new ModelAndView(CAR_EDIT_VIEW_NAME);
        }
    }

    @RequestMapping(value = DELETE_URL,
            method = DELETE)
    @ResponseBody
    public ModelAndView handleDeleteCar(Model model,
                                        @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) EditFormCommand carFormEditCommand,
                                        BindingResult result) {

        if (!result.hasErrors()) {
            CarEditForm carEditForm = (CarEditForm) carFormEditCommand.getEditForm();
            try {
                this.carControllerUtil.deleteCar(carFormEditCommand);

                EntityManagementLoadCommand entityManagementLoadCommand = new EntityManagementLoadCommand();
                entityManagementLoadCommand.setCarId(carEditForm.getId());
                entityManagementLoadCommand.setQueryType(REMOVE_CAR);
                this.entityManagementService.reloadEntities(entityManagementLoadCommand);

                String successMessage = super.getMessageSource()
                                             .getMessage(ENTITY_DELETED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                                         new Object[] {
                                                                 carEditForm.getManufacturer().toString() +
                                                                 " " +
                                                                 carEditForm.getModel()},
                                                         LocaleContextHolder.getLocale());

                model.addAttribute(SUCCESS_MESSAGE, successMessage);
                model.addAttribute(CAR_EDIT_FORM_COMMAND, new CarEditFormCommand());
            } catch (Exception e) {
                log.error("There was an error while deleting car with model: {}",
                          carEditForm.getModel(),
                          e);
                model.addAttribute(EXCEPTION_MESSAGE, e.toString());
            } finally {
                this.fillModel(model, carFormEditCommand);
            }
        }

        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    @ModelAttribute(value = CAR_EDIT_FORM_COMMAND)
    public EditFormCommand createCarEditFormCommand(@PathVariable(ID) Long carId) {
        Car car = (Car) super.getSqlCarRepository().findOne(carId);
        CarEditForm carEditForm = (CarEditForm) this.carFormFactory.buildFormFromEntity(car);
        return new CarEditFormCommand(carEditForm);
    }

    @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND)
    public CarInternetContentEditFormCommand createCarInternetContentEditFormCommand(@PathVariable(ID) Long carId) {
        List<CarInternetContent> carInternetContents = super.getSqlCarInternetContentRepository().getByCarId(carId);
        List<CarInternetContentForm> carInternetContentForms =
                carInternetContents.stream()
                                   .map(this.carInternetContentFormFactory::buildFormFromEntity)
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
