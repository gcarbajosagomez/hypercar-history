package com.phistory.mvc.controller.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

/**
 * HTTP-related set of utilities
 * 
 * @author gonzalo
 *
 */
@Component
@Slf4j
public class HTTPUtil {
	/**
	 * Extract the payload parameters contained in the given {@link HttpServletRequest} in a query string style
	 * <p>
	 * example: {param1: 'x', param2: 'y'} becomes "param1=x&param2=y"
	 * 
	 * @param request
	 * @return an {@link Optional} containing the resulting {@link String}, an {@link Optional#empty()} otherwise
	 */
	public Optional<String> extractRequestPayloadParamsFromRequest(HttpServletRequest request)
	{
		InputStream inputStream = null;
		try
		{
			inputStream = request.getInputStream();
		
	        if (inputStream != null)
	        {  
	        	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
	
	            char[] charBuffer = new char[128];  
	            int bytesRead = -1;  	
	            
            	StringBuilder stringBuilder = new StringBuilder();
            	
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0)
				{  
					stringBuilder = new StringBuilder().append(charBuffer, 0, bytesRead);  
				}
				
				String requestUri = parseJSONParamsToQueryString(stringBuilder.toString());
				
				return Optional.of(requestUri);			
	        }
		}
		catch (IOException ioe)
		{
			log.error("There was an error while extracting the request params when calling URI: " + request.getRequestURI(), ioe);
		} 
        
        return Optional.empty();
	}
	
	/**
	 * Parse a JSON-formated {@link String} into a query string-formated one
	 * 
	 * @param jsonString
	 * @return The formated {@link String}
	 */
	private String parseJSONParamsToQueryString(String jsonString)
	{
		jsonString = jsonString.replaceAll("[\"{}]", "");
		jsonString = jsonString.replaceAll(":", "=");
		jsonString = jsonString.replaceAll(",", "&");
		
		return jsonString;
	}
}