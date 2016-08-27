package com.phistory.data.model.car;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.GenerationType.AUTO;
import static org.hibernate.annotations.CascadeType.ALL;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.phistory.data.model.DriveWheelType;
import com.phistory.data.model.Manufacturer;
import com.phistory.data.model.brake.BrakeSet;
import com.phistory.data.model.engine.Engine;
import com.phistory.data.model.transmission.Transmission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Cascade;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phistory.data.model.GenericObject;
import com.phistory.data.model.tyre.TyreSet;

/**
 *
 * @author Gonzalo
 */
@Entity
@Indexed
@Table(name = Car.CAR_TABLE_NAME,
       uniqueConstraints = @UniqueConstraint(columnNames = {"car_manufacturer_id", "car_model"}))
@JsonIgnoreProperties(value = {"previewImage"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car implements Serializable, GenericObject
{
    public static final String CAR_TABLE_NAME = "car";
    private static final long serialVersionUID = 7919352931966461840L;
    
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "car_id")
    private Long id;
    
    @ManyToOne
    @Cascade(value = SAVE_UPDATE)
    @JoinColumn(name = "car_manufacturer_id", nullable = false)
    private Manufacturer manufacturer;
    
    @Column(name = "car_model", nullable = false)
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String model;
    
    @Column(name = "car_engine_layout", nullable = false)
    private EngineLayout engineLayout;
    
    @ManyToOne
    @Cascade(value = SAVE_UPDATE)
    @JoinColumn(name = "car_engine_id", nullable = true)
    //Do not change nullable to false as otherwise Hibernate won't be able to persist the entity
    //since right before persisting it the engine is null
    private Engine engine;
    
    @Enumerated(ORDINAL)
    @Column(name = "car_body_shape", nullable = false)
    private CarBodyShape bodyShape;
    
    @Enumerated(ORDINAL)
    @Column(name = "car_seats_config", nullable = false)
    private CarSeatsConfig carSeatsConfig;
    
    @Column(name = "car_top_speed", nullable = true)
    private Integer topSpeed;
    
    @Column(name = "car_acceleration", nullable = true)
    private Float acceleration;
    
    @Column(name = "car_fuel_consumption", nullable = true)
    private Float fuelConsumption;
    
    @Enumerated(ORDINAL)
    @Column(name = "car_production_type", nullable = false)
    private ProductionType productionType;
    
    @Column(name = "car_production_start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
    private Calendar productionStartDate;
    
    @Column(name = "car_production_end_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar productionEndDate;
    
    @Column(name = "car_weight", nullable = true)
    private Long weight;
    
    @Column(name = "car_length", nullable = true)
    private Long length;
    
    @Column(name = "car_width", nullable = true)
    private Long width;
    
    @Column(name = "car_height", nullable = true)
    private Long height;
    
    @OneToOne(orphanRemoval = true)
    @Cascade(value = ALL)
    @JoinColumn(name = "car_brake_set_id", nullable = true, unique = true)
    private BrakeSet brakeSet;
    
    @OneToOne()
    @Cascade(value = ALL)
    @JoinColumn(name = "car_transmission_id", nullable = true, unique = true)
    private Transmission transmission;
    
    @Column(name = "car_fuel_tank_capacity", nullable = true)
    private Long fuelTankCapacity;
    
    @OneToOne
    @Cascade(value = ALL)
    @JoinColumn(name = "car_tyre_set_id", nullable = true, unique = true)
    private TyreSet tyreSet;
    
    @Enumerated(ORDINAL)
    @Column(name = "car_drive_wheel_type", nullable = false)
    private DriveWheelType driveWheelType;
    
    @Column(name = "car_road_legal", nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean roadLegal;

    @Override
    public Long getId() {
        return id;
    }   
	
	@Override
	public String getFriendlyName()
	{
		if (getManufacturer() != null)
		{
			return new StringBuilder(getManufacturer().getName() + " " + getModel() + " (id: " + this.id + ")").toString();
		}
		
		return null;
	}
}
