package com.phistory.mvc.cms.springframework.view;

import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import com.phistory.data.dao.sql.impl.SQLEngineDAO;
import com.phistory.data.dao.sql.impl.SQLManufacturerDAO;
import com.phistory.data.model.Language;
import com.phistory.data.model.brake.BrakeDiscMaterial;
import com.phistory.data.model.car.*;
import com.phistory.data.model.engine.EngineCylinderDisposition;
import com.phistory.data.model.engine.EngineType;
import com.phistory.data.model.picture.Picture;
import com.phistory.data.model.transmission.DriveWheelType;
import com.phistory.data.model.transmission.TransmissionType;
import com.phistory.data.model.tyre.TyreManufacturer;
import com.phistory.data.model.tyre.TyreType;
import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.command.CarMaterial;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.phistory.mvc.controller.BaseControllerData.ENGINE;
import static com.phistory.mvc.controller.BaseControllerData.PICTURES;

/**
 * Fills a Spring Framework Model with car edit related information
 *
 * @author gonzalo
 *
 */
@Component
public class CarEditModelFiller implements ModelFiller
{
    private SQLManufacturerDAO sqlManufacturerDAO;
    private SQLEngineDAO       sqlEngineDAO;
    private InMemoryPictureDAO inMemoryPictureDAO;

	@Inject
	public CarEditModelFiller(SQLManufacturerDAO sqlManufacturerDAO,
							  SQLEngineDAO sqlEngineDAO,
							  InMemoryPictureDAO inMemoryPictureDAO) {
		this.sqlManufacturerDAO = sqlManufacturerDAO;
		this.sqlEngineDAO = sqlEngineDAO;
		this.inMemoryPictureDAO = inMemoryPictureDAO;
	}

	@Override
	public void fillModel(Model model)
	{
		model.addAttribute("manufacturers", 				this.sqlManufacturerDAO.getAll());
    	model.addAttribute("engineLayouts", 				CarEngineLayout.values());
    	model.addAttribute("engineDispositions", 			CarEngineDisposition.values());
    	model.addAttribute("engines", 						this.sqlEngineDAO.getAll());
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
    	model.addAttribute("productionTypes", 				CarProductionType.values());
    	model.addAttribute("tyreManufacturers", 			TyreManufacturer.values());
    	model.addAttribute("tyreTypes", 					TyreType.values());
	}

	/**
	 * Fill the model with information
	 *
	 * @param model
	 * @param command
	 */
	public void fillCarEditModel(Model model, CarFormEditCommand command) {
		List<Picture> pictures = new ArrayList<>();
		Long carId = command.getCarForm().getId();

		if (carId != null) {
			pictures = this.inMemoryPictureDAO.getByCarId(carId);
		}

		model.addAttribute(PICTURES, pictures);
		this.fillModel(model);
	}
}