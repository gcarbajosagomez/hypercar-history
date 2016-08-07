<#import "pageLanguage.ftl" as language/>

<#macro addPictureUploadBoxFunctionScript>

<script type="application/javascript">
    function addPictureUploadBox() {
        var pictureUploadBoxNum = $("input[id^='carForm.pictureFiles']").length;
        var newPictureUploadBox = $('<input>', {
            'type': 'file',
            'id': 'carForm.pictureFiles[' + pictureUploadBoxNum + ']',
            'name': 'carForm.pictureFiles[' + pictureUploadBoxNum + ']',
            'onChange': 'displayCarPictureWhenFileSelected(this.files[0]);',
            'accept': 'image/*',
            'size': '10'
        });

        var numberOfCarPictureAreas = $("[id^='car-picture-area']").length;
        var newCarPictureAreaDiv = $('<div>', {'id': 'car-picture-area-' + numberOfCarPictureAreas});
        newCarPictureAreaDiv.append($('<img>', {
            'id': 'car-picture-' + numberOfCarPictureAreas,
            'class': "thumbnail preview-img"
        }));

        var pictureFileInputTd = $('<td>');
        pictureFileInputTd.append('<br>');
        pictureFileInputTd.append(newPictureUploadBox);
        var pictureAreaTd = $('<td>');
        pictureAreaTd.append('<br>');
        pictureAreaTd.append(newCarPictureAreaDiv);
        var newTr = $('<tr>');
        newTr.append(pictureFileInputTd);
        newTr.append(pictureAreaTd);

        $('#pictureUploadInputs').find('tbody').append(newTr);
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

<#macro addDisplayCarPictureWhenFileSelected>

<script type="application/javascript">
    function displayCarPictureWhenFileSelected(previewFile) {
        var reader = new FileReader();

        reader.onload = function (e) {
            var numberOfCarPictures = $("[id^='car-picture-area']").length;
        <#-- first car-picture-area is number 0 -->
            numberOfCarPictures--;
            var img = $('#car-picture-' + numberOfCarPictures)[0];
            img.src = reader.result;

            $('#car-picture-area-' + numberOfCarPictures).append(img);
        }

        reader.readAsDataURL(previewFile);
    }
</script>
</#macro>

<#macro addBlueImpGallery>
    <#-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
    <div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-use-bootstrap-modal="false">
    <#-- The container for the modal slides -->
        <div class="slides"></div>
    <#-- Controls for the borderless lightbox -->
        <h3 class="title"></h3>
        <a class="prev">‹</a>
        <a class="next">›</a>
        <a class="close">×</a>
        <a class="play-pause"></a>
        <ol class="indicator"></ol>
    <#-- The modal dialog, which will be used to wrap the lightbox content -->
        <div class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" aria-hidden="true">&times;</button>
                        <h4 class="modal-title"></h4>
                    </div>
                    <div class="modal-body next"></div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default pull-left prev">
                            <i class="glyphicon glyphicon-chevron-left"></i>
                            ${language.getTextSource('previous')}
                        </button>
                        <button type="button" class="btn btn-primary next">
                            ${language.getTextSource('next')}
                            <i class="glyphicon glyphicon-chevron-right"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>