package com.hhistory.data.model.car;

import com.hhistory.data.model.Language;
import lombok.*;
import org.joda.time.DateTime;

/**
 * Created by Gonzalo Carbajosa on 18/03/19
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class YouTubeVideo extends CarInternetContent {

    private String videoId;

    @Builder
    public YouTubeVideo(Long id,
                        String link,
                        CarInternetContentType type,
                        DateTime addedDate,
                        Language contentLanguage,
                        Car car,
                        String videoId) {
        super(id, link, type, addedDate, contentLanguage, car);
        this.videoId = videoId;
    }

    //Explicitly declaring the builder class is necessary to avoid overriding issues with parent class' builder
    public static class YouTubeVideoBuilder extends CarInternetContent.CarInternetContentBuilder {
        YouTubeVideoBuilder() {
            super();
        }
    }
}
