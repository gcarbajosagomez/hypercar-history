package com.phistory.mvc.cms.propertyEditor;

import com.phistory.data.dao.SQLDAO;
import com.phistory.data.dao.sql.impl.SQLPictureDAO;
import com.phistory.data.model.Picture;

public class PreviewPicturePropertyEditor extends GenericObjectPropertyEditor<Picture, Long>
{
	public PreviewPicturePropertyEditor(SQLDAO<Picture, Long> SQLDAO)
	{
		super(SQLDAO);
	}
	
	@Override
    public void setAsText(String idText) throws IllegalArgumentException
    {
        if (idText != null && !idText.isEmpty())
        {
            Long carId = new Long(idText);
            setValue(((SQLPictureDAO) SQLDAO).getCarPreview(carId));
        }
        else
        {
            setValue(null);
        }
    }
}
