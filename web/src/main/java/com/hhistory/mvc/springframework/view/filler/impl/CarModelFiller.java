package com.hhistory.mvc.springframework.view.filler.impl;

import com.hhistory.mvc.springframework.view.filler.ModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import static com.hhistory.mvc.cms.form.factory.impl.CarFormFactory.CAR_MATERIAL_STRING_SEPARATOR;
import static com.hhistory.mvc.command.PictureLoadAction.LOAD_CAR_PICTURE;
import static com.hhistory.mvc.command.PictureLoadAction.LOAD_CAR_PREVIEW;
import static com.hhistory.mvc.controller.BaseControllerData.*;

/**
 * Fills a Spring Framework Model with car related information
 *
 * @author gonzalo
 */
@Component
public class CarModelFiller implements ModelFiller {

    @Override
    public Model fillModel(Model model) {
        model.addAttribute(CAR_ID, CAR_ID);
        model.addAttribute("loadCarPreviewAction", LOAD_CAR_PREVIEW.getName());
        model.addAttribute("loadCarPictureAction", LOAD_CAR_PICTURE.getName());
        model.addAttribute("unitsOfMeasureCookieName", UNITS_OF_MEASURE_COOKIE_NAME);
        model.addAttribute("unitsOfMeasureMetric", UNITS_OF_MEASURE_METRIC);
        model.addAttribute("unitsOfMeasureImperial", UNITS_OF_MEASURE_IMPERIAL);
        model.addAttribute("paginationURL", PAGINATION_URL);
        model.addAttribute("carMaterialsStringSeparator", CAR_MATERIAL_STRING_SEPARATOR);
        return model;
    }
}
