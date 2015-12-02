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
	protected static final String CAR_LIST_URL           			= "carList";
	protected static final String CAR_EDIT_URL 		 				= "carEdit";
	protected static final String CAR_DELETE_URL 	 				= "carDelete";
	protected static final String ENGINE_EDIT_URL 					= "engineEdit";
	protected static final String MANUFACTURER_EDIT_URL    			= "manufacturerEdit";
    protected static final String MANUFACTURER_LIST_URL    			= "manufacturerList";
    protected static final String MANUFACTURER_DELETE_URL  			= "manufacturerDelete";
    protected static final String ENGINE_CONTENT_LIST_URL  			= "engineContentList";
	protected static final String PREVIEW_PICTURE_DELETE_URL 		= "previewPictureDelete";
    protected static final String PICTURE_DELETE_URL 	   			= "pictureDelete";
    protected static final String LOGIN_URL	 	   					= "login";
    
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
	
	@ModelAttribute(value = CMS_CONTEXT)
    public void fillBaseCmsModel(Model model)
    {
		model.addAttribute("carEditURL", 			CAR_EDIT_URL);
		model.addAttribute("manufacturerListURL", 	MANUFACTURER_LIST_URL);
		model.addAttribute("manufacturerEditURL", 	MANUFACTURER_EDIT_URL);
		model.addAttribute("loginURL", 	 			LOGIN_URL);
		model.addAttribute("loggedIn", 	 			loggedIn);
		model.addAttribute("cmsContext", 			CMS_CONTEXT);
    }
	
	@InitBinder
    public void initBinder(WebDataBinder binder)
    {
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
