package com.phistory.mvc.controller.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Component;

/**
 * A provider for dates
 * 
 * @author gonzalo
 *
 */
@Component
public class DateProvider
{	
	/**
	 * Return the current time in the "Europe/Madrid" timeZone
	 * 
	 * @return The {@link DateTime}
	 */
	public DateTime getCurrentTime()
	{
		return DateTime.now(DateTimeZone.forID("Europe/Madrid")).withMillisOfSecond(0)
																.withSecondOfMinute(0);
	}
}
