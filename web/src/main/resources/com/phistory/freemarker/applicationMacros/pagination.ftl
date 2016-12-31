<#import "pageLanguage.ftl" as language/>
<#import "genericFunctionalities.ftl" as generic/>

<#macro createCarsPagination chunkedModelsList=[]>
	var paginationOptions =
   	{
	    bootstrapMajorVersion : 3,
		currentPage: ${pagNumData},
		alignment: 'left',
	    totalPages: <#if (chunkedModelsList?size > 0)>${chunkedModelsList?size}<#else>1</#if>,
		useBootstrapTooltip: true,
		tooltipTitles:
		   	function(type,page,current)
	       	{
	       		switch (type) {
	            case "first":
	                return "${language.getTextSource('pagination.goToFirstPage')} ";
	            case "prev":
	                return "${language.getTextSource('pagination.goToPreviousPage')} ";
	            case "next":
	                return "${language.getTextSource('pagination.goToNextPage')} ";
	            case "last":
	                return "${language.getTextSource('pagination.goToLastPage')} ";
	            case "page":
	                return "${language.getTextSource('pagination.goToPage')} " + page;
	            }
	     	},        
	    onPageClicked:
	       	function(e,originalEvent,type,page)
	       	{      		
	       		if (!ajaxCallBeingProcessed)
	       		{	         			
	       			ajaxCallBeingProcessed = true;
	       			var paginationData = {
	       									${pagNum} : page,
	       									${carsPerPage} : ${carsPerPageData}
	       							  	 };
		   	    	$.ajax({
	    	       			type: 'GET',
	            			url: "/${carsURL}/${paginationURL}",
	           				data: paginationData, 
	           				beforeSend: function(xhr)
	           				{
                                <#if requestIsDesktop>
                                    <@generic.addLoadingSpinnerToComponentScript "main-car-list-div"/>
                                <#else>
                                    <@generic.addLoadingSpinnerToComponentScript "car-list-div"/>
                                </#if>
								addCRSFTokenToAjaxRequest(xhr);
                            }
	                	  })	                	 
	                	  .done(function (data)
						  {    	            	 
	    	        	    	writeCarPreviews(data.cars);
                                <#if requestIsDesktop>
	    	        	     	    $('#main-car-list-div').unblock();
                                <#else>
                                    $('#car-list-div').unblock();
                                </#if>
	    	        	     	ajaxCallBeingProcessed = false;
	    	        	 	    	
	    	        	     	if (data != null)
	    	        	     	{
	    	        	     		window.history.pushState(null,'',"${carsURL}?${pagNum}=" + data.pagNumData + "&${carsPerPage}=" + data.carsPerPageData<#if doNotTrack> + "&${doNotTrackParam}=true"</#if>);
								}
						  });  
				}                      
	    	}
	}        

	$('#pagination-ul').bootstrapPaginator(paginationOptions);
	$('#pagination-ul').addClass('cursor-pointer');
</#macro>

<#macro createContentSearchPaginationFunction>
		var paginationOptions =
    	{
	    	bootstrapMajorVersion : 3,
    	    currentPage: contentSearchDto.${pagNum},
    	    alignment: 'left',
        	totalPages: Math.ceil(contentSearchDto.searchTotalResults/contentSearchDto.${carsPerPage}),
    	    useBootstrapTooltip: true,
    	    tooltipTitles:
    	    	function(type,page,current)
	         	{
	         		switch (type) {
                    case "first":
                        return "${language.getTextSource('pagination.goToFirstPage')} ";
                    case "prev":
                        return "${language.getTextSource('pagination.goToPreviousPage')} ";
                    case "next":
                        return "${language.getTextSource('pagination.goToNextPage')} ";
                    case "last":
                        return "${language.getTextSource('pagination.goToLastPage')} ";
                    case "page":
                        return "${language.getTextSource('pagination.goToPage')} " + page;
                    }
	         	},        
        	onPageClicked:
	         	function(e,originalEvent,type,page)
	         	{      		
	         		if (!ajaxCallBeingProcessed)
	         		{	         			
	         			ajaxCallBeingProcessed = true;

	         			delete contentSearchDto.searchTotalResults; 
	         			contentSearchDto.${pagNum} = page;  
	         			 				   			
	         			$.ajax({
        	        			type:'GET',
	        					url: "/${modelsSearchURL}",
        						data: contentSearchDto,
                				beforeSend: function(xhr)
                				{
                                    <#if requestIsDesktop>
                                        <@generic.addLoadingSpinnerToComponentScript "main-car-list-div"/>
                                    <#else>
                                        <@generic.addLoadingSpinnerToComponentScript "car-list-div"/>
                                    </#if>
									addCRSFTokenToAjaxRequest(xhr);
  								}
	                	  	  })	                	 
	                	  	  .done(function (data)
						  	  {    	            	 
        	        	 	    	if (data != null)
      								{
      									document.children[0].innerHTML = data;
                                        <#if requestIsDesktop>
                                            $('#main-car-list-div').unblock();
                                        <#else>
                                            $('#car-list-div').unblock();
                                        </#if>
        	        	 	    		window.history.pushState(null,'',"${modelsSearchURL}?${pagNum}=" + page + "&${carsPerPage}=" + contentSearchDto.${carsPerPage} + "&${contentToSearch}=" + contentSearchDto.${contentToSearch}<#if doNotTrack> + "&${doNotTrackParam}=true"</#if>);
    									paginationOptions.currentPage = contentSearchDto.${pagNum};
        	        	 	    		$('#pagination-ul').bootstrapPaginator(paginationOptions);
    									$('#pagination-ul').addClass('cursor-pointer');
        	        	 	    		
        	        	 	    		ajaxCallBeingProcessed = false;
        	        	 	    	}
									setupContentSearchEventListeners();	
						  	  });  
					}                      
            	}
    	}        

    	$('#pagination-ul').bootstrapPaginator(paginationOptions);
    	$('#pagination-ul').addClass('cursor-pointer');
</#macro>