<#import "../applicationMacros/pageLanguage.ftl" as language/>

<#macro addPictureUploadBoxFunctionScript>

    <script type="application/javascript">
        function addPictureUploadBox() {
            var dividerDiv = $('<hr>');
            var pictureUploadBoxNum = $("input[id$='picture.galleryPosition']").length;
            var numberOfCarPictureAreas = $("[id^='car-picture-area']").length;

            var newPictureUploadBoxId = 'editForm.pictureFileEditCommands[' + pictureUploadBoxNum + '].pictureFile';
            var newPictureUploadBox = $('<input>', {
                'type': 'file',
                'id': newPictureUploadBoxId,
                'name': newPictureUploadBoxId,
                'onChange': 'displayCarPictureWhenFileSelected(this.files[0],' + numberOfCarPictureAreas + ');',
                'accept': 'image/*',
                'class': 'form-control'
            });

            var newCarPictureAreaDiv = $('<div>', {'id': 'car-picture-area-' + numberOfCarPictureAreas});
            newCarPictureAreaDiv.append($('<img>', {
                'id': 'car-picture-' + numberOfCarPictureAreas,
                'class': "thumbnail resizable-img edit-car-picture"
            }));

            var galleryPositionInputId = 'editForm.pictureFileEditCommands[' + pictureUploadBoxNum + '].picture.galleryPosition';
            var newGalleryPositionInput = $('<input>', {
                'type': 'text',
                'id': galleryPositionInputId,
                'name': galleryPositionInputId,
                'class': 'pull-right form-control'
            });

            var newEligibleForPreviewSelectId = 'editForm.pictureFileEditCommands[' + pictureUploadBoxNum + '].picture.eligibleForPreview';
            var newEligibleForPreviewSelect = $('<select>', {
                'id': newEligibleForPreviewSelectId,
                'name': newEligibleForPreviewSelectId,
                'class': 'form-control'
            });
            newEligibleForPreviewSelect.append($('<option></option>', {
                'value': 'false',
                'selected': 'true'
            })
                    .text("${language.getTextSource('no')}"));

            newEligibleForPreviewSelect.append($('<option></option>', {
                'value': 'true'
            })
                    .text("${language.getTextSource('yes')}"));

            var dividerDivTd =  $('<td>', {'colspan': '2'});
            dividerDivTd.append(dividerDiv)
            var pictureFileInputTd = $('<td>', {'style': 'width:65%'});
            pictureFileInputTd.append(newPictureUploadBox);

            var pictureDataTd = $('<td>', {
                'rowspan': '2',
                'style': 'width:35%; padding-left:40px'
            });

            var galleryPositionDl = $('<dl>', {'class': 'dl-vertical text-left'});
            var galleryPositionDt = $('<dt>');
            galleryPositionDt.append("${language.getTextSource('picture.galleryPosition')}");

            var galleryPositionDd = $('<dd>');
            galleryPositionDd.append(newGalleryPositionInput);

            galleryPositionDl.append(galleryPositionDt);
            galleryPositionDl.append(galleryPositionDd);

            var eligibleForPreviewDl = $('<dl>', {'class': 'dl-vertical text-left'});
            var eligibleForPreviewDt = $('<dt>');
            eligibleForPreviewDt.append("${language.getTextSource('picture.eligibleForPreview')}");

            var eligibleForPreviewDd = $('<dd>');
            eligibleForPreviewDd.append(newEligibleForPreviewSelect);

            eligibleForPreviewDl.append(eligibleForPreviewDt);
            eligibleForPreviewDl.append(eligibleForPreviewDd);

            pictureDataTd.append(galleryPositionDl);
            pictureDataTd.append(eligibleForPreviewDl);

            var pictureAreaTd = $('<td>');
            pictureAreaTd.append(newCarPictureAreaDiv);

            var dividerTr = $('<tr>');
            dividerTr.append(dividerDivTd);

            var pictureFileInputTr = $('<tr>');
            pictureFileInputTr.append(pictureFileInputTd);
            pictureFileInputTr.append(pictureDataTd);

            var pictureAreaTr = $('<tr>');
            pictureAreaTr.append(pictureAreaTd);

            $('#pictureUploadInputs').find('tbody').append(dividerTr);
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

<#macro addDisplayCarPictureWhenFileSelectedFunctionScript>

    <script type="application/javascript">
        function displayCarPictureWhenFileSelected(previewFile, pictureAreaIndex) {
            var reader = new FileReader();

            reader.onload = function (e) {
                var img = $('#car-picture-' + pictureAreaIndex)[0];
                img.src = reader.result;

                $('#car-picture-area-' + pictureAreaIndex).append(img);
            };

            reader.readAsDataURL(previewFile);
        }
    </script>
</#macro>