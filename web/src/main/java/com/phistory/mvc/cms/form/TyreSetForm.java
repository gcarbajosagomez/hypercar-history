package com.phistory.mvc.cms.form;

import com.phistory.data.model.tyre.TyreManufacturer;
import com.phistory.data.model.tyre.TyreType;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.phistory.data.model.car.Car;
import com.phistory.data.model.tyre.Tyre;

/**
 * TyreSet form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class TyreSetForm {
    private Long id;
    private TyreManufacturer manufacturer;
    private TyreType type;
    private String model;
    private Tyre frontTyre;
    private Tyre rearTyre;
    private Car car;

    public TyreSetForm() {
        this.frontTyre = new Tyre();
        this.rearTyre = new Tyre();
    }
}
