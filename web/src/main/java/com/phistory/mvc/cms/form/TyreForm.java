package com.phistory.mvc.cms.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.phistory.data.model.tyre.TyreTrain;

/**
 * Tyre form
 *
 * @author Gonzalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TyreForm
{
    private Long id;
    private Long width;
    private Long profile;
    private Long rimDiameter;
    private TyreTrain train;
}
