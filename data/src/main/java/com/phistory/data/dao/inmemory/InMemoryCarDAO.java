package com.phistory.data.dao.inmemory;

import com.phistory.data.dao.InMemoryDAO;
import com.phistory.data.dao.sql.impl.SQLCarDAO;
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
import java.util.Objects;
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
public class InMemoryCarDAO implements InMemoryDAO<Car, Long> {
    public static final String BEAN_NAME = "inMemoryCarDAO";

    private static final int LOAD_ENTITIES_INITIAL_DELAY = 15000;

    private InMemoryPictureDAO inMemoryInMemoryPictureDAO;
    private com.phistory.data.dao.sql.impl.SQLCarDAO sqlCarDAO;
    private List<Car> cars;

    @Autowired
    public InMemoryCarDAO(InMemoryPictureDAO inMemoryInMemoryPictureDAO, SQLCarDAO sqlCarDAO) {
        this.inMemoryInMemoryPictureDAO = inMemoryInMemoryPictureDAO;
        this.sqlCarDAO = sqlCarDAO;
    }

    @Scheduled(initialDelay = LOAD_ENTITIES_INITIAL_DELAY, fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading Car entities in memory");
        this.cars = this.sqlCarDAO.getAll();
    }

    @Override
    public void loadEntityFromDB(Long id) {
        log.info("Loading Car: " + id + " entity in memory");
        Car carToReload = this.getById(id);
        Car dbCar = this.sqlCarDAO.getById(id);

        if (Objects.nonNull(dbCar)) {
            if (Objects.nonNull(carToReload)) {
                this.cars.stream()
                         .filter(car -> car.getId().equals(dbCar.getId()))
                         .findFirst()
                         .ifPresent(car -> car = dbCar);

            } else {
                //we're loading a car that's not yet in memory because it has been just stored
                this.cars.add(dbCar);
            }
        } else {
            //removing a car from the memory that either never existed in the DB or has just been removed from it
            this.cars.remove(carToReload);
        }
    }

    @Override
    public void removeEntity(Long id) {
        log.info("Removing Car: " + id + " entity from the memory cache");
        this.cars.stream()
                 .filter(car -> car.getId().equals(id))
                 .findFirst()
                 .ifPresent(this.cars::remove);
    }

    public Car getCarByPictureId(Long pictureId) {
        return this.inMemoryInMemoryPictureDAO.getPictures()
                                              .stream()
                                              .filter(picture -> picture.getId().equals(pictureId))
                                              .map(Picture::getCar)
                                              .findFirst()
                                              .orElse(null);
    }

    @Override
    public Car getById(Long id) {
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
