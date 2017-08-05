package com.hhistory.mvc.springframework.interceptor;

import com.hhistory.mvc.language.Language;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static com.hhistory.mvc.controller.BaseControllerData.LANGUAGE_DATA;
import static com.hhistory.mvc.controller.BaseControllerData.PICTURES_URL;

/**
 * Created by Gonzalo Carbajosa on 21/01/17.
 */
@Slf4j
public class LocaleChangeInterceptor extends HandlerInterceptorAdapter {
    private CookieLocaleResolver localeResolver;

    public LocaleChangeInterceptor(CookieLocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (this.uriCanChangeLocale(request.getRequestURI())) {
            Optional.ofNullable((Language) request.getAttribute(LANGUAGE_DATA))
                    .map(Language::getIsoCode)
                    .ifPresent(newLanguage -> {
                        this.localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLanguage));
                        this.localeResolver.addCookie(response, newLanguage);
                    });
        }

        // Proceed in any case.
        return true;
    }

    private boolean uriCanChangeLocale(String uri) {
        return !uri.contains(PICTURES_URL);
    }
}
