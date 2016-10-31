package com.phistory.data.model;

/**
 *
 * @author Gonzalo
 */
public interface GenericObject
{
    String ENTITY_ID_PROPERTY_NAME = "id";

    Long getId();
    
    String getFriendlyName();
}
