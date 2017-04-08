package com.phistory.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Blob;

import static javax.persistence.GenerationType.*;

/**
 * main.java.
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "manufacturer")
@JsonIgnoreProperties(value = {"logo"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer implements GenericEntity {

    public static final String NAME_PROPERTY = "name";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "manufacturer_id")
    private Long id;

    @Column(name = "manufacturer_name", nullable = false)
    private String name;

    @Column(name = "manufacturer_nationality")
    private String nationality;

    @Column(name = "manufacturer_logo")
    @Lob
    private Blob logo;

    @Column(name = "manufacturer_history_es", columnDefinition = "LONGTEXT")
    private String historyES;

    @Column(name = "manufacturer_history_en", columnDefinition = "LONGTEXT")
    private String historyEN;

    @Override
    public String toString() {
        return this.name;
    }
}
