package com.phistory.data.dao.sql.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.dao.SQLDAO;
import com.phistory.data.dto.ContentSearchDto;
import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.engine.Engine;
import com.phistory.data.query.command.SimpleDataConditionCommand;
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

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
@Slf4j
public class SQLContentSearchDAO extends SQLDAO<GenericEntity, Long>
{		
    @Override
    public List<GenericEntity> getAll()
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
     * Search content with Hibernate Search
     * 
     * @param searchCommand
     * @return ContentSearchDto filled with the content entities to search
     */
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
           
            query = this.applySortingToSearchQuery(searchCommand, query);
            query.setFirstResult(searchCommand.getFirstResult());
			query.setProjection(searchCommand.getProjectedFields().toArray(new String[]{}));
            
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
    		for (int i = 0; i < orderByList.size(); i++)
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
            List<SortField> sortFields = orderByMap.keySet()
                                                   .stream()
                                                   .map((orderBy) -> new SortField(orderBy, Type.STRING))
                                                   .collect(Collectors.toList());

            Sort sort = new Sort(sortFields.toArray(new SortField[]{}));
			searchQuery.setSort(sort);
    	}
    	
    	return searchQuery;    	
    }
}
