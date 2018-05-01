<#import "/spring.ftl" as spring/>
<#import "../applicationMacros/pageLanguage.ftl" as language/>
<#import "../applicationMacros/carUtils.ftl" as carUtils/>

<#macro writeCarBrakeInfo brake>
    <#assign trainName = brake.train.getName()/>

    <ul class="list-group brake-set-list-group">
        <li class="list-group-item brake-set-list-group-item"><h3 class="brake-set-train-name">${language.getTextSource('brakeSet.${trainName}')}</h3>
            <dl class="dl-horizontal text-left">
                <dt>
                    ${language.getTextSource('brake.type')} :
                </dt>
                <dd>
                    <p class="text-muted">
                        ${language.getTextSource('brake.type.${brake.getType().getName()}')}
                    </p>
                </dd>
                <#if brake.getType() == "DISC">
                    <dt>
                        ${language.getTextSource('brake.disc.diameter')} :
                    </dt>
                    <dd>
                        <p class="text-muted">
                            ${carUtils.writeCarNumericData (brake.discDiameter!-1)}<#if brake.discDiameter??><em class="measure-unit-text"> ${language.getTextSource('MM')}</em></#if>
                        </p>
                    </dd>
                    <dt>
                        ${language.getTextSource('brake.disc.material')} :
                    </dt>
                    <dd>
                        <#if brake.discMaterial??>
                            <p class="text-muted">
                                ${language.getTextSource('brake.disc.material.${brake.discMaterial}')}
                            </p>
                        </#if>
                    </dd>
                    <dt>
                        ${language.getTextSource('brake.caliper.numOfPistons')} :
                    </dt>
                    <dd>
                        <p class="text-muted">
                            ${carUtils.writeCarNumericData (brake.caliperNumOfPistons!-1)}
                        </p>
                    </dd>
                </#if>
            </dl>
        </li>
    </ul>
</#macro>

<#macro writeBrakeEditFields brake brakeType objectBindingPath brakeTrain>
    <#assign brakeTrainName = brakeTrain.getName()/>
    <#assign baseBindingPath = "${objectBindingPath?string}${brakeTrainName}DiscBrake"/>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="text-left">${language.getTextSource('brakeSet.${brakeTrainName}')}</h4>
        </div>
        <div class="panel-body">
            <dl id="${brakeTrainName}-brake-main-dl" class="dl-horizontal dl-horizontal-edit text-left">
                <#if brake.id??>
                    <dt>
                        ${language.getTextSource('id')}
                    </dt>
                    <dd>
                        <h5 class="entity-id text-muted">${brake.id}</h5>
                        <@spring.formHiddenInput "${baseBindingPath}.id", ""/>
                    </dd>
                </#if>
                <@spring.bind "${baseBindingPath}.train"/>
                <input type="hidden" id="${baseBindingPath?string?replace("CEFC.", "")}.train" name="${baseBindingPath?string?replace("CEFC.", "")}.train" class="form-control" value="${brakeTrain}">

                <dt>
                    ${language.getTextSource('brake.type')}
                </dt>
                <dd>
                    <@spring.bind "${objectBindingPath}${brakeTrainName}BrakeType"/>

                    <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                        <#list brakeTypes as brakeTypeItem>
                            <#assign brakeTypeName = brakeTypeItem.getName()/>
                            <option value="${brakeTypeItem}" <#if brakeTypeItem == brakeType> selected</#if>>${language.getTextSource('brake.type.${brakeTypeName}')}</option>
                        </#list>
                    </select>
                </dd>

                <#if brakeType == "DISC">
                    <dt id="${brakeTrainName}-brake-disc-diameter-dt">
                        ${language.getTextSource('brake.disc.diameter')}
                    </dt>
                    <dd id="${brakeTrainName}-brake-disc-diameter-dd">
                        <@spring.formInput "${baseBindingPath?string}.discDiameter", "class=form-control placeholder=${language.getTextSource('MM')}", "text"/>
                    </dd>
                    <dt id="${brakeTrainName}-brake-disc-material-dt">
                        ${language.getTextSource('brake.disc.material')}
                    </dt>
                    <dd id="${brakeTrainName}-brake-disc-material-dd">
                        <@spring.bind "${baseBindingPath}.discMaterial"/>

                        <select id="${spring.status.expression}" name="${spring.status.expression}" class="form-control">
                            <#list brakeDiscMaterials as brakeDiscMaterial>
                                <option value="${brakeDiscMaterial}" <#if spring.status.value?? && brakeDiscMaterial == spring.status.value!""> selected</#if>>${language.getTextSource('brake.disc.material.${brakeDiscMaterial}')}</option>
                            </#list>
                        </select>
                    </dd>
                    <dt id="${brakeTrainName}-brake-disc-num-of-pistons-dt">
                        ${language.getTextSource('brake.caliper.numOfPistons')}
                    </dt>
                    <dd id="${brakeTrainName}-brake-disc-num-of-pistons-dd">
                        <@spring.formInput "${baseBindingPath?string}.caliperNumOfPistons", "class=form-control", "text"/>
                    </dd>
                </#if>
            </dl>
        </div>
    </div>
