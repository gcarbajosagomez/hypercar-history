package com.phistory.mvc.springframework.view.filler;

import com.phistory.data.dao.inmemory.InMemoryCarDAO;
import com.phistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;

import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.controller.CMSBaseController.TECHNOLOGY_STACK_URL;
import static com.phistory.mvc.controller.BaseControllerData.*;

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

    @Inject
    public BaseModelFiller(InMemoryCarDAO inMemoryCarDAO,
                           InMemoryPictureDAO inMemoryPictureDAO,
                           InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO,
                           ResourceBundleMessageSource messageSource) {
        this.inMemoryCarDAO = inMemoryCarDAO;
        this.inMemoryPictureDAO = inMemoryPictureDAO;
        this.inMemoryCarInternetContentDAO = inMemoryCarInternetContentDAO;
        this.messageSource = messageSource;
    }

    @Override
    public void fillModel(Model model) {
        model.addAttribute("indexURL", INDEX_URL);
        model.addAttribute("manufacturerHistoryURL", MANUFACTURER_HISTORY_URL);
        model.addAttribute("cookiesPolicyURL", COOKIES_POLICY_URL);
        model.addAttribute("carsURL", CARS_URL);
        model.addAttribute("enginesURL", ENGINES_URL);
        model.addAttribute("modelsSearchURL", MODELS_SEARCH_URL);
        model.addAttribute("technologyStackURL", TECHNOLOGY_STACK_URL);
        model.addAttribute("siteURL", "http://www.paganihistory.com");
        model.addAttribute(ID, ID);
        model.addAttribute("engineIdData", ENGINE_ID);
        model.addAttribute("carsPerPage", CARS_PER_PAGE);
        model.addAttribute("pagNum", PAG_NUM);
        model.addAttribute("contentToSearch", CONTENT_TO_SEARCH);
        model.addAttribute("languageCookieName", LANGUAGE_COOKIE_NAME);
        model.addAttribute("cmsContext", CMS_CONTEXT);
        model.addAttribute("languageQueryString", LANGUAGE_DATA);
        model.addAttribute("doNotTrackParam", DO_NOT_TRACK_REQUEST_PARAM);
        model.addAttribute("carsHeaderLinkValue", this.buildCarsHeaderLinkValue());
        model.addAttribute(MODELS, this.inMemoryCarDAO.getAllVisibleOrderedByProductionStartDate());
        model.addAttribute(NUMBER_OF_PICTURES, this.inMemoryPictureDAO.getAllIds().size());
        model.addAttribute(NUMBER_OF_VIDEOS, this.inMemoryCarInternetContentDAO.getAllVideos().size());
        model.addAttribute("languageSpanishCode", LANGUAGE_SPANISH_CODE);
        model.addAttribute("languageEnglishCode", LANGUAGE_ENGLISH_CODE);
    }

    private String buildCarsHeaderLinkValue() {
        return this.messageSource.getMessage(CARS_HEADER_LINK_MESSAGE_ID,
                                             new Object[] {this.inMemoryCarDAO.countVisibleCars()},
                                             LocaleContextHolder.getLocale());
    }
}
