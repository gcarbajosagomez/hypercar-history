package com.phistory.mvc.controller.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcp.data.model.car.CarInternetContent;

/**
 * Set of utilities for {@link CarInternetContent}
 * 
 * @author gonzalo
 *
 */
@Component
public class CarInternetContentUtils 
{
	private static final String EMBED_URL_PATH = "/embed";
	private static final String WATCH_QUERY_STRING = "watch?v=";
	private static final String YOUTUBE_BASE_URL = "www.youtube.com";
	
	/**
	 * Build embed-ready URLs for the supplied {@link CarInternetContent#link}s
	 * 
	 * @param carInternetContents
	 * @return The {@link List<CarInternetContent>} with embed-ready URLs
	 */
	public List<CarInternetContent> buildEmbedlinks(List<CarInternetContent> carInternetContents) 
	{
		List<CarInternetContent> processedList = new ArrayList<>();
		carInternetContents.stream()
		  				   .filter(content -> content.getLink().contains(YOUTUBE_BASE_URL))
						   .forEach(content -> {
							   String processedLink = content.getLink();
							   processedLink = processedLink.replace(YOUTUBE_BASE_URL, YOUTUBE_BASE_URL + EMBED_URL_PATH);
							   processedLink = processedLink.replace(WATCH_QUERY_STRING, "");
							   content.setLink(processedLink);
							   processedList.add(content);
							});
		 return processedList;
	}
	
	/**
	 * Extract the video ids from the Youtube videos contained in the the supplied {@link CarInternetContent#link}s
	 * 
	 * @param carInternetContents
	 * @return A {@link List<String>} with the Youtube video ids
	 */
	public List<String> extractYoutubeVideoIds(List<CarInternetContent> carInternetContents) 
	{
		List<String> processedList = new ArrayList<>();
		carInternetContents.stream()
		  				   .filter(content -> content.getLink().contains(YOUTUBE_BASE_URL))
						   .forEach(content -> {							   
							try {
								URL youtubeURL = new URL(content.getLink());
								String videoId = youtubeURL.getQuery().replace("v=", "");
								processedList.add(videoId);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							   
							});
		 return processedList;
	}
}
