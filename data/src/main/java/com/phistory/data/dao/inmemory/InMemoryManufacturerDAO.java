package com.phistory.data.dao.inmemory;

import com.phistory.data.dao.InMemoryDAO;
import com.phistory.data.dao.sql.impl.SQLManufacturerDAO;
import com.phistory.data.model.Manufacturer;
import com.phistory.data.model.car.CarInternetContent;
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

/**
 * Created by Gonzalo Carbajosa on 6/01/17.
 */
@Repository(value = InMemoryManufacturerDAO.BEAN_NAME)
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class InMemoryManufacturerDAO implements InMemoryDAO<Manufacturer, Long> {
    public static final String BEAN_NAME = "inMemoryManufacturerInternetContentDAO";

    private SQLManufacturerDAO sqlManufacturerDAO;
    @Getter
    private List<Manufacturer> manufacturers = new ArrayList<>();

    @Autowired
    public InMemoryManufacturerDAO(SQLManufacturerDAO sqlManufacturerDAO) {
        this.sqlManufacturerDAO = sqlManufacturerDAO;
    }

    @Scheduled(initialDelayString = "${data.manufacturers.inMemoryLoadDelay}", fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading Manufacturers entities in memory");
        this.manufacturers = this.sqlManufacturerDAO.getAll();
    }

    @Override
    public void loadEntityFromDB(Long id) {
        log.info("Loading Manufacturer: " + id + " entity in memory");
        Manufacturer manufacturer = this.getById(id);
        Manufacturer dbContent = this.sqlManufacturerDAO.getById(id);

        if (Objects.nonNull(dbContent)) {
            if (Objects.nonNull(manufacturer)) {
                int indexToReload = this.manufacturers.indexOf(manufacturer);
                this.manufacturers.set(indexToReload, dbContent);

            } else {
                //we're loading a car that's not yet in memory because it has been just stored
                this.manufacturers.add(dbContent);
            }
        } else {
            //removing a car from the memory that either never existed in the DB or has just been removed from it
            this.manufacturers.remove(manufacturer);
        }
    }

    @Override
    public void removeEntity(Long id) {
        log.info("Removing Manufacturer: " + id + " entity from the memory cache");
        this.manufacturers.stream()
                          .filter(content -> content.getId().equals(id))
                          .findFirst()
                          .ifPresent(this.manufacturers::remove);
    }

    @Override
    public Manufacturer getById(Long id) {
        return this.manufacturers.stream()
                                 .filter(content -> content.getId().equals(id))
                                 .findFirst()
                                 .orElse(null);
    }

    public Manufacturer getByName(String manufacturerName) {
        return this.manufacturers.stream()
                                 .filter(inMemoryManufacturer -> inMemoryManufacturer.getName()
                                                                                     .toLowerCase()
                                                                                     .equals(manufacturerName.toLowerCase()))
                                 .findFirst()
                                 .orElse(null);
    }
}
