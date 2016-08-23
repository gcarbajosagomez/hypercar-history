<#import "/spring.ftl" as spring/>
<#import "../applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/pagination.ftl" as pagination/>

<@generic.startPage language.getTextSource('title.allModels')/>

<div id="main-container" class="container panel panel-default cars-main-container main-panel">
	<div class="row">
		<div class="col-lg-2">
			<div class="list-group">
				<#list models as car>
    				<a class="list-group-item" href='<@spring.url "/${cmsContext}${carsURL}/${car.id}/${editURL}"/>'>
    					<h5 class="text-center list-group-element">${car.model}</h5>
    				</a>
  				</#list> 
			</div>
		</div>
		
		<div id="main-car-list-div" class="col-lg-10 thumbnail">
			<#if (carsPerPageData >= 1)>
				<div id="car-list-div" class="col-lg-12">	
					<ul class="grid preview">		
						<#list cars?chunk(2) as row>	
							<div id="car-list-row" class="row">
								<#list row as car>
									<@printDesktopCarPreview car car_index row_index/>
								</#list>
							</div>
						</#list>
					</ul>
				</div>
				
				<#assign chunkedModelsList = models?chunk(carsPerPageData)>
				
				<div class="col-lg-12" style="margin-top: 55px;">
					<div class="col-lg-6 col-md-6 col-sm-7 col-xs-8 center-block well well-sm">
						<div id="pagination-row-div" class="row">
							<#if (chunkedModelsList?size > 1)>					
								<div class="text-left col-lg-8 col-md-7 col-sm-7 col-xs-8">
									<ul id="pagination-ul"></ul>
								</div>
							</#if>
							<div class="text-right col-lg-4 col-md-5 col-sm-5 col-xs-4" style="height:56px; margin-bottom: 20px;">
								<button id="cars-per-page-dropdown" class="btn btn-default dropdown-toggle" style="padding: 10px; margin-top: 15px" type="button" data-toggle="dropdown">
    								${language.getTextSource('pagination.carsPerPage')}
    								<span class="caret"></span>
  								</button>
  								<ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="cars-per-page-dropdown">
									<li role="presentation"><a role="menuitem" href="${carsURL}?${pagNum}=1&${carsPerPage}=5">5</a></li>
    								<li role="presentation"><a role="menuitem" href="${carsURL}?${pagNum}=1&${carsPerPage}=10">10</a></li>
    								<li role="presentation"><a role="menuitem" href="${carsURL}?${pagNum}=1&${carsPerPage}=15">15</a></li>
    								<li role="presentation"><a role="menuitem" href="${carsURL}?${pagNum}=1&${carsPerPage}=20">20</a></li>
   									<li role="presentation" class="divider"></li>
	    							<li role="presentation"><a role="menuitem" href="${carsURL}?${pagNum}=1&${carsPerPage}=${models?size}">${language.getTextSource('pagination.allCars')}</a></li>
  								</ul>
  							</div>
  						</div>
  					</div>	
				</div>			
			</#if>
		</div>		
	</div>
</div>
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
    	carRowString = "<div id='car-list-row' class='row'>";
    	carRowString = carRowString.concat("<ul class='grid preview'>");

    	for (var i=0 ; i< cars.length; i++)
    	{
    		carRowString = carRowString.concat("<div class='col-lg-6 col-md-6 col-sm-12 preview-outer' id='" + cars[i].manufacturer.name + "-" + cars[i].model + "-div'>");
    		carRowString = carRowString.concat(	  "<div class='thumbnail preview-div'>");
    		carRowString = carRowString.concat(	  	 "<li style='z-index:" + (zIndex - i) + "'>");
    		carRowString = carRowString.concat(	  	 	"<figure>");
		   	carRowString = carRowString.concat(				"<div class='caption vertically-aligned-div vertically-aligned-preview-div'>");
            carRowString = carRowString.concat(					"<img class='img-thumbnail preview-img' src='/${picturesURL}/${loadCarPreviewAction}?${carId}=" + cars[i].id + "' alt='" + cars[i].manufacturer.name + " " + cars[i].model + "'>");
            carRowString = carRowString.concat(				"</div>");
            carRowString = carRowString.concat(				"<figcaption>");
			carRowString = carRowString.concat(					"<a href='/${carsURL}/" + cars[i].id + "/${editURL}' style='padding-bottom: 0px; padding-top: 0px;'>");
			carRowString = carRowString.concat(						"<h3 class='text-center'>" + cars[i].model + "</h3>");
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

<#macro printDesktopCarPreview car car_index row_index>
    <div id="${car.manufacturer.name}-${car.model}-div" class="col-lg-6 col-md-6 col-sm-12 preview-outer">
        <#assign zIndex = (car_index + 1) * (row_index + 1)>
		<#--the Z-index of the elements on top must be higher than those below, threrfore the figure must be inverted -->
		<#assign zIndex = zIndex + (cars?size - ((car_index + 1) * (row_index + 1)) - zIndex)>
		<#if car_index == 0>
            <#assign zIndex = (zIndex) - (1 * row_index)>
        </#if>
        <div class="thumbnail preview-div">
            <li style="z-index: <#if zIndex??>${zIndex}<#else>1</#if>">
                <figure>
                    <div class="caption vertically-aligned-div vertically-aligned-preview-div">
                        <img class="img-thumbnail preview-img" src='<@spring.url "/${picturesURL}/${loadCarPreviewAction}?${carId}=${car.id}"/>' alt="${car.manufacturer.name} ${car.model}">
                    </div>
                    <figcaption>
                        <a href='<@spring.url "/${cmsContext}${carsURL}/${car.id}/${editURL}"/>' style="padding-bottom: 0px; padding-top: 0px;">
                            <h3 class="text-center model-name">${car.model}</h3>
                        </a>
                    </figcaption>
                </figure>
            </li>
        </div>
    </div>
</#macro>  