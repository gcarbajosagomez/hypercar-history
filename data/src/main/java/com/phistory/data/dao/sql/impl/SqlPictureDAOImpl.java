package com.phistory.data.dao.sql.impl;

import com.phistory.data.command.PictureDataCommand;
import com.phistory.data.dao.sql.SqlPictureDAO;
import com.phistory.data.dao.sql.SqlPictureRepository;
import com.phistory.data.model.picture.Picture;
import com.phistory.data.model.util.PictureUtil;
import org.hibernate.Hibernate;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import static com.phistory.data.model.GenericEntity.ID_FIELD;
import static com.phistory.data.model.picture.Picture.CAR_ID_PROPERTY_NAME;

/**
 * @author Gonzalo
 */
@Transactional
@Component
public class SqlPictureDAOImpl extends SqlDAOImpl<Picture> implements SqlPictureDAO {

    private SqlPictureRepository sqlPictureRepository;

    @Inject
    public SqlPictureDAOImpl(EntityManager entityManager,
                             SqlPictureRepository sqlPictureRepository) {
        super(entityManager);
        this.sqlPictureRepository = sqlPictureRepository;
    }

    @Override
    public List<Picture> getByCarId(Long carId) {
        Query query = super.getEntityManager()
                           .createQuery("FROM Picture AS picture"
                                        + " WHERE picture.car.id = :carId"
                                        + " ORDER BY picture.galleryPosition ASC");

        query.setParameter(CAR_ID_PROPERTY_NAME, carId);

        return query.getResultList();
    }

    @Override
    public Picture getCarPreview(Long carId) {
        Query query = super.getEntityManager()
                           .createQuery("FROM Picture AS picture"
                                        + " WHERE picture.car.id = :carId"
                                        + " AND picture.eligibleForPreview = true");

        query.setParameter(CAR_ID_PROPERTY_NAME, carId);
        List<Picture> previewCandidates = query.getResultList();

        return PictureUtil.getPreviewPictureFromCandidates(previewCandidates);
    }

    @Override
    public Picture getManufacturerLogo(Long manufacturerId) {
        Picture picture = new Picture();

        Query query = super.getEntityManager()
                           .createQuery("SELECT manufacturer.logo"
                                        + " FROM Manufacturer AS manufacturer"
                                        + " WHERE manufacturer.id = :manufacturerId");

        query.setParameter("manufacturerId", manufacturerId);
        picture.setImage((Blob) query.getSingleResult());

        return picture;
    }

    @Override
    public void saveOrEdit(PictureDataCommand pictureEditCommand) throws IOException {
        LobCreator lobCreator = Hibernate.getLobCreator(super.getCurrentSession());
        Blob pictureBlob = lobCreator.createBlob(pictureEditCommand.getMultipartFile().getInputStream(), -1);

        Picture picture = pictureEditCommand.getPicture();
        picture.setImage(pictureBlob);
        this.sqlPictureRepository.save(picture);
    }

    @Override
    public void updateGalleryPosition(Picture picture) {
        EntityManager entityManager = super.getEntityManager();
        //detach pictures from the persistence context so that when 2 pictures for
        // the same car with the same galleryPosition don't try to override each other
        entityManager.clear();
        Query query = entityManager.createQuery("UPDATE Picture"
                                                + " SET galleryPosition = :galleryPosition"
                                                + " WHERE id = :id"
                                                + " AND car = :car");

        query.setParameter("galleryPosition", picture.getGalleryPosition());
        query.setParameter(ID_FIELD, picture.getId());
        query.setParameter("car", picture.getCar());
        query.executeUpdate();
    }

    @Override
    public List<Picture> getPaginated(int firstResult, int limit) {
        Query query = super.getEntityManager()
                           .createQuery("FROM Picture AS picture"
                                        + " ORDER BY picture.id");

        query.setFirstResult(firstResult);
        query.setMaxResults(limit);

        return query.getResultList();
    }
}
