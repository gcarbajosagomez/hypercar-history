package com.phistory.mvc.cms.service.crud;

import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import org.springframework.validation.BindingResult;

/**
 * Created by Gonzalo Carbajosa on 6/05/17.
 */
public interface CrudService {

    CrudOperationDTO saveOrEditEntity(EditFormCommand editFormCommand,
                                      BindingResult result);

    CrudOperationDTO deleteEntity(EditFormCommand editFormCommand,
                                  BindingResult result);
}
