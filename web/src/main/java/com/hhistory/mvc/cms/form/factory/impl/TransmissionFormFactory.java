package com.hhistory.mvc.cms.form.factory.impl;

import com.hhistory.mvc.cms.form.TransmissionEditForm;
import com.hhistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.hhistory.data.model.transmission.Transmission;

/**
 * Creates new Transmissions out of the data contained in TransmissionForms and vice versa
 *
 * @author Gonzalo
 */
@Slf4j
@Component
public class TransmissionFormFactory implements EntityFormFactory<Transmission, TransmissionEditForm> {

    @Override
    public TransmissionEditForm buildFormFromEntity(Transmission transmission) {
        try {
            return new TransmissionEditForm(transmission.getId(),
                                            transmission.getType(),
                                            transmission.getNumOfGears(),
                                            transmission.getCar());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new TransmissionEditForm();
    }

    @Override
    public Transmission buildEntityFromForm(TransmissionEditForm transmissionEditForm) {
        try {
            return new Transmission(transmissionEditForm.getId(),
                                    transmissionEditForm.getType(),
                                    transmissionEditForm.getNumOfGears(),
                                    transmissionEditForm.getCar());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new Transmission();
    }
}
