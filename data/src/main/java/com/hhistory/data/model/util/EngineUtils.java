package com.hhistory.data.model.util;

import com.hhistory.data.model.engine.EngineCylinderDisposition;
import org.springframework.util.StringUtils;

import java.util.Objects;

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
            StringBuilder sb = new StringBuilder(size.toString());
            sb.append(" - ")
              .append(cylinderDisposition)
              .append(numberOfCylinders);

            if (Objects.nonNull(maxPower)) {
                sb.append(" - ")
                  .append(maxPower);
            }
            return sb.toString();
        }
        return code;
    }
}
