package com.phistory.mvc.controller;

public class BaseControllerData
{
	/********************
     *******URLs*********
     ********************/
	public static final String INDEX_URL   		 				= "index";
	public static final String CARS_URL           				= "cars";
	public static final String ENGINES_URL           			= "engines";
	public static final String MODELS_SEARCH_URL  				= "modelsSearch";
	public static final String PICTURES_URL 		 			= "pictures";
	public static final String COOKIES_POLICY_URL 				= "cookiesPolicy"; 
	public static final String PAGINATION_URL					= "pagination";
	public static final String CONTACT_US_URL					= "contactUs";
	
	/*************************
     ******Request params*****
     *************************/
	public static final String ID          						= "id";
    public static final String CAR_ID          					= "carId";
    public static final String MANUFACTURER_ID          		= "manufacturerId";
    public static final String PICTURE_ID      					= "picId";
    public static final String PAG_NUM      	  				= "pagNum";
    public static final String PAG_NUM_DATA	  					= "pagNumData";
    public static final String CARS_PER_PAGE  					= "carsPerPage";
    public static final String CARS_PER_PAGE_DATA    			= "carsPerPageData";
    public static final String CONTENT_TO_SEARCH     			= "contentToSearch";
    public static final String SEARCH_TOTAL_RESULTS_DATA 		= "searchTotalResultsData";
	
    /*************************
     **********Actions********
     *************************/
	public static final String ACTION               		  	= "action";
	public static final String LOAD_CAR_PICTURE_ACTION     		= "loadCarPicture";
    public static final String LOAD_CAR_PREVIEW_ACTION     		= "loadCarPreview";
    public static final String LOAD_MANUFACTURER_LOGO_ACTION 	= "loadManufacturerLogo";
	public static final String PICTURE_LOAD_COMMAND_ACTION 		= "pictureLoadCommand";
	
	/*************************
     **********Misc***********
     *************************/
	public static final String IMAGE_CONTENT_TYPE 				= "image/jpg";	
	public static final String UNITS_OF_MEASURE   				= "unitsOfMeasure"; 
	public static final String UNITS_OF_MEASURE_COOKIE_NAME  	= "def_units"; 	 
	public static final String UNITS_OF_MEASURE_METRIC       	= "metric"; 		 
	public static final String UNITS_OF_MEASURE_IMPERIAL     	= "imperial";
	public static final String LANGUAGE_COOKIE_NAME   			= "def_lang"; 
	public static final String ERROR_VIEW_NAME                  = "/error";  
	public static final String CARS                   			= "cars";  
	public static final String MODELS                   		= "models"; 
	public static final String CONTENT_SEARCH_DTO       		= "contentSearchDto";
	public static final String CONTENT_TO_SEARCH_DATA      		= "contentToSearchData";
	public static final String CAR_DETAILS       				= "carDetails";
	public static final String ENGINE                  			= "engine";  
	public static final String SUCCESS_MESSAGE         			= "successMessage";  
	public static final String EXCEPTION_MESSAGE       			= "exceptionMessage";  
	public static final String CONTACT_US_SUCCESS_MESSAGE       = "contactUsSuccessMessage";  
	public static final String CONTACT_US_EXCEPTION_MESSAGE     = "contactUsExceptionMessage";  
}
