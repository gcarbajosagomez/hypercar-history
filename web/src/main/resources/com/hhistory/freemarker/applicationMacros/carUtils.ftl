<#import "pageLanguage.ftl" as language/>
<#import "picture.ftl" as picture/>
<#import "pagination.ftl" as pagination/>
<#import "genericFunctionalities.ftl" as generic/>
<#import "uriUtils.ftl" as uriUtils/>
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

<#function writeCarStringData data>
    <#if (data?length > 0)>
        <#assign stringData>${data}</#assign>
    <#else>
        <#assign stringData>${language.getTextSource('unknown')}</#assign>
    </#if>
    <#return stringData/>
</#function>

<#macro addSetUnitsOfMeasureFunctionScript>
    <script type="application/javascript">
        function setUnitsOfMeasure(unitsOfMeasure, mainForm) {
            if ($.cookie('${unitsOfMeasureCookieName}') !== unitsOfMeasure && !ajaxCallBeingProcessed) {
                $.cookie('${unitsOfMeasureCookieName}', unitsOfMeasure, {path : '/', expires : 14});
                ajaxCallBeingProcessed = true;

                $.ajax({
                            type:'GET',
                            url: mainForm.action,
                            dataType: 'html',
                            cache: false,
                            beforeSend: function() {
                                if(unitsOfMeasure === '${unitsOfMeasureMetric}') {
                                    $('#metric-units-loading-gif').removeClass('sr-only');
                                }
                                else if(unitsOfMeasure === '${unitsOfMeasureImperial}') {
                                    $('#imperial-units-loading-gif').removeClass('sr-only');
                                }

                                <@generic.addLoadingSpinnerToComponentScript "main-car-details-div"/>
                            }
                        })
                        .done(function(data) {
                            document.body.innerHTML = data;
                            ajaxCallBeingProcessed = false;
                            <@picture.addPicturesGalleryFunctionScript "images-gallery" "carousel-inner"/>
                            $('#main-car-details-div').unblock();
                        });
            }
        }
    </script>
</#macro>

<#function getCarProductionLifeTime car>
    <#assign productionStartYear>${car.productionStartDate.time?string("yyyy")}</#assign>
    <#assign productionEndYear><#if car.productionEndDate??>${car.productionEndDate.time?string("yyyy")}</#if></#assign>
    <#assign productionLifeTime = ""/>

    <#if productionEndYear != "">
        <#if productionStartYear != productionEndYear>
            <#assign productionLifeTime>${productionStartYear} - ${productionEndYear}</#assign>
        <#else>
            <#assign productionLifeTime>${productionStartYear}</#assign>
        </#if>
    <#else>
        <#assign productionLifeTime>
        ${productionStartYear} - ${language.getTextSource('car.productionEndDate.inProduction')}
        </#assign>
    </#if>
    <#return productionLifeTime/>
</#function>

<#macro writePaginationMarkup>
    <div id="car-pagination-main-div" class="col-lg-12">
        <div class="col-lg-7 col-md-8 col-sm-10 col-xs-12 center-block well well-sm pagination-well">
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
                        <li role="presentation"><a role="menuitem" href='${pagination.getCarsPerPageURI(6)}'>6</a></li>
                        <li role="presentation"><a role="menuitem" href='${pagination.getCarsPerPageURI(12)}'>12</a></li>
                        <li role="presentation"><a role="menuitem" href='${pagination.getCarsPerPageURI(18)}'>18</a></li>
                        <li role="presentation"><a role="menuitem" href='${pagination.getCarsPerPageURI(24)}'>24</a></li>
                        <hr>
                        <li role="presentation"><a role="menuitem" href='<#if requestIsCMS>${pagination.getCarsPerPageURI(cmsModels?size)}<#else>${pagination.getCarsPerPageURI(visibleModels?size)}</#if>'>${language.getTextSource('pagination.allCars')}</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</#macro>