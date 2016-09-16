package com.phistory.data.model.tyre;

import com.phistory.data.model.GenericObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;

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
public class Tyre implements GenericObject
{
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "tyre_id")
    private Long id;
    @Column(name = "tyre_width", nullable = true)
    private Long width;
    @Column(name = "tyre_profile", nullable = true)
    private Long profile;
    @Column(name = "rim_diameter", nullable = true)
    private Long rimDiameter;
    @Enumerated(ORDINAL)
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
