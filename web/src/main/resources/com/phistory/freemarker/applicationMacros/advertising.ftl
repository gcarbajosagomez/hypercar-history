<#macro addHTMLPerformSmaatoAdRequestsScript>
    <script type="text/javascript" src="https://soma-assets.smaato.net/js/smaatoAdTag.js"></script>
    <script>
        <@performSmaatoAdRequest "130205382" "medrect" "PaganiHistory_${deviceMake?lower_case}_300x250"/>
    </script>
</#macro>

<#macro performSmaatoJSAdRequests>
    $.getScript("https://soma-assets.smaato.net/js/smaatoAdTag.js", function() {
        <@performSmaatoAdRequest "130205382" "medrect" "PaganiHistory_${deviceMake?lower_case}_300x250"/>
    });
</#macro>

<#macro performSmaatoAdRequest adSpaceId, dimension, adSpaceName>
    var options = {publisherId: 1100029117,
                   adSpaceId: ${adSpaceId},
                   adDivId: "smt-${adSpaceId}",
                   format: "all",
                   formatstrict: false,
                   dimension: "${dimension}",
                   dimensionstrict: false,
                   keywords: "cars, supercars",
                   autoReload: 60,
                   coppa: 0,
                   iabcategory: "IAB2-4",
                   adspacename: "${adSpaceName}"
                  };

    <#if contentToSearchData??>
        options.qs = "${contentToSearchData}";
    <#else>
        var contentToSearchInputValue = $("#content-search-input")[0].value;
        if (contentToSearchInputValue && contentToSearchInputValue.length > 0) {
            options.qs = contentToSearchInputValue;
        }
    </#if>

    SomaJS.loadAd(options);
</#macro>