</#macro>

<#macro addLoadBrakeDiscFieldsScriptFunction>
    <script type="application/javascript">

        function addBrakeTypeSelectListener() {
          <#list brakeTrains as brakeTrain>
              <#assign brakeTrainName = brakeTrain.getName()/>
              $('#editForm\\.brakeSetEditForm\\.${brakeTrainName}BrakeType').on('change', function() {
                    if (this.value === 'DISC') {
                        addDiscBrakeInputs('${brakeTrainName}');
                    } else {
                        removeDiscBrakeInputs('${brakeTrainName}');
                    }
                });
          </#list>
        }
    </script>
</#macro>

<#macro addDiscBrakeInputsFunctionsScript>

    <script type="application/javascript">
        function addDiscBrakeInputs(brakeTrain) {
            const discDiameterDt = $('<dt>', {'id' : brakeTrain + '-brake-disc-diameter-dt'});
            discDiameterDt.append("${language.getTextSource('brake.disc.diameter')}");
            const baseBrakeDiscId = 'editForm.brakeSetEditForm.' + brakeTrain + 'DiscBrake.';
            const brakeDiscDiameterInputId = baseBrakeDiscId + 'discDiameter';
            const brakeDiscDiameterInput = $('<input>', {
                'type': 'text',
                'id': brakeDiscDiameterInputId,
                'name': brakeDiscDiameterInputId,
                'placeholder': '${language.getTextSource('MM')}',
                'class': 'form-control'
            });
            const discDiameterDd = $('<dd>', {'id' : brakeTrain + '-brake-disc-diameter-dd'});
            discDiameterDd.append(brakeDiscDiameterInput);

            const discMaterialDt = $('<dt>', {'id' : brakeTrain + '-brake-disc-material-dt'});
            discMaterialDt.append("${language.getTextSource('brake.disc.material')}");
            const brakeDiscMaterialInputId = baseBrakeDiscId + 'discMaterial';
            const brakeDiscMaterialSelect = $('<select>', {
                'id': brakeDiscMaterialInputId,
                'name': brakeDiscMaterialInputId,
                'class': 'form-control'
            });
            <#list brakeDiscMaterials?reverse as brakeDiscMaterial>
                brakeDiscMaterialSelect.append($('<option></option>', {
                    'value': '${brakeDiscMaterial}'
                })
                        .text("${language.getTextSource('brake.disc.material.${brakeDiscMaterial}')}"));
            </#list>
            const discMaterialDd = $('<dd>', {'id' : brakeTrain + '-brake-disc-material-dd'});
            discMaterialDd.append(brakeDiscMaterialSelect);

            const numberOfPistonsDt = $('<dt>', {'id' : brakeTrain + '-brake-disc-num-of-pistons-dt'});
            numberOfPistonsDt.append("${language.getTextSource('brake.caliper.numOfPistons')}");
            const brakeNumberOfPistonsInputId = baseBrakeDiscId + 'caliperNumOfPistons';
            const brakeNumberOfPistonsInput = $('<input>', {
                'type': 'text',
                'id': brakeNumberOfPistonsInputId,
                'name': brakeNumberOfPistonsInputId,
                'class': 'form-control'
            });
            const numberOfPistonsDd = $('<dd>', {'id' : brakeTrain + '-brake-disc-num-of-pistons-dd'});
            numberOfPistonsDd.append(brakeNumberOfPistonsInput);

            const mainDlSelector = '#' + brakeTrain + '-brake-main-dl';
            $(mainDlSelector).append(discDiameterDt);
            $(mainDlSelector).append(discDiameterDd);
            $(mainDlSelector).append(discMaterialDt);
            $(mainDlSelector).append(discMaterialDd);
            $(mainDlSelector).append(numberOfPistonsDt);
            $(mainDlSelector).append(numberOfPistonsDd);
        }

        function removeDiscBrakeInputs(brakeTrain) {
            $('#' + brakeTrain + '-brake-disc-diameter-dt').remove();
            $('#' + brakeTrain + '-brake-disc-diameter-dd').remove();
            $('#' + brakeTrain + '-brake-disc-material-dt').remove();
            $('#' + brakeTrain + '-brake-disc-material-dd').remove();
            $('#' + brakeTrain + '-brake-disc-num-of-pistons-dt').remove();
            $('#' + brakeTrain + '-brake-disc-num-of-pistons-dd').remove();
        }
    </script>
</#macro>