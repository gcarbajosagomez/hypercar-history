package com.phistory.mvc.controller.cms;

import com.phistory.data.model.car.Car;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.command.CarInternetContentEditCommand;
import com.phistory.mvc.cms.form.CarForm;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.creator.CarFormCreator;
import com.phistory.mvc.cms.form.creator.CarInternetContentFormCreator;
import com.phistory.mvc.cms.springframework.view.CarEditModelFiller;
import com.phistory.mvc.controller.cms.util.CMSCarControllerUtil;
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
import java.util.ArrayList;
import java.util.List;

import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.controller.cms.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Gonzalo
 */
@Secured(USER_ROLE)
@Controller
@Slf4j
@RequestMapping(value = CMS_CONTEXT + CARS + "/{" + ID + "}")
public class CMSCarEditController extends CMSBaseController {
    @Inject
    private CMSCarControllerUtil carControllerUtil;
    @Inject
    private CarFormCreator carFormCreator;
    @Inject
    private CarInternetContentFormCreator carInternetContentFormCreator;
    @Inject
    private ModelFiller carModelFiller;
    @Inject
    private CarEditModelFiller carEditModelFiller;
    @Inject
    private ModelFiller pictureModelFiller;

    @RequestMapping(value = EDIT_URL,
                    method = GET)
    public ModelAndView handleEditCarDefault(Model model,
                                             @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand carFormEditCommand) {
        this.fillModel(model, carFormEditCommand);
        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    @RequestMapping(value = EDIT_URL,
                    method = {POST, PUT})
    @ResponseBody
    public ModelAndView handleEditCar(Model model,
                                      @Valid @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand carFormEditCommand,
                                      BindingResult carFormEditCommandResult,
                                      @Valid @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND) CarInternetContentEditCommand carInternetContentEditCommand,
                                      BindingResult carInternetContentEditCommandResult) {
        try {
            if (!carFormEditCommandResult.hasErrors()) {
                Car car = this.carControllerUtil.saveOrEditCar(carFormEditCommand);
                String successMessage = super.getMessageSource()
                                             .getMessage(ENTITY_EDITED_SUCCESSFULLY_RESULT_MESSAGE,
                                                         new Object[]{car.getFriendlyName()},
                                                         LocaleContextHolder.getLocale());

                if (!carInternetContentEditCommandResult.hasErrors()) {
                    this.carControllerUtil.saveOrEditCarInternetContents(carInternetContentEditCommand);
                }
                model.addAttribute(SUCCESS_MESSAGE, successMessage);
            } else {
                String errorMessage = super.getMessageSource()
                                           .getMessage(ENTITY_CONTAINED_ERRORS_RESULT_MESSAGE,
                                                       null,
                                                       LocaleContextHolder.getLocale());

                model.addAttribute(EXCEPTION_MESSAGE, errorMessage);
            }
        } catch (Exception e) {
            log.error("There was an error while trying to edit car with model: {}", carFormEditCommand.getCarForm().getModel(), e);
            model.addAttribute(EXCEPTION_MESSAGE, e.toString());
        } finally {
            this.fillModel(model, carFormEditCommand);
        }

        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    @RequestMapping(value = DELETE_URL,
                    method = DELETE)
    @ResponseBody
    public ModelAndView handleDeleteCar(Model model,
                                        @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand carFormEditCommand,
                                        BindingResult result) {
        if (!result.hasErrors()) {
            try {
                this.carControllerUtil.deleteCar(carFormEditCommand);
                String successMessage = super.getMessageSource()
                                             .getMessage(ENTITY_DELETED_SUCCESSFULLY_RESULT_MESSAGE,
                                                         new Object[]{carFormEditCommand.getCarForm().getManufacturer().getFriendlyName() + " " +
                                                                      carFormEditCommand.getCarForm().getModel()},
                                                         LocaleContextHolder.getLocale());

                model.addAttribute(SUCCESS_MESSAGE, successMessage);
                model.addAttribute(CAR_EDIT_FORM_COMMAND, new CarFormEditCommand());
            } catch (Exception e) {
                log.error("There was an error while trying to delete car with model: {}", carFormEditCommand.getCarForm().getModel(), e);
                model.addAttribute(EXCEPTION_MESSAGE, e.toString());
            } finally {
                this.fillModel(model, carFormEditCommand);
            }
        }

        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    @ModelAttribute(value = CAR_EDIT_FORM_COMMAND)
    public CarFormEditCommand createCarEditFormCommand(@PathVariable(ID) Long carId) {
        Car car = getCarDAO().getById(carId);
        CarForm carForm = this.carFormCreator.createFormFromEntity(car);
        CarFormEditCommand command = new CarFormEditCommand(carForm);

        return command;
    }

    @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND)
    public CarInternetContentEditCommand createCarInternetContentEditFormCommand(@PathVariable(ID) Long carId) throws Exception {
        List<CarInternetContent> carInternetContents = super.getCarInternetContentDAO().getByCarId(carId);
        List<CarInternetContentForm> carInternetContentForms = new ArrayList<>();
        carInternetContents.forEach(internetContent -> carInternetContentForms.add(this.carInternetContentFormCreator.createFormFromEntity(internetContent)));

        return new CarInternetContentEditCommand(carInternetContentForms);
    }

    /**
     * Fill the supplied {@link Model}
     *
     * @param model
     * @param carFormEditCommand
     */
    private void fillModel(Model model, CarFormEditCommand carFormEditCommand) {
        this.carModelFiller.fillModel(model);
        this.carEditModelFiller.fillCarEditModel(model, carFormEditCommand);
        this.pictureModelFiller.fillModel(model);
    }
}
