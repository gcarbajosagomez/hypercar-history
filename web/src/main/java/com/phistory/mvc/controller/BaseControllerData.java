package com.phistory.mvc.controller;

public class BaseControllerData
{
	/********************
     *******URLs*********
     ********************/
	public static final String INDEX_URL   		 	= "index";
	public static final String CARS_URL           	= "cars";
	public static final String ENGINES_URL          = "engines";
	public static final String MODELS_SEARCH_URL  	= "modelsSearch";
	public static final String PICTURES_URL 		= "pictures";
	public static final String COOKIES_POLICY_URL 	= "cookiesPolicy"; 
	public static final String PAGINATION_URL		= "pagination";
	public static final String CONTACT_US_URL		= "contactUs";
	public static final String TECHNOLOGY_STACK_URL	= "technologyStack";
	
	/*************************
     ******Request params*****
     *************************/
	public static final String ID          						= "id";
    public static final String CAR_ID          					= "carId";
    public static final String MANUFACTURER_ID          		= "manufacturerId";
    public static final String PAG_NUM      	  				= "pn";
    public static final String PAG_NUM_DATA	  					= "pagNumData";
    public static final String CARS_PER_PAGE  					= "cpp";
    public static final String CARS_PER_PAGE_DATA    			= "carsPerPageData";
    public static final String CONTENT_TO_SEARCH     			= "cts";
    public static final String SEARCH_TOTAL_RESULTS_DATA 		= "searchTotalResultsData";
    public static final String LANGUAGE_DATA					= "l";
    public static final String DO_NOT_TRACK_REQUEST_PARAM		= "dnt";

    /*************************
     **********Actions********
     *************************/
	public static final String PICTURE_LOAD_ACTION_ACTION 		= "action";
	public static final String PICTURE_LOAD_COMMAND_ACTION 		= "pictureLoadCommand";
	
	/*************************
     **********Misc***********
     *************************/
	public static final String IMAGE_CONTENT_TYPE 					= "image/jpg";	
	public static final String UNITS_OF_MEASURE   					= "unitsOfMeasure"; 
	public static final String UNITS_OF_MEASURE_COOKIE_NAME  		= "def_units"; 	 
	public static final String UNITS_OF_MEASURE_METRIC       		= "metric"; 		 
	public static final String UNITS_OF_MEASURE_IMPERIAL     		= "imperial";
	public static final String LANGUAGE_COOKIE_NAME   				= "def_lang"; 
	public static final String ERROR_VIEW_NAME                  	= "/error";  
	public static final String CARS                   				= "cars";
	public static final String CAR                					= "car";
	public static final String PICTURE_IDS                   		= "pictureIds";
	public static final String PICTURES	                   			= "pictures";
    public static final String MODELS                   			= "models";
	public static final String CONTENT_TO_SEARCH_DATA      			= "contentToSearchData";
	public static final String CAR_DETAILS       					= "carDetails";
	public static final String ENGINE                  				= "engine";  
	public static final String SUCCESS_MESSAGE         				= "successMessage";  
	public static final String EXCEPTION_MESSAGE       				= "exceptionMessage";  
	public static final String CONTACT_US_SUCCESS_MESSAGE       	= "contactUsSuccessMessage";  
	public static final String CONTACT_US_EXCEPTION_MESSAGE     	= "contactUsExceptionMessage";   
	public static final String YOUTUBE_VIDEO_IDS     				= "youtubeVideoIds";    
	public static final String CAR_INTERNET_CONTENT_REVIEW_ARTICLES	= "carInternetContentReviewArticles";  
	public static final String STATIC_RESOURCES_URI					= "/static/";
}
