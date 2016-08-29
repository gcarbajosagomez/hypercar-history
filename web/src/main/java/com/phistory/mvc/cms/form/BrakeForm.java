package com.phistory.mvc.cms.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.phistory.data.model.brake.BrakeDiscMaterial;
import com.phistory.data.model.brake.BrakeTrain;

/**
 * Brake form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrakeForm
{
    private Long id;
    private Long discDiameter;
    private BrakeDiscMaterial discMaterial;
    private Long caliperNumOfPistons;
    private BrakeTrain train;
}
