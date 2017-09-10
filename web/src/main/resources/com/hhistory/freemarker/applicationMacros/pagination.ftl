<#import "pageLanguage.ftl" as language/>
<#import "genericFunctionalities.ftl" as generic/>
<#import "uriUtils.ftl" as uriUtils/>

<#macro addCarsPagination chunkedModelsList=[]>
	<@createPaginationCallFuncion "/${carsURL}/${paginationURL}" false/>
	<@addPaginatorFunction chunkedModelsList?size/>
</#macro>

<#macro addCMSCarsPagination chunkedModelsList=[]>
	<@addCMSPaginatorFunction chunkedModelsList?size/>
</#macro>

<#macro addCMSPaginatorFunction totalPages=1>
	var paginationOptions = {
		bootstrapMajorVersion : 3,
		currentPage: ${pagNumData},
		alignment: 'left',
		totalPages: ${totalPages},
		useBootstrapTooltip: true,
		<@addPaginatorTooltipTitlesProperty/>
		onPageClicked:
			function(e,originalEvent,type,page) {
				var paginationDto = {
					${pagNum}      : page,
					${carsPerPage} : ${carsPerPageData}
				};
				var manufacturerValue = $('#manufacturer-selector')[0].value;
				if (manufacturerValue) {
					paginationDto['manufacturer'] = manufacturerValue;
				}
				requestPagination(paginationDto);
			}
	}

	$('#pagination-ul').bootstrapPaginator(paginationOptions);
	$('#pagination-ul').addClass('cursor-pointer');
</#macro>

<#macro addPaginatorFunction totalPages=1>
	var paginationOptions = {
		bootstrapMajorVersion : 3,
		currentPage: ${pagNumData},
		alignment: 'left',
		totalPages: ${totalPages},
		useBootstrapTooltip: true,
		<@addPaginatorTooltipTitlesProperty/>
		onPageClicked:
			function(e,originalEvent,type,page) {
				var paginationDto = {
					${pagNum}      : page,
					${carsPerPage} : ${carsPerPageData}
				};
				requestPagination(paginationDto);
			}
	}

	$('#pagination-ul').bootstrapPaginator(paginationOptions);
	$('#pagination-ul').addClass('cursor-pointer');
</#macro>

<#macro addPaginatorTooltipTitlesProperty>
	tooltipTitles:
		function(type,page,current) {
			switch (type) {
				case "first":
					return "${language.getTextSource('pagination.goToFirstPage')}";
				case "prev":
					return "${language.getTextSource('pagination.goToPreviousPage')}";
				case "next":
					return "${language.getTextSource('pagination.goToNextPage')}";
				case "last":
					return "${language.getTextSource('pagination.goToLastPage')}";
				case "page":
					return "${language.getTextSource('pagination.goToPage')}" + page;
		}
	},
</#macro>

<#macro createPaginatorFunction>
	function createPaginator(currentPage, totalPages) {
		var paginationOptions = {
			bootstrapMajorVersion : 3,
			currentPage: currentPage,
			alignment: 'left',
			totalPages: totalPages > 0 ? totalPages : 1,
			useBootstrapTooltip: true,
			<@addPaginatorTooltipTitlesProperty/>
			onPageClicked:
				function(e,originalEvent,type,page) {
					var paginationDto = {
						${pagNum}      : page,
						${carsPerPage} : ${carsPerPageData}
					};
					requestPagination(paginationDto);
				}
		}

		$('#pagination-ul').bootstrapPaginator(paginationOptions);
		$('#pagination-ul').addClass('cursor-pointer');
	}
</#macro>

