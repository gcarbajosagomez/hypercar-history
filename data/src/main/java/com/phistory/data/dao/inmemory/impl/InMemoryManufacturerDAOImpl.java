package com.phistory.data.dao.inmemory.impl;

import com.phistory.data.dao.inmemory.InMemoryManufacturerDAO;
import com.phistory.data.dao.sql.SqlManufacturerDAO;
import com.phistory.data.dao.sql.SqlManufacturerRepository;
import com.phistory.data.model.Manufacturer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Gonzalo Carbajosa on 6/01/17.
 */
@Component(value = InMemoryManufacturerDAOImpl.BEAN_NAME)
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class InMemoryManufacturerDAOImpl implements InMemoryManufacturerDAO {
    public static final String BEAN_NAME = "inMemoryManufacturerInternetContentDAO";

    private SqlManufacturerRepository sqlManufacturerRepository;
    private List<Manufacturer> manufacturers = new ArrayList<>();

    @Autowired
    public InMemoryManufacturerDAOImpl(SqlManufacturerRepository sqlManufacturerRepository) {
        this.sqlManufacturerRepository = sqlManufacturerRepository;
    }

    @Scheduled(initialDelayString = "${data.manufacturers.inMemoryLoadDelay}", fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading Manufacturers entities in memory");
        this.manufacturers = new ArrayList<>();
        this.sqlManufacturerRepository.findAll()
                                      .iterator()
                                      .forEachRemaining(this.manufacturers::add);
    }

    @Override
    public void loadEntityFromDB(Long id) {
        log.info("Loading Manufacturer: " + id + " entity in memory");
        Manufacturer manufacturer = this.getById(id);
        Manufacturer dbContent = this.sqlManufacturerRepository.findOne(id);

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

    @Override
    public List<Manufacturer> getEntities() {
        return this.manufacturers;
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
