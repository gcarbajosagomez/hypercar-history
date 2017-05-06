package com.phistory.mvc.cms.service.impl;

import com.phistory.data.dao.inmemory.InMemoryCarDAO;
import com.phistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.phistory.data.dao.inmemory.InMemoryManufacturerDAO;
import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import com.phistory.data.dao.sql.SqlCarInternetContentRepository;
import com.phistory.data.dao.sql.SqlPictureRepository;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.data.model.picture.Picture;
import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import com.phistory.mvc.cms.service.EntityManagementService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

/**
 * Created by Gonzalo Carbajosa on 3/12/16.
 */
@Component
public class EntityManagementServiceImpl implements EntityManagementService {
    private InMemoryCarDAO                  inMemoryCarDAO;
    private InMemoryPictureDAO              inMemoryPictureDAO;
    private SqlPictureRepository            sqlPictureRepository;
    private InMemoryCarInternetContentDAO   inMemoryCarInternetContentDAO;
    private SqlCarInternetContentRepository sqlCarInternetContentRepository;
    private InMemoryManufacturerDAO         inMemoryManufacturerDAO;

    @Inject
    public EntityManagementServiceImpl(InMemoryCarDAO inMemoryCarDAO,
                                       InMemoryPictureDAO inMemoryPictureDAO,
                                       SqlPictureRepository sqlPictureRepository,
                                       InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO,
                                       SqlCarInternetContentRepository sqlCarInternetContentRepository,
                                       InMemoryManufacturerDAO inMemoryManufacturerDAO) {
        this.inMemoryCarDAO = inMemoryCarDAO;
        this.inMemoryPictureDAO = inMemoryPictureDAO;
        this.sqlPictureRepository = sqlPictureRepository;
        this.inMemoryCarInternetContentDAO = inMemoryCarInternetContentDAO;
        this.sqlCarInternetContentRepository = sqlCarInternetContentRepository;
        this.inMemoryManufacturerDAO = inMemoryManufacturerDAO;
    }

    @Override
    public void reloadEntities(EntityManagementLoadCommand entityManagementLoadCommand) {
        Long carId = entityManagementLoadCommand.getCarId();
        Long manufacturerId = entityManagementLoadCommand.getManufacturerId();

        switch (entityManagementLoadCommand.getQueryType()) {
            case RELOAD_CARS:
                if (Objects.nonNull(carId)) {
                    this.inMemoryCarDAO.loadEntityFromDB(carId);
                } else {
                    this.inMemoryCarDAO.loadEntitiesFromDB();
                }
                break;
            case REMOVE_CAR:
                this.inMemoryCarDAO.removeEntity(carId);
                break;
            case RELOAD_PICTURES:
                if (Objects.nonNull(carId)) {
                    List<Picture> picturesToReload = this.sqlPictureRepository.getByCarId(carId);
                    picturesToReload.forEach(picture -> this.inMemoryPictureDAO.loadEntityFromDB(picture.getId()));
                } else {
                    this.inMemoryPictureDAO.loadEntitiesFromDB();
                }
                break;
            case REMOVE_PICTURE:
                this.inMemoryPictureDAO.removeEntity(entityManagementLoadCommand.getPictureId());
                break;
            case RELOAD_CAR_INTERNET_CONTENTS:
                if (Objects.nonNull(carId)) {
                    List<CarInternetContent> contentsToReload = this.sqlCarInternetContentRepository.getByCarId(carId);
                    contentsToReload.forEach(content -> this.inMemoryCarInternetContentDAO.loadEntityFromDB(content.getId()));
                } else {
                    this.inMemoryCarInternetContentDAO.loadEntitiesFromDB();
                }
                break;
            case REMOVE_CAR_INTERNET_CONTENTS:
                this.inMemoryCarInternetContentDAO.removeEntity(entityManagementLoadCommand.getCarInternetContentId());
                break;
            case RELOAD_MANUFACTURERS:
                if (Objects.nonNull(manufacturerId)) {
                    this.inMemoryManufacturerDAO.loadEntityFromDB(manufacturerId);
                } else {
                    this.inMemoryManufacturerDAO.loadEntitiesFromDB();
                }
                break;
            case REMOVE_MANUFACTURERS:
                this.inMemoryManufacturerDAO.removeEntity(entityManagementLoadCommand.getManufacturerId());
                break;
        }
    }
}
