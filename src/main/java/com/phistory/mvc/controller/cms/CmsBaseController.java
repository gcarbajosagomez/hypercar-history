package com.phistory.mvc.controller.cms;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.phistory.mvc.cms.propertyEditor.DatePropertyEditor;
import com.phistory.mvc.cms.propertyEditor.GenericObjectPropertyEditor;
import com.phistory.mvc.cms.propertyEditor.PreviewPicturePropertyEditor;
import com.phistory.mvc.controller.BaseController;
import com.tcp.data.dao.impl.EngineDao;
import com.tcp.data.dao.impl.ManufacturerDao;
import com.tcp.data.model.Manufacturer;
import com.tcp.data.model.Picture;
import com.tcp.data.model.engine.Engine;

/**
 * Base controller that contains common CMS data and functionality 
 * 
 * @author Gonzalo
 *
 */
public class CmsBaseController extends BaseController
{
	private static boolean loggedIn = false;
	@Inject
    private ManufacturerDao manufacturerDao;
	@Inject
    private EngineDao engineDao;
	@Inject
	private ResourceBundleMessageSource messageSource;
	
	/********************
     *******URLs*********
     ********************/
	protected static final String CMS_CONTEXT 						= "cms/"; 
	protected static final String EDIT_URL 		 					= "edit";
    protected static final String DELETE_URL  						= "delete";
    protected static final String LOGIN_URL	 	   					= "login";
    protected static final String CONTENT_LIST_URL  				= "contentList";
	protected static final String CARS_URL		           			= "cars";
	protected static final String MANUFACTURERS_URL					= "manufacturers"; 
	protected static final String PREVIEW_PICTURE_DELETE_URL 		= "previewPictureDelete";
    
    /*************************
     ******Request params*****
     *************************/
    protected static final String ENGINE_ID          				= "engineId";
    protected static final String MANUFACTURERS_PER_PAGE  			= "manufacturersPerPage";
    protected static final String MANUFACTURERS_PER_PAGE_DATA    	= "manufacturersPerPageData";
    protected static final String DELETE_PREVIEW_PICTURE 			= "deletePreviewPicture";
    
    /*************************
     **********Misc***********
     *************************/
    protected static final String MANUFACTURERS 					= "manufacturers"; 
    protected static final String CAR_EDIT_VIEW_NAME 				= "/cms/carEdit"; 
    protected static final String MANUFACTURER_EDIT_VIEW_NAME 		= "/cms/manufacturerEdit"; 
    protected static final String CAR_EDIT_FORM_COMMAND 		    = "CEFC"; 
	
	@ModelAttribute(value = CMS_CONTEXT)
    public void fillBaseCmsModel(Model model)
    {
		model.addAttribute("editURL", 				EDIT_URL);
		model.addAttribute("deleteURL", 			DELETE_URL);
		model.addAttribute("manufacturersURL", 		MANUFACTURERS_URL);
		model.addAttribute("loginURL", 	 			LOGIN_URL);
		model.addAttribute("loggedIn", 	 			loggedIn);
		model.addAttribute("cmsContext", 			CMS_CONTEXT);
    }
	
	@InitBinder
    public void initBinder(WebDataBinder binder)
    {
		binder.registerCustomEditor(Manufacturer.class, new GenericObjectPropertyEditor<Manufacturer, Long>(manufacturerDao));
        binder.registerCustomEditor(Engine.class, new GenericObjectPropertyEditor<Engine, Long>(engineDao));
        binder.registerCustomEditor(Picture.class, new PreviewPicturePropertyEditor(getPictureDao()));
        binder.registerCustomEditor(Calendar.class, new DatePropertyEditor(new SimpleDateFormat("yyyy-MM")));
    }

	public ManufacturerDao getManufacturerDao() {
		return manufacturerDao;
	}

	public EngineDao getEngineDao() {
		return engineDao;
	}

	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setLoggedIn(boolean loggedIn) {
		CmsBaseController.loggedIn = loggedIn;
	}	
}
