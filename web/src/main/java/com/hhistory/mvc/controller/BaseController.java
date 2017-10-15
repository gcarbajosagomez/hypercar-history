package com.hhistory.mvc.controller;

import com.hhistory.data.dao.inmemory.InMemoryCarDAO;
import com.hhistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.hhistory.data.dao.inmemory.InMemoryManufacturerDAO;
import com.hhistory.data.dao.sql.SqlCarDAO;
import com.hhistory.data.dao.sql.SqlContentSearchDAO;
import com.hhistory.data.dao.sql.SqlPictureDAO;
import com.hhistory.data.dao.sql.SqlPictureRepository;
import com.hhistory.mvc.controller.util.CarControllerUtil;
import com.hhistory.mvc.language.Language;
import com.hhistory.mvc.manufacturer.Manufacturer;
import com.hhistory.mvc.service.ManufacturerService;
import com.hhistory.mvc.service.URILoggingService;
import com.hhistory.mvc.springframework.view.filler.ModelFiller;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.mobile.device.Device;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.hhistory.data.dao.sql.SqlCarRepository.CAR_REPOSITORY;
import static com.hhistory.mvc.manufacturer.Manufacturer.PAGANI;

/**
 * Base controller that contains common data and functionality
 *
 * @author Gonzalo
 */
@Slf4j
public abstract class BaseController extends BaseControllerData {

    @Inject
    @Getter
    private SqlCarDAO sqlCarDAO;

    @Inject
    @Named(CAR_REPOSITORY)
    @Getter
    private CrudRepository sqlCarRepository;

    @Inject
    @Getter
    private InMemoryCarDAO inMemoryCarDAO;

    @Inject
    @Getter
    private SqlPictureDAO sqlPictureDAO;

    @Inject
    @Getter
    private SqlPictureRepository sqlPictureRepository;

    @Inject
    @Getter
    private SqlContentSearchDAO sqlContentSearchDAO;

    @Inject
    @Getter
    private InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO;

    @Inject
    @Getter
    private InMemoryManufacturerDAO inMemoryManufacturerDAO;

    @Inject
    private ModelFiller baseModelFiller;

    @Inject
    @Getter
    private ResourceBundleMessageSource messageSource;

    @Inject
    private URILoggingService uriLoggingService;

    @Inject
    @Getter
    private CarControllerUtil carControllerUtil;

    @Inject
    @Getter
    private ManufacturerService manufacturerService;

    @Inject
    private CookieLocaleResolver cookieLocaleResolver;

    @ModelAttribute
    public ModelAndView fillBaseModel(@RequestParam(value = DO_NOT_TRACK_REQUEST_PARAM, required = false) boolean dnt,
                                      Model model,
                                      Device device,
                                      HttpServletRequest request) {
        try {
            String requestURI = this.uriLoggingService.logURI(request);
            model.addAttribute("requestURI", requestURI);
            model.addAttribute("requestIsDesktop", device.isNormal());
            model.addAttribute("deviceMake", device.getDevicePlatform().name());
            model.addAttribute("doNotTrack", dnt);
            model.addAttribute(REQUEST_CONTAINS_MANUFACTURER_DATA, request.getAttribute(REQUEST_CONTAINS_MANUFACTURER_DATA));
            model.addAttribute(MANUFACTURER, Optional.ofNullable((Manufacturer) request.getAttribute(MANUFACTURER_DATA))
                                                     .orElse(PAGANI));

            Optional<Language> languageOptional = Language.map(this.cookieLocaleResolver.resolveLocale(request).getLanguage());
            languageOptional.ifPresent(language -> model.addAttribute(LANGUAGE_DATA, language));

            this.manufacturerService.mapToInMemoryEntity(model)
                                    .ifPresent(manufacturer -> model.addAttribute(MANUFACTURER_ENTITY, manufacturer));

            this.baseModelFiller.fillModel(model);

            return new ModelAndView();
        } catch (Exception e) {
            log.error(e.toString(), e);

            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }

    protected Model fillModel(Model model) {
        return model;
    }
}
