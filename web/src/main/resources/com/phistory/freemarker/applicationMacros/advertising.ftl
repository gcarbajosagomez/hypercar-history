<#macro addHTMLPerformSmaatoAdRequestsScript>
    <script type="text/javascript" src="https://soma-assets.smaato.net/js/smaatoAdTag.js"></script>
    <script>
        <@performSmaatoAdRequest "130205382" "below-the-header-medium-banner" "medrect" "PaganiHistory_${deviceMake?lower_case}_300x250"/>
    </script>
</#macro>

<#macro performSmaatoJSAdRequests>
    $.getScript("https://soma-assets.smaato.net/js/smaatoAdTag.js", function() {
        <@performSmaatoAdRequest "130205382" "below-the-header-medium-banner" "medrect" "PaganiHistory_${deviceMake?lower_case}_300x250"/>
    });
</#macro>

<#macro performSmaatoAdRequest adSpaceId, adDivId, dimension, adSpaceName>
    var options = {publisherId: 1100029117,
                   adSpaceId: ${adSpaceId},
                   adDivId: "${adDivId}",
                   format: "all",
                   formatstrict: false,
                   dimension: "${dimension}",
                   dimensionstrict: false,
                   keywords: "cars,technology",
                   autoReload: 45,
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

<#macro performMopubJSAdRequests>
    <script type="text/javascript">
        window.mopub = [{
            ad_unit: "7ed9b1aa12de4da9afcd8a8a3f7c158a",
            ad_container_id: "below-the-header-small-banner",
            ad_width: 320,
            ad_height: 50,
            keywords: "cars, technology",
        },{
            ad_unit: "09763a959d3643c08f246c2d156cc346",
            ad_container_id: "below-the-header-medium-banner",
            ad_width: 300,
            ad_height: 250,
            keywords: "cars, technology",
        }];

        (function() {
            var mopubjs = document.createElement("script");
            mopubjs.async = true;
            mopubjs.type = "text/javascript";
            mopubjs.src = "//d1zg4cyg8u4pko.cloudfront.net/mweb/mobileweb.min.js";
            var node = document.getElementsByTagName("script")[0];
            node.parentNode.insertBefore(mopubjs, node);
        })();
    </script>
</#macro>