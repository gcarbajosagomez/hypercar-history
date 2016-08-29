package com.phistory.data.command;

import com.phistory.data.query.command.SimpleDataConditionCommand;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Selection;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class SearchCommand
{	
    private Class<?> entityClass;
    private Selection<?>[] selections;
    private Map<String, SimpleDataConditionCommand> conditionMap;
    private List<Order> orderByList;
    private Map<String, Boolean> orderByMap;
    private List<String> projectedFields;
    private int firstResult;
    private int maxResults;
}
