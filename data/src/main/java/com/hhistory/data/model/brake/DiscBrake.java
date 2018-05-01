package com.hhistory.data.model.brake;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

import static com.hhistory.data.model.brake.BrakeType.*;
import static javax.persistence.EnumType.ORDINAL;

@Entity
@DiscriminatorValue(value = "disc")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DiscBrake extends Brake {

    @Column(name = "brake_disc_diameter")
    private Long discDiameter;

    @Enumerated(ORDINAL)
    @Column(name = "brake_disc_material")
    private BrakeDiscMaterial discMaterial;

    @Column(name = "brake_caliper_number_of_pistons")
    private Long caliperNumOfPistons;

    public DiscBrake(Long id,
                     BrakeTrain train,
                     Long discDiameter,
                     BrakeDiscMaterial discMaterial,
                     Long caliperNumOfPistons) {
        super(id, train);
        this.discDiameter = discDiameter;
        this.discMaterial = discMaterial;
        this.caliperNumOfPistons = caliperNumOfPistons;
    }

    @Override
    public BrakeType getType() {
        return DISC;
    }
}
