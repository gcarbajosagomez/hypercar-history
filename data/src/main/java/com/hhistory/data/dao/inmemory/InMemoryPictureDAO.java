package com.hhistory.data.dao.inmemory;

import com.hhistory.data.dao.PictureDAO;
import com.hhistory.data.model.picture.Picture;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface InMemoryPictureDAO extends PictureDAO, InMemoryDAO<Picture, Long> {

}
