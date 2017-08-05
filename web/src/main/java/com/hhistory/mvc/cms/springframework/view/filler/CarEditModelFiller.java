package com.hhistory.mvc.cms.springframework.view.filler;

import com.hhistory.data.dao.inmemory.InMemoryPictureDAO;
import com.hhistory.data.dao.sql.SqlEngineRepository;
import com.hhistory.data.dao.sql.SqlManufacturerRepository;
import com.hhistory.data.model.Language;
import com.hhistory.data.model.brake.BrakeDiscMaterial;
import com.hhistory.data.model.car.*;
import com.hhistory.data.model.engine.EngineCylinderDisposition;
import com.hhistory.data.model.engine.EngineType;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.data.model.transmission.DriveWheelType;
import com.hhistory.data.model.transmission.TransmissionType;
import com.hhistory.data.model.tyre.TyreManufacturer;
import com.hhistory.data.model.tyre.TyreType;
import com.hhistory.mvc.cms.command.EditFormCommand;
import com.hhistory.mvc.cms.command.CarMaterial;
import com.hhistory.mvc.springframework.view.filler.ModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.hhistory.mvc.controller.BaseControllerData.ENGINE;
import static com.hhistory.mvc.controller.BaseControllerData.PICTURES;

/**
 * Fills a Spring Framework Model with car edit related information
 *
 * @author gonzalo
 */
@Component
public class CarEditModelFiller implements ModelFiller {

    private SqlManufacturerRepository sqlManufacturerRepository;
    private SqlEngineRepository       sqlEngineRepository;
    private InMemoryPictureDAO        inMemoryPictureDAO;

    @Inject
    public CarEditModelFiller(SqlManufacturerRepository sqlManufacturerRepository,
                              SqlEngineRepository sqlEngineRepository,
                              InMemoryPictureDAO inMemoryPictureDAO) {
        this.sqlManufacturerRepository = sqlManufacturerRepository;
        this.sqlEngineRepository = sqlEngineRepository;
        this.inMemoryPictureDAO = inMemoryPictureDAO;
    }

    @Override
    public Model fillModel(Model model) {
        model.addAttribute("manufacturers", this.sqlManufacturerRepository.findAll());
        model.addAttribute("engineLayouts", CarEngineLayout.values());
        model.addAttribute("engineDispositions", CarEngineDisposition.values());
        model.addAttribute("engines", this.sqlEngineRepository.findAll());
        model.addAttribute(ENGINE, ENGINE);
        model.addAttribute("carMaterials", CarMaterial.values());
        model.addAttribute("bodyShapes", CarBodyShape.values());
        model.addAttribute("seatsConfigs", CarSeatsConfig.values());
        model.addAttribute("brakeDiscMaterials", BrakeDiscMaterial.values());
        model.addAttribute("transmissionTypes", TransmissionType.values());
        model.addAttribute("engineTypes", EngineType.values());
        model.addAttribute("engineCylinderDispositions", EngineCylinderDisposition.values());
        model.addAttribute("driveWheelTypes", DriveWheelType.values());
        model.addAttribute("carInternetContentTypes", CarInternetContentType.values());
        model.addAttribute("carInternetContentLanguages", Language.values());
        model.addAttribute("productionTypes", CarProductionType.values());
        model.addAttribute("tyreManufacturers", TyreManufacturer.values());
        model.addAttribute("tyreTypes", TyreType.values());
        return model;
    }

    /**
     * Fill the model with information
     *
     * @param model
     * @param command
     */
    public Model fillCarEditModel(Model model, EditFormCommand command) {
        List<Picture> pictures = new ArrayList<>();
        Long carId = command.getEditForm().getId();

        if (carId != null) {
            pictures = this.inMemoryPictureDAO.getByCarId(carId);
        }

        model.addAttribute(PICTURES, pictures);
        model = this.fillModel(model);
        return model;
    }
}