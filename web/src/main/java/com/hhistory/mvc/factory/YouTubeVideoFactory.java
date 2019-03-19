package com.hhistory.mvc.factory;

import com.hhistory.data.model.car.CarInternetContent;
import com.hhistory.data.model.car.YouTubeVideo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;

/**
 * Created by Gonzalo Carbajosa on 18/03/19
 */
@Slf4j
@Component
public class YouTubeVideoFactory {
    private static final String YOUTUBE_BASE_URL = "www.youtube.com";

    public YouTubeVideo buildFromCarInternetContent(CarInternetContent carInternetContent) {
        return YouTubeVideo.builder()
                           .id(carInternetContent.getId())
                           .link(carInternetContent.getLink())
                           .type(carInternetContent.getType())
                           .addedDate(carInternetContent.getAddedDate())
                           .contentLanguage(carInternetContent.getContentLanguage())
                           .car(carInternetContent.getCar())
                           .videoId(extractYoutubeVideos(carInternetContent))
                           .build();
    }

    /**
     * Extract the video id from the YouTube video contained in the the supplied {@link CarInternetContent#link}
     */
    private String extractYoutubeVideos(CarInternetContent carInternetContent) {
        return Optional.of(carInternetContent).filter(content -> content.getLink().contains(YOUTUBE_BASE_URL))
                       .map(content -> {
                           try {
                               URL youtubeURL = new URL(content.getLink());
                               return youtubeURL.getQuery().replace("v=", "");
                           } catch (Exception e) {
                               log.error(e.toString(), e);
                               return null;
                           }
                       })
                       .orElse(null);
    }
}
