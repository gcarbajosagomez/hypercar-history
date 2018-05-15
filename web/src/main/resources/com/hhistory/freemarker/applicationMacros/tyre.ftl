<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/carUtils.ftl" as carUtils/>

<#macro writeTyreInfo tyreSet>
    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="text-left car-details-panel-heading">${language.getTextSource('tyres')}</h3>
            </div>
            <div class="panel-body inner-car-details-panel-body">
                <dl class="dl-horizontal text-left">
                    <dt>
                        ${language.getTextSource('tyreSet.manufacturer')} :
                    </dt>
                    <dd>
                        <p class="text-muted">
                            <#if (tyreSet.manufacturer??)>
                                ${tyreSet.manufacturer.getName()}
                            <#else>
                                ${language.getTextSource('unknown')}
                            </#if>
                        </p>
                    </dd>
                    <dt>
                        ${language.getTextSource('tyreSet.model')} :
                    </dt>
                    <dd>
                        <p class="text-muted">
                            <#if (tyreSet.model?length > 0)>
                                ${tyreSet.model}
                            <#else>
                                ${language.getTextSource('unknown')}
                            </#if>
                        </p>
                    </dd>
                    <dt>
                        ${language.getTextSource('tyreSet.type')} :
                    </dt>
                    <dd>
                        <p class="text-muted">
                            <#if (tyreSet.manufacturer??)>
                                ${language.getTextSource('tyreSet.type.${tyreSet.type.getName()}')}
                            <#else>
                                ${language.getTextSource('unknown')}
                            </#if>
                        </p>
                    </dd>
                    <dt>
                        ${language.getTextSource('tyreSet.frontTyre')} :
                    </dt>
                    <dd>
                        <p class="text-muted">
                            <#assign frontTyre = tyreSet.frontTyre/>
                            <#if tyreHasValue(frontTyre)>
                                ${carUtils.writeCarNumericData (frontTyre.width!-1)} / ${carUtils.writeCarNumericData (frontTyre.profile!-1)} / <#if frontTyre.rimDiameter??><em class="measure-unit-text">R</em></#if>${carUtils.writeCarNumericData (frontTyre.rimDiameter!-1)}
                            <#else>
                                ${language.getTextSource('unknown')}
                            </#if>
                        </p>
                    </dd>
                    <dt>
                        ${language.getTextSource('tyreSet.rearTyre')} :
                    </dt>
                    <dd>
                        <p class="text-muted">
                            <#assign rearTyre = tyreSet.rearTyre/>
                            <#if tyreHasValue(rearTyre)>${carUtils.writeCarNumericData (rearTyre.width!-1)} / ${carUtils.writeCarNumericData (rearTyre.profile!-1)} / <#if frontTyre.rimDiameter??><em class="measure-unit-text">R</em></#if>${carUtils.writeCarNumericData (rearTyre.rimDiameter!-1)}
                            <#else>
                                ${language.getTextSource('unknown')}
                            </#if>
                        </p>
                    </dd>
                </dl>
            </div>
        </div>
    </div>
</#macro>

<#function tyreHasValue tyre>
    <#if tyre.width?? || tyre.profile?? || tyre.rimDiameter??>
        <#return true>
    </#if>
    <#return false>
</#function>