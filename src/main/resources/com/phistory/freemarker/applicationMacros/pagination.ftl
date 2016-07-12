<#macro createCarsPagination chunkedModelsList>
	var options =
   	{
	    bootstrapMajorVersion : 3,
		currentPage: ${pagNumData},
		alignment: 'left',
	    totalPages: <#if chunkedModelsList??>${chunkedModelsList?size}<#else>1</#if>,
		useBootstrapTooltip: true,
		tooltipTitles:
		   	function(type,page,current)
	       	{
	       		switch (type) {
	            case "first":
	                return "${getTextSource('pagination.goToFirstPage')} ";
	            case "prev":
	                return "${getTextSource('pagination.goToPreviousPage')} ";
	            case "next":
	                return "${getTextSource('pagination.goToNextPage')} ";
	            case "last":
	                return "${getTextSource('pagination.goToLastPage')} ";
	            case "page":
	                return "${getTextSource('pagination.goToPage')} " + page;
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
	            			url: "${carsURL}/${paginationURL}",
	           				data: paginationData, 
	           				beforeSend: function(xhr)
	           				{
								$('#main-car-list-div').block({ 
									css: {         										
	    									border:         '0px solid', 
	    									backgroundColor:'rgba(94, 92, 92, 0)'
									},
	           						message: '<div class="row"><h1 class="col-lg-6 col-md-6 col-sm-12 col-xs-12" style="color: #fff">${getTextSource('loading')}</h1><i id="pagination-loading-gif" class="col-lg-4 col-md-4 col-sm-12 col-xs-12 fa fa-circle-o-notch fa-4x fa-spin blue"></i></div>' 
	        					});
	        					
									addCrsfTokenToAjaxRequest(xhr);
								}
	                	  })	                	 
	                	  .done(function (data)
						  {    	            	 
	    	        	    	writeCarPreviews(data.cars);
	    	        	     	$('#main-car-list-div').unblock(); 
	    	        	     	ajaxCallBeingProcessed = false;
	    	        	 	    	
	    	        	     	if (data != null)
	    	        	     	{
	    	        	     		window.history.pushState(null,'',"${carsURL}?${pagNum}=" + data.pagNumData + "&${carsPerPage}=" + data.carsPerPageData); 
								}
						  });  
				}                      
	    	}
	}        

	$('#pagination-ul').bootstrapPaginator(options);
	$('#pagination-ul').addClass('cursor-pointer');
</#macro>

<#macro createContentSearchPaginationFunction>
		var options =
    	{
	    	bootstrapMajorVersion : 3,
    	    currentPage: contentSearchDto.pagNum,
    	    alignment: 'left',
        	totalPages: Math.ceil(contentSearchDto.searchTotalResults/contentSearchDto.${carsPerPage}),
    	    useBootstrapTooltip: true,
    	    tooltipTitles:
    	    	function(type,page,current)
	         	{
	         		switch (type) {
                    case "first":
                        return "${getTextSource('pagination.goToFirstPage')} ";
                    case "prev":
                        return "${getTextSource('pagination.goToPreviousPage')} ";
                    case "next":
                        return "${getTextSource('pagination.goToNextPage')} ";
                    case "last":
                        return "${getTextSource('pagination.goToLastPage')} ";
                    case "page":
                        return "${getTextSource('pagination.goToPage')} " + page;
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
									$('#main-car-list-div').block({ 
										css: {         										
        										border:         '0px solid', 
        										backgroundColor:'rgba(94, 92, 92, 0)'
    									},
                						message: '<div class="row"><h1 class="col-lg-6 col-md-6 col-sm-12 col-xs-12" style="color: #fff">${getTextSource('loading')}</h1><i id="pagination-loading-gif" class="col-lg-4 col-md-4 col-sm-12 col-xs-12 fa fa-circle-o-notch fa-4x fa-spin blue"></i></div>' 
            						});      						

									addCrsfTokenToAjaxRequest(xhr);
  								}
	                	  	  })	                	 
	                	  	  .done(function (data)
						  	  {    	            	 
        	        	 	    	if (data != null)
      								{
      									document.children[0].innerHTML = data;      									
        	        	 	    		$('#main-car-list-div').unblock();       	        	 	    		
        	        	 	    		window.history.pushState(null,'',"${modelsSearchURL}?${pagNum}=" + page + "&${carsPerPage}=" + contentSearchDto.carsPerPage + "&${contentToSearch}=" + contentSearchDto.contentToSearch);	
    									options.currentPage = contentSearchDto.${pagNum};
        	        	 	    		$('#pagination-ul').bootstrapPaginator(options);
    									$('#pagination-ul').addClass('cursor-pointer');
        	        	 	    		
        	        	 	    		ajaxCallBeingProcessed = false;
        	        	 	    	}
									setupContentSearchEventListeners();	
						  	  });  
					}                      
            	}
    	}        

    	$('#pagination-ul').bootstrapPaginator(options);
    	$('#pagination-ul').addClass('cursor-pointer');
</#macro>