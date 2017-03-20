package com.phistory.data.command;

import com.phistory.data.query.command.SimpleDataConditionCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.Order;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Gonzalo
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCommand
{	
    private Class<?> entityClass;
    private Map<String, SimpleDataConditionCommand> conditionMap;
    private List<Order> orderByList;
    private Map<String, Boolean> orderByMap;
    private List<String> projectedFields;
    private int firstResult;
    private int maxResults;
}
