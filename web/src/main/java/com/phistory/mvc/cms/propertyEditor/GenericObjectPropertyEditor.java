package com.phistory.mvc.cms.propertyEditor;

import com.phistory.data.model.GenericEntity;
import org.springframework.data.repository.CrudRepository;

import java.beans.PropertyEditorSupport;
import java.io.Serializable;

/**
 * @author Gonzalo
 */
public class GenericObjectPropertyEditor<TYPE extends GenericEntity,
        IDENTIFIER extends Serializable> extends PropertyEditorSupport {

    private CrudRepository<TYPE, IDENTIFIER> repository;

    public GenericObjectPropertyEditor(CrudRepository<TYPE, IDENTIFIER> repository) {
        this.repository = repository;
    }

    @Override
    public String getAsText() {
        if (getValue() != null) {
            Long id = ((TYPE) getValue()).getId();

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
        if (idText != null && !idText.isEmpty()) {
            IDENTIFIER id = (IDENTIFIER) new Long(idText);
            super.setValue(this.repository.findOne(id));
        } else {
            super.setValue(null);
        }
    }
}
