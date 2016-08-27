package com.tcp.data.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

import com.tcp.data.model.Picture;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureDataCommand
{
	MultipartFile multipartFile;
	Picture picture;	
}
