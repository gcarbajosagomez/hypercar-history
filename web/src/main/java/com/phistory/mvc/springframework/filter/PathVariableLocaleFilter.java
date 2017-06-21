package com.phistory.mvc.springframework.filter;

import com.phistory.mvc.language.Language;
import com.phistory.mvc.manufacturer.Manufacturer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.controller.CMSBaseController.STATIC_RESOURCES_URI;
import static com.phistory.mvc.controller.BaseControllerData.*;

/**
 * Created by Gonzalo Carbajosa on 21/01/17.
 */
@Slf4j
public class PathVariableLocaleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String url = StringUtils.defaultString(request.getRequestURI()
                                                      .substring(request.getContextPath().length()));

        if (this.urlContainsInfo(url)) {
            url = this.addManufacturerToRequest(url, request);
            url = this.addLanguageToRequest(url, request);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    private boolean urlContainsInfo(String url) {
        return !url.contains(STATIC_RESOURCES_URI);
    }

    private String addManufacturerToRequest(String url, HttpServletRequest request) {
        return this.getManufacturerFromURL(url)
                   .map(manufacturer -> {
                       request.setAttribute(MANUFACTURER_DATA, manufacturer);
                       return StringUtils.removeStart(url, '/' + manufacturer.getShortName());
                   })
                   .orElse(url);
    }

    private Optional<Manufacturer> getManufacturerFromURL(String url) {
        String manufacturerShortName = "";
        String[] variables = url.split("/");
        if (variables.length >= 1) {
            manufacturerShortName = variables[1];
        }
        return Manufacturer.mapShortName(manufacturerShortName);
    }

    private String addLanguageToRequest(String url, HttpServletRequest request) {
        return this.getLanguageFromURL(url)
                   .map(language -> {
                       request.setAttribute(LANGUAGE_DATA, language);
                       return StringUtils.removeStart(url, '/' + language.getIsoCode());
                   })
                   .orElse(url);
    }

    private Optional<Language> getLanguageFromURL(String url) {
        String languageName = "";
        String[] variables = url.split("/");
        if (variables.length >= 1) {
            languageName = variables[1];
        }
        return Language.map(languageName);
    }
}
