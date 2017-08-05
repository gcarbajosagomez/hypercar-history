package com.hhistory.data.model.brake;

import com.hhistory.data.model.GenericEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;

/**
 * @author Gonzalo
 */
@Entity
@Table(name = "brake")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brake implements GenericEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "brake_id")
    private Long id;

    @Column(name = "brake_disc_diameter", nullable = false)
    private Long discDiameter;

    @Enumerated(ORDINAL)
    @Column(name = "brake_disc_material", nullable = false)
    private BrakeDiscMaterial discMaterial;

    @Column(name = "brake_caliper_number_of_pistons")
    private Long caliperNumOfPistons;

    @Enumerated(ORDINAL)
    @Column(name = "brake_train", nullable = false)
    private BrakeTrain train;

    @Override
    public Long getId() {
        return id;
    }
}
