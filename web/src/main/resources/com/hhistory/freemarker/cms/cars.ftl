<#import "/spring.ftl" as spring/>
<#import "../applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/pagination.ftl" as pagination/>
<#import "../applicationMacros/carUtils.ftl" as carUtils/>
<#import "../applicationMacros/uriUtils.ftl" as uriUtils/>

<#assign paginationFirstResult><#if paginationFirstResult??>${paginationFirstResult + 1}<#else>0</#if></#assign>
<#assign paginationLastResult><#if paginationLastResult??>${paginationLastResult}<#else>0</#if></#assign>
<@generic.startPage language.getTextSource('${manufacturer.getName()}.meta.title.allModels', [allModels?size, paginationFirstResult, paginationLastResult])/>

<div id="main-container" class="container panel panel-default cars-main-container">
	<div class="row">
		<div class="col-lg-2">
			<div id="cms-models-list-group" class="list-group">
                <select id="manufacturer-selector" class="form-control manufacturer-selector">
                    <option value="" selected>${language.getTextSource('pagination.allManufacturers')}</option>
					<#list manufacturers as manufacturer>
						<option value="${manufacturer.getShortName()}">${manufacturer.getName()?cap_first}</option>
					</#list>
                </select>
				<#list cmsModels as car>
    				<a class="list-group-item" href='${uriUtils.buildDomainURI("/${cmsContext}${carsURL}/${car.id}/${editURL}")}'>
    					<h5 class="text-center list-group-element">${car.manufacturer.name} ${car.model}</h5>
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
				<@carUtils.writePaginationMarkup/>
			</#if>
		</div>		
	</div>
</div>
<#assign chunkedModelsList = allModels?chunk(carsPerPageData)>
<@generic.endPage chunkedModelsList/>

<script type='application/javascript'>

	<#assign url><#if requestContainsManufacturerData?? && requestContainsManufacturerData>/${manufacturerShortName}</#if>/${cmsContext}${carsURL}/${paginationURL}</#assign>
	<@pagination.createPaginatorFunction/>
	<@pagination.createPaginationCallFuncion url true/>

	$(document).ready(function() {
		<@pagination.addCMSCarsPagination chunkedModelsList/>
		setUpManufacturerSelectorValueChangedListener(1);
	});
    
    function writeCarListRow(cars, zIndex) {
    	var carRowString = "<div id='car-list-row' class='row'>";
    	carRowString = carRowString.concat("<ul class='grid preview'>");

    	for (var i=0; i< cars.length; i++) {
    		carRowString = carRowString.concat("<div class='col-lg-6 col-md-6 col-sm-12 preview-outer' id='" + cars[i].manufacturer.name + "-" + cars[i].model + "-div'>");
    		carRowString = carRowString.concat(	  "<div class='thumbnail preview-div'>");
    		carRowString = carRowString.concat(	  	 "<li style='z-index:" + (zIndex - i) + "'>");
    		carRowString = carRowString.concat(	  	 	"<figure>");
		   	carRowString = carRowString.concat(				"<div class='caption vertically-aligned-div vertically-aligned-preview-div'>");
            carRowString = carRowString.concat(					"<img class='img-thumbnail preview-img' src='/${picturesURL}/${loadCarPreviewAction}?${id}=" + cars[i].id + "' alt='" + cars[i].manufacturer.name + " " + cars[i].model + "'>");
            carRowString = carRowString.concat(				"</div>");
            carRowString = carRowString.concat(				"<figcaption>");
			carRowString = carRowString.concat(					"<a href='<#if requestContainsManufacturerData?? && requestContainsManufacturerData>/${manufacturerShortName}</#if>/${cmsContext}${carsURL}/" + cars[i].id + "/${editURL}' style='padding-bottom: 0px; padding-top: 0px;'>");
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

    function buildManufacturerSelectorListElement() {
        var selector = $('<select>', {
            'id': "manufacturer-selector",
			'class': "form-control manufacturer-selector"
		});

        selector.append($('<option>', {
            value: "",
            text: "${language.getTextSource('pagination.allManufacturers')}"
        }));

		<#list manufacturers as manufacturer>
            selector.append($('<option>', {
                value: "${manufacturer.getShortName()}",
                text: "${manufacturer.getName()?cap_first}"
            }));
		</#list>

        return selector;
	}

    function buildModelListElement(model) {
		var modelListElement = $('<a>', {
            'class': "list-group-item",
            'href': "${uriUtils.buildDomainURI("/${cmsContext}${carsURL}/\" + model.id + \"/${editURL}")}"
        });
		modelListElement.append('<h5 class="text-center list-group-element">' + model.model + '</h5>');
		return modelListElement;
	}

    function setUpManufacturerSelectorValueChangedListener() {
        $("#manufacturer-selector").change(function () {
            var paginationDto = {
				${pagNum}      : 1,
				${carsPerPage} : ${carsPerPageData}
			};

            var manufacturerValue = $('#manufacturer-selector')[0].value;
            if (manufacturerValue) {
                paginationDto['manufacturer'] = manufacturerValue;
            }
            requestPagination(paginationDto);
        });
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
                        <img class="img-thumbnail preview-img" src='<@spring.url "/${picturesURL}/${loadCarPreviewAction}?${id}=${car.id}"/>' alt="${car.manufacturer.name} ${car.model}">
                    </div>
                    <figcaption>
                        <a href='${uriUtils.buildDomainURI("/${cmsContext}${carsURL}/${car.id}/${editURL}")}' style="padding-bottom: 0px; padding-top: 0px;">
                            <h3 class="text-center model-name">${car.model}</h3>
                        </a>
                    </figcaption>
                </figure>
            </li>
        </div>
    </div>
</#macro>  