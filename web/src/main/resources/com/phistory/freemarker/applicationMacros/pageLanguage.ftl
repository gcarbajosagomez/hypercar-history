<#import "/spring.ftl" as spring/>
<#import "pagination.ftl" as pagination/>
<#import "genericFunctionalities.ftl" as generic/>
<#import "advertising.ftl" as advertising/>
<#import "picture.ftl" as picture/>

<#macro addSetPageLanguage chunkedModelsList=[]>

	<script type='application/javascript'>
		function setPageLanguage(locale, mainForm)
		{
		   if ($.cookie('${languageCookieName}') != locale && !ajaxCallBeingProcessed)
		   {
		   		ajaxCallBeingProcessed = true;
                var url = mainForm.action.replace(/\/(${languageEnglishCode}|${languageSpanishCode})\//, '/');
		   		var matches = mainForm.action.match(/^https?\:\/\/([^\/?#]+)(?:[\/?#]|$)/i);
               	var domain = matches && matches[0];

               	url = url.replace(domain, domain + '${manufacturerShortName}/' + locale + '/');

		   		$.ajax({
		        	type:'GET',
			        url: url,
		    	    contentType :'application/json; charset=UTF-8',
			        beforeSend: function(xhr)
		    	    {
                        if (locale == '${languageEnglishCode}') {
                            $('#english-loading-gif').removeClass('sr-only');
                        }
                        else if (locale == '${languageSpanishCode}') {
                            $('#spanish-loading-gif').removeClass('sr-only');
                        }

                        var regex = new RegExp('/' + locale + '/(.*)','g');
                        var replacedURL = url.replace(regex, "/$1");
                        window.history.pushState(null, '', replacedURL);
                        <@generic.addLoadingSpinnerToComponentScript "main-wrap-div"/>
                        addCRSFTokenToAjaxRequest(xhr);
		   			}
			   })
			   .done(function(data)
			   {
			        document.children[0].innerHTML = data;
					<#if requestIsCars || requestIsModelsSearch>
			            <#--Pagination is only created if the language change is called from the cars page and if needed -->
			            if ($('#car-list-div').length > 0)
			            {
			              	<#if requestIsCars && (chunkedModelsList?size > 0)>
								<#if requestIsCMS>
									<@pagination.addCMSCarsPagination chunkedModelsList/>
								<#else>
									<@pagination.addCarsPagination chunkedModelsList/>
								</#if>
							<#elseif requestIsModelsSearch>
								var contentSearchDto = {
										 				 ${pagNum} 			: <#if pagNumData??>${pagNumData}<#else>1</#if>,
				         				 				 ${carsPerPage} 	: <#if carsPerPageData??>${carsPerPageData}<#else>${defaultCarsPerPageData}</#if>,
										 				 ${contentToSearch} : $("#content-search-input")[0].value,
										 				 searchTotalResults : $("#search-total-results")[0].value
				         			   				   };
								<@pagination.createContentSearchPaginationFunction/>
							</#if>
			            }
					</#if>

                   <#if shouldReloadPictureGallery()>
					   <#if requestIsManufacturerHistory>
						   <@picture.addPicturesGalleryFunctionScript "images-gallery" "picture-gallery"/>
					   <#else>
						   <@picture.addPicturesGalleryFunctionScript "images-gallery" "carousel-inner"/>
					   </#if>
                   </#if>

		            ajaxCallBeingProcessed = false;
		            setupContentSearchEventListeners();

				   	<#if !requestIsCMS && !doNotTrack>
                        performGoogleAnalyticsRequest();

					   <#if !requestIsDesktop>
						   <@advertising.performSmaatoJSAdRequests/>
					   </#if>
				   	</#if>
					$('#main-wrap-div').unblock();
			   });
		   }
		}
	</script>
</#macro>

<#function shouldReloadPictureGallery>
    <#return requestIsManufacturerHistory || requestIsCarDetails || requestIsCarEdit>
</#function>

<#macro addHrefLangInfo>
	<#assign pageLanguages = [languageSpanishCode, languageEnglishCode]>
	<#assign requestContainsManufacturerData = requestContainsManufacturerData?? && requestContainsManufacturerData>
	<#list pageLanguages as language>
		<#if requestURI?contains("/" + language + "/")>
            <link rel="alternate" hreflang="${language}" href="${requestURI?replace("/" + language + "/", '/')}"/>
			<#if requestContainsManufacturerData>
            	<link rel="alternate" hreflang="${language}" href="/${manufacturerShortName}${requestURI?replace("/" + language + "/", '/')}"/>
			</#if>
		<#else>
            <#if requestURI?contains("/" + languageSpanishCode + "/")>
                <link rel="alternate" hreflang="${language}" href="${requestURI?replace("/" + languageSpanishCode + "/", "/" + languageEnglishCode + "/")}"/>
            	<link rel="alternate" hreflang="${language}" href="/${manufacturerShortName}${requestURI?replace("/" + languageSpanishCode + "/", "/")}"/>
				<#if requestContainsManufacturerData>
                	<link rel="alternate" hreflang="${language}" href="/${manufacturerShortName}${requestURI?replace("/" + languageSpanishCode + "/", "/" + languageEnglishCode + "/")}"/>
				</#if>
            <#elseif requestURI?contains("/" + languageEnglishCode + "/")>
                <link rel="alternate" hreflang="${language}" href="${requestURI?replace("/" + languageEnglishCode + "/", "/" + languageSpanishCode + "/")}"/>
            	<link rel="alternate" hreflang="${language}" href="/${manufacturerShortName}${requestURI?replace("/" + languageEnglishCode + "/", "/")}"/>
				<#if requestContainsManufacturerData>
                	<link rel="alternate" hreflang="${language}" href="/${manufacturerShortName}${requestURI?replace("/" + languageEnglishCode + "/", "/" + languageSpanishCode + "/")}"/>
				</#if>
			<#else>
                <link rel="alternate" hreflang="${language}" href="/${language}${requestURI}"/>
				<link rel="alternate" hreflang="${language}" href="/${manufacturerShortName}/${language}${requestURI}"/>
            </#if>
		</#if>
	</#list>
</#macro>

<#function getTextSource text arguments=[""]>
	<#assign message><@spring.messageArgs text arguments/></#assign>
	<#return message/>
</#function>