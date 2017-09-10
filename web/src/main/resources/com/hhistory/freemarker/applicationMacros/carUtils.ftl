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
                        ${writeCarNumericData (brake.discDiameter?default(-1))}<#if brake.discDiameter??><em class="measure-unit-text"> ${language.getTextSource('MM')}</em></#if>
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

<#macro writeBrakeEditFields brake objectBindingPath brakeTrain>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="text-left">${language.getTextSource('brakeSet.${brakeTrain?lower_case}')}</h4>
    </div>
    <div class="panel-body">
        <dl class="dl-horizontal dl-horizontal-edit text-left">
            <#if brake.id??>
                <dt>
                    ${language.getTextSource('id')}
                </dt>
                <dd>
                    <h5 class="entity-id text-muted">${brake.id}</h5>
                    <@spring.formHiddenInput "${objectBindingPath?string}.id", ""/>
                </dd>
            </#if>
            <@spring.bind "${objectBindingPath?string}.train"/>
            <input type="hidden" id="${objectBindingPath?string?replace("CEFC.", "")}.train" name="${objectBindingPath?string?replace("CEFC.", "")}.train" class="form-control" value="${brakeTrain}">
            <dt>
                ${language.getTextSource('brake.disc.diameter')}
            </dt>
            <dd>
                <@spring.formInput "${objectBindingPath?string}.discDiameter", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
            </dd>
            <dt>
                ${language.getTextSource('brake.disc.material')}
            </dt>
            <dd>
                <@spring.bind "${objectBindingPath}.discMaterial"/>

                <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                    <#list brakeDiscMaterials as brakeDiscMaterial>
                        <option value="${brakeDiscMaterial}" <#if spring.status.value?? && brakeDiscMaterial == spring.status.value?default("")> selected</#if>>${language.getTextSource('brake.disc.material.${brakeDiscMaterial}')}</option>
                    </#list>
                </select>
            </dd>
            <dt>
                ${language.getTextSource('brake.caliper.numOfPistons')}
            </dt>
            <dd>
                <@spring.formInput "${objectBindingPath?string}.caliperNumOfPistons", "class=form-control", "text"/>
            </dd>
        </dl>
    </div>
</div>
</#macro>

<#macro writeTyreEditFields tyre objectBindingPath tyreTrain>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="text-left">${language.getTextSource('brakeSet.${tyreTrain?lower_case}')}</h4>
    </div>
    <div class="panel-body">
        <dl class="dl-horizontal dl-horizontal-edit text-left">
            <#if tyre.id??>
                <dt>
                    ${language.getTextSource('id')}
                </dt>
                <dd>
                    <h5 class="entity-id text-muted">${tyre.id}</h5>
                    <@spring.formHiddenInput "${objectBindingPath}.id", ""/>
                </dd>
            </#if>
            <@spring.bind "${objectBindingPath?string}.train"/>
            <input type="hidden" id="${objectBindingPath?string?replace("CEFC.", "")}.train" name="${objectBindingPath?string?replace("CEFC.", "")}.train" class="form-control" value="${tyreTrain}">
            <dt>
                ${language.getTextSource('tyre.width')}
            </dt>
            <dd>
                <@spring.formInput "${objectBindingPath?string}.width", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
            </dd>
            <dt>
                ${language.getTextSource('tyre.profile')}
            </dt>
            <dd>
                <@spring.formInput "${objectBindingPath?string}.profile", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
            </dd>
            <dt>
                ${language.getTextSource('tyre.rimDiameter')}
            </dt>
            <dd>
                <@spring.formInput "${objectBindingPath}.rimDiameter", "class=form-control placeholder=${language.getTextSource('inch')}", "text"/>
            </dd>
        </dl>
    </div>
</div>
</#macro>

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
                                if(unitsOfMeasure == '${unitsOfMeasureMetric}') {
                                    $('#metric-units-loading-gif').removeClass('sr-only');
                                }
                                else if(unitsOfMeasure == '${unitsOfMeasureImperial}') {
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
                        <a href='${uriUtils.buildDomainURI("/${carsURL}/${car.getNormalizedModelName()}")}'>
                            <img class="img-thumbnail preview-img"
                                 src='<@spring.url "/${picturesURL}/${loadCarPreviewAction}?${id}=${car.id}"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'
                                 alt="${car.manufacturer.name} ${modelName} preview"
                                 title="${car.manufacturer.name} ${modelName}">
                        </a>
                    </div>
                    <figcaption>
                        <a href='${uriUtils.buildDomainURI("/${carsURL}/${car.getNormalizedModelName()}")}'>
                            <h3 class="text-<#if requestIsDesktop>center<#else>left center-block</#if>
                                            <#if (requestIsDesktop && modelName?length > 33) || (!requestIsDesktop && modelName?length > 21)> double-line-car-model-name</#if>">${modelName}</h3>
                        </a>
                    </figcaption>
                </figure>
            </li>
        </div>
    </div>
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
                        <li role="presentation"><a role="menuitem" href='${pagination.getCarsPerPageURI(6)}'>6</a></li>
                        <li role="presentation"><a role="menuitem" href='${pagination.getCarsPerPageURI(12)}'>12</a></li>
                        <li role="presentation"><a role="menuitem" href='${pagination.getCarsPerPageURI(18)}'>18</a></li>
                        <li role="presentation"><a role="menuitem" href='${pagination.getCarsPerPageURI(24)}'>24</a></li>
                        <hr>
                        <li role="presentation"><a role="menuitem" href='${pagination.getCarsPerPageURI(allModels?size)}'>${language.getTextSource('pagination.allCars')}</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</#macro>