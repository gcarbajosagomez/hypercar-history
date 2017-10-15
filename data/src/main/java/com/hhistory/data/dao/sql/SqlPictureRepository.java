package com.hhistory.data.dao.sql;

import com.hhistory.data.model.picture.Picture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hhistory.data.dao.sql.SqlPictureRepository.PICTURE_REPOSITORY;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
@Repository(PICTURE_REPOSITORY)
public interface SqlPictureRepository extends CrudRepository<Picture, Long> {

    String PICTURE_REPOSITORY = "pictureRepository";

    @Query("SELECT picture " +
           "FROM Picture AS picture JOIN picture.car as car " +
           "WHERE car.id = :carId " +
           "ORDER BY picture.galleryPosition ASC")
    List<Picture> getByCarId(@Param("carId") Long carId);

    @Query("SELECT picture " +
           "FROM Picture AS picture JOIN picture.car as car " +
           "WHERE car.id = :carId " +
           "AND picture.eligibleForPreview = true")
    List<Picture> getCarPreviews(@Param("carId") Long carId);

}
