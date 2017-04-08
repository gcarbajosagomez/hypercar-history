package com.phistory.data.dao.inmemory.impl;

import com.phistory.data.command.CarQueryCommand;
import com.phistory.data.dao.inmemory.InMemoryCarDAO;
import com.phistory.data.dao.inmemory.InMemoryDAO;
import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import com.phistory.data.dao.sql.SqlCarRepository;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.picture.Picture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
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
@Component(value = InMemoryCarDAOImpl.BEAN_NAME)
@EnableScheduling
@Slf4j
public class InMemoryCarDAOImpl implements InMemoryCarDAO {
    public static final String BEAN_NAME = "inMemoryCarDAO";

    private InMemoryPictureDAO inMemoryInMemoryPictureDAO;
    private SqlCarRepository   sqlCarRepository;
    private List<Car> cars = new ArrayList<>();

    @Inject
    public InMemoryCarDAOImpl(InMemoryPictureDAO inMemoryPictureDAO, SqlCarRepository sqlCarRepository) {
        this.inMemoryInMemoryPictureDAO = inMemoryPictureDAO;
        this.sqlCarRepository = sqlCarRepository;
    }

    @Scheduled(initialDelayString = "${data.cars.inMemoryLoadDelay}", fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading Car entities in memory");
        this.cars = new ArrayList<>();
        this.sqlCarRepository.findAll()
                             .iterator()
                             .forEachRemaining(this.cars::add);
    }

    @Override
    public void loadEntityFromDB(Long id) {
        log.info("Loading Car: " + id + " entity in memory");
        Car carToReload = this.getById(id);
        Car dbCar = this.sqlCarRepository.findOne(id);

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
        return this.inMemoryInMemoryPictureDAO.getEntities()
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

    @Override
    public List<Car> getEntities() {
        return this.cars;
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
