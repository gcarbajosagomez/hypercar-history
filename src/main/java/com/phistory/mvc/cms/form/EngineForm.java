package com.phistory.mvc.cms.form;

import javax.validation.constraints.NotNull;

import com.tcp.data.model.engine.EngineCylinderDisposition;
import com.tcp.data.model.engine.EngineType;

/**
 * Engine form
 *
 * @author Gonzalo
 */
public class EngineForm
{   
    private Long id;
    private String code;
    @NotNull(message = "The field must not be blank.")
    private Long size;
    @NotNull(message = "The field must not be blank.")
    private EngineType type;
    @NotNull(message = "The field must not be blank.")
    private EngineCylinderDisposition cylinderDisposition;
    @NotNull(message = "The field must not be blank.")
    private Long numberOfCylinders;
    private Long numberOfValves;
    private Long maxPower;
    private Long maxRPM;
    private Long maxPowerRPM;
    private Long maxTorque;
    private Long maxTorqueRPM;

    public EngineForm() {
    }

    public EngineForm(Long id, String code, Long size, EngineType type, EngineCylinderDisposition cylinderDisposition, Long numberOfCylinders, Long numberOfValves, Long maxPower, Long maxRPM, Long maxPowerRPM, Long maxTorque, Long maxTorqueRPM) {
        this.id = id;
        this.code = code;
        this.size = size;
        this.type = type;
        this.cylinderDisposition = cylinderDisposition;
        this.numberOfCylinders = numberOfCylinders;
        this.numberOfValves = numberOfValves;
        this.maxPower = maxPower;
        this.maxRPM = maxRPM;
        this.maxPowerRPM = maxPowerRPM;
        this.maxTorque = maxTorque;
        this.maxTorqueRPM = maxTorqueRPM;
    }

    public EngineCylinderDisposition getCylinderDisposition() {
        return cylinderDisposition;
    }

    public void setCylinderDisposition(EngineCylinderDisposition cylinderDisposition) {
        this.cylinderDisposition = cylinderDisposition;
    }

    public Long getNumberOfCylinders() {
        return numberOfCylinders;
    }

    public void setNumberOfCylinders(Long numberOfCylinders) {
        this.numberOfCylinders = numberOfCylinders;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(Long maxPower) {
        this.maxPower = maxPower;
    }

    public Long getMaxPowerRPM() {
        return maxPowerRPM;
    }

    public void setMaxPowerRPM(Long maxPowerRPM) {
        this.maxPowerRPM = maxPowerRPM;
    }

    public Long getMaxRPM() {
        return maxRPM;
    }

    public void setMaxRPM(Long maxRPM) {
        this.maxRPM = maxRPM;
    }

    public Long getMaxTorque() {
        return maxTorque;
    }

    public void setMaxTorque(Long maxTorque) {
        this.maxTorque = maxTorque;
    }

    public Long getMaxTorqueRPM() {
        return maxTorqueRPM;
    }

    public void setMaxTorqueRPM(Long maxTorqueRPM) {
        this.maxTorqueRPM = maxTorqueRPM;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public EngineType getType() {
        return type;
    }

    public void setType(EngineType type) {
        this.type = type;
    }

    public Long getNumberOfValves() {
        return numberOfValves;
    }

    public void setNumberOfValves(Long numberOfValves) {
        this.numberOfValves = numberOfValves;
    }
}
