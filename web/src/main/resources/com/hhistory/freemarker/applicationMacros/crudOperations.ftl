<#import "genericFunctionalities.ftl" as generic/>
<#import "pageLanguage.ftl" as language/>

<#macro addCreateEntityFunctionScript>

    <script type="application/javascript">
        function saveEntity(url, confirmSaveMessage) {
            bootbox.confirm(confirmSaveMessage, function (result) {
                //OK button
                if (result === true) {
                    $.ajax({
                        type: 'POST',
                        url: url,
                        data: new FormData($("#main-form")[0]),
                        processData: false,
                        contentType: false,
                        beforeSend: function (xhr) {
                            <@generic.addLoadingSpinnerToComponentScript "main-container"/>
                            addCRSFTokenToAjaxRequest(xhr);
                        }
                    }).done(function (data) {
                        document.children[0].innerHTML = data;
                        setupPictureGalleryPositionInputs();
                        addDatePicker();
                        addBrakeTypeSelectListener();

                        var savedEntityId = $("input[id*='.id']")[0].value;
                        var urlReplacement = '';

                        if (savedEntityId.length > 0) {
                            urlReplacement = "/" + savedEntityId + "/${editURL}";
                        }
                        else {
                            urlReplacement = "/${editURL}";
                        }

                        window.history.pushState(null, '', url.replace("/${saveURL}", urlReplacement));
                        $('#main-container').unblock();
                    });
                }
            });
        }
    </script>
</#macro>

<#macro addEditEntityFunctionScript>
    <script type="application/javascript">
        function editEntity(url, confirmEditMessage) {
            bootbox.confirm(confirmEditMessage, function (result) {
                //OK button
                if (result === true) {
                    $.ajax({
                        type: 'POST',
                        url: url,
                        data: new FormData($("#main-form")[0]),
                        processData: false,
                        contentType: false,
                        beforeSend: function (xhr) {
                            <@generic.addLoadingSpinnerToComponentScript "main-container"/>
                            addCRSFTokenToAjaxRequest(xhr);
                        }
                    }).done(function (data) {
                        document.children[0].innerHTML = data;
                        setupPictureGalleryPositionInputs();
                        addDatePicker();
                        addBrakeTypeSelectListener();

                        $('#main-container').unblock();
                    });
                }
            });
        }
    </script>
</#macro>

<#macro addDeleteEntityFunctionScript>
    <script type="application/javascript">
        function deleteEntity(url, confirmDeleteMessage) {
            bootbox.confirm(confirmDeleteMessage, function (result) {
                //OK button
                if (result === true) {
                    $.ajax({
                        url: url,
                        type: 'DELETE',
                        beforeSend: function (xhr) {
                            <@generic.addLoadingSpinnerToComponentScript "main-container"/>
                            addCRSFTokenToAjaxRequest(xhr);
                        }
                    }).done(function (data) {
                        document.children[0].innerHTML = data;
                        window.history.pushState(null, '', url.replace(/\/[0-9]{1,}\/${deleteURL}/, "/${editURL}"));
                        $('#main-container').unblock();
                    });
                }
            });
        }
    </script>
</#macro>

<#macro addOperationResultMessage crudOperationDTO>
    <#if crudOperationDTO.errorMessages??>
        <#list crudOperationDTO.errorMessages as errorMessage>
            <#if errorMessage?has_content>
                <div class="col-xs-12 alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    ${errorMessage}
                </div>
            </#if>
        </#list>
    <#elseif crudOperationDTO.successMessage?has_content>
        <div class="col-xs-12 alert alert-success" role="info">
            <span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
            ${crudOperationDTO.successMessage}
        </div>
    </#if>
</#macro>