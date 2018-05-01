package com.hhistory.data.model.brake;

import com.hhistory.data.model.GenericEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.hhistory.data.model.brake.Brake.BRAKE_TYPE_COLUMN_NAME;
import static javax.persistence.DiscriminatorType.*;
import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

/**
 * @author Gonzalo
 */
@Entity
@Table(name = "brake")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = BRAKE_TYPE_COLUMN_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class Brake implements GenericEntity {

    public static final String BRAKE_TYPE_COLUMN_NAME = "brake_type";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "brake_id")
    private Long id;

    @Enumerated(ORDINAL)
    @Column(name = "brake_train", nullable = false)
    private BrakeTrain train;

    @Override
    public Long getId() {
        return id;
    }

    public abstract BrakeType getType();
}
