package com.hhistory.mvc.cms.form;

import com.hhistory.data.model.Manufacturer;
import com.hhistory.data.model.car.*;
import com.hhistory.data.model.transmission.DriveWheelType;
import com.hhistory.mvc.cms.command.CarMaterial;
import com.hhistory.mvc.cms.command.PictureEditCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Car form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class CarEditForm implements EditForm {

    private Long id;

    @NotNull(message = "The field must not be blank.")
    private Boolean visible;

    @Valid
    @NotNull(message = "The field must not be blank.")
    private Manufacturer manufacturer;

    @NotEmpty(message = "NotEmpty.carForm.model")
    private String model;

    @NotNull(message = "The field must not be blank.")
    private CarEngineLayout engineLayout;

    @NotNull(message = "The field must not be blank.")
    private CarEngineDisposition engineDisposition;

    @Valid
    @NotNull(message = "The field must not be blank.")
    private EditForm engineEditForm;

    private List<CarMaterial> chassisMaterials;
    private List<CarMaterial> bodyMaterials;

    @NotNull(message = "The field must not be blank.")
    private CarBodyShape bodyShape;

    @NotNull(message = "The field must not be blank.")
    private CarSeatsConfig seatsConfig;

    private Integer topSpeed;
    private Float   acceleration;
    private Float   fuelConsumption;

    @NotNull(message = "The field must not be blank.")
    private CarProductionType productionType;

    @NotNull
    private Calendar productionStartDate;
    private Calendar productionEndDate;

    @Digits(integer = 8, fraction = 4, message = "The numeric field has a wrong format")
    private Long weight;

    private Long length;
    private Long width;
    private Long height;
    private Long wheelbase;

    @Valid
    @NotNull(message = "The field must not be blank.")
    private EditForm brakeSetEditForm;

    @Valid
    @NotNull(message = "The field must not be blank.")
    private EditForm transmissionEditForm;

    private Long fuelTankCapacity;

    @Valid
    @NotNull(message = "The field must not be blank.")
    private EditForm tyreSetEditForm;

    private List<PictureEditCommand> pictureFileEditCommands;

    @NotNull(message = "The field above must not be blank.")
    private DriveWheelType driveWheel;

    @NotNull(message = "The field must not be blank.")
    private Boolean roadLegal;

    private String descriptionES;
    private String descriptionEN;

    public CarEditForm() {
        this.visible = true;
        this.manufacturer = new Manufacturer();
        this.engineEditForm = new EngineEditForm();
        this.brakeSetEditForm = new BrakeSetEditForm();
        this.transmissionEditForm = new TransmissionEditForm();
        this.tyreSetEditForm = new TyreSetEditForm();
        this.pictureFileEditCommands = new ArrayList<>();
        this.roadLegal = true;
    }
}
