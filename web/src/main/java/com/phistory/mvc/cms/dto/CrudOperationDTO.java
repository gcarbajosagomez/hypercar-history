package com.phistory.mvc.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO to hold the result of a CRUD operation for a single entity
 *
 * Created by Gonzalo Carbajosa on 14/04/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrudOperationDTO {

    private String       successMessage;
    private String       errorMessage;
    private List<String> errorMessages;
    private Object       entity;
}
