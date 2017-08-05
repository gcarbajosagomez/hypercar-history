package com.hhistory.mvc.cms.form;

import com.hhistory.data.model.Language;
import com.hhistory.data.model.car.Car;
import com.hhistory.data.model.car.CarInternetContent;
import com.hhistory.data.model.car.CarInternetContentType;
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
public class CarInternetContentForm {

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

    public EditForm adapt() {
        return new CarInternetContentFormAdapter(this);
    }
}
