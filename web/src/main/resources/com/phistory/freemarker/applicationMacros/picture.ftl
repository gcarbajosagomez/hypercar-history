<#import "pageLanguage.ftl" as language/>

<#macro addPictureUploadBoxFunctionScript>

    <script type="application/javascript">
        function addPictureUploadBox() {
            var pictureUploadBoxNum = $("input[id$='picture.galleryPosition']").length;
            var numberOfCarPictureAreas = $("[id^='car-picture-area']").length;
            var newPictureUploadBox = $('<input>', {
                'type': 'file',
                'id': 'carForm.pictureFileEditCommands[' + pictureUploadBoxNum + '].pictureFile',
                'name': 'carForm.pictureFileEditCommands[' + pictureUploadBoxNum + '].pictureFile',
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

            var galleryPositionInputId = 'carForm.pictureFileEditCommands[' + pictureUploadBoxNum + '].picture.galleryPosition';
            var newGalleryPositionInput = $('<input>', {
                'type': 'text',
                'id': galleryPositionInputId,
                'name': galleryPositionInputId,
                'size': '10',
                'class': 'pull-right form-control'
            });

            var pictureFileInputTd = $('<td>', {'style': 'width:80%'});
            pictureFileInputTd.append(newPictureUploadBox);
            var galleryPositionInputTd = $('<td>', {'style': 'width:20%; padding-left:40px'});
            galleryPositionInputTd.append(newGalleryPositionInput);
            var pictureAreaTd = $('<td>');
            pictureAreaTd.append(newCarPictureAreaDiv);
            var pictureFileInputTr = $('<tr>');
            pictureFileInputTr.append(pictureFileInputTd);
            pictureFileInputTr.append(galleryPositionInputTd);
            var pictureAreaTr = $('<tr>');
            pictureAreaTr.append(pictureAreaTd);

            $('#pictureUploadInputs').find('tbody').append(pictureFileInputTr);
            $('#pictureUploadInputs').find('tbody').append(pictureAreaTr);
            $("input[name='" + galleryPositionInputId + "']").TouchSpin({
                verticalbuttons: true,
                verticalupclass: 'glyphicon glyphicon-plus',
                verticaldownclass: 'glyphicon glyphicon-minus'
            });
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
                    .done(function (<#list youtubeVideoIds as videoId>video${videoId?index}<#if videoId?has_next>,</#if></#list>) {
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