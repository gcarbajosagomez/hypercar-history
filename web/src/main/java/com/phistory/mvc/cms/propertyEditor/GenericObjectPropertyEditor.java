package com.phistory.mvc.cms.propertyEditor;

import com.phistory.data.model.GenericEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author Gonzalo
 */
public class GenericObjectPropertyEditor<TYPE extends GenericEntity> extends PropertyEditorSupport {

    private CrudRepository<TYPE, Long> repository;

    public GenericObjectPropertyEditor(CrudRepository<TYPE, Long> repository) {
        this.repository = repository;
    }

    @Override
    public String getAsText() {
        if (super.getValue() != null) {
            Long id = ((TYPE) super.getValue()).getId();

            if (id != null) {
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
            super.setValue(this.repository.findOne(id));
        } else {
            super.setValue(null);
        }
    }
}
