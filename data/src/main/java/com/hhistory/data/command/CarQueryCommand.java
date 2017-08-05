package com.hhistory.data.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Gonzalo Carbajosa on 25/12/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarQueryCommand {

    private Long carId;
    private String modelName;
    private Long engineId;
}
