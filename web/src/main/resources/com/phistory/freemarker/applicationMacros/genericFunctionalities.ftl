<#import "/spring.ftl" as spring/>
<#import "contactUs.ftl" as contactUs/>
<#import "pageLanguage.ftl" as language/>
<#import "contentSearch.ftl" as contentSearch/>
<#import "picture.ftl" as picture/>
<#import "advertising.ftl" as advertising/>
<#import "cookiesDirective.ftl" as cookiesDirective/>

<#macro startPage title='' metaKeywords=language.getTextSource('meta.keywords.index') metaDescription=''>
    <@identifyRequestURL/>
	<#global triggerMobileAdvertisement = !requestIsDesktop && !doNotTrack && !requestIsCMS/>

	<!DOCTYPE html>
    	<#global lang = language.getTextSource('paganiHistory.language')/>
        <html lang="${lang}" class="no-js">
            <head>
            		<title>${title} <#if title?? && (title?length > 0)> | </#if> ${language.getTextSource('paganiHistory')}</title>
            		<meta charset="UTF-8">
					<meta name="viewport" content="width=device-width, initial-scale=1.0">
                	<meta name="keywords" content="${metaKeywords}">
					<meta name="description" content="<#if (metaDescription?length > 0)>${metaDescription}<#else>${title}</#if>">
					<#-- CRSF token to protect against cross site attacks -->
					<meta name="_csrf" content="${_csrf.token}"/>
					<#-- default header name is X-CSRF-TOKEN -->
					<meta name="_csrf_header" content="${_csrf.headerName}"/>

					<@language.addHrefLangInfo/>
					<link rel="shortcut icon" href="/static/img/favicon.ico">
        			<link rel="stylesheet" href="/static/stylesheet/bootstrap.min.css">
                	<link rel="stylesheet" href="/static/stylesheet/font-awesome.min.css">
        			<link rel="stylesheet" href="/static/stylesheet/main.min.css">
				    <#if !requestIsDesktop>
                        <link rel="stylesheet" href="/static/stylesheet/main-mobile.min.css">
				    </#if>
	                <#if requestIsCMS>
                        <link rel="stylesheet" href="/static/stylesheet/jquery.bootstrap-touchspin.min.css">
                    </#if>

            		<script src="/static/javascript/lib/jquery.min.js"></script>
            		<script src="/static/javascript/lib/jquery.cookie.min.js"></script>
            		<script src="/static/javascript/lib/jquery.cookiesdirective.min.js"></script>
            		<script src="/static/javascript/lib/jquery.blockUI.min.js"></script>
        			<script src="/static/javascript/lib/bootstrap.min.js"></script>
                    <script src="/static/javascript/lib/bootstrap-paginator.min.js"></script>
        			<script src="/static/javascript/lib/bootbox.min.js"></script>
                    <script src="/static/javascript/lib/modernizr.custom.min.js"></script>
					<script src="/static/javascript/main.js"></script>
                    <#if requestIsManufacturerHistory || requestIsCarDetails || requestIsCarEdit>
                        <link rel="stylesheet" href="/static/stylesheet/blueimp-gallery.min.css">
                        <script src="/static/javascript/lib/blueimp-gallery.min.js"></script>
                    </#if>
                    <#if requestIsCMS>
                        <script src="/static/javascript/lib/bootstrap-datepicker.min.js"></script>
                        <script src="/static/javascript/lib/jquery.bootstrap-touchspin.min.js"></script>
                    </#if>

        			<#-- since this file is imported at the beginning of each template, and then this macro is called, this function must be called after jQuery has been loaded -->
        			<script type='application/javascript'>
						var ajaxCallBeingProcessed = false;

                        <#-- this script needs all of the elements to have been created before it loads, therefore it must be included once the page has been loaded-->
                        document.addEventListener("touchstart", function(){}, true);

                        $(function() {
                            $('.navbar-collapse').on('shown.bs.collapse', function () {
                                $('#navbar-toggle-arrow').removeClass('fa-arrow-down');
                                $('#navbar-toggle-arrow').addClass('fa-arrow-up');
                            }).on( "hidden.bs.collapse", function() {
                                $('#navbar-toggle-arrow').removeClass('fa-arrow-up');
                                $('#navbar-toggle-arrow').addClass('fa-arrow-down');
                            });;
                        });

						$(document).ready(function()
						{
                            <#-- this script needs all of the elements to have been created before it loads, therefore it must be included once the page has been loaded-->
                            $.getScript('/static/javascript/lib/toucheffects.min.js', null);

                            <@cookiesDirective.loadCookiesDirectiveScript/>
        					setupContentSearchEventListeners();

        					$('#contact-us-modal-div').on('show.bs.modal', function () {
    							setUpContactUsModal();
							});
    					});

                        <#if !requestIsCMS && !doNotTrack>
                        	performGoogleAnalyticsRequest();
                        </#if>
					</script>
            </head>
            <body>
              	<div id="main-wrap-div">
	            	<div class="wrap">
    	        		<nav class="navbar navbar-default pagani-history-navbar" role="navigation">
          					<div class="navbar-header">
                                <a class="navbar-brand pagani-history-navbar-brand" href='<@spring.url "/"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'>
									<img class="main-logo" src="/static/img/pagani-history-logo.png" alt="Home page" title="Home page">
								</a>

                                <a class="toggle-button navbar-toggle collapsed" data-toggle="collapse" data-target="#main-navbar-collapse" aria-expanded="false">
                                    <i id="navbar-toggle-arrow" class="fa fa-arrow-down" aria-hidden="true"></i>
                                </a>
        	  				</div>

          					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 navbar-collapse-main-container">
          					 	<div id="main-navbar-collapse" class="collapse navbar-collapse well bordered-div">
	          						 <ul class="nav navbar-nav">
                                     	<li><a href='<@spring.url "/${manufacturerHistoryURL}"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'>${language.getTextSource('manufacturer.history.headerLinkValue')?upper_case}</a></li>
                                        <hr class="navbar-divider">
										<li><a href='<@spring.url "/${carsURL}"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'>${carsHeaderLinkValue?upper_case}</a></li>
                                        <hr class="navbar-divider">
        	      						<li>
	        	  							<a id="language-dropdown-toggle" class="dropdown-toggle cursor-pointer" data-toggle="dropdown">${language.getTextSource('language')?upper_case} <b class="caret"></b></a>
    	      								<ul class="dropdown-menu">
        	  									<li role="presentation">
          											<a id="spanish-language-link" class="cursor-pointer" role="menuitem" tabindex="-1" onClick="setPageLanguage('${languageSpanishCode}', $('#main-form')[0]);">
          												<div class="row language-selection-div">
          													<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 text-left">
          													    <h4>${language.getTextSource('language.spanish')?upper_case}</h4>
          													</div>
															<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
          														<img class="language-flag" src='<@spring.url "/static/img/spain_flag.jpg"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'
                                                                     alt="${language.getTextSource('language.spanish')?upper_case}"/>
          													</div>
          													<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 center-block">
	          													<i id="spanish-loading-gif" class="fa fa-circle-o-notch fa-lg fa-spin blue sr-only"></i>
    	      												</div>
	    	      										</div>
    	    	  									</a>
        	  									</li>
          										<hr role="separator" class="divider">
          										<li role="presentation">
          											<a id="english-language-link" class="cursor-pointer" role="menuitem" tabindex="-1" onClick="setPageLanguage('${languageEnglishCode}', $('#main-form')[0]);">
														<div class="row language-selection-div">
    	      												<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 text-left">
																<h4>${language.getTextSource('language.english')?upper_case}</h4>
          													</div>
															<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
          														<img class="language-flag" src='<@spring.url "/static/img/uk_flag.jpg"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'
                                                                     alt="${language.getTextSource('language.english')?upper_case}"/>
															</div>
	          												<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 center-block">
		          												<i id="english-loading-gif" class="fa fa-circle-o-notch fa-lg fa-spin blue sr-only"></i>
    		      											</div>
        		  										</div>
          											</a>
          										</li>
          									</ul>
              							</li>
              							<#if requestIsCMS && (loggedIn?? && loggedIn)>
                                            <hr class="navbar-divider">
              								<li>
              									<a id="cms-dropdown-toggle" class="dropdown-toggle cursor-pointer" data-toggle="dropdown">${language.getTextSource('cms')?upper_case} <b class="caret"></b></a>
          										<ul class="dropdown-menu">
          											<li role="presentation">
          												<a href='<@spring.url "/${cmsContext}${manufacturersURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.listManufacturers')?upper_case}</a>
													</li>
													<li role="presentation">
          												<a id="new-manufacturer-link" href='<@spring.url "/${cmsContext}${manufacturersURL}/${editURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.newManufacturer')?upper_case}</a>
													</li>
                                                    <hr role="separator" class="divider">
        	  										<li role="presentation">
          												<a href='<@spring.url "/${cmsContext}${carsURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.listCars')?upper_case}</a>
													</li>
													<li role="presentation">
          												<a href='<@spring.url "/${cmsContext}${carsURL}/${editURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.newCar')?upper_case}</a>
													</li>
                                                    <hr role="separator" class="divider">
													<li role="presentation">
          												<a onClick="submitLoginForm(false);" class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.logout')?upper_case}</a>
													</li>
          										</ul>
              								</li>
              							</#if>
                                        <hr class="navbar-divider">
              							<li class="search-container-li">
              								<div id="search-container">
												<a>${language.getTextSource('contentSearch.search')?upper_case}</a>
												<input id="content-search-input" type="text" class="content-search-input" value="<#if contentToSearchData??>${contentToSearchData}</#if>"/>
												<label for="content-search-input">
													<span id="content-search-span" class="glyphicon glyphicon-search search-icon"></span>
												</label>
												<input id="search-total-results" type="hidden" value="<#if searchTotalResultsData??>${searchTotalResultsData}<#else>0</#if>"/>
											</div>
              							</li>
              						 </ul>
 	         					 </div>
							</div>
    	        		</nav>
						<#if triggerMobileAdvertisement>
                            <div id="below-the-header-medium-banner" class="col-lg-12 center-block below-the-header-mobile-banner-div mobile-medium-banner-div"></div>
						</#if>

        	    		<form id="main-form" action="${requestURI}" method="POST">
