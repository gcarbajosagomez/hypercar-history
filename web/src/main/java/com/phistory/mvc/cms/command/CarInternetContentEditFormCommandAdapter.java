package com.phistory.mvc.cms.command;

import com.phistory.mvc.cms.form.CarInternetContentForm;
import com.phistory.mvc.cms.form.EditForm;

import java.util.List;
import java.util.Optional;

/**
 * Adapts {@link CarInternetContentEditFormCommand}s to {@link EditFormCommand}s
 * <p>
 * Created by Gonzalo Carbajosa on 21/05/17.
 */
public class CarInternetContentEditFormCommandAdapter implements EditFormCommand {

    private CarInternetContentEditFormCommand carInternetContentEditFormCommand;

    public CarInternetContentEditFormCommandAdapter(CarInternetContentEditFormCommand carInternetContentEditFormCommand) {
        this.carInternetContentEditFormCommand = carInternetContentEditFormCommand;
    }

    @Override
    public EditForm getEditForm() {
        return Optional.ofNullable(this.carInternetContentEditFormCommand.getEditForms().get(0))
                       .map(CarInternetContentForm::adapt)
                       .orElse(null);
    }

    @Override
    public void setEditForm(EditForm form) {
    }

    public void setEditForms(List<CarInternetContentForm> forms) {
        this.carInternetContentEditFormCommand.setEditForms(forms);
    }

    public List<CarInternetContentForm> getEditForms() {
        return this.carInternetContentEditFormCommand.getEditForms();
    }
}
