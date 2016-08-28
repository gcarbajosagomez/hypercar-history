package com.phistory.data.model.brake;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.phistory.data.model.GenericObject;
import com.phistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

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
public class BrakeSet implements GenericObject
{
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
