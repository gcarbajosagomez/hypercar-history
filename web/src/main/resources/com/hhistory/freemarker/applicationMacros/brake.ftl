<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/carUtils.ftl" as carUtils/>

<#macro writeCarBrakeInfo brake>
    <#assign trainName = brake.train.getName()/>

    <ul class="list-group brake-set-list-group">
        <li class="list-group-item brake-set-list-group-item"><h3 class="brake-set-train-name">${language.getTextSource('brakeSet.${trainName}')}</h3>
            <dl class="dl-horizontal text-left">
                <dt>
                    ${language.getTextSource('brake.type')} :
                </dt>
                <dd>
                    <p class="text-muted">
                        ${language.getTextSource('brake.type.${brake.getType().getName()}')}
                    </p>
                </dd>
                <#if brake.getType() == "DISC">
                    <dt>
                        ${language.getTextSource('brake.disc.diameter')} :
                    </dt>
                    <dd>
                        <p class="text-muted">
                            ${carUtils.writeCarNumericData (brake.discDiameter!-1)}<#if brake.discDiameter??><em class="measure-unit-text"> ${language.getTextSource('MM')}</em></#if>
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
                            ${carUtils.writeCarNumericData (brake.caliperNumOfPistons!-1)}
                        </p>
                    </dd>
                </#if>
            </dl>
        </li>
    </ul>
</#macro>