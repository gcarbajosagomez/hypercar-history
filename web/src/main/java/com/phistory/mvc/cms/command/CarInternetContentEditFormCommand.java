package com.phistory.mvc.cms.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.EditForm;
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
public class CarInternetContentEditFormCommand implements EditFormCommand {

    @Valid
    @NotEmpty
    @JsonDeserialize(contentAs = CarInternetContentForm.class)
    private List<EditForm> editForms;

    public CarInternetContentEditFormCommand() {
        this.editForms = new ArrayList<>();
    }

    @Override
    public EditForm getEditForm() {
        if (this.editForms.isEmpty()) {
            return new CarInternetContentForm();
        }
        return this.editForms.get(0);
    }

    @Override
    public void setEditForm(EditForm form) {
        if (this.editForms.isEmpty()) {
            this.editForms = new ArrayList<>();
        }
        this.editForms.add(form);
    }
}
