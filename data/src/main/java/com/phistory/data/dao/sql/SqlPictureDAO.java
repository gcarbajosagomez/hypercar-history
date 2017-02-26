package com.phistory.data.dao.sql;

import com.phistory.data.command.PictureDataCommand;
import com.phistory.data.model.picture.Picture;

import java.io.IOException;
import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface SqlPictureDAO extends SqlDAO<Picture, Long> {

    void updateGalleryPosition(Picture picture);

    Long count();

    List<Picture> getPaginated(int firstResult, int limit);

    Picture getById(Long id);

    void saveOrEdit(PictureDataCommand pictureEditCommand) throws IOException;

    List<Picture> getByCarId(Long carId);

    Picture getCarPreview(Long carId);

    Picture getManufacturerLogo(Long manufacturerId);
}
