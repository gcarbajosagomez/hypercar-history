package com.phistory.data.model.brake;

import com.phistory.data.model.GenericObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "brake")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brake implements GenericObject
{	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "brake_id")
    private Long id;
    @Column(name = "brake_disc_diameter", nullable = false)
    private Long discDiameter;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "brake_disc_material", nullable = false)
    private BrakeDiscMaterial discMaterial;
    @Column(name = "brake_caliper_number_of_pistons", nullable = true)
    private Long caliperNumOfPistons;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "brake_train", nullable = false)
    private BrakeTrain train;
    
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
