<#compress>
    <#import "/spring.ftl" as spring/>
    <#import "applicationMacros/genericFunctionalities.ftl" as generic/>
    <#import "applicationMacros/pageLanguage.ftl" as language/>
    <#import "applicationMacros/pagination.ftl" as pagination/>
    <#import "applicationMacros/metaData.ftl" as metaData/>
    <#import "applicationMacros/carUtils.ftl" as carUtils/>
    <#import "applicationMacros/uriUtils.ftl" as uriUtils/>

    <#assign manufacturerName = manufacturer.getName()/>
    <#assign metaKeywords = language.getTextSource('${manufacturerName}.meta.keywords.cars', [models?size])/>
    <#assign paginationFirstResult><#if paginationFirstResult??>${paginationFirstResult + 1}<#else>0</#if></#assign>
    <#assign paginationLastResult><#if paginationLastResult??>${paginationLastResult}<#else>0</#if></#assign>
    <@generic.startPage language.getTextSource('${manufacturerName}.meta.title.allModels', [models?size, paginationFirstResult, paginationLastResult])
                        metaKeywords
                        language.getTextSource('${manufacturerName}.meta.title.allModels.metaDescription', [models?size, paginationFirstResult, paginationLastResult])/>

    <div id="car-list-main-container" class="container panel panel-default">
        <div class="main-row-container row">
            <div class="col-lg-2">
                <div class="list-group">
                    <#list models as car>
                        <a class="list-group-item" href='${uriUtils.buildDomainURI("/${carsURL}/${car.getNormalizedModelName()}")}'/>
                            <h5 class="text-center list-group-element">${car.model?upper_case}</h5>
                        </a>
                    </#list>
                    <#if models??>
                        <a class="list-group-item" <#if requestIsCars>href='${uriUtils.buildDomainURI("/${carsURL}?${pagNum}=1&${carsPerPage}=${models?size}")}'</#if>>
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
                    <@carUtils.writePaginationMarkup/>
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
                    <@pagination.addCarsPagination chunkedModelsList/>
                <#elseif requestIsModelsSearch>
                    var contentSearchDto = {
                                            ${pagNum} 			: <#if pagNumData??>${pagNumData}<#else>1</#if>,
                                            ${carsPerPage} 	    : <#if carsPerPageData??>${carsPerPageData}<#else>${defaultCarsPerPageData}</#if>,
                                            ${contentToSearch}  : $("#content-search-input")[0].value,
                                            searchTotalResults  : $("#search-total-results")[0].value
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
                carRowString = carRowString.concat(				    "<a href='${carsURL}/" + cars[i].normalizedModelName + "<#if doNotTrack>?${doNotTrackParam}=true</#if>'>");
                carRowString = carRowString.concat(					    "<img class='img-thumbnail preview-img' src='/${picturesURL}/${loadCarPreviewAction}?${id}=" + cars[i].id + "' alt='" + cars[i].manufacturer.name + " " + carModel + " preview' title='" + cars[i].manufacturer.name + " " + carModel + "'>");
                carRowString = carRowString.concat(				    "</a>");
                carRowString = carRowString.concat(				"</div>");
                carRowString = carRowString.concat(				"<figcaption>");
                carRowString = carRowString.concat(					"<a href='${carsURL}/" + cars[i].normalizedModelName + "<#if doNotTrack>?${doNotTrackParam}=true</#if>'>");

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
</#compress>