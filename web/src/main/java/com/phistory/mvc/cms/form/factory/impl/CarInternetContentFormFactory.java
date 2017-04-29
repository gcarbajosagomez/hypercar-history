package com.phistory.mvc.cms.form.factory.impl;

import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.data.model.car.CarInternetContent;

/**
 * {@link CarInternetContent} implementation of an {@link EntityFormFactory}
 * 
 * @author gonzalo
 *
 */
@Slf4j
@Component
public class CarInternetContentFormFactory implements EntityFormFactory<CarInternetContent, CarInternetContentForm>
{	
	/** 
     * Create a new {@link CarInternetContentForm} out of the data contained in the supplied {@link CarInternetContent}
     */
	@Override
	public CarInternetContentForm createFormFromEntity(CarInternetContent carInternetContent)
	{
		try
		{
			CarInternetContentForm carInternetContentForm = new CarInternetContentForm(carInternetContent.getId(),
																					   carInternetContent.getLink(),
																					   carInternetContent.getType(),
																					   carInternetContent.getAddedDate(),
																					   carInternetContent.getContentLanguage(),
																					   carInternetContent.getCar());
			return carInternetContentForm;
		}
        catch (Exception e)
        {
            log.error(e.toString(), e);
        }
		
		return new CarInternetContentForm();
	}

	/** 
     * Create a new {@link CarInternetContent} out of the data contained in the supplied {@link CarInternetContentForm}
     */
	@Override
	public CarInternetContent createEntityFromForm(CarInternetContentForm carInternetContentForm)
	{
		try
		{
			return new CarInternetContent(carInternetContentForm.getId(),
										  carInternetContentForm.getLink(),
										  carInternetContentForm.getType(),
										  carInternetContentForm.getAddedDate(),
										  carInternetContentForm.getContentLanguage(),
										  carInternetContentForm.getCar());
		} 
		catch(Exception e)
		{
			log.error(e.toString(), e);
		}
		
		return new CarInternetContent();
	}
}
