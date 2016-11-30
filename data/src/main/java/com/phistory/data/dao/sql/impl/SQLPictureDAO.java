package com.phistory.data.dao.sql.impl;

import com.phistory.data.command.PictureDataCommand;
import com.phistory.data.dao.SQLDAO;
import com.phistory.data.model.picture.Picture;
import com.phistory.data.model.picture.PictureType;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import static com.phistory.data.model.GenericEntity.ID_FIELD;
import static com.phistory.data.model.picture.PictureType.PICTURE;
import static com.phistory.data.model.picture.PictureType.PREVIEW_PICTURE;

/**
 * @author Gonzalo
 */
@Transactional
@Repository
public class SQLPictureDAO extends SQLDAO<Picture, Long> {

    @Override
    public List<Picture> getAll() {
        return super.getCurrentSession()
                    .createQuery("FROM Picture")
                    .list();
    }

    @Override
    public Picture getById(Long id) {
        Query q = getCurrentSession().createQuery("FROM Picture AS picture"
                                               + " WHERE picture.id = :id");
        q.setParameter(ID_FIELD, id);
        return (Picture) q.uniqueResult();
    }

    public List<Picture> getByCarId(Long carId) {
        Query q = getCurrentSession().createQuery("FROM Picture AS picture"
                                               + " WHERE picture.type = :pictureType"
                                               + " AND picture.car.id = :carId"
                                               + " ORDER BY picture.galleryPosition ASC");

        q.setParameter("pictureType", PICTURE);
        q.setParameter("carId", carId);

        return q.list();
    }

    public Picture getCarPreview(Long carId) {
        Query q = getCurrentSession().createQuery("FROM Picture AS picture"
                                               + " WHERE picture.car.id = :carId"
                                               + " AND picture.type = " + PREVIEW_PICTURE.ordinal());

        q.setParameter("carId", carId);
        return (Picture) q.uniqueResult();
    }

    public Picture getManufacturerLogo(Long manufacturerId) {
        Picture picture = new Picture();

        Query q = getCurrentSession().createQuery("SELECT manufacturer.logo"
                                               + " FROM Manufacturer AS manufacturer"
                                               + " WHERE manufacturer.id = :manufacturerId");

        q.setParameter("manufacturerId", manufacturerId);
        picture.setImage((Blob) q.uniqueResult());

        return picture;
    }

    public void saveOrEdit(PictureDataCommand pictureEditCommand) throws IOException {
        LobCreator lobCreator = Hibernate.getLobCreator(getCurrentSession());
        Blob pictureBlob = lobCreator.createBlob(pictureEditCommand.getMultipartFile().getInputStream(), -1);

        Picture picture = pictureEditCommand.getPicture();
        picture.setImage(pictureBlob);

        super.saveOrEdit(picture);
    }

    public void updateGalleryPosition(Picture picture) {
        Query q = super.getCurrentSession()
                       .createQuery("UPDATE Picture"
                                 + " SET galleryPosition = :galleryPosition"
                                 + " WHERE id = :id"
                                 + " AND car = :car");

        q.setParameter("galleryPosition", picture.getGalleryPosition());
        q.setParameter("id", picture.getId());
        q.setParameter("car", picture.getCar());
        q.executeUpdate();
    }

    public Long count() {
        return (Long) super.getCurrentSession()
                           .createQuery("SELECT COUNT (picture.car.id)"
                                     + " FROM Picture AS picture")
                           .uniqueResult();
    }

    /**
     * Count the number of {@link PictureType#PICTURE}s belonging to the supplied carId
     *
     * @param carId
     * @return
     */
    public Long countPicturesByCarId(Long carId) {
        Query query = super.getCurrentSession()
                           .createQuery("SELECT COUNT (picture.car.id)"
                                     + " FROM Picture AS picture"
                                     + " WHERE picture.car.id = :carId"
                                     + " AND picture.type =" + PICTURE.ordinal());

        query.setParameter("carId", carId);
        return (Long) query.uniqueResult();
    }

    public List<Picture> getPaginated(int firstResult, int limit) {
        Query query = super.getCurrentSession()
                           .createQuery("FROM Picture AS picture"
                                     + " ORDER BY picture.id");

        query.setFirstResult(firstResult);
        query.setMaxResults(limit);

        return query.list();
    }
}
