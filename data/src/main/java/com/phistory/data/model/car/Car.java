package com.phistory.data.model.car;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phistory.data.model.DriveWheelType;
import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.Manufacturer;
import com.phistory.data.model.brake.BrakeSet;
import com.phistory.data.model.engine.Engine;
import com.phistory.data.model.transmission.Transmission;
import com.phistory.data.model.tyre.TyreSet;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.util.Calendar;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.TemporalType.*;
import static org.hibernate.annotations.CascadeType.ALL;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

/**
 *
 * @author Gonzalo
 */
@Entity
@Indexed
@Table(name = Car.CAR_TABLE_NAME,
       uniqueConstraints = @UniqueConstraint(columnNames = {Car.MANUFACTURER_ID_FIELD, Car.MODEL_FIELD, Car.ENGINE_ID_FIELD}))
@JsonIgnoreProperties(value = {"previewImage"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements GenericEntity
{
    public static final String CAR_TABLE_NAME                       = "car";
    public static final String MODEL_FIELD                          = "car_model";
    public static final String MANUFACTURER_ID_FIELD                = "car_manufacturer_id";
    public static final String ENGINE_ID_FIELD                      = "car_engine_id";
    public static final String MODEL_PROPERTY_NAME                  = "model";
    public static final String PRODUCTION_START_DATE_PROPERTY_NAME  = "productionStartDate";

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "car_id")
    private Long id;

    @ManyToOne
    @Cascade(value = SAVE_UPDATE)
    @JoinColumn(name = MANUFACTURER_ID_FIELD, nullable = false)
    private Manufacturer manufacturer;

    @Column(name = MODEL_FIELD, nullable = false)
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
    private String model;

    @Column(name = "car_engine_layout", nullable = false)
    private EngineLayout engineLayout;

    @ManyToOne
    @Cascade(value = SAVE_UPDATE)
    @JoinColumn(name = ENGINE_ID_FIELD, nullable = true)
    //Do not change nullable to false as otherwise Hibernate won't be able to persist the entity
    //since right before persisting it the engine is null
    private Engine engine;

    @Column(name = "car_chassis_materials", nullable = true)
    private String chassisMaterials;

    @Column(name = "car_body_materials", nullable = true)
    private String bodyMaterials;

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
    @Temporal(DATE)
    @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
    private Calendar productionStartDate;

    @Column(name = "car_production_end_date", nullable = true)
    @Temporal(DATE)
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

    @Column(name = "car_description_es", nullable = true, columnDefinition="LONGTEXT")
    private String descriptionES;

    @Column(name = "car_description_en", nullable = true, columnDefinition="LONGTEXT")
    private String descriptionEN;

    @Override
    public Long getId() {
        return id;
    }

	@Override
	public String getFriendlyName()
	{
		if (this.getManufacturer() != null)
		{
			return new StringBuilder(this.getManufacturer().getName() + " " + this.getModel() + " (id: " + this.id + ")").toString();
		}

		return null;
	}
}
