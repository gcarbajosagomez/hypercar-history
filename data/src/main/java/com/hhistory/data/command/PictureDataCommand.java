package com.hhistory.data.command;

import com.hhistory.data.model.picture.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

/**
 * Command class to save and edit {@link Picture}s
 *
 * Created by Gonzalo Carbajosa on 29/11/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureDataCommand
{
	MultipartFile multipartFile;
	Picture picture;
}
