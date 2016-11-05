package com.phistory.mvc.cms.propertyEditor;

import com.phistory.data.dao.DAO;
import com.phistory.data.dao.sql.impl.PictureDAO;
import com.phistory.data.model.Picture;

public class PreviewPicturePropertyEditor extends GenericObjectPropertyEditor<Picture, Long>
{
	public PreviewPicturePropertyEditor(DAO<Picture, Long> DAO)
	{
		super(DAO);
	}
	
	@Override
    public void setAsText(String idText) throws IllegalArgumentException
    {
        if (idText != null && !idText.isEmpty())
        {
            Long carId = new Long(idText);
            setValue(((PictureDAO) DAO).getCarPreview(carId));
        }
        else
        {
            setValue(null);
        }
    }
}
