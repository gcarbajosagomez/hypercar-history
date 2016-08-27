package com.tcp.data.model.brake;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tcp.data.model.GenericObject;
import com.tcp.data.model.car.Car;

/**
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "brake_set",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"front_brake_id", "back_brake_id", "brake_set_car_id"}))
@JsonIgnoreProperties(value = {"car"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrakeSet implements Serializable, GenericObject
{
    private static final long serialVersionUID = 1253990673493594859L;
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "brake_set_id")
    private Long id;
    @OneToOne(orphanRemoval = true)
    @Cascade(value = CascadeType.ALL)
    @JoinColumn(name = "front_brake_id", nullable = true, unique = true)
    private Brake frontBrake;
    @OneToOne(orphanRemoval = true)
    @Cascade(value = CascadeType.ALL)
    @JoinColumn(name = "back_brake_id", nullable = true, unique = true)
    private Brake backBrake;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brake_set_car_id", nullable = true)
    private Car car;

    @Override
    public Long getId() {
        return id;
    }

	@Override
	public String getFriendlyName() {
		// TODO Auto-generated method stub
		return null;
	}
}
