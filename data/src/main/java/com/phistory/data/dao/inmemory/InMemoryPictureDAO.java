package com.phistory.data.dao.inmemory;

import com.phistory.data.model.picture.Picture;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface InMemoryPictureDAO extends InMemoryDAO<Picture, Long> {

    List<Long> getIdsByCarId(Long carId);

    List<Picture> getByCarId(Long carId);

    List<Long> getAllIds();

    Picture getCarPreview(Long carId);
}
