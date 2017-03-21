package com.phistory.data.dao.inmemory.impl;

import com.phistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.phistory.data.dao.inmemory.InMemoryDAO;
import com.phistory.data.dao.sql.SqlCarInternetContentRepository;
import com.phistory.data.model.car.CarInternetContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.phistory.data.model.car.CarInternetContentType.REVIEW_ARTICLE;
import static com.phistory.data.model.car.CarInternetContentType.VIDEO;

/**
 * {@link CarInternetContent} {@link InMemoryDAO}
 * <p>
 * Created by gonzalo on 11/4/16.
 */
@Component(value = InMemoryCarInternetContentDAOImpl.BEAN_NAME)
@EnableScheduling
@Slf4j
public class InMemoryCarInternetContentDAOImpl implements InMemoryCarInternetContentDAO {
    public static final String BEAN_NAME = "inMemoryCarInternetContentDAO";

    private SqlCarInternetContentRepository sqlCarInternetContentRepository;
    private List<CarInternetContent> carInternetContents = new ArrayList<>();

    @Autowired
    public InMemoryCarInternetContentDAOImpl(SqlCarInternetContentRepository sqlCarInternetContentRepository) {
        this.sqlCarInternetContentRepository = sqlCarInternetContentRepository;
    }

    @Scheduled(initialDelayString = "${data.carInternetContents.inMemoryLoadDelay}", fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading CarInternetContent entities in memory");
        this.carInternetContents = new ArrayList<>();
        this.sqlCarInternetContentRepository.findAll()
                                            .iterator()
                                            .forEachRemaining(this.carInternetContents::add);
    }

    @Override
    public void loadEntityFromDB(Long id) {
        log.info("Loading CarInternetContent: " + id + " entity in memory");
        CarInternetContent contentToReload = this.getById(id);
        CarInternetContent dbContent = this.sqlCarInternetContentRepository.findOne(id);

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

    @Override
    public List<CarInternetContent> getEntities() {
        return this.carInternetContents;
    }

    public List<CarInternetContent> getAllVideos() {
        return this.carInternetContents.stream()
                                       .filter(content -> content.getType().equals(VIDEO))
                                       .collect(Collectors.toList());
    }

    public List<CarInternetContent> getVideosByCarId(Long carId) {
        return this.carInternetContents.stream()
                                       .filter(content -> content.getCar().getId().equals(carId) &&
                                                          content.getType().equals(VIDEO))
                                       .collect(Collectors.toList());
    }

    public List<CarInternetContent> getReviewArticlesByCarId(Long carId) {
        return this.carInternetContents.stream()
                                       .filter(content -> content.getCar().getId().equals(carId) &&
                                                          content.getType().equals(REVIEW_ARTICLE))
                                       .collect(Collectors.toList());
    }
}
