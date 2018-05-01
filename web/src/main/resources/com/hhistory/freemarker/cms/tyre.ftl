<#import "/spring.ftl" as spring/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>

<#macro writeTyreEditFields tyre objectBindingPath tyreTrain>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="text-left">${language.getTextSource('brakeSet.${tyreTrain.getName()}')}</h4>
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