</#macro>

<#macro endPage chunkedModelsList=[]>
						</form>
					</div>

					<div class="pagani-history-footer navbar">
						<div class="row">
        					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<h4>${language.getTextSource('footer.aboutUs')?upper_case}</h4>
								<p class="text-muted text-left">
									${language.getTextSource('footer.aboutUs.text', [models?size])}
								</p>
        					</div>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <h4>${language.getTextSource('footer.contactUs')?upper_case}</h4>
								<p class="text-muted text-left">
									${language.getTextSource('footer.contactUs.text')}
								</p>
	        				</div>
    	  				</div>
      					<div class="row lower-footer-row">
      						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <h4>${language.getTextSource('footer.technologyStack')?upper_case}</h4>
      							<p class="text-muted text-left">
									${language.getTextSource('footer.technologyStack.text')}
								</p>
								<@contactUs.createContactUsDialog/>
								<div id="technology-stack-modal-div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="technology-stack-label" aria-hidden="true"></div>
							</div>
                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <h4>${language.getTextSource('meta.title.cookiesPolicy')?upper_case}</h4>
                                <p class="text-muted text-left">
                                    ${language.getTextSource('footer.cookiesPolicy.text')}
                                </p>
                            </div>
      					</div>
                        <hr>
      					<div class="row" style="margin-bottom: 15px">
        					<p class="col-lg-12 text-muted text-center" style="padding-top: 15px">
								${language.getTextSource('paganiHistory')} 2015 - ${.now?string("yyyy")}. ${language.getTextSource('footer.allRightsReserved')}
							</p>
    	  				</div>
    				</div>
    			</div>
                <@addBackToTopButton/>
				<@language.addSetPageLanguage chunkedModelsList/>
				<@contentSearch.addHandleContentSearchFunctionScript/>
				<#if triggerMobileAdvertisement>
					<@advertising.addHTMLPerformSmaatoAdRequestsScript/>
				</#if>
			</body>
        </html>
