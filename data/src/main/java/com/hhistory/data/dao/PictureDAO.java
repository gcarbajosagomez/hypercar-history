package com.hhistory.data.dao;

import com.hhistory.data.model.picture.Picture;

import java.util.List;
import java.util.Optional;

/**
 * Created by Gonzalo Carbajosa on 15/10/17.
 */
public interface PictureDAO {

    /**
     * Get the {@link Picture} whose {@link Picture#getId()} matches the {@code id} supplied
     *
     * @param id
     * @return The {@link Picture} found if any, null otherwise
     */
    Picture getById(Long id);

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
    List getAllIds();

    /**
     * Get all the {@link Picture#id}s of {@link Picture}s whose {@link Picture#eligibleForPreview} is true and
     * {@link Picture#car#manufacturer#id} matches the supplied {@code manufacturerId}
     *
     * @return the {@link List} of {@link Picture} ids
     */
    List<Long> getAllPreviewIds(Long manufacturerId);

    /**
     * Get the preview {@link Picture} for the supplied {@code carId}
     *
     * @param carId
     * @return
     */
    Optional<Picture> getCarPreview(Long carId);
}
