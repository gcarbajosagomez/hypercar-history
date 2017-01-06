<#--
 Created by Gonzalo Carbajosa on 6/01/17.
-->
<#compress>
    <#import "applicationMacros/genericFunctionalities.ftl" as generic/>
    <#import "applicationMacros/pageLanguage.ftl" as language/>

    <@generic.startPage language.getTextSource('meta.title.manufacturerHistory')
                        language.getTextSource('meta.keywords.manufacturerHistory')
                        language.getTextSource('meta.title.manufacturerHistory.metaDescription')/>

        <div id="main-container" class="container">
            <div class="panel panel-body col-lg-12">
                <#if (manufacturer.historyES?? && manufacturer.historyES?length > 0) && lang == languageSpanishCode>
                    ${generic.normalizeDatabaseString(manufacturer.historyES?j_string)}
				<#elseif (manufacturer.historyEN?? && manufacturer.historyEN?length > 0) && lang == languageEnglishCode>
                ${generic.normalizeDatabaseString(manufacturer.historyEN?j_string)}
                </#if>
            </div>
        </div>
    <@generic.endPage/>
</#compress>