package com.hhistory.mvc.cms.service.impl;

import com.hhistory.data.dao.PictureDAO;
import com.hhistory.data.dao.inmemory.InMemoryCarDAO;
import com.hhistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.hhistory.data.dao.inmemory.InMemoryManufacturerDAO;
import com.hhistory.data.dao.sql.SqlCarInternetContentRepository;
import com.hhistory.data.dao.sql.SqlPictureRepository;
import com.hhistory.data.model.car.CarInternetContent;
import com.hhistory.mvc.cms.command.EntityManagementLoadCommand;
import com.hhistory.mvc.cms.service.EntityManagementService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Objects;

import static com.hhistory.data.dao.sql.SqlPictureDAO.SQL_PICTURE_DAO;

/**
 * Created by Gonzalo Carbajosa on 3/12/16.
 */
@Component
public class EntityManagementServiceImpl implements EntityManagementService {
    private InMemoryCarDAO                  inMemoryCarDAO;
    private PictureDAO                      pictureDAO;
    private SqlPictureRepository            sqlPictureRepository;
    private InMemoryCarInternetContentDAO   inMemoryCarInternetContentDAO;
    private SqlCarInternetContentRepository sqlCarInternetContentRepository;
    private InMemoryManufacturerDAO         inMemoryManufacturerDAO;

    @Inject
    public EntityManagementServiceImpl(InMemoryCarDAO inMemoryCarDAO,
                                       @Named(SQL_PICTURE_DAO) PictureDAO pictureDAO,
                                       SqlPictureRepository sqlPictureRepository,
                                       InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO,
                                       SqlCarInternetContentRepository sqlCarInternetContentRepository,
                                       InMemoryManufacturerDAO inMemoryManufacturerDAO) {
        this.inMemoryCarDAO = inMemoryCarDAO;
        this.pictureDAO = pictureDAO;
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
                /*if (Objects.nonNull(carId)) {
                    List<Picture> picturesToReload = this.sqlPictureRepository.getByCarId(carId);
                    picturesToReload.forEach(picture -> this.pictureDAO.loadEntityFromDB(picture.getId()));
                } else {
                    this.pictureDAO.loadEntitiesFromDB();
                }*/
                break;
            case REMOVE_PICTURE:
                //this.pictureDAO.removeEntity(entityManagementLoadCommand.getPictureId());
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
