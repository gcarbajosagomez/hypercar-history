package com.phistory.mvc.cms.springframework.view;

import static com.phistory.mvc.controller.BaseControllerData.*;
import static com.phistory.mvc.controller.BaseControllerData.ENGINE;

import java.util.ArrayList;

import javax.inject.Inject;

import com.phistory.mvc.controller.BaseControllerData;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.dao.impl.EngineDao;
import com.tcp.data.dao.impl.ManufacturerDao;
import com.tcp.data.dao.impl.PictureDao;
import com.tcp.data.model.DriveWheelType;
import com.tcp.data.model.Language;
import com.tcp.data.model.Picture;
import com.tcp.data.model.brake.BrakeDiscMaterial;
import com.tcp.data.model.car.CarBodyShape;
import com.tcp.data.model.car.CarInternetContentType;
import com.tcp.data.model.car.CarSeatsConfig;
import com.tcp.data.model.car.EngineLayout;
import com.tcp.data.model.car.ProductionType;
import com.tcp.data.model.engine.EngineCylinderDisposition;
import com.tcp.data.model.engine.EngineType;
import com.tcp.data.model.transmission.TransmissionType;

/**
 * Fills a Spring Framework Model with car edit related information
 * 
 * @author gonzalo
 *
 */
@Component
public class CarEditModelFiller implements ModelFiller
{
	@Inject
	private ManufacturerDao manufacturerDao;
	@Inject
	private EngineDao engineDao;
	@Inject
	private PictureDao pictureDao;
	
	@Override
	public void fillModel(Model model) 
	{
		model.addAttribute("manufacturers", 				this.manufacturerDao.getAll());
    	model.addAttribute("engineLayouts", 				EngineLayout.values());
    	model.addAttribute("engines", 						this.engineDao.getAll());
    	model.addAttribute(ENGINE, 							ENGINE);
    	model.addAttribute("bodyShapes", 					CarBodyShape.values());
    	model.addAttribute("seatsConfigs", 					CarSeatsConfig.values());
    	model.addAttribute("brakeDiscMaterials", 			BrakeDiscMaterial.values());
    	model.addAttribute("transmissionTypes", 			TransmissionType.values());
    	model.addAttribute("engineTypes", 					EngineType.values());
    	model.addAttribute("engineCylinderDispositions",	EngineCylinderDisposition.values());
    	model.addAttribute("driveWheelTypes", 				DriveWheelType.values()); 
    	model.addAttribute("carInternetContentTypes", 		CarInternetContentType.values()); 
    	model.addAttribute("carInternetContentLanguages", 	Language.values()); 
    	model.addAttribute("productionTypes", 				ProductionType.values()); 
	}
	
	/**
	 * Fill the model with information
	 * 
	 * @param model
	 * @param command
	 */
	public void fillCarEditModel(Model model, CarFormEditCommand command)
	{
		model.addAttribute(PICTURE_IDS, command.getCarForm().getId() != null ? this.pictureDao.getIdsByCarId(command.getCarForm().getId()) : new ArrayList<Picture>());
		this.fillModel(model);
	}
}