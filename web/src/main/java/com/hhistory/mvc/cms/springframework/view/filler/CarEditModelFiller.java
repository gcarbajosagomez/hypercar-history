package com.hhistory.mvc.cms.springframework.view.filler;

import com.hhistory.data.dao.PictureDAO;
import com.hhistory.data.dao.sql.SqlEngineRepository;
import com.hhistory.data.dao.sql.SqlManufacturerRepository;
import com.hhistory.data.model.Language;
import com.hhistory.data.model.brake.BrakeDiscMaterial;
import com.hhistory.data.model.brake.BrakeTrain;
import com.hhistory.data.model.brake.BrakeType;
import com.hhistory.data.model.car.*;
import com.hhistory.data.model.engine.EngineCylinderDisposition;
import com.hhistory.data.model.engine.EngineType;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.data.model.transmission.DriveWheelType;
import com.hhistory.data.model.transmission.TransmissionType;
import com.hhistory.data.model.tyre.TyreManufacturer;
import com.hhistory.data.model.tyre.TyreTrain;
import com.hhistory.data.model.tyre.TyreType;
import com.hhistory.mvc.cms.command.CarMaterial;
import com.hhistory.mvc.cms.command.EditFormCommand;
import com.hhistory.mvc.springframework.view.filler.ModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static com.hhistory.data.dao.sql.SqlPictureDAO.SQL_PICTURE_DAO;
import static com.hhistory.mvc.cms.controller.CMSBaseController.MANUFACTURER_ENTITIES;
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
    private PictureDAO                pictureDAO;

    @Inject
    public CarEditModelFiller(SqlManufacturerRepository sqlManufacturerRepository,
                              SqlEngineRepository sqlEngineRepository,
                              @Named(SQL_PICTURE_DAO) PictureDAO pictureDAO) {
        this.sqlManufacturerRepository = sqlManufacturerRepository;
        this.sqlEngineRepository = sqlEngineRepository;
        this.pictureDAO = pictureDAO;
    }

    @Override
    public Model fillModel(Model model) {
        model.addAttribute(MANUFACTURER_ENTITIES, this.sqlManufacturerRepository.findAll());
        model.addAttribute("engineLayouts", CarEngineLayout.values());
        model.addAttribute("engineDispositions", CarEngineDisposition.values());
        model.addAttribute("engines", this.sqlEngineRepository.findAll());
        model.addAttribute(ENGINE, ENGINE);
        model.addAttribute("carMaterials", CarMaterial.values());
        model.addAttribute("bodyShapes", CarBodyShape.values());
        model.addAttribute("seatsConfigs", CarSeatsConfig.values());
        model.addAttribute("brakeTypes", BrakeType.values());
        model.addAttribute("brakeTrains", BrakeTrain.values());
        model.addAttribute("brakeDiscMaterials", BrakeDiscMaterial.values());
        model.addAttribute("transmissionTypes", TransmissionType.values());
        model.addAttribute("engineTypes", EngineType.values());
        model.addAttribute("engineCylinderDispositions", EngineCylinderDisposition.values());
        model.addAttribute("driveWheelTypes", DriveWheelType.values());
        model.addAttribute("carInternetContentTypes", CarInternetContentType.values());
        model.addAttribute("carInternetContentLanguages", Language.values());
        model.addAttribute("productionTypes", CarProductionType.values());
        model.addAttribute("tyreTrains", TyreTrain.values());
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
            pictures = this.pictureDAO.getByCarId(carId);
        }

        model.addAttribute(PICTURES, pictures);
        model = this.fillModel(model);
        return model;
    }
}