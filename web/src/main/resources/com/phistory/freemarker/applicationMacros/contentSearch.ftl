<#import "pagination.ftl" as pagination/>
<#import "pageLanguage.ftl" as language/>
<#import "genericFunctionalities.ftl" as generic/>
<#import "advertising.ftl" as advertising/>
<#import "uriUtils.ftl" as uriUtils/>

<#macro addHandleContentSearchFunctionScript>
	<script type='application/javascript'>
		function handleContentSearch(contentToSearch)
		{
			var contentSearchDto = {		
									 ${pagNum} 			: <#if pagNumData??>${pagNumData}<#else>1</#if>,
			         				 ${carsPerPage} 	: <#if carsPerPageData??>${carsPerPageData}<#else>${defaultCarsPerPageData}</#if>,
									 ${contentToSearch} : contentToSearch
			         			   }; 
			         			   
			$.ajax({            
		        	type:'GET',
			        url: "${uriUtils.buildDomainURI("/${modelsSearchURL}")}",
		    	    data: contentSearchDto,
			        beforeSend: function(xhr)
		    	    {
						<@generic.addLoadingSpinnerToComponentScript "main-container"/>
                        addCRSFTokenToAjaxRequest(xhr);
		    	    }
			 })
			 .done(function(data)
			 {      	    	
		       	if (data != null)
		      	{       		
					document.children[0].innerHTML = data;
					contentSearchDto.searchTotalResults = $("#search-total-results")[0].value;			
		
					<#--pagination is only created if needed -->
		            if (contentSearchDto.searchTotalResults > 0)
		            {
		              	<@pagination.createContentSearchPaginationFunction/>
		            }
		            else
		            {
		            	<#--No content found -->
						$("#car-pagination-main-div").remove();
						$("#car-list-div").remove();
		
												  var noContentFoundElements = "<div class='col-xs-12 well well-lg'>";
						noContentFoundElements = noContentFoundElements.concat(		"<h3>${language.getTextSource('contentSearch.noContentFound')}</h3>");
						noContentFoundElements = noContentFoundElements.concat("</div>");
						
						$("#main-car-list-div").append(noContentFoundElements);				
		            }                      
		
					window.history.pushState(null,'',<#if (requestContainsManufacturerData?? && requestContainsManufacturerData)>"/${manufacturerShortName}</#if>/${modelsSearchURL}?${pagNum}=1&${carsPerPage}=" + contentSearchDto.cpp + "&${contentToSearch}=" + contentSearchDto.cts<#if doNotTrack> + "&${doNotTrackParam}=true"</#if>);
					setupContentSearchEventListeners();	
				}
				
				ajaxCallBeingProcessed = false;
				<#if !requestIsCMS && !doNotTrack>
                    performGoogleAnalyticsRequest();

					<#if !requestIsDesktop>
						<@advertising.performSmaatoJSAdRequests/>
					</#if>
				</#if>
			});   
		}
	</script>
</#macro>