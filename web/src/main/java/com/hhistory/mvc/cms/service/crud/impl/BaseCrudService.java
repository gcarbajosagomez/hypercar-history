package com.hhistory.mvc.cms.service.crud.impl;

import com.hhistory.data.model.GenericEntity;
import com.hhistory.mvc.cms.dto.CrudOperationDTO;
import com.hhistory.mvc.cms.service.crud.CrudService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hhistory.mvc.cms.controller.CMSBaseController.ENTITY_CONTAINED_ERRORS_TEXT_SOURCE_KEY;
import static com.hhistory.mvc.cms.controller.CMSBaseController.ENTITY_EDITED_SUCCESSFULLY_TEXT_SOURCE_KEY;
import static com.hhistory.mvc.cms.controller.CMSBaseController.ENTITY_SAVED_SUCCESSFULLY_TEXT_SOURCE_KEY;

/**
 * Created by Gonzalo Carbajosa on 10/05/17.
 */
public abstract class BaseCrudService implements CrudService {

    private MessageSource messageSource;

    public BaseCrudService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    protected String getSaveOrEditSuccessMessage(GenericEntity genericEntity) {
        String successMessageKey;
        if (StringUtils.isEmpty(genericEntity.getId())) {
            successMessageKey = ENTITY_SAVED_SUCCESSFULLY_TEXT_SOURCE_KEY;
        } else {
            successMessageKey = ENTITY_EDITED_SUCCESSFULLY_TEXT_SOURCE_KEY;
        }

        return this.messageSource.getMessage(successMessageKey,
                                             new Object[] {genericEntity.toString()},
                                             LocaleContextHolder.getLocale());
    }

    @Override
    public CrudOperationDTO addBindingResultErrors(BindingResult result, CrudOperationDTO crudOperationDTO) {
        String errorMessage = this.messageSource.getMessage(ENTITY_CONTAINED_ERRORS_TEXT_SOURCE_KEY,
                                                            new Object[] {},
                                                            LocaleContextHolder.getLocale());

        List<String> bindingErrorMessages = result.getAllErrors()
                                                  .stream()
                                                  .map(ObjectError::toString)
                                                  .collect(Collectors.toList());

        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(errorMessage);
        errorMessages.addAll(bindingErrorMessages);

        crudOperationDTO.addErrorMessages(errorMessages);
        return crudOperationDTO;
    }
}
