package com.phistory.mvc.cms.command;

import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.EditForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public CarInternetContentForm getEditForm() {
        if (this.editForms.isEmpty()) {
            return new CarInternetContentForm();
        }
        return this.editForms.get(0);
    }
}
