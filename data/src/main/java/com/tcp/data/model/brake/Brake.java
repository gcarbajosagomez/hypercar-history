package com.tcp.data.model.brake;

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
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "brake")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brake implements Serializable, GenericObject
{	
    private static final long serialVersionUID = -4451996981295051537L;    
    //
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
