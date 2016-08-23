<#import "pageLanguage.ftl" as language/>
<#import "picture.ftl" as picture/>
<#import "genericFunctionalities.ftl" as generic/>

<#function writeCarNumericData data>
    <#if data != -1>
        <#assign numericData>${data?string["0.#"]}</#assign>
    <#else>
        <#assign numericData>${language.getTextSource('undefined')}</#assign>
    </#if>
    <#return numericData/>
</#function>

<#function writeNonDecimalCarNumericData data>
    <#if data != -1>
        <#assign numericData>${data?string["0"]}</#assign>
    <#else>
        <#assign numericData>${language.getTextSource('undefined')}</#assign>
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
        <div class="panel-body">
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