<#--
 Created by Gonzalo Carbajosa on 25/06/17.
-->
<#import "/spring.ftl" as spring/>

<#macro identifyRequestURL>
    <#global requestIsManufacturerHistory = false/>
    <#global requestIsCMS = false/>
    <#global requestIsCarEdit = false/>
    <#global requestIsCars = false/>
    <#global requestIsCarDetails = false/>
    <#global requestIsModelsSearch = false/>
    <#global requestContainsManufacturer = false/>

    <#if requestURI?contains(cmsContext)>
        <#global requestIsCMS = true/>
        <#if requestURI?matches("/" + cmsContext + carsURL + "/([0-9]{1,})/" + editURL + ".{0,}")>
            <#global requestIsCarEdit = true/>
        </#if>
    <#elseif requestURI?contains(manufacturerHistoryURL)>
        <#global requestIsManufacturerHistory = true/>
    <#elseif requestURI?contains(carsURL)>
        <#global requestIsCars = true/>
    <#elseif requestURI?contains(carURL)>
        <#global requestIsCarDetails = true/>
    <#elseif requestURI?contains(searchURL)>
        <#global requestIsModelsSearch = true/>
    </#if>
</#macro>

<#--
    Build a URI within the domain based on the supplied uri
-->
<#function buildDomainURI uri>
    <#assign domainURI>
        <#if requestContainsManufacturerData?? && requestContainsManufacturerData>/${manufacturerShortName}</#if><@spring.url uri/><#if doNotTrack><#if uri?contains("?")>&<#else>?</#if>${doNotTrackParam}=true</#if></#assign>

    <#return domainURI>
</#function>

<#function sanitizeCarName carName>
    <#assign sanitizedName>${carName?replace('\'','%27')?replace("/", "%252F")}</#assign>

    <#return sanitizedName>
</#function>