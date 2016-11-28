package com.phistory.data.dao.inmemory;

import com.phistory.data.dao.InMemoryDAO;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.picture.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.phistory.data.model.picture.PictureType.*;
import static com.phistory.data.model.picture.PictureType.PREVIEW_PICTURE;

/**
 * {@link Picture} {@link InMemoryDAO}
 *
 * Created by gonzalo on 11/4/16.
 */
@Repository(value = InMemoryPictureDAO.BEAN_NAME)
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class InMemoryPictureDAO implements InMemoryDAO<Picture> {
    public static final String BEAN_NAME = "InMemoryPictureDAO";

    private static final int NUMBER_OF_CHUNKS_TO_LOAD_PICTURES = 20;

    @Autowired
    private com.phistory.data.dao.sql.impl.SQLPictureDAO sqlSQLPictureDAO;
    @Getter
    private List<Picture> pictures = new ArrayList<>();

    @Scheduled(fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading Picture entities in-memory");
        Long pictureCount = this.sqlSQLPictureDAO.count();

        Double chunkSizeDouble = (pictureCount.doubleValue() / NUMBER_OF_CHUNKS_TO_LOAD_PICTURES);
        chunkSizeDouble = Math.floor(chunkSizeDouble);
        int chunkSize = new Double(chunkSizeDouble).intValue();

        this.pictures = this.sqlSQLPictureDAO.getPaginated(0, chunkSize);

        for (int i = 2; i < NUMBER_OF_CHUNKS_TO_LOAD_PICTURES; i++) {
            this.getPictures().addAll(this.sqlSQLPictureDAO.getPaginated(chunkSize,
                                                                      chunkSizeDouble.intValue()));
            chunkSize = chunkSize + chunkSizeDouble.intValue();
        }

        this.getPictures().addAll(this.sqlSQLPictureDAO.getPaginated(chunkSize,
                                                                     pictureCount.intValue()));
    }

    /**
     * Get a {@link List} of {@link Picture#id} whose {@link Picture#car#id} matches the supplied {@code carId}
     *
     * @param carId
     * @return The resulting {@link List<Long>}
     */
    public List<Long> getPictureIdsByCarId(Long carId) {
        return this.pictures.stream()
                            .filter(picture -> picture.getCar() != null && picture.getCar().getId().equals(carId) && picture.getType().equals(PICTURE))
                            .sorted(Comparator.comparing(Picture::getGalleryPosition))
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

    /**
     * Get the {@link Picture} whose {@link Picture#id} matches the supplied {@code pictureId}
     *
     * @param pictureId
     * @return The {@link Picture} if found, null otherwise
     */
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
        return this.getPictures().stream()
                                 .filter(picture -> {
                                     Car car = picture.getCar();
                                     return picture.getType().equals(PREVIEW_PICTURE) && (car != null && car.getId().equals(carId));
                                 })
                                 .findFirst()
                                 .orElse(null);
    }
}
