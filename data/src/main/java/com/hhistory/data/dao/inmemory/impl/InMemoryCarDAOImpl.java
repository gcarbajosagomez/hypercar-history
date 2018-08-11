package com.hhistory.data.dao.inmemory.impl;

import com.hhistory.data.command.CarQueryCommand;
import com.hhistory.data.dao.inmemory.InMemoryCarDAO;
import com.hhistory.data.dao.inmemory.InMemoryDAO;
import com.hhistory.data.dao.inmemory.InMemoryPictureDAO;
import com.hhistory.data.dao.sql.SqlCarRepository;
import com.hhistory.data.model.Manufacturer;
import com.hhistory.data.model.car.Car;
import com.hhistory.data.model.picture.Picture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@link Car} {@link InMemoryDAO}
 * <p>
 * Created by gonzalo on 11/4/16.
 */
@Component(InMemoryCarDAOImpl.IN_MEMORY_CAR_DAO)
@EnableScheduling
@Slf4j
public class InMemoryCarDAOImpl implements InMemoryCarDAO {

    public static final String IN_MEMORY_CAR_DAO = "inMemoryCarDAO";

    private InMemoryPictureDAO inMemoryPictureDAO;
    private SqlCarRepository   sqlCarRepository;
    private List<Car> cars;

    @Inject
    public InMemoryCarDAOImpl(InMemoryPictureDAO inMemoryPictureDAO,
                              SqlCarRepository sqlCarRepository) {
        this.inMemoryPictureDAO = inMemoryPictureDAO;
        this.sqlCarRepository = sqlCarRepository;
    }

    @Scheduled(fixedDelayString = "${data.entities.inMemoryLoadDelay}")
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
        log.info("Loading Car: {} entity in memory", id);
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
        log.info("Removing Car: {} entity from the memory cache", id);
        this.cars.stream()
                 .filter(car -> car.getId().equals(id))
                 .findFirst()
                 .ifPresent(this.cars::remove);
    }

    @Override
    public Car getCarByPictureId(Long pictureId) {
        return this.inMemoryPictureDAO.getEntities()
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

    @Override
    public Car getByQueryCommand(CarQueryCommand queryCommand) {
        return this.getByQueryCommandOrderedByProductionStartDate(queryCommand)
                   .stream()
                   .findFirst()
                   .orElse(null);
    }

    @Override
    public List<Car> getByQueryCommandOrderedByProductionStartDate(CarQueryCommand queryCommand) {
        Manufacturer manufacturer = queryCommand.getManufacturer();
        Boolean visible = queryCommand.getVisible();
        Long carId = queryCommand.getCarId();
        Long engineId = queryCommand.getEngineId();
        String modelName = queryCommand.getModelName();

        return this.cars.stream()
                        .filter(car -> !Optional.ofNullable(manufacturer).isPresent() ||
                                       car.getManufacturer().equals(manufacturer))
                        .filter(car -> !Optional.ofNullable(visible).isPresent() ||
                                       car.getVisible() == visible)
                        .filter(car -> !Optional.ofNullable(carId).isPresent() ||
                                       car.getId().equals(carId))
                        .filter(car -> !Optional.ofNullable(engineId).isPresent() ||
                                       car.getEngine().getId().equals(engineId))
                        .filter(car -> !Optional.ofNullable(modelName).isPresent() ||
                                       car.getNormalizedModelName().equals(Car.normalizeModelName(modelName)))
                        .sorted(Comparator.comparing(Car::getProductionStartDate))
                        .collect(Collectors.toList());
    }

    @Override
    public long countVisibleCars(Manufacturer manufacturer) {
        return this.cars.stream()
                        .filter(car -> car.getManufacturer().equals(manufacturer))
                        .filter(Car::getVisible)
                        .count();
    }
}
