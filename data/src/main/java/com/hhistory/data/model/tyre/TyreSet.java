package com.hhistory.data.model.tyre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hhistory.data.model.GenericEntity;
import com.hhistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.GenerationType.*;

/**
 * main.java.
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "tyre_set",
        uniqueConstraints = @UniqueConstraint(columnNames = {TyreSet.FRONT_TYRE_ID_FIELD,
                TyreSet.REAR_TYRE_ID_FIELD,
                TyreSet.TYRE_SET_CAR_ID_FIELD}))
@JsonIgnoreProperties(value = {"car"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TyreSet implements GenericEntity {
    public static final String FRONT_TYRE_ID_FIELD   = "front_tyre_id";
    public static final String REAR_TYRE_ID_FIELD    = "rear_tyre_id";
    public static final String TYRE_SET_CAR_ID_FIELD = "tyre_set_car_id";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tyre_set_id")
    private Long id;

    @Enumerated(ORDINAL)
    @Column(name = "tyre_set_manufacturer_name")
    private TyreManufacturer manufacturer;

    @Column(name = "tyre_set_type")
    private TyreType type;

    @Column(name = "tyre_set_model_name")
    private String model;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = FRONT_TYRE_ID_FIELD, nullable = false, unique = true)
    private Tyre frontTyre;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = REAR_TYRE_ID_FIELD, nullable = false, unique = true)
    private Tyre rearTyre;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = TYRE_SET_CAR_ID_FIELD)
    private Car car;

    @Override
    public Long getId() {
        return id;
    }
}
