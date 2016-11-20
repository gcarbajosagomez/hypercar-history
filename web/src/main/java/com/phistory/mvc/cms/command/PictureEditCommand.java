package com.phistory.mvc.cms.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

import com.phistory.data.model.Picture;

/**
 * Command class to edit a {@link Picture}
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureEditCommand
{
	private Picture picture;
	private MultipartFile pictureFile;
}
