<#import "/spring.ftl" as spring/>
<#import "contactUs.ftl" as contactUs/>
<#import "pageLanguage.ftl" as language/>
<#import "contentSearch.ftl" as contentSearch/>
<#import "picture.ftl" as picture/>

<#macro startPage title=''>
    <@identifyRequestURL/>

	<!DOCTYPE html>
    	<#assign lang = language.getTextSource('paganiHistory.language')/>
        <html lang="${lang}" class="no-js">
            <head>
            		<title>${title} <#if title?? && (title?length > 0)> | </#if> ${language.getTextSource('paganiHistory')}</title>
            		<meta charset="UTF-8">
					<meta name="viewport" content="width=device-width, initial-scale=1.0">
					<meta name="description" content="${title}">
					<#-- CRSF token to protect against cross site attacks -->
					<meta name="_csrf" content="${_csrf.token}"/>
					<#-- default header name is X-CSRF-TOKEN -->
					<meta name="_csrf_header" content="${_csrf.headerName}"/>
                    <script type="application/ld+json">
                    {
                        "@context":"http://schema.org/",
                        "@type":"WebSite",
                        "name":"${language.getTextSource('paganiHistory')}",
                        "url":"${siteURL}",
                        "about":"${language.getTextSource('footer.aboutUs.text')}",
                        "keywords":"${language.getTextSource('pagani')}, ${language.getTextSource('structuredData.carsURL')}",
                        "potentialAction": {
                            "@type":"SearchAction",
                            "target":"${siteURL}/${modelsSearchURL}?${contentToSearch}={contentToSearch}",
                            "query-input": {
                                "@type":"PropertyValueSpecification",
                                "valueName":"contentToSearch",
                                "valueRequired":"http://schema.org/True"
                            }
                        }
                    }
                    </script>

					<@language.addHrefLangInfo/>
					<link rel="shortcut icon" href="/static/img/favicon.ico">
        			<link rel="stylesheet" href="/static/stylesheet/bootstrap.min.css">
					<link rel="stylesheet" href="/static/stylesheet/bootstrap-theme.min.css">
                	<link rel="stylesheet" href="/static/stylesheet/font-awesome.min.css">
        			<link rel="stylesheet" href="/static/stylesheet/main.min.css">

            		<script src="/static/javascript/lib/jquery.min.js"></script>
            		<script src="/static/javascript/lib/jquery.cookie.js"></script>
            		<script src="/static/javascript/lib/jquery.cookiesdirective.js"></script>
            		<script src="/static/javascript/lib/jquery.blockUI.js"></script>
        			<script src="/static/javascript/lib/bootstrap.min.js"></script>
                    <script src="/static/javascript/lib/bootstrap-paginator.min.js"></script>
        			<script src="/static/javascript/lib/bootbox.min.js"></script>
                    <script src="/static/javascript/lib/modernizr.custom.js"></script>
					<script src="/static/javascript/main.min.js"></script>

                    <#if requestIsCarDetails || requestIsCarEdit>
                        <link rel="stylesheet" href="/static/stylesheet/blueimp-gallery.min.css">
                        <link rel="stylesheet" href="/static/stylesheet/bootstrap-image-gallery.min.css">
                        <script src="/static/javascript/lib/jquery.blueimp-gallery.min.js"></script>
                        <script src="/static/javascript/lib/bootstrap-image-gallery.min.js"></script>
                    </#if>
                    <#if requestIsCMS>
                        <script src="/static/javascript/lib/bootstrap-datepicker.min.js"></script>
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
                            $.getScript('/static/javascript/lib/toucheffects.js', null);

        					$.cookiesDirective({
            					privacyPolicyUri: '/${cookiesPolicyURL}',
            					position: 'bottom',
								message: '${language.getTextSource('cookiesDirectiveMessage')}',
								deleteAndBlockCookiesMessage: '${language.getTextSource('cookiesDirectiveMessage.deleteAndBlockCookiesMessage')}',
								privacyPolicyMessage: '${language.getTextSource('cookiesDirectiveMessage.privacyPolicyMessage')}',
								acceptCookiesMessage: '${language.getTextSource('cookiesDirectiveMessage.acceptCookies')}',
								continueButton: '${language.getTextSource('cookiesDirectiveMessage.continueButton')}',
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

        					$('#contact-us-modal-div').on('show.bs.modal', function () {
    							setUpContactUsModal();
							});
    					});

                        <#if !requestIsCMS>
                            <@addGoogleAnalyticsScript/>
                        </#if>
					</script>
            </head>
            <body>
              	<div id="main-wrap-div">
	            	<div class="wrap">
    	        		<nav class="navbar navbar-default pagani-history-navbar" role="navigation">
        	    		    <#-- Brand and toggle get grouped for better mobile display -->
          					<div class="navbar-header">
                                <a class="navbar-brand pagani-history-navbar-brand" href='<@spring.url "/"/>'>
									<img class="main-logo" src="/static/img/pagani-history-logo.png">
								</a>

                                <a class="toggle-button navbar-toggle collapsed" data-toggle="collapse" data-target="#main-navbar-collapse" aria-expanded="false">
                                    <i id="navbar-toggle-arrow" class="fa fa-arrow-down" aria-hidden="true"></i>
                                </a>
        	  				 </div>

          					 <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 navbar-collapse-main-container">
          					 	<#-- Collect the nav links, forms, and other content for toggling -->
          					 	<div id="main-navbar-collapse" class="collapse navbar-collapse well">
	          						 <ul class="nav navbar-nav">
    	          						<li><a href='<@spring.url "/${carsURL}"/>'>${language.getTextSource('cars')}</a></li>
                                        <div class="navbar-divider"></div>
        	      						<li>
	        	  							<a id="language-dropdown-toggle" class="dropdown-toggle cursor-pointer" data-toggle="dropdown">${language.getTextSource('language')} <b class="caret"></b></a>
    	      								<ul class="dropdown-menu">
        	  									<li role="presentation">
          											<a id="spanish-language-link" class="cursor-pointer" role="menuitem" tabindex="-1" onClick="setPageLanguage('es', $('#main-form')[0]);">
          												<div class="row language-selection-div">
          													<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5 text-left">
          													    <h4>${language.getTextSource('language.spanish')}</h4>
          													</div>
															<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
          														<img class="language-flag" src='<@spring.url "/static/img/spain_flag.jpg"/>' title="${language.getTextSource('language.spanish')}"/>
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
																<h4>${language.getTextSource('language.english')}</h4>
          													</div>
															<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
          														<img class="language-flag" src='<@spring.url "/static/img/uk_flag.jpg"/>' title="${language.getTextSource('language.english')}"/>
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
                                            <div class="navbar-divider"></div>
              								<li>
              									<a id="cms-dropdown-toggle" class="dropdown-toggle cursor-pointer" data-toggle="dropdown">${language.getTextSource('cms')} <b class="caret"></b></a>
          										<ul class="dropdown-menu">
          											<li role="presentation">
          												<a href='<@spring.url "/${cmsContext}${manufacturersURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.listManufacturers')}</a>
													</li>
													<li role="presentation">
          												<a id="new-manufacturer-link" href='<@spring.url "/${cmsContext}${manufacturersURL}/${editURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.newManufacturer')}</a>
													</li>
													<li role="separator" class="divider"></li>
        	  										<li role="presentation">
          												<a href='<@spring.url "/${cmsContext}${carsURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.listCars')}</a>
													</li>
													<li role="presentation">
          												<a href='<@spring.url "/${cmsContext}${carsURL}/${editURL}"/>' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.newCar')}</a>
													</li>
													<li role="separator" class="divider"></li>
													<li role="presentation">
          												<a onClick="submitLoginForm(false);" class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.logout')}</a>
													</li>
          										</ul>
              								</li>
              							</#if>
                                        <div class="navbar-divider"></div>
              							<li class="search-container-li">
              								<div id="search-container">
												<a>${language.getTextSource('contentSearch.search')}</a>
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
        	    		<form id="main-form" action="${requestURI}" method="POST">
