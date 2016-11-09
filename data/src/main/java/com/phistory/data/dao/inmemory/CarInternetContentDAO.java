package com.phistory.data.dao.inmemory;

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
import java.util.stream.Collectors;

/**
 * {@link CarInternetContent} {@link InMemoryDAO}
 *
 * Created by gonzalo on 11/4/16.
 */
@Repository(value = CarInternetContentDAO.BEAN_NAME)
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class CarInternetContentDAO implements InMemoryDAO<CarInternetContent> {
    public static final String BEAN_NAME = "inMemoryCarInternetContentDAO";

    private static final int LOAD_ENTITIES_INITIAL_DELAY = 20000;

    @Autowired
    private com.phistory.data.dao.sql.impl.CarInternetContentDAO carInternetContentDAO;
    @Getter
    private List<CarInternetContent> carInternetContents = new ArrayList<>();

    @Scheduled(initialDelay = LOAD_ENTITIES_INITIAL_DELAY, fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading CarInternetContent entities in-memory");
        this.carInternetContents = this.carInternetContentDAO.getAllProjected();
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
