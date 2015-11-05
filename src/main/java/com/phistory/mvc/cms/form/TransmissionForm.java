package com.phistory.mvc.cms.form;

import com.tcp.data.model.car.Car;
import com.tcp.data.model.transmission.TransmissionType;

/**
 * Transmission form
 *
 * @author Gonzalo
 */
public class TransmissionForm
{
    private Long id;
    private TransmissionType type;
    private Integer numOfGears;
    private Car car;

    public TransmissionForm() {
    }

    public TransmissionForm(Long id, TransmissionType type, Integer numOfGears, Car car) {
        this.id = id;
        this.type = type;
        this.numOfGears = numOfGears;
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumOfGears() {
        return numOfGears;
    }

    public void setNumOfGears(Integer numOfGears) {
        this.numOfGears = numOfGears;
    }

    public TransmissionType getType() {
        return type;
    }

    public void setType(TransmissionType type) {
        this.type = type;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
