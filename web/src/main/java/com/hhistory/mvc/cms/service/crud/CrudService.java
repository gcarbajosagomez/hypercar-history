package com.hhistory.mvc.cms.service.crud;

import com.hhistory.mvc.cms.command.EditFormCommand;
import com.hhistory.mvc.cms.dto.CrudOperationDTO;
import org.springframework.validation.BindingResult;

/**
 * Created by Gonzalo Carbajosa on 6/05/17.
 */
public interface CrudService {

    CrudOperationDTO saveOrEditEntity(EditFormCommand editFormCommand,
                                      BindingResult result);

    CrudOperationDTO deleteEntity(EditFormCommand editFormCommand,
                                  BindingResult result);

    CrudOperationDTO addBindingResultErrors(BindingResult result,
                                            CrudOperationDTO crudOperationDTO);
}
