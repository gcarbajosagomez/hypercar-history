package com.hhistory.data.query.command;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
public class SimpleDataConditionCommand {

    private EntityConditionType entityConditionType;
    private Object[] values;
    
    public Object getConditionSingleValue(){
        return this.values[0];
    }

    public enum EntityConditionType {

        BIGGER_THAN, BIGGER_OR_EQUAL_THAN, LOWER_THAN, LOWER_OR_EQUAL_THAN, BETWEEN, EQUAL, LIKE
    }
}
