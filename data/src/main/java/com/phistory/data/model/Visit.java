package com.phistory.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

/**
 *
 * @author Gonzalo
 */
@Getter
@Setter
@Entity
@Table(name = "visit")
public class Visit implements GenericObject
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "visit_id")
    private Long id;
    @Column(name = "visit_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateTime;
    @Column(name = "visit_ip", nullable = true)
    private String IP;
    @Column(name = "visit_uri", nullable = true)
    private String URI;

    @Override
    public Long getId() {
        return id;
    }

	@Override
	public String getFriendlyName() {
		return null;
	}
}
