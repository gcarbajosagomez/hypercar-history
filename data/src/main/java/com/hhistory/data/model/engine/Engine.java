package com.hhistory.data.model.engine;

import com.hhistory.data.model.GenericEntity;
import com.hhistory.data.model.util.EngineUtils;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;

/**
 * @author Gonzalo
 */
@Entity
@Table(name = "engine")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Engine implements GenericEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "engine_id")
    private Long id;

    @Column(name = "engine_code")
    private String code;

    @Column(name = "engine_size", nullable = false)
    private Integer size;

    @Enumerated(ORDINAL)
    @Column(name = "engine_type", nullable = false)
    private EngineType type;

    @Enumerated(ORDINAL)
    @Column(name = "engine_cylinder_disposition", nullable = false)
    private EngineCylinderDisposition cylinderDisposition;

    @Column(name = "engine_cylinder_bank_angle")
    private Integer cylinderBankAngle;

    @Column(name = "engine_number_of_cylinders", nullable = false)
    private Integer numberOfCylinders;

    @Column(name = "engine_number_of_valves")
    private Integer numberOfValves;

    @Column(name = "engine_max_power")
    private Integer maxPower;

    @Column(name = "engine_max_rpm")
    private Integer maxRPM;

    @Column(name = "engine_max_power_rpm")
    private Integer maxPowerRPM;

    @Column(name = "engine_max_torque")
    private Integer maxTorque;

    @Column(name = "engine_max_torque_rpm")
    private Integer maxTorqueRPM;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return EngineUtils.buildToString(this.code,
                                         this.size,
                                         this.cylinderDisposition,
                                         this.numberOfCylinders,
                                         this.maxPower);
    }
}
