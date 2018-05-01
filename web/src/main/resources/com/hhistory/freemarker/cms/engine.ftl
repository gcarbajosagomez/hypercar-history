<#import "/spring.ftl" as spring/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/genericFunctionalities.ftl" as generic/>
<#import "../applicationMacros/uriUtils.ftl" as uriUtils/>

<#macro addLoadEngineFromDBScriptFunction>
<script type="application/javascript">

    function loadEngineFromDB() {
        $('#engine-code-selection-table').removeClass('sr-only');
    }
</script>
</#macro>

<#macro addEraseEngineFormFieldsScriptFunction>
<script type="application/javascript">

    function eraseEngineFormFields() {
        removeAllSuccessMessages();
        removeAllErrorMessages();
    <#--By loading a null Id we can easily erase all the inputs -->
        fillEngineInputValues({});

        $('#engine-code-selection-table').addClass('sr-only');
        $('#engine-id-dt').addClass('sr-only');
        $('#engine-id-dd').addClass('sr-only');
        $('#engine-save-or-edit-link').attr("onClick", "saveEngine()");
        $('#engine-save-or-edit-span').text(" ${language.getTextSource('cms.saveEngine')}");
    }
</script>
</#macro>

<#macro addLoadEngineByIdFunctionScript>
<script type="application/javascript">

    function loadEngineById(engineId) {
        if (!ajaxCallBeingProcessed) {
            ajaxCallBeingProcessed = true;

            $('.sr-only').removeClass('sr-only');

            $.ajax({
                        type: 'GET',
                        url: '${uriUtils.buildDomainURI("/${cmsContext}${engineURL}/")}' + engineId,
                        contentType: 'application/json; charset=UTF-8',
                        dataType: 'json',
                        beforeSend: function (xhr) {
                            <@generic.addLoadingSpinnerToComponentScript "engine-main-div"/>
                            addCRSFTokenToAjaxRequest(xhr);
                        }
                    })
                    .done(function (engine) {
                        $('#engine-main-div').unblock();

                        fillEngineInputValues(engine);
                        $('#engine-save-or-edit-link').attr("onClick", "editEngine(" + engine.id + ")");
                        $('#engine-save-or-edit-span').text(" ${language.getTextSource('cms.editEngine')}");
                        ajaxCallBeingProcessed = false;
                    });
        }
    }
</script>
</#macro>

<#macro addFillEngineInputValuesFunctionScript>
<script type="application/javascript">

    function fillEngineInputValues(engine) {
        removeAllSuccessMessages();
        removeAllErrorMessages();

        if (engine) {
            if (engine.id) {
                $('#editForm\\.engineEditForm\\.id\\.label')[0].innerText = engine.id;
                $('#editForm\\.engineEditForm\\.id')[0].value = engine.id;
            }
            else {
                $('#editForm\\.engineEditForm\\.id\\.label')[0].innerText = "";
                $('#editForm\\.engineEditForm\\.id')[0].value = "";
                $('#load-engine-by-id-select')[0].value = "";
            }

            if (engine.code) {
                $('#editForm\\.engineEditForm\\.code')[0].value = engine.code;
            }
            else {
                $('#editForm\\.engineEditForm\\.code')[0].value = "";
            }

            if (engine.type) {
                $('#editForm\\.engineEditForm\\.type')[0].value = engine.type;
            }
            else {
                $('#editForm\\.engineEditForm\\.type')[0].value = "";
            }

            if (engine.cylinderDisposition) {
                $('#editForm\\.engineEditForm\\.cylinderDisposition')[0].value = engine.cylinderDisposition;
            }
            else {
                $('#editForm\\.engineEditForm\\.cylinderDisposition')[0].value = "";
            }

            if (engine.cylinderBankAngle) {
                $('#editForm\\.engineEditForm\\.cylinderBankAngle')[0].value = engine.cylinderBankAngle;
            }
            else {
                $('#editForm\\.engineEditForm\\.cylinderBankAngle')[0].value = "";
            }

            if (engine.numberOfCylinders) {
                $('#editForm\\.engineEditForm\\.numberOfCylinders')[0].value = engine.numberOfCylinders;
            }
            else {
                $('#editForm\\.engineEditForm\\.numberOfCylinders')[0].value = "";
            }

            if (engine.numberOfValves) {
                $('#editForm\\.engineEditForm\\.numberOfValves')[0].value = engine.numberOfValves;
            }
            else {
                $('#editForm\\.engineEditForm\\.numberOfValves')[0].value = "";
            }

            if (engine.size) {
                $('#editForm\\.engineEditForm\\.size')[0].value = engine.size;
            }
            else {
                $('#editForm\\.engineEditForm\\.size')[0].value = "";
            }

            if (engine.maxPower) {
                $('#editForm\\.engineEditForm\\.maxPower')[0].value = engine.maxPower;
            }
            else {
                $('#editForm\\.engineEditForm\\.maxPower')[0].value = "";
            }

            if (engine.maxRPM) {
                $('#editForm\\.engineEditForm\\.maxRPM')[0].value = engine.maxRPM;
            }
            else {
                $('#editForm\\.engineEditForm\\.maxRPM')[0].value = "";
            }

            if (engine.maxPowerRPM) {
                $('#editForm\\.engineEditForm\\.maxPowerRPM')[0].value = engine.maxPowerRPM;
            }
            else {
                $('#editForm\\.engineEditForm\\.maxPowerRPM')[0].value = "";
            }

            if (engine.maxTorque) {
                $('#editForm\\.engineEditForm\\.maxTorque')[0].value = engine.maxTorque;
            }
            else {
                $('#editForm\\.engineEditForm\\.maxTorque')[0].value = "";
            }

            if (engine.maxTorqueRPM) {
                $('#editForm\\.engineEditForm\\.maxTorqueRPM')[0].value = engine.maxTorqueRPM;
            }
            else {
                $('#editForm\\.engineEditForm\\.maxTorqueRPM')[0].value = "";
            }
        }
    }
