package com.phistory.data.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class Manufacturer implements Serializable, GenericObject
{
    private static final long serialVersionUID = 1004179131340222927L;
    //
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
