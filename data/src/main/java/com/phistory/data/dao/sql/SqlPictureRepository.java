package com.phistory.data.dao.sql;

import com.phistory.data.model.picture.Picture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gonzalo Carbajosa on 20/03/17.
 */
@Repository
public interface SqlPictureRepository  extends CrudRepository<Picture, Long> {
}
