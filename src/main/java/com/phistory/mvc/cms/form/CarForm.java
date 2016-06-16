package com.phistory.mvc.cms.form;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.phistory.mvc.cms.command.PictureEditCommand;
import com.tcp.data.model.DriveWheelType;
import com.tcp.data.model.Manufacturer;
import com.tcp.data.model.car.CarBodyShape;
import com.tcp.data.model.car.CarSeatsConfig;
import com.tcp.data.model.car.EngineLayout;

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
    @NotNull(message = "The field must not be blank.")
    private CarBodyShape bodyShape;  
    @NotNull(message = "The field must not be blank.")
    private CarSeatsConfig seatsConfig;
    private Integer topSpeed;
    private Float acceleration;
    private Float fuelConsuption;
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
    private List<MultipartFile> pictureFiles;
    @NotNull(message = "The field above must not be blank.")
    private DriveWheelType driveWheel;
    @NotNull(message = "The field must not be blank.")
    private Boolean roadLegal;

    public CarForm()
    {
        this.engineForm = 		new EngineForm();
        this.brakeSetForm = 	new BrakeSetForm();
        this.transmissionForm = new TransmissionForm();
        this.tyreSetForm = 		new TyreSetForm();
        this.manufacturer = 	new Manufacturer();
    }
}
