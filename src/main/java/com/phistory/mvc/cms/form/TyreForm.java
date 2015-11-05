package com.phistory.mvc.cms.form;

import com.tcp.data.model.tyre.CarTyreTrain;

/**
 * Tyre form
 *
 * @author Gonzalo
 */
public class TyreForm
{
    private Long id;
    private Long width;
    private Long profile;
    private Long rimDiameter;
    private CarTyreTrain train;

    public TyreForm() {
    }

    public TyreForm(Long id, Long width, Long profile, Long rimDiameter, CarTyreTrain train)
    {
        this.id = id;
        this.width = width;
        this.profile = profile;
        this.rimDiameter = rimDiameter;
        this.train = train;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfile() {
        return profile;
    }

    public void setProfile(Long profile) {
        this.profile = profile;
    }

    public Long getRimDiameter() {
        return rimDiameter;
    }

    public void setRimDiameter(Long rimDiameter) {
        this.rimDiameter = rimDiameter;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public CarTyreTrain getTrain() {
        return train;
    }

    public void setTrain(CarTyreTrain train) {
        this.train = train;
    }
}
