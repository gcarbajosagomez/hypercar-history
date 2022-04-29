package com.hhistory.mvc.cms.service.impl;

import com.hhistory.data.dao.inmemory.InMemoryCarDAO;
import com.hhistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.hhistory.data.dao.inmemory.InMemoryManufacturerDAO;
import com.hhistory.data.dao.inmemory.InMemoryPictureDAO;
import com.hhistory.data.dao.sql.SqlCarInternetContentRepository;
import com.hhistory.data.dao.sql.SqlPictureRepository;
import com.hhistory.data.model.car.CarInternetContent;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.mvc.cms.command.EntityManagementLoadCommand;
import com.hhistory.mvc.cms.service.EntityManagementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

/**
 * Created by Gonzalo Carbajosa on 3/12/16.
 */
@AllArgsConstructor(onConstructor = @__(@Inject))
@Component
public class EntityManagementServiceImpl implements EntityManagementService {

    private InMemoryCarDAO                  inMemoryCarDAO;
    private InMemoryPictureDAO              inMemoryPictureDAO;
    private SqlPictureRepository            sqlPictureRepository;
    private InMemoryCarInternetContentDAO   inMemoryCarInternetContentDAO;
    private SqlCarInternetContentRepository sqlCarInternetContentRepository;
    private InMemoryManufacturerDAO         inMemoryManufacturerDAO;

    @Override
    public void reloadEntities(EntityManagementLoadCommand entityManagementLoadCommand) {
        Long carId = entityManagementLoadCommand.getCarId();
        Long manufacturerId = entityManagementLoadCommand.getManufacturerId();

        switch (entityManagementLoadCommand.getQueryType()) {
            case RELOAD_CARS -> {
                if (Objects.nonNull(carId)) {
                    this.inMemoryCarDAO.loadEntityFromDB(carId);
                } else {
                    this.inMemoryCarDAO.loadEntitiesFromDB();
                }
            }
            case REMOVE_CAR -> this.inMemoryCarDAO.removeEntity(carId);
            case RELOAD_PICTURES -> {
                if (Objects.nonNull(carId)) {
                    List<Picture> picturesToReload = this.sqlPictureRepository.getByCarId(carId);
                    picturesToReload.parallelStream()
                                    .map(Picture::getId)
                                    .forEach(inMemoryPictureDAO::loadEntityFromDB);
                } else {
                    this.inMemoryPictureDAO.loadEntitiesFromDB();
                }
            }
            case REMOVE_PICTURE -> this.inMemoryPictureDAO.removeEntity(entityManagementLoadCommand.getPictureId());
            case RELOAD_CAR_INTERNET_CONTENTS -> {
                if (Objects.nonNull(carId)) {
                    List<CarInternetContent> contentsToReload = this.sqlCarInternetContentRepository.getByCarId(carId);
                    contentsToReload.parallelStream()
                                    .map(CarInternetContent::getId)
                                    .forEach(inMemoryCarInternetContentDAO::loadEntityFromDB);
                } else {
                    this.inMemoryCarInternetContentDAO.loadEntitiesFromDB();
                }
            }
            case REMOVE_CAR_INTERNET_CONTENTS -> this.inMemoryCarInternetContentDAO.removeEntity(entityManagementLoadCommand.getCarInternetContentId());
            case RELOAD_MANUFACTURERS -> {
                if (Objects.nonNull(manufacturerId)) {
                    this.inMemoryManufacturerDAO.loadEntityFromDB(manufacturerId);
                } else {
                    this.inMemoryManufacturerDAO.loadEntitiesFromDB();
                }
            }
            case REMOVE_MANUFACTURERS -> this.inMemoryManufacturerDAO.removeEntity(entityManagementLoadCommand.getManufacturerId());
        }
    }
}
