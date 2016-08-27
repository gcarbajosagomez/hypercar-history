package com.tcp.data.model.engine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.tcp.data.model.GenericObject;

/**
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "engine")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Engine implements Serializable, GenericObject
{
    private static final long serialVersionUID = -1463000525545868760L;
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "engine_id")
    private Long id;
    @Column(name = "engine_code", nullable = true)
    private String code;
    @Column(name = "engine_size", nullable = false)
    private Long size;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "engine_type", nullable = false)
    private EngineType type;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "engine_cylinder_disposition", nullable = false)
    private EngineCylinderDisposition cylinderDisposition;
    @Column(name = "engine_number_of_cylinders", nullable = false)
    private Long numberOfCylinders;
    @Column(name = "engine_number_of_valves", nullable = true)
    private Long numberOfValves;
    @Column(name = "engine_max_power", nullable = true)
    private Long maxPower;
    @Column(name = "engine_max_rpm", nullable = true)
    private Long maxRPM;
    @Column(name = "engine_max_power_rpm", nullable = true)
    private Long maxPowerRPM;
    @Column(name = "engine_max_torque", nullable = true)
    private Long maxTorque;
    @Column(name = "engine_max_torque_rpm", nullable = true)
    private Long maxTorqueRPM;

    @Override
    public Long getId() {
        return id;
    }

	@Override
	public String getFriendlyName() {
		return getCode();
	}  
}
