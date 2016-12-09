package com.phistory.data.dao.inmemory;

import com.phistory.data.dao.InMemoryDAO;
import com.phistory.data.dao.sql.impl.SQLCarInternetContentDAO;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.car.CarInternetContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@link CarInternetContent} {@link InMemoryDAO}
 *
 * Created by gonzalo on 11/4/16.
 */
@Repository(value = InMemoryCarInternetContentDAO.BEAN_NAME)
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class InMemoryCarInternetContentDAO implements InMemoryDAO<CarInternetContent, Long> {
    public static final String BEAN_NAME = "inMemoryCarInternetContentDAO";

    private com.phistory.data.dao.sql.impl.SQLCarInternetContentDAO sqlCarInternetContentDAO;
    @Getter
    private List<CarInternetContent> carInternetContents = new ArrayList<>();

    @Autowired
    public InMemoryCarInternetContentDAO(SQLCarInternetContentDAO sqlCarInternetContentDAO) {
        this.sqlCarInternetContentDAO = sqlCarInternetContentDAO;
    }

    @Scheduled(initialDelayString = "${data.carInternetContents.inmemoryLoadDelay}", fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading CarInternetContent entities in memory");
        this.carInternetContents = this.sqlCarInternetContentDAO.getAllProjected();
    }

    @Override
    public void loadEntityFromDB(Long id) {
        log.info("Loading CarInternetContent: " + id + " entity in memory");
        CarInternetContent contentToReload = this.getById(id);
        CarInternetContent dbContent = this.sqlCarInternetContentDAO.getById(id);

        if (Objects.nonNull(dbContent)) {
            if (Objects.nonNull(contentToReload)) {
                int indexToReload = this.carInternetContents.indexOf(contentToReload);
                this.carInternetContents.set(indexToReload, dbContent);

            } else {
                //we're loading a car that's not yet in memory because it has been just stored
                this.carInternetContents.add(dbContent);
            }
        } else {
            //removing a car from the memory that either never existed in the DB or has just been removed from it
            this.carInternetContents.remove(contentToReload);
        }
    }

    @Override
    public void removeEntity(Long id) {
        log.info("Removing CarInternetContent: " + id + " entity from the memory cache");
        this.carInternetContents.stream()
                                .filter(content -> content.getId().equals(id))
                                .findFirst()
                                .ifPresent(this.carInternetContents::remove);
    }

    @Override
    public CarInternetContent getById(Long id) {
        return this.carInternetContents.stream()
                                       .filter(content -> content.getId().equals(id))
                                       .findFirst()
                                       .orElse(null);
    }

    /**
     * Get a {@link List <CarInternetContent>} whose {@link CarInternetContent#car#id} matches the supplied {@code carId}
     *
     * @param carId
     * @return The resulting {@link List<CarInternetContent>}
     */
    public List<CarInternetContent> getByCarId(Long carId) {
        return this.carInternetContents.stream()
                                       .filter(internetContent -> internetContent.getCar() != null && internetContent.getCar().getId().equals(carId))
                                       .collect(Collectors.toList());
    }
}