</#macro>

<#macro addLoadingSpinnerToComponentScript componentId>
    $('#${componentId}').block({
        css: {
			<#if requestIsDesktop>
			    width: '100%',
			</#if>
            border: '0px solid',
            backgroundColor: 'rgba(94, 92, 92, 0)'
        },
        message: '<div class="row" style="<#if requestIsDesktop>width: 60%; margin-left: 40%<#else>width: 320px; margin-left: -50px</#if> !important;">' +
                     '<h1 class="<#if requestIsDesktop>col-lg-4 col-md-6 col-sm-12 col-xs-12<#else>col-lg-4 col-md-6 col-sm-12 col-xs-6</#if>" style="color: #fff">${language.getTextSource('loading')}</h1>' +
                     '<i id="loading-gif" class="<#if requestIsDesktop>col-lg-2 col-md-4 col-sm-12 col-xs-12<#else>col-lg-4 col-md-4 col-sm-12 col-xs-4</#if> fa fa-snowflake-o fa-4x fa-spin blue"></i>' +
                 '</div>'
    });
</#macro>

<#macro identifyRequestURL>
    <#global requestIsManufacturerHistory = false/>
    <#global requestIsCMS = false/>
    <#global requestIsCarEdit = false/>
    <#global requestIsCars = false/>
    <#global requestIsCarDetails = false/>
    <#global requestIsModelsSearch = false/>

	<#if requestURI?contains(manufacturerHistoryURL)>
		<#global requestIsManufacturerHistory = true/>
	</#if>
    <#if requestURI?contains(cmsContext)>
        <#global requestIsCMS = true/>
        <#if requestURI?matches("/" + cmsContext + carsURL + "/([0-9]{1,})/" + editURL + ".{0,}")>
            <#global requestIsCarEdit = true/>
        </#if>
    </#if>
	<#if requestURI?contains(carsURL)>
        <#if requestURI?matches(".*/" + carsURL + "[^/]*")>
			<#global requestIsCars = true/>
        <#else>
			<#global requestIsCarDetails = true/>
        </#if>
    </#if>
    <#if requestURI?contains(modelsSearchURL)>
        <#global requestIsModelsSearch = true/>
    </#if>
</#macro>

<#macro addBackToTopButton>
    <a href="#" class="toggle-button back-to-top" style="display: none;">
        <i class="fa fa-arrow-up"></i>
    </a>

    <script type='application/javascript'>
        $(document).ready(function() {
            var offset = 250;
            var duration = 300;
            $(window).scroll(function() {
                if ($(this).scrollTop() > offset) {
                    $('.back-to-top').fadeIn(duration);
                } else {
                    $('.back-to-top').fadeOut(duration);
                }
            });

            $('.back-to-top').click(function(event) {
                event.preventDefault();
                $('html, body').animate({scrollTop: 0}, duration);
                return false;
            })
        });
    </script>
</#macro>

<#function normalizeDatabaseString string>
	<#assign normalizedString>${string?replace("\\r\\n", "<br/>")}</#assign>
	<#assign normalizedString>${normalizedString?replace("\\", "")}</#assign>
	<#assign normalizedString>${normalizedString?replace("(.)(.{0,})", "<d class=\"big-text\">$1</d><d>$2</d>", "r")}</#assign>
	<#return normalizedString/>
</#function>