package com.phistory.mvc.cms.propertyEditor;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gonzalo
 */
public class DatePropertyEditor extends PropertyEditorSupport
{
    private static final Logger logger = LoggerFactory.getLogger(DatePropertyEditor.class);
    private SimpleDateFormat dateFormat;

    public DatePropertyEditor(SimpleDateFormat dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    @Override
    public String getAsText()
    {
        try
        {
            Calendar calendar = (Calendar) getValue();
            
            if (calendar != null)
            {            
            	return dateFormat.format(calendar.getTime());  
            }
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
            
        }
        
        return "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        try
        {
            Date date = dateFormat.parse(text);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            setValue(calendar);
        }
        catch (Exception e)
        {
            logger.error(e.toString(), e);
        }
    }
}
