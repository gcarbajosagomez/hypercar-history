<#import "/spring.ftl" as spring/>
<#import "applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "applicationMacros/pageLanguage.ftl" as language/>
<#import "applicationMacros/pagination.ftl" as pagination/>
<#import "applicationMacros/metaData.ftl" as metaData/>
<#import "applicationMacros/carUtils.ftl" as carUtils/>

<#assign metaKeywords = language.getTextSource('meta.keywords.cars', [models?size])/>
<@generic.startPage language.getTextSource('meta.title.allModels', [models?size])
                    metaKeywords
                    language.getTextSource('meta.title.allModels.metaDescription', [models?size, numberOfPictures, numberOfVideos])/>

<div id="main-container" class="container panel panel-default main-container main-panel">
	<div class="main-car-list-container row">
		<div class="col-lg-2">
			<div class="list-group">
				<#list models as car>
    				<a class="list-group-item" href='<@spring.url "${carsURL}/${car.getNormalizedModelName()}"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'>
    					<h5 class="text-center list-group-element">${car.model?upper_case}</h5>
    				</a>
  				</#list> 
				<#if models??>
					<a class="list-group-item" <#if requestIsCars>href='<@spring.url "${carsURL}?${pagNum}=1&${carsPerPage}=${models?size}"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'</#if>>
    					<h5 class="text-center<#if requestIsCars> list-group-element</#if>">${models?size} ${language.getTextSource('models')?upper_case}</h5>
    				</a>
    			</#if>
			</div>
		</div>
		
		<#if requestIsDesktop><div id="main-car-list-div" class="col-lg-10 thumbnail"></#if>
			<#if (carsPerPageData >= 1)>
				<div id="car-list-div" class="col-lg-<#if requestIsDesktop>12<#else>10</#if>">
					<ul class="grid preview">
                        <#list cars?chunk(2) as row>
							<div class="row car-list-row">
								<#list row as car>
                                    <@carUtils.printCarPreview car car_index row_index/>
								</#list>
							</div>
						</#list>
					</ul>
				</div>
                <div id="car-pagination-main-div" class="col-lg-12">
                    <div class="col-lg-7 col-md-8 col-sm-10 col-xs-12 center-block well well-sm">
                        <div id="pagination-row-div" class="row">
                            <div class="text-left col-lg-8 col-md-8 <#if requestIsDesktop>col-sm-8 col-xs-8<#else>col-sm-12 col-xs-12</#if>">
                                <ul id="pagination-ul"></ul>
                            </div>
                            <div class="text-right col-lg-4 col-md-4 <#if requestIsDesktop>col-sm-4 col-xs-4<#else>col-sm-12 col-xs-12</#if>">
                                <button id="cars-per-page-dropdown" class="btn btn-default dropdown-toggle <#if requestIsDesktop>pull-right<#else>pull-left</#if>" data-toggle="dropdown">
                                    ${language.getTextSource('pagination.carsPerPage')}
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-<#if requestIsDesktop>right<#else>left</#if>" role="menu" aria-labelledby="cars-per-page-dropdown">
                                    <li role="presentation"><a role="menuitem" href='<@spring.url "${carsURL}?${pagNum}=1&${carsPerPage}=5"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'>5</a></li>
                                    <li role="presentation"><a role="menuitem" href='<@spring.url "${carsURL}?${pagNum}=1&${carsPerPage}=10"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'>10</a></li>
                                    <li role="presentation"><a role="menuitem" href='<@spring.url "${carsURL}?${pagNum}=1&${carsPerPage}=15"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'>15</a></li>
                                    <li role="presentation"><a role="menuitem" href='<@spring.url "${carsURL}?${pagNum}=1&${carsPerPage}=20"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'>20</a></li>
                                    <li role="presentation" class="divider"></li>
                                    <li role="presentation"><a role="menuitem" href='<@spring.url "${carsURL}?${pagNum}=1&${carsPerPage}=${models?size}"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'>${language.getTextSource('pagination.allCars')}</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
			</#if>
        <#if requestIsDesktop></div></#if>
	</div>
</div>
<#assign chunkedModelsList = models?chunk(carsPerPageData)>
<@generic.endPage chunkedModelsList/>
<@metaData.addCarListStructuredMetadata metaKeywords/>

<script type='application/javascript'>
	
	<#if cars?? && (models?size > carsPerPageData)>
		$(document).ready(function()
		{
            <#if requestIsCars && (chunkedModelsList?size > 0)>
                <@pagination.createCarsPagination chunkedModelsList/>
            <#elseif requestIsModelsSearch>
                var contentSearchDto = {
                                        ${pagNum} 			: 1,
                                        ${carsPerPage} 	: <#if carsPerPageData??>${carsPerPageData}<#else>8</#if>,
                                        ${contentToSearch} : $("#content-search-input")[0].value,
                                        searchTotalResults : $("#search-total-results")[0].value
                };
                <@pagination.createContentSearchPaginationFunction/>
            </#if>
		    });
	</#if>

	function writeCarListRow(cars, zIndex)
    {
        var carRowString = "<div class='row car-list-row'>";
        carRowString = carRowString.concat("<ul class='grid preview'>");

        for (var i = 0; i< cars.length; i++)
        {
            var carModel = cars[i].model;
            carRowString = carRowString.concat("<div id='" + cars[i].manufacturer.name + "-" + cars[i].model + "-div' class='col-lg-6 col-md-6 col-sm-12 preview-outer<#if !requestIsDesktop> center-block</#if>'>");
            carRowString = carRowString.concat(	  "<div class='thumbnail preview-div'>");
            carRowString = carRowString.concat(	  	 "<li style='z-index:" + (zIndex - i) + "'>");
            carRowString = carRowString.concat(	  	 	"<figure>");
            carRowString = carRowString.concat(				"<div class='caption vertically-aligned-div vertically-aligned-preview-div'>");
            carRowString = carRowString.concat(				    "<a href='/${carsURL}/" + cars[i].normalizedModelName + "'>");
            carRowString = carRowString.concat(					    "<img class='img-thumbnail preview-img' src='${picturesURL}/${loadCarPreviewAction}?${id}=" + cars[i].id + "' alt='" + cars[i].manufacturer.name + " " + cars[i].model + "'>");
            carRowString = carRowString.concat(				    "</a>");
            carRowString = carRowString.concat(				"</div>");
            carRowString = carRowString.concat(				"<figcaption>");
            carRowString = carRowString.concat(					"<a href='/${carsURL}/" + cars[i].normalizedModelName + "'>");

            if (carModel.length < 33)
            {
                carRowString = carRowString.concat(						"<h3 class='text-center'>" + carModel + "</h3>");
            }
            else
            {
                carRowString = carRowString.concat(						"<h3 class='text-center double-line-car-model-name'>" + carModel + "</h3>");
            }

            carRowString = carRowString.concat(					"</a>");
            carRowString = carRowString.concat(				"</figcaption>");
            carRowString = carRowString.concat(	  		"</figure>");
            carRowString = carRowString.concat(	  	 "</li>");
            carRowString = carRowString.concat(	  "</div>");
            carRowString = carRowString.concat("</div>");
        }

        carRowString = carRowString.concat("</ul>");
        carRowString = carRowString.concat("</div>");

        return carRowString;
    }
</script>