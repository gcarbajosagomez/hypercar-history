<#import "pageLanguage.ftl" as language/>

<#macro addPictureUploadBoxFunctionScript>

    <script type="application/javascript">
        function addPictureUploadBox() {
            var pictureUploadBoxNum = $("input[id^='carForm.pictureFiles']").length;
            var numberOfCarPictureAreas = $("[id^='car-picture-area']").length;
            var newPictureUploadBox = $('<input>', {
                'type': 'file',
                'id': 'carForm.pictureFiles[' + pictureUploadBoxNum + ']',
                'name': 'carForm.pictureFiles[' + pictureUploadBoxNum + ']',
                'onChange': 'displayCarPictureWhenFileSelected(this.files[0],' + numberOfCarPictureAreas + ');',
                'accept': 'image/*',
                'size': '10',
                'class': 'form-control'
            });

            var newCarPictureAreaDiv = $('<div>', {'id': 'car-picture-area-' + numberOfCarPictureAreas});
            newCarPictureAreaDiv.append($('<img>', {
                'id': 'car-picture-' + numberOfCarPictureAreas,
                'class': "thumbnail resizable-img"
            }));

            var pictureFileInputTd = $('<td>');
            pictureFileInputTd.append(newPictureUploadBox);
            var pictureAreaTd = $('<td>');
            pictureAreaTd.append(newCarPictureAreaDiv);
            var pictureFileInputTr = $('<tr>');
            pictureFileInputTr.append(pictureFileInputTd);
            var pictureAreaTr = $('<tr>');
            pictureAreaTr.append(pictureAreaTd);

            $('#pictureUploadInputs').find('tbody').append(pictureFileInputTr);
            $('#pictureUploadInputs').find('tbody').append(pictureAreaTr);
        }
    </script>
</#macro>

<#macro addDisplayPreviewImageWhenFileSelectedFunctionScript>

    <script type="application/javascript">
        function displayPreviewImageWhenFileSelected(previewFile) {
            var reader = new FileReader();

            reader.onload = function (e) {
                var img = $('#car-preview-image')[0];
                img.src = reader.result;

                $('#car-preview-picture-area').append(img);
            }

            reader.readAsDataURL(previewFile);
        }
    </script>
</#macro>

<#macro addDisplayCarPictureWhenFileSelectedFunctionScript>

    <script type="application/javascript">
        function displayCarPictureWhenFileSelected(previewFile, pictureAreaIndex) {
            var reader = new FileReader();

            reader.onload = function (e) {
                var img = $('#car-picture-' + pictureAreaIndex)[0];
                img.src = reader.result;

                $('#car-picture-area-' + pictureAreaIndex).append(img);
            }

            reader.readAsDataURL(previewFile);
        }
    </script>
</#macro>

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
                    .done(function (<#list youtubeVideoIds as videoId>${videoId?string?replace("-", "")}<#if videoId?has_next>,</#if></#list>) {
                        var options = {
                            container: '#${galleryName}',
                            fullScreen: true,
                            useBootstrapModal: false,
                            youTubeClickToPlay: <#if requestIsDesktop>false<#else>true</#if>,
                            youTubePlayerVars: {rel: 0}
                        }
                        blueimp.Gallery(galleryVideos, options);
                    });
        }
    </script>
</#macro>