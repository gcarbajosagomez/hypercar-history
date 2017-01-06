package com.phistory.mvc.cms.service;

import com.phistory.data.dao.inmemory.InMemoryCarDAO;
import com.phistory.data.dao.inmemory.InMemoryCarInternetContentDAO;
import com.phistory.data.dao.inmemory.InMemoryManufacturerDAO;
import com.phistory.data.dao.inmemory.InMemoryPictureDAO;
import com.phistory.data.dao.sql.impl.SQLCarInternetContentDAO;
import com.phistory.data.dao.sql.impl.SQLManufacturerDAO;
import com.phistory.data.dao.sql.impl.SQLPictureDAO;
import com.phistory.data.model.Manufacturer;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.data.model.picture.Picture;
import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

/**
 * Created by Gonzalo Carbajosa on 3/12/16.
 */
@Component
public class EntityManagementService {
    private InMemoryCarDAO                inMemoryCarDAO;
    private InMemoryPictureDAO            inMemoryPictureDAO;
    private SQLPictureDAO                 sqlPictureDAO;
    private InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO;
    private SQLCarInternetContentDAO      sqlCarInternetContentDAO;
    private InMemoryManufacturerDAO       inMemoryManufacturerDAO;

    @Inject
    public EntityManagementService(InMemoryCarDAO inMemoryCarDAO,
                                   InMemoryPictureDAO inMemoryPictureDAO,
                                   SQLPictureDAO sqlPictureDAO,
                                   InMemoryCarInternetContentDAO inMemoryCarInternetContentDAO,
                                   SQLCarInternetContentDAO sqlCarInternetContentDAO,
                                   InMemoryManufacturerDAO inMemoryManufacturerDAO) {
        this.inMemoryCarDAO = inMemoryCarDAO;
        this.inMemoryPictureDAO = inMemoryPictureDAO;
        this.sqlPictureDAO = sqlPictureDAO;
        this.inMemoryCarInternetContentDAO = inMemoryCarInternetContentDAO;
        this.sqlCarInternetContentDAO = sqlCarInternetContentDAO;
        this.inMemoryManufacturerDAO = inMemoryManufacturerDAO;
    }

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
                    List<Picture> picturesToReload = this.sqlPictureDAO.getByCarId(carId);
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
                    List<CarInternetContent> contentsToReload = this.sqlCarInternetContentDAO.getByCarId(carId);
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
