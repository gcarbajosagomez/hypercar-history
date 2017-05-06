package com.phistory.mvc.cms.form;

import com.phistory.data.model.Language;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.car.CarInternetContent;
import com.phistory.data.model.car.CarInternetContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

/**
 * {@link CarInternetContent} edit form
 *
 * @author gonzalo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarInternetContentForm implements EditForm {

    private Long id;

    @NotEmpty
    private String link;

    @NotNull
    private CarInternetContentType type;

    private DateTime addedDate;

    @NotNull
    private Language contentLanguage;

    @NotNull
    private Car car;
}
