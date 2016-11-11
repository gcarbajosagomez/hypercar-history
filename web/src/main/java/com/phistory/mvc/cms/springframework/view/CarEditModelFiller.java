package com.phistory.mvc.cms.springframework.view;

import static com.phistory.mvc.controller.BaseControllerData.*;
import static com.phistory.mvc.controller.BaseControllerData.ENGINE;

import java.util.ArrayList;

import javax.inject.Inject;

import com.phistory.mvc.cms.command.CarMaterial;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import com.phistory.data.dao.sql.impl.SQLEngineDAO;
import com.phistory.data.dao.sql.impl.SQLManufacturerDAO;
import com.phistory.data.dao.sql.impl.SQLPictureDAO;
import com.phistory.data.model.DriveWheelType;
import com.phistory.data.model.Language;
import com.phistory.data.model.Picture;
import com.phistory.data.model.brake.BrakeDiscMaterial;
import com.phistory.data.model.car.CarBodyShape;
import com.phistory.data.model.car.CarInternetContentType;
import com.phistory.data.model.car.CarSeatsConfig;
import com.phistory.data.model.car.EngineLayout;
import com.phistory.data.model.car.ProductionType;
import com.phistory.data.model.engine.EngineCylinderDisposition;
import com.phistory.data.model.engine.EngineType;
import com.phistory.data.model.transmission.TransmissionType;

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
	private SQLManufacturerDAO manufacturerDAO;
	@Inject
	private SQLEngineDAO engineDAO;
	@Inject
	private SQLPictureDAO SQLPictureDAO;
	
	@Override
	public void fillModel(Model model) 
	{
		model.addAttribute("manufacturers", 				this.manufacturerDAO.getAll());
    	model.addAttribute("engineLayouts", 				EngineLayout.values());
    	model.addAttribute("engines", 						this.engineDAO.getAll());
    	model.addAttribute(ENGINE, 							ENGINE);
    	model.addAttribute("carMaterials", 					CarMaterial.values());
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
		model.addAttribute(PICTURE_IDS, command.getCarForm().getId() != null ? this.SQLPictureDAO.getIdsByCarId(command.getCarForm().getId()) : new ArrayList<Picture>());
		this.fillModel(model);
	}
}