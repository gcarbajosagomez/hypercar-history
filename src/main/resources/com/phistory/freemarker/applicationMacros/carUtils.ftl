<#import "pageLanguage.ftl" as language/>
<#import "genericFunctionalities.ftl" as generic/>

<#function writeCarNumericData data>
    <#if data != -1>
        <#assign numericData>${data?string["0.#"]}</#assign>
    <#else>
        <#assign numericData>${language.getTextSource('undefined')}</#assign>
    </#if>
    <#return numericData/>
</#function>

<#function writeNonDecimalCarNumericData data>
    <#if data != -1>
        <#assign numericData>${data?string["0"]}</#assign>
    <#else>
        <#assign numericData>${language.getTextSource('undefined')}</#assign>
    </#if>
    <#return numericData/>
</#function>

<#macro writeCarBrakeInfo brake>
    <div class="panel panel-default">
        <div class="panel-heading">
            <#if brake.train = "FRONT">
                <h3 class="text-left car-details-panel-heading">${language.getTextSource('brakeSet.front')}</h3>
            <#else>
                <h3 class="text-left car-details-panel-heading">${language.getTextSource('brakeSet.back')}</h3>
            </#if>
        </div>
        <div class="panel-body">
            <dl class="dl-horizontal text-left">
                <dt>
                    ${language.getTextSource('brake.disc.diameter')} :
                </dt>
                <dd>
                    <p class="text-muted">
                        ${writeCarNumericData (brake.discDiameter?default(-1))}<#if brake.discDiameter??><em class="measure-unit-text">${language.getTextSource('MM')}</em></#if>
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
                        ${writeCarNumericData (brake.caliperNumOfPistons?default(-1))}
                    </p>
                </dd>
            </dl>
        </div>
    </div>
</#macro>

<#macro addSetUnitsOfMeasureFunctionScript>
    <script type="application/javascript">
        function setUnitsOfMeasure(unitsOfMeasure, mainForm)
        {
            if ($.cookie('${unitsOfMeasureCookieName}') != unitsOfMeasure && !ajaxCallBeingProcessed)
            {
                $.cookie('${unitsOfMeasureCookieName}', unitsOfMeasure, {path : '/', expires : 14});
                ajaxCallBeingProcessed = true;

                $.ajax({
                            type:'GET',
                            url: mainForm.action,
                            dataType: 'html',
                            beforeSend: function()
                            {
                                if(unitsOfMeasure == '${unitsOfMeasureMetric}')
                                {
                                    $('#metric-units-loading-gif').removeClass('sr-only');
                                }
                                else if(unitsOfMeasure == '${unitsOfMeasureImperial}')
                                {
                                    $('#imperial-units-loading-gif').removeClass('sr-only');
                                }

                                <@generic.addLoadingSpinnerToComponentScript "main-car-details-div"/>
                            }
                        })
                        .done(function(data)
                        {
                            document.body.innerHTML = data;
                            ajaxCallBeingProcessed = false;
                            $('#main-car-details-div').unblock();
                        });
            }
        }
    </script>
</#macro>

<#macro addYoutubeVideosFunctionScript youtubeVideosPresent>
    <#if youtubeVideosPresent>
        <script type="application/javascript">
            function hideOrShowPictures(show)
            {
                if (show)
                {
                    $('#car-pictures-carousel').removeClass('hidden');
                    $('#show-pictures-tab').addClass('active');
                    $('#car-videos-carousel').addClass('hidden');
                    $('#show-videos-tab').removeClass('active');
                }
                else
                {
                    $('#car-pictures-carousel').addClass('hidden');
                    $('#show-pictures-tab').removeClass('active');
                    $('#car-videos-carousel').removeClass('hidden');
                    $('#show-videos-tab').addClass('active');
                }
            }
        </script>

        <script src="/resources/javascript/lib/youtube-iframe-api.min.js"></script>
        <script type="application/javascript">
            <#list youtubeVideoIds as videoId>
                var player${videoId?index};
            </#list>
            function onYouTubeIframeAPIReady() {
                <#list youtubeVideoIds as videoId>
                    player${videoId?index} = new YT.Player('${videoId}-iframe-div', {
                        videoId: '${videoId}',
                        width: '100%',
                        height: '600',
                        events: {
                            'onStateChange': on${videoId?index}PlayerStateChange
                        }
                    });

                    function on${videoId?index}PlayerStateChange(event) {
                        switch (event.data) {
                            case 1:
                                $('#car-videos-carousel').carousel('pause');
                                break;
                            case 2:
                                $('#car-videos-carousel').carousel({pause: false, interval: 8000});
                                break;
                        }
                    }
                </#list>
            }

            $('#car-videos-carousel').bind('slide.bs.carousel', function (e) {
                <#list youtubeVideoIds as videoId>
                    player${videoId?index}.pauseVideo();
                </#list>
            });
        </script>
    </#if>
</#macro>