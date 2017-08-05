package com.hhistory.mvc.cms.form;

import com.hhistory.data.model.tyre.TyreManufacturer;
import com.hhistory.data.model.tyre.TyreType;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.hhistory.data.model.car.Car;
import com.hhistory.data.model.tyre.Tyre;

/**
 * TyreSet form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class TyreSetEditForm implements EditForm {

    private Long             id;
    private TyreManufacturer manufacturer;
    private TyreType         type;
    private String           model;
    private Tyre             frontTyre;
    private Tyre             rearTyre;
    private Car              car;

    public TyreSetEditForm() {
        this.frontTyre = new Tyre();
        this.rearTyre = new Tyre();
    }
}
