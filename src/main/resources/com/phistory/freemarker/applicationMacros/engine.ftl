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
    <#--By loading a null Id we can easily erase all the inputs -->
        fillEngineInputValues({});

        $('#engine-code-selection-table').addClass('sr-only');
        $('#engine-id-dt').addClass('sr-only');
        $('#engine-id-dd').addClass('sr-only');
        $('#engine-save-or-edit-link').attr("onClick", "saveEntity('<@spring.url "/${cmsContext}${enginesURL}/${saveURL}"/>', '${language.getTextSource('engine.confirmSave')}')");
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
                        url: '<@spring.url "/${cmsContext}${enginesURL}/"/>' + engineId,
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
                        $('#engine-save-or-edit-link').attr("onClick", "editEngine()");
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
        if (engine != null) {
            if (engine.id != null) {
                document.getElementById('carForm.engineForm.id.label').innerText = engine.id;
                document.getElementById('carForm.engineForm.id').value = engine.id;
            }
            else {
                document.getElementById('carForm.engineForm.id.label').innerText = "";
                document.getElementById('carForm.engineForm.id').value = "";
            }

            if (engine.code != null) {
                document.getElementById('carForm.engineForm.code').value = engine.code;
            }
            else {
                document.getElementById('carForm.engineForm.code').value = "";
            }

            if (engine.type != null) {
                document.getElementById('carForm.engineForm.type').value = engine.type;
            }
            else {
                document.getElementById('carForm.engineForm.type').value = "";
            }

            if (engine.cylinderDisposition != null) {
                document.getElementById('carForm.engineForm.cylinderDisposition').value = engine.cylinderDisposition;
            }
            else {
                document.getElementById('carForm.engineForm.cylinderDisposition').value = "";
            }

            if (engine.numberOfCylinders != null) {
                document.getElementById('carForm.engineForm.numberOfCylinders').value = engine.numberOfCylinders;
            }
            else {
                document.getElementById('carForm.engineForm.numberOfCylinders').value = "";
            }

            if (engine.numberOfValves != null) {
                document.getElementById('carForm.engineForm.numberOfValves').value = engine.numberOfValves;
            }
            else {
                document.getElementById('carForm.engineForm.numberOfValves').value = "";
            }

            if (engine.size != null) {
                document.getElementById('carForm.engineForm.size').value = engine.size;
            }
            else {
                document.getElementById('carForm.engineForm.size').value = "";
            }

            if (engine.maxPower != null) {
                document.getElementById('carForm.engineForm.maxPower').value = engine.maxPower;
            }
            else {
                document.getElementById('carForm.engineForm.maxPower').value = "";
            }

            if (engine.maxPowerRPM != null) {
                document.getElementById('carForm.engineForm.maxPowerRPM').value = engine.maxPowerRPM;
            }
            else {
                document.getElementById('carForm.engineForm.maxRPM').value = "";
            }

            if (engine.maxTorque != null) {
                document.getElementById('carForm.engineForm.maxTorque').value = engine.maxTorque;
            }
            else {
                document.getElementById('carForm.engineForm.maxTorque').value = "";
            }

            if (engine.maxTorqueRPM != null) {
                document.getElementById('carForm.engineForm.maxTorqueRPM').value = engine.maxTorqueRPM;
            }
            else {
                document.getElementById('carForm.engineForm.maxTorqueRPM').value = "";
            }
        }
    }
</script>
</#macro>