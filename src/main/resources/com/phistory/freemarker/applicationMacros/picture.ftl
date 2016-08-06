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