package com.phistory.mvc.springframework.view.filler.impl;

import com.phistory.mvc.springframework.view.filler.ModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.phistory.mvc.controller.BaseControllerData.PICTURES_URL;

/**
 * Fills a Spring Framework Model with picture related information
 *
 * @author gonzalo
 */
@Component
public class PictureModelFiller implements ModelFiller {

    @Override
    public Model fillModel(Model model) {
        model.addAttribute("picturesURL", PICTURES_URL);
        return model;
    }
}
