package com.phistory.data.dao.inmemory;

import com.phistory.data.dao.InMemoryDAO;
import com.phistory.data.model.picture.Picture;
import com.phistory.data.model.car.Car;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link Car} {@link InMemoryDAO}
 *
 * Created by gonzalo on 11/4/16.
 */
@Repository(value = InMemoryCarDAO.BEAN_NAME)
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class InMemoryCarDAO implements InMemoryDAO<Car> {
    public static final String BEAN_NAME = "inMemoryCarDAO";

    private static final int LOAD_ENTITIES_INITIAL_DELAY = 15000;

    @Autowired
    private InMemoryPictureDAO inMemoryInMemoryPictureDAO;
    @Autowired
    private com.phistory.data.dao.sql.impl.SQLCarDAO sqlCarDAO;
    private List<Car> cars;

    @Scheduled(initialDelay = LOAD_ENTITIES_INITIAL_DELAY, fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading Car entities in-memory");
        this.cars = this.sqlCarDAO.getAll();
    }

    public Car getCarByPictureId(Long pictureId) {
        return this.inMemoryInMemoryPictureDAO.getPictures()
                                              .stream()
                                              .filter(picture -> picture.getId().equals(pictureId))
                                              .map(Picture::getCar)
                                              .findFirst()
                                              .orElse(null);
    }

    /**
     * Get the {@link Car} whose {@link Car#id} matches the {@code id} supplied
     *
     * @param id
     * @return The {@link Car} found if any, null otherwise
     */
    public Car loadCarById(Long id) {
        return this.cars.stream()
                        .filter(car -> car.getId().equals(id))
                        .findFirst()
                        .orElse(null);
    }

    /**
     * Get all {@link Car}s ordered by their {@link Car#productionStartDate} descending
     *
     * @return
     */
    public List<Car> getAllOrderedByProductionStartDate() {
        return this.cars.stream()
                        .sorted(Comparator.comparing(Car::getProductionStartDate))
                        .collect(Collectors.toList());
    }
}
