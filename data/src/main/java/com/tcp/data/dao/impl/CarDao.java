package com.tcp.data.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcp.data.command.SearchCommand;
import com.tcp.data.dao.generic.Dao;
import com.tcp.data.model.car.Car;
import com.tcp.data.query.command.SimpleDataConditionCommand;
import com.tcp.data.query.command.SimpleDataConditionCommand.EntityConditionType;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class CarDao extends Dao<Car, Long>
{		
    @Override
    public List<Car> getAll()
    {
        List<Car> cars = null;        
        
        SearchCommand searchCommand = new SearchCommand(Car.class,
        												null,
        												null,
        												null,
        												null,
        												0,
        												0);
        cars = getByCriteria(searchCommand);
        
        return cars;        
    }    
   
    public List<Car> getAllOrdered(SearchCommand searchCommand)
    {
        List<Car> cars = null;        
                  
        cars = getByCriteria(searchCommand);
        return cars;        
    }

    @Override
    public Car getById(Long carId)
    {
        Car car = null;
       
        Map<String, SimpleDataConditionCommand> conditionMap = new LinkedHashMap<String, SimpleDataConditionCommand>();
        Object[] values = {new Double(carId)};
        conditionMap.put("id", new SimpleDataConditionCommand((EntityConditionType.EQUAL), values));

        SearchCommand searchCommand = new SearchCommand(Car.class,
        												null,
        												conditionMap,
        												null,
        												null,
        												0,
        												0);
        
        List<Car> results = getByCriteria(searchCommand);
        
        if (!results.isEmpty())
        {
        	car = results.get(0);
        } 
        
        return car;
    }

    public List<Car> getByManufacturerId(Long manufacturerId)
    {
        List<Car> cars = null;        
        
        Map<String, SimpleDataConditionCommand> conditionMap = new LinkedHashMap<String, SimpleDataConditionCommand>();
        Object[] values = {new Double(manufacturerId)};
        conditionMap.put("manufacturer", new SimpleDataConditionCommand((EntityConditionType.EQUAL), values));

        Map<String, Boolean> orderByMap = new LinkedHashMap<String, Boolean>();
        orderByMap.put("productionStartDate", Boolean.TRUE);

        SearchCommand searchCommand = new SearchCommand(Car.class,
        												null,
        												conditionMap,
        												null,
        												orderByMap,
        												0,
        												0);

        cars = getByCriteria(searchCommand);
        
        return cars;        
    }    
    
	@SuppressWarnings("unchecked")
	public List<Car> getDistinctModelsWithId()
	{
    	List<Car> models = null;    	
    	
    	Query q = getCurrentSession().createQuery("SELECT DISTINCT car.model AS model, car.id AS id"
                    					       + " FROM Car AS car"
                    						   + " ORDER BY car.productionStartDate ASC");
    		
    	q.setResultTransformer(new AliasToBeanResultTransformer(Car.class));
    	models = q.list();
       
        return models;        
    }
	
	public Car getByPictureId(Long pictureId)
	{
		Query q = getCurrentSession().createQuery("SELECT car.model AS model, car.manufacturer as manufacturer"
			       							   + " FROM Car AS car, Picture AS picture, Manufacturer as manufacturer"
			       							   + " WHERE picture.id = :pictureId"
			       							   + " AND picture.car.id = car.id"
			       							   + " AND car.manufacturer.id = manufacturer.id");

		q.setLong("pictureId", pictureId);
		q.setResultTransformer(new AliasToBeanResultTransformer(Car.class));
		
		return (Car) q.uniqueResult();
	}
}
