<#--
 Created by Gonzalo Carbajosa on 26/12/16.
-->
<#import "pageLanguage.ftl" as language/>
<#import "carUtils.ftl" as carUtils/>

<#macro addIndexStructuredMetadata>
    <script type="application/ld+json">
        {
            "@context":"http://schema.org/",
            "@type":"BreadcrumbList",
            "itemListElement": [
                <@addHomeBreadCrumbItem/>
            ]
        }
    </script>
    <@addWebSiteMetaDataElement/>
</#macro>

<#macro addCarListStructuredMetadata metaKeywords>
    <script type="application/ld+json">
        {
            "@context":"http://schema.org/",
            "@type":"BreadcrumbList",
            "itemListElement": [
                <@addHomeBreadCrumbItem/>,
                <@addCarsBreadCrumbItem/>
            ]
        }
    </script>
    <@addWebSiteMetaDataElement/>
</#macro>

<#macro addCarStructuredMetadata metaKeywords>
    <script type="application/ld+json">
        {
            "@context":"http://schema.org/",
            "@type":"BreadcrumbList",
            "itemListElement": [
                <@addHomeBreadCrumbItem/>,
                <@addCarsBreadCrumbItem/>,
                <@addCarDetailsBreadCrumbItem/>
            ]
        }
    </script>

    <script type="application/ld+json">
        {
            "@context":"http://schema.org/",
            "@type":"Car",
            "url":"${siteURL}${requestURI}/",
            "manufacturer":"${language.getTextSource('${manufacturerName}')}",
            "name":"${car.model}",
            "model":"${car.model}",
            "category":"${language.getTextSource('car.bodyShape.${car.bodyShape.getName()}')?lower_case}",
            "bodyType":"${language.getTextSource('car.bodyShape.${car.bodyShape.getName()}')?lower_case}",
            "description":"${language.getTextSource('${manufacturerName}')} ${car.model}",
            "image":"${siteURL}/${picturesURL}/${loadCarPreviewAction}?${id}=${car.id}",
            "numberOfForwardGears":"${carUtils.writeCarNumericData (car.transmission.numOfGears?default(-1))}",
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
    <@addWebSiteMetaDataElement metaKeywords/>
</#macro>

<#macro addHomeBreadCrumbItem>
    {
        "@type":"ListItem",
        "position":1,
        "item": {
            "@id":"${siteURL}/",
            "name":"Home"
        }
    }
</#macro>

<#macro addCarsBreadCrumbItem>
    {
        "@type":"ListItem",
        "position":2,
        "item": {
            "@id":"${siteURL}/${carsURL}/",
            "name":"${language.getTextSource('cars.all', [models?size])}"
        }
    }
</#macro>

<#macro addCarDetailsBreadCrumbItem>
    {
        "@type":"ListItem",
        "position":3,
        "item": {
            "@id":"${siteURL}${requestURI}/",
            "name":"${language.getTextSource('${manufacturerName}')} ${car.model}"
        }
    }
</#macro>

<#macro addWebSiteMetaDataElement metaKeywords=language.getTextSource('meta.keywords.index')>
    <script type="application/ld+json">
        {
            "@context":"http://schema.org/",
            "@type":"WebSite",
            "name":"${language.getTextSource('${manufacturerName}History')}",
            "url":"${siteURL}",
            "about":"${language.getTextSource('footer.aboutUs.text', [models?size])}",
            "keywords":"${metaKeywords}",
            "potentialAction": {
                "@type":"SearchAction",
                "target":"${manufacturerShortName}/${siteURL}/${modelsSearchURL}?${contentToSearch}={contentToSearch}",
                "query-input": {
                    "@type":"PropertyValueSpecification",
                    "valueName":"contentToSearch",
                    "valueRequired":"http://schema.org/True"
                }
            }
        }
    </script>
</#macro>