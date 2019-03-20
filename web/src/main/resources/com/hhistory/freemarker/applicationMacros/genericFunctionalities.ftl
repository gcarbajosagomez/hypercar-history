<#import "/spring.ftl" as spring/>
<#import "contactUs.ftl" as contactUs/>
<#import "pageLanguage.ftl" as language/>
<#import "contentSearch.ftl" as contentSearch/>
<#import "picture.ftl" as picture/>
<#import "cookiesDirective.ftl" as cookiesDirective/>
<#import "uriUtils.ftl" as uriUtils/>
<#import "emoji.ftl" as emoji/>

<#macro startPage title='' metaKeywords='' metaDescription=''>
    <@uriUtils.identifyRequestURL/>
	<#global triggerMobileAdvertisement = !requestIsDesktop && !doNotTrack && !requestIsCMS/>
	<#global manufacturerShortName = manufacturer.getShortName()/>
	<#global manufacturerName = manufacturer.getName()/>

	<!DOCTYPE html>
    	<#global lang = language.getTextSource('hypercarHistory.language')/>
        <html lang="${lang}" class="no-js">
            <head>
            		<title>${title} <#if title?? && (title?length > 0)> | </#if> ${language.getTextSource('${manufacturerName}History')}</title>
            		<meta charset="UTF-8">
					<meta name="viewport" content="width=device-width, initial-scale=1.0">
                	<meta name="keywords" content="${metaKeywords}">
					<meta name="description" content="<#if (metaDescription?length > 0)>${metaDescription}<#else>${title}</#if>">
					<#-- CRSF token to protect against cross site attacks -->
					<meta name="_csrf" content="${_csrf.token}"/>
					<#-- default header name is X-CSRF-TOKEN -->
					<meta name="_csrf_header" content="${_csrf.headerName}"/>

					<@language.addHrefLangInfo/>
					<link rel="shortcut icon" href="/static/${manufacturerName}/favicon.ico">
        			<link rel="stylesheet" href="/static/stylesheet/bootstrap.min.css">
                	<link rel="stylesheet" href="/static/stylesheet/font-awesome.min.css">
        			<link rel="stylesheet" href="/static/stylesheet/main.min.css">
        			<link rel="stylesheet" href="/static/stylesheet/${manufacturerName}/main.min.css">
				    <#if !requestIsDesktop>
                        <link rel="stylesheet" href="/static/stylesheet/main-mobile.min.css">
                        <link rel="stylesheet" href="/static/stylesheet/${manufacturerName}/main-mobile.min.css">
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
					<script src="/static/javascript/main.min.js"></script>
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
                            const navBarToggleArrow = $('#navbar-toggle-arrow');
                            $('.navbar-collapse').on('shown.bs.collapse', function () {
                                navBarToggleArrow.removeClass('fa-arrow-down');
                                navBarToggleArrow.addClass('fa-arrow-up');
                            }).on( "hidden.bs.collapse", function() {
                                navBarToggleArrow.removeClass('fa-arrow-up');
                                navBarToggleArrow.addClass('fa-arrow-down');
                            });
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
    	        		<nav class="navbar navbar-default hypercar-history-navbar" role="navigation">
          					<div class="navbar-header">
								<a class="navbar-brand hypercar-history-navbar-brand" href='${uriUtils.buildDomainURI("/")}'>
									<img class="main-logo" src="/static/${manufacturerName}/main-logo.png" alt="Home page" title="Home page">
								</a>

                                <a class="toggle-button navbar-toggle collapsed" data-toggle="collapse" data-target="#main-navbar-collapse" aria-expanded="false">
                                    <i id="navbar-toggle-arrow" class="fa fa-arrow-down" aria-hidden="true"></i>
                                </a>
        	  				</div>

          					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 navbar-collapse-main-container">
          					 	<div id="main-navbar-collapse" class="collapse navbar-collapse well bordered-div">
	          						 <ul class="nav navbar-nav">
										 <li><a href='${uriUtils.buildDomainURI("/${manufacturerHistoryURL}")}'>${language.getTextSource('${manufacturerName}.history.headerLinkValue')?upper_case}</a></li>
                                         <hr class="navbar-divider">
										 <li><a href='${uriUtils.buildDomainURI("/${carsURL}")}'>${carsHeaderLinkValue?upper_case}</a></li>
                                         <hr class="navbar-divider">
                                         <li>
                                             <a id="language-dropdown-toggle" class="dropdown-toggle cursor-pointer" data-toggle="dropdown">${language.getTextSource('otherStories')?upper_case} <b class="caret"></b></a>
                                             <ul class="dropdown-menu">
												 <#list manufacturerEntities as manufacturer>
                                                     <!-- TODO remove check after Koenigsegg is enabled -->
													 <#if manufacturer.name?lower_case != 'koenigsegg'>
                                                         <#if manufacturerName?lower_case != manufacturer.name?lower_case>
                                                             <li role="presentation">
                                                                 <a href="${language.getTextSource('${manufacturer.name?lower_case}.website')}" target="_blank" class="cursor-pointer" role="menuitem" tabindex="-1">
                                                                     <div class="row other-stories-dropdown-selection-div">
                                                                         <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7 text-left">
                                                                             <h4 title="${manufacturer.name} History">${manufacturer.name?upper_case} HISTORY</h4>
                                                                         </div>
                                                                         <div class="col-lg-5 col-md-5 col-sm-5 col-xs-5 pull-right">
                                                                             <img class="manufacturer-logo pull-right"
                                                                                  src='<@spring.url "/${picturesURL}/${loadManufacturerLogoAction}?${id}=${manufacturer.id}"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'
                                                                                  alt="${manufacturer.name} History"
                                                                                  title="${manufacturer.name} History"/>
                                                                         </div>
                                                                     </div>
                                                                 </a>
                                                             </li>
                                                             <#assign index = manufacturer?index/>
                                                             <!-- TODO revert to -1 after Koenigsegg is enabled -->
                                                             <#if index < (manufacturerEntities?size - 2)>
                                                                 <#if manufacturerName?lower_case != manufacturerEntities[index + 1]?lower_case>
                                                                     <hr role="separator" class="divider">
                                                                 </#if>
                                                             </#if>
                                                         </#if>
                                                     </#if>
												 </#list>
                                             </ul>
                                         </li>
                                         <hr class="navbar-divider">
        	      						<li>
	        	  							<a id="language-dropdown-toggle" class="dropdown-toggle cursor-pointer" data-toggle="dropdown">${language.getTextSource('language')?upper_case} <b class="caret"></b></a>
    	      								<ul class="dropdown-menu">
        	  									<li role="presentation">
          											<a id="spanish-language-link" class="cursor-pointer" role="menuitem" tabindex="-1" onClick="setPageLanguage('${languageSpanishCode}', $('#main-form')[0]);">
          												<div class="row dropdown-selection-div">
          													<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 text-left">
          													    <h4 title="${language.getTextSource('language.spanish')}">${language.getTextSource('language.spanish')?upper_case}</h4>
          													</div>
															<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                                                                <p class="language-flag">${emoji.getFlagEmojiByLanguage('spanish')}</p>
          													</div>
          													<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 center-block">
	          													<i id="spanish-loading-gif" class="fa fa-circle-o-notch fa-lg fa-spin manufacturer-colour sr-only"></i>
    	      												</div>
	    	      										</div>
    	    	  									</a>
        	  									</li>
          										<hr role="separator" class="divider">
          										<li role="presentation">
          											<a id="english-language-link" class="cursor-pointer" role="menuitem" tabindex="-1" onClick="setPageLanguage('${languageEnglishCode}', $('#main-form')[0]);">
														<div class="row dropdown-selection-div">
    	      												<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 text-left">
																<h4 title="${language.getTextSource('language.english')}">${language.getTextSource('language.english')?upper_case}</h4>
          													</div>
															<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
																<p class="language-flag">${emoji.getFlagEmojiByLanguage('english')}</p>
															</div>
	          												<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 center-block">
		          												<i id="english-loading-gif" class="fa fa-circle-o-notch fa-lg fa-spin manufacturer-colour sr-only"></i>
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
          												<a href='${uriUtils.buildDomainURI("/${cmsContext}${manufacturersURL}")}' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.listManufacturers')?upper_case}</a>
													</li>
													<li role="presentation">
          												<a id="new-manufacturer-link" href='${uriUtils.buildDomainURI("/${cmsContext}${manufacturersURL}/${editURL}")}' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.newManufacturer')?upper_case}</a>
													</li>
                                                    <hr role="separator" class="divider">
        	  										<li role="presentation">
          												<a href='${uriUtils.buildDomainURI("/${cmsContext}${carsURL}")}' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.listCars')?upper_case}</a>
													</li>
													<li role="presentation">
          												<a href='${uriUtils.buildDomainURI("/${cmsContext}${carsURL}/${editURL}")}' class="cursor-pointer" role="menuitem" tabindex="-1">${language.getTextSource('cms.newCar')?upper_case}</a>
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

					<div class="hypercar-history-footer navbar">
						<div class="row">
        					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<h4 class="hypercar-history-footer-header">${language.getTextSource('footer.aboutUs')?upper_case}</h4>
								<p class="text-muted text-left">
									${language.getTextSource('${manufacturerName}.footer.aboutUs.text',
															 [uriUtils.buildDomainURI("/${carsURL}?pn=1&cpp=${visibleModels?size}"), visibleModels?size])}
								</p>
        					</div>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <h4>${language.getTextSource('footer.contactUs')?upper_case}</h4>
								<p class="text-muted text-left">
									${language.getTextSource('${manufacturerName}.footer.contactUs.text')}
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
                        <div class="row">
                            <h4 class="col-lg-12 text-center">
								${language.getTextSource('footer.donations')?upper_case}
                            </h4>
                            <p class="col-lg-12 text-muted <#if requestIsDesktop>text-center<#else>text-left</#if>">${language.getTextSource('footer.donations.text')}</p>

							<h5 class="text-muted <#if requestIsDesktop>text-center<#else>text-left</#if>"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 226.777 226.777" fill="#777"><title>${language.getTextSource('footer.donations.donate')} ${language.getTextSource('bitcoin')}</title><path d="M182.981 112.854c-7.3-5.498-17.699-7.697-17.699-7.697s8.8-5.102 12.396-10.199c3.6-5.099 5.399-12.999 5.7-17.098.299-4.101 1-21.296-12.399-31.193-10.364-7.658-22.241-10.698-38.19-11.687V.278h-21.396V34.57H95.096V.278H73.702V34.57H31.61v22.219h12.372c3.373 0 9.372.375 11.921 3.228 2.55 2.848 3 4.349 3 9.895l.001 88.535c0 2.099-.4 4.697-2.201 6.398-1.798 1.701-3.597 2.098-7.898 2.098H36.009l-4.399 25.698h42.092v34.195h21.395v-34.195h16.297v34.195h21.396v-34.759c5.531-.323 10.688-.742 13.696-1.136 6.1-.798 19.896-2.398 32.796-11.397 12.896-9 15.793-23.098 16.094-37.294.304-14.197-5.102-23.897-12.395-29.396zM95.096 58.766s6.798-.599 13.497-.501c6.701.099 12.597.3 21.398 3 8.797 2.701 13.992 9.3 14.196 17.099.199 7.799-3.204 12.996-9.2 16.296-5.998 3.299-14.292 5.099-22.094 5.396-7.797.301-17.797 0-17.797 0v-41.29zm47.89 102.279c-4.899 2.701-14.698 5.1-24.194 5.798-9.499.701-23.696.401-23.696.401v-45.893s13.598-.698 24.197 0c10.597.703 19.495 3.4 23.492 5.403 3.999 1.998 11 6.396 11 16.896 0 10.496-5.903 14.696-10.799 17.395z"/></svg>
                             	: ${language.getTextSource('footer.donations.btc.address')}</h5>
                            <h5 class="text-muted <#if requestIsDesktop>text-center<#else>text-left</#if>"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 226.777 226.777" fill="#777"><title>${language.getTextSource('footer.donations.donate')} ${language.getTextSource('litecoin')}</title><path d="M94.718 184.145l12.778-60.835 64.578-44.271 7.57-36.156-64.591 44.452L133.398 0h-49.61L57.142 127.189l-27.167 18.698-6.308 34.894 25.972-17.806-13.358 63.768h158.917l8.829-42.598z"/></svg>
                                : ${language.getTextSource('footer.donations.ltc.address')}</h5>
                            <h5 class="text-muted <#if requestIsDesktop>text-center<#else>text-left</#if>"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 226.777 226.777" fill="#777"><title>${language.getTextSource('footer.donations.donate')} ${language.getTextSource('ether')}</title><path d="M112.553 157V86.977l-68.395 29.96zm0-74.837V-.056L46.362 111.156zM116.962-.09v82.253l67.121 29.403zm0 87.067v70.025l68.443-40.045zm-4.409 140.429v-56.321L44.618 131.31zm4.409 0l67.935-96.096-67.935 39.775z"/></svg>
                                : ${language.getTextSource('footer.donations.eth.address')}</h5>
							<h5 class="text-muted <#if requestIsDesktop>text-center<#else>text-left</#if>"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 32 32" fill="#777"><title>${language.getTextSource('footer.donations.donate')} ${language.getTextSource('nano')}</title><path d="M16 32C7.163 32 0 24.837 0 16S7.163 0 16 0s16 7.163 16 16-7.163 16-16 16zm8.95-21c-.858 0-1.583.693-1.583 1.583 0 1.254-.198 1.583-1.584 1.583h-.132a1.548 1.548 0 0 0-1.418 1.55v.034c0 1.22-.23 1.517-1.583 1.517-.066 0-.132 0-.165.033-.792.099-1.418.758-1.418 1.55 0 .858.692 1.583 1.583 1.583.825 0 1.517-.66 1.55-1.451v-.132c0-1.121.363-1.55 1.55-1.583h.033c.825 0 1.518-.66 1.55-1.484v-.1c0-1.154.363-1.583 1.584-1.583.857 0 1.583-.692 1.583-1.583 0-.824-.693-1.517-1.55-1.517zm-9.302 3.166h-.132c-1.385 0-1.583-.33-1.583-1.583a1.584 1.584 0 0 0-3.166 0c0 1.254-.198 1.55-1.583 1.55h-.132a1.548 1.548 0 0 0-1.419 1.55c0 .858.693 1.584 1.584 1.584.824 0 1.517-.66 1.55-1.451v-.1c0-1.154.363-1.583 1.583-1.583s1.583.43 1.583 1.55c0 .858.693 1.584 1.583 1.584.891 0 1.584-.693 1.584-1.583a1.597 1.597 0 0 0-1.452-1.518zm-9.565 6.267a1.583 1.583 0 1 0 0-3.166 1.583 1.583 0 0 0 0 3.166z"/></svg>
                                : ${language.getTextSource('footer.donations.nano.address')}</h5>
                            <h5 class="text-muted <#if requestIsDesktop>text-center<#else>text-left</#if>"><svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 227 227" fill="#777"><title>${language.getTextSource('footer.donations.donate')} ${language.getTextSource('bitcoinCash')}</title><path d="M113.666 43.5c-5.767-.01-11.629.7-17.475 2.191v-.003c-37.41 9.551-59.99 47.587-50.431 84.957 9.56 37.37 47.637 59.925 85.049 50.375 37.412-9.548 59.99-47.584 50.432-84.954-8.067-31.53-36.434-52.514-67.575-52.566zM0 43.82V183.5h64.531c-22-15.421-36.383-40.953-36.383-69.84 0-28.886 14.382-54.418 36.383-69.84H0zm162.469 0c22 15.422 36.383 40.954 36.383 69.84 0 28.887-14.382 54.419-36.383 69.84H227V43.82h-64.531zm-50.832 23.037l3.465 13.545c6.33-1.205 11.273-1.202 16.087.754 6.224 2.526 7.666 9.308 7.96 10.938.291 1.63.377 4.894-.521 7.24-.896 2.35-3.824 5.219-3.824 5.219s4.279-.179 7.683 1.24c3.397 1.42 6.479 4.664 7.778 10.236 1.303 5.569 1.574 11.361-2.564 16.159-4.039 4.679-9.113 6.699-11.498 7.649l-.179.07c-1.136.455-3.108 1.133-5.237 1.81l3.469 13.565-8.359 2.135-3.415-13.346-6.367 1.625 3.414 13.346-8.359 2.135-3.414-13.348-16.447 4.197-.846-10.469 5-1.275c1.68-.431 2.342-.764 2.875-1.605.533-.844.43-1.899.22-2.721l-8.837-34.553c-.554-2.165-.88-2.705-2.16-3.563-1.282-.859-3.663-.409-4.983-.069l-4.832 1.231-2.219-8.672 16.448-4.197-3.427-13.383 8.36-2.133 3.423 13.385 6.367-1.625-3.424-13.385 8.363-2.135zm3.666 24.047c-2.965-.012-5.03.447-7.33.998-2.627.63-5.225 1.543-5.225 1.543l4.121 16.116s3.938-.881 6.955-1.776c3.018-.896 6.082-2.422 8.094-4.308 2.015-1.887 2.824-4.256 1.969-7.28-.86-3.024-3.547-5.08-7.254-5.258a31.112 31.112 0 0 0-1.33-.035zm8.926 24.002a32.93 32.93 0 0 0-5.777.555c-4.213.784-9.456 2.412-9.456 2.412l4.582 17.91s5.579-1.296 9.219-2.518c3.641-1.22 7.229-3.133 8.875-4.676 1.643-1.542 3.529-3.771 2.481-7.867-1.048-4.098-4.221-5.116-5.983-5.498-.881-.191-2.258-.328-3.941-.318z"/></svg>
								: ${language.getTextSource('footer.donations.bch.address')}</h5>
                        </div>
                        <div class="row lower-footer-row" style="margin-bottom: 15px">
                            <p class="col-lg-12 text-muted text-center" style="padding-top: 15px">
								${language.getTextSource('${manufacturerName}History')} 2015 - ${.now?string("yyyy")}. ${language.getTextSource('footer.allRightsReserved')}
                            </p>
                        </div>
    				</div>
    			</div>
                <@addBackToTopButton/>
				<@language.addSetPageLanguage chunkedModelsList/>
				<@contentSearch.addHandleContentSearchFunctionScript/><#--
				<#if triggerMobileAdvertisement>
					<@advertising.addHTMLPerformSmaatoAdRequestsScript/>
				</#if>-->
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
                     '<i id="loading-gif" class="<#if requestIsDesktop>col-lg-2 col-md-4 col-sm-12 col-xs-12<#else>col-lg-4 col-md-4 col-sm-12 col-xs-4</#if> fa fa-snowflake-o fa-4x fa-spin manufacturer-colour"></i>' +
                 '</div>'
    });
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