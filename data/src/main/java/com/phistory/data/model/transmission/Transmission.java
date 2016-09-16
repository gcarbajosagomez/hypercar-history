package com.phistory.data.model.transmission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phistory.data.model.GenericObject;
import com.phistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

/**
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "transmission")
@JsonIgnoreProperties(value = {"car"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transmission implements GenericObject
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transmission_id")
    private Long id;
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "transmission_type", nullable = false)
    private TransmissionType type;
    @Column(name = "transmission_num_of_gears", nullable = false)
    private Integer numOfGears;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "transmission_car_id", nullable = true)
    private Car car;

    @Override
    public Long getId() {
        return id;
    }

	@Override
	public String getFriendlyName() {
		return null;
	}
}
