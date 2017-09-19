package com.hhistory.data.model.util;

import com.hhistory.data.model.engine.EngineCylinderDisposition;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Created by Gonzalo Carbajosa on 19/09/17.
 */
public class EngineUtils {

    public static String buildToString(String code,
                                       Integer size,
                                       EngineCylinderDisposition cylinderDisposition,
                                       Integer numberOfCylinders,
                                       Integer maxPower) {
        if (StringUtils.isEmpty(code)) {
            StringBuilder sb = new StringBuilder();
            Optional.ofNullable(size)
                    .ifPresent(value -> sb.append(size.toString()).append(" - ")
                                          .append(cylinderDisposition)
                                          .append(numberOfCylinders));

            Optional.ofNullable(maxPower)
                    .ifPresent(value -> sb.append(" - ")
                                          .append(maxPower));
            return sb.toString();
        }
        return code;
    }
}
