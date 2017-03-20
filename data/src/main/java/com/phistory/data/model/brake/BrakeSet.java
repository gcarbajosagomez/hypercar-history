package com.phistory.data.model.brake;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CascadeType.ALL;

/**
 * @author Gonzalo
 */
@Entity
@Table(name = "brake_set",
        uniqueConstraints = @UniqueConstraint(columnNames = {BrakeSet.FRONT_BRAKE_ID_FIELD, BrakeSet.REAR_BRAKE_ID_FIELD, BrakeSet.BRAKE_SET_CAR_ID_FIELD}))
@JsonIgnoreProperties(value = {"car"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrakeSet implements GenericEntity {

    public static final String FRONT_BRAKE_ID_FIELD = "front_brake_id";
    public static final String REAR_BRAKE_ID_FIELD = "rear_brake_id";
    public static final String BRAKE_SET_CAR_ID_FIELD = "brake_set_car_id";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "brake_set_id")
    private Long id;

    @OneToOne(orphanRemoval = true)
    @Cascade(value = ALL)
    @JoinColumn(name = FRONT_BRAKE_ID_FIELD, unique = true)
    private Brake frontBrake;

    @OneToOne(orphanRemoval = true)
    @Cascade(value = ALL)
    @JoinColumn(name = REAR_BRAKE_ID_FIELD, unique = true)
    private Brake rearBrake;

    @OneToOne
    @JoinColumn(name = BRAKE_SET_CAR_ID_FIELD)
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
