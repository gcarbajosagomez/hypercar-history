package com.phistory.data.command;

import com.phistory.data.model.picture.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureDataCommand
{
	MultipartFile multipartFile;
	Picture picture;
}
