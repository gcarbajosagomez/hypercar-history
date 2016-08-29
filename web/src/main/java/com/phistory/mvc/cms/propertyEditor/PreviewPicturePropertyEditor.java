package com.phistory.mvc.cms.propertyEditor;

import com.phistory.data.dao.generic.Dao;
import com.phistory.data.dao.impl.PictureDao;
import com.phistory.data.model.Picture;

public class PreviewPicturePropertyEditor extends GenericObjectPropertyEditor<Picture, Long>
{
	public PreviewPicturePropertyEditor(Dao<Picture, Long> dao)
	{
		super(dao);		
	}
	
	@Override
    public void setAsText(String idText) throws IllegalArgumentException
    {
        if (idText != null && !idText.isEmpty())
        {
            Long carId = new Long(idText);
            setValue(((PictureDao)dao).getCarPreview(carId));
        }
        else
        {
            setValue(null);
        }
    }
}
