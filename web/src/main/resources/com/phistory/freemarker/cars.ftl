<#import "/spring.ftl" as spring/>
<#import "applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "applicationMacros/pageLanguage.ftl" as language/>
<#import "applicationMacros/pagination.ftl" as pagination/>

<@generic.startPage language.getTextSource('title.allModels')/>

<div id="main-container" class="container panel panel-default main-container main-panel">
	<div class="main-car-list-container row">
		<div class="col-lg-2">
			<div class="list-group">
				<#list models as car>
    				<a class="list-group-item" href='<@spring.url "${carsURL}/${car.id}"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'>
    					<h5 class="text-center list-group-element">${car.model}</h5>
    				</a>
  				</#list> 
				<#if models??>
					<a class="list-group-item" <#if requestIsCars>href='<@spring.url "${carsURL}?${pagNum}=1&${carsPerPage}=${models?size}"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'</#if>>
    					<h5 class="text-center<#if requestIsCars> list-group-element</#if>">${models?size} ${language.getTextSource('models')}</h5>
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
                                    <@printCarPreview car car_index row_index/>
								</#list>
							</div>
						</#list>
					</ul>
				</div>
                <div id="car-pagination-main-div" class="col-lg-12">
                    <div class="col-lg-7 col-md-8 col-sm-10 <#if requestIsDesktop>col-xs-11<#else>col-xs-12</#if> center-block well well-sm">
                        <div id="pagination-row-div" class="row">
                            <div class="text-left col-lg-8 col-md-8 <#if requestIsDesktop>col-sm-8 col-xs-12<#else>col-sm-12 col-xs-12</#if>">
                                <ul id="pagination-ul"></ul>
                            </div>
                            <div class="text-right col-lg-4 col-md-4 <#if requestIsDesktop>col-sm-4 col-xs-12<#else>col-sm-12 col-xs-12</#if>">
                                <button id="cars-per-page-dropdown" class="btn btn-default dropdown-toggle pull-left" style="padding: 10px; margin-top: 15px" data-toggle="dropdown">
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

<script type='application/javascript'>
	
	<#if cars?? && (models?size > carsPerPageData)>
		$( document ).ready(function()
		{
  			<@pagination.createCarsPagination chunkedModelsList/>
		});
	</#if>

	function writeCarListRow(cars, zIndex)
    {
        var carRowString = "<div class='row car-list-row'>";
        carRowString = carRowString.concat("<ul class='grid preview'>");

        for (var i=0 ; i< cars.length; i++)
        {
            var carModel = cars[i].model;
            carRowString = carRowString.concat("<div id='" + cars[i].manufacturer.name + "-" + cars[i].model + "-div' class='col-lg-6 col-md-6 col-sm-12 preview-outer<#if !requestIsDesktop> center-block</#if>'>");
            carRowString = carRowString.concat(	  "<div class='thumbnail preview-div'>");
            carRowString = carRowString.concat(	  	 "<li style='z-index:" + (zIndex - i) + "'>");
            carRowString = carRowString.concat(	  	 	"<figure>");
            carRowString = carRowString.concat(				"<div class='caption vertically-aligned-div vertically-aligned-preview-div'>");
            carRowString = carRowString.concat(				    "<a href='/${carsURL}/" + cars[i].id + "'>");
            carRowString = carRowString.concat(					    "<img class='img-thumbnail preview-img' src='${picturesURL}/${loadCarPreviewAction}?${carId}=" + cars[i].id + "' alt='" + cars[i].manufacturer.name + " " + cars[i].model + "'>");
            carRowString = carRowString.concat(				    "</a>");
            carRowString = carRowString.concat(				"</div>");
            carRowString = carRowString.concat(				"<figcaption>");
            carRowString = carRowString.concat(					"<a href='/${carsURL}/" + cars[i].id + "'>");

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

<#macro printCarPreview car car_index row_index>
    <#assign modelName>${car.model}</#assign>
    <div id="${car.manufacturer.name}-${modelName}-div" class="col-lg-6 col-md-6 col-sm-12 preview-outer<#if !requestIsDesktop> center-block</#if>">
        <#assign zIndex = (car_index + 1) * (row_index + 1)>
        <#--the Z-index of the elements on top must be higher than those below, threrfore the figure must be inverted -->
        <#assign zIndex = zIndex + (cars?size - ((car_index + 1) * (row_index + 1)) - zIndex)>
        <#if requestIsDesktop && car_index == 0>
            <#assign zIndex = (zIndex) - (1 * row_index)>
        <#else>
        </#if>
        <div class="thumbnail preview-div">
            <li style="z-index: <#if zIndex??>${zIndex}<#else>1</#if>">
                <figure>
                    <div class="caption vertically-aligned-div vertically-aligned-preview-div">
                        <a href='<@spring.url "/${carsURL}/${car.id}"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'>
                        	<img class="img-thumbnail preview-img" src='<@spring.url "/${picturesURL}/${loadCarPreviewAction}?${carId}=${car.id}"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'
                                 alt="${car.manufacturer.name} ${modelName}">
                    	</a>
					</div>
                    <figcaption>
                        <a href='<@spring.url "/${carsURL}/${car.id}"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'>
                            <h3 class="text-<#if requestIsDesktop>center<#else>left center-block</#if>
                                            <#if (requestIsDesktop && modelName?length > 33) || (!requestIsDesktop && modelName?length > 21)> double-line-car-model-name</#if>">${modelName}</h3>
                        </a>
                    </figcaption>
                </figure>
            </li>
        </div>
    </div>
</#macro>