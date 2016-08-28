package com.phistory.data.model.tyre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phistory.data.model.GenericObject;
import com.phistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
public class TyreSet implements GenericObject
{
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
