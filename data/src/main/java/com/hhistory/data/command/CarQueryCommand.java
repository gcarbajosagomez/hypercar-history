package com.hhistory.data.command;

import com.hhistory.data.model.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Gonzalo Carbajosa on 25/12/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CarQueryCommand {

    private Long carId;
    private String modelName;
    private Long engineId;
    private Manufacturer manufacturer;
    private Boolean visible;
}
