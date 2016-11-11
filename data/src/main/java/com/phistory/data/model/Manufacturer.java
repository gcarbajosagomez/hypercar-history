package com.phistory.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;

/**main.java.
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
public class Manufacturer implements GenericEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "manufacturer_id")
    private Long id;
    @Column(name = "manufacturer_name", nullable = false)
    private String name;
    @Column(name = "manufacturer_nationality", nullable = true)
    private String nationality;
    @Column(name = "manufacturer_logo", nullable = true)
    @Lob
    private Blob logo;
    @Column(name = "manufacturer_story", nullable = true)
    private String story;

	@Override
	public String getFriendlyName() {
		return this.name;
	}
}
