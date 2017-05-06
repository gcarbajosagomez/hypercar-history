package com.phistory.mvc.cms.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.phistory.data.model.car.Car;
import com.phistory.data.model.transmission.TransmissionType;

/**
 * Transmission form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransmissionEditForm implements EditForm {

    private Long             id;
    private TransmissionType type;
    private Integer          numOfGears;
    private Car              car;
}
