package com.phistory.mvc.command;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command class to load pictures
 * 
 * @author Gonzalo
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureLoadCommand implements Serializable
{	
	private static final long serialVersionUID = -5058848031245544704L;
	
	private String action;
	private Long pictureId;
	private Long manufacturerId;
	private Long carId;
}
