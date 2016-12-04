package com.phistory.data.model.tyre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.FetchType.LAZY;

/**
 *main.java.
 * @author Gonzalo
 */
@Entity
@Table(name = "tyre_set",
	   uniqueConstraints = @UniqueConstraint(columnNames = {TyreSet.FRONT_TYRE_ID_FIELD,
                                                            TyreSet.BACK_TYRE_ID_FIELD,
                                                            TyreSet.TYRE_SET_CAR_ID_FIELD}))
@JsonIgnoreProperties(value = {"car"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TyreSet implements GenericEntity
{
    public static final String FRONT_TYRE_ID_FIELD = "front_tyre_id";
    public static final String BACK_TYRE_ID_FIELD = "back_tyre_id";
    public static final String TYRE_SET_CAR_ID_FIELD = "tyre_set_car_id";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tyre_set_id")
    private Long id;

    @Enumerated(ORDINAL)
    @Column(name = "tyre_set_manufacturer_name", nullable = false)
    private TyreManufacturer manufacturer;

    @Column(name = "tyre_set_type", nullable = false)
    private TyreType type;

    @Column(name = "tyre_set_model_name", nullable = false)
    private String model;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = FRONT_TYRE_ID_FIELD, nullable = false, unique = true)
    private Tyre frontTyre;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = BACK_TYRE_ID_FIELD, nullable = false, unique = true)
    private Tyre backTyre;

    @OneToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = TYRE_SET_CAR_ID_FIELD)
    private Car car;

    @Override
    public Long getId() {
        return id;
    }

	@Override
	public String toString() {
		return null;
	}
}
