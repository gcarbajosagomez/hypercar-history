package com.phistory.mvc.cms.form;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.phistory.data.model.engine.EngineCylinderDisposition;
import com.phistory.data.model.engine.EngineType;

/**
 * Engine form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineEditForm implements EditForm {

    private Long   id;
    private String code;

    @NotNull(message = "The field must not be blank.")
    private Integer size;

    @NotNull(message = "The field must not be blank.")
    private EngineType type;

    @NotNull(message = "The field must not be blank.")
    private EngineCylinderDisposition cylinderDisposition;

    private Integer cylinderBankAngle;

    @NotNull(message = "The field must not be blank.")
    private Integer numberOfCylinders;

    private Integer numberOfValves;
    private Integer maxPower;
    private Integer maxRPM;
    private Integer maxPowerRPM;
    private Integer maxTorque;
    private Integer maxTorqueRPM;
}
