<#macro addHandleContentSearch>
	<script type='text/javascript'>
		function handleContentSearch(contentToSearch, mainForm)
		{
			var contentSearchDto = {		
									 ${pagNum} : 1,
			         				 ${carsPerPage} : <#if carsPerPageData??>${carsPerPageData}<#else>8</#if>,
									 ${contentToSearch} : contentToSearch
			         			   }; 
			         			   
			$.ajax({            
		        	type:'GET',
			        url: "/${modelsSearchURL}",
		    	    data: contentSearchDto,
			        beforeSend: function(xhr)
		    	    {
		    	    	$('#main-container').block({ 
							css: {         										
		        					border:         '0px solid', 
		        					backgroundColor:'rgba(94, 92, 92, 0)'
		    				},
		                	message: '<div class="row"><h1 class="col-lg-6 col-md-6 col-sm-12 col-xs-12" style="color: #fff">${getTextSource('loading')}</h1><i id="pagination-loading-gif" class="col-lg-4 col-md-4 col-sm-12 col-xs-12 fa fa-circle-o-notch fa-4x fa-spin blue"></i></div>' 
		            	});
		
						addCrsfTokenToAjaxRequest(xhr);
		    	    }
			 })
			 .done(function(data)
			 {      	    	
		       	if (data != null)
		      	{       		
					document.children[0].innerHTML = data;
					contentSearchDto.searchTotalResults = $("#search-total-results")[0].value;			
		
					<#--Pagination is only created if needed -->
		            if (contentSearchDto.searchTotalResults > 0)
		            {
		              	<@createContentSearchPaginationFunction/>
		            }
		            else
		            {
		            	<#--No content found -->
						$("#car-pagination-main-div").remove();
						$("#car-list-div").remove();
		
												  var noContentFoundElements = "<div class='col-xs-12 well well-lg'>";
						noContentFoundElements = noContentFoundElements.concat(		"<h3>${getTextSource('contentSearch.noContentFound')}</h3>");
						noContentFoundElements = noContentFoundElements.concat("</div>");
						
						$("#main-car-list-div").append(noContentFoundElements);				
		            }                      
		
					window.history.pushState(null,'',"/${modelsSearchURL}?${pagNum}=1&${carsPerPage}=" + contentSearchDto.carsPerPage + "&${contentToSearch}=" + contentSearchDto.contentToSearch);			
					setupContentSearchEventListeners();	
				}
				
				ajaxCallBeingProcessed = false;        	
			});   
		}
	</script>
</#macro>