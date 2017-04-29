package com.phistory.data.dao.sql.impl;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.dao.sql.SqlDAO;
import com.phistory.data.model.GenericEntity;
import com.phistory.data.query.command.SimpleDataConditionCommand;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Gonzalo
 */
@Slf4j
public abstract class AbstractSqlDAO<TYPE extends GenericEntity> implements SqlDAO<TYPE> {

    @Getter
    @PersistenceContext
    private EntityManager entityManager;

    public AbstractSqlDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<TYPE> getByCriteria(SearchCommand searchCommand) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(searchCommand.getEntityClass());

        Root<TYPE> entityRoot = criteriaQuery.from(searchCommand.getEntityClass());

        if (searchCommand.getOrderByMap() != null) {
            criteriaQuery = this.processOrderByMap(searchCommand.getOrderByMap(), entityRoot, criteriaBuilder, criteriaQuery);
        } else if (searchCommand.getOrderByList() != null) {
            criteriaQuery = criteriaQuery.orderBy(searchCommand.getOrderByList());
        }

        if (searchCommand.getConditionMap() != null) {
            for (Predicate predicate : this.processConditionMap(searchCommand.getConditionMap(), entityRoot, criteriaBuilder)) {
                criteriaQuery = criteriaQuery.where(predicate);
            }
        }

        TypedQuery<TYPE> query = this.entityManager.createQuery(criteriaQuery);

        if (searchCommand.getMaxResults() > 0) {
            query.setMaxResults(searchCommand.getMaxResults());
        }

        if (searchCommand.getFirstResult() > 0) {
            query.setFirstResult(searchCommand.getFirstResult());
        }
        return query.getResultList();
    }

    private CriteriaQuery<?> processOrderByMap(Map<String, Boolean> orderByMap,
                                               Root<TYPE> entityRoot,
                                               CriteriaBuilder criteriaBuilder,
                                               CriteriaQuery<?> criteriaQuery) {
        List<Order> orders = new ArrayList<>();

        for (String entityProperty : orderByMap.keySet()) {
            if (orderByMap.get(entityProperty)) {
                orders.add(criteriaBuilder.asc(entityRoot.get(entityProperty)));
            } else {
                orders.add(criteriaBuilder.desc(entityRoot.get(entityProperty)));
            }
        }

        return criteriaQuery.orderBy(orders);
    }

    private List<Predicate> processConditionMap(Map<String, SimpleDataConditionCommand> conditionMap,
                                                Root<TYPE> entityRoot,
                                                CriteriaBuilder criteriaBuilder) {
        List<Predicate> conditionPredicates = new ArrayList<>();

        for (String entityProperty : conditionMap.keySet()) {
            SimpleDataConditionCommand conditionCommand = conditionMap.get(entityProperty);

            SimpleDataConditionCommand.EntityConditionType entityConditionType = conditionCommand.getEntityConditionType();

            Double doubleConditionValue;
            String stringConditionValue = "";

            Expression<Double> doubleComparingEx = null;

            try {
                if (conditionCommand.getConditionSingleValue() instanceof Number) {
                    doubleConditionValue = (Double) conditionCommand.getConditionSingleValue();
                    doubleComparingEx = criteriaBuilder.literal(doubleConditionValue);
                } else if (conditionCommand.getConditionSingleValue() instanceof String) {
                    stringConditionValue = (String) conditionCommand.getConditionSingleValue();
                }
            } catch (Exception e) {
                log.error(e.toString(), e);
            }

            switch (entityConditionType) {
                case EQUAL:
                    conditionPredicates.add(criteriaBuilder.equal(entityRoot.get(entityProperty),
                                                                  conditionCommand.getConditionSingleValue()));
                    break;
                case BIGGER_THAN:
                    conditionPredicates.add(criteriaBuilder.greaterThan(entityRoot.get(entityProperty)
                                                                                  .as(Double.class),
                                                                        doubleComparingEx));
                    break;
                case LOWER_THAN:
                    conditionPredicates
                            .add(criteriaBuilder.lessThan(entityRoot.get(entityProperty)
                                                                    .as(Double.class),
                                                          doubleComparingEx));
                    break;
                case LIKE:
                    conditionPredicates.add(criteriaBuilder.like(entityRoot.get(entityProperty)
                                                                           .as(String.class),
                                                                 "%" + stringConditionValue + "%"));
                    break;
                default:
                    break;
            }
        }

        return conditionPredicates;
    }

    /**
     * Get the current Hibernate {@link Session}
     *
     * @return
     */
    @Override
    public Session getCurrentSession() {
        return this.entityManager.unwrap(Session.class);
    }
}
