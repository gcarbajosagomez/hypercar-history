package com.hhistory.mvc.springframework.filter;

import com.hhistory.mvc.language.Language;
import com.hhistory.mvc.manufacturer.Manufacturer;
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
import java.util.stream.Stream;

import static com.hhistory.mvc.cms.controller.CMSBaseController.STATIC_RESOURCES_URI;
import static com.hhistory.mvc.controller.BaseControllerData.*;
import static com.hhistory.mvc.manufacturer.Manufacturer.PAGANI;

/**
 * Created by Gonzalo Carbajosa on 21/01/17.
 */
@Slf4j
public class PathVariableLocaleFilter extends OncePerRequestFilter {

    private static final String DOMAIN_PREFIX       = "www.";
    private static final String DOMAIN_SUFFIX       = "history.com";
    private static final String HOST_REQUEST_HEADER = "Host";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestHost = request.getHeader(HOST_REQUEST_HEADER);
        log.info("Request coming from domain {}", requestHost);
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
        return !(url.contains(STATIC_RESOURCES_URI) || url.contains(PICTURES_URL));
    }

    private String addManufacturerToRequest(String url, HttpServletRequest request) {
        return this.getManufacturerFromURL(url, request)
                   .map(manufacturer -> {
                       request.setAttribute(MANUFACTURER_DATA, manufacturer);
                       return StringUtils.removeStart(url, '/' + manufacturer.getShortName());
                   })
                   .orElse(url);
    }

    private Optional<Manufacturer> getManufacturerFromURL(String url, HttpServletRequest request) {
        if (url.matches(".*/[a-z]{1}/.*")) {
            String[] variables = url.split("/");
            String manufacturerShortName = variables[1];
            request.setAttribute(REQUEST_CONTAINS_MANUFACTURER_DATA, true);
            return Manufacturer.mapShortName(manufacturerShortName);
        }

        //if there's no manufacturer path in que URL, we extract it from the domain name
        return Optional.of(Stream.of(Manufacturer.values())
                                 .filter(manufacturer -> {
                                     String requestHost = request.getHeader(HOST_REQUEST_HEADER);
                                     String manufacturerPath = DOMAIN_PREFIX + manufacturer.getName() + DOMAIN_SUFFIX;
                                     return requestHost.equals(manufacturerPath);
                                 })
                                 .findFirst()
                                 .orElse(PAGANI));
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
