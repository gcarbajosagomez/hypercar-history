package com.phistory.mvc.cms.propertyEditor;

import com.tcp.data.dao.Dao;
import com.tcp.data.model.ModelObject;

import java.beans.PropertyEditorSupport;
import java.io.Serializable;

/**
 *
 * @author Gonzalo
 */
public class GenericObjectPropertyEditor<TYPE extends ModelObject, IDENTIFIER extends Serializable> extends PropertyEditorSupport
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
            setValue(dao.getById(id));
        }
        else
        {
            setValue(null);
        }
    }
}
