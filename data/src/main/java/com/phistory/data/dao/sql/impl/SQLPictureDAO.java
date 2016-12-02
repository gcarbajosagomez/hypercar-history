package com.phistory.data.dao.sql.impl;

import com.phistory.data.command.PictureDataCommand;
import com.phistory.data.dao.SQLDAO;
import com.phistory.data.model.picture.Picture;
import com.phistory.data.model.util.PictureUtil;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import static com.phistory.data.model.GenericEntity.ID_FIELD;

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
                                               + " WHERE picture.car.id = :carId"
                                               + " ORDER BY picture.galleryPosition ASC");

        q.setParameter("carId", carId);

        return q.list();
    }

    public Picture getCarPreview(Long carId) {
        Query q = getCurrentSession().createQuery("FROM Picture AS picture"
                                               + " WHERE picture.car.id = :carId"
                                               + " AND picture.eligibleForPreview = true");

        q.setParameter("carId", carId);
        List<Picture> previewCandidates = q.list();
        return PictureUtil.getPreviewPictureFromCandidates(previewCandidates);
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

    public List<Picture> getPaginated(int firstResult, int limit) {
        Query query = super.getCurrentSession()
                           .createQuery("FROM Picture AS picture"
                                     + " ORDER BY picture.id");

        query.setFirstResult(firstResult);
        query.setMaxResults(limit);

        return query.list();
    }
}
