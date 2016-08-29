package com.phistory.mvc.command;

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
public class PictureLoadCommand
{	
	private String action;
	private Long pictureId;
	private Long manufacturerId;
	private Long carId;
}
