package com.phistory.mvc.springframework.view.filler.impl;

import com.phistory.data.dao.inmemory.InMemoryCarDAO;
import com.phistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import com.phistory.data.model.Manufacturer;
import com.phistory.data.model.car.Car;
import com.phistory.mvc.service.ManufacturerService;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;

import java.util.List;

import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.controller.CMSBaseController.TECHNOLOGY_STACK_URL;
import static com.phistory.mvc.controller.BaseControllerData.*;
import static com.phistory.mvc.dto.PaginationDTO.ITEMS_PER_PAGE_DEFAULT_VALUE;
import static com.phistory.mvc.language.Language.ENGLISH;
import static com.phistory.mvc.language.Language.SPANISH;

/**
 * Fills a Spring Framework Model with the basic information for the web context
 *
 * @author gonzalo
 */
@Component
public class BaseModelFiller implements ModelFiller {
    private static final String CARS_HEADER_LINK_MESSAGE_ID = "cars.all";

    private InMemoryCarDAO                inMemoryCarDAO;
    private InMemoryPictureDAO            inMemoryPictureDAO;
    private InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO;
    private ResourceBundleMessageSource   messageSource;
    private ManufacturerService           manufacturerService;

    @Inject
    public BaseModelFiller(InMemoryCarDAO inMemoryCarDAO,
                           InMemoryPictureDAO inMemoryPictureDAO,
                           InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO,
                           ResourceBundleMessageSource messageSource,
                           ManufacturerService manufacturerService) {
        this.inMemoryCarDAO = inMemoryCarDAO;
        this.inMemoryPictureDAO = inMemoryPictureDAO;
        this.inMemoryCarInternetContentDAO = inMemoryCarInternetContentDAO;
        this.messageSource = messageSource;
        this.manufacturerService = manufacturerService;
    }

    @Override
    public Model fillModel(Model model) {
        model.addAttribute("indexURL", INDEX_URL);
        model.addAttribute("manufacturerHistoryURL", MANUFACTURER_HISTORY_URL);
        model.addAttribute("cookiesPolicyURL", COOKIES_POLICY_URL);
        model.addAttribute("carsURL", CARS_URL);
        model.addAttribute("engineURL", ENGINE_URL);
        model.addAttribute("searchURL", SEARCH_URL);
        model.addAttribute("technologyStackURL", TECHNOLOGY_STACK_URL);
        model.addAttribute("siteURL", "http://www.paganihistory.com");
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
        model.addAttribute(NUMBER_OF_PICTURES, this.inMemoryPictureDAO.getAllIds().size());
        model.addAttribute(NUMBER_OF_VIDEOS, this.inMemoryCarInternetContentDAO.getAllVideos().size());
        model.addAttribute("defaultCarsPerPageData", ITEMS_PER_PAGE_DEFAULT_VALUE);

        this.manufacturerService.getInMemoryEntityFromModel(model)
                                .ifPresent(manufacturer -> {
                                    model.addAttribute("carsHeaderLinkValue", this.buildCarsHeaderLinkValue(manufacturer));
                                    List<Car> models =
                                            this.inMemoryCarDAO.getAllVisibleOrderedByProductionStartDate(manufacturer);
                                    model.addAttribute(ALL_MODELS, models);
                                });

        return model;
    }

    private String buildCarsHeaderLinkValue(Manufacturer manufacturer) {
        return this.messageSource.getMessage(CARS_HEADER_LINK_MESSAGE_ID,
                                             new Object[] {this.inMemoryCarDAO.countVisibleCars(manufacturer)},
                                             LocaleContextHolder.getLocale());
    }
}
