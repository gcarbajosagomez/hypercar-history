package com.phistory.mvc.cms.controller;

import com.phistory.data.dao.sql.SqlCarDAO;
import com.phistory.data.dao.sql.SqlCarInternetContentDAO;
import com.phistory.data.model.Manufacturer;
import com.phistory.data.model.car.Car;
import com.phistory.data.model.engine.Engine;
import com.phistory.mvc.cms.propertyEditor.DatePropertyEditor;
import com.phistory.mvc.cms.propertyEditor.GenericObjectPropertyEditor;
import com.phistory.mvc.controller.BaseController;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import lombok.Getter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.phistory.data.dao.sql.SqlCarInternetContentRepository.CAR_INTERNET_CONTENT_REPOSITORY;
import static com.phistory.data.dao.sql.SqlCarRepository.CAR_REPOSITORY;
import static com.phistory.data.dao.sql.SqlEngineRepository.ENGINE_REPOSITORY;
import static com.phistory.data.dao.sql.SqlManufacturerRepository.MANUFACTURER_REPOSITORY;
import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;

/**
 * Base controller that contains common CMS data and functionality
 *
 * @author Gonzalo
 */
@Controller
@RequestMapping(value = CMS_CONTEXT)
public class CMSBaseController extends BaseController {

    /********************
     *******URLs*********
     ********************/
    public static final String CMS_CONTEXT               = "cms/";
    public static final String LOGIN_URL                 = "login";
    public static final String SAVE_URL                  = "save";
    public static final String EDIT_URL                  = "edit";
    public static final String DELETE_URL                = "delete";
    public static final String CARS_URL                  = "cars";
    public static final String MANUFACTURERS_URL         = "manufacturers";
    public static final String CAR_INTERNET_CONTENTS_URL = "carInternetContents";
    public static final String ENTITY_MANAGEMENT_URL     = "entityManagement";

    /*************************
     ******Request params*****
     *************************/
    public static final String QUERY_STRING_SEPARATOR = "?";
    public static final String LOGIN_SUCCESS          = "success";
    public static final String LOGIN_ERROR            = "error";
    public static final String LOGOUT                 = "logout";

    public static final String MANUFACTURER_ID             = "manufacturerId";
    public static final String MANUFACTURERS_PER_PAGE      = "manufacturersPerPage";
    public static final String MANUFACTURERS_PER_PAGE_DATA = "manufacturersPerPageData";
    public static final String CAR_INTERNET_CONTENT_ID     = "carInternetContentId";

    /*************************
     **********Actions********
     *************************/
    public static final String DELETE_CAR_PICTURE_ACTION      = "deleteCarPicture";
    public static final String ENTITY_MANAGEMENT_QUERY_ACTION = "entityManagementQueryAction";

    /*************************
     **********Misc***********
     *************************/
    public static final String MANUFACTURERS                              = "manufacturers";
    public static final String CAR_EDIT_VIEW_NAME                         = "/cms/carEdit";
    public static final String MANUFACTURER_EDIT_VIEW_NAME                = "/cms/manufacturerEdit";
    public static final String CAR_EDIT_FORM_COMMAND                      = "CEFC";
    public static final String CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND     = "CICEFC";
    public static final String MANUFACTURER_EDIT_FORM_COMMAND             = "MEFC";
    public static final String PICTURE_EDIT_FORM_COMMAND                  = "PEFC";
    public static final String ENTITY_MANAGEMENT_LOAD_COMMAND             = "EMLC";
    public static final String LOGGED_IN                                  = "loggedIn";
    public static final String DATE_FORMAT                                = "yyyy-MM";
    public static final String ENTITY_SAVED_SUCCESSFULLY_RESULT_MESSAGE   = "entitySavedSuccessfully";
    public static final String ENTITY_EDITED_SUCCESSFULLY_RESULT_MESSAGE  = "entityEditedSuccessfully";
    public static final String ENTITY_DELETED_SUCCESSFULLY_RESULT_MESSAGE = "entityDeletedSuccessfully";
    public static final String ENTITY_CONTAINED_ERRORS_RESULT_MESSAGE     = "entityContainedErrors";
    public static final String CMS_MODELS                                 = "cmsModels";

    private static final String CACHE_CONTROL_HTTP_HEADER = "Cache-Control";
    private static final String PRAGMA_HTTP_HEADER        = "Pragma";
    private static final String EXPIRES_HTTP_HEADER       = "Expires";

    @Getter
    @Inject
    @Named(MANUFACTURER_REPOSITORY)
    private CrudRepository           sqlManufacturerRepository;
    @Getter
    @Inject
    @Named(ENGINE_REPOSITORY)
    private CrudRepository           sqlEngineRepository;
    @Inject
    private SqlCarDAO                sqlCarDAO;
    @Inject
    @Named(CAR_REPOSITORY)
    private CrudRepository           sqlCarRepository;
    @Getter
    @Inject
    private SqlCarInternetContentDAO sqlCarInternetContentDAO;
    @Getter
    @Inject
    @Named(CAR_INTERNET_CONTENT_REPOSITORY)
    private CrudRepository           sqlCarInternetContentRepository;
    @Inject
    private ModelFiller              cmsBaseModelFiller;

    @ModelAttribute
    public void fillBaseCmsModel(Model model, HttpServletResponse response) {
        this.cmsBaseModelFiller.fillModel(model);
        this.setNotCacheHeadersToResponse(response);
        model.addAttribute("saveURL", SAVE_URL);
        model.addAttribute("deleteURL", DELETE_URL);
        model.addAttribute("manufacturersURL", MANUFACTURERS_URL);
        model.addAttribute("loginURL", LOGIN_URL);
        model.addAttribute(LOGGED_IN, true);
        model.addAttribute("deleteCarPictureAction", DELETE_CAR_PICTURE_ACTION);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Manufacturer.class, new GenericObjectPropertyEditor<>(this.sqlManufacturerRepository));
        binder.registerCustomEditor(Car.class, new GenericObjectPropertyEditor<>(this.sqlCarRepository));
        binder.registerCustomEditor(Engine.class, new GenericObjectPropertyEditor<>(this.sqlEngineRepository));
        binder.registerCustomEditor(Calendar.class, new DatePropertyEditor(new SimpleDateFormat(DATE_FORMAT)));
    }

    /**
     * Set the necessary HTTP headers to the supplied {@link HttpServletResponse} so that it's not cached by browsers
     *
     * @param response
     */
    private void setNotCacheHeadersToResponse(HttpServletResponse response) {
        response.setHeader(CACHE_CONTROL_HTTP_HEADER, "no-cache, no-store, max-age=0, must-revalidate");
        response.setHeader(PRAGMA_HTTP_HEADER, "no-cache");
        response.setHeader(EXPIRES_HTTP_HEADER, "0");
    }
}
