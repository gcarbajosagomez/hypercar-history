package com.hhistory.data.dao.inmemory.impl;

import com.hhistory.data.dao.inmemory.InMemoryDAO;
import com.hhistory.data.dao.inmemory.InMemoryPictureDAO;
import com.hhistory.data.dao.sql.SqlPictureDAO;
import com.hhistory.data.dao.sql.SqlPictureRepository;
import com.hhistory.data.model.car.Car;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.data.model.util.PictureUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.hhistory.data.dao.inmemory.impl.InMemoryPictureDAOImpl.IN_MEMORY_PICTURE_DAO;
import static java.util.Comparator.*;

/**
 * {@link Picture} {@link InMemoryDAO}
 * <p>
 * Created by gonzalo on 11/4/16.
 */
@Component(value = IN_MEMORY_PICTURE_DAO)
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class InMemoryPictureDAOImpl implements InMemoryPictureDAO {

    public static final String IN_MEMORY_PICTURE_DAO = "inMemoryPictureDAO";

    private static final int PICTURE_LOADING_NUMBER_OF_CHUNKS = 5;

    private SqlPictureRepository sqlPictureRepository;
    private SqlPictureDAO        sqlPictureDAO;
    private PictureUtil          pictureUtil;
    private List<Picture>        pictures;

    @Inject
    public InMemoryPictureDAOImpl(SqlPictureRepository sqlPictureRepository,
                                  SqlPictureDAO sqlPictureDAO,
                                  PictureUtil pictureUtil) {
        this.sqlPictureRepository = sqlPictureRepository;
        this.sqlPictureDAO = sqlPictureDAO;
        this.pictureUtil = pictureUtil;
    }

    @Transactional
    //@Scheduled(fixedDelayString = "${data.entities.inMemoryLoadDelay}")
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading Picture entities in-memory");
        Long pictureCount = this.sqlPictureRepository.count();

        int chunkSize = (int) (pictureCount.doubleValue() / PICTURE_LOADING_NUMBER_OF_CHUNKS);

        pictures = new ArrayList<>();
        IntStream.rangeClosed(0, PICTURE_LOADING_NUMBER_OF_CHUNKS)
                 .forEach(i -> {
                     int skipPosition = chunkSize * i;
                     this.pictures.addAll(this.sqlPictureDAO.getPaginated(skipPosition,
                                                                          chunkSize));
                 });
    }

    @Override
    public void loadEntityFromDB(Long id) {
        log.info("Loading Picture: {} entity in memory", id);
        Picture pictureToReload = this.getById(id);
        sqlPictureRepository.findById(id)
                            .ifPresentOrElse(dbPicture -> {
                                                 if (Objects.nonNull(pictureToReload)) {
                                                     int indexToReload = this.pictures.indexOf(pictureToReload);
                                                     this.pictures.set(indexToReload, dbPicture);
                                                 } else {
                                                     //we're loading a picture that's not yet in memory because it has been just stored
                                                     //a BLOB object that has been just stored needs to be refreshed before its content can be read for some reason
                                                     this.sqlPictureDAO.getCurrentSession().refresh(dbPicture);
                                                     this.pictures.add(dbPicture);
                                                 }
                                             },
                                             //removing a picture from the memory that either never existed in the DB or has just been removed from it
                                             () -> pictures.remove(pictureToReload));
    }

    @Override
    public void removeEntity(Long id) {
        log.info("Removing Picture: {} entity from the memory cache", id);
        this.pictures.parallelStream()
                     .filter(picture -> picture.getId().equals(id))
                     .findFirst()
                     .ifPresent(this.pictures::remove);
    }

    @Override
    public List<Picture> getEntities() {
        return this.pictures;
    }

    @Override
    public Picture getById(Long pictureId) {
        return this.pictures.parallelStream()
                            .filter(picture -> picture.getId().equals(pictureId))
                            .findFirst()
                            .orElse(null);
    }

    @Override
    public List<Long> getIdsByCarId(Long carId) {
        return this.getByCarId(carId)
                   .parallelStream()
                   .map(Picture::getId)
                   .toList();
    }

    @Override
    public List<Picture> getByCarId(Long carId) {
        return this.pictures.parallelStream()
                            .filter(picture -> Objects.nonNull(picture.getCar()) && picture.getCar().getId().equals(carId))
                            .sorted(comparing(Picture::getGalleryPosition, nullsFirst(naturalOrder())))
                            .toList();
    }

    @Override
    public List<Long> getAllIds() {
        return this.pictures.parallelStream()
                            .map(Picture::getId)
                            .toList();
    }

    @Override
    public List<Long> getAllPreviewIds(Long manufacturerId) {
        return this.pictures.parallelStream()
                            .filter(picture -> picture.getCar().getManufacturer().getId().equals(manufacturerId))
                            .filter(picture -> picture.getCar().getVisible())
                            .filter(Picture::getEligibleForPreview)
                            .map(Picture::getId)
                            .toList();
    }

    @Override
    public Optional<Picture> getCarPreview(Long carId) {
        List<Picture> previewCandidates =
                this.pictures.parallelStream()
                             .filter(picture -> {
                                 Car car = picture.getCar();
                                 return (Objects.nonNull(car) && car.getId().equals(carId)) && picture.getEligibleForPreview();
                             })
                             .toList();

        return this.pictureUtil.getPreviewPictureFromCandidates(previewCandidates);
    }
}
