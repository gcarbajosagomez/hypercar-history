package com.tcp.data.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Gonzalo
 */
@Getter
@Setter
@Entity
@Table(name = "visit")
public class Visit implements Serializable, GenericObject
{
    private static final long serialVersionUID = 3717603741285689555L;
    //
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
