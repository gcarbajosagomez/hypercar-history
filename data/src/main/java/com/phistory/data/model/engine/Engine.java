package com.phistory.data.model.engine;

import com.phistory.data.model.GenericEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.*;

/**
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "engine")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Engine implements GenericEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "engine_id")
    private Long id;

    @Column(name = "engine_code", nullable = true)
    private String code;

    @Column(name = "engine_size", nullable = false)
    private Integer size;

    @Enumerated(ORDINAL)
    @Column(name = "engine_type", nullable = false)
    private EngineType type;

    @Enumerated(ORDINAL)
    @Column(name = "engine_cylinder_disposition", nullable = false)
    private EngineCylinderDisposition cylinderDisposition;

    @Column(name = "engine_cylinder_bank_angle", nullable = true)
    private Integer cylinderBankAngle;

    @Column(name = "engine_number_of_cylinders", nullable = false)
    private Integer numberOfCylinders;

    @Column(name = "engine_number_of_valves", nullable = true)
    private Integer numberOfValves;

    @Column(name = "engine_max_power", nullable = true)
    private Integer maxPower;

    @Column(name = "engine_max_rpm", nullable = true)
    private Integer maxRPM;

    @Column(name = "engine_max_power_rpm", nullable = true)
    private Integer maxPowerRPM;

    @Column(name = "engine_max_torque", nullable = true)
    private Integer maxTorque;

    @Column(name = "engine_max_torque_rpm", nullable = true)
    private Integer maxTorqueRPM;

    @Override
    public Long getId() {
        return id;
    }

	@Override
	public String toString() {
		return getCode();
	}  
}
