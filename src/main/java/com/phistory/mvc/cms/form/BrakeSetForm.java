package com.phistory.mvc.cms.form;

import com.tcp.data.model.brake.Brake;
import com.tcp.data.model.car.Car;

/**
 * Brake set form
 * 
 * @author Gonzalo
 */
public class BrakeSetForm
{
    private Long id;
    private Brake frontBrake;
    private Brake backBrake;
    private Car car;

    public BrakeSetForm()
    {
        frontBrake = new Brake();
        backBrake = new Brake();
    }

    public BrakeSetForm(Long id, Brake frontBrake, Brake backBrake, Car car)
    {
        this.id = id;
        this.frontBrake = frontBrake;
        this.backBrake = backBrake;
        this.car = car;
    }

    public Brake getBackBrake() {
        return backBrake;
    }

    public void setBackBrake(Brake backBrake) {
        this.backBrake = backBrake;
    }

    public Brake getFrontBrake() {
        return frontBrake;
    }

    public void setFrontBrake(Brake frontBrake) {
        this.frontBrake = frontBrake;
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
