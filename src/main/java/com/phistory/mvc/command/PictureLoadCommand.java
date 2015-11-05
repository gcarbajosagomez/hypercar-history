package com.phistory.mvc.command;

import java.io.Serializable;

/**
 * Command class to load pictures
 * 
 * @author Gonzalo
 *
 */
public class PictureLoadCommand implements Serializable
{	
	private static final long serialVersionUID = -5058848031245544704L;
	
	private String action;
	private Long pictureId;
	private Long carId;

	public PictureLoadCommand() {
	}

	public PictureLoadCommand(String action, Long pictureId, Long carId) {
		this.action = action;
		this.pictureId = pictureId;
		this.carId = carId;
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
}
