<#import "/spring.ftl" as spring />
<#include "pagination.ftl">
<#include "contactUs.ftl">
<#include "pageLanguage.ftl">
<#include "contentSearch.ftl">

<#macro startPage title=''>      
    <!DOCTYPE html>
    	<#assign pageLanguage = getTextSource('paganiHistory.language')/>
        <html lang="${pageLanguage}" class="no-js">
            <head>
            		<title>${title} <#if title?? && (title?length > 0)> | </#if> ${getTextSource('paganiHistory')}</title>
            		<meta charset="UTF-8">
					<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
					<meta name="viewport" content="width=device-width, initial-scale=1.0">
					<meta name="description" content="${title}">
					<#-- CRSF token to protect against cross site attacks -->
					<meta name="_csrf" content="${_csrf.token}"/>
					<#-- default header name is X-CSRF-TOKEN -->
					<meta name="_csrf_header" content="${_csrf.headerName}"/>
					 
					<@addHrefLangInfo/>
					<link rel="shortcut icon" href="/resources/img/favicon.ico">    				
        			<link rel="stylesheet" href="/resources/stylesheet/bootstrap.min.css">
					<link rel="stylesheet" href="/resources/stylesheet/bootstrap-theme.min.css">
                	<link rel="stylesheet" href="/resources/stylesheet/font-awesome.min.css">
					<link rel="stylesheet" href="/resources/stylesheet/bootstrap-image-gallery.min.css">
					<link rel="stylesheet" href="/resources/stylesheet/blueimp-gallery.min.css">
        			<link rel="stylesheet" href="/resources/stylesheet/main.min.css">     			
					
            		<script src="/resources/javascript/lib/jquery.min.js"></script>	
            		<script src="/resources/javascript/lib/jquery.cookie.js"></script>
            		<script src="/resources/javascript/lib/jquery.cookiesdirective.js"></script>
            		<script src="/resources/javascript/lib/jquery.blockUI.js"></script>
					<script src="/resources/javascript/lib/jquery.blueimp-gallery.min.js"></script>
        			<script src="/resources/javascript/lib/bootstrap.min.js"></script>
        			<script src="/resources/javascript/lib/bootstrap-image-gallery.min.js"></script>
        			<script src="/resources/javascript/lib/bootstrap-paginator.min.js"></script>
        			<script src="/resources/javascript/lib/bootstrap-datepicker.js"></script>
        			<script src="/resources/javascript/lib/bootbox.min.js"></script>
					<script src="/resources/javascript/lib/modernizr.custom.js"></script>
					<script src="/resources/javascript/main.min.js"></script>
					
        			<#-- since this file is imported at the beginning of each template, and then this macro is called, this function must be called after jQuery has been loaded -->
        			<script type='text/javascript'>						
						var ajaxCallBeingProcessed = false;
						document.addEventListener("touchstart", function(){}, true);

						$(document).ready(function()
						{		
							<#-- this script needs all of the elements to have been created before it loads, therefore it must be included once the page has been loaded-->
							$.getScript('/resources/javascript/lib/toucheffects.js', null);							
												
        					$.cookiesDirective({
            					privacyPolicyUri: '/${cookiesPolicyURL}',
            					position: 'bottom',
								message: '${getTextSource('cookiesDirectiveMessage')}',
								deleteAndBlockCookiesMessage: '${getTextSource('cookiesDirectiveMessage.deleteAndBlockCookiesMessage')}',
								privacyPolicyMessage: '${getTextSource('cookiesDirectiveMessage.privacyPolicyMessage')}',
								acceptCookiesMessage: '${getTextSource('cookiesDirectiveMessage.acceptCookies')}',
								continueButton: '${getTextSource('cookiesDirectiveMessage.continueButton')}',
								duration: 200,
								limit: 0,
								fontFamily: 'Helvetica Neue, Helvetica, Arial, sans-serif',
								fontColor: '#FFFFFF',
								fontSize: '13px',
								backgroundColor: 'rgba(63, 63, 63, 1)',
								backgroundOpacity: '90',
								linkColor: '#CA7300'
        					});	
        					
        					setupContentSearchEventListeners();	
        					
        					$('#contact-us-modal-div').on('show.bs.modal', function (e) {
    							setUpContactUsModal();
							});				
    					});
    					
  						(function(i,s,o,g,r,a,m)
  						{
  							i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  						   (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  							m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  						})
  						
  						(window,document,'script','//www.google-analytics.com/analytics.js','ga');
  						ga('create', 'UA-59713760-1', 'auto');
  						ga('send', 'pageview'); 
					</script>
            </head>
            <body> 
              	<div id="main-wrap-div">
	            	<div class="wrap">
    	        		<nav class="navbar navbar-default pagani-history-navbar" role="navigation">       		
        	    		    <#-- Brand and toggle get grouped for better mobile display -->
          					<div class="navbar-header">
          						 <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#main-navbar-collapse"></button>          					     							
          						 
          						 <a class="navbar-brand pagani-history-navbar-brand" href='<@spring.url "/"/>'>				
          							<div class="row">
          								<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8" style="padding-right: 0px;"> 
											<h1 class="italic-font">${getTextSource('paganiHistory')}</h1>
											<h4 class="italic-font text-right" style="padding-right: 30px">${getTextSource('paganiHistory.dataAndImages')}</h4>
										</div>
          					   			<div  class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
          					   				<img class="main-logo" src="/resources/img/pagani-logo.png">
          					   			</div>
	          					   	</div>
    	      					 </a>
        	  				 </div>
          					 
          					 <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
          					 	<#-- Collect the nav links, forms, and other content for toggling -->
          					 	<div id="main-navbar-collapse" class="collapse navbar-collapse well">
	          						 <ul class="nav navbar-nav">          							
    	          						<li><a href='<@spring.url "/${carsURL}"/>'>${getTextSource('cars')}</a></li>              					
        	      						<li>              						
	        	  							<a id="language-dropdown-toggle" class="dropdown-toggle cursor-pointer" data-toggle="dropdown">${getTextSource('language')} <b class="caret"></b></a>
    	      								<ul class="dropdown-menu"> 
        	  									<li role="presentation">
          											<a id="spanish-language-link" class="cursor-pointer" role="menuitem" tabindex="-1" onClick="setPageLanguage('es', $('#main-form')[0]);">
          												<div class="row language-selection-div">
          													<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5 text-left"> 
          													    ${getTextSource('language.spanish')}
          													</div>
															<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
          														<img class="language-flag" src='<@spring.url "/resources/img/spain_flag.jpg"/>' title="${getTextSource('language.spanish')}"/>
          													</div>
          													<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 center-block">
	          													<i id="spanish-loading-gif" class="fa fa-circle-o-notch fa-lg fa-spin blue sr-only"></i>   
    	      												</div>
	    	      										</div>
    	    	  									</a>									
        	  									</li>
          										<li role="separator" class="divider"></li>
          										<li role="presentation"> 
          											<a id="english-language-link" class="cursor-pointer" role="menuitem" tabindex="-1" onClick="setPageLanguage('en', $('#main-form')[0]);"> 
														<div class="row language-selection-div">
    	      												<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5 text-left">
        	  													${getTextSource('language.english')}
          													</div>
															<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
          														<img class="language-flag" src='<@spring.url "/resources/img/uk_flag.jpg"/>' title="${getTextSource('language.english')}"/>
															</div>
	          												<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 center-block">    										
		          												<i id="english-loading-gif" class="fa fa-circle-o-notch fa-lg fa-spin blue sr-only"></i>  
    		      											</div>
        		  										</div>
          											</a>          									
          										</li>   							       
          									</ul>        							              						
              							</li>
              							<#if requestURI?contains("${cmsContext}") && loggedIn?? && loggedIn == true>
              								<li>
              									<a class="dropdown-toggle cursor-pointer" data-toggle="dropdown">${getTextSource('cms')} <b class="caret"></b></a>              									
          										<ul class="dropdown-menu"> 
          											<li role="presentation">
          												<a href='<@spring.url "/${cmsContext}${manufacturersURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${getTextSource('cms.listManufacturers')}</a>
													</li>
													<li role="presentation">
          												<a href='<@spring.url "/${cmsContext}${manufacturersURL}/${editURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${getTextSource('cms.newManufacturer')}</a>
													</li> 
													<li role="separator" class="divider"></li>
        	  										<li role="presentation">
          												<a href='<@spring.url "/${cmsContext}${carsURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${getTextSource('cms.listCars')}</a>
													</li>
													<li role="presentation">
          												<a href='<@spring.url "/${cmsContext}${carsURL}/${editURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${getTextSource('cms.newCar')}</a>
													</li> 
													<li role="separator" class="divider"></li>
													<li role="presentation">
          												<a onClick="submitLoginForm(false);" class="cursor-pointer" role="menuitem" tabindex="-1">${getTextSource('cms.logout')}</a>
													</li>   							       
          										</ul> 
              								</li>
              							</#if>
              							<li>              							
              								<div id="search-container">              								
              									<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1" style="padding-left: 0px; padding-right: 0px;">
              										<a>${getTextSource('contentSearch.search')}</a>
												</div>
												<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10" style="padding-top: 10px; padding-left: 20px;">
													<input id="content-search-input" type="text" class="content-search-input" value="<#if contentToSearchData??>${contentToSearchData}</#if>"/>
  													<label for="content-search-input">
	  													<span id="content-search-span" class="glyphicon glyphicon-search search-icon"></span>
  													</label>
  													<input id="search-total-results" type="hidden" value="<#if searchTotalResultsData??>${searchTotalResultsData}<#else>0</#if>"/>												
  												</div>
											</div>              							
              							</li>								
              						 </ul>
 	         					 </div>
							</div>
    	        		</nav>
        	    		<form id="main-form" action="${requestURI}" method="POST">             	    				      
</#macro>

<#macro endPage>
						</form>
					</div> 
					
					<div class="pagani-history-footer">
						<div class="row">      					
        					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<b>${getTextSource('footer.aboutUs')}</b>
								<p class="text-muted text-left">
									${getTextSource('footer.aboutUs.text')}
								</p>
        					</div>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<b>${getTextSource('footer.contactUs')}</b>
								<p class="text-muted text-left">
									${getTextSource('footer.contactUs.text')}
								</p>								
	        				</div>
    	  				</div>
      					<div class="row"> 
      						<div class="col-lg-12">
      							<b>${getTextSource('footer.technologyStack')}</b>
      							<p class="text-muted text-left">
									${getTextSource('footer.technologyStack.text')}       							   
								</p>
							
								<@createContactUsDialog/>
								<div id="technology-stack-modal-div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="technology-stack-label" aria-hidden="true">
  									<div class="modal-dialog modal-lg">
    									<div class="modal-content">
      										<div class="modal-header">
      											<div class="row">
  													<div class="col-md-8 col-md-8 col-sm-10 col-xs-10">
		       											<h3 class="modal-title">${getTextSource('footer.technologyStack')}</h3>
													</div>
													<div class="col-md-4 col-sm-2 col-xs-2">                            
               	    									<button type="button" class="close pull-right" data-dismiss="modal" aria-hidden="true">&times;</button>			
                   									</div>
               									</div>
		      								</div>
			      							<div class="modal-body" style="background-color: rgba(0, 0, 0, 0.611765);">
    			    							<div class="thumbnail row technology-stack" style="margin-bottom: 0px;">
    			    								<div class="col-lg-12" style="padding-top: 10px;">     		    									
    		    										<img src="/resources/img/tech-stack/openshift-logo.png">   										
    		    										<div class="thumbnail row technology-stack-inner-logo">
    		    											<div class="col-lg-12" style="margin-top: 10px;"> 
   			    												<div class="row" style="margin-top: 10px;"> 
   			    													<div class="col-lg-4" style="padding-bottom: 10px;">
   		    															<img class="center-block" src="/resources/img/tech-stack/wildfly-logo.png">
   		    														</div>  			    												
   		    														<div class="col-lg-4" style="padding-bottom: 10px;">
   		    															<img class="center-block" src="/resources/img/tech-stack/git-logo.png">
   		    														</div>
   		    														<div class="col-lg-4" style="padding: 10px;">
   		    															<img class="center-block" src="/resources/img/tech-stack/maven-logo.png">
   		    														</div>
	   		    												</div>
   			    												<div class="thumbnail">
   			    													<div class="thumbnail row technology-stack-inner-logo" style="margin-top: 10px; margin-bottom: 10px;">   		    														
   		    															<div class="col-lg-6"> 
   		    																<img class="center-block" src="/resources/img/tech-stack/hibernate-logo.png">
   		    															</div>
   		    															<div class="col-lg-6" style="padding-top: 10px;"> 
    		    															<img class="center-block" src="/resources/img/tech-stack/mysql-logo.png">
   			    														</div>
    			    												</div>
   				    												<div class="thumbnail row technology-stack-inner-logo" style="margin-top: 10px; margin-bottom: 10px;">
   				    													<div class="col-lg-12">  														
   		    																<div class="col-lg-6" style="padding-top: 10px;">
   		    																	<div class="col-lg-12">
   		    																		<img class="center-block" src="/resources/img/tech-stack/jquery-logo.png">
   		    																	</div>
    		    																	<div class="col-lg-12">
   		    																		<img class="center-block" src="/resources/img/tech-stack/ajax-logo.png" style="padding-top: 10px;">	
   		    																	</div>  
	   		    																<div class="col-lg-12"> 
   			    																	<img class="center-block" src="/resources/img/tech-stack/freemarker-logo.png" style="padding-top: 20px; padding-bottom: 10px;">				
   			    																</div> 	
    		    															</div>    		    													
   				    														<div class="col-lg-6" style="padding-top: 10px;"> 
   			    																<img class="center-block" src="/resources/img/tech-stack/html-css-js-bootstrap-logo.png">				
   			    															</div>    
   		    															</div> 		    												
   		    														</div>
    		    													<div class="thumbnail row technology-stack-inner-logo" style="margin-top: 10px;">   			    													  		    												
   			    														<div class="col-lg-6"> 
   			    															<img class="center-block" src="/resources/img/tech-stack/spring-framework-logo.png">				
   			    														</div>    		    	
   		    															<div class="col-lg-6"> 
   		    																<img class="center-block" src="/resources/img/tech-stack/javaee-logo.png">				
	   		    														</div>    		    												
   			    													</div>    		    												
   			    												</div>
   		    												</div>
   		    											</div>
    		    									</div>
    		    								</div>
      										</div>      											
    									</div>
	  								</div>	
								</div>  
      						</div>
      					</div>
      					<div class="row">       					
        					<p class="col-lg-12 text-muted text-center" style="padding-top: 30px">
								${getTextSource('paganiHistory')} 2015 - ${.now?string("yyyy")}. ${getTextSource('footer.allRightsReserved')}.
							</p>
    	  				</div>
    				</div>     
    			</div>    	  
			</body>			
        </html>
        <@addSetPageLanguage/>
        <@addHandleContentSearch/>	
</#macro>

<#macro getCarProductionLife car>
    <#if car.productionStartDate??>	
		<#assign productionStartYear><@getYear car.productionStartDate/></#assign>
		${productionStartYear}
    </#if>  
    
    <#if car.productionEndDate??>
		<#assign productionEndYear><@getYear car.productionEndDate/></#assign>	
	<#else>
		<#assign productionEndYear></#assign>	
	</#if>
     
    <#if (productionStartYear?string != "" && productionEndYear?string != "") && (productionStartYear?string != productionEndYear?string)>		  
          - <@getYear car.productionEndDate/>
    <#elseif productionEndYear?string == "">
          - ${getTextSource('car.productionEndDate.inProduction')}    
    </#if>
</#macro>

<#macro getYear date>    
  ${date.time?string("yyyy")}
</#macro> 

<#function getTextSource text arguments=[""]>	
	<#assign message>
         <@spring.messageArgs text arguments/>
    </#assign>
	
	<#return message/>
</#function>

<#macro addBlueImpGallery>
	<#-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
	<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-use-bootstrap-modal="false">
    	<#-- The container for the modal slides -->
    	<div class="slides"></div>
    	<#-- Controls for the borderless lightbox -->
    	<h3 class="title"></h3>
    	<a class="prev">‹</a>
    	<a class="next">›</a>
    	<a class="close">×</a>
    	<a class="play-pause"></a>
    	<ol class="indicator"></ol>
    	<#-- The modal dialog, which will be used to wrap the lightbox content -->
    	<div class="modal fade">
        	<div class="modal-dialog">
           		<div class="modal-content">
           			<div class="modal-header">
           				<button type="button" class="close" aria-hidden="true">&times;</button>
           				<h4 class="modal-title"></h4>
           			</div>
           			<div class="modal-body next"></div>
           			<div class="modal-footer">
           				<button type="button" class="btn btn-default pull-left prev">
           					<i class="glyphicon glyphicon-chevron-left"></i>
           					${getTextSource('previous')}    
           				</button>
               			<button type="button" class="btn btn-primary next">
                   			${getTextSource('next')}
                   			<i class="glyphicon glyphicon-chevron-right"></i>
               			</button>
           			</div>
        		</div>
        	</div>
    	</div>
	</div>
</#macro>

<#macro addOperationResultMessage exceptionMessage, successMessage>
	<#if exceptionMessage?has_content>
		<div class="col-xs-12 alert alert-danger" role="alert">
			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
			<span class="sr-only">${getTextSource('error')}:</span>${exceptionMessage}
		</div>
	<#elseif successMessage?has_content>
		<div class="col-xs-12 alert alert-success" role="info">
			<span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
			<span class="sr-only">${getTextSource('info')}:</span>${successMessage}
		</div>
	</#if>    
</#macro>	