package com.phistory.mvc.controller.cms;

import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Controller
@RequestMapping(value = CMS_CONTEXT)
public class CmsBaseController extends BaseController
{
	@Setter
	private boolean loggedIn = false;
	@Getter
	@Inject
    private ManufacturerDao manufacturerDao;
	@Getter
	@Inject
    private EngineDao engineDao;
	@Getter
	@Inject
	private ResourceBundleMessageSource messageSource;
	
	/********************
     *******URLs*********
     ********************/
	public static final String CMS_CONTEXT 						= "cms/"; 
	public static final String LOGIN_URL	 	   				= "login";
	
	public static final String SAVE_URL 		 				= "save";
	public static final String EDIT_URL 		 				= "edit";
    public static final String DELETE_URL  						= "delete";
    public static final String CONTENT_LIST_URL  				= "contentList";
	public static final String CARS_URL		           			= "cars";
	public static final String MANUFACTURERS_URL				= "manufacturers"; 
	public static final String PREVIEW_PICTURE_DELETE_URL 		= "previewPictureDelete";
    
    /*************************
     ******Request params*****
     *************************/
	public static final String QUERY_STRING_SEPARATOR			= "?";
	public static final String LOGIN_SUCCESS 					= "success";
	public static final String LOGIN_ERROR 						= "error";
	public static final String LOGOUT 							= "logout";
    
    public static final String MANUFACTURERS_PER_PAGE  			= "manufacturersPerPage";
    public static final String MANUFACTURERS_PER_PAGE_DATA    	= "manufacturersPerPageData";
    public static final String DELETE_PREVIEW_PICTURE 			= "deletePreviewPicture";
    
    /*************************
     **********Misc***********
     *************************/
    public static final String MANUFACTURERS 					= "manufacturers"; 
    public static final String CAR_EDIT_VIEW_NAME 				= "/cms/carEdit"; 
    public static final String MANUFACTURER_EDIT_VIEW_NAME 		= "/cms/manufacturerEdit"; 
    public static final String CAR_EDIT_FORM_COMMAND 		    = "CEFC"; 
    public static final String MANUFACTURER_EDIT_FORM_COMMAND 	= "MEFC"; 
    public static final String EXCEPTION_MESSAGE 				= "exceptionMessage"; 
    public static final String SUCCESS_MESSAGE  				= "successMessage"; 
    public static final String LOGGEDIN  						= "loggedIn"; 
    
	@ModelAttribute
    public void fillBaseCmsModel(Model model)
    {
		model.addAttribute("saveURL", 			SAVE_URL);
		model.addAttribute("deleteURL", 		DELETE_URL);
		model.addAttribute("manufacturersURL",	MANUFACTURERS_URL);
		model.addAttribute("loginURL", 	 		LOGIN_URL);
		model.addAttribute(LOGGEDIN, 	 		true);
    }
	
	@InitBinder
    public void initBinder(WebDataBinder binder)
    {
		binder.registerCustomEditor(Manufacturer.class,	new GenericObjectPropertyEditor<Manufacturer, Long>(this.manufacturerDao));
        binder.registerCustomEditor(Engine.class, 		new GenericObjectPropertyEditor<Engine, Long>(this.engineDao));
        binder.registerCustomEditor(Picture.class, 		new PreviewPicturePropertyEditor(getPictureDao()));
        binder.registerCustomEditor(Calendar.class, 	new DatePropertyEditor(new SimpleDateFormat("yyyy-MM")));
    }	
}
