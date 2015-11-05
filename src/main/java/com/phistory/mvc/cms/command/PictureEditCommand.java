package com.phistory.mvc.cms.command;

import org.springframework.web.multipart.MultipartFile;

import com.tcp.data.model.Picture;

/**
 * Command class to edit a Picture
 *
 * @author Gonzalo
 */
public class PictureEditCommand
{
	private Picture picture;
	private MultipartFile pictureFile;

	public PictureEditCommand() {		
	}	

	public PictureEditCommand(Picture picture, MultipartFile pictureFile) {
		this.picture = picture;
		this.pictureFile = pictureFile;
	}
	
	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public MultipartFile getPictureFile() {
		return pictureFile;
	}

	public void setPictureFile(MultipartFile pictureFile) {
		this.pictureFile = pictureFile;
	}	
}