</script>
</#macro>

<#macro addSaveEngineFunctionScript>

<script type="application/javascript">
    function saveEngine() {
        var engineEditFormCommand = createEngineFromDOM();

        bootbox.confirm("${language.getTextSource('engine.confirmSave')}", function (result) {
            //OK button
            if (result === true) {
                $.ajax({
                    url: '/${cmsContext}${engineURL}/${saveURL}',
                    type: 'POST',
                    data: JSON.stringify(engineEditFormCommand),
                    contentType: 'application/json; charset=UTF-8',
                    beforeSend: function (xhr) {
                        <@generic.addLoadingSpinnerToComponentScript "engine-main-div"/>
                        addCRSFTokenToAjaxRequest(xhr);
                    }
                }).done(function (data) {
                    appendEngineCrudOperationsResultMessages(data);
                    $('#engine-main-div').unblock();
                });
            }
        });
    }
</script>
</#macro>

<#macro addEditEngineFunctionScript>

<script type="application/javascript">
    function editEngine(engineId) {
        var engineEditFormCommand = createEngineFromDOM();

        bootbox.confirm("${language.getTextSource('engine.confirmEdit')}", function (result) {
            //OK button
            if (result === true) {
                $.ajax({
                    url: '/${cmsContext}${engineURL}/' + engineId + '/${editURL}',
                    type: 'PUT',
                    data: JSON.stringify(engineEditFormCommand),
                    contentType: 'application/json; charset=UTF-8',
                    beforeSend: function (xhr) {
                        <@generic.addLoadingSpinnerToComponentScript "engine-main-div"/>
                        addCRSFTokenToAjaxRequest(xhr);
                    }
                }).done(function (data) {
                    appendEngineCrudOperationsResultMessages(data);
                    $('#engine-main-div').unblock();
                });
            }
        });
    }
</script>
</#macro>

<#macro addDeleteEngineFunctionScript>

<script type="application/javascript">
    function deleteEngine(engineId) {

        bootbox.confirm("${language.getTextSource('engine.confirmDelete')}", function (result) {
            //OK button
            if (result === true) {
                $.ajax({
                    url: '/${cmsContext}${engineURL}/' + engineId + '/${deleteURL}',
                    type: 'DELETE',
                    beforeSend: function (xhr) {
                        <@generic.addLoadingSpinnerToComponentScript "engine-main-div"/>
                        addCRSFTokenToAjaxRequest(xhr);
                    }
                }).done(function (data) {
                    appendEngineCrudOperationsResultMessages(data);
                    var successMessage = data.successMessage;
                    if (successMessage) {
                        <#-- empty engine input values after deletion -->
                        fillEngineInputValues({});
                    }
                    $('#engine-main-div').unblock();
                });
            }
        });
    }
</script>
</#macro>