package com.phistory.mvc.cms.command;

import com.phistory.mvc.cms.form.EditForm;

/**
 * Created by Gonzalo Carbajosa on 29/04/17.
 */
public interface EditFormCommand {

    EditForm getEditForm();

    void setEditForm(EditForm form);
}
