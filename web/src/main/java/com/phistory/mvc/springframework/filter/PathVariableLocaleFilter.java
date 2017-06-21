package com.phistory.mvc.springframework.filter;

import com.phistory.mvc.language.Language;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.phistory.mvc.controller.BaseControllerData.LANGUAGE_DATA;
import static com.phistory.mvc.language.Language.*;
import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Created by Gonzalo Carbajosa on 21/01/17.
 */
@Slf4j
public class PathVariableLocaleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("Request coming from domain {}", request.getHeader("Host"));
        String url = defaultString(request.getRequestURI().substring(request.getContextPath().length()));
        String language = this.getLanguageFromURL(url);
        if (!Objects.isNull(language)) {
            request.setAttribute(LANGUAGE_DATA, language);
        }

        String newUrl = StringUtils.removeStart(url, '/' + language);
        RequestDispatcher dispatcher = request.getRequestDispatcher(newUrl);
        dispatcher.forward(request, response);
    }

    private String getLanguageFromURL(String url) {
        String[] variables = url.split("/");

        String languageName = "";
        if (variables.length > 0) {
            languageName = variables[1];
        }
        Language language = map(languageName);
        if (!Objects.isNull(language)) {
            return language.getIsoCode();
        }
        return null;
    }
}
