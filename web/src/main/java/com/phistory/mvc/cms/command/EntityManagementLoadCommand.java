package com.phistory.mvc.cms.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Gonzalo Carbajosa on 3/12/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityManagementLoadCommand {
    private EntityManagementQueryType queryType;
    private Long carId;
    private Long pictureId;
    private Long carInternetContentId;
    private Long manufacturerId;
}
