package com.phistory.mvc.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Gonzalo Carbajosa on 3/06/17.
 */
@Data
@Builder
public class ContactUsMessageDTO {

    private String successMessage;
    private String errorMessage;
}
