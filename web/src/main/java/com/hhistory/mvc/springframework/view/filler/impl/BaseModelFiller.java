package com.hhistory.mvc.springframework.view.filler.impl;

import com.hhistory.data.command.CarQueryCommand;
import com.hhistory.data.dao.PictureDAO;
import com.hhistory.data.dao.inmemory.InMemoryCarDAO;
import com.hhistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.hhistory.data.dao.inmemory.InMemoryManufacturerDAO;
import com.hhistory.data.dao.inmemory.impl.InMemoryPictureDAOImpl;
import com.hhistory.data.model.Manufacturer;
import com.hhistory.data.model.car.Car;
import com.hhistory.mvc.service.ManufacturerService;
import com.hhistory.mvc.springframework.view.filler.ModelFiller;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static com.hhistory.data.dao.inmemory.impl.InMemoryCarDAOImpl.IN_MEMORY_CAR_DAO;
import static com.hhistory.data.dao.inmemory.impl.InMemoryPictureDAOImpl.*;
import static com.hhistory.data.dao.sql.SqlPictureDAO.SQL_PICTURE_DAO;
import static com.hhistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.hhistory.mvc.cms.controller.CMSBaseController.TECHNOLOGY_STACK_URL;
import static com.hhistory.mvc.command.PictureLoadAction.LOAD_MANUFACTURER_LOGO;
import static com.hhistory.mvc.controller.BaseControllerData.*;
import static com.hhistory.mvc.dto.PaginationDTO.ITEMS_PER_PAGE_DEFAULT_VALUE;
import static com.hhistory.mvc.language.Language.ENGLISH;
import static com.hhistory.mvc.language.Language.SPANISH;

/**
 * Fills a Spring Framework Model with the basic information for the web context
 *
 * @author gonzalo
 */
@Component
public class BaseModelFiller implements ModelFiller {
    private static final String CARS_HEADER_BASE_LINK_MESSAGE_ID = ".cars.all";

    private InMemoryCarDAO                inMemoryCarDAO;
    private PictureDAO                    pictureDAO;
    private InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO;
    private ResourceBundleMessageSource   messageSource;
    private ManufacturerService           manufacturerService;
    private InMemoryManufacturerDAO       inMemoryManufacturerDAO;

    @Inject
    public BaseModelFiller(InMemoryCarDAO inMemoryCarDAO,
                           @Named(SQL_PICTURE_DAO) PictureDAO pictureDAO,
                           InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO,
                           ResourceBundleMessageSource messageSource,
                           ManufacturerService manufacturerService,
                           InMemoryManufacturerDAO inMemoryManufacturerDAO) {
        this.inMemoryCarDAO = inMemoryCarDAO;
        this.pictureDAO = pictureDAO;
        this.inMemoryCarInternetContentDAO = inMemoryCarInternetContentDAO;
        this.messageSource = messageSource;
        this.manufacturerService = manufacturerService;
        this.inMemoryManufacturerDAO = inMemoryManufacturerDAO;
    }

    @Override
    public Model fillModel(Model model) {
        model.addAttribute("indexURL", INDEX_URL);
        model.addAttribute("manufacturerHistoryURL", MANUFACTURER_HISTORY_URL);
        model.addAttribute("cookiesPolicyURL", COOKIES_POLICY_URL);
        model.addAttribute("carsURL", CARS_URL);
        model.addAttribute("carURL", CAR_URL);
        model.addAttribute("engineURL", ENGINE_URL);
        model.addAttribute("searchURL", SEARCH_URL);
        model.addAttribute("technologyStackURL", TECHNOLOGY_STACK_URL);
        model.addAttribute("picturesURL", PICTURES_URL);
        model.addAttribute(ID, ID);
        model.addAttribute("engineIdData", ENGINE_ID);
        model.addAttribute("carsPerPage", CARS_PER_PAGE);
        model.addAttribute("pagNum", PAG_NUM);
        model.addAttribute("contentToSearch", CONTENT_TO_SEARCH);
        model.addAttribute("cmsContext", CMS_CONTEXT);
        model.addAttribute("doNotTrackParam", DO_NOT_TRACK_REQUEST_PARAM);
        model.addAttribute("languageCookieName", LANGUAGE_COOKIE_NAME);
        model.addAttribute("languageSpanishCode", SPANISH.getIsoCode());
        model.addAttribute("languageEnglishCode", ENGLISH.getIsoCode());
        model.addAttribute(NUMBER_OF_PICTURES, this.pictureDAO.getAllIds().size());
        model.addAttribute(NUMBER_OF_VIDEOS, this.inMemoryCarInternetContentDAO.getAllVideos().size());
        model.addAttribute("defaultCarsPerPageData", ITEMS_PER_PAGE_DEFAULT_VALUE);

        this.manufacturerService.getInMemoryEntityFromModel(model)
                                .ifPresent(manufacturer -> {
                                    model.addAttribute("carsHeaderLinkValue", this.buildCarsHeaderLinkValue(model,
                                                                                                            manufacturer));
                                    CarQueryCommand queryCommand = CarQueryCommand.builder()
                                                                                  .manufacturer(manufacturer)
                                                                                  .build();
                                    List<Car> models =
                                            this.inMemoryCarDAO.getByQueryCommandOrderedByProductionStartDate(queryCommand);
                                    model.addAttribute(ALL_MODELS, models);

                                    queryCommand = CarQueryCommand.builder()
                                                                  .manufacturer(manufacturer)
                                                                  .visible(true)
                                                                  .build();
                                    List<Car> visibleModels =
                                            this.inMemoryCarDAO.getByQueryCommandOrderedByProductionStartDate(queryCommand);
                                    model.addAttribute(VISIBLE_MODELS, visibleModels);
                                });

        model.addAttribute("loadManufacturerLogoAction", LOAD_MANUFACTURER_LOGO.getName());
        model.addAttribute(MANUFACTURER_ENTITIES, this.inMemoryManufacturerDAO.getEntities());

        return model;
    }

    private String buildCarsHeaderLinkValue(Model model, Manufacturer manufacturer) {
        String manufacturerName = this.manufacturerService.getFromModel(model).getName();
        return this.messageSource.getMessage(manufacturerName + CARS_HEADER_BASE_LINK_MESSAGE_ID,
                                             new Object[] {this.inMemoryCarDAO.countVisibleCars(manufacturer)},
                                             LocaleContextHolder.getLocale());
    }
}
