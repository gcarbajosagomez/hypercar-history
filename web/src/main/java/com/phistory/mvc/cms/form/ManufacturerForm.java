package com.phistory.mvc.cms.form;

import com.phistory.mvc.cms.command.PictureEditCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Manufacturer form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerForm {

    private Long id;

    @NotEmpty(message = "NotEmpty.manufacturerForm.name")
    private String name;

    @NotEmpty(message = "The field above must not be blank.")
    private String nationality;

    private PictureEditCommand previewPictureEditCommand;
    private String             historyES;
    private String             historyEN;
}
