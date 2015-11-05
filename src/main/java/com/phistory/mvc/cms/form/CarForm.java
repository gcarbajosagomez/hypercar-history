package com.phistory.mvc.cms.form;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

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
public class CarForm
{
    private Long id;
    @Valid
    private Manufacturer manufacturer;
    @NotEmpty(message = "NotEmpty.carForm.model")
    private String model;
    @NotNull(message = "The field must not be blank.")
    private EngineLayout engineLayout;
    @Valid
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
    private BrakeSetForm brakeSetForm;    
    @Valid
    private TransmissionForm transmissionForm;
    private Long fuelTankCapacity;
    @Valid
    private TyreSetForm tyreSetForm;
    private PictureEditCommand previewPictureEditCommand;
    private List<MultipartFile> pictureFiles;
    @NotNull(message = "The field above must not be blank.")
    private DriveWheelType driveWheel;

    public CarForm()
    {
        this.engineForm = new EngineForm();
        this.brakeSetForm = new BrakeSetForm();
        this.transmissionForm = new TransmissionForm();
        this.tyreSetForm = new TyreSetForm();
        this.manufacturer = new Manufacturer();
    }

    public CarForm(Long id,
    			   Manufacturer manufacturer,
    			   String model,
    			   EngineLayout engineLayout,
    			   EngineForm engineForm,
    			   CarBodyShape bodyShape,
    			   CarSeatsConfig seatsConfig,
    			   Integer topSpeed,
    			   Float acceleration,
    			   Float fuelConsuption,
    			   Calendar productionStartDate,
    			   Calendar productionEndDate,
    			   Long weight,
    			   Long length,
    			   Long width,
    			   Long height,
    			   BrakeSetForm brakeSetForm,
    			   TransmissionForm transmissionForm,
    			   Long fuelTankCapacity,
    			   TyreSetForm tyreSetForm,
    			   PictureEditCommand previewPictureEditCommand,
    			   DriveWheelType driveWheel)
    {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.engineLayout = engineLayout;
        this.bodyShape = bodyShape;
        this.seatsConfig = seatsConfig;
        this.engineForm = engineForm;
        this.topSpeed = topSpeed;
        this.acceleration = acceleration;
        this.fuelConsuption = fuelConsuption;
        this.productionStartDate = productionStartDate;
        this.productionEndDate = productionEndDate;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.brakeSetForm = brakeSetForm;
        this.transmissionForm = transmissionForm;
        this.fuelTankCapacity = fuelTankCapacity;
        this.tyreSetForm = tyreSetForm;  
        this.previewPictureEditCommand = previewPictureEditCommand;
        this.driveWheel = driveWheel;
    }

    public Float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Float acceleration) {
        this.acceleration = acceleration;
    }

    public BrakeSetForm getBrakeSetForm() {
        return brakeSetForm;
    }

    public void setBrakeSetForm(BrakeSetForm brakeSetForm) {
        this.brakeSetForm = brakeSetForm;
    }

    public EngineForm getEngineForm() {
        return engineForm;
    }

    public void setEngineForm(EngineForm engineForm) {
        this.engineForm = engineForm;
    }

    public Float getFuelConsuption() {
        return fuelConsuption;
    }

    public void setFuelConsuption(Float fuelConsuption) {
        this.fuelConsuption = fuelConsuption;
    }

    public Long getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(Long fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public PictureEditCommand getPreviewPictureEditCommand() {
		return previewPictureEditCommand;
	}

	public void setPreviewPictureEditCommand(PictureEditCommand previewPictureEditCommand) {
		this.previewPictureEditCommand = previewPictureEditCommand;
	}

	public List<MultipartFile> getPictureFiles() {
        return pictureFiles;
    }

    public void setPictureFiles(List<MultipartFile> pictureFiles) {
        this.pictureFiles = pictureFiles;
    }

    public Calendar getProductionEndDate() {
        return productionEndDate;
    }

    public void setProductionEndDate(Calendar productionEndDate) {
        this.productionEndDate = productionEndDate;
    }

    public Calendar getProductionStartDate() {
        return productionStartDate;
    }

    public void setProductionStartDate(Calendar productionStartDate) {
        this.productionStartDate = productionStartDate;
    }

    public Integer getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(Integer topSpeed) {
        this.topSpeed = topSpeed;
    }

    public TransmissionForm getTransmissionForm() {
        return transmissionForm;
    }

    public void setTransmissionForm(TransmissionForm transmissionForm) {
        this.transmissionForm = transmissionForm;
    }

    public TyreSetForm getTyreSetForm() {
        return tyreSetForm;
    }

    public void setTyreSetForm(TyreSetForm tyreSetForm) {
        this.tyreSetForm = tyreSetForm;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public DriveWheelType getDriveWheel() {
        return driveWheel;
    }

    public void setDriveWheel(DriveWheelType driveWheel) {
        this.driveWheel = driveWheel;
    }

	public EngineLayout getEngineLayout() {
		return engineLayout;
	}

	public void setEngineLayout(EngineLayout engineLayout) {
		this.engineLayout = engineLayout;
	}

	public CarBodyShape getBodyShape() {
		return bodyShape;
	}

	public void setBodyShape(CarBodyShape bodyShape) {
		this.bodyShape = bodyShape;
	}

	public CarSeatsConfig getSeatsConfig() {
		return seatsConfig;
	}

	public void setSeatsConfig(CarSeatsConfig seatsConfig) {
		this.seatsConfig = seatsConfig;
	}   
}
