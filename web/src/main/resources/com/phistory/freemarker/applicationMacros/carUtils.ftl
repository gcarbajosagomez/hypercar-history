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
    <div class="panel panel-default">
        <div class="panel-heading">
            <#if brake.train = "FRONT">
                <h3 class="text-left car-details-panel-heading">${language.getTextSource('brakeSet.front')}</h3>
            <#else>
                <h3 class="text-left car-details-panel-heading">${language.getTextSource('brakeSet.back')}</h3>
            </#if>
        </div>
        <div class="panel-body inner-car-details-panel-body">
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
        </div>
    </div>
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
                            <img class="img-thumbnail preview-img" src='<@spring.url "/${picturesURL}/${loadCarPreviewAction}?${id}=${car.id}"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'
                                 alt="${car.manufacturer.name} ${modelName}">
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

<#function normalizeCarDescriptionString string>
    <#assign normalizedString>${string?replace("\\r\\n", "<br/>")}</#assign>
    <#assign normalizedString>${normalizedString?replace("\\", "")}</#assign>
    <#assign normalizedString>${normalizedString?replace("(.)(.{0,})", "<d class=\"big-text\">$1</d><d>$2</d>", "r")}</#assign>
    <#return normalizedString/>
</#function>