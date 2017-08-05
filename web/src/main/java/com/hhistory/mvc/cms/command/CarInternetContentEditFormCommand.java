package com.hhistory.mvc.cms.command;

import com.hhistory.mvc.cms.form.CarInternetContentForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Command class to save or edit a {@link CarInternetContentForm}
 *
 * @author gonzalo
 */
@Data
@AllArgsConstructor
public class CarInternetContentEditFormCommand {

    @Valid
    @NotEmpty
    private List<CarInternetContentForm> editForms;

    public CarInternetContentEditFormCommand() {
        this.editForms = new ArrayList<>();
    }

    public EditFormCommand adapt() {
        return new CarInternetContentEditFormCommandAdapter(this);
    }
}
