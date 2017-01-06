package com.phistory.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.phistory.mvc.controller.BaseControllerData.MANUFACTURER_HISTORY_URL;
import static com.phistory.mvc.manufacturer.Manufacturer.PAGANI;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

/**
 * Created by Gonzalo Carbajosa on 6/01/17.
 */
@Slf4j
@Controller
@RequestMapping(value = {MANUFACTURER_HISTORY_URL},
        method = {GET, HEAD})
public class ManufacturerHistoryController extends BaseController {

    @RequestMapping
    public ModelAndView handleDefault(Model model) {
        try {
            model.addAttribute(MANUFACTURER, super.getInMemoryManufacturerDAO().getByName(PAGANI.getName()));
            return new ModelAndView(MANUFACTURER_HISTORY_VIEW_NAME);
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new ModelAndView(ERROR_VIEW_NAME);
        }
    }
}
