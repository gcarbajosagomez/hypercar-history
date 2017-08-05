package com.hhistory.data.dao.sql.impl;

import com.hhistory.data.command.PictureDataCommand;
import com.hhistory.data.dao.sql.SqlPictureDAO;
import com.hhistory.data.dao.sql.SqlPictureRepository;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.data.model.util.PictureUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

import static com.hhistory.data.dao.sql.impl.SqlPictureDAOImpl.SQL_PICTURE_DAO;
import static com.hhistory.data.model.GenericEntity.ID_FIELD;

/**
 * @author Gonzalo
 */
@Transactional
@Component(SQL_PICTURE_DAO)
@Slf4j
public class SqlPictureDAOImpl extends AbstractSqlDAO<Picture> implements SqlPictureDAO {

    private SqlPictureRepository sqlPictureRepository;
    private PictureUtil          pictureUtil;

    @Inject
    public SqlPictureDAOImpl(EntityManager entityManager,
                             SqlPictureRepository sqlPictureRepository,
                             PictureUtil pictureUtil) {
        super(entityManager);
        this.sqlPictureRepository = sqlPictureRepository;
        this.pictureUtil = pictureUtil;
    }

    @Override
    public Optional<Picture> getCarPreview(Long carId) {
        List<Picture> previewCandidates = this.sqlPictureRepository.getCarPreviews(carId);
        return this.pictureUtil.getPreviewPictureFromCandidates(previewCandidates);
    }

    @Override
    public Picture getManufacturerLogo(Long manufacturerId) {
        Picture picture = new Picture();

        Query query = super.getEntityManager()
                           .createQuery("SELECT manufacturer.logo " +
                                        "FROM Manufacturer AS manufacturer " +
                                        "WHERE manufacturer.id = :manufacturerId");

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
        log.info("Saving or editing Picture: {}", picture.toString());
        this.sqlPictureRepository.save(picture);
    }

    @Override
    public void updateGalleryPosition(Picture picture) {
        EntityManager entityManager = super.getEntityManager();
        // we need to detach the picture from the persistence context so that when there are 2 pictures for
        // the same car with the same galleryPosition they don't try to override each other
        entityManager.detach(picture);
        Query query = entityManager.createQuery("UPDATE Picture " +
                                                "SET galleryPosition = :galleryPosition " +
                                                "WHERE id = :id " +
                                                "AND car = :car");

        query.setParameter("galleryPosition", picture.getGalleryPosition());
        query.setParameter(ID_FIELD, picture.getId());
        query.setParameter("car", picture.getCar());
        query.executeUpdate();
    }

    @Override
    public List<Picture> getPaginated(int firstResult, int limit) {
        Query query = super.getEntityManager()
                           .createQuery("FROM Picture AS picture " +
                                        "ORDER BY picture.id");

        query.setFirstResult(firstResult);
        query.setMaxResults(limit);

        return query.getResultList();
    }
}
