package com.hhistory.mvc.cms.propertyEditor;

import com.hhistory.data.model.GenericEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author Gonzalo
 */
public class GenericObjectPropertyEditor<TYPE extends GenericEntity> extends PropertyEditorSupport {

    private CrudRepository<TYPE, Long> crudRepository;

    public GenericObjectPropertyEditor(CrudRepository<TYPE, Long> crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public String getAsText() {
        if (super.getValue() != null) {
            Long id = ((TYPE) super.getValue()).getId();

            if (Objects.nonNull(id)) {
                return id.toString();
            } else {
                return "-1";
            }
        }

        return null;
    }

    @Override
    public void setAsText(String idText) throws IllegalArgumentException {
        if (!StringUtils.isEmpty(idText)) {
            Long id = Long.valueOf(idText);
            super.setValue(crudRepository.findById(id)
                                         .orElse(null));
        } else {
            super.setValue(null);
        }
    }
}
