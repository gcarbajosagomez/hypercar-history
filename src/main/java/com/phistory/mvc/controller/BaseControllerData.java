package com.phistory.mvc.controller;

public class BaseControllerData
{
	/********************
     *******URLs*********
     ********************/
	protected static final String INDEX_URL   		 				= "index";
	protected static final String CARS_URL           				= "cars";
	protected static final String ENGINES_URL           			= "engines";
	protected static final String MODELS_SEARCH_URL  				= "modelsSearch";
	protected static final String PICTURES_URL 		 				= "pictures";
	protected static final String COOKIES_POLICY_URL 				= "cookiesPolicy"; 
	
	/*************************
     ******Request params*****
     *************************/
	protected static final String ID          						= "id";
    protected static final String CAR_ID          					= "carId";
    protected static final String MANUFACTURER_ID          			= "manufacturerId";
    protected static final String PICTURE_ID      					= "picId";
    protected static final String PAG_NUM      	  					= "pagNum";
    protected static final String PAG_NUM_DATA	  					= "pagNumData";
    protected static final String CARS_PER_PAGE  					= "carsPerPage";
    protected static final String CARS_PER_PAGE_DATA    			= "carsPerPageData";
    protected static final String CONTENT_TO_SEARCH     			= "contentToSearch";
    protected static final String SEARCH_TOTAL_RESULTS_DATA 		= "searchTotalResultsData";
	
    /*************************
     **********Actions********
     *************************/
	protected static final String ACTION               		  		= "action";
	protected static final String LOAD_CAR_PICTURE_ACTION     		= "loadCarPicture";
    protected static final String LOAD_CAR_PREVIEW_ACTION     		= "loadCarPreview";
    protected static final String LOAD_MANUFACTURER_LOGO_ACTION 	= "loadManufacturerLogo";
	protected static final String PICTURE_LOAD_COMMAND_ACTION 		= "pictureLoadCommand";
	
	/*************************
     **********Misc***********
     *************************/
	protected static final String PAGANI_HISTORY_WEB 				= "pagani-history-web";	
	protected static final String IMAGE_CONTENT_TYPE 				= "image/jpg";	
	protected static final String UNITS_OF_MEASURE   				= "unitsOfMeasure"; 
	protected static final String UNITS_OF_MEASURE_COOKIE_NAME  	= "def_units"; 	 
	protected static final String UNITS_OF_MEASURE_METRIC       	= "metric"; 		 
	protected static final String UNITS_OF_MEASURE_IMPERIAL     	= "imperial";
	protected static final String LANGUAGE_COOKIE_NAME   			= "def_lang"; 
	protected static final String ERROR_VIEW_NAME                   = "/error";  
	protected static final String CARS                   			= "cars";  
	protected static final String MODELS                   			= "models"; 
	protected static final String CONTENT_SEARCH_DTO       			= "contentSearchDto";
	protected static final String CAR_DETAILS       				= "carDetails";
}
