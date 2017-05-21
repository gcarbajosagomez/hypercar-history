package com.phistory.mvc.cms.dto;

import com.phistory.data.model.GenericEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO to hold the result of a CRUD operation for a single entity
 * <p>
 * Created by Gonzalo Carbajosa on 14/04/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrudOperationDTO {

    private String successMessage;
    private List<String> errorMessages;
    private GenericEntity entity;

    public void addErrorMessage(String errorMessage) {
        if (Objects.isNull(this.errorMessages)) {
            this.errorMessages = new ArrayList<>();
        }
        this.errorMessages.add(errorMessage);
    }

    public void addErrorMessages(List<String> errorMessages) {
        if (Objects.isNull(this.errorMessages)) {
            this.errorMessages = new ArrayList<>();
        }
        this.errorMessages.addAll(errorMessages);
    }
}
