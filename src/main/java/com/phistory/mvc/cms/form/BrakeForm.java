package com.phistory.mvc.cms.form;

import lombok.Data;

import com.tcp.data.model.brake.Brake.BrakeDiscMaterial;
import com.tcp.data.model.brake.CarBrakeTrain;

/**
 * Brake form
 *
 * @author Gonzalo
 */
@Data
public class BrakeForm
{
    private Long id;
    private Long discDiameter;
    private BrakeDiscMaterial discMaterial;
    private Long caliperNumOfPistons;
    private CarBrakeTrain train;
}
