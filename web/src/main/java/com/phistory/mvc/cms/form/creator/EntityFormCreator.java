package com.phistory.mvc.cms.form.creator;

/**
 * Creates new entities out of the data contained in entity forms and vice versa
 * 
 * @author Gonzalo
 *
 * @param <ENTITY> The entity type
 * @param <ENTITY_FORM> The entity form type
 */
public interface EntityFormCreator<ENTITY, ENTITY_FORM>
{
	/**
	 * Create a new entity form out of the data contained in an entity
	 *  
	 * @param entity 
	 * @return The entity form created
	 */
	ENTITY_FORM createFormFromEntity(ENTITY entity);
	
	/**
	 * Create a new entity out of the data contained in an entity form
	 * 
	 * @param entityForm
	 * @return The entity created
	 */
	ENTITY createEntityFromForm(ENTITY_FORM entityForm);
}
