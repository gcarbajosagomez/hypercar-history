package com.tcp.data.model.transmission;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tcp.data.model.GenericObject;
import com.tcp.data.model.car.Car;
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
public class Transmission implements Serializable, GenericObject
{
    private static final long serialVersionUID = -1092366163400094540L;
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transmission_id")
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "transmission_type", nullable = false)
    private TransmissionType type;
    @Column(name = "transmission_num_of_gears", nullable = false)
    private Integer numOfGears;
    @OneToOne(fetch = FetchType.LAZY)
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
