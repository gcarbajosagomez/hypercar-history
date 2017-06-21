package com.phistory.mvc.service.impl;

import com.phistory.mvc.language.Language;
import com.phistory.mvc.service.URILoggingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.phistory.mvc.controller.BaseControllerData.LANGUAGE_DATA;

/**
 * Created by Gonzalo Carbajosa on 21/02/17.
 */
@Component
@Slf4j
public class URILoggingServiceImpl implements URILoggingService {

    @Override
    public String logURI(HttpServletRequest request) {
        String requestURI = this.extractRequestUriFromRequest(request);
        log.info("Handling " + request.getMethod() + " request to URI " + requestURI);
        return requestURI;
    }

    /**
     * Extract the requested URI from the {@link HttpServletRequest}
     *
     * @param request
     * @return A string containing the requested URI if everything went well, an empty String otherwise
     */
    private String extractRequestUriFromRequest(HttpServletRequest request) {
        StringBuilder requestedURI = new StringBuilder();

        Optional.ofNullable((Language) request.getAttribute(LANGUAGE_DATA))
                .ifPresent(language -> requestedURI.append("/" + language.getIsoCode()));

        requestedURI.append(request.getRequestURI());

        String queryString = request.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            requestedURI.append("?" + queryString);
        }

        return requestedURI.toString();
    }
}
