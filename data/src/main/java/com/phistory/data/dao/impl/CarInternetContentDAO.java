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
	@SuppressWarnings("unchecked")
	@Override
	public List<CarInternetContent> getAll()
	{
		List<CarInternetContent> carInternetContents = null;        
	    carInternetContents = super.getCurrentSession().createQuery("FROM CarInternetContent").list();
	        
	    return carInternetContents;     
	}

	@Override
	public CarInternetContent getById(Long id)
	{
		CarInternetContent carInternetContent = null;
        
        Query q = getCurrentSession().createQuery("FROM CarInternetContent AS internetContent"
                                               + " WHERE internetContent.id = :id");
        q.setParameter("id", id);
        carInternetContent = (CarInternetContent) q.uniqueResult();
        
        return carInternetContent; 
	}
	
	/**
	 * Get all the {@link CarInternetContent}s whose {@link CarInternetContent#car#id} matches the 
	 * carId supplied
	 * 
	 * @param carId
	 * @return The {@link List<CarInternetContent>} if everything went well, null otherwise
	 */
	@SuppressWarnings("unchecked")
	public List<CarInternetContent> getByCarId(Long carId)
	{
		List<CarInternetContent> carInternetContents = null;
        
        Query q = getCurrentSession().createQuery("FROM CarInternetContent AS internetContent"
                                               + " WHERE internetContent.car.id = :carId");
        q.setParameter("carId", carId);
        carInternetContents = q.list();
        
        return carInternetContents; 
	}

}
