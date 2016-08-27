package com.tcp.data.model.car;

/**
 * Enumeration of the different production types a {@link Car} can have
 * 
 * @author gonzalo
 *
 */
public enum ProductionType
{
	SERIES_RUN("seriesRun"),
	LIMITED_EDITION("limitedEdition"),
	ONE_OFF("oneOff"), 
	PROTOTYPE("prototype");
	
	private String name;
	
	ProductionType(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
}
