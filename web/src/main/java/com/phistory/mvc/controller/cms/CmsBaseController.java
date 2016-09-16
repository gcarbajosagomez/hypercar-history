package com.phistory.mvc.controller.cms;

import static com.phistory.mvc.controller.cms.CmsBaseController.CMS_CONTEXT;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;

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
import com.phistory.data.dao.impl.CarDao;
import com.phistory.data.dao.impl.CarInternetContentDAO;
import com.phistory.data.dao.impl.EngineDao;
import com.phistory.data.dao.impl.ManufacturerDao;
import com.phistory.data.model.Manufacturer;
import com.phistory.data.model.Picture;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.engine.Engine;

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
	@Getter
	@Inject
    private ManufacturerDao manufacturerDao;
	@Getter
	@Inject
    private EngineDao engineDao;	
	@Inject
    private CarDao carDAO;
	@Getter
	@Inject
    private CarInternetContentDAO carInternetContentDAO;	
	
	/********************
     *******URLs*********
     ********************/
	public static final String CMS_CONTEXT 					= "cms/"; 
	public static final String LOGIN_URL	 	   			= "login";	
	public static final String SAVE_URL 		 			= "save";
	public static final String EDIT_URL 		 			= "edit";
    public static final String DELETE_URL  					= "delete";
	public static final String CARS_URL		           		= "cars";
	public static final String MANUFACTURERS_URL			= "manufacturers";
	public static final String CAR_INTERNET_CONTENTS_URL    = "carInternetContents";

    /*************************
     ******Request params*****
     *************************/
	public static final String QUERY_STRING_SEPARATOR		= "?";
	public static final String LOGIN_SUCCESS 				= "success";
	public static final String LOGIN_ERROR 					= "error";
	public static final String LOGOUT 						= "logout";
    
    public static final String MANUFACTURERS_PER_PAGE  		= "manufacturersPerPage";
    public static final String MANUFACTURERS_PER_PAGE_DATA  = "manufacturersPerPageData";

	/*************************
     **********Actions********
     *************************/
    public static final String DELETE_CAR_PICTURE_ACTION    = "deleteCarPicture";
    
    /*************************
     **********Misc***********
     *************************/
	public static final String MANUFACTURERS 							= "manufacturers";
	public static final String CAR_EDIT_VIEW_NAME 						= "/cms/carEdit";
	public static final String MANUFACTURER_EDIT_VIEW_NAME 				= "/cms/manufacturerEdit";
	public static final String CAR_EDIT_FORM_COMMAND 					= "CEFC";
	public static final String CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND 	= "CICEFC";
	public static final String MANUFACTURER_EDIT_FORM_COMMAND 			= "MEFC";
	public static final String PICTURE_EDIT_FORM_COMMAND 			    = "PEFC";
	public static final String LOGGED_IN 								= "loggedIn";
    
	@ModelAttribute
    public void fillBaseCmsModel(Model model, HttpServletResponse response)
    {
        this.setNotCacheHeadersToResponse(response);
		model.addAttribute("saveURL", 			        SAVE_URL);
		model.addAttribute("deleteURL", 		        DELETE_URL);
		model.addAttribute("manufacturersURL",	        MANUFACTURERS_URL);
		model.addAttribute("loginURL", 	 		        LOGIN_URL);
		model.addAttribute(LOGGED_IN, 	 		        true);
		model.addAttribute("deleteCarPictureAction",    DELETE_CAR_PICTURE_ACTION);
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder)
    {
		binder.registerCustomEditor(Manufacturer.class,	new GenericObjectPropertyEditor<>(this.manufacturerDao));
		binder.registerCustomEditor(Car.class,			new GenericObjectPropertyEditor<>(this.carDAO));
        binder.registerCustomEditor(Engine.class, 		new GenericObjectPropertyEditor<>(this.engineDao));
        binder.registerCustomEditor(Picture.class, 		new PreviewPicturePropertyEditor(super.getPictureDao()));
        binder.registerCustomEditor(Calendar.class, 	new DatePropertyEditor(new SimpleDateFormat("yyyy-MM")));
    }

    /**
     * Set the necessary HTTP headers to the supplied {@link HttpServletResponse} so that it's not cached by browsers
     * @param response
     */
    private void setNotCacheHeadersToResponse(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
    }
}
