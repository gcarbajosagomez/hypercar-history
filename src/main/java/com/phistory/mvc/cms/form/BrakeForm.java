package com.phistory.mvc.cms.form;

import com.tcp.data.model.brake.CarBrakeTrain;
import com.tcp.data.model.brake.Brake.BrakeDiscMaterial;

/**
 * Brake form
 *
 * @author Gonzalo
 */
public class BrakeForm
{
    private Long id;
    private Long discDiameter;
    private BrakeDiscMaterial discMaterial;
    private Long caliperNumOfPistons;
    private CarBrakeTrain train;

    public BrakeForm() {
    }

    public BrakeForm(Long id, Long discDiameter, BrakeDiscMaterial discMaterial, Long caliperNumOfPistons, CarBrakeTrain train) {
        this.id = id;
        this.discDiameter = discDiameter;
        this.discMaterial = discMaterial;
        this.caliperNumOfPistons = caliperNumOfPistons;
        this.train = train;
    }

    public Long getcaliperNumOfPistons() {
        return caliperNumOfPistons;
    }

    public void setcaliperNumOfPistons(Long caliperNumOfPistons) {
        this.caliperNumOfPistons = caliperNumOfPistons;
    }

    public Long getDiscDiameter() {
        return discDiameter;
    }

    public void setDiscDiameter(Long discDiameter) {
        this.discDiameter = discDiameter;
    }

    public BrakeDiscMaterial getDiscMaterial() {
        return discMaterial;
    }

    public void setDiscMaterial(BrakeDiscMaterial discMaterial) {
        this.discMaterial = discMaterial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarBrakeTrain getTrain() {
        return train;
    }

    public void setTrain(CarBrakeTrain train) {
        this.train = train;
    }
}
