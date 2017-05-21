package com.phistory.mvc.cms.form.factory.impl;

import com.phistory.mvc.cms.form.CarInternetContentFormAdapter;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.data.model.car.CarInternetContent;

/**
 * {@link CarInternetContent} implementation of an {@link EntityFormFactory}
 *
 * @author gonzalo
 */
@Slf4j
@Component
public class CarInternetContentFormFactory implements EntityFormFactory<CarInternetContent, CarInternetContentFormAdapter> {

    @Override
    public CarInternetContentFormAdapter buildFormFromEntity(CarInternetContent carInternetContent) {
        try {
            CarInternetContentForm carInternetContentForm
                    = new CarInternetContentForm(carInternetContent.getId(),
                                                 carInternetContent.getLink(),
                                                 carInternetContent.getType(),
                                                 carInternetContent.getAddedDate(),
                                                 carInternetContent.getContentLanguage(),
                                                 carInternetContent.getCar());

            return new CarInternetContentFormAdapter(carInternetContentForm);
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new CarInternetContentFormAdapter(new CarInternetContentForm());
    }

    @Override
    public CarInternetContent buildEntityFromForm(CarInternetContentFormAdapter carInternetContentFormAdapter) {
        try {
            CarInternetContentForm carInternetContentForm = carInternetContentFormAdapter.adapt();
            return new CarInternetContent(carInternetContentForm.getId(),
                                          carInternetContentForm.getLink(),
                                          carInternetContentForm.getType(),
                                          carInternetContentForm.getAddedDate(),
                                          carInternetContentForm.getContentLanguage(),
                                          carInternetContentForm.getCar());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new CarInternetContent();
    }
}
