<#import "genericFunctionalities.ftl" as generic/>
<#import "pageLanguage.ftl" as language/>

<#macro addCreateEntityFunctionScript>

    <script type="application/javascript">
        function saveEntity(url, confirmSaveMessage) {
            bootbox.confirm(confirmSaveMessage, function (result) {
                //OK button
                if (result == true) {
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
                if (result == true) {
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
                            })
                            .done(function (data) {
                                document.children[0].innerHTML = data;
                                $('#main-container').unblock();
                            });
                }
            });
        }
    </script>
</#macro>

<#macro addEditEngineFunctionScript>

    <script type="application/javascript">
        function editEngine() {
            bootbox.confirm("${language.getTextSource('engine.confirmEdit')}", function (result) {
                //OK button
                if (result == true) {
                    $.ajax({
                                url: '/${cmsContext}${enginesURL}/${editURL}',
                                type: 'POST',
                                data: JSON.stringify(),
                                contentType: 'application/json; charset=UTF-8',
                                beforeSend: function (xhr) {
                                    addCRSFTokenToAjaxRequest(xhr);
                                }
                            })
                            .done(function (data) {

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
                if (result == true) {
                    $.ajax({
                                url: url,
                                type: 'DELETE',
                                beforeSend: function (xhr) {
                                    <@generic.addLoadingSpinnerToComponentScript "main-container"/>
                                    addCRSFTokenToAjaxRequest(xhr);
                                }
                            })
                            .done(function (data) {
                                document.children[0].innerHTML = data;
                                window.history.pushState(null,'', url.replace(/\/[0-9]{1,}\/${deleteURL}/, "/${editURL}"));
                                $('#main-container').unblock();
                            });
                }
            });
        }
    </script>
</#macro>