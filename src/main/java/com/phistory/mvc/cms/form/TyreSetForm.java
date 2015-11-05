package com.phistory.mvc.cms.form;

import com.tcp.data.model.car.Car;
import com.tcp.data.model.tyre.Tyre;

/**
 * TyreSet form
 *
 * @author Gonzalo
 */
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

    public TyreSetForm(Long id, Tyre frontTyre, Tyre backTyre, Car car)
    {
        this.id = id;
        this.frontTyre = frontTyre;
        this.backTyre = backTyre;
        this.car = car;
    }

    public Tyre getBackTyre() {
        return backTyre;
    }

    public void setBackTyre(Tyre backTyre) {
        this.backTyre = backTyre;
    }

    public Tyre getFrontTyre() {
        return frontTyre;
    }

    public void setFrontTyre(Tyre frontTyre) {
        this.frontTyre = frontTyre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
