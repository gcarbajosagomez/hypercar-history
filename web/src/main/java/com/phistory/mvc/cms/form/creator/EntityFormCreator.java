package com.phistory.mvc.cms.form.creator;

/**
 * Creates new entities out of the data contained in entity forms and vice versa
 * 
 * @author Gonzalo
 *
 * @param <ENTITY> The entity type
 * @param <ENTITYFORM> The entity form type
 */
public interface EntityFormCreator<ENTITY, ENTITYFORM>
{
	/**
	 * Create a new entity form out of the data contained in an entity
	 *  
	 * @param entity 
	 * @return The entity form created
	 */
	public ENTITYFORM createFormFromEntity(ENTITY entity);
	
	/**
	 * Create a new entity out of the data contained in an entity form
	 * 
	 * @param entityForm
	 * @return The entity created
	 */
	public ENTITY createEntityFromForm(ENTITYFORM entityForm);
}
