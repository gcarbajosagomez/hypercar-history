<#import "pageLanguage.ftl" as language/>
<#import "picture.ftl" as picture/>
<#import "genericFunctionalities.ftl" as generic/>
<#import "/spring.ftl" as spring/>

<#function writeCarNumericData data>
    <#if data != -1>
        <#assign numericData>${data?string["0.#"]}</#assign>
    <#else>
        <#assign numericData>${language.getTextSource('unknown')}</#assign>
    </#if>
    <#return numericData/>
</#function>

<#function writeNonDecimalCarNumericData data>
    <#if data != -1>
        <#assign numericData>${data?string["0"]}</#assign>
    <#else>
        <#assign numericData>${language.getTextSource('unknown')}</#assign>
    </#if>
    <#return numericData/>
</#function>

<#macro writeCarBrakeInfo brake>
    <#assign trainName = brake.train.getName()/>

    <ul class="list-group brake-set-list-group">
        <li class="list-group-item brake-set-list-group-item"><h3 class="brake-set-train-name">${language.getTextSource('brakeSet.${trainName}')}</h3>
            <dl class="dl-horizontal text-left">
                <dt>
                    ${language.getTextSource('brake.disc.diameter')} :
                </dt>
                <dd>
                    <p class="text-muted">
                        ${writeCarNumericData (brake.discDiameter?default(-1))}<#if brake.discDiameter??><em class="measure-unit-text">${language.getTextSource('MM')}</em></#if>
                    </p>
                </dd>
                <dt>
                    ${language.getTextSource('brake.disc.material')} :
                </dt>
                <dd>
                    <#if brake.discMaterial??>
                        <p class="text-muted">
                            ${language.getTextSource('brake.disc.material.${brake.discMaterial}')}
                        </p>
                    </#if>
                </dd>
                <dt>
                    ${language.getTextSource('brake.caliper.numOfPistons')} :
                </dt>
                <dd>
                    <p class="text-muted">
                        ${writeCarNumericData (brake.caliperNumOfPistons?default(-1))}
                    </p>
                </dd>
            </dl>
        </li>
    </ul>
</#macro>

<#macro addSetUnitsOfMeasureFunctionScript>
    <script type="application/javascript">
        function setUnitsOfMeasure(unitsOfMeasure, mainForm)
        {
            if ($.cookie('${unitsOfMeasureCookieName}') !== unitsOfMeasure && !ajaxCallBeingProcessed)
            {
                $.cookie('${unitsOfMeasureCookieName}', unitsOfMeasure, {path : '/', expires : 14});
                ajaxCallBeingProcessed = true;

                $.ajax({
                            type:'GET',
                            url: mainForm.action,
                            dataType: 'html',
                            cache: false,
                            beforeSend: function()
                            {
                                if(unitsOfMeasure == '${unitsOfMeasureMetric}')
                                {
                                    $('#metric-units-loading-gif').removeClass('sr-only');
                                }
                                else if(unitsOfMeasure == '${unitsOfMeasureImperial}')
                                {
                                    $('#imperial-units-loading-gif').removeClass('sr-only');
                                }

                                <@generic.addLoadingSpinnerToComponentScript "main-car-details-div"/>
                            }
                        })
                        .done(function(data)
                        {
                            document.body.innerHTML = data;
                            ajaxCallBeingProcessed = false;
                            <@picture.addPicturesGalleryFunctionScript "images-gallery" "carousel-inner"/>
                            $('#main-car-details-div').unblock();
                        });
            }
        }
    </script>
</#macro>

<#macro printCarPreview car car_index row_index>
    <#assign modelName>${car.model}</#assign>
    <div id="${car.manufacturer.name}-${modelName}-div" class="col-lg-6 col-md-6 col-sm-12 preview-outer<#if !requestIsDesktop> center-block</#if>">
        <#assign zIndex = (car_index + 1) * (row_index + 1)>
        <#--the Z-index of the elements on top must be higher than those below, threrfore the figure must be inverted -->
        <#assign zIndex = zIndex + (cars?size - ((car_index + 1) * (row_index + 1)) - zIndex)>
        <#if requestIsDesktop && car_index == 0>
            <#assign zIndex = (zIndex) - (1 * row_index)>
        </#if>
        <div class="thumbnail preview-div">
            <li style="z-index: <#if zIndex??>${zIndex}<#else>1</#if>">
                <figure>
                    <div class="caption vertically-aligned-div vertically-aligned-preview-div">
                        <a href='<@spring.url "/${carsURL}/${car.getNormalizedModelName()}"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'>
                            <img class="img-thumbnail preview-img"
                                 src='<@spring.url "/${picturesURL}/${loadCarPreviewAction}?${id}=${car.id}"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'
                                 alt="${car.manufacturer.name} ${modelName} preview"
                                 title="${car.manufacturer.name} ${modelName}">
                        </a>
                    </div>
                    <figcaption>
                        <a href='<@spring.url "/${carsURL}/${car.getNormalizedModelName()}"/><#if doNotTrack>?${doNotTrackParam}=true</#if>'>
                            <h3 class="text-<#if requestIsDesktop>center<#else>left center-block</#if>
                                                <#if (requestIsDesktop && modelName?length > 33) || (!requestIsDesktop && modelName?length > 21)> double-line-car-model-name</#if>">${modelName}</h3>
                        </a>
                    </figcaption>
                </figure>
            </li>
        </div>
    </div>
</#macro>

<#macro writePaginationMarkup>
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
</#macro>