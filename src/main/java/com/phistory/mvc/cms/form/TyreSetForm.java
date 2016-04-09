package com.phistory.mvc.cms.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.tcp.data.model.car.Car;
import com.tcp.data.model.tyre.Tyre;

/**
 * TyreSet form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class TyreSetForm
{
    private Long id;
    private Tyre frontTyre;
    private Tyre backTyre;
    private Car car;
    
    public TyreSetForm()
    {
        this.frontTyre = new Tyre();
        this.backTyre = new Tyre();
    }
}
