package com.phistory.mvc.cms.propertyEditor;

import com.phistory.data.dao.generic.Dao;
import com.phistory.data.model.GenericObject;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author Gonzalo
 */
public class GenericObjectPropertyEditor<TYPE extends GenericObject, IDENTIFIER> extends PropertyEditorSupport
{
    protected Dao<TYPE, IDENTIFIER> dao;

    public GenericObjectPropertyEditor(Dao<TYPE, IDENTIFIER> dao)
    {
        this.dao = dao;
    }

    @SuppressWarnings("unchecked")
	@Override
    public String getAsText()
    {
        if (getValue() != null)
        {
            Long id = ((TYPE) getValue()).getId();
            
            if (id != null)
            {
                return id.toString();
            }
            else
            {
                return "-1";
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void setAsText(String idText) throws IllegalArgumentException
    {    	
        if (idText != null && !idText.isEmpty())
        {
            IDENTIFIER id = (IDENTIFIER) new Long(idText);
            super.setValue(this.dao.getById(id));
        }
        else
        {
        	super.setValue(null);
        }
    }
}
