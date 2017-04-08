package com.phistory.data.dao.sql;

import com.phistory.data.model.picture.Picture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import static com.phistory.data.dao.sql.SqlPictureRepository.PICTURE_REPOSITORY;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
@Repository(PICTURE_REPOSITORY)
public interface SqlPictureRepository  extends CrudRepository<Picture, Long> {

    String PICTURE_REPOSITORY = "pictureRepository";
}
