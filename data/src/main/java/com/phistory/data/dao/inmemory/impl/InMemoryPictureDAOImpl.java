package com.phistory.data.dao.inmemory.impl;

import com.phistory.data.dao.inmemory.InMemoryDAO;
import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import com.phistory.data.dao.sql.SqlPictureDAO;
import com.phistory.data.dao.sql.SqlPictureRepository;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.picture.Picture;
import com.phistory.data.model.util.PictureUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

/**
 * {@link Picture} {@link InMemoryDAO}
 * <p>
 * Created by gonzalo on 11/4/16.
 */
@Component(value = InMemoryPictureDAOImpl.BEAN_NAME)
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class InMemoryPictureDAOImpl implements InMemoryPictureDAO {
    public static final String BEAN_NAME = "InMemoryPictureDAOImpl";

    private static final int NUMBER_OF_CHUNKS_TO_LOAD_PICTURES = 10;

    private SqlPictureRepository sqlPictureRepository;
    private SqlPictureDAO        sqlPictureDAO;
    private List<Picture> pictures = new ArrayList<>();

    @Inject
    public InMemoryPictureDAOImpl(SqlPictureRepository sqlPictureRepository,
                                  SqlPictureDAO sqlPictureDAO) {
        this.sqlPictureRepository = sqlPictureRepository;
        this.sqlPictureDAO = sqlPictureDAO;
    }

    @Transactional
    @Scheduled(initialDelayString = "${data.pictures.inMemoryLoadDelay}", fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading Picture entities in-memory");
        Long pictureCount = this.sqlPictureRepository.count();

        Double chunkSizeDouble = (pictureCount.doubleValue() / NUMBER_OF_CHUNKS_TO_LOAD_PICTURES);
        chunkSizeDouble = Math.floor(chunkSizeDouble);
        int chunkSize = new Double(chunkSizeDouble).intValue();

        this.pictures = this.sqlPictureDAO.getPaginated(0, chunkSize);

        for (int i = 2; i < NUMBER_OF_CHUNKS_TO_LOAD_PICTURES; i++) {
            this.pictures.addAll(this.sqlPictureDAO.getPaginated(chunkSize,
                                                                 chunkSizeDouble.intValue()));
            chunkSize = chunkSize + chunkSizeDouble.intValue();
        }

        this.pictures.addAll(this.sqlPictureDAO.getPaginated(chunkSize,
                                                             pictureCount.intValue()));
    }

    @Override
    public void loadEntityFromDB(Long id) {
        log.info("Loading Picture: " + id + " entity in memory");
        Picture pictureToReload = this.getById(id);
        Picture dbPicture = this.sqlPictureRepository.findOne(id);

        if (Objects.nonNull(dbPicture)) {
            if (Objects.nonNull(pictureToReload)) {
                int indexToReload = this.pictures.indexOf(pictureToReload);
                this.pictures.set(indexToReload, dbPicture);
            } else {
                //we're loading a picture that's not yet in memory because it has been just stored
                //a BLOB object that has been just stored needs to be refreshed before its content can be read for some reason
                this.sqlPictureDAO.getCurrentSession().refresh(dbPicture);
                this.pictures.add(dbPicture);
            }
        } else {
            //removing a picture from the memory that either never existed in the DB or has just been removed from it
            this.pictures.remove(pictureToReload);
        }
    }

    @Override
    public void removeEntity(Long id) {
        log.info("Removing Picture: " + id + " entity from the memory cache");
        this.pictures.stream()
                     .filter(picture -> picture.getId().equals(id))
                     .findFirst()
                     .ifPresent(this.pictures::remove);
    }

    /**
     * Get a {@link List} of {@link Picture#id} whose {@link Picture#car#id} matches the supplied {@code carId}
     *
     * @param carId
     * @return The resulting {@link List<Long>}
     */
    public List<Long> getIdsByCarId(Long carId) {
        return this.pictures.stream()
                            .filter(picture -> Objects.nonNull(picture.getCar()) && picture.getCar().getId().equals(carId))
                            .sorted(comparing(Picture::getGalleryPosition, nullsFirst(naturalOrder())))
                            .map(Picture::getId)
                            .collect(Collectors.toList());
    }

    /**
     * Get a {@link List} of {@link Picture}s whose {@link Picture#car#id} matches the supplied {@code carId}
     *
     * @param carId
     * @return The resulting {@link List<Picture>}
     */
    public List<Picture> getByCarId(Long carId) {
        return this.pictures.stream()
                            .filter(picture -> Objects.nonNull(picture.getCar()) && picture.getCar().getId().equals(carId))
                            .sorted(comparing(Picture::getGalleryPosition, nullsFirst(naturalOrder())))
                            .collect(Collectors.toList());
    }

    /**
     * Get all the {@link Picture#id}s
     *
     * @return the {@link List} of {@link Picture} ids
     */
    public List<Long> getAllIds() {
        return this.pictures.stream()
                            .map(Picture::getId)
                            .collect(Collectors.toList());
    }

    @Override
    public Picture getById(Long pictureId) {
        return this.pictures.stream()
                            .filter(picture -> picture.getId().equals(pictureId))
                            .findFirst()
                            .orElse(null);
    }

    @Override
    public List<Picture> getEntities() {
        return this.pictures;
    }

    /**
     * Get the preview {@link Picture} for the supplied {@code carId}
     *
     * @param carId
     * @return
     */
    public Picture getCarPreview(Long carId) {
        List<Picture> previewCandidates =
                this.pictures.stream()
                             .filter(picture -> {
                                 Car car = picture.getCar();
                                 return ((Objects.nonNull(car) && car.getId().equals(carId)) && picture.getEligibleForPreview());
                             })
                             .collect(Collectors.toList());

        return PictureUtil.getPreviewPictureFromCandidates(previewCandidates);
    }
}
