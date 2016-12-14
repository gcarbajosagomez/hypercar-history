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

<#macro addCarStructuredMetadata>
    <script type="application/ld+json">
        {
            "@context":"http://schema.org/",
            "@type":"BreadcrumbList",
            "itemListElement": [{
                    "@type":"ListItem",
                    "position":1,
                    "item": {
                        "@id":"${siteURL}/",
                        "name":"Home"
                    }
                },
                {
                    "@type":"ListItem",
                    "position":2,
                    "item": {
                        "@id":"${siteURL}/${carsURL}/",
                        "name":"${language.getTextSource('cars.all', [models?size])}"
                    }
                },
                {
                    "@type":"ListItem",
                    "position":3,
                    "item": {
                        "@id":"${siteURL}${requestURI}/",
                        "name":"${language.getTextSource('pagani')} ${car.model}"
                    }
                }]
        }
    </script>

    <script type="application/ld+json">
        {
            "@context":"http://schema.org/",
            "@type":"Car",
            "url":"${siteURL}${requestURI}/",
            "manufacturer":"${language.getTextSource('pagani')}",
            "name":"${car.model}",
            "model":"${car.model}",
            "category":"${language.getTextSource('car.bodyShape.${car.bodyShape}')?lower_case}",
            "bodyType":"${language.getTextSource('car.bodyShape.${car.bodyShape}')?lower_case}",
            "description":"${language.getTextSource('pagani')} ${car.model}",
            "image":"${siteURL}/${picturesURL}/${loadCarPreviewAction}?${id}=${car.id}",
            "numberOfForwardGears":"${writeCarNumericData (car.transmission.numOfGears?default(-1))}",
            "driveWheelConfiguration":"${language.getTextSource('car.driveWheelType.${car.driveWheelType}')}",
            "accelerationTime":"${carUtils.writeCarNumericData (car.acceleration?default(-1))}<#if car.acceleration??>${language.getTextSource('S')}</#if>",
            "speed":"${carUtils.writeCarNumericData (car.topSpeed?default(-1))}<#if car.topSpeed??>${language.getTextSource('Km/h')}</#if>",
            "productionDate":"${car.productionStartDate.time?string("yyyy")}",
            "weight":"${carUtils.writeCarNumericData (car.weight?default(-1))}<#if car.weight??>${language.getTextSource('Kg')}</#if>",
            "vehicleEngine": {
                "@type": "EngineSpecification",
                "name":"${car.engine.size}${language.getTextSource('CM3')} ${language.getTextSource('engine.type.${car.engine.type}')} ${car.engine.cylinderDisposition}${car.engine.numberOfCylinders}",
                "description":"${car.engine.size}${language.getTextSource('CM3')} ${language.getTextSource('engine.type.${car.engine.type}')} ${car.engine.cylinderDisposition}${car.engine.numberOfCylinders}"
            }
        }
    </script>
</#macro>

<#function normalizeCarDescriptionString string>
    <#assign normalizedString>${string?replace("\\r\\n", "<br/>")}</#assign>
    <#assign normalizedString>${normalizedString?replace("\\", "")}</#assign>
    <#assign normalizedString>${normalizedString?replace("(.)(.{0,})", "<d class=\"big-text\">$1</d><d>$2</d>", "r")}</#assign>
    <#return normalizedString/>
</#function>