</#macro>

<#macro endPage chunkedModelsList=[]>
						</form>
					</div>

					<div class="pagani-history-footer navbar">
						<div class="row">
        					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<b>${language.getTextSource('footer.aboutUs')}</b>
								<p class="text-muted text-left">
									${language.getTextSource('footer.aboutUs.text')}
								</p>
        					</div>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<b>${language.getTextSource('footer.contactUs')}</b>
								<p class="text-muted text-left">
									${language.getTextSource('footer.contactUs.text')}
								</p>
	        				</div>
    	  				</div>
      					<div class="row lower-footer-row">
      						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
      							<b>${language.getTextSource('footer.technologyStack')}</b>
      							<p class="text-muted text-left">
									${language.getTextSource('footer.technologyStack.text')}
								</p>
								<@contactUs.createContactUsDialog/>
								<div id="technology-stack-modal-div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="technology-stack-label" aria-hidden="true"></div>
							</div>
                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <b>${language.getTextSource('title.cookiesPolicy')}</b>
                                <p class="text-muted text-left">
                                    ${language.getTextSource('footer.cookiesPolicy.text')}
                                </p>
                            </div>
      					</div>
                        <div class="divider"></div>
      					<div class="row" style="margin-bottom: 15px">
        					<p class="col-lg-12 text-muted text-center" style="padding-top: 15px">
								${language.getTextSource('paganiHistory')} 2015 - ${.now?string("yyyy")}. ${language.getTextSource('footer.allRightsReserved')}
							</p>
    	  				</div>
    				</div>
    			</div>
                <@addBackToTopButton/>
				<@language.addSetPageLanguage chunkedModelsList/>
				<@contentSearch.addHandleContentSearch/>
			</body>
        </html>
