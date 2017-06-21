package com.phistory.mvc.cms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.cms.controller.CMSBaseController.LOGIN_URL;

@Slf4j
@Controller
@RequestMapping({CMS_CONTEXT, CMS_CONTEXT + LOGIN_URL})
public class CMSLoginController extends CMSBaseController {

    @RequestMapping
    public ModelAndView handleLogin(HttpServletResponse request,
                                    Model model,
                                    @RequestParam(value = LOGIN_SUCCESS, required = false) String success,
                                    @RequestParam(value = LOGIN_ERROR, required = false) String error,
                                    @RequestParam(value = LOGOUT, required = false) String logout) {
        try {
            if (success != null) {
                String manufacturerShortName = super.getManufacturerService().getFromModel(model).getShortName();
                request.sendRedirect("/" + manufacturerShortName + "/" + CMS_CONTEXT + CARS_URL);
            } else if (error != null) {
                model.addAttribute(LOGGED_IN, false);
                model.addAttribute(LOGIN_ERROR, super.getMessageSource()
                                                     .getMessage("cms.login.invalidCredentials",
                                                                 new Object[] {},
                                                                 LocaleContextHolder.getLocale()));
            } else if (logout != null) {
                model.addAttribute(LOGGED_IN, false);
                model.addAttribute(LOGOUT, super.getMessageSource()
                                                .getMessage("cms.logout.successful",
                                                            new Object[] {},
                                                            LocaleContextHolder.getLocale()));
            }
            //not logged in yet
            else {
                model.addAttribute(LOGGED_IN, false);
            }

            return new ModelAndView(CMS_CONTEXT + LOGIN_URL);
        } catch (Exception e) {
            model.addAttribute(LOGGED_IN, false);
            log.error(e.toString(), e);

            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }
}
