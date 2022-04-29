package com.hhistory.data.dao.sql.impl;

import com.hhistory.data.command.SearchCommand;
import com.hhistory.data.dao.sql.SqlContentSearchDAO;
import com.hhistory.data.model.GenericEntity;
import com.hhistory.data.query.command.SimpleDataConditionCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Gonzalo
 */
@Transactional
@Component
@Slf4j
public class SqlContentSearchDAOImpl implements SqlContentSearchDAO {

    private EntityManager entityManager;

    @Inject
    public SqlContentSearchDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void indexPreviouslyStoredDatabaseRecords() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException ie) {
            log.error(ie.toString(), ie);
        }
    }

    @Override
    public List<GenericEntity> searchContent(SearchCommand searchCommand) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.entityManager);
        Class<?> entityClass = searchCommand.getEntityClass();

        // create native Lucene query using the query DSL alternatively you can write the Lucene query using the Lucene query parser
        // or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                                                         .buildQueryBuilder()
                                                         .forEntity(entityClass)
                                                         .get();

        Map<String, SimpleDataConditionCommand> conditionMap = searchCommand.getConditionMap();

        List<Query> queries = conditionMap.keySet()
                                          .stream()
                                          .map(entityPropertyName -> {
                                              SimpleDataConditionCommand conditionCommand = conditionMap.get(entityPropertyName);
                                              String queryTerm = conditionCommand.getConditionSingleValue().toString();

                                              return queryBuilder.keyword()
                                                                 .onField(entityPropertyName)
                                                                 .matching(queryTerm)
                                                                 .createQuery();
                                          })
                                          .toList();

        BooleanJunction bool = queryBuilder.bool();
        for (Query query : queries) {
            bool = bool.should(query);
        }

        Query luceneQuery = bool.createQuery();

        //create a Hibernate Search query out of a Lucene one
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery,
                                                                                entityClass);

        fullTextQuery = this.applySortingToSearchQuery(searchCommand, fullTextQuery);
        fullTextQuery.setFirstResult(searchCommand.getFirstResult());
        List<String> projectedFields = searchCommand.getProjectedFields();
        if (Objects.nonNull(projectedFields)) {
            fullTextQuery.setProjection(projectedFields.toArray(new String[] {}));
        }

        return (List<GenericEntity>) fullTextQuery.getResultList();

    }

    /**
     * Apply sorting to a Hibernate Search query
     *
     * @param searchCommand
     * @param searchQuery
     * @return
     */
    private FullTextQuery applySortingToSearchQuery(SearchCommand searchCommand, FullTextQuery searchQuery) {
        Map<String, Boolean> orderByMap = searchCommand.getOrderByMap();

        if (orderByMap != null && !orderByMap.isEmpty()) {
            List<SortField> sortFields = orderByMap.keySet()
                                                   .stream()
                                                   .map((orderBy) -> new SortField(orderBy, Type.LONG))
                                                   .toList();

            Sort sort = new Sort(sortFields.toArray(new SortField[] {}));
            searchQuery.setSort(sort);
        }

        return searchQuery;
    }
}