<#macro createPaginationCallFuncion url isCms=false>
	function requestPagination(paginationDto) {
		if (!ajaxCallBeingProcessed) {
			ajaxCallBeingProcessed = true;
			$.ajax({
				type: 'GET',
				url: '${url}',
				data: paginationDto,
				beforeSend: function(xhr){
					<@generic.addLoadingSpinnerToComponentScript "main-container"/>

					addCRSFTokenToAjaxRequest(xhr);
				}
			})
			.done(function (paginationDTO) {
				writeCarPreviews(paginationDTO.items);
				<#if isCms>
					writeModelListGroup(paginationDTO);
                	setUpManufacturerSelectorValueChangedListener();

					var totalPages = Math.ceil(paginationDTO.models.length / ${carsPerPageData});
                	createPaginator(paginationDTO.pagNum, totalPages);
				</#if>

				$('#main-container').unblock();

				ajaxCallBeingProcessed = false;

				if (paginationDTO) {
					var pageTitle = paginationDTO.pageTitle;
					if (pageTitle) {
						document.title = pageTitle;
					}
					window.history.pushState(null,'',"${carsURL}?${pagNum}=" + paginationDTO.pagNum + "&${carsPerPage}=" + paginationDTO.itemsPerPage<#if doNotTrack> + "&${doNotTrackParam}=true"</#if>);
				}
			});
		}
	}
</#macro>

<#macro createContentSearchPaginationFunction>
		var paginationOptions = {
	    	bootstrapMajorVersion : 3,
    	    currentPage: contentSearchDto.${pagNum},
    	    alignment: 'left',
        	totalPages: Math.ceil(contentSearchDto.searchTotalResults/contentSearchDto.${carsPerPage}),
    	    useBootstrapTooltip: true,
			<@addPaginatorTooltipTitlesProperty/>
        	onPageClicked:
	         	function(e,originalEvent,type,page) {
	         		if (!ajaxCallBeingProcessed) {
	         			ajaxCallBeingProcessed = true;

	         			delete contentSearchDto.searchTotalResults; 
	         			contentSearchDto.${pagNum} = page;  
	         			 				   			
	         			$.ajax({
        	        			type:'GET',
	        					url: "${uriUtils.buildDomainURI("/${searchURL}")}",
        						data: contentSearchDto,
                				beforeSend: function(xhr) {
                                    <#if requestIsDesktop>
                                        <@generic.addLoadingSpinnerToComponentScript "main-car-list-div"/>
                                    <#else>
                                        <@generic.addLoadingSpinnerToComponentScript "car-list-div"/>
                                    </#if>
									addCRSFTokenToAjaxRequest(xhr);
  								}
	                	  	  })	                	 
	                	  	  .done(function (data) {
        	        	 	    	if (data) {
      									document.children[0].innerHTML = data;
                                        <#if requestIsDesktop>
                                            $('#main-car-list-div').unblock();
                                        <#else>
                                            $('#car-list-div').unblock();
                                        </#if>
        	        	 	    		window.history.pushState(null,'',"${searchURL}?${pagNum}=" + page + "&${carsPerPage}=" + contentSearchDto.${carsPerPage} + "&${contentToSearch}=" + contentSearchDto.${contentToSearch}<#if doNotTrack> + "&${doNotTrackParam}=true"</#if>);
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

<#function getCarsPerPageURI carsPerPageNumber>
	<#assign uri>
		<#if requestURI?matches(".*([&?]${pagNum}=).*")>
			<#if (requestContainsManufacturerData?? && requestContainsManufacturerData)>/${manufacturerShortName}</#if>${requestURI?replace("${pagNum}=[0-9]*&${carsPerPage}=[0-9]*", "${pagNum}=1&${carsPerPage}=${carsPerPageNumber}", "r")}
		<#else>
			<#if requestURI?contains("?")>
				<#if (requestContainsManufacturerData?? && requestContainsManufacturerData)>/${manufacturerShortName}</#if>${requestURI}&${pagNum}=1&${carsPerPage}=${carsPerPageNumber}
			<#else>
				<#if (requestContainsManufacturerData?? && requestContainsManufacturerData)>/${manufacturerShortName}</#if>${requestURI}?${pagNum}=1&${carsPerPage}=${carsPerPageNumber}
			</#if>
		</#if>
	</#assign>

	<#return uri>
</#function>