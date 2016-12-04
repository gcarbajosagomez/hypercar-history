package com.phistory.data.dao.inmemory;

import com.phistory.data.dao.InMemoryDAO;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.picture.Picture;
import com.phistory.data.model.util.PictureUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static java.util.Comparator.*;

/**
 * {@link Picture} {@link InMemoryDAO}
 *
 * Created by gonzalo on 11/4/16.
 */
@Repository(value = InMemoryPictureDAO.BEAN_NAME)
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class InMemoryPictureDAO implements InMemoryDAO<Picture, Long> {
    public static final String BEAN_NAME = "InMemoryPictureDAO";

    @Autowired
    private com.phistory.data.dao.sql.impl.SQLPictureDAO sqlPictureDAO;
    @Getter
    private List<Picture> pictures = new ArrayList<>();

    @Scheduled(fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading Picture entities in memory");
        this.pictures.addAll(this.sqlPictureDAO.getAll());
    }

    @Override
    public void loadEntityFromDB(Long id) {
        log.info("Loading Picture: " + id + " entity in memory");
        Picture pictureToReload = this.getById(id);
        Picture dbPicture = this.sqlPictureDAO.getById(id);

        if (Objects.nonNull(dbPicture)) {
            if (Objects.nonNull(pictureToReload)) {
                this.pictures.stream()
                             .filter(picture -> picture.getId().equals(dbPicture.getId()))
                             .findFirst()
                             .ifPresent(picture -> picture = dbPicture);

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
    public List<Long> getPictureIdsByCarId(Long carId) {
        return this.pictures.stream()
                            .filter(picture -> Objects.nonNull(picture.getCar()) && picture.getCar().getId().equals(carId))
                            .sorted(comparing(Picture::getGalleryPosition, nullsFirst(naturalOrder())))
                            .map(Picture::getId)
                            .collect(Collectors.toList());
    }

    /**
     * Get all the {@link Picture#id}s
     *
     * @return the {@link List} of {@link Picture} ids
     */
    public List<Long> getAllPictureIds() {
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

    /**
     * Get the preview {@link Picture} for the supplied {@code carId}
     *
     * @param carId
     * @return
     */
    public Picture getCarPreview(Long carId) {
        List<Picture> previewCandidates =
                this.getPictures().stream()
                                  .filter(picture -> {
                                        Car car = picture.getCar();
                                        return ((Objects.nonNull(car) && car.getId().equals(carId)) && picture.getEligibleForPreview());
                                  })
                                  .collect(Collectors.toList());

       return PictureUtil.getPreviewPictureFromCandidates(previewCandidates);
    }
}
