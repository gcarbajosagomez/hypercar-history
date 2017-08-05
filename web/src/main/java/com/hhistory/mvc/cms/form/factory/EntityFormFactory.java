package com.hhistory.mvc.cms.form.factory;

import com.hhistory.mvc.cms.form.EditForm;

/**
 * Creates new entities out of the data contained in entity forms and vice versa
 * 
 * @author Gonzalo
 *
 * @param <ENTITY> The entity type
 * @param <ENTITY_FORM> The entity form type
 */
public interface EntityFormFactory<ENTITY, ENTITY_FORM extends EditForm>
{
	/**
	 * Build a new entity form out of the data contained in an entity
	 *  
	 * @param entity 
	 * @return The entity form created
	 */
	ENTITY_FORM buildFormFromEntity(ENTITY entity);
	
	/**
	 * Build a new entity out of the data contained in an entity form
	 * 
	 * @param entityForm
	 * @return The entity created
	 */
	ENTITY buildEntityFromForm(ENTITY_FORM entityForm);
}
