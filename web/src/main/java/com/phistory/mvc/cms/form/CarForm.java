package com.phistory.mvc.cms.form;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.phistory.mvc.cms.command.CarMaterial;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import com.phistory.mvc.cms.command.PictureEditCommand;
import com.phistory.data.model.transmission.DriveWheelType;
import com.phistory.data.model.Manufacturer;
import com.phistory.data.model.car.CarBodyShape;
import com.phistory.data.model.car.CarSeatsConfig;
import com.phistory.data.model.car.EngineLayout;
import com.phistory.data.model.car.ProductionType;

/**
 * Car form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class CarForm
{
    private Long id;

    @Valid
    @NotNull(message = "The field must not be blank.")
    private Manufacturer manufacturer;

    @NotEmpty(message = "NotEmpty.carForm.model")
    private String model;

    @NotNull(message = "The field must not be blank.")
    private EngineLayout engineLayout;

    @Valid
    @NotNull(message = "The field must not be blank.")
    private EngineForm engineForm;

    private List<CarMaterial> chassisMaterials;

    private List<CarMaterial> bodyMaterials;

    @NotNull(message = "The field must not be blank.")
    private CarBodyShape bodyShape;

    @NotNull(message = "The field must not be blank.")
    private CarSeatsConfig seatsConfig;

    private Integer topSpeed;

    private Float acceleration;

    private Float fuelConsumption;

    @NotNull(message = "The field must not be blank.")
    private ProductionType productionType;

    private Calendar productionStartDate;

    private Calendar productionEndDate;

    @Digits(integer=8, fraction=4, message="The numeric field has a wrong format")
    private Long weight;

    private Long length;

    private Long width;

    private Long height;

    @Valid
    @NotNull(message = "The field must not be blank.")
    private BrakeSetForm brakeSetForm;    

    @Valid
    @NotNull(message = "The field must not be blank.")
    private TransmissionForm transmissionForm;

    private Long fuelTankCapacity;

    @Valid
    @NotNull(message = "The field must not be blank.")
    private TyreSetForm tyreSetForm;

    private PictureEditCommand previewPictureEditCommand;

    private List<PictureEditCommand> pictureFileEditCommands;

    @NotNull(message = "The field above must not be blank.")
    private DriveWheelType driveWheel;

    @NotNull(message = "The field must not be blank.")
    private Boolean roadLegal = true;

    private String descriptionES;

    private String descriptionEN;

    public CarForm()
    {
        this.engineForm         = new EngineForm();
        this.brakeSetForm       = new BrakeSetForm();
        this.transmissionForm   = new TransmissionForm();
        this.tyreSetForm        = new TyreSetForm();
        this.manufacturer       = new Manufacturer();
    }
}
