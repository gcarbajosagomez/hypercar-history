<#macro addSetPageLanguage>
	<script type='text/javascript'>
		function setPageLanguage(locale, mainForm)
		{  
		   if ($.cookie('${languageCookieName}') != locale && !ajaxCallBeingProcessed) 
		   { 
		   		ajaxCallBeingProcessed = true;
		   		
				if (mainForm.action.search("&lang=") != -1)
				{
					<#--The lang param must be removed from the URL before sending it -->
					mainForm.action = mainForm.action.replace(/[&?]lang=e[ns]/,'');
				}
		   		
		   		$.ajax({            
		        	type:'GET',
			        url: mainForm.action,
		    	    contentType :'application/json; charset=UTF-8',
		        	data: { lang: locale },
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
		
						$('#main-wrap-div').block({ 
							css: {         										
		        					border:         '0px solid', 
		        					backgroundColor:'rgba(94, 92, 92, 0)'
		    				},
		                	message: '<div class="row"><h1 class="col-lg-6 col-md-6 col-sm-12 col-xs-12" style="color: #fff">${getTextSource('loading')}</h1><i id="pagination-loading-gif" class="col-lg-4 col-md-4 col-sm-12 col-xs-12 fa fa-circle-o-notch fa-4x fa-spin blue"></i></div>' 
		            	});
		
						xhr = addCrsfTokenToAjaxRequest(xhr);
		   			}
			   })
			   .done(function(data)
			   {           
			        document.children[0].innerHTML = data;			        
					<#if (requestURI?contains(carsURL) && !requestURI?contains(cmsContext)) || requestURI?contains(modelsSearchURL)>      
			            <#--Pagination is only created if the language change is called from the cars page and if needed -->
			            if ($('#car-list-div').length > 0)
			            {
			              	<#if requestURI?contains(carsURL)>              		
			              		<@createCarsPagination chunkedModelsList![]/>
			              	<#elseif requestURI?contains(modelsSearchURL)>
								var contentSearchDto = {		
										 				 ${pagNum} 			: 1,
				         				 				 ${carsPerPage} 	: <#if carsPerPageData??>${carsPerPageData}<#else>8</#if>,
										 				 ${contentToSearch} : $("#content-search-input")[0].value,
										 				 searchTotalResults : $("#search-total-results")[0].value	
				         			   				   };				         		 	   				   
								<@createContentSearchPaginationFunction/>
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