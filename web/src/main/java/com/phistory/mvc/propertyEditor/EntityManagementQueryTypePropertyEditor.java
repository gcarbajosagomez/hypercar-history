package com.phistory.mvc.propertyEditor;

import com.phistory.mvc.cms.command.EntityManagementQueryType;

import java.beans.PropertyEditorSupport;

/**
 * Created by Gonzalo Carbajosa on 3/12/16.
 */
public class EntityManagementQueryTypePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String entityManagementQueryType) throws IllegalArgumentException {
        super.setValue(EntityManagementQueryType.map(entityManagementQueryType));
    }
}
