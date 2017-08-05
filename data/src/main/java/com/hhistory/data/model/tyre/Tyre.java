package com.hhistory.data.model.tyre;

import com.hhistory.data.model.GenericEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * main.java.
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "tyre")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tyre implements GenericEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
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
}
