package com.phistory.data.model.transmission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;

/**
 * @author Gonzalo
 */
@Entity
@Table(name = "transmission")
@JsonIgnoreProperties(value = {"car"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transmission implements GenericEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "transmission_id")
    private Long id;

    @Enumerated(value = ORDINAL)
    @Column(name = "transmission_type", nullable = false)
    private TransmissionType type;

    @Column(name = "transmission_num_of_gears", nullable = false)
    private Integer numOfGears;

    @OneToOne
    @JoinColumn(name = "transmission_car_id")
    private Car car;

    @Override
    public Long getId() {
        return id;
    }
}
