package com.phistory.mvc.controller;

import com.phistory.data.dao.inmemory.InMemoryCarDAO;
import com.phistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.phistory.data.dao.inmemory.InMemoryManufacturerDAO;
import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import com.phistory.data.dao.sql.impl.SQLCarDAO;
import com.phistory.data.dao.sql.impl.SQLCarInternetContentDAO;
import com.phistory.data.dao.sql.impl.SQLContentSearchDAO;
import com.phistory.data.dao.sql.impl.SQLPictureDAO;
import com.phistory.mvc.service.URILoggingService;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mobile.device.Device;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Base controller that contains common data and functionality
 *
 * @author Gonzalo
 */
@Slf4j
public class BaseController extends BaseControllerData {

    @Inject
    @Getter
    private SQLCarDAO                     sqlCarDAO;
    @Inject
    @Getter
    private InMemoryCarDAO                inMemoryCarDAO;
    @Inject
    @Getter
    private SQLPictureDAO                 sqlPictureDAO;
    @Inject
    @Getter
    private InMemoryPictureDAO            inMemoryPictureDAO;
    @Inject
    @Getter
    private SQLContentSearchDAO           sqlContentSearchDAO;
    @Inject
    @Getter
    private SQLCarInternetContentDAO      sqlCarInternetContentDAO;
    @Inject
    @Getter
    private InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO;
    @Inject
    @Getter
    private InMemoryManufacturerDAO       inMemoryManufacturerDAO;
    @Inject
    private ModelFiller                   baseModelFiller;
    @Getter
    @Inject
    private ResourceBundleMessageSource   messageSource;
    @Inject
    private LocaleResolver                localeResolver;
    @Inject
    private URILoggingService             uriLoggingService;

    @ModelAttribute
    public void fillBaseModel(@RequestParam(value = DO_NOT_TRACK_REQUEST_PARAM, required = false) boolean dnt,
                              Model model,
                              Device device,
                              HttpServletRequest request) {
        String requestURI = this.uriLoggingService.logURI(request);
        model.addAttribute("requestURI", requestURI);
        model.addAttribute("requestIsDesktop", device.isNormal());
        model.addAttribute("deviceMake", device.getDevicePlatform().name());
        model.addAttribute("doNotTrack", dnt);

        this.baseModelFiller.fillModel(model);
    }
}
