package com.phistory.data.dao.sql;

import com.phistory.data.command.PictureDataCommand;
import com.phistory.data.model.picture.Picture;
import org.springframework.data.jpa.repository.Modifying;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Gonzalo Carbajosa on 25/02/17.
 */
public interface SqlPictureDAO extends SqlDAO<Picture> {

    String SQL_PICTURE_DAO = "sqlPictureDAO";

    @Modifying
    void updateGalleryPosition(Picture picture);

    List<Picture> getPaginated(int firstResult, int limit);

    @Modifying
    void saveOrEdit(PictureDataCommand pictureEditCommand) throws IOException;

    Optional<Picture> getCarPreview(Long carId);

    Picture getManufacturerLogo(Long manufacturerId);
}
