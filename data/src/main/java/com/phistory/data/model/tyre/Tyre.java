package com.tcp.data.model.tyre;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.tcp.data.model.GenericObject;

/**
 *main.java.
 * @author Gonzalo
 */
@Entity
@Table(name = "tyre")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tyre implements Serializable, GenericObject
{
    private static final long serialVersionUID = -7487186443029126193L;
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tyre_id")
    private Long id;
    @Column(name = "tyre_width", nullable = true)
    private Long width;
    @Column(name = "tyre_profile", nullable = true)
    private Long profile;
    @Column(name = "rim_diameter", nullable = true)
    private Long rimDiameter;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tyre_train", nullable = false)
    private CarTyreTrain train;

    @Override
    public Long getId() {
        return id;
    }

	@Override
	public String getFriendlyName() {
		return null;
	}
}
