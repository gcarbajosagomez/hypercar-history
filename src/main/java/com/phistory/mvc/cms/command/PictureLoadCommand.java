package com.phistory.mvc.cms.command;

import java.io.Serializable;

/**
 * Command class to load a Picture
 *
 * @author Gonzalo
 */
public class PictureLoadCommand implements Serializable
{
    private static final long serialVersionUID = 1239015819094069184L;
	//
	private String action;
    private Long pictureId;
    private Long carId;
    private Long manufacturerId;

    public PictureLoadCommand() {
    }

    public PictureLoadCommand(String action, Long pictureId, Long carId, Long manufacturerId)
    {
        this.action = (action != null) ? action : "";
        this.pictureId = pictureId;
        this.carId = carId;
        this.manufacturerId = manufacturerId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
}
