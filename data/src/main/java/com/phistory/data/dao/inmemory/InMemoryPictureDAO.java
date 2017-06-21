package com.phistory.data.dao.inmemory;

import com.phistory.data.model.picture.Picture;

import java.util.List;
import java.util.Optional;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface InMemoryPictureDAO extends InMemoryDAO<Picture, Long> {

    /**
     * Get a {@link List} of {@link Picture#id} whose {@link Picture#car#id} matches the supplied {@code carId}
     *
     * @param carId
     * @return The resulting {@link List<Long>}
     */
    List<Long> getIdsByCarId(Long carId);

    /**
     * Get a {@link List} of {@link Picture}s whose {@link Picture#car#id} matches the supplied {@code carId}
     *
     * @param carId
     * @return The resulting {@link List<Picture>}
     */
    List<Picture> getByCarId(Long carId);

    /**
     * Get all the {@link Picture#id}s
     *
     * @return the {@link List} of {@link Picture} ids
     */
    List<Long> getAllIds();

    /**
     * Get the preview {@link Picture} for the supplied {@code carId}
     *
     * @param carId
     * @return
     */
    Optional<Picture> getCarPreview(Long carId);
}
