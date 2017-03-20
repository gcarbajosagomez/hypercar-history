package com.phistory.data.dao.sql.impl;

import com.phistory.data.dao.sql.SqlCarInternetContentDAO;
import com.phistory.data.model.car.CarInternetContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static com.phistory.data.model.picture.Picture.CAR_ID_PROPERTY_NAME;

/**
 * Data Access Object class for {@link CarInternetContent}s
 *
 * @author gonzalo
 */
@Transactional
@Component
public class SqlCarInternetContentDAOImpl extends SqlDAOImpl<CarInternetContent> implements SqlCarInternetContentDAO {

    @Autowired
    public SqlCarInternetContentDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * Get all the {@link CarInternetContent}s whose {@link CarInternetContent#car#id} matches the
     * carId supplied
     *
     * @param carId
     * @return The {@link List<CarInternetContent>} if everything went well, null otherwise
     */
    @Override
    public List<CarInternetContent> getByCarId(Long carId) {
        Query query = super.getEntityManager()
                           .createQuery("FROM CarInternetContent AS internetContent"
                                        + " WHERE internetContent.car.id = :carId");

        query.setParameter(CAR_ID_PROPERTY_NAME, carId);

        return query.getResultList();
    }

}
