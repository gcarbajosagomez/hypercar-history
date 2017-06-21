package com.phistory.mvc.propertyEditor;

import com.phistory.mvc.manufacturer.Manufacturer;

import java.beans.PropertyEditorSupport;

/**
 * Created by Gonzalo Carbajosa on 21/05/17.
 */
public class ManufactuerPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String manufacturerShortName) throws IllegalArgumentException {
        super.setValue(Manufacturer.mapShortName(manufacturerShortName));
    }
}
