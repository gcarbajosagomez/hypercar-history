package com.phistory.data.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.model.GenericEntity;
import com.phistory.data.query.command.SimpleDataConditionCommand;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
@Slf4j
public abstract class SQLDAO<TYPE extends GenericEntity, IDENTIFIER>
{
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private HibernateEntityManagerFactory entityManagerFactory;
    
    /**
     * Get all the entities of a given type
     * 
     * @return The list of entities
     */
    public abstract List<TYPE> getAll();
    
    /**
     * Get an entity by id
     * 
     * @param id
     * @return The given entity
     */
    public abstract TYPE getById(IDENTIFIER id);

	public List<TYPE> getByCriteria(SearchCommand searchCommand)
    {
        CriteriaBuilder criteriaBuilder;
        CriteriaQuery criteriaQuery;
        
        EntityManager entityManager = createEntityManager();
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(searchCommand.getEntityClass());
            
        Root<TYPE> entityRoot = criteriaQuery.from(searchCommand.getEntityClass());
            
        if (searchCommand.getOrderByMap() != null)
        {
            criteriaQuery = this.processOrderByMap(searchCommand.getOrderByMap(), entityRoot, criteriaBuilder, criteriaQuery);
        }
        else if (searchCommand.getOrderByList() != null)
        {
        	criteriaQuery = criteriaQuery.orderBy(searchCommand.getOrderByList());
        }
            
        if (searchCommand.getConditionMap() != null)
        {
           for (Predicate predicate : this.processConditionMap(searchCommand.getConditionMap(), entityRoot, criteriaBuilder))
           {
        	   criteriaQuery = criteriaQuery.where(predicate);
           }
        }
            
        TypedQuery query = entityManager.createQuery(criteriaQuery);
        
        if (searchCommand.getMaxResults() > 0)
        {        	
        	query.setMaxResults(searchCommand.getMaxResults());
        }
        
        if (searchCommand.getFirstResult() > 0)
        {        	
        	query.setFirstResult(searchCommand.getFirstResult());
        }

        return query.getResultList();
    }
    
    /**
     * Saves or edits an entity
     * 
     * @param entity
     */
    public void saveOrEdit(TYPE entity)
    {
       if (entity != null)
       {
    	   Session session = this.getCurrentSession();
    	   session.clear();
    	   
    	   if (entity.getId() == null)
    	   {
    		   log.info("Saving new entity: " + entity.getFriendlyName());
    		   session.save(entity);
    	   } 
    	   else
    	   {
    		   log.info("Editing entity: " + entity.getFriendlyName());
    		   session.update(entity);
    	   }
       }        
    }
    
    /**
     * Deletes an entity
     * 
     * @param entity
     */
    public void delete(TYPE entity)
    {       	
       if (entity != null)
       {
           log.info("Deleting entity: " + entity.toString());
           Session session = getCurrentSession();
           session.clear();
           session.delete(entity);
       }        
    }
    
    /**
     * Creates an entity manager
     * 
     * @return
     */
    protected EntityManager createEntityManager()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager(sessionFactory.getAllClassMetadata());
       
        return entityManager;        
    }
    
    private CriteriaQuery<?> processOrderByMap(Map<String, Boolean> orderByMap,
    		                       Root<TYPE> entityRoot,
    		                       CriteriaBuilder criteriaBuilder,
    		                       CriteriaQuery<?> criteriaQuery)
    {
        List<Order> orders = new ArrayList<>();
        
        for (String entityProperty : orderByMap.keySet())
        {
            if (orderByMap.get(entityProperty))
            {
                orders.add(criteriaBuilder.asc(entityRoot.get(entityProperty)));
            }
            else
            {
                orders.add(criteriaBuilder.desc(entityRoot.get(entityProperty)));
            }
        }
        
        return criteriaQuery.orderBy(orders);
    }
    
    private List<Predicate> processConditionMap(Map<String,
            SimpleDataConditionCommand> conditionMap,
    											Root<TYPE> entityRoot,
    											CriteriaBuilder criteriaBuilder)
    {
        List<Predicate> conditionPredicates = new ArrayList<>();
        	
        for (String entityProperty : conditionMap.keySet())
        {
            SimpleDataConditionCommand conditionCommand = conditionMap.get(entityProperty);
                
            SimpleDataConditionCommand.EntityConditionType entityConditionType = conditionCommand.getEntityConditionType();
                
            Double doubleConditionValue;
            String stringConditionValue = "";
                
            Expression<Double> doubleComparingEx = null;
                
            try
            {
                if (conditionCommand.getConditionSingleValue() instanceof Number)
                {
                	doubleConditionValue = (Double) conditionCommand.getConditionSingleValue();
                	doubleComparingEx = criteriaBuilder.literal(doubleConditionValue);
                }
                else if (conditionCommand.getConditionSingleValue() instanceof String)
                {
                	stringConditionValue = (String) conditionCommand.getConditionSingleValue();
                }
            } 
            catch (Exception e)
            {
                log.error(e.toString(), e);
            }
                
            switch (entityConditionType)
            {
              case EQUAL:
                 conditionPredicates.add(criteriaBuilder.equal(entityRoot.get(entityProperty), conditionCommand.getConditionSingleValue()));
                 break;
              case BIGGER_THAN:
                 conditionPredicates.add(criteriaBuilder.greaterThan(entityRoot.get(entityProperty).as(Double.class), doubleComparingEx));
                 break;
              case LOWER_THAN:
                 conditionPredicates.add(criteriaBuilder.lessThan(entityRoot.get(entityProperty).as(Double.class), doubleComparingEx));
                 break;
              case LIKE:
                 conditionPredicates.add(criteriaBuilder.like(entityRoot.get(entityProperty).as(String.class), "%" + stringConditionValue + "%"));
                 break;
              default:
                 break;
           }
        }        
        
       return conditionPredicates;        
    }
    
    /**
     * Opens a new Hibernate session
     * 
     * @return
     */
    public Session openSession()
    {
        Session session = sessionFactory.openSession();
        session.setFlushMode(FlushMode.COMMIT);
        
        return session;
    }
    
    /**
     * Gets current Hibernate session
     * 
     * @return
     */
    public Session getCurrentSession()
    {
        Session session = sessionFactory.getCurrentSession();
        session.setFlushMode(FlushMode.COMMIT);     
        
        return session;
    }
    
    /**
     * Triggers the index process of the previously stored objects in the database
     */
    protected void hibernateSearchIndexPreviouslyStoredDatabaseRecords()
    {
    	try
    	{
    		FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
      
    		fullTextSession.createIndexer().startAndWait();
		}
    	catch (InterruptedException ie)
    	{
    		log.error(ie.toString(), ie);
		}
    }
}
