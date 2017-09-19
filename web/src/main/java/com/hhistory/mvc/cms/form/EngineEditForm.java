package com.hhistory.mvc.cms.form;

import javax.validation.constraints.NotNull;

import com.hhistory.data.model.util.EngineUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.hhistory.data.model.engine.EngineCylinderDisposition;
import com.hhistory.data.model.engine.EngineType;
import org.springframework.util.StringUtils;

import java.util.Objects;

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

    @Override
    public String toString() {
        return EngineUtils.buildToString(this.code,
                                         this.size,
                                         this.cylinderDisposition,
                                         this.numberOfCylinders,
                                         this.maxPower);
    }
}