</#macro>

<#function getCarProductionLifeTime car>
    <#assign productionStartYear>${car.productionStartDate.time?string("yyyy")}</#assign>
    <#assign productionEndYear>${car.productionEndDate.time?string("yyyy")}</#assign>
    <#assign productionLifeTime = ""/>

    <#if productionEndYear != "">
        <#if productionStartYear != productionEndYear>
            <#assign productionLifeTime>${productionStartYear} - ${productionEndYear}</#assign>
        <#else>
            <#assign productionLifeTime>${productionStartYear}</#assign>
        </#if>
    <#else>
        <#assign productionLifeTime>
            ${productionStartYear} - ${language.getTextSource('car.productionEndDate.inProduction')}
        </#assign>
    </#if>
    <#return productionLifeTime/>
</#function>

<#macro addOperationResultMessage exceptionMessage, successMessage>
	<#if exceptionMessage?has_content>
		<div class="col-xs-12 alert alert-danger" role="alert">
			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
			<span class="sr-only">${language.getTextSource('error')}:</span>${exceptionMessage}
		</div>
	<#elseif successMessage?has_content>
		<div class="col-xs-12 alert alert-success" role="info">
			<span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
			<span class="sr-only">${language.getTextSource('info')}:</span>${successMessage}
		</div>
	</#if>    
</#macro>

<#macro addGoogleAnalyticsScript>
    (function(i,s,o,g,r,a,m)
    {
        i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })

    (window,document,'script','//www.google-analytics.com/analytics.js','ga');
    ga('create', 'UA-59713760-1', 'auto');
    ga('send', 'pageview');
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
                     '<i id="loading-gif" class="<#if requestIsDesktop>col-lg-2 col-md-4 col-sm-12 col-xs-12<#else>col-lg-4 col-md-4 col-sm-12 col-xs-4</#if> fa fa-circle-o-notch fa-4x fa-spin blue"></i>' +
                 '</div>'
    });
</#macro>

<#macro identifyRequestURL>
    <#global requestIsCMS = false/>
    <#global requestIsCarEdit = false/>
    <#global requestIsCars = false/>
    <#global requestIsCarDetails = false/>
    <#global requestIsModelsSearch = false/>

    <#if requestURI?contains(cmsContext)>
        <#global requestIsCMS = true/>
        <#if requestURI?matches("/" + cmsContext + carsURL + "/([0-9]{1,})/" + editURL)>
            <#global requestIsCarEdit = true/>
        </#if>
    </#if>
	<#if requestURI?contains(carsURL)>
        <#if requestURI?matches("/" + carsURL + "/([0-9]{1,})")>
            <#global requestIsCarDetails = true/>
        <#else>
            <#global requestIsCars = true/>
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