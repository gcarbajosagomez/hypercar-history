package com.phistory.mvc.cms.springframework.view;

import com.phistory.mvc.springframework.view.filler.ModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.phistory.mvc.cms.command.EntityManagementQueryType.RELOAD_CARS;
import static com.phistory.mvc.cms.controller.CMSBaseController.EDIT_URL;

/**
 * Fills a Spring Framework {@link Model} with the basic information for the CMS web context
 *
 * Created by Gonzalo Carbajosa on 3/12/16.
 */
@Component(value = "cmsBaseModelFiller")
public class BaseModelFiller implements ModelFiller {

    @Override
    public void fillModel(Model model) {
        model.addAttribute("editURL",                           EDIT_URL);
        model.addAttribute("reloadCarsEntityManagementAction",  RELOAD_CARS);
    }
}
