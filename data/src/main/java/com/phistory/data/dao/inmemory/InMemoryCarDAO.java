package com.phistory.data.dao.inmemory;

import com.phistory.data.command.CarQueryCommand;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@link Car} {@link InMemoryDAO}
 * <p>
 * Created by gonzalo on 11/4/16.
 */
@Repository(value = InMemoryCarDAO.BEAN_NAME)
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class InMemoryCarDAO implements InMemoryDAO<Car, Long> {
    public static final String BEAN_NAME = "inMemoryCarDAO";

    private InMemoryPictureDAO                       inMemoryInMemoryPictureDAO;
    private com.phistory.data.dao.sql.impl.SQLCarDAO sqlCarDAO;
    private List<Car> cars = new ArrayList<>();

    @Autowired
    public InMemoryCarDAO(InMemoryPictureDAO inMemoryInMemoryPictureDAO, SQLCarDAO sqlCarDAO) {
        this.inMemoryInMemoryPictureDAO = inMemoryInMemoryPictureDAO;
        this.sqlCarDAO = sqlCarDAO;
    }

    @Scheduled(initialDelayString = "${data.cars.inMemoryLoadDelay}", fixedDelay = LOAD_ENTITIES_DELAY)
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
                int indexToReload = this.cars.indexOf(carToReload);
                this.cars.set(indexToReload, dbCar);
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

    public Car getByQueryCommand(CarQueryCommand queryCommand) {
        return this.cars.stream()
                        .filter(car -> Objects.isNull(queryCommand.getCarId()) || car.getId().equals(queryCommand.getCarId()))
                        .filter(car -> Objects.isNull(queryCommand.getEngineId()) ||
                                       car.getEngine().getId().equals(queryCommand.getEngineId()))
                        .filter(car -> Objects.isNull(queryCommand.getModelName()) ||
                                       car.getNormalizedModelName().equals(Car.normalizeModelName(queryCommand.getModelName())))
                        .findFirst()
                        .orElse(null);
    }

    /**
     * Get all {@link Car}s whose {@link Car#visible} is true ordered by their {@link Car#productionStartDate} descending
     *
     * @return
     */
    public List<Car> getAllVisibleOrderedByProductionStartDate() {
        return this.cars.stream()
                        .filter(Car::getVisible)
                        .sorted(Comparator.comparing(Car::getProductionStartDate))
                        .collect(Collectors.toList());
    }

    public long countVisibleCars() {
        return this.cars.stream()
                        .filter(Car::getVisible)
                        .count();
    }
}
