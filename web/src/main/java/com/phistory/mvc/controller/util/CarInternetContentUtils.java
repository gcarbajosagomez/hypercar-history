package com.phistory.mvc.controller.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.phistory.data.model.car.CarInternetContent;

/**
 * Set of utilities for {@link CarInternetContent}
 *
 * @author gonzalo
 */
@Slf4j
@Component
public class CarInternetContentUtils {
    private static final String YOUTUBE_BASE_URL = "www.youtube.com";

    /**
     * Extract the video ids from the Youtube videos contained in the the supplied {@link CarInternetContent#link}s
     *
     * @param carInternetContents
     * @return A {@link List<String>} with the Youtube video ids
     */
    public List<String> extractYoutubeVideoIds(List<CarInternetContent> carInternetContents) {
        List<String> processedList = new ArrayList<>();
        carInternetContents.stream()
                           .filter(content -> content.getLink().contains(YOUTUBE_BASE_URL))
                           .forEach(content -> {
                               try {
                                   URL youtubeURL = new URL(content.getLink());
                                   String videoId = youtubeURL.getQuery().replace("v=", "");
                                   processedList.add(videoId);
                               } catch (Exception e) {
                                   log.error(e.toString(), e);
                               }
                            });
        return processedList;
    }
}
