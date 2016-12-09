<#macro addHTMLPerformSmaatoAdRequestScript>
    <script type="text/javascript" src="https://soma-assets.smaato.net/js/smaatoAdTag.js"></script>
    <script>
        <@performSmaatoAdRequest/>
    </script>
</#macro>

<#macro performSmaatoJSAdRequest>
    $.getScript("https://soma-assets.smaato.net/js/smaatoAdTag.js", function() {
        <@performSmaatoAdRequest/>
    });
</#macro>

<#macro performSmaatoAdRequest>
    var options = {publisherId: 1100029117,
                   adSpaceId: 130205382,
                   adDivId: "smt-130205382",
                   format: "all",
                   formatstrict: false,
                   dimension: "xxlarge",
                   dimensionstrict: false,
                   keywords: "cars, supercars",
                   autoReload: 60,
                   coppa: 0,
                   iabcategory: "IAB2-4",
                   adspacename: "PaganiHistory_${deviceMake?lower_case}_320x50"
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