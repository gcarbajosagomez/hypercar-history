package com.phistory.mvc.cms.form.factory.impl;

import com.phistory.mvc.cms.form.TyreSetEditForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.data.model.tyre.TyreSet;

/**
 * Creates new TyreSets out of the data contained in TyreSetForms and vice versa
 *
 * @author Gonzalo
 */
@Slf4j
@Component
public class TyreSetFormFactory implements EntityFormFactory<TyreSet, TyreSetEditForm> {

    @Override
    public TyreSetEditForm buildFormFromEntity(TyreSet tyreSet) {
        try {
            return new TyreSetEditForm(tyreSet.getId(),
                                       tyreSet.getManufacturer(),
                                       tyreSet.getType(),
                                       tyreSet.getModel(),
                                       tyreSet.getFrontTyre(),
                                       tyreSet.getRearTyre(),
                                       tyreSet.getCar());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new TyreSetEditForm();
    }

    @Override
    public TyreSet buildEntityFromForm(TyreSetEditForm tyreSetEditForm) {
        try {
            return new TyreSet(tyreSetEditForm.getId(),
                               tyreSetEditForm.getManufacturer(),
                               tyreSetEditForm.getType(),
                               tyreSetEditForm.getModel(),
                               tyreSetEditForm.getFrontTyre(),
                               tyreSetEditForm.getRearTyre(),
                               tyreSetEditForm.getCar());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new TyreSet();
    }
}
