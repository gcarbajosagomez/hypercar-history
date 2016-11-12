package com.phistory.mvc.controller;

import static com.phistory.mvc.controller.BaseControllerData.COOKIES_POLICY_URL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to handle CookiesPolicy URLs
 *
 * @author Gonzalo
 */
@Slf4j
@Controller
@RequestMapping(value = COOKIES_POLICY_URL,
                method = {GET, HEAD})
public class CookiesPolicyController extends BaseController {

    @RequestMapping
    public ModelAndView handleDefault() {
        try {
            return new ModelAndView();
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }
}
