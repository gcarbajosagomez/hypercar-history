package com.phistory.mvc.cms.controller;

import com.phistory.data.model.car.Car;
import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.command.CarEditFormCommand;
import com.phistory.mvc.cms.command.CarInternetContentEditFormCommand;
import com.phistory.mvc.cms.controller.util.CMSCarControllerUtil;
import com.phistory.mvc.cms.form.CarEditForm;
import com.phistory.mvc.cms.form.EditForm;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.springframework.view.filler.CarEditModelFiller;
import com.phistory.mvc.controller.CarListController;
import com.phistory.mvc.controller.util.CarControllerUtil;
import com.phistory.mvc.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.phistory.mvc.cms.controller.CMSBaseController.CARS_URL;
import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Secured(value = USER_ROLE)
@Controller
@RequestMapping(value = CMS_CONTEXT + CARS_URL)
@Slf4j
public class CMSCarController extends CMSBaseController {

    private CarListController          carListController;
    private ModelFiller                carModelFiller;
    private ModelFiller                pictureModelFiller;
    private CarEditModelFiller         carEditModelFiller;
    private AbstractCarListModelFiller sqlCarListModelFiller;
    private CMSCarControllerUtil       cmsCarControllerUtil;
    private CarControllerUtil          carControllerUtil;

    @Inject
    public CMSCarController(CarListController carListController,
                            ModelFiller carModelFiller,
                            ModelFiller pictureModelFiller,
                            CarEditModelFiller carEditModelFiller,
                            AbstractCarListModelFiller sqlCarListModelFiller,
                            CMSCarControllerUtil cmsCarControllerUtil,
                            CarControllerUtil carControllerUtil) {
        this.carListController = carListController;
        this.carModelFiller = carModelFiller;
        this.pictureModelFiller = pictureModelFiller;
        this.carEditModelFiller = carEditModelFiller;
        this.sqlCarListModelFiller = sqlCarListModelFiller;
        this.cmsCarControllerUtil = cmsCarControllerUtil;
        this.carControllerUtil = carControllerUtil;
    }

    @RequestMapping(method = GET)
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

    @RequestMapping(value = {"/" + PAGINATION_URL},
            method = GET)
    @ResponseBody
    public PaginationDTO handlePagination(Model model, PaginationDTO paginationDTO) {
        return this.carListController.handlePagination(model, paginationDTO);
    }

    @RequestMapping(value = EDIT_URL,
            method = GET)
    public ModelAndView handleNewCar(Model model,
                                     @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) EditFormCommand carFormEditCommand) {
        try {
            this.fillModel(model, carFormEditCommand);
            return new ModelAndView(CAR_EDIT_VIEW_NAME);
        } catch (Exception e) {
            log.error(e.toString(), e);
            model.addAttribute(EXCEPTION_MESSAGE, e.toString());

            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }

    @RequestMapping(value = SAVE_URL,
            method = POST)
    public ModelAndView handleSaveNewCar(Model model,
                                         @Valid @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) EditFormCommand carFormEditCommand,
                                         BindingResult carFormEditCommandResult,
                                         @Valid @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND) CarInternetContentEditFormCommand carInternetContentEditFormCommand,
                                         BindingResult carInternetContentEditFormCommandResult) {
        try {
            if (!carFormEditCommandResult.hasErrors()) {
                this.cmsCarControllerUtil.saveOrEditCar(carFormEditCommand).ifPresent(car -> {
                    String successMessage = getMessageSource().getMessage(ENTITY_SAVED_SUCCESSFULLY_TEXT_SOURCE_KEY,
                                                                          new Object[] {car.toString()},
                                                                          LocaleContextHolder.getLocale());

                    if (!carInternetContentEditFormCommandResult.hasErrors()) {
                        List<CarInternetContentForm> enrichedCarInternetContentForms =
                                this.enrichCarInternetContentForms(carInternetContentEditFormCommand.getEditForms(),
                                                                   car);

                        carInternetContentEditFormCommand.setEditForms(enrichedCarInternetContentForms);

                        try {
                            this.cmsCarControllerUtil.saveOrEditCarInternetContents(carInternetContentEditFormCommand);
                        } catch (Exception e) {
                            log.error("There was an error while editing car with model: {}",
                                      ((CarEditForm) carFormEditCommand.getEditForm()).getModel(),
                                      e);
                            model.addAttribute(EXCEPTION_MESSAGE, e.toString());
                        }
                    }
                    this.cmsCarControllerUtil.reloadCarAndPictureDBEntities(car.getId());
                    model.addAttribute(SUCCESS_MESSAGE, successMessage);
                });
            } else {
                String errorMessage = super.getMessageSource().getMessage(ENTITY_CONTAINED_ERRORS_TEXT_SOURCE_KEY,
                                                                          new Object[] {},
                                                                          LocaleContextHolder.getLocale());
                model.addAttribute(EXCEPTION_MESSAGE, errorMessage);
            }
        } catch (Exception e) {
            log.error("There was an error while saving new car with model: {}",
                      ((CarEditForm) carFormEditCommand.getEditForm()).getModel(),
                      e);
            model.addAttribute(EXCEPTION_MESSAGE, e.toString());
        } finally {
            this.fillModel(model, carFormEditCommand);
        }

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

    @ModelAttribute(value = CAR_EDIT_FORM_COMMAND)
    public EditFormCommand createCarEditFormCommand() {
        return new CarEditFormCommand();
    }

    @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND)
    public CarInternetContentEditFormCommand createCarInternetContentEditFormCommand() {
        return new CarInternetContentEditFormCommand();
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
