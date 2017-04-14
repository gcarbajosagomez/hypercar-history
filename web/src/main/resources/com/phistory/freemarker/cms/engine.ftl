<#import "/spring.ftl" as spring/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/genericFunctionalities.ftl" as generic/>

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
        $('#engine-save-or-edit-link').attr("onClick", "saveEntity('<@spring.url "/${cmsContext}${engineURL}/${saveURL}"/>', '${language.getTextSource('engine.confirmSave')}')");
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
                        url: '<@spring.url "/${cmsContext}${engineURL}/"/>' + engineId,
                        contentType: 'application/json; charset=UTF-8',
                        beforeSend: function (xhr) {
                            <@generic.addLoadingSpinnerToComponentScript "engine-main-div"/>
                            addCRSFTokenToAjaxRequest(xhr);
                        }
                    })
                    .done(function (data) {
                        var engine = data;
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
                $('#carForm\\.engineForm\\.id\\.label')[0].innerText = engine.id;
                $('#carForm\\.engineForm\\.id')[0].value = engine.id;
            }
            else {
                $('#carForm\\.engineForm\\.id\\.label')[0].innerText = "";
                $('#carForm\\.engineForm\\.id')[0].value = "";
            }

            if (engine.code) {
                $('#carForm\\.engineForm\\.code')[0].value = engine.code;
            }
            else {
                $('#carForm\\.engineForm\\.code')[0].value = "";
            }

            if (engine.type) {
                $('#carForm\\.engineForm\\.type')[0].value = engine.type;
            }
            else {
                $('#carForm\\.engineForm\\.type')[0].value = "";
            }

            if (engine.cylinderDisposition) {
                $('#carForm\\.engineForm\\.cylinderDisposition')[0].value = engine.cylinderDisposition;
            }
            else {
                $('#carForm\\.engineForm\\.cylinderDisposition')[0].value = "";
            }

            if (engine.cylinderBankAngle) {
                $('#carForm\\.engineForm\\.cylinderBankAngle')[0].value = engine.cylinderBankAngle;
            }
            else {
                $('#carForm\\.engineForm\\.cylinderBankAngle')[0].value = "";
            }

            if (engine.numberOfCylinders) {
                $('#carForm\\.engineForm\\.numberOfCylinders')[0].value = engine.numberOfCylinders;
            }
            else {
                $('#carForm\\.engineForm\\.numberOfCylinders')[0].value = "";
            }

            if (engine.numberOfValves) {
                $('#carForm\\.engineForm\\.numberOfValves')[0].value = engine.numberOfValves;
            }
            else {
                $('#carForm\\.engineForm\\.numberOfValves')[0].value = "";
            }

            if (engine.size) {
                $('#carForm\\.engineForm\\.size')[0].value = engine.size;
            }
            else {
                $('#carForm\\.engineForm\\.size')[0].value = "";
            }

            if (engine.maxPower) {
                $('#carForm\\.engineForm\\.maxPower')[0].value = engine.maxPower;
            }
            else {
                $('#carForm\\.engineForm\\.maxPower')[0].value = "";
            }

            if (engine.maxRPM) {
                $('#carForm\\.engineForm\\.maxRPM')[0].value = engine.maxRPM;
            }
            else {
                $('#carForm\\.engineForm\\.maxRPM')[0].value = "";
            }

            if (engine.maxPowerRPM) {
                $('#carForm\\.engineForm\\.maxPowerRPM')[0].value = engine.maxPowerRPM;
            }
            else {
                $('#carForm\\.engineForm\\.maxPowerRPM')[0].value = "";
            }

            if (engine.maxTorque) {
                $('#carForm\\.engineForm\\.maxTorque')[0].value = engine.maxTorque;
            }
            else {
                $('#carForm\\.engineForm\\.maxTorque')[0].value = "";
            }

            if (engine.maxTorqueRPM) {
                $('#carForm\\.engineForm\\.maxTorqueRPM')[0].value = engine.maxTorqueRPM;
            }
            else {
                $('#carForm\\.engineForm\\.maxTorqueRPM')[0].value = "";
            }
        }
    }
</script>
</#macro>

<#macro addSaveEngineFunctionScript>

<script type="application/javascript">
    function saveEngine() {
        var engine = createEngineFromDOM();

        bootbox.confirm("${language.getTextSource('engine.confirmSave')}", function (result) {
            //OK button
            if (result == true) {
                $.ajax({
                    url: '/${cmsContext}${engineURL}/${saveURL}',
                    type: 'POST',
                    data: JSON.stringify(engine),
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
        var engine = createEngineFromDOM();

        bootbox.confirm("${language.getTextSource('engine.confirmEdit')}", function (result) {
            //OK button
            if (result == true) {
                $.ajax({
                    url: '/${cmsContext}${engineURL}/' + engineId + '/${editURL}',
                    type: 'POST',
                    data: JSON.stringify(engine),
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
            if (result == true) {
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