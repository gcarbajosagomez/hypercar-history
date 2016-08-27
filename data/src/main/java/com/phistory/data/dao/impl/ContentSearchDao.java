package com.tcp.data.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;

import lombok.extern.slf4j.Slf4j;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcp.data.command.SearchCommand;
import com.tcp.data.dao.generic.Dao;
import com.tcp.data.dto.ContentSearchDto;
import com.tcp.data.model.GenericObject;
import com.tcp.data.model.car.Car;
import com.tcp.data.model.engine.Engine;
import com.tcp.data.query.command.SimpleDataConditionCommand;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
@Slf4j
public class ContentSearchDao extends Dao<GenericObject, Long>
{		
    @Override
    public List<GenericObject> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Engine getById(Long id)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void hibernateSearchIndexPreviouslyStoredDatabaseRecords()
    {
    	super.hibernateSearchIndexPreviouslyStoredDatabaseRecords();
    }
	
    /**
     * Search content identified by a given text
     * 
     * @param searchText
     * @return
     */
    public List<GenericObject> searchContent(String searchText)
    {
        List<GenericObject> searchResults = null;
        
        Map<String, SimpleDataConditionCommand> conditionMap = new LinkedHashMap<String, SimpleDataConditionCommand>();
        Object[] values = {searchText};
        conditionMap.put("model", new SimpleDataConditionCommand((SimpleDataConditionCommand.EntityConditionType.LIKE), values));

        SearchCommand searchCommand = new SearchCommand(Car.class,
        												null,
        												conditionMap,
        												null,
        												null,
        												0,
        												0);
        searchResults = getByCriteria(searchCommand);
        
        return searchResults;        
    }
		
    /**
     * Search content with Hibernate Search
     * 
     * @param searchCommand
     * @return ContentSearchDto filled with the content entities to search
     */
	@SuppressWarnings("unchecked")
	public ContentSearchDto hibernateSearchSearchContent(SearchCommand searchCommand)
	{		
		int totalResults = 0;
		
		EntityManager entityManager = createEntityManager();
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		
		entityManager.getTransaction().begin();

		// create native Lucene query using the query DSL alternatively you can write the Lucene query using the Lucene query parser
		// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
											   			 .buildQueryBuilder()
											   			 .forEntity(searchCommand.getEntityClass())
											   			 .get();
		
		Map<String, SimpleDataConditionCommand> conditionMap = searchCommand.getConditionMap();
		List<Object> results = new ArrayList<>();
		
		for (String entityProperty : conditionMap.keySet())
        {
            SimpleDataConditionCommand conditionCommand = conditionMap.get(entityProperty);
		
            Query luceneQuery = queryBuilder.keyword()
								  .onField(entityProperty)
								  .matching(conditionCommand.getConditionSingleValue())
								  .createQuery();			
             
            //create a Hibernate Search query out of a Lucene one
            FullTextQuery query = fullTextEntityManager.createFullTextQuery(luceneQuery, searchCommand.getEntityClass());
           
            query = applySortingToSearchQuery(searchCommand, query);
            query.setFirstResult(searchCommand.getFirstResult());
            
            if (searchCommand.getMaxResults() > 0)
            {
            	query.setMaxResults(searchCommand.getMaxResults());
            }

            totalResults = query.getResultSize();
            results = query.getResultList();

            entityManager.getTransaction().commit();
            entityManager.close();
            
            break;
        }
		
		ContentSearchDto contentSearchDto = new ContentSearchDto(results, totalResults);
		
		return contentSearchDto;
	}
    
    /**
     * Apply sorting to a Hibernate Search query
     * 
     * @param searchCommand
     * @param searchQuery
     * @return
     */
    private FullTextQuery applySortingToSearchQuery(SearchCommand searchCommand, FullTextQuery searchQuery)
    {
    	List<Order> orderByList = searchCommand.getOrderByList();
    	
    	if (orderByList != null && !orderByList.isEmpty())
    	{
    		for (int i=0; i < orderByList.size(); i++)
    		{
    			if (i > 0)
    			{
    				log.warn("Only 1 order can be applied to a given Hibernate Search query. Therefore Order field: " + orderByList.get(i).getExpression().getAlias() + " won't be applied");
    				break;
    			}
    			
    			Sort sort = new Sort(new SortField(orderByList.get(0).toString(), Type.STRING));
        		searchQuery.setSort(sort);
    		}    		
    	}
    	
    	Map<String, Boolean> orderByMap = searchCommand.getOrderByMap();
    	
    	if (orderByMap != null && !orderByMap.isEmpty())
    	{
    		for (String orderBy : orderByMap.keySet())
    		{
    			Sort sort = new Sort(new SortField(orderBy, Type.STRING));    		
    			searchQuery.setSort(sort);
    		}
    	}
    	
    	return searchQuery;    	
    }
}
