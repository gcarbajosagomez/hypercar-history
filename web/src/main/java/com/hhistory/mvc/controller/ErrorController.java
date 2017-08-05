package com.hhistory.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.hhistory.mvc.controller.BaseControllerData.ERROR_URL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

/**
 * Created by Gonzalo Carbajosa on 31/12/16.
 */
@Controller
@RequestMapping(value = ERROR_URL,
                method = {GET, HEAD})
public class ErrorController extends BaseController {

    @RequestMapping
    public ModelAndView handleDefault() {
        return new ModelAndView(ERROR_VIEW_NAME);
    }
}
