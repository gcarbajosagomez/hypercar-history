package com.phistory.mvc.cms.form;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.tcp.data.model.engine.EngineCylinderDisposition;
import com.tcp.data.model.engine.EngineType;

/**
 * Engine form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
