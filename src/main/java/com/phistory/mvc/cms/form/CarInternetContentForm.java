package com.phistory.mvc.cms.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.joda.time.DateTime;

import com.tcp.data.model.Language;
import com.tcp.data.model.car.Car;
import com.tcp.data.model.car.CarInternetContent;
import com.tcp.data.model.car.CarInternetContentType;

/**
 * {@link CarInternetContent} edit form
 * 
 * @author gonzalo
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarInternetContentForm
{
	private Long id;
	private String link;
	private CarInternetContentType type;
	private DateTime addedDate;	
	private Language contentLanguage;
	private Car car;
}
