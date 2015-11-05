package com.phistory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point to the application
 * 
 * @author gonzalo
 *
 */
@SpringBootApplication()
public class Main
{
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	/**
	 * Entry method to the application
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{		
		try
		{			
			SpringApplication.run(Main.class, args);
		}
		catch (Exception e) 
		{
			logger.error(e.toString(), e);
		}
	}
}
