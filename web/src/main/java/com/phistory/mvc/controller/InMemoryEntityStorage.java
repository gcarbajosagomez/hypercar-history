package com.phistory.mvc.controller;

import com.phistory.data.dao.impl.CarDao;
import com.phistory.data.dao.impl.CarInternetContentDAO;
import com.phistory.data.dao.impl.PictureDao;
import com.phistory.data.model.Picture;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.mvc.command.PictureLoadCommand;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static com.phistory.data.model.Picture.PictureType.PREVIEW_PICTURE;

/**
 * In-memory storage for DB entities
 *
 * Created by gonzalo on 11/1/16.
 */
@Component
@Data
public class InMemoryEntityStorage {

    @Inject
    private CarInternetContentDAO carInternetContentDAO;
    @Inject
    private CarDao carDao;
    @Inject
    private PictureDao pictureDao;

    private List<Car> cars = new ArrayList<>();
    private List<CarInternetContent> carInternetContents = new ArrayList<>();
    private List<Picture> pictures = new ArrayList<>();

    /**
     * Load all the {@link CarInternetContent}s there are on the DB
     */
    public void loadCarInternetContents() {
        this.setCarInternetContents(this.carInternetContentDAO.getAllProjected());
    }

    /**
     * Load all the {@link Car}s there are on the DB
     */
    public void loadCars() {
        this.setCars(this.carDao.getAll());
    }

    /**
     * Load all the {@link Car} {@link Picture}s there are on the DB
     */
    public void loadPictures() {
        Long pictureCount = this.pictureDao.count();
        int numberOfChunks = 20;
        Double chunkSizeDouble = (pictureCount.doubleValue() / numberOfChunks);
        chunkSizeDouble = Math.floor(chunkSizeDouble);
        int chunkSize = new Double(chunkSizeDouble).intValue();

        this.setPictures(this.pictureDao.getPaginated(0, chunkSize));

        for(int i = 2; i < numberOfChunks; i++) {
            this.getPictures().addAll(this.pictureDao.getPaginated(chunkSize,
                                                                   chunkSizeDouble.intValue()));
            chunkSize = chunkSize + chunkSizeDouble.intValue();
        }

        this.getPictures().addAll(this.pictureDao.getPaginated(chunkSize,
                                                               pictureCount.intValue()));
    }

    /**
     * Get the {@link Car} whose {@link Car#id} matches the {@code id} supplied
     *
     * @param id
     * @return The {@link Car} found if any, null otherwise
     */
    public Car loadCarById(Long id) {
        return this.cars.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a {@link List<Car>} paginated and ordered by {@link Car#productionStartDate} desc
     *
     * @param paginationDTO
     * @return The resulting {@link List<Car>}
     */
    public List<Car> loadCarsBySearchCommand(CarsPaginationDto paginationDTO) {
        return this.cars.stream()
                .skip(paginationDTO.getFirstResult())
                .limit(paginationDTO.getCarsPerPage())
                .sorted((car1, car2) -> {
                    Calendar productionDate1 = car1.getProductionStartDate();
                    Calendar productionDate2 = car2.getProductionStartDate();
                    return productionDate1.compareTo(productionDate2);
                })
                .collect(Collectors.toList());
    }

    public Car getCarByPictureId(Long pictureId) {
        return this.pictures.stream()
                            .filter(picture -> picture.getId().equals(pictureId))
                            .map(Picture::getCar)
                            .findFirst()
                            .orElse(null);
    }

    /**
     * Get a {@link List<CarInternetContent>} whose {@link CarInternetContent#car#id} matches the supplied {@code carId}
     *
     * @param carId
     * @return The resulting {@link List<CarInternetContent>}
     */
    public List<CarInternetContent> getCarInternetContentsByCarId(Long carId) {
        return this.carInternetContents.stream()
                .filter(internetContent -> internetContent.getCar() != null && internetContent.getCar().getId().equals(carId))
                .collect(Collectors.toList());
    }

    /**
     * Get a {@link List} of {@link Picture#id} whose {@link Picture#car#id} matches the supplied {@code carId
     * }
     * @param carId
     * @return The resulting {@link List<Long>}
     */
    public List<Long> getPictureIdsByCarId(Long carId) {
        return this.pictures
                   .stream()
                   .filter(picture -> picture.getCar() != null && picture.getCar().getId().equals(carId))
                   .map(Picture::getId)
                   .collect(Collectors.toList());
    }

    /**
     * Load a {@link Picture} from the DB depending on the action being performed
     *
     * @param command
     * @return
     */
    public Picture loadPicture(PictureLoadCommand command)
    {
        Long pictureId = command.getPictureId();
        Long carId = command.getCarId();

        switch (command.getAction()) {
            case LOAD_CAR_PICTURE: {
                if (pictureId != null) {
                    this.findById(pictureId);
                }
            }
            case LOAD_CAR_PREVIEW: {
                if (carId != null) {
                    return this.pictures.stream()
                               .filter(picture -> picture.getType().equals(PREVIEW_PICTURE))
                               .filter(picture -> picture.getCar() != null && picture.getCar().getId().equals(carId))
                               .findFirst()
                               .orElse(null);
                }
            }
            case LOAD_MANUFACTURER_LOGO: {
                if (command.getManufacturerId() != null) {
                    return this.pictureDao.getManufacturerLogo(command.getManufacturerId());
                }
            }
            default: {
                if (command.getPictureId() != null) {
                    this.findById(pictureId);
                }
            }
        }

        return this.findById(pictureId);
    }

    /**
     * Get all the {@link Picture#id}s
     *
     * @return the {@link List} of {@link Picture} ids
     */
    public List<Long> getAllPictureIds() {
        return this.pictures.stream()
                            .map(Picture::getId)
                            .collect(Collectors.toList());
    }

    /**
     * Get the {@link Picture} from the supplied {@link List} whose {@link Picture#id} matches the supplied pictureID
     *
     * @param pictureId
     * @return The {@link Picture} if found, null otherwise
     */
    private Picture findById(Long pictureId) {
        return this.pictures.stream()
                            .filter(picture -> picture.getId().equals(pictureId))
                            .findFirst()
                            .orElse(null);
    }
}
