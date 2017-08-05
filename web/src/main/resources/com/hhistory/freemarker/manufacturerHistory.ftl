<#--
 Created by Gonzalo Carbajosa on 6/01/17.
-->
<#compress>
    <#import "applicationMacros/genericFunctionalities.ftl" as generic/>
    <#import "applicationMacros/pageLanguage.ftl" as language/>
    <#import "applicationMacros/picture.ftl" as picture/>

    <@generic.startPage language.getTextSource('meta.title.manufacturerHistory')
                        language.getTextSource('meta.keywords.manufacturerHistory')
                        language.getTextSource('meta.title.manufacturerHistory.metaDescription')/>

        <div id="main-container" class="container panel panel-default main-container main-panel">
            <div class="main-row-container row">
                <div class="col-lg-12 manufacturer-history-div">
                    <#if manufacturerEntity??>
                        <#assign historyES = manufacturerEntity.historyES/>
                        <#assign historyEN = manufacturerEntity.historyEN/>

                        <#if (historyES?? && historyES?length > 0) && lang == languageSpanishCode>
                            ${generic.normalizeDatabaseString(historyES?j_string)}
                        <#elseif (historyEN?? && historyEN?length > 0) && lang == languageEnglishCode>
                            ${generic.normalizeDatabaseString(historyEN?j_string)}
                        </#if>
                    </#if>
                </div>
            </div>
        </div>
    <@picture.addPicturesGallery "images-gallery" "picture-gallery"/>
    <@generic.endPage/>
</#compress>