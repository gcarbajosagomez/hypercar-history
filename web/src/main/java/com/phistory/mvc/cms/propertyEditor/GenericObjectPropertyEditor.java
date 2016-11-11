package com.phistory.mvc.cms.propertyEditor;

import com.phistory.data.dao.SQLDAO;
import com.phistory.data.model.GenericEntity;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author Gonzalo
 */
public class GenericObjectPropertyEditor<TYPE extends GenericEntity, IDENTIFIER> extends PropertyEditorSupport
{
    protected SQLDAO<TYPE, IDENTIFIER> SQLDAO;

    public GenericObjectPropertyEditor(SQLDAO<TYPE, IDENTIFIER> SQLDAO)
    {
        this.SQLDAO = SQLDAO;
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
            super.setValue(this.SQLDAO.getById(id));
        }
        else
        {
        	super.setValue(null);
        }
    }
}
