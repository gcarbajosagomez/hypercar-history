package com.phistory.data.model.tyre;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.phistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phistory.data.model.GenericObject;

/**
 *main.java.
 * @author Gonzalo
 */
@Entity
@Table(name = "tyre_set",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"front_tyre_id", "front_tyre_id", "tyre_set_car_id"}))
@JsonIgnoreProperties(value = {"car"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TyreSet implements Serializable, GenericObject
{
    private static final long serialVersionUID = -6737665091994278170L;
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tyre_set_id")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "front_tyre_id", nullable = false, unique = true)
    private Tyre frontTyre;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "back_tyre_id", nullable = false, unique = true)
    private Tyre backTyre;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tyre_set_car_id", nullable = true)
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
