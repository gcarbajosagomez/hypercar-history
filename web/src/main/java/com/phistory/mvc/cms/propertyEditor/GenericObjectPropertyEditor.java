package com.phistory.mvc.cms.propertyEditor;

import com.phistory.data.model.GenericEntity;
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
            Long id = new Long(idText);
            super.setValue(this.crudRepository.findOne(id));
        } else {
            super.setValue(null);
        }
    }
}
