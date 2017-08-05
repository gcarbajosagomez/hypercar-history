package com.hhistory.data.dao.inmemory;

import com.hhistory.data.model.car.CarInternetContent;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface InMemoryCarInternetContentDAO extends InMemoryDAO<CarInternetContent, Long> {

    List<CarInternetContent> getAllVideos();

    List<CarInternetContent> getVideosByCarId(Long carId);

    List<CarInternetContent> getReviewArticlesByCarId(Long carId);
}
