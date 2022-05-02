package com.hhistory.data.dao.inmemory.impl;

import com.hhistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.hhistory.data.dao.inmemory.InMemoryDAO;
import com.hhistory.data.model.car.CarInternetContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.hhistory.data.dao.sql.SqlCarInternetContentRepository.CAR_INTERNET_CONTENT_REPOSITORY;
import static com.hhistory.data.model.car.CarInternetContentType.REVIEW_ARTICLE;
import static com.hhistory.data.model.car.CarInternetContentType.VIDEO;

/**
 * {@link CarInternetContent} {@link InMemoryDAO}
 * <p>
 * Created by gonzalo on 11/4/16.
 */
@Component
@EnableScheduling
@Slf4j
public class InMemoryCarInternetContentDAOImpl implements InMemoryCarInternetContentDAO {

    private CrudRepository<CarInternetContent, Long> sqlCarInternetContentRepository;
    private List<CarInternetContent>                 carInternetContents;

    @Inject
    public InMemoryCarInternetContentDAOImpl(@Named(CAR_INTERNET_CONTENT_REPOSITORY)
                                                     CrudRepository<CarInternetContent, Long> sqlCarInternetContentRepository) {
        this.sqlCarInternetContentRepository = sqlCarInternetContentRepository;
    }

    @Scheduled(fixedDelayString = "${data.entities.inMemoryLoadDelay}")
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading CarInternetContent entities in memory");
        this.carInternetContents = new ArrayList<>();
        this.sqlCarInternetContentRepository.findAll()
                                            .forEach(carInternetContent -> this.carInternetContents.add(carInternetContent));
    }

    @Override
    public void loadEntityFromDB(Long id) {
        log.info("Loading CarInternetContent: {} entity in memory", id);
        CarInternetContent contentToReload = this.getById(id);
        sqlCarInternetContentRepository.findById(id)
                                       .ifPresentOrElse(dbContent -> {
                                                            if (Objects.nonNull(contentToReload)) {
                                                                int indexToReload = this.carInternetContents.indexOf(contentToReload);
                                                                this.carInternetContents.set(indexToReload, dbContent);
                                                            } else {
                                                                //we're loading a car that's not yet in memory because it has been just stored
                                                                this.carInternetContents.add(dbContent);
                                                            }
                                                        },
                                                        //removing a car from the memory that either never existed in the DB or has just been removed from it
                                                        () -> carInternetContents.remove(contentToReload));
    }

    @Override
    public void removeEntity(Long id) {
        log.info("Removing CarInternetContent: {} entity from the memory cache", id);
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

    @Override
    public List<CarInternetContent> getAllVideos() {
        return this.carInternetContents.stream()
                                       .filter(content -> content.getType().equals(VIDEO))
                                       .toList();
    }

    @Override
    public List<CarInternetContent> getVideosByCarId(Long carId) {
        return this.carInternetContents.stream()
                                       .filter(content -> content.getCar().getId().equals(carId) &&
                                                          content.getType().equals(VIDEO))
                                       .toList();
    }

    @Override
    public List<CarInternetContent> getReviewArticlesByCarId(Long carId) {
        return this.carInternetContents.stream()
                                       .filter(content -> content.getCar().getId().equals(carId) &&
                                                          content.getType().equals(REVIEW_ARTICLE))
                                       .toList();
    }
}
