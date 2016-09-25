<#import "/spring.ftl" as spring/>
<#import "pagination.ftl" as pagination/>
<#import "genericFunctionalities.ftl" as generic/>

<#macro addSetPageLanguage chunkedModelsList=[]>

	<script type='application/javascript'>
		function setPageLanguage(locale, mainForm)
		{  
		   if ($.cookie('${languageCookieName}') != locale && !ajaxCallBeingProcessed) 
		   { 
		   		ajaxCallBeingProcessed = true;
		   		
				if (mainForm.action.search(/[&?]${languageQueryString}=/) != -1)
				{
					<#--The lang param must be removed from the URL before sending it -->
					mainForm.action = mainForm.action.replace(/[&?]${languageQueryString}=e[ns]/,'');
				}
		   		
		   		$.ajax({            
		        	type:'GET',
			        url: mainForm.action,
		    	    contentType :'application/json; charset=UTF-8',
		        	data: { ${languageQueryString}: locale },
			        beforeSend: function(xhr)
		    	    {
		        	    if(locale == 'en')
		            	{
			                $('#english-loading-gif').removeClass('sr-only');
		    	        }
		        	    else if(locale == 'es')
		            	{
		                	$('#spanish-loading-gif').removeClass('sr-only');
			            }

                        window.history.pushState(null, '', mainForm.action);
                        <@generic.addLoadingSpinnerToComponentScript "main-wrap-div"/>
                        addCRSFTokenToAjaxRequest(xhr);
		   			}
			   })
			   .done(function(data)
			   {           
			        document.children[0].innerHTML = data;
					<#if (requestURI?matches("/" + carsURL + "([0-9]{0})") && !requestURI?contains(cmsContext)) || requestURI?contains(modelsSearchURL)>
			            <#--Pagination is only created if the language change is called from the cars page and if needed -->
			            if ($('#car-list-div').length > 0)
			            {
			              	<#if requestURI?contains(carsURL) && (chunkedModelsList?size > 0)>
			              		<@pagination.createCarsPagination chunkedModelsList/>
			              	<#elseif requestURI?contains(modelsSearchURL)>
								var contentSearchDto = {
										 				 ${pagNum} 			: 1,
				         				 				 ${carsPerPage} 	: <#if carsPerPageData??>${carsPerPageData}<#else>8</#if>,
										 				 ${contentToSearch} : $("#content-search-input")[0].value,
										 				 searchTotalResults : $("#search-total-results")[0].value
				         			   				   };
								<@pagination.createContentSearchPaginationFunction/>
							</#if>
			            }
					</#if>
		               
		            ajaxCallBeingProcessed = false;
		            setupContentSearchEventListeners();	
					$('#main-wrap-div').unblock();           	
			   });
		   }
		}
	</script>
</#macro>

<#macro addHrefLangInfo>
	<#assign pageLanguages = ['en', 'es']>
	<#list pageLanguages as language>		
		<#if requestURI?contains(languageQueryString + "=")>
			<link rel="alternate" hreflang="${language}" href="${requestURI?replace(languageQueryString + "=" + "\\w{2}\\b", languageQueryString + "=" + language, 'r')}"/>
			<#if requestURI?contains(language)>
				<link rel="alternate" hreflang="${language}" href="${requestURI?replace("\\?" + languageQueryString + "=" + "\\w{2}\\b", '', 'r')}"/>
			</#if>
		<#else>
			<link rel="alternate" hreflang="${language}" href="${requestURI}<#if requestURI?contains("?")>&<#else>?</#if>${languageQueryString}=${language}"/>
		</#if>
	</#list>
</#macro>

<#function getTextSource text arguments=[""]>
	<#assign message><@spring.messageArgs text arguments/></#assign>
	<#return message/>
</#function>