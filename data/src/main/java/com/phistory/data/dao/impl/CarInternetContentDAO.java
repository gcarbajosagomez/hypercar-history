package com.phistory.data.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.phistory.data.dao.generic.Dao;
import com.phistory.data.model.car.CarInternetContent;

/**
 * Data Access Object class for {@link CarInternetContent}s
 * 
 * @author gonzalo
 *
 */
@Transactional
@Repository
public class CarInternetContentDAO extends Dao<CarInternetContent, Long>
{
	@Override
	public List<CarInternetContent> getAll()
	{
	    return  super.getCurrentSession().createQuery("FROM CarInternetContent").list();
	}

	@Override
	public CarInternetContent getById(Long id)
	{
        Query q = getCurrentSession().createQuery("FROM CarInternetContent AS internetContent"
                                               + " WHERE internetContent.id = :id");
        q.setParameter("id", id);
		CarInternetContent carInternetContent = (CarInternetContent) q.uniqueResult();
        
        return carInternetContent; 
	}
	
	/**
	 * Get all the {@link CarInternetContent}s whose {@link CarInternetContent#car#id} matches the 
	 * carId supplied
	 * 
	 * @param carId
	 * @return The {@link List<CarInternetContent>} if everything went well, null otherwise
	 */
	public List<CarInternetContent> getByCarId(Long carId)
	{
        Query q = getCurrentSession().createQuery("FROM CarInternetContent AS internetContent"
                                               + " WHERE internetContent.car.id = :carId");
        q.setParameter("carId", carId);
		List<CarInternetContent> carInternetContents = q.list();
        
        return carInternetContents; 
	}

}
