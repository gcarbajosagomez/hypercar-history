package com.phistory.mvc.cms.propertyEditor;

import com.tcp.data.dao.Dao;
import com.tcp.data.dao.impl.PictureDao;
import com.tcp.data.model.Picture;

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
