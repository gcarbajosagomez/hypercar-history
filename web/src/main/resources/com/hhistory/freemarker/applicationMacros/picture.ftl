<#import "/spring.ftl" as spring/>
<#import "pageLanguage.ftl" as language/>
<#import "uriUtils.ftl" as uriUtils/>

<#macro addPicturesGallery galleryName classNameToTriggerGallery>
    <div id="${galleryName}" class="blueimp-gallery blueimp-gallery-controls">
        <div class="slides"></div>
        <h3 class="title"></h3>
        <a class="prev">‹</a>
        <a class="next">›</a>
        <a class="close">×</a>
        <a class="play-pause"></a>
        <ol class="indicator"></ol>
    </div>

    <script type="application/javascript">
        <@addPicturesGalleryFunctionScript galleryName classNameToTriggerGallery/>
    </script>
</#macro>

<#macro addPicturesGalleryFunctionScript galleryName classNameToTriggerGallery>
    $(".${classNameToTriggerGallery}").click(function (event) {
        event = event || window.event;

        var options = {container: '#${galleryName}',
                       event: event,
                       useBootstrapModal: false,
                       fullScreen: true,
                       stretchImages: true};

        var links = $("a[gallery='#${galleryName}']");
        blueimp.Gallery(links, options);
    });
</#macro>

<#macro addOpenVideoGalleryFunctionScript galleryName>

    <div id="${galleryName}" class="blueimp-gallery blueimp-gallery-controls">
        <div class="slides"></div>
        <h3 class="title"></h3>
        <a class="prev">‹</a>
        <a class="next">›</a>
        <a class="close">×</a>
        <a class="play-pause"></a>
        <ol class="indicator"></ol>
    </div>

    <script type="application/javascript">
        function openVideoGallery() {
            var galleryVideos = [];

            <#--By doing this we defer adding the videos until all AJAX calls have been completed -->
            $.when(<#list youtubeVideoIds as videoId>
                    $.getJSON('https://noembed.com/embed',
                            {format: 'json', url: 'https://www.youtube.com/watch?v=${videoId}'}, function (data) {
                                galleryVideos.push({
                                    title: data.title,
                                    href: 'https://www.youtube.com/watch?v=${videoId}',
                                    type: 'text/html',
                                    youtube: '${videoId}',
                                    poster: 'https://img.youtube.com/vi/${videoId}/sddefault.jpg'
                                });
                            })<#if videoId?has_next>,</#if>
                    </#list>)
                    .done(function (<#list youtubeVideoIds as videoId>video${videoId?index}<#if videoId?has_next>,</#if></#list>) {
                        var options = {
                            container: '#${galleryName}',
                            fullScreen: true,
                            useBootstrapModal: false,
                            youTubeClickToPlay: <#if requestIsDesktop>false<#else>true</#if>,
                            youTubePlayerVars: {
                                rel: 0,
                                color: 'white',
                                playsinline: 0
                            }
                        }
                        blueimp.Gallery(galleryVideos, options);
                    });
        }
    </script>
</#macro>

<#macro printCarPreview car car_index row_index>
    <#assign modelName>${car.model}</#assign>
    <div id="${car.manufacturer.name}-${modelName}-div" class="col-lg-6 col-md-6 col-sm-12 preview-outer<#if !requestIsDesktop> center-block</#if>">
        <#assign zIndex = (car_index + 1) * (row_index + 1)>
    <#--the Z-index of the elements on top must be higher than those below, threrfore the figure must be inverted -->
        <#assign zIndex = zIndex + (cars?size - ((car_index + 1) * (row_index + 1)) - zIndex)>
        <#if requestIsDesktop && car_index == 0>
            <#assign zIndex = (zIndex) - (1 * row_index)>
        </#if>
        <div class="thumbnail preview-div">
            <li style="z-index: <#if zIndex??>${zIndex}<#else>1</#if>">
                <figure>
                    <div class="caption vertically-aligned-div vertically-aligned-preview-div">
                        <a href='${uriUtils.buildDomainURI("/${carURL}/${car.getNormalizedModelName()}")}'>
                            <img class="img-thumbnail preview-img"
                                 src='<@spring.url "/${picturesURL}/${loadCarPreviewAction}?${id}=${car.id}"/><#if doNotTrack>&${doNotTrackParam}=true</#if>'
                                 alt="${car.manufacturer.name} ${modelName} preview"
                                 title="${car.manufacturer.name} ${modelName}">
                        </a>
                    </div>
                    <figcaption>
                        <a href='${uriUtils.buildDomainURI("/${carURL}/${car.getNormalizedModelName()}")}'>
                            <h3 class="text-<#if requestIsDesktop>center<#else>left center-block</#if>
                                            <#if (requestIsDesktop && modelName?length > 33) || (!requestIsDesktop && modelName?length > 21)> double-line-car-model-name</#if>">${modelName}</h3>
                        </a>
                    </figcaption>
                </figure>
            </li>
        </div>
    </div>
</#macro>