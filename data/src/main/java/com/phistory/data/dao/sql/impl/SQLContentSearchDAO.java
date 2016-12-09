package com.phistory.data.dao.sql.impl;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.dao.SQLDAO;
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

import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hibernate.search.Search.getFullTextSession;

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

	/**
	 * Triggers the index process of the previously stored objects in the database
	 */
	public void hibernateSearchIndexPreviouslyStoredDatabaseRecords()
	{
		try {
			getFullTextSession(super.getCurrentSession()).createIndexer().startAndWait();
		} catch (InterruptedException ie) {
			log.error(ie.toString(), ie);
		}
	}
		
    /**
     * Search content with Hibernate Search
     * 
     * @param searchCommand
     * @return ContentSearchDto filled with the content entities to search
     */
	public List<GenericEntity> hibernateSearchSearchContent(SearchCommand searchCommand)
	{		
		EntityManager entityManager = super.createEntityManager();
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		
		entityManager.getTransaction().begin();

		// create native Lucene query using the query DSL alternatively you can write the Lucene query using the Lucene query parser
		// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
											   			 .buildQueryBuilder()
											   			 .forEntity(searchCommand.getEntityClass())
											   			 .get();
		
		Map<String, SimpleDataConditionCommand> conditionMap = searchCommand.getConditionMap();
		
		return conditionMap.keySet()
						   .stream()
				    	   .map(entityPropertyName -> {
								SimpleDataConditionCommand conditionCommand = conditionMap.get(entityPropertyName);

								Query luceneQuery = queryBuilder.keyword()
																.wildcard()
																.onField(entityPropertyName)
																.matching(conditionCommand.getConditionSingleValue().toString())
																.createQuery();

								//create a Hibernate Search query out of a Lucene one
								FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, searchCommand.getEntityClass());

								fullTextQuery = this.applySortingToSearchQuery(searchCommand, fullTextQuery);
								fullTextQuery.setFirstResult(searchCommand.getFirstResult());
								fullTextQuery.setProjection(searchCommand.getProjectedFields().toArray(new String[]{}));

								if (searchCommand.getMaxResults() > 0)
								{
									fullTextQuery.setMaxResults(searchCommand.getMaxResults());
								}

								List<GenericEntity> results = fullTextQuery.getResultList();

								entityManager.getTransaction().commit();
								entityManager.close();

								return results;
						   })
						   .findFirst()
						   .orElse(Collections.emptyList());
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
