package com.hhistory.mvc.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gonzalo Carbajosa on 21/02/17.
 */
public interface URILoggingService {

    /**
     * Log the supplied {@code request}'s URI
     *
     * @param request
     * @return the logged URI
     */
    String logURI(HttpServletRequest request);
}
