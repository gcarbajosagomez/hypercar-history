package com.phistory.data.model.tyre;

import com.phistory.data.model.GenericEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;

/**
 *main.java.
 * @author Gonzalo
 */
@Entity
@Table(name = "tyre")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tyre implements GenericEntity
{
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "tyre_id")
    private Long id;

    @Column(name = "tyre_width")
    private Long width;

    @Column(name = "tyre_profile")
    private Long profile;

    @Column(name = "rim_diameter")
    private Long rimDiameter;

    @Enumerated(ORDINAL)
    @Column(name = "tyre_train", nullable = false)
    private TyreTrain train;

    @Override
    public Long getId() {
        return id;
    }

	@Override
	public String getFriendlyName() {
		return null;
	}